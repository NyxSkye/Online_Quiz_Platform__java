<%@ page import="java.util.*,javax.servlet.http.*" %>
<%@ page session="true" %>
<%
    HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute("userId") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    List<Map<String,Object>> qs = (List<Map<String,Object>>) request.getAttribute("questions");
    if (qs == null) qs = new ArrayList<>();
%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Take Quiz</title></head>
<body>
  <h2>Take Quiz</h2>
  <p>Welcome, <%= session.getAttribute("username") %> | <a href="logout">Logout</a></p>

  <form action="submit-quiz" method="post">
    <% for (Map q : qs) { %>
      <div style="margin-bottom:20px;">
        <p><strong>Q<%= q.get("id") %>:</strong> <%= q.get("question") %></p>
        <label><input type="radio" name="q<%= q.get("id") %>" value="A"> <%= q.get("A") %></label><br>
        <label><input type="radio" name="q<%= q.get("id") %>" value="B"> <%= q.get("B") %></label><br>
        <label><input type="radio" name="q<%= q.get("id") %>" value="C"> <%= q.get("C") %></label><br>
        <label><input type="radio" name="q<%= q.get("id") %>" value="D"> <%= q.get("D") %></label><br>
      </div>
    <% } %>

    <button type="submit">Submit Quiz</button>
  </form>
</body>
</html>
