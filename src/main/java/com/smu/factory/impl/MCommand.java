package com.smu.factory.impl;

import com.smu.factory.Command;
import com.smu.service.Service;

import java.sql.SQLException;

public class MCommand implements Command {
    private Service service;

    public MCommand(Service service) {
        this.service = service;
    }

    @Override
    public void execute(String[] parts) throws SQLException {
        System.out.println("----------------------------------------------------------");
        String ID = parts[1].trim();
        service.ListMatches(ID);
    }
}
