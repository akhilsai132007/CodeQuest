<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard | CodeQuest</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

<%
    Integer adminId = (Integer) session.getAttribute("adminId");
    String adminUsername =
        (String) session.getAttribute("adminUsername");

    if (adminId == null) {
        response.sendRedirect("admin-login.jsp");
        return;
    }
%>

<nav class="navbar">
    <div class="logo">CodeQuest Admin</div>

    <div>
        <a href="admin-dashboard">Dashboard</a>
        <a href="add-question.jsp">Add Question</a>
        <a href="view-questions">View Questions</a>
        <a href="admin-logout">Logout</a>
    </div>
</nav>

<section class="hero">
    <h1>Welcome, <%= adminUsername %>!</h1>
    <p>Manage programming missions and questions.</p>
</section>

<div class="form-container">

    <h2>Admin Control Panel</h2>

    <p>
        Create programming questions, manage existing missions,
        and maintain the CodeQuest learning experience.
    </p>

    <div class="dashboard-actions">

        <a href="add-question.jsp" class="btn btn-primary">
            Add New Question
        </a>

        <a href="view-questions" class="btn btn-primary">
            View Questions
        </a>

    </div>

</div>

<footer class="footer">
    <p>&copy; 2026 CodeQuest. All rights reserved.</p>
</footer>

</body>
</html>