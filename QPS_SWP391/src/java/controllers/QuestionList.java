package controllers;

import dal.QuestionDAO;
import models.Question;
import java.io.IOException;
import java.util.List;
import java.util.Set; 
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class QuestionList extends HttpServlet {

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
        int subjectId = Integer.parseInt(request.getParameter("id"));
        int pageSize = 15; 
        int currentPage = Integer.parseInt(request.getParameter("page") != null ? request.getParameter("page") : "1");
        String chapterId = request.getParameter("chapterId");
        
        QuestionDAO questionDAO = new QuestionDAO();
        List<Question> questionList;

        if (chapterId != null && !chapterId.isEmpty()) {   
            questionList = questionDAO.getQuestionsByChapter(subjectId, chapterId);
        } else {
            questionList = questionDAO.getQuestionsBySubjectId(subjectId);
        }
        int totalQuestions = questionList.size(); 
        int totalPages = (int) Math.ceil((double) totalQuestions / pageSize);
        
        int offset = (currentPage - 1) * pageSize;
        List<Question> paginatedQuestions = questionList.subList(
            Math.min(offset, totalQuestions), 
            Math.min(offset + pageSize, totalQuestions)
        );

        Set<Integer> chapterSet = questionDAO.getUniqueChapters(subjectId);

        request.setAttribute("questionList", paginatedQuestions);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("chapterSet", chapterSet);

        request.getRequestDispatcher("questionlist.jsp").forward(request, response);
    } catch (NumberFormatException e) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid subjectId or page number");
    } catch (Exception e) {
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing your request.");
    }
}
}
