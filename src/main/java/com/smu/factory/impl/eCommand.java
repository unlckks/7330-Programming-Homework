package com.smu.factory.impl;

import com.smu.factory.Command;
import com.smu.service.Service;

import java.sql.SQLException;

public class eCommand implements Command {
    private Service service;

    public eCommand(Service service) {
        this.service = service;
    }

    @Override
    public void execute(String[] parts) throws SQLException {
        System.out.println("----------------------------------------------------------");
        service.checkAndCreateTable();
    }
}
