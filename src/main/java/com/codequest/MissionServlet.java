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

@WebServlet("/missions")
public class MissionServlet extends HttpServlet {

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

        String sql = "SELECT id, title, description, category, "
                   + "difficulty, option_a, option_b, option_c, "
                   + "option_d, xp_reward "
                   + "FROM missions ORDER BY id DESC";

        ArrayList<HashMap<String, Object>> missions =
            new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                HashMap<String, Object> mission = new HashMap<>();

                mission.put("id", rs.getInt("id"));
                mission.put("title", rs.getString("title"));
                mission.put("description", rs.getString("description"));
                mission.put("category", rs.getString("category"));
                mission.put("difficulty", rs.getString("difficulty"));

                mission.put("optionA", rs.getString("option_a"));
                mission.put("optionB", rs.getString("option_b"));
                mission.put("optionC", rs.getString("option_c"));
                mission.put("optionD", rs.getString("option_d"));

                mission.put("xpReward", rs.getInt("xp_reward"));

                missions.add(mission);
            }

            request.setAttribute("missions", missions);

            request.getRequestDispatcher("missions.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            throw new ServletException(
                "Unable to load missions.", e
            );
        }
    }
}