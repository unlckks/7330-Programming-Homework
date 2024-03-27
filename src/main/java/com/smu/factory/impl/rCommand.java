package com.smu.factory.impl;

import com.smu.factory.Command;
import com.smu.service.Service;

import java.sql.SQLException;

public class rCommand implements Command {
    private Service service;

    public rCommand(Service service) {
        this.service = service;
    }

    @Override
    public void execute(String[] parts) throws SQLException {
        System.out.println("----------------------------------------------------------");
        service.clearAllData();
    }
}
