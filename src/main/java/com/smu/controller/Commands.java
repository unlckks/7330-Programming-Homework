package com.smu.controller;

import com.smu.factory.Command;
import com.smu.factory.impl.Factory;
import com.smu.service.Service;
import java.sql.SQLException;

/**
 * @Author: MingYun
 * @Date: 2024-03-23 20:40
 */
public class Commands {
    public static Service service;


    public static void executeCommands(String line) throws SQLException {
        String[] parts = line.split(",");
        if (parts.length == 0) {
            return;
        }
        String commandStr  = parts[0];
        Command command = new Factory(service).getCommand(commandStr);
        if (command != null) {
            command.execute(parts);
        } else {
            System.out.println("input invalid");
        }
    }
}