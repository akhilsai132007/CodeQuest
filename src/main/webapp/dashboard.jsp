
<%@ page contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Student Dashboard - CodeQuest</title>

    <link rel="stylesheet" href="style.css">

</head>

<body>

    <!-- Navigation Bar -->

    <header class="navbar">

        <h1 class="logo">CodeQuest</h1>

        <nav>

            <a href="index.html">Home</a>

            <a href="dashboard">Dashboard</a>

            <a href="missions">Missions</a>

            <a href="leaderboard.jsp">Leaderboard</a>

            <a href="logout">Logout</a>

        </nav>

    </header>


    <!-- Dashboard -->

    <main class="hero">

        <div class="form-container">

            <%
                Integer studentId =
                        (Integer) session.getAttribute("studentId");

                if (studentId == null) {

                    response.sendRedirect("login.jsp");

                    return;
                }

                String username =
                        (String) request.getAttribute("username");

                Integer totalXP =
                        (Integer) request.getAttribute("totalXP");

                Integer playerLevel =
                        (Integer) request.getAttribute("playerLevel");

                Integer completedMissions =
                        (Integer) request.getAttribute(
                                "completedMissions");

                Integer currentLevelXP =
                        (Integer) request.getAttribute(
                                "currentLevelXP");

                Integer nextLevelXP =
                        (Integer) request.getAttribute(
                                "nextLevelXP");

                Integer progressPercentage =
                        (Integer) request.getAttribute(
                                "progressPercentage");

                String error =
                        (String) request.getAttribute("error");
            %>


            <% if (error != null) { %>

                <p style="color: #f87171;">
                    <%= error %>
                </p>

            <% } else if (username != null) { %>


                <!-- Welcome Message -->

                <h2>
                    Welcome Back,
                    <%= username %>!
                </h2>

                <p>
                    Ready to continue your coding adventure?
                </p>


                <!-- Student Statistics -->

                <div class="dashboard-stats">

                    <div class="stat-card">

                        <h3>Total XP</h3>

                        <p>
                            <%= totalXP %>
                        </p>

                    </div>


                    <div class="stat-card">

                        <h3>Current Level</h3>

                        <p>
                            <%= playerLevel %>
                        </p>

                    </div>


                    <div class="stat-card">

                        <h3>Completed Missions</h3>

                        <p>
                            <%= completedMissions %>
                        </p>

                    </div>

                </div>


                <!-- Level Progress -->

                <div class="progress-section">

                    <h3>Level Progress</h3>

                    <p>

                        <%= currentLevelXP %> /
                        <%= nextLevelXP %> XP

                    </p>

                    <div class="progress-bar">

                        <div
                            class="progress-fill"
                            style="width: <%= progressPercentage %>%;">
                        </div>

                    </div>

                    <p>

                        <%= progressPercentage %>% completed
                        toward the next level!

                    </p>

                </div>


                <!-- Dashboard Actions -->

                <div class="dashboard-actions">

                    <a href="missions"
                       class="btn btn-primary">

                        Start Missions

                    </a>

                    <a href="leaderboard.jsp"
                       class="btn btn-primary">

                        View Leaderboard

                    </a>

                </div>


            <% } %>

        </div>

    </main>


    <!-- Footer -->

    <footer class="footer">

        <p>
            &copy; 2026 CodeQuest |
            Learn. Code. Level Up.
        </p>

    </footer>

</body>

</html>
