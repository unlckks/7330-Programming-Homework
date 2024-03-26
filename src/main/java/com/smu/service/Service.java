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
public class Service {

    public void checkAndCreateTable() throws SQLException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {

            conn = JdbcUtils.getConnection();
            st = conn.createStatement();

            String sqlPlayer = "CREATE TABLE IF NOT EXISTS Player (" +
                    "ID INT PRIMARY KEY," +
                    "Name VARCHAR(255) UNIQUE NOT NULL," +
                    "Birthdate DATE NOT NULL," +
                    "Rating INT," +
                    "State CHAR(2));";
            st.execute(sqlPlayer);

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
            st.execute(sqlMatches);
            System.out.println("e------>Checked and created Player and Matches tables if not exist");
        } catch (Exception e) {
            System.out.println(" Input Invalid");
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }


    public void clearAllData() throws SQLException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();

            st = conn.createStatement();

            String clearPlayer = "DELETE FROM Player";
            String clearMatches = "DELETE FROM Matches ";

            st.execute(clearPlayer);
            st.execute(clearMatches);


            System.out.println("r------>clear all data");
        } catch (Exception e) {
            System.out.println(" Input Invalid");
        } finally {
            JdbcUtils.release(conn, st, rs);
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
            preparedStatement.setInt(4, player.getRating());
            preparedStatement.setString(5, player.getState());
            preparedStatement.execute();
            System.out.println("e------>insert player data success");
        } catch (Exception e) {
            System.out.println(" Input Invalid");
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }


    public void addMatches(Matches matches) throws SQLException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            String checkSql = "SELECT COUNT(*) FROM Matches WHERE HostID = ? AND GuestID = ? AND Start = ? AND End = ? AND HostWin = ?";
            PreparedStatement pst = conn.prepareStatement(checkSql);
            pst.setInt(1, matches.getHostID());
            pst.setInt(2, matches.getGuestID());
            pst.setTimestamp(3, Timestamp.valueOf(matches.getStart()));
            pst.setTimestamp(4, Timestamp.valueOf(matches.getEnd()));
            pst.setBoolean(5, matches.getHostWin());
            rs = pst.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
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
                preparedStatement.execute();
                System.out.println("m------>insert Matches data success");
            } else {
                System.out.println("m------> Match data already exists");
            }
        } catch (Exception e) {
            System.out.println(" Input Invalid");
        } finally {
            JdbcUtils.release(conn, st, rs);
        }

    }


    public void addMatchesInformation(Matches matches) throws SQLException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            String isGameResult = "SELECT COUNT(*) FROM Matches WHERE HostID = ? AND GuestID = ? AND Start = ? ";
            PreparedStatement pst = conn.prepareStatement(isGameResult);
            pst.setInt(1, matches.getHostID());
            pst.setInt(2, matches.getGuestID());
            pst.setTimestamp(3, Timestamp.valueOf(matches.getStart()));
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) == 0) {
                String sql = "INSERT INTO Matches (HostID, GuestID, start) VALUES (?, ?, ?)";
                pst.close();
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setInt(1, matches.getHostID());
                preparedStatement.setInt(2, matches.getGuestID());
                preparedStatement.setTimestamp(3, Timestamp.valueOf(matches.getStart()));
                preparedStatement.execute();
                System.out.println("n------>insert Matches information success");
            } else {
                System.out.println("Invalid case :Match already exists");
            }

        } catch (Exception e) {
            System.out.println(" Invalid case :Match already exists");
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (st != null) try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (conn != null) JdbcUtils.release(conn, null, null);
        }
    }


    public void resultGame(Matches matches) throws SQLException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            String isGameResult = "SELECT COUNT(*) FROM Matches WHERE HostID = ? AND GuestID = ?";
            PreparedStatement pst = conn.prepareStatement(isGameResult);
            pst.setInt(1, matches.getHostID());
            pst.setInt(2, matches.getGuestID());
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                String sql = "UPDATE Matches SET  Start = ?, End = ?, HostWin = ?, PreRatingHost = ?, PostRatingHost = ?, PreRatingGuest = ?, PostRatingGuest = ? WHERE HostID = ? AND GuestID = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setTimestamp(1, Timestamp.valueOf(matches.getStart()));
                preparedStatement.setTimestamp(2, Timestamp.valueOf(matches.getEnd()));
                preparedStatement.setBoolean(3, matches.getHostWin());
                preparedStatement.setInt(4, matches.getPreRatingHost());
                preparedStatement.setInt(5, matches.getPostRatingHost());
                preparedStatement.setInt(6, matches.getPreRatingGuest());
                preparedStatement.setInt(7, matches.getPostRatingGuest());
                preparedStatement.setInt(8, matches.getHostID());
                preparedStatement.setInt(9, matches.getGuestID());
                preparedStatement.executeUpdate();
                System.out.println("c------>update Matches success");
            } else {
                System.out.println("Reject Input");
            }
        } catch (Exception e) {
            System.out.println(" Input Invalid");
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }

    public void selectPlayer(String playerId) throws SQLException {
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
                System.out.println(player.getId() + ", " + player.getName() + ", " + player.getBirthdate() + ", " + player.getRating() + ", " + player.getState());
            }
        } catch (Exception e) {
            System.out.println(" Input Invalid");
        } finally {
            JdbcUtils.release(conn, st, rs);
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
            String lastPlayerName = "";
            while (resultSet.next()) {
                String playerId = resultSet.getString("PlayerID");
                String playerName = resultSet.getString("PlayerName");
                String opponentId = resultSet.getString("OpponentID");
                String opponentName = resultSet.getString("OpponentName");
                int wins = resultSet.getInt("Wins");
                int lost = resultSet.getInt("Lost");
                if (!playerName.equals(lastPlayerName)) {
                    System.out.println(playerId + ", " + playerName);
                    lastPlayerName = playerName;
                }

                System.out.println(opponentId + ", " + opponentName + ", " + wins + ", " + lost);
            }
            return player;
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }

    public void dateMatches(LocalDate startDate, LocalDate endDate) throws SQLException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            String sql = "SELECT " +
                    "    m.Start, " +
                    "    m.End, " +
                    "    h.Name AS HostName, " +
                    "    g.Name AS GuestName, " +
                    "    CASE " +
                    "        WHEN m.HostWin = 1 THEN 'H' " +
                    "        WHEN m.HostWin = 0 THEN 'G' " +
                    "    END AS Winner " +
                    "FROM " +
                    "    `Matches` m " +
                    "JOIN " +
                    "    `Player` h ON m.HostID = h.ID " +
                    "JOIN " +
                    "    `Player` g ON m.GuestID = g.ID " +
                    "WHERE " +
                    "    m.Start >= ? AND m.End < DATE_ADD(?, INTERVAL 1 DAY) " +
                    "ORDER BY " +
                    "    m.Start ASC, m.HostID ASC ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setDate(1, Date.valueOf(startDate));
            preparedStatement.setDate(2, Date.valueOf(endDate));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String startTime = resultSet.getString("start");
                String endTime = resultSet.getString("end");
                String hostName = resultSet.getString("HostName");
                String guestName = resultSet.getString("GuestName");
                String hostWins = resultSet.getString("Winner");
                System.out.println(startTime + ", " + endTime + ", " + hostName + ", " + guestName + ", " + hostWins);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }

    public void ListMatches(String id) throws SQLException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            String sql = "SELECT p.ID, p.Name, m.Start, m.End, " +
                    "CASE " +
                    "    WHEN p.ID = m.HostID THEN m.GuestID " +
                    "    ELSE m.HostID " +
                    "END AS OpponentID, " +
                    "CASE " +
                    "    WHEN p.ID = m.HostID THEN (SELECT Name FROM Player WHERE ID = m.GuestID) " +
                    "    ELSE (SELECT Name FROM Player WHERE ID = m.HostID) " +
                    "END AS OpponentName, " +
                    "CASE " +
                    "    WHEN (p.ID = m.HostID AND m.Hostwin = 1) THEN 'W' " +
                    "    WHEN (p.ID = m.GuestID AND m.Hostwin = 0) THEN 'W' " +
                    "    WHEN (p.ID = m.HostID AND m.Hostwin = 0) THEN 'L' " +
                    "    WHEN (p.ID = m.GuestID AND m.Hostwin = 1) THEN 'L' " +
                    "END AS Result, " +
                    "CASE " +
                    "    WHEN p.ID = m.HostID THEN m.PostRatingHost " +
                    "    ELSE m.PostRatingGuest " +
                    "END AS PostRating, " +
                    "m.PostRatingHost AS PostRating, "+
                    "CASE " +
                    "    WHEN p.ID = m.HostID THEN m.PreRatingHost " +
                    "    ELSE m.PreRatingGuest " +
                    "END AS PreRating " +
                    "FROM Player p " +
                    "INNER JOIN Matches m ON p.ID = m.HostID OR p.ID = m.GuestID " +
                    "WHERE p.ID = ? " +
                    "ORDER BY m.Start";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            int lastPostRating = -1;
            boolean isFirstRow = true;
            boolean isFlagged = false;
            while (resultSet.next()) {

                if (isFirstRow) {
                    System.out.println(resultSet.getInt("ID") + ", " + resultSet.getString("name"));
                    isFirstRow = false;
                }

                String start = resultSet.getString("Start");
                String end = resultSet.getString("End");
                String opponentName = resultSet.getString("OpponentName");
                int opponentID = resultSet.getInt("OpponentID");
                String result = resultSet.getString("Result");
                int postRating = resultSet.getInt("PostRating");

                if (result != null && postRating != 0) {
                    isFlagged = true ;

                    if (isFlagged && lastPostRating != -1 && resultSet.getInt("PreRating") != lastPostRating) {
                        System.out.println("inconsistent rating ");
                    }
                    System.out.println(start + ", " + end + ", " + opponentName + ", " + opponentID + ", " + result + ", " + postRating);

                    lastPostRating = postRating;
                }
                if (isFirstRow) {
                    System.out.println("No matches found for player ID: " + id);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            JdbcUtils.release(conn, st, rs);
        }

    }


}