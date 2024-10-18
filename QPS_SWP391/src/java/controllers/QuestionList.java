package controllers;

import dal.QuestionDAO;
import models.Question;
import java.io.IOException;
import java.util.List;
import java.util.Set; // Import Set
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
  
            QuestionDAO questionDAO = new QuestionDAO();
            
            Question result = questionDAO.getQuestionsWithCount(subjectId, currentPage, pageSize); 
                  
            int totalQuestions = result.getTotalCount();
            int totalPages = (int) Math.ceil((double) totalQuestions / pageSize);
          
            Set<Integer> chapterSet = questionDAO.getUniqueChapters(subjectId);

            request.setAttribute("questionList", result.getQuestions());
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
