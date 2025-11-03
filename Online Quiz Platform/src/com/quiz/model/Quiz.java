package com.quiz.model;

// Basic Model for the Quizzes table
public class Quiz {
    private int quizId;
    private String title;
    private int duration;
    private String approvalStatus;
    
    // Constructor
    public Quiz(int quizId, String title, int duration, String approvalStatus) {
        this.quizId = quizId;
        this.title = title;
        this.duration = duration;
        this.approvalStatus = approvalStatus;
    }

    // Getters
    public int getQuizId() {
        return quizId;
    }
    
    public String getTitle() {
        return title;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public int getDuration() {
        return duration;
    }

    // (Other getters/setters for duration, dates, etc., should be added here)
}