/*
package com.smu.controller;

import com.smu.domain.Matches;
import com.smu.domain.Player;
import com.smu.service.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

*/
/**
 * @Author: MingYun
 * @Date: 2024-02-28 16:46
 *//*

public class executeCommand {

    private final static Service service = new Service();

    */
/**
     *
     * @param line
     * @throws SQLException
     *//*

    public static void executeCommand(String line) throws SQLException {
        String[] parts = line.split(",");

        if (parts.length == 0) {
            return;
        }
        String command = parts[0];
        switch (command) {
            case "e":
                System.out.println("----------------------------------------------------------");
                service.checkAndCreateTable();
                break;
            case "r":
                System.out.println("----------------------------------------------------------");
                service.clearAllData();
                break;
            case "p":
                System.out.println("----------------------------------------------------------");
                if (parts.length == 6) {
                    Player player = new Player();
                    Integer Id = Integer.valueOf(parts[1].trim());
                    player.setId(Id);
                    player.setName(parts[2].trim());
                    player.setBirthdate(LocalDate.parse(parts[3].trim(), DateTimeFormatter.ofPattern("yyyyMMdd")));
                    Integer rating = Integer.valueOf(parts[4].trim());
                    player.setRating(rating);
                    player.setState(parts[5].trim());
                    service.addPlayer(player);
                }
                break;

            case "m":
                System.out.println("----------------------------------------------------------");
                if (parts.length == 10) {
                    Matches matches = new Matches();
                    Integer HostId = Integer.valueOf(parts[1].trim());
                    matches.setHostID(HostId);
                    Integer GuestId = Integer.valueOf(parts[2].trim());
                    matches.setGuestID(GuestId);
                    //Use 24-hour format for HH
                    matches.setStart(LocalDateTime.parse(parts[3].trim(), DateTimeFormatter.ofPattern("yyyyMMdd:HH:mm:ss")));
                    //Use 24-hour format for HH
                    matches.setEnd(LocalDateTime.parse(parts[4].trim(), DateTimeFormatter.ofPattern("yyyyMMdd:HH:mm:ss")));
                    String hostWinValue = parts[5].trim();
                    boolean hostWinBoolean = "1".equals(hostWinValue);
                    matches.setHostWin(hostWinBoolean);
                    matches.setPreRatingHost(Integer.valueOf(parts[6].trim()));
                    matches.setPostRatingHost(Integer.valueOf(parts[7].trim()));
                    matches.setPreRatingGuest(Integer.valueOf(parts[8].trim()));
                    matches.setPostRatingGuest(Integer.valueOf(parts[9].trim()));
                    service.addMatches(matches);
                }

                break;

            case "n":
                System.out.println("----------------------------------------------------------");
                if (parts.length == 4) {
                    Matches matches = new Matches();
                    Integer HostId = Integer.valueOf(parts[1].trim());
                    Integer GuestID = Integer.valueOf(parts[2].trim());
                    matches.setHostID(HostId);
                    matches.setGuestID(GuestID);
                    matches.setStart(LocalDateTime.parse(parts[3].trim(), DateTimeFormatter.ofPattern("yyyyMMdd:HH:mm:ss")));
                    service.addPlayedInformation(matches);
                }

                break;

            case "c":
                System.out.println("----------------------------------------------------------");
                if (parts.length == 2) {
                    Integer ID = Integer.valueOf(parts[1].trim());
                    Boolean  exists = service.gameExists(ID);
                    if(exists){
                        Matches matches = new Matches();
                        Integer HostId = Integer.valueOf(parts[1].trim());
                        Integer GuestID = Integer.valueOf(parts[2].trim());
                        matches.setHostID(HostId);
                        matches.setGuestID(GuestID);
                        //Use 24-hour format for HH
                        matches.setStart(LocalDateTime.parse(parts[3].trim(), DateTimeFormatter.ofPattern("yyyyMMdd:HH:mm:ss")));
                        //Use 24-hour format for HH
                        matches.setEnd(LocalDateTime.parse(parts[4].trim(), DateTimeFormatter.ofPattern("yyyyMMdd:HH:mm:ss")));
                        String hostWinValue = parts[5].trim();
                        boolean hostWinBoolean = "1".equals(hostWinValue);
                        matches.setHostWin(hostWinBoolean);
                        matches.setPreRatingHost(Integer.valueOf(parts[6].trim()));
                        matches.setPostRatingHost(Integer.valueOf(parts[7].trim()));
                        matches.setPreRatingGuest(Integer.valueOf(parts[8].trim()));
                        matches.setPostRatingGuest(Integer.valueOf(parts[9].trim()));
                        service.resultGame(matches);
                    }else{
                        System.out.println("reject");
                    }
                }
                break;
            case "P":
                System.out.println("----------------------------------------------------------");
                if (parts.length == 2) {
                    String ID = parts[1].trim();
                    service.selectPlayer(ID);

                }

                break;
            case "A":
                System.out.println("----------------------------------------------------------");
                if (parts.length == 2) {
                    String ID = parts[1].trim();
                    service.selectWinList(ID);
                }
                break;
            case "D":
                System.out.println("----------------------------------------------------------");
                if (parts.length == 3) {
                    LocalDate startDate = LocalDate.parse(parts[1].trim(), DateTimeFormatter.ofPattern("yyyyMMdd"));
                    LocalDate endDate = LocalDate.parse(parts[2].trim(), DateTimeFormatter.ofPattern("yyyyMMdd"));
                    service.dateMatches(startDate, endDate);
                }
                break;

            case "M":
                System.out.println("----------------------------------------------------------");
                if (parts.length == 2) {
                    String ID = parts[1].trim();
                    service.ListMatches(ID);
                }
                break;
            default:
                System.out.println("input invalid");
        }
    }


}

*/
