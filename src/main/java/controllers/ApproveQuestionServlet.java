package controllers;

import utils.DBConnection;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/approve-question")
public class ApproveQuestionServlet extends javax.servlet.http.HttpServlet {
    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("role"))) {
            resp.sendError(403);
            return;
        }

        String idStr = req.getParameter("id");
        if (idStr == null) {
            resp.sendRedirect("approve-questions.jsp?error=1");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "UPDATE questions SET approved = TRUE WHERE id=?"
            );
            ps.setInt(1, Integer.parseInt(idStr));

            ps.executeUpdate();
            resp.sendRedirect("approve-questions.jsp?approved=1");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("approve-questions.jsp?error=2");
        }
    }
}
