package com.smu.factory.impl;

import com.smu.factory.Command;
import com.smu.service.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DCommand implements Command {
    private Service service;

    public DCommand(Service service) {
        this.service = service;
    }

    @Override
    public void execute(String[] parts) throws SQLException {
            System.out.println("----------------------------------------------------------");
            LocalDate startDate = LocalDate.parse(parts[1].trim(), DateTimeFormatter.ofPattern("yyyyMMdd"));
            LocalDate endDate = LocalDate.parse(parts[2].trim(), DateTimeFormatter.ofPattern("yyyyMMdd"));
        service.dateMatches(startDate, endDate);
    }
}
