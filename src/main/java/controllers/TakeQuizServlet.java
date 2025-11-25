package controllers;

import utils.DBConnection;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/take-quiz")
public class TakeQuizServlet extends javax.servlet.http.HttpServlet {
    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM questions WHERE approved = TRUE"
            );

            ResultSet rs = ps.executeQuery();

            List<Map<String, Object>> questions = new ArrayList<>();

            while (rs.next()) {
                Map<String, Object> q = new HashMap<>();
                q.put("id", rs.getInt("id"));
                q.put("question", rs.getString("question"));
                q.put("A", rs.getString("optionA"));
                q.put("B", rs.getString("optionB"));
                q.put("C", rs.getString("optionC"));
                q.put("D", rs.getString("optionD"));
                questions.add(q);
            }

            req.setAttribute("questions", questions);
            req.getRequestDispatcher("take-quiz.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("login.jsp?error=1");
        }
    }
}
