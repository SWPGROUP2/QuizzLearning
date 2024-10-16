<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
</head>
<body>
    <h1>An Error Occurred</h1>
    <p><strong>Error Message:</strong> <%= request.getParameter("message") %></p>
    <a href="question?id=<%= request.getParameter("id") %>">Go back to question list</a>
</body>
</html>
