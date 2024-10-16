package controllers;

import dal.QuestionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import models.Question;

public class EditQuestionController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String questionIdStr = request.getParameter("questionId");
            int questionId = Integer.parseInt(questionIdStr);
            QuestionDAO dao = new QuestionDAO();
            Question question = dao.getQuestionById(questionId);
            request.setAttribute("question", question);
            request.setAttribute("subjectId", question.getSubjectId());
            request.getRequestDispatcher("editquestion.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(EditQuestionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String questionText = request.getParameter("question");
            String definition = request.getParameter("definition");
            String questionIdStr = request.getParameter("questionId");
            String subjectIdStr = request.getParameter("subjectId");
            int subjectId = Integer.parseInt(subjectIdStr);
            int questionId = Integer.parseInt(questionIdStr);
            QuestionDAO dao = new QuestionDAO();
            dao.updateQuestion(questionId, questionText, definition);
            response.sendRedirect("question?id=" + subjectId);
        } catch (SQLException ex) {
            Logger.getLogger(EditQuestionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for editing questions";
    }
}
