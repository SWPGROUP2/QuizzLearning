package controllers;

import dal.QuestionDAO;
import models.Question;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class QuestionList extends HttpServlet {

  
   public QuestionService questionService = new QuestionService();


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        int subjectid = Integer.parseInt(request.getParameter("id"));
        int pageSize = 15; 
        int currentPage = Integer.parseInt(request.getParameter("page") != null ? request.getParameter("page") : "1");

        Question result = questionService.getQuestionsWithCount(subjectid, currentPage, pageSize);
        int totalQuestions = result.getTotalCount();
        int totalPages = (int) Math.ceil((double) totalQuestions / pageSize); // Calculate total pages

        // Set data for JSP
        request.setAttribute("questionList", result.getQuestions());
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);

        // Forward to JSP
        request.getRequestDispatcher("question.jsp").forward(request, response);
    }
}