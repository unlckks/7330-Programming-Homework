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

/**
 * @Author: MingYun
 * @Date: 2024-02-28 16:46
 */
public class main {

    private final static Service service = new Service();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the CSV file:");
        String fileName = scanner.nextLine();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                executeCommand(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void executeCommand(String line) throws SQLException {
        String[] parts = line.split(",");
        // skip empty lines
        if (parts.length == 0) {
            return;
        }
        String command = parts[0];
        switch (command) {
            case "e":
                service.checkAndCreateTable();
                break;
            case "r":
                service.clearAllData();
                break;
            case "p":
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
                if (parts.length == 10) {
                    Matches matches = new Matches();
                    Integer HostId = Integer.valueOf(parts[1].trim());
                    matches.setHostID(HostId);
                    Integer GuestId = Integer.valueOf(parts[2].trim());
                    matches.setGuestID(GuestId);
                    matches.setStart(LocalDateTime.parse(parts[3].trim(), DateTimeFormatter.ofPattern("yyyyMMdd:HH:mm:ss")));
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
                    if (parts.length == 10) {
                    Matches matches = new Matches();
                    Integer HostId = Integer.valueOf(parts[1].trim());
                    Integer GuestID = Integer.valueOf(parts[2].trim());
                    matches.setHostID(HostId);
                    matches.setGuestID(GuestID);
                    matches.setStart(LocalDateTime.parse(parts[3].trim(), DateTimeFormatter.ofPattern("yyyyMMdd:HH:mm:ss")));
                    matches.setEnd(LocalDateTime.parse(parts[4].trim(), DateTimeFormatter.ofPattern("yyyyMMdd:HH:mm:ss")));
                    String hostWinValue = parts[5].trim();
                    boolean hostWinBoolean = "1".equals(hostWinValue);
                    matches.setHostWin(hostWinBoolean);
                    matches.setPreRatingHost(Integer.valueOf(parts[6].trim()));
                    matches.setPostRatingHost(Integer.valueOf(parts[7].trim()));
                    matches.setPreRatingGuest(Integer.valueOf(parts[8].trim()));
                    matches.setPostRatingGuest(Integer.valueOf(parts[9].trim()));
                    service.resultGame(matches);
                }
                break;
            case "P":
                if (parts.length == 2) {
                    String ID = parts[1].trim();
                    Player player = service.selectPlayer(ID);
                    System.out.println(player.getId() + ", " + player.getName() + ", " + player.getBirthdate()+", " + player.getRating() + ", " + player.getState());
                }
                break;
            case "A":
                if (parts.length == 2) {
                    String ID = parts[1].trim();
                    service.selectWinList(ID);
                }
                break;
            case "D":
                if (parts.length == 3) {
                    LocalDate startDate = LocalDate.parse(parts[1].trim(), DateTimeFormatter.ofPattern("yyyyMMdd"));
                    LocalDate endDate = LocalDate.parse(parts[2].trim(), DateTimeFormatter.ofPattern("yyyyMMdd"));
                    service.dateMatches(startDate, endDate);
                }
                break;
            case "M":
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

