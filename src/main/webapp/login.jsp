<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Login - Quiz App</title></head>
<body>
  <h2>Login</h2>
  <form action="login" method="post">
    <label>Username: <input name="username" required></label><br>
    <label>Password: <input type="password" name="password" required></label><br>
    <button type="submit">Login</button>
  </form>
  <p style="color:red;">
    <% if (request.getParameter("error") != null) { %>
      <% if ("1".equals(request.getParameter("error"))) { %>
        Invalid credentials.
      <% } else { %>
        Something went wrong.
      <% } %>
    <% } %>
  </p>
</body>
</html>
