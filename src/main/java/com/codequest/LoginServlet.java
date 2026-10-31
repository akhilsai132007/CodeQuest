
package com.codequest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // Get login form values
        String usernameOrEmail =
                request.getParameter("usernameOrEmail");

        String password =
                request.getParameter("password");

        // Validate empty fields
        if (usernameOrEmail == null ||
            password == null ||
            usernameOrEmail.trim().isEmpty() ||
            password.isEmpty()) {

            request.setAttribute(
                    "error",
                    "Username and password are required.");

            request.getRequestDispatcher(
                    "login.jsp").forward(request, response);

            return;
        }

        usernameOrEmail = usernameOrEmail.trim();

        // SQL query to check username or email
        String sql =
                "SELECT id, username, email, password, " +
                "total_xp, player_level " +
                "FROM users " +
                "WHERE username = ? OR email = ?";

        try (
            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql)
        ) {

            ps.setString(1, usernameOrEmail);
            ps.setString(2, usernameOrEmail);

            try (ResultSet rs = ps.executeQuery()) {

                // Check whether student exists
                if (rs.next()) {

                    String storedPassword =
                            rs.getString("password");

                    // Compare passwords
                    // This matches the current registration code.
                    if (password.equals(storedPassword)) {

                        int studentId =
                                rs.getInt("id");

                        String username =
                                rs.getString("username");

                        // Prevent session fixation
                        HttpSession oldSession =
                                request.getSession(false);

                        if (oldSession != null) {
                            oldSession.invalidate();
                        }

                        // Create new session
                        HttpSession session =
                                request.getSession(true);

                        // Store student information
                        session.setAttribute(
                                "studentId",
                                studentId);

                        session.setAttribute(
                                "username",
                                username);

                        // Session timeout: 30 minutes
                        session.setMaxInactiveInterval(30 * 60);

                        // Redirect to student dashboard
                        response.sendRedirect(
                                request.getContextPath()
                                + "/dashboard");

                    } else {

                        showError(
                                request,
                                response,
                                "Invalid username or password.");
                    }

                } else {

                    showError(
                            request,
                            response,
                            "Invalid username or password.");
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();

            showError(
                    request,
                    response,
                    "Database error. Please try again later.");
        }
    }

    private void showError(
            HttpServletRequest request,
            HttpServletResponse response,
            String message)
            throws ServletException, IOException {

        request.setAttribute("error", message);

        request.getRequestDispatcher(
                "login.jsp").forward(request, response);
    }
}
