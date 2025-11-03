package com.quiz.service;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
    
    // Use the database name we created
    private static final String URL = "jdbc:mysql://localhost:3306/Online_Quiz_Platform?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "@Qtot0to7#";

    /**
     * Establishes and returns a connection to the Online_Quiz_Platform database.
     * Uses a general try-catch block as requested.
     * @return A valid Connection object, or null on failure.
     */
    public static Connection getConnection() {
        try {
            // Load the MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish the connection
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            
            return connection;
            
        } catch (Exception e) {
            // Catches ClassNotFoundException, SQLException, and any other exceptions
            System.err.println("Database connection failed!");
            e.printStackTrace();
            return null;
        }
    }
    
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                System.err.println("Error closing connection.");
                e.printStackTrace();
            }
        }
    }
}