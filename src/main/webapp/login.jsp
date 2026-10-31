
<%@ page contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Student Login - CodeQuest</title>

    <link rel="stylesheet" href="style.css">

</head>

<body>

    <!-- Navigation Bar -->

    <header class="navbar">

        <h1 class="logo">CodeQuest</h1>

        <nav>
            <a href="index.html">Home</a>

            <a href="register.jsp">Register</a>

            <a href="admin-login.jsp">Admin Login</a>
        </nav>

    </header>


    <!-- Login Section -->

    <main class="hero">

        <div class="form-container">

            <h2>Welcome Back!</h2>

            <p>
                Log in and continue your coding journey.
            </p>


            <!-- Registration Success Message -->

            <%
                String registered =
                        request.getParameter("registered");

                if ("true".equals(registered)) {
            %>

                <p style="color: #4ade80;">
                    Registration successful!
                    Please log in.
                </p>

            <%
                }
            %>


            <!-- Login Error Message -->

            <%
                String error =
                        (String) request.getAttribute("error");

                if (error != null) {
            %>

                <p style="color: #f87171;">
                    <%= error %>
                </p>

            <%
                }
            %>


            <!-- Login Form -->

            <form action="login" method="post">

                <!-- Username or Email -->

                <div class="form-group">

                    <label for="usernameOrEmail">
                        Username or Email
                    </label>

                    <input
                        type="text"
                        id="usernameOrEmail"
                        name="usernameOrEmail"
                        placeholder="Enter username or email"
                        maxlength="100"
                        required>

                </div>


                <!-- Password -->

                <div class="form-group">

                    <label for="password">
                        Password
                    </label>

                    <input
                        type="password"
                        id="password"
                        name="password"
                        placeholder="Enter your password"
                        required>

                </div>


                <!-- Login Button -->

                <button
                    type="submit"
                    class="btn btn-primary">

                    Login

                </button>

            </form>


            <!-- Registration Link -->

            <p class="form-link">

                Don't have an account?

                <a href="register.jsp">
                    Register here
                </a>

            </p>

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
