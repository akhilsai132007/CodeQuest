<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Leaderboard | CodeQuest</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

<%
    Integer studentId = (Integer) session.getAttribute("studentId");

    if (studentId == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    ArrayList<HashMap<String, Object>> leaderboard =
        (ArrayList<HashMap<String, Object>>)
        request.getAttribute("leaderboard");
%>

<nav class="navbar">
    <div class="logo">CodeQuest</div>

    <div>
        <a href="dashboard">Dashboard</a>
        <a href="missions">Missions</a>
        <a href="leaderboard">Leaderboard</a>
        <a href="logout">Logout</a>
    </div>
</nav>

<section class="hero">
    <h1>CodeQuest Leaderboard</h1>
    <p>See how you rank against other programmers!</p>
</section>

<div class="form-container">

    <h2>Top Players</h2>

    <% if (leaderboard == null || leaderboard.isEmpty()) { %>

        <p>No players are available on the leaderboard yet.</p>

    <% } else { %>

        <div style="overflow-x: auto;">

            <table>
                <thead>
                    <tr>
                        <th>Rank</th>
                        <th>Username</th>
                        <th>Total XP</th>
                        <th>Level</th>
                    </tr>
                </thead>

                <tbody>

                <% for (HashMap<String, Object> player : leaderboard) { %>

                    <tr>
                        <td><%= player.get("rank") %></td>

                        <td>
                            <%= player.get("username") %>
                        </td>

                        <td>
                            <%= player.get("totalXP") %>
                        </td>

                        <td>
                            <%= player.get("playerLevel") %>
                        </td>
                    </tr>

                <% } %>

                </tbody>
            </table>

        </div>

    <% } %>

</div>

<footer class="footer">
    <p>&copy; 2026 CodeQuest. All rights reserved.</p>
</footer>

</body>
</html>