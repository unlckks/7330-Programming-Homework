package com.smu.service;

import com.smu.domain.Matches;
import com.smu.domain.Player;
import com.smu.util.JdbcUtils;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 * @Author: MingYun
 * @Date: 2024-02-28 17:25
 */
public class Service {

    public void checkAndCreateTable() throws SQLException {
        Connection conn = null;
        Statement st = null;
        conn = JdbcUtils.getConnection();
        st = conn.createStatement();

        String sqlPlayer = "CREATE TABLE IF NOT EXISTS Player (" +
                "ID CHAR(8) PRIMARY KEY NOT NULL," +
                "Name VARCHAR(255) UNIQUE NOT NULL," +
                "Birthdate DATE NOT NULL," +
                "State CHAR(2) NOT NULL);";
        st.executeUpdate(sqlPlayer);

        String sqlMatches = "CREATE TABLE IF NOT EXISTS Matches (" +
                "HostID CHAR(8) NOT NULL," +
                "GuestID CHAR(8) NOT NULL," +
                "Start DATETIME NOT NULL," +
                "End DATETIME," +
                "Hostwin BOOLEAN," +
                "PreRatingHost INT," +
                "PreRatingGuest INT," +
                "PostRatingHost INT," +
                "PostRatingGuest INT," +
                "FOREIGN KEY (HostID) REFERENCES Player(ID)," +
                "FOREIGN KEY (GuestID) REFERENCES Player(ID));";
        st.executeUpdate(sqlMatches);

        System.out.println("Checked and created tables if not exist.");

    }


    public void clearAllData() throws SQLException {
        Connection conn = null;
        Statement st = null;

        conn = JdbcUtils.getConnection();
        st = conn.createStatement();
        //set foreign key off
        st.execute("SET foreign_key_checks = 0");

        String clearPlayer = "DELETE FROM Player";
        String clearMatches = "DELETE FROM Matches ";

        st.executeUpdate(clearPlayer);
        st.executeUpdate(clearMatches);
        st.execute("SET foreign_key_checks = 1");


        System.out.println("clear all data success");

    }

