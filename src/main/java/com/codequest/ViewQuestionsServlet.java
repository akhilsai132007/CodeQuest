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

@WebServlet("/view-questions")
public class ViewQuestionsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Check admin login
        if (session == null || session.getAttribute("adminId") == null) {
            response.sendRedirect("admin-login.jsp");
            return;
        }

        ArrayList<HashMap<String, Object>> questions =
                new ArrayList<>();

        String sql = "SELECT id, title, category, difficulty, xp_reward, "
                   + "correct_answer FROM missions ORDER BY id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                HashMap<String, Object> question = new HashMap<>();

                question.put("id", rs.getInt("id"));
                question.put("title", rs.getString("title"));
                question.put("category", rs.getString("category"));
                question.put("difficulty", rs.getString("difficulty"));
                question.put("xpReward", rs.getInt("xp_reward"));
                question.put("correctAnswer",
                             rs.getString("correct_answer"));

                questions.add(question);
            }

            request.setAttribute("questions", questions);

            request.getRequestDispatcher("view-questions.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();

            response.setContentType("text/html");
            response.getWriter().println(
                "<h3>Error loading questions. Check the console.</h3>"
            );
        }
    }
}