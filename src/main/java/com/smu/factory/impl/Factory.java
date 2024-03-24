package com.smu.factory.impl;


import com.smu.factory.Command;
import com.smu.service.Service;

public class Factory {

    private Service service;

    public Factory(Service service) {
        this.service = service;
    }

    public Command getCommand(String command) {
        Service service = new Service();
        switch (command) {
            case "e":
                return new eCommand(service);
            case "r":
                return new rCommand(service);
            case "p":
                return new CommandForp(service);
            case "m":
                return new CommandForm(service);
            case "n":
                return new nCommand(service);
            case "c":
                return new cCommand(service);
            case "P":
                return new PCommand(service);
            case "A":
                return new ACommand(service);
            case "D":
                return new DCommand(service);
            case "M":
                return new MCommand(service);
            default:
               return  null;
        }
    }
}
