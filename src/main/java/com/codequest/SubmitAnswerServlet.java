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

@WebServlet("/submit-answer")
public class SubmitAnswerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
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

        int studentId = (Integer) session.getAttribute("studentId");

        String missionIdValue = request.getParameter("missionId");
        String submittedAnswer = request.getParameter("answer");

        int missionId;

        try {
            missionId = Integer.parseInt(missionIdValue);

            if (missionId <= 0) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException e) {

            request.setAttribute("message", "Invalid mission ID.");
            request.setAttribute("isCorrect", false);

            request.getRequestDispatcher("mission-result.jsp")
                   .forward(request, response);
            return;
        }

        if (submittedAnswer == null ||
            !(submittedAnswer.equals("A") ||
              submittedAnswer.equals("B") ||
              submittedAnswer.equals("C") ||
              submittedAnswer.equals("D"))) {

            request.setAttribute("message", "Please select a valid answer.");
            request.setAttribute("isCorrect", false);

            request.getRequestDispatcher("mission-result.jsp")
                   .forward(request, response);
            return;
        }

        String missionSql =
            "SELECT correct_answer, xp_reward FROM missions WHERE id = ?";

        String submissionSql =
            "INSERT INTO mission_submissions "
          + "(user_id, mission_id, answer, is_correct) "
          + "VALUES (?, ?, ?, ?)";

        String updateUserSql =
            "UPDATE users "
          + "SET total_xp = total_xp + ?, "
          + "player_level = FLOOR((total_xp + ?) / 100) + 1 "
          + "WHERE id = ?";

        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            try {
                String correctAnswer;
                int xpReward;

                // 1. Retrieve the correct answer and XP reward.
                try (PreparedStatement ps =
                         con.prepareStatement(missionSql)) {

                    ps.setInt(1, missionId);

                    try (ResultSet rs = ps.executeQuery()) {

                        if (!rs.next()) {
                            con.rollback();

                            request.setAttribute(
                                "message",
                                "This mission does not exist."
                            );

                            request.setAttribute("isCorrect", false);

                            request.getRequestDispatcher(
                                "mission-result.jsp"
                            ).forward(request, response);

                            return;
                        }

                        correctAnswer = rs.getString("correct_answer");
                        xpReward = rs.getInt("xp_reward");
                    }
                }

                boolean isCorrect =
                    submittedAnswer.equalsIgnoreCase(correctAnswer);

                // 2. Record the attempt.
                // The database UNIQUE constraint prevents a second attempt.
                try (PreparedStatement ps =
                         con.prepareStatement(submissionSql)) {

                    ps.setInt(1, studentId);
                    ps.setInt(2, missionId);
                    ps.setString(3, submittedAnswer);
                    ps.setBoolean(4, isCorrect);

                    ps.executeUpdate();
                }

                // 3. Award XP only when the answer is correct.
                if (isCorrect) {

                    try (PreparedStatement ps =
                             con.prepareStatement(updateUserSql)) {

                        ps.setInt(1, xpReward);
                        ps.setInt(2, xpReward);
                        ps.setInt(3, studentId);

                        ps.executeUpdate();
                    }
                }

                con.commit();

                request.setAttribute("isCorrect", isCorrect);
                request.setAttribute("xpReward", xpReward);

                if (isCorrect) {
                    request.setAttribute(
                        "message",
                        "Correct answer! You earned " + xpReward + " XP."
                    );
                } else {
                    request.setAttribute(
                        "message",
                        "Incorrect answer. Better luck next time!"
                    );
                }

                request.getRequestDispatcher("mission-result.jsp")
                       .forward(request, response);

            } catch (SQLException e) {

                con.rollback();

                // SQLState 23000 usually indicates a constraint violation,
                // including the UNIQUE(user_id, mission_id) constraint.
                if ("23000".equals(e.getSQLState())) {

                    request.setAttribute(
                        "message",
                        "You have already attempted this mission."
                    );

                    request.setAttribute("isCorrect", false);

                    request.getRequestDispatcher(
                        "mission-result.jsp"
                    ).forward(request, response);

                    return;
                }

                throw e;

            } finally {
                con.setAutoCommit(true);
            }

        } catch (Exception e) {
            throw new ServletException(
                "Unable to submit your answer.", e
            );
        }
    }
}