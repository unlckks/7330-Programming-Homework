package com.smu.factory.impl;

import com.smu.domain.Player;
import com.smu.factory.Command;
import com.smu.service.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CommandForp implements Command {
    private Service service;

    public CommandForp(Service service) {
        this.service = service;
    }

    @Override
    public void execute(String[] parts) throws SQLException {
        System.out.println("----------------------------------------------------------");
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
}
