package controllers;

import utils.DBConnection;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.*;

@WebServlet("/add-question")
public class AddQuestionServlet extends javax.servlet.http.HttpServlet {
    @Override
    protected void doPost(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp)
        throws IOException, ServletException {

        HttpSession session = req.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("role"))) {
            resp.sendError(403);
            return;
        }

        String q = req.getParameter("question");
        String A = req.getParameter("optionA");
        String B = req.getParameter("optionB");
        String C = req.getParameter("optionC");
        String D = req.getParameter("optionD");
        String correct = req.getParameter("correct");

        if (q == null || correct == null) {
            resp.sendRedirect("add-question.jsp?error=1");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO questions(question, optionA, optionB, optionC, optionD, correct_option, approved) VALUES (?, ?, ?, ?, ?, ?, false)"
            );

            ps.setString(1, q);
            ps.setString(2, A);
            ps.setString(3, B);
            ps.setString(4, C);
            ps.setString(5, D);
            ps.setString(6, correct);

            ps.executeUpdate();

            resp.sendRedirect("admin-dashboard.jsp?added=1");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("admin-dashboard.jsp?error=1");
        }
    }
}
