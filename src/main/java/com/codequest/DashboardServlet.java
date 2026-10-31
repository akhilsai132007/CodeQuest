
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

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // XP required for each level
    private static final int XP_PER_LEVEL = 100;

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Check student session
        HttpSession session =
                request.getSession(false);

        if (session == null ||
            session.getAttribute("studentId") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login.jsp");

            return;
        }

        // Get logged-in student ID
        Integer studentId =
                (Integer) session.getAttribute("studentId");

        // SQL query to retrieve student details
        String studentSql =
                "SELECT username, total_xp, player_level " +
                "FROM users WHERE id = ?";

        // SQL query to count correctly completed missions
        String completedSql =
                "SELECT COUNT(*) AS completed_count " +
                "FROM mission_submissions " +
                "WHERE user_id = ? AND is_correct = TRUE";

        try (
            Connection con = DBConnection.getConnection();

            PreparedStatement studentPs =
                    con.prepareStatement(studentSql);

            PreparedStatement completedPs =
                    con.prepareStatement(completedSql)
        ) {

            // Retrieve student information
            studentPs.setInt(1, studentId);

            try (ResultSet studentRs =
                    studentPs.executeQuery()) {

                if (!studentRs.next()) {

                    session.invalidate();

                    response.sendRedirect(
                            request.getContextPath()
                            + "/login.jsp");

                    return;
                }

                String username =
                        studentRs.getString("username");

                int totalXP =
                        studentRs.getInt("total_xp");

                int playerLevel =
                        studentRs.getInt("player_level");

                // Retrieve completed mission count
                completedPs.setInt(1, studentId);

                int completedMissions = 0;

                try (ResultSet completedRs =
                        completedPs.executeQuery()) {

                    if (completedRs.next()) {

                        completedMissions =
                                completedRs.getInt(
                                        "completed_count");
                    }
                }

                // Calculate XP progress
                int currentLevelXP =
                        totalXP % XP_PER_LEVEL;

                int nextLevelXP =
                        XP_PER_LEVEL;

                int progressPercentage =
                        (currentLevelXP * 100)
                        / XP_PER_LEVEL;

                // Send data to dashboard.jsp
                request.setAttribute(
                        "username", username);

                request.setAttribute(
                        "totalXP", totalXP);

                request.setAttribute(
                        "playerLevel", playerLevel);

                request.setAttribute(
                        "completedMissions",
                        completedMissions);

                request.setAttribute(
                        "currentLevelXP",
                        currentLevelXP);

                request.setAttribute(
                        "nextLevelXP",
                        nextLevelXP);

                request.setAttribute(
                        "progressPercentage",
                        progressPercentage);

                // Forward request to JSP
                request.getRequestDispatcher(
                        "dashboard.jsp").forward(
                                request, response);
            }

        } catch (SQLException e) {

            e.printStackTrace();

            request.setAttribute(
                    "error",
                    "Unable to load dashboard. Please try again.");

            request.getRequestDispatcher(
                    "dashboard.jsp").forward(
                            request, response);
        }
    }
}
