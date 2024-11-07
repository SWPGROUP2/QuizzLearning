package controllers;
import dal.QuestionDAO;
import dal.UserDAO;
import models.Question;
import models.QuestionType;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import models.User;


public class QuestionList extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get filter parameters from the request
        String subjectId = request.getParameter("subjectId");
        String chapterId = request.getParameter("chapterId");
        String questionTypeId = request.getParameter("questionTypeId");
        String currentPageParam = request.getParameter("page");
         String questionSearch = request.getParameter("questionSearch");
        int currentPage = (currentPageParam != null) ? Integer.parseInt(currentPageParam) : 1;
        int questionsPerPage = 10;
        
         HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("account");

        // If the user is not logged in, redirect to login page
        if (loggedInUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Retrieve the userId and roleId from the logged-in user
        int userId = loggedInUser.getUserId();  // Assuming `User` has `getUserId()` method
        int roleId = loggedInUser.getRoleId();  // Assuming `User` has `getRoleId()` method
        QuestionDAO questionDAO = new QuestionDAO();

        List<Question> questionList = questionDAO.getFilteredQuestions(subjectId, chapterId, questionTypeId, currentPage, questionsPerPage, roleId, userId, questionSearch);

        Map<Integer, String> uniqueSubjects = questionDAO.getUniqueSubjects(userId);
Set<Integer> uniqueChapters = questionDAO.getUniqueChapters();
        List<QuestionType> questionTypeList = questionDAO.getAllQuestionTypes();
Map<Integer, String> uniqueQuestionTypes = questionDAO.getUniqueQuestionTypes();
        int totalQuestions = questionDAO.getFilteredQuestionCount(subjectId, chapterId, questionTypeId, userId);
        int totalPages = (int) Math.ceil((double) totalQuestions / questionsPerPage);

        request.setAttribute("questionList", questionList);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("uniqueSubjects", uniqueSubjects);
        request.setAttribute("uniqueChapters", uniqueChapters);
        request.setAttribute("uniqueQuestionTypes", uniqueQuestionTypes);// Pass unique subjects
        request.setAttribute("questionTypeList", questionTypeList);

        request.getRequestDispatcher("/questionlist.jsp").forward(request, response);
    }
}
