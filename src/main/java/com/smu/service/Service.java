package com.smu.service;

import com.smu.domain.Matches;
import com.smu.domain.Player;
import com.smu.util.JdbcUtils;
import lombok.val;

import java.sql.*;


/**
 * @Author: MingYun
 * @Date: 2024-02-28 17:25
 */
public class Service  {

    public   void checkAndCreateTable() throws SQLException {
        Connection conn=null;
        Statement st=null;
         conn = JdbcUtils.getConnection();
         st=conn.createStatement();

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
        Connection conn=null;
        Statement st=null;

            conn = JdbcUtils.getConnection();
            st=conn.createStatement();
            String clearPlayer ="DELETE FROM Player";
            String clearMatches ="DELETE FROM Matches";
            st.executeUpdate(clearPlayer);
            st.executeUpdate(clearMatches);

        System.out.println("clear all data success");

    }

    public void addPlayer(Player player) throws SQLException {
        Connection conn=null;
        Statement st=null;
        conn = JdbcUtils.getConnection();
        String sql = "INSERT INTO Player (ID, Name, Birthdate, State) VALUES (?, ?, ?, ?);";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, player.getId());
        preparedStatement.setString(2, player.getName());
        preparedStatement.setDate(3, Date.valueOf(player.getBirthdate()));
        preparedStatement.setString(4, player.getState());
        preparedStatement.executeUpdate();
        System.out.println("insert success");
    }
    public  void addMatches(Matches matches) throws SQLException {
        Connection conn=null;
        Statement st=null;
        conn = JdbcUtils.getConnection();
        String sql = "INSERT INTO MatchDetails (HostID, GuestID, Start, End, HostWin, PreRatingHost, PostRatingHost, PreRatingGuest, PostRatingGuest) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"；
       PreparedStatement preparedStatement =  conn.prepareStatement(sql);
    }


}

