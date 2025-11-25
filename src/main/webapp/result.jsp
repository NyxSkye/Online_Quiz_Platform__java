<%@ page session="true" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Result</title></head>
<body>
  <h2>Result</h2>
  <p>Your score: <strong><%= request.getAttribute("score") %></strong></p>
  <p><a href="take-quiz">Take Again</a> | <a href="logout">Logout</a></p>
</body>
</html>
