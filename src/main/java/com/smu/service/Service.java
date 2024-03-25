package com.smu.service;

import com.smu.domain.Matches;
import com.smu.domain.Player;
import com.smu.util.JdbcUtils;

import java.sql.*;
import java.time.LocalDate;


/**
 * @Author: MingYun
 * @Date: 2024-02-28 17:25
 */
public class Service   {

    public void checkAndCreateTable() throws SQLException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs =null ;
        try {

            conn = JdbcUtils.getConnection();
            st = conn.createStatement();

            String sqlPlayer = "CREATE TABLE IF NOT EXISTS Player (" +
                    "ID INT PRIMARY KEY," +
                    "Name VARCHAR(255) UNIQUE NOT NULL," +
                    "Birthdate DATE NOT NULL," +
                    "Rating INT,"+
                    "State CHAR(2));";
            st.executeUpdate(sqlPlayer);

            String sqlMatches = "CREATE TABLE IF NOT EXISTS Matches (" +
                    "HostID INT," +
                    "GuestID INT," +
                    "Start DATETIME NOT NULL," +
                    "End DATETIME," +
                    "HostWin BOOLEAN," +
                    "PreRatingHost INT," +
                    "PreRatingGuest INT," +
                    "PostRatingHost INT," +
                    "PostRatingGuest INT);";
            st.executeUpdate(sqlMatches);

            System.out.println("e------>Checked and created Player and Matches tables if not exist");

        } finally {
            JdbcUtils.release(conn,st,rs);
        }
    }


    public void clearAllData() throws SQLException {
        Connection conn = null;
        Statement st = null;
        try {
            conn = JdbcUtils.getConnection();

            st = conn.createStatement();

            String clearPlayer = "DELETE FROM Player";
            String clearMatches = "DELETE FROM Matches ";

            st.executeUpdate(clearPlayer);
            st.executeUpdate(clearMatches);


            System.out.println("r------>clear all data");
        } finally {
            if (conn != null) conn.close();
        }
    }

    public void addPlayer(Player player) throws SQLException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            String sql = "INSERT INTO Player (ID, Name, Birthdate,Rating, State) VALUES (?, ?, ?, ?,?);";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, player.getId());
            preparedStatement.setString(2, player.getName());
            preparedStatement.setDate(3, Date.valueOf(player.getBirthdate()));
            preparedStatement.setInt(4,player.getRating());
            preparedStatement.setString(5, player.getState());
            preparedStatement.executeUpdate();
            System.out.println("p------>insert player data success");
        } finally {
            JdbcUtils.release(conn,st,rs);
        }
    }

    public void addMatches(Matches matches) throws SQLException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null ;
        try {
            conn = JdbcUtils.getConnection();

            String sql = "INSERT INTO Matches (HostID, GuestID, Start, End, HostWin, PreRatingHost, PostRatingHost, PreRatingGuest, PostRatingGuest) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, matches.getHostID());
            preparedStatement.setInt(2, matches.getGuestID());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(matches.getStart()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(matches.getEnd()));
            preparedStatement.setBoolean(5, matches.getHostWin());
            preparedStatement.setInt(6, matches.getPreRatingHost());
            preparedStatement.setInt(7, matches.getPostRatingHost());
            preparedStatement.setInt(8, matches.getPreRatingGuest());
            preparedStatement.setInt(9, matches.getPostRatingGuest());
            preparedStatement.executeUpdate();
            System.out.println("m------>insert Matches data success");
        } finally {
            JdbcUtils.release(conn,st,rs);
        }
    }


    public void addMatchesInformation(Matches matches) throws SQLException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null ;
        try {
            conn = JdbcUtils.getConnection();
            String sql = "INSERT INTO Matches (HostID, GuestID, start) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, matches.getHostID());
            preparedStatement.setInt(2, matches.getGuestID());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(matches.getStart()));
            preparedStatement.executeUpdate();
            System.out.println("n------>insert Matches information success");
        } finally {
            JdbcUtils.release(conn,st,rs);
        }

    }
    public Boolean gameExists(Integer HostId) throws SQLException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null ;
        try {
            conn = JdbcUtils.getConnection();
            String  sql = "SELECT count(HostId) FROM Matches WHERE HostId = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,HostId);
            ResultSet resultSet  = preparedStatement.executeQuery();
            return resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            return false;
        }finally {
            JdbcUtils.release(conn,st,rs);
        }

    }
    public void resultGame(Matches matches) throws SQLException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            String sql = "UPDATE Matches SET GuestID = ?, start = ?, end = ?, HostWin = ?, PreRatingHost = ?, PostRatingHost = ?, PreRatingGuest = ?, PostRatingGuest = ? WHERE HostID = ? ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(2, matches.getGuestID());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(matches.getStart()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(matches.getStart()));
            preparedStatement.setBoolean(5, matches.getHostWin());
            preparedStatement.setInt(6, matches.getPreRatingHost());
            preparedStatement.setInt(7, matches.getPostRatingHost());
            preparedStatement.setInt(8, matches.getPreRatingGuest());
            preparedStatement.setInt(9, matches.getPostRatingGuest());
            preparedStatement.setInt(1, matches.getHostID());
            preparedStatement.executeUpdate();
            System.out.println("c------>update Matches success");
        }finally {
            JdbcUtils.release(conn,st,rs);
        }
    }

    public Player selectPlayer(String playerId) throws SQLException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT ID, name, birthdate, rating, state FROM Player WHERE ID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, playerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Player player = new Player();
            if (resultSet.next()) {
                player.setId(resultSet.getInt("id"));
                player.setName(resultSet.getString("name"));
                LocalDate birthdate = resultSet.getDate("birthdate").toLocalDate();
                player.setBirthdate(birthdate);
                player.setRating(resultSet.getInt("rating"));
                player.setState(resultSet.getString("state"));
                System.out.println(player.getId() + ", " + player.getName() + ", " + player.getBirthdate()+", " + player.getRating() + ", " + player.getState());
            }
            return player;

        } finally {
            JdbcUtils.release(conn,st,rs);
        }
    }

    public Player selectWinList(String id) throws SQLException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            String sql = "SELECT p.ID AS PlayerID, p.Name AS PlayerName, " +
                    "COALESCE(opponent.ID, 'No matches') AS OpponentID, " +
                    "COALESCE(opponent.Name, 'No opponents') AS OpponentName, " +
                    "SUM(CASE WHEN (p.ID = m.HostID AND m.Hostwin = TRUE) OR (p.ID = m.GuestID AND m.Hostwin = FALSE) THEN 1 ELSE 0 END) AS Wins, " +
                    "SUM(CASE WHEN (p.ID = m.HostID AND m.Hostwin = FALSE) OR (p.ID = m.GuestID AND m.Hostwin = TRUE) THEN 1 ELSE 0 END) AS Lost " +
                    "FROM Player p " +
                    "LEFT JOIN Matches m ON p.ID = m.HostID OR p.ID = m.GuestID " +
                    "LEFT JOIN Player opponent ON (p.ID = m.HostID AND opponent.ID = m.GuestID) OR (p.ID = m.GuestID AND opponent.ID = m.HostID) " +
                    "WHERE p.ID = ? AND m.Hostwin IS NOT NULL " +
                    "GROUP BY p.ID, opponent.ID " +
                    "ORDER BY p.ID, COALESCE(opponent.ID, 'No matches');";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Player player = new Player();
            while (resultSet.next()) {
                String playerId = resultSet.getString("PlayerID");
                String playerName = resultSet.getString("PlayerName");
                String opponentId = resultSet.getString("OpponentID");
                String opponentName = resultSet.getString("OpponentName");
                int wins = resultSet.getInt("Wins");
                int lost = resultSet.getInt("Lost");
                System.out.println("Player ID: " + playerId + ", player Name: " + playerName);
                System.out.println("Opponent Player ID: " + opponentId + ", Opponent Player Name: " + opponentName + ", Wins: " + wins + ", lost: " + lost);
            }
            return player;
        } finally {
            JdbcUtils.release(conn,st,rs);
        }
    }

    public void dateMatches(LocalDate startDate, LocalDate endDate) throws SQLException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            String sql = "SELECT " +
                    "    m.start, " +
                    "    m.end, " +
                    "    h.Name AS HostName, " +
                    "    g.Name AS GuestName, " +
                    "    CASE " +
                    "        WHEN m.HostWin = 1 THEN 'H' " +
                    "        WHEN m.HostWin = 0 THEN 'G' " +
                    "    END AS Winner," +
                    "    h.Rating AS PreRatingHost, " +
                    "    g.Rating AS PreRatingGuest, " +
                    "    (h.Rating + IF(m.HostWin = 1, m.PostRatingHost - m.PreRatingHost, m.PreRatingHost - m.PostRatingHost)) AS PostRatingHost, " +
                    "    (g.Rating + IF(m.HostWin = 0, m.PostRatingGuest - m.PreRatingGuest, m.PreRatingGuest - m.PostRatingGuest)) AS PostRatingGuest " +
                    "FROM " +
                    "    `Matches` m " +
                    "JOIN " +
                    "    `Player` h ON m.HostID = h.ID " +
                    "JOIN " +
                    "    `Player` g ON m.GuestID = g.ID " +
                    "WHERE " +
                    "    m.start >= ? AND (m.end <= ? OR m.end IS NULL) " +
                    "ORDER BY " +
                    "    m.start ASC, m.HostID ASC";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setDate(1, Date.valueOf(startDate));
            preparedStatement.setDate(2, Date.valueOf(endDate));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String startTime = resultSet.getString("start");
                String endTime = resultSet.getString("end");
                String hostName = resultSet.getString("HostName");
                String guestName = resultSet.getString("GuestName");
                String  hostWins = resultSet.getString("Winner");
                System.out.println(startTime + ", " + endTime + ", " + hostName + ", " + guestName + ", " + hostWins);
            }
        } finally {
            JdbcUtils.release(conn,st,rs);
        }
    }

    public void ListMatches(String id) throws SQLException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null ;
        try {
            conn = JdbcUtils.getConnection();
            String sql = "SELECT p.ID, p.Name, m.Start, m.End, "
                    + "CASE WHEN p.ID = m.HostID THEN m.GuestID ELSE m.HostID END AS OpponentID, "
                    + "CASE WHEN p.ID = m.HostID THEN (SELECT Name FROM Player WHERE ID = m.GuestID) "
                    + "ELSE (SELECT Name FROM Player WHERE ID = m.HostID) END AS OpponentName, "
                    + "CASE WHEN (p.ID = m.HostID AND m.Hostwin = TRUE) OR (p.ID = m.GuestID AND m.Hostwin = FALSE) "
                    + "THEN 'W' ELSE 'L' END AS Result, "
                    + "CASE WHEN p.ID = m.HostID THEN m.PostRatingHost ELSE m.PostRatingGuest END AS PostRating, "
                    + "CASE WHEN p.ID = m.HostID THEN m.PreRatingHost ELSE m.PreRatingGuest END AS PreRating "
                    + "FROM Player p "
                    + "INNER JOIN Matches m ON p.ID = m.HostID OR p.ID = m.GuestID "
                    + "WHERE p.ID = ? "
                    + "ORDER BY m.Start";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            int lastPostRating = -1 ;
            boolean isFirstRow = true;
            while (resultSet.next()) {
                if (isFirstRow) {
                    System.out.println(resultSet.getInt("ID") + ", " + resultSet.getString("name"));
                    isFirstRow = false;
                }
                int currentPreRating = resultSet.getInt("PreRating");
                if (lastPostRating != -1 && currentPreRating != lastPostRating) {

                    System.out.println("inconsistent rating");
                }
                String start = resultSet.getString("Start");
                String end = resultSet.getString("End");
                String opponentName = resultSet.getString("OpponentName");
                int opponentID = resultSet.getInt("OpponentID");
                int postRating = resultSet.getInt("PostRating");
                String result = resultSet.getString("Result");
                System.out.println(start + ", " + end + ", "
                        +  opponentName + ", " +opponentID+ ", "
                        +postRating + ", " +result);
                lastPostRating = resultSet.getInt("PostRating");
                if (isFirstRow) {
                    System.out.println("No matches found for player ID: " + id);
                }
            }
        } finally {
            JdbcUtils.release(conn,st,rs);
        }

    }


}