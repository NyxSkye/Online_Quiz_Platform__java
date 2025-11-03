package com.quiz.dao;

import com.quiz.model.Quiz;
import com.quiz.service.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO {

    /**
     * Creates a new quiz record in the Quizzes table.
     */
    public boolean createQuiz(Quiz quiz, int creatorId) {
        String sql = "INSERT INTO Quizzes (CreatorID, Title, Duration, ApprovalStatus) VALUES (?, ?, ?, ?)";
        Connection conn = DBUtil.getConnection();
        if (conn == null) return false;

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, creatorId);
            pstmt.setString(2, quiz.getTitle());
            pstmt.setInt(3, quiz.getDuration());
            pstmt.setString(4, "Pending"); // Default status

            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        quiz.setQuizId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }

        } catch (Exception e) {
            System.err.println("Error creating quiz.");
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn);
        }
        return false;
    }
    
    /**
     * Retrieves all quizzes for the Admin Dashboard.
     */
    public List<Quiz> getAllQuizzes() {
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT QuizID, Title, Duration, ApprovalStatus FROM Quizzes";
        Connection conn = DBUtil.getConnection();
        if (conn == null) return quizzes;
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                quizzes.add(new Quiz(
                    rs.getInt("QuizID"),
                    rs.getString("Title"),
                    rs.getInt("Duration"),
                    rs.getString("ApprovalStatus")
                ));
            }
        } catch (Exception e) {
            System.err.println("Error fetching all quizzes.");
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn);
        }
        return quizzes;
    }
    
    // Add methods for approveQuiz(quizId), rejectQuiz(quizId), and getAvailableQuizzesForParticipant()
}