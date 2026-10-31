<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Mission Result | CodeQuest</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

<%
    Integer studentId = (Integer) session.getAttribute("studentId");

    if (studentId == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    Boolean isCorrect = (Boolean) request.getAttribute("isCorrect");
    String message = (String) request.getAttribute("message");
    Integer xpReward = (Integer) request.getAttribute("xpReward");
%>

<nav class="navbar">
    <div class="logo">CodeQuest</div>

    <div>
        <a href="dashboard">Dashboard</a>
        <a href="missions">Missions</a>
        <a href="leaderboard.jsp">Leaderboard</a>
        <a href="logout">Logout</a>
    </div>
</nav>

<section class="hero">

    <% if (Boolean.TRUE.equals(isCorrect)) { %>

        <h1>Mission Completed!</h1>
        <p>Great job! You solved the programming challenge.</p>

    <% } else { %>

        <h1>Mission Result</h1>
        <p>Keep practicing and improving your skills.</p>

    <% } %>

</section>

<div class="form-container">

    <% if (message != null) { %>

        <h2><%= message %></h2>

    <% } else { %>

        <h2>Your result is ready.</h2>

    <% } %>

    <% if (Boolean.TRUE.equals(isCorrect) && xpReward != null) { %>

        <p>
            XP Earned:
            <strong><%= xpReward %> XP</strong>
        </p>

    <% } %>

    <div class="dashboard-actions">

        <a href="missions" class="btn btn-primary">
            Back to Missions
        </a>

        <a href="dashboard" class="btn btn-primary">
            View Dashboard
        </a>

    </div>

</div>

<footer class="footer">
    <p>&copy; 2026 CodeQuest. All rights reserved.</p>
</footer>

</body>
</html>