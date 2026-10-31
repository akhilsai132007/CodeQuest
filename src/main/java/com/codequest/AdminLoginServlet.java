package com.codequest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin-login")
public class AdminLoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || password == null ||
            username.trim().isEmpty() || password.isEmpty()) {

            request.setAttribute("error", "Please enter username and password.");
            request.getRequestDispatcher("admin-login.jsp")
                   .forward(request, response);
            return;
        }

        String sql = "SELECT id, username FROM admins "
                   + "WHERE username = ? AND password = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username.trim());
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    HttpSession session = request.getSession();

                    // Store admin details separately from student details.
                    session.setAttribute("adminId", rs.getInt("id"));
                    session.setAttribute("adminUsername",
                                         rs.getString("username"));

                    response.sendRedirect(
                        request.getContextPath() + "/admin-dashboard"
                    );

                } else {

                    request.setAttribute(
                        "error",
                        "Invalid admin username or password."
                    );

                    request.getRequestDispatcher("admin-login.jsp")
                           .forward(request, response);
                }
            }

        } catch (Exception e) {
            throw new ServletException("Admin login failed.", e);
        }
    }
}