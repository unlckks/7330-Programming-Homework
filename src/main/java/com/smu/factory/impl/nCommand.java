package com.smu.factory.impl;

import com.smu.domain.Matches;
import com.smu.factory.Command;
import com.smu.service.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class nCommand implements Command {
    private Service service;

    public nCommand(Service service) {
        this.service = service;
    }

    @Override
    public void execute(String[] parts) throws SQLException {
        System.out.println("----------------------------------------------------------");
        Matches matches = new Matches();
        Integer HostId = Integer.valueOf(parts[1].trim());
        Integer GuestID = Integer.valueOf(parts[2].trim());
        matches.setHostID(HostId);
        matches.setGuestID(GuestID);
        matches.setStart(LocalDateTime.parse(parts[3].trim(), DateTimeFormatter.ofPattern("yyyyMMdd:HH:mm:ss")));
        service.addMatchesInformation(matches);
    }
}
