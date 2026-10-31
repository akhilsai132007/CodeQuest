
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Registration | CodeQuest</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

<nav class="navbar">
    <a href="index.html" class="logo">Code<span>Quest</span></a>

    <div class="nav-links">
        <a href="index.html">Home</a>
        <a href="login.jsp">Student Login</a>
        <a href="admin-login.jsp">Admin Login</a>
    </div>
</nav>

<main class="auth-page">
    <section class="auth-card">

        <div class="auth-icon">⚡</div>

        <p class="tagline">YOUR CODING ADVENTURE STARTS HERE</p>
        <h1>Create Your Account</h1>
        <p class="auth-description">
            Join CodeQuest, solve missions, earn XP, and level up your coding skills.
        </p>

        <% if (request.getAttribute("error") != null) { %>
            <div class="message error">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>

        <form action="register" method="post" class="auth-form">

            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" id="username" name="username"
                       placeholder="Choose a username" required>
            </div>

            <div class="form-group">
                <label for="email">Email Address</label>
                <input type="email" id="email" name="email"
                       placeholder="Enter your email" required>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password"
                       placeholder="Create a password" required>
            </div>

            <div class="form-group">
                <label for="confirmPassword">Confirm Password</label>
                <input type="password" id="confirmPassword"
                       name="confirmPassword"
                       placeholder="Confirm your password" required>
            </div>

            <button type="submit" class="btn btn-primary auth-submit">
                Create Account <span>→</span>
            </button>
        </form>

        <p class="auth-switch">
            Already have an account?
            <a href="login.jsp">Login here</a>
        </p>

        <div class="auth-benefits">
            <span>🎯 Programming Missions</span>
            <span>⚡ Earn XP</span>
            <span>🏆 Leaderboard</span>
        </div>

    </section>
</main>

<footer class="footer">
    <a href="index.html" class="logo">Code<span>Quest</span></a>
    <p>© 2026 CodeQuest. Learn. Play. Level Up.</p>
</footer>

</body>
</html>
