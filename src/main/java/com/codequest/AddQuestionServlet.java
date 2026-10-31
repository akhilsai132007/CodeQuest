package com.codequest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/add-question")
public class AddQuestionServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("adminId") == null) {

            response.sendRedirect(
                request.getContextPath() + "/admin-login.jsp"
            );
            return;
        }

        int adminId = (Integer) session.getAttribute("adminId");

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        String difficulty = request.getParameter("difficulty");

        String optionA = request.getParameter("optionA");
        String optionB = request.getParameter("optionB");
        String optionC = request.getParameter("optionC");
        String optionD = request.getParameter("optionD");

        String correctAnswer = request.getParameter("correctAnswer");
        String xpValue = request.getParameter("xpReward");

        if (title == null || description == null ||
            category == null || difficulty == null ||
            optionA == null || optionB == null ||
            optionC == null || optionD == null ||
            correctAnswer == null || xpValue == null) {

            request.setAttribute("error", "Please fill in all fields.");
            request.getRequestDispatcher("add-question.jsp")
                   .forward(request, response);
            return;
        }

        title = title.trim();
        description = description.trim();

        if (title.isEmpty() || description.isEmpty() ||
            optionA.trim().isEmpty() || optionB.trim().isEmpty() ||
            optionC.trim().isEmpty() || optionD.trim().isEmpty()) {

            request.setAttribute("error", "Fields cannot be empty.");
            request.getRequestDispatcher("add-question.jsp")
                   .forward(request, response);
            return;
        }

        int xpReward;

        try {
            xpReward = Integer.parseInt(xpValue);

            if (xpReward < 1 || xpReward > 1000) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException e) {

            request.setAttribute(
                "error",
                "XP reward must be between 1 and 1000."
            );

            request.getRequestDispatcher("add-question.jsp")
                   .forward(request, response);
            return;
        }

        if (!correctAnswer.equals("A") &&
            !correctAnswer.equals("B") &&
            !correctAnswer.equals("C") &&
            !correctAnswer.equals("D")) {

            request.setAttribute(
                "error",
                "Please select a valid correct answer."
            );

            request.getRequestDispatcher("add-question.jsp")
                   .forward(request, response);
            return;
        }

        String sql = "INSERT INTO missions "
                   + "(title, description, category, difficulty, "
                   + "option_a, option_b, option_c, option_d, "
                   + "correct_answer, xp_reward, created_by) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, category);
            ps.setString(4, difficulty);

            ps.setString(5, optionA.trim());
            ps.setString(6, optionB.trim());
            ps.setString(7, optionC.trim());
            ps.setString(8, optionD.trim());

            ps.setString(9, correctAnswer);
            ps.setInt(10, xpReward);
            ps.setInt(11, adminId);

            int rowsInserted = ps.executeUpdate();

            if (rowsInserted > 0) {

                request.setAttribute(
                    "success",
                    "Mission added successfully!"
                );

                request.getRequestDispatcher("add-question.jsp")
                       .forward(request, response);

            } else {

                request.setAttribute(
                    "error",
                    "Unable to add mission. Please try again."
                );

                request.getRequestDispatcher("add-question.jsp")
                       .forward(request, response);
            }

        } catch (Exception e) {
            throw new ServletException("Error adding mission.", e);
        }
    }
}