package com.example.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/java_institue";
        String username = "root";
        String password = "admin";

        Connection con = DriverManager.getConnection(url, username, password);
        System.out.println("Connecting DataBase...");
        return con;
    }
}
