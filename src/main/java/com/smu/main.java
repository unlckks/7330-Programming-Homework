package com.smu;

import com.smu.controller.Commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;
import static com.smu.controller.Commands.executeCommands;

/**
 * @Author: MingYun
 * @Date: 2024-03-23 19:51
 */
public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the CSV file:");
        String fileName = scanner.nextLine();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                executeCommands(line);

            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
