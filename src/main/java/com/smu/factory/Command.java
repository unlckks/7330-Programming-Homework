package com.smu.factory;

import java.sql.SQLException;

public interface Command {  void execute(String[] parts) throws SQLException;
}
