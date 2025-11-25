package controllers;

import utils.DBConnection;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.*;

@WebServlet("/submit-quiz")
public class SubmitQuizServlet extends javax.servlet.http.HttpServlet {
    @Override
    protected void doPost(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        int score = 0;

        try (Connection con = DBConnection.getConnection()) {

            PreparedStatement ps = con.prepareStatement(
                "SELECT id, correct_option FROM questions WHERE approved = TRUE"
            );
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String correct = rs.getString("correct_option");
                String userAnswer = req.getParameter("q" + id);

                if (userAnswer != null && correct.equalsIgnoreCase(userAnswer)) {
                    score++;
                }
            }

            PreparedStatement save = con.prepareStatement(
                "INSERT INTO scores(user_id, score) VALUES (?, ?)"
            );
            save.setInt(1, userId);
            save.setInt(2, score);
            save.executeUpdate();

            req.setAttribute("score", score);
            req.getRequestDispatcher("result.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("take-quiz?error=1");
        }
    }
}