    public void addPlayer(Player player) throws SQLException {
        Connection conn = null;
        Statement st = null;
        conn = JdbcUtils.getConnection();
        String sql = "INSERT INTO Player (ID, Name, Birthdate, State) VALUES (?, ?, ?, ?);";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, player.getId());
        preparedStatement.setString(2, player.getName());
        preparedStatement.setDate(3, Date.valueOf(player.getBirthdate()));
        preparedStatement.setString(4, player.getState());
        preparedStatement.executeUpdate();
        System.out.println("insert player success");
    }

    public void addMatches(Matches matches) throws SQLException {
        Connection conn = null;
        Statement st = null;
        conn = JdbcUtils.getConnection();
        String sql = "INSERT INTO Matches (HostID, GuestID, Start, End, HostWin, PreRatingHost, PostRatingHost, PreRatingGuest, PostRatingGuest) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, matches.getHostID());
        preparedStatement.setString(2, matches.getGuestID());
        preparedStatement.setTimestamp(3, Timestamp.valueOf(matches.getStart()));
        preparedStatement.setTimestamp(4, Timestamp.valueOf(matches.getEnd()));
        preparedStatement.setInt(5, matches.getHostWin());
        preparedStatement.setInt(6, matches.getPreRatingHost());
        preparedStatement.setInt(7, matches.getPostRatingHost());
        preparedStatement.setInt(8, matches.getPreRatingGuest());
        preparedStatement.setInt(9, matches.getPostRatingGuest());
        preparedStatement.executeUpdate();
        System.out.println("insert Matches success");
    }


    public void addPlayedInformation(Matches matches) throws SQLException {
        Connection conn = null;
        Statement st = null;
        conn = JdbcUtils.getConnection();
        String sql = "INSERT INTO Matches (HostID, GuestID, start) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, matches.getHostID());
        preparedStatement.setString(2, matches.getGuestID());
        preparedStatement.setTimestamp(3, Timestamp.valueOf(matches.getStart()));
        preparedStatement.executeUpdate();
        System.out.println("add played information success");


    }

    public void resultGame(Matches matches) throws SQLException {
        Connection conn = null;
        Statement st = null;
        conn = JdbcUtils.getConnection();

        String sql = "IF EXISTS (SELECT * FROM dbprog.TABLES WHERE TABLE_NAME = 'result' BEGININSERT INTO result (HostID, GuestID, Start, End, HostWin, PreRatingHost, PostRatingHost, PreRatingGuest, PostRatingGuest) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, matches.getHostID());
        preparedStatement.setString(2, matches.getGuestID());
        preparedStatement.setTimestamp(3, Timestamp.valueOf(matches.getStart()));
        preparedStatement.setTimestamp(4, Timestamp.valueOf(matches.getStart()));
        preparedStatement.setInt(5, matches.getHostWin());
        preparedStatement.setInt(6, matches.getPreRatingHost());
        preparedStatement.setInt(7, matches.getPostRatingHost());
        preparedStatement.setInt(8, matches.getPreRatingGuest());
        preparedStatement.setInt(9, matches.getPostRatingGuest());
        preparedStatement.executeUpdate();
        System.out.println("update Matches success");
    }

    public Player selectPlayer(String playerId) throws SQLException {
        Connection conn = null;
        Statement st = null;
        conn = JdbcUtils.getConnection();
        String sql = "SELECT ID, name, birthdate, state FROM Player WHERE ID = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, playerId);
        ResultSet resultSet = preparedStatement.executeQuery();
        Player player = new Player();
        if (resultSet.next()) {
            player.setId(resultSet.getString("id"));
            player.setName(resultSet.getString("name"));
            LocalDate birthdate = resultSet.getDate("birthdate").toLocalDate();
            player.setBirthdate(birthdate);
            player.setState(resultSet.getString("state"));
        }
        return player;

    }

    public Player selectWinList(String id) throws SQLException {
        Connection conn = null;
        Statement st = null;
        conn = JdbcUtils.getConnection();
        String sql = "SELECT p.ID AS PlayerID, p.Name AS PlayerName, " +
                "COALESCE(opponent.ID, 'No matches') AS OpponentID, " +
                "COALESCE(opponent.Name, 'No opponents') AS OpponentName, " +
                "SUM(CASE WHEN (p.ID = m.HostID AND m.Hostwin = TRUE) OR (p.ID = m.GuestID AND m.Hostwin = FALSE) THEN 1 ELSE 0 END) AS Wins, " +
                "SUM(CASE WHEN (p.ID = m.HostID AND m.Hostwin = FALSE) OR (p.ID = m.GuestID AND m.Hostwin = TRUE) THEN 1 ELSE 0 END) AS Losses " +
                "FROM Player p " +
                "LEFT JOIN Matches m ON p.ID = m.HostID OR p.ID = m.GuestID " +
                "LEFT JOIN Player opponent ON (p.ID = m.HostID AND opponent.ID = m.GuestID) OR (p.ID = m.GuestID AND opponent.ID = m.HostID) " +
                "WHERE m.Hostwin IS NOT NULL " +
                "GROUP BY p.ID, opponent.ID " +
                "ORDER BY p.ID, COALESCE(opponent.ID, 'No matches');";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        Player player = new Player();
        while (resultSet.next()) {
            String playerId = resultSet.getString("PlayerID");
            String playerName = resultSet.getString("PlayerName");
            String opponentId = resultSet.getString("OpponentID");
            String opponentName = resultSet.getString("OpponentName");
            int wins = resultSet.getInt("Wins");
            int losses = resultSet.getInt("Losses");
            System.out.println("Player ID: " + playerId + ", Name: " + playerName);
            System.out.println("Opponent ID: " + opponentId + ", Opponent Name: " + opponentName + ", Wins: " + wins + ", Losses: " + losses);
        }
        return player;
    }

}