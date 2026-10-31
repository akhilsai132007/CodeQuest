<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>

<%
    // Check admin session
    if (session == null || session.getAttribute("adminId") == null) {
        response.sendRedirect("admin-login.jsp");
        return;
    }

    // Get questions from ViewQuestionsServlet
    ArrayList<HashMap<String, Object>> questions =
        (ArrayList<HashMap<String, Object>>) request.getAttribute("questions");

    // Avoid null pointer exception
    if (questions == null) {
        questions = new ArrayList<>();
    }

    String error = request.getParameter("error");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <title>View Questions | CodeQuest</title>

    <link rel="stylesheet" href="style.css">

    <style>
        .questions-container {
            width: 90%;
            max-width: 1200px;
            margin: 40px auto;
        }

        .questions-container h1 {
            text-align: center;
            margin-bottom: 25px;
        }

        .table-wrapper {
            overflow-x: auto;
        }

        .questions-table {
            width: 100%;
            border-collapse: collapse;
            background: white;
            border-radius: 10px;
            overflow: hidden;
        }

        .questions-table th,
        .questions-table td {
            padding: 14px;
            text-align: center;
            border: 1px solid #ddd;
        }

        .questions-table th {
            background: #222;
            color: white;
        }

        .questions-table tr:nth-child(even) {
            background: #f5f5f5;
        }

        .questions-table td.title-cell {
            text-align: left;
            min-width: 200px;
        }

        .action-buttons {
            display: flex;
            gap: 8px;
            justify-content: center;
        }

        .delete-btn {
            background: #e53935;
            color: white;
            border: none;
            padding: 8px 14px;
            border-radius: 5px;
            cursor: pointer;
        }

        .delete-btn:hover {
            background: #c62828;
        }

        .message {
            text-align: center;
            color: #c62828;
            margin-bottom: 15px;
        }

        .empty-message {
            text-align: center;
            padding: 25px;
        }

        .back-link {
            display: inline-block;
            margin-bottom: 20px;
            text-decoration: none;
        }
    </style>
</head>

<body>

    <nav class="navbar">
        <div class="logo">CodeQuest</div>

        <div>
            <a href="admin-dashboard.jsp">Dashboard</a>
            <a href="add-question.jsp">Add Question</a>
            <a href="view-questions">View Questions</a>
            <a href="admin-logout">Logout</a>
        </div>
    </nav>

    <div class="questions-container">

        <h1>Manage Questions</h1>

        <a href="admin-dashboard.jsp" class="back-link">
            ← Back to Admin Dashboard
        </a>

        <% if ("submissions".equals(error)) { %>

            <p class="message">
                This question cannot be deleted because students have
                already attempted it.
            </p>

        <% } else if ("deleteFailed".equals(error)) { %>

            <p class="message">
                Unable to delete the question. Please try again.
            </p>

        <% } %>

        <div class="table-wrapper">

            <table class="questions-table">

                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Title</th>
                        <th>Category</th>
                        <th>Difficulty</th>
                        <th>XP</th>
                        <th>Correct Answer</th>
                        <th>Action</th>
                    </tr>
                </thead>

                <tbody>

                    <% if (questions.isEmpty()) { %>

                        <tr>
                            <td colspan="7" class="empty-message">
                                No questions found.
                            </td>
                        </tr>

                    <% } else { %>

                        <% for (HashMap<String, Object> question : questions) { %>

                            <tr>

                                <td>
                                    <%= question.get("id") %>
                                </td>

                                <td class="title-cell">
                                    <%= question.get("title") %>
                                </td>

                                <td>
                                    <%= question.get("category") %>
                                </td>

                                <td>
                                    <%= question.get("difficulty") %>
                                </td>

                                <td>
                                    <%= question.get("xpReward") %>
                                </td>

                                <td>
                                    <%= question.get("correctAnswer") %>
                                </td>

                                <td>

                                    <div class="action-buttons">

                                        <form action="delete-question"
                                              method="post"
                                              onsubmit="return confirm('Are you sure you want to delete this question?');">

                                            <input type="hidden"
                                                   name="id"
                                                   value="<%= question.get("id") %>">

                                            <button type="submit"
                                                    class="delete-btn">
                                                Delete
                                            </button>

                                        </form>

                                    </div>

                                </td>

                            </tr>

                        <% } %>

                    <% } %>

                </tbody>

            </table>

        </div>

    </div>

</body>
</html>