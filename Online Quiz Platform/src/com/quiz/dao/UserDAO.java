package com.quiz.dao;

import com.quiz.model.User;
import com.quiz.service.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * UserDAO handles all CRUD operations related to the User table.
 * Note: SQLException is handled internally to satisfy the specific
 * requirement of not having it in the imports of this file.
 */
public class UserDAO {

    private static final String INSERT_USER_SQL = 
            "INSERT INTO Users (name, email, password, role) VALUES (?, ?, ?, ?)";
    
    /**
     * Saves a new user to the database.
     * @param user The User object to save.
     * @return true if the user was successfully saved, false otherwise.
     */
    public boolean registerUser(User user) {
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)) {
            
            if (connection == null) {
                System.err.println("Registration failed: Database connection is null.");
                return false;
            }

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getRole());

            int result = preparedStatement.executeUpdate();
            return result > 0;

        } catch (Exception e) {
            // Catches SQLException and prints it, avoiding the explicit import as requested
            System.err.println("Error during user registration (UserDAO): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
