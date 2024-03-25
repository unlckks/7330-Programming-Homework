package com.smu.factory.impl;

import com.smu.domain.Matches;
import com.smu.factory.Command;
import com.smu.service.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class cCommand implements Command {
    private Service service;

    public cCommand(Service service) {
        this.service = service;
    }

    @Override
    public void execute(String[] parts) throws SQLException {
        System.out.println("----------------------------------------------------------");
        Integer hostId = Integer.valueOf(parts[1].trim());
        Integer gustId = Integer.valueOf(parts[2].trim());
        Boolean  exists = service.gameExists(hostId , gustId);
        if(exists){
            Matches matches = new Matches();
            matches.setHostID(hostId);
            matches.setGuestID(gustId);
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
            System.out.println("Reject Input");
        }
    }
}
