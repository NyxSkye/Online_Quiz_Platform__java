<%@ page import="java.sql.*,utils.DBConnection" %>
<%@ page session="true" %>
<%
    HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute("role") == null || !"admin".equals(session.getAttribute("role"))) {
        response.sendRedirect("login.jsp");
        return;
    }

    java.util.List<java.util.Map<String,Object>> list = new java.util.ArrayList<>();
    try (java.sql.Connection con = utils.DBConnection.getConnection()) {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM questions WHERE approved = FALSE");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            java.util.Map<String,Object> m = new java.util.HashMap<>();
            m.put("id", rs.getInt("id"));
            m.put("question", rs.getString("question"));
            list.add(m);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Approve Questions</title></head>
<body>
  <h2>Approve Questions</h2>
  <p><a href="admin-dashboard.jsp">Back</a></p>
  <table border="1" cellpadding="6">
    <tr><th>ID</th><th>Question</th><th>Action</th></tr>
    <% for (java.util.Map<String,Object> m : list) { %>
      <tr>
        <td><%= m.get("id") %></td>
        <td><%= m.get("question") %></td>
        <td><a href="approve-question?id=<%= m.get("id") %>">Approve</a></td>
      </tr>
    <% } %>
  </table>
</body>
</html>
