package controllers;

import dal.QuestionDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AddQuestionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String subjectIdStr = request.getParameter("subjectId");
        request.setAttribute("subjectId", subjectIdStr);
        request.getRequestDispatcher("addquestion.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String questionText = request.getParameter("question");
        String definition = request.getParameter("definition");
        String subjectIdStr = request.getParameter("subjectId");

        int subjectId = Integer.parseInt(subjectIdStr);
        QuestionDAO dao = new QuestionDAO();

        if (dao.questionExists(subjectId, questionText)) {
            request.setAttribute("errorMessage", "This question already exists in this subject.");
            request.setAttribute("subjectId", subjectIdStr);
            request.getRequestDispatcher("addquestion.jsp").forward(request, response);
        } else {
            dao.addQuestion(subjectId, questionText, definition);
            response.sendRedirect("question?id=" + subjectId);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for adding questions";
    }
}
