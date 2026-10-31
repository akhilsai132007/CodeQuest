<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Programming Missions | CodeQuest</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

<%
    Integer studentId = (Integer) session.getAttribute("studentId");

    if (studentId == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    ArrayList<HashMap<String, Object>> missions =
        (ArrayList<HashMap<String, Object>>)
        request.getAttribute("missions");
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
    <h1>Programming Missions</h1>
    <p>Choose a mission, solve the question, and earn XP!</p>
</section>

<div class="form-container">

    <h2>Available Missions</h2>

    <% if (missions == null || missions.isEmpty()) { %>

        <p>No missions are available yet. Check back soon!</p>

    <% } else { %>

        <p>Total Missions: <%= missions.size() %></p>

        <% for (HashMap<String, Object> mission : missions) { %>

            <div class="mission-card">

                <h3>
                    <%= mission.get("title") %>
                </h3>

                <p>
                    <strong>Category:</strong>
                    <%= mission.get("category") %>
                </p>

                <p>
                    <strong>Difficulty:</strong>
                    <%= mission.get("difficulty") %>
                </p>

                <p>
                    <strong>Reward:</strong>
                    <%= mission.get("xpReward") %> XP
                </p>

                <p>
                    <%= mission.get("description") %>
                </p>

                <form action="submit-answer" method="post">

                    <input type="hidden"
                           name="missionId"
                           value="<%= mission.get("id") %>">

                    <div class="form-group">

                        <label>
                            <input type="radio"
                                   name="answer"
                                   value="A"
                                   required>

                            A. <%= mission.get("optionA") %>
                        </label>

                    </div>

                    <div class="form-group">

                        <label>
                            <input type="radio"
                                   name="answer"
                                   value="B"
                                   required>

                            B. <%= mission.get("optionB") %>
                        </label>

                    </div>

                    <div class="form-group">

                        <label>
                            <input type="radio"
                                   name="answer"
                                   value="C"
                                   required>

                            C. <%= mission.get("optionC") %>
                        </label>

                    </div>

                    <div class="form-group">

                        <label>
                            <input type="radio"
                                   name="answer"
                                   value="D"
                                   required>

                            D. <%= mission.get("optionD") %>
                        </label>

                    </div>

                    <button type="submit" class="btn btn-primary">
                        Submit Answer
                    </button>

                </form>

            </div>

            <hr>

        <% } %>

    <% } %>

</div>

<footer class="footer">
    <p>&copy; 2026 CodeQuest. All rights reserved.</p>
</footer>

</body>
</html>