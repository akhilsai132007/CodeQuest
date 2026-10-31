package com.codequest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/leaderboard")
public class LeaderboardServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("studentId") == null) {

            response.sendRedirect(
                request.getContextPath() + "/login.jsp"
            );
            return;
        }

        String sql = "SELECT username, total_xp, player_level "
                   + "FROM users "
                   + "ORDER BY total_xp DESC, player_level DESC, "
                   + "username ASC";

        ArrayList<HashMap<String, Object>> leaderboard =
            new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            int rank = 1;

            while (rs.next()) {

                HashMap<String, Object> player = new HashMap<>();

                player.put("rank", rank);
                player.put("username", rs.getString("username"));
                player.put("totalXP", rs.getInt("total_xp"));
                player.put("playerLevel", rs.getInt("player_level"));

                leaderboard.add(player);

                rank++;
            }

            request.setAttribute("leaderboard", leaderboard);

            request.getRequestDispatcher("leaderboard.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            throw new ServletException(
                "Unable to load leaderboard.", e
            );
        }
    }
}