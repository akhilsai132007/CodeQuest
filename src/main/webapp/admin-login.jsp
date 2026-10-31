<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Login | CodeQuest</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

<nav class="navbar">
    <div class="logo">CodeQuest</div>
    <div>
        <a href="index.html">Home</a>
    </div>
</nav>

<section class="hero">
    <h1>Admin Login</h1>
    <p>Access the CodeQuest administration panel.</p>
</section>

<div class="form-container">

    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
        <p class="error-message"><%= error %></p>
    <%
        }
    %>

    <form action="admin-login" method="post">

        <div class="form-group">
            <label for="username">Admin Username</label>
            <input type="text"
                   id="username"
                   name="username"
                   placeholder="Enter admin username"
                   required>
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password"
                   id="password"
                   name="password"
                   placeholder="Enter admin password"
                   required>
        </div>

        <button type="submit" class="btn btn-primary">
            Login as Admin
        </button>

    </form>

    <p class="form-link">
        <a href="login.jsp">Student Login</a>
    </p>

</div>

<footer class="footer">
    <p>&copy; 2026 CodeQuest. All rights reserved.</p>
</footer>

</body>
</html>