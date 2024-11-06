package controllers;
import dal.QuestionDAO;
import models.Question;
import models.QuestionType;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class QuestionList extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get filter parameters from the request
        String subjectId = request.getParameter("subjectId");
        String chapterId = request.getParameter("chapterId");
        String questionTypeId = request.getParameter("questionTypeId");
        String currentPageParam = request.getParameter("page");

        int currentPage = (currentPageParam != null) ? Integer.parseInt(currentPageParam) : 1;
        int questionsPerPage = 10;

        // Initialize the QuestionDAO
        QuestionDAO questionDAO = new QuestionDAO();

        // Fetch the filtered questions list
        List<Question> questionList = questionDAO.getFilteredQuestions(subjectId, chapterId, questionTypeId, currentPage, questionsPerPage);

        // Fetch the list of unique subjects from the database
        Map<Integer, String> uniqueSubjects = questionDAO.getUniqueSubjects();
Set<Integer> uniqueChapters = questionDAO.getUniqueChapters();
        // Fetch all question types
        List<QuestionType> questionTypeList = questionDAO.getAllQuestionTypes();
Map<Integer, String> uniqueQuestionTypes = questionDAO.getUniqueQuestionTypes();
        // Get the total count of questions for pagination
        int totalQuestions = questionDAO.getFilteredQuestionCount(subjectId, chapterId, questionTypeId);
        int totalPages = (int) Math.ceil((double) totalQuestions / questionsPerPage);

        // Set the request attributes
        request.setAttribute("questionList", questionList);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("uniqueSubjects", uniqueSubjects);
        request.setAttribute("uniqueChapters", uniqueChapters);
request.setAttribute("uniqueQuestionTypes", uniqueQuestionTypes);// Pass unique subjects
        request.setAttribute("questionTypeList", questionTypeList);

        // Forward the request and response to the JSP page
        request.getRequestDispatcher("/questionlist.jsp").forward(request, response);
    }
}
