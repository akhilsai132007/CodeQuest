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
import jakarta.servlet.http.HttpSession;

@WebServlet("/delete-question")
public class DeleteQuestionServlet extends HttpServlet {

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

        String idValue = request.getParameter("id");
        int questionId;

        try {
            questionId = Integer.parseInt(idValue);

            if (questionId <= 0) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException e) {

            response.sendRedirect(
                request.getContextPath() + "/view-questions"
            );
            return;
        }

        String sql = "DELETE FROM missions WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, questionId);

            int rowsDeleted = ps.executeUpdate();

            if (rowsDeleted > 0) {
                response.sendRedirect(
                    request.getContextPath()
                    + "/view-questions?deleted=true"
                );
            } else {
                response.sendRedirect(
                    request.getContextPath()
                    + "/view-questions?deleted=false"
                );
            }

        } catch (SQLException e) {

            // A mission may already have student submissions.
            // Foreign-key constraints can prevent its deletion.
            if ("23000".equals(e.getSQLState())) {

                response.sendRedirect(
                    request.getContextPath()
                    + "/view-questions?error=submissions"
                );

            } else {
                throw new ServletException(
                    "Unable to delete question.", e
                );
            }
        }
    }
}