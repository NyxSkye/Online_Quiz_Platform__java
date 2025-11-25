<%@ page import="javax.servlet.http.*,java.sql.*" %>
<%@ page session="true" %>
<%
    HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute("role") == null || !"admin".equals(session.getAttribute("role"))) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Admin Dashboard</title></head>
<body>
  <h2>Admin Dashboard</h2>
  <p>Welcome, <%= session.getAttribute("username") %> | <a href="logout">Logout</a></p>

  <h3>Add Question</h3>
  <form action="add-question" method="post">
    <textarea name="question" placeholder="Question" required rows="3" cols="60"></textarea><br>
    A: <input name="optionA" required><br>
    B: <input name="optionB" required><br>
    C: <input name="optionC" required><br>
    D: <input name="optionD" required><br>
    Correct (A/B/C/D): <input name="correct" required pattern="[ABCDabcd]"><br>
    <button type="submit">Add</button>
  </form>

  <p><a href="approve-questions.jsp">Approve Pending Questions</a></p>
</body>
</html>
