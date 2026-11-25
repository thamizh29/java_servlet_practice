package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.example.config.DBconnection;

public class TestDataBase {

        public static List<Map<String, Object>> getUsers() throws SQLException {
        List<Map<String, Object>> userList = new ArrayList<>();
        String sql = "SELECT id, name, email, mobile FROM student";

        try (Connection con = DBconnection.getConnection()) {
            if (con == null) {
                throw new SQLException("Database connection failed");
            }

            try (PreparedStatement ps = con.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> user = new HashMap<>();
                    user.put("id", rs.getInt("id"));
                    user.put("name", rs.getString("name"));
                    user.put("email", rs.getString("email"));
                    user.put("mobile", rs.getString("mobile"));
                    userList.add(user);
                }
            }
        }

        System.out.println(userList);

        return userList;
    }

       public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("You want to Connect DataBase");
        System.out.println("1. Yes");
        System.out.println("2. No");
        int value = input.nextInt();
        if (value == 1) {
            try (Connection con = DBconnection.getConnection()) {
                System.out.println("Data Base Connected Successfully");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("DB Connection Faile" + " " + e);
            }
        }else{
            System.out.println("DataBase connection process canceld");
        }

        try {
            getUsers();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to retrieve users: " + e);
        }

    }
}
