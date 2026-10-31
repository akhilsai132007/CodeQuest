<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Question | CodeQuest</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

<%
    Integer adminId = (Integer) session.getAttribute("adminId");

    if (adminId == null) {
        response.sendRedirect("admin-login.jsp");
        return;
    }

    String error = (String) request.getAttribute("error");
    String success = (String) request.getAttribute("success");
%>

<nav class="navbar">
    <div class="logo">CodeQuest Admin</div>

    <div>
        <a href="admin-dashboard">Dashboard</a>
        <a href="view-questions">View Questions</a>
        <a href="admin-logout">Logout</a>
    </div>
</nav>

<section class="hero">
    <h1>Add a Programming Mission</h1>
    <p>Create a new question for students to solve.</p>
</section>

<div class="form-container">

    <% if (error != null) { %>
        <p class="error-message"><%= error %></p>
    <% } %>

    <% if (success != null) { %>
        <p class="success-message"><%= success %></p>
    <% } %>

    <form action="add-question" method="post">

        <div class="form-group">
            <label for="title">Question Title</label>
            <input type="text"
                   id="title"
                   name="title"
                   placeholder="Example: Java Variables"
                   required>
        </div>

        <div class="form-group">
            <label for="description">Question Description</label>
            <textarea id="description"
                      name="description"
                      rows="4"
                      placeholder="Write the programming question..."
                      required></textarea>
        </div>

        <div class="form-group">
            <label for="category">Category</label>
            <select id="category" name="category" required>
                <option value="">Select Category</option>
                <option value="Java">Java</option>
                <option value="Python">Python</option>
                <option value="C">C</option>
                <option value="C++">C++</option>
                <option value="SQL">SQL</option>
                <option value="Web Development">Web Development</option>
            </select>
        </div>

        <div class="form-group">
            <label for="difficulty">Difficulty</label>
            <select id="difficulty" name="difficulty" required>
                <option value="">Select Difficulty</option>
                <option value="Easy">Easy</option>
                <option value="Medium">Medium</option>
                <option value="Hard">Hard</option>
            </select>
        </div>

        <div class="form-group">
            <label for="optionA">Option A</label>
            <input type="text"
                   id="optionA"
                   name="optionA"
                   required>
        </div>

        <div class="form-group">
            <label for="optionB">Option B</label>
            <input type="text"
                   id="optionB"
                   name="optionB"
                   required>
        </div>

        <div class="form-group">
            <label for="optionC">Option C</label>
            <input type="text"
                   id="optionC"
                   name="optionC"
                   required>
        </div>

        <div class="form-group">
            <label for="optionD">Option D</label>
            <input type="text"
                   id="optionD"
                   name="optionD"
                   required>
        </div>

        <div class="form-group">
            <label for="correctAnswer">Correct Answer</label>
            <select id="correctAnswer"
                    name="correctAnswer"
                    required>
                <option value="">Select Correct Option</option>
                <option value="A">Option A</option>
                <option value="B">Option B</option>
                <option value="C">Option C</option>
                <option value="D">Option D</option>
            </select>
        </div>

        <div class="form-group">
            <label for="xpReward">XP Reward</label>
            <input type="number"
                   id="xpReward"
                   name="xpReward"
                   min="1"
                   max="1000"
                   value="50"
                   required>
        </div>

        <button type="submit" class="btn btn-primary">
            Add Mission
        </button>

    </form>

</div>

<footer class="footer">
    <p>&copy; 2026 CodeQuest. All rights reserved.</p>
</footer>

</body>
</html>