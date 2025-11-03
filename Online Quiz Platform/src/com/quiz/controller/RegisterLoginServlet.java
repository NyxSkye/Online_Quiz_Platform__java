package com.quiz.controller;

import com.quiz.dao.UserDAO;
import com.quiz.model.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * RegisterLoginServlet handles form submission from index.html,
 * registers the user, and redirects them based on their selected role.
 */
@WebServlet("/RegisterLoginServlet")
public class RegisterLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public void init() {
        this.userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Get parameters from the HTML form
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        // Simple validation
        if (name == null || email == null || password == null || role == null || role.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing form parameters.");
            return;
        }

        // 2. Create User object
        User newUser = new User(name, email, password, role);

        // 3. Register the user in the database
        boolean success = userDAO.registerUser(newUser);

        if (success) {
            System.out.println("User registered successfully: " + email + " with role: " + role);
            
            // 4. Role-based Redirection
            if ("Admin".equalsIgnoreCase(role)) {
                // Redirect to admin/admin_dashboard.html
                response.sendRedirect(request.getContextPath() + "/admin/admin_dashboard.html");
            } else if ("QuizCreator".equalsIgnoreCase(role)) {
                // Redirect to creator/creator_dashboard.html
                response.sendRedirect(request.getContextPath() + "/creator/creator_dashboard.html");
            } else if ("Student".equalsIgnoreCase(role)) {
                // Redirect to student/student_dashboard.html
                response.sendRedirect(request.getContextPath() + "/student/student_dashboard.html");
            } else {
                // Default fallback
                response.sendRedirect(request.getContextPath() + "/index.html?error=invalidrole");
            }
        } else {
            // Handle registration failure (e.g., email already exists)
            System.err.println("User registration failed for: " + email);
            response.sendRedirect(request.getContextPath() + "/index.html?error=registrationfailed");
        }
    }
}
