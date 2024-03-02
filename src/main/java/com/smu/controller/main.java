package com.smu.controller;


import com.smu.domain.Matches;
import com.smu.domain.Player;
import com.smu.service.Service;
import lombok.val;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * @Author: MingYun
 * @Date: 2024-02-28 16:46
 */
public class main  {

    public static Service service  = new Service();


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
            switch (command){
                case "e":
                    service.checkAndCreateTable() ;
                    break ;
                case "r":
                    service.clearAllData();
                    break ;
                case "p":
                    if(parts.length > 4 ){
                        Player player = new Player();
                        player.setId(parts[1].trim());
                        player.setName(parts[2].trim());
                        player.setBirthdate(Date.valueOf(parts[3].trim()).toLocalDate());
                        player.setState(parts[4].trim());
                        service.addPlayer(player);
                        System.out.println("insert success!");
                        break;
                    }
                case "m" :
                    if(parts.length == 9 ){
                        Matches matches = new Matches();
                        matches.setHostID(parts[1].trim());
                        matches.setGuestID(parts[2].trim());
                        matches.setStart(LocalDateTime.parse(parts[3].trim()));
                        matches.setEnd(LocalDateTime.parse(parts[4].trim()));
                        matches.setHostWin(Integer.parseInt(parts[5].trim())));
                        matches.setPreRatingHost(Integer.valueOf(parts[6].trim()));
                        matches.setPostRatingHost(Integer.valueOf(parts[7].trim()));
                        matches.setPreRatingGuest(Integer.valueOf(parts[8].trim()));
                        matches.setPostRatingGuest(Integer.valueOf(parts[9].trim()));
                        service.addMatches(matches);
                    }

                default: System.out.println("please input correct command");
            }
        }


    }

