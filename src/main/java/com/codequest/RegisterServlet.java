
package com.codequest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String username =
                request.getParameter("username");

        String email =
                request.getParameter("email");

        String password =
                request.getParameter("password");

        String confirmPassword =
                request.getParameter("confirmPassword");

        // Validate empty fields
        if (username == null ||
            email == null ||
            password == null ||
            confirmPassword == null ||
            username.trim().isEmpty() ||
            email.trim().isEmpty() ||
            password.isEmpty() ||
            confirmPassword.isEmpty()) {

            request.setAttribute(
                    "error",
                    "All fields are required.");

            request.getRequestDispatcher(
                    "register.jsp").forward(request, response);

            return;
        }

        username = username.trim();
        email = email.trim();

        // Validate username length
        if (username.length() < 3 ||
            username.length() > 50) {

            request.setAttribute(
                    "error",
                    "Username must contain 3 to 50 characters.");

            request.getRequestDispatcher(
                    "register.jsp").forward(request, response);

            return;
        }

        // Validate password length
        if (password.length() < 8) {

            request.setAttribute(
                    "error",
                    "Password must contain at least 8 characters.");

            request.getRequestDispatcher(
                    "register.jsp").forward(request, response);

            return;
        }

        // Validate password confirmation
        if (!password.equals(confirmPassword)) {

            request.setAttribute(
                    "error",
                    "Passwords do not match.");

            request.getRequestDispatcher(
                    "register.jsp").forward(request, response);

            return;
        }

        String sql =
                "INSERT INTO users " +
                "(username, email, password, total_xp, player_level) " +
                "VALUES (?, ?, ?, 0, 1)";

        try (
            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql)
        ) {

            ps.setString(1, username);
            ps.setString(2, email);

            // Demo implementation.
            // Replace with a secure password hash before production.
            ps.setString(3, password);

            int result = ps.executeUpdate();

            if (result > 0) {

                response.sendRedirect(
                        "login.jsp?registered=true");

            } else {

                request.setAttribute(
                        "error",
                        "Registration failed. Please try again.");

                request.getRequestDispatcher(
                        "register.jsp").forward(request, response);
            }

        } catch (SQLException e) {

            // MySQL error code 1062 means duplicate entry
            if (e.getErrorCode() == 1062) {

                request.setAttribute(
                        "error",
                        "Username or email already exists.");

            } else {

                e.printStackTrace();

                request.setAttribute(
                        "error",
                        "Database error. Please try again later.");
            }

            request.getRequestDispatcher(
                    "register.jsp").forward(request, response);
        }
    }
}
