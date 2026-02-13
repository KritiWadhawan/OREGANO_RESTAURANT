package com.oregano.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // TODO: Update these constants with your DB credentials
    private static final String URL = "jdbc:mysql://localhost:3306/oregano_db?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "rootpassword";

    static {
        try {
            // ensure driver is loaded
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC driver not found.");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
