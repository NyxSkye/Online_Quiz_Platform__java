package com.quiz.controller;

import com.quiz.dao.QuizDAO;
import com.quiz.model.Quiz;
import com.quiz.model.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CreateQuiz")
public class QuizCreationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private QuizDAO quizDAO = new QuizDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Authenticate and get user
        User creator = (User) request.getSession().getAttribute("currentUser");
        if (creator == null || !"Quiz Creator".equals(creator.getRoleName())) {
            response.sendRedirect("index.html"); 
            return;
        }

        // 2. Get form data
        String title = request.getParameter("title");
        int duration = Integer.parseInt(request.getParameter("duration"));
        // Assuming questions/answers are submitted via separate forms/steps after this primary save

        // 3. Create Quiz object and save
        Quiz newQuiz = new Quiz(0, title, duration, "Pending");
        boolean success = quizDAO.createQuiz(newQuiz, creator.getUserId());

        if (success) {
            // Redirect to the Question/Answer creation form for the newly created quiz
            response.sendRedirect("creatorQuizForm.jsp?quizId=" + newQuiz.getQuizId());
        } else {
            request.setAttribute("errorMessage", "Failed to create quiz.");
            request.getRequestDispatcher("creatorDashboard.jsp").forward(request, response);
        }
    }
}