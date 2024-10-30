package controllers;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.TestDAO;
import models.Test;

public class TestList extends HttpServlet {
    TestDAO testDAO = new TestDAO();
    private static final int testsPerPage = 5;

   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchQuery = request.getParameter("searchQuery");
        String pageParam = request.getParameter("page");
        String sortBy = request.getParameter("sortBy");
        String sortOrder = request.getParameter("sortOrder"); 

        int currentPage = 1;
        if (pageParam != null) {
            currentPage = Integer.parseInt(pageParam);
        }

        List<Test> tests;
        int totalTests;
        if (searchQuery != null && !searchQuery.isEmpty()) {
            tests = testDAO.searchTestsByName(searchQuery, currentPage, testsPerPage, sortBy, sortOrder);
            totalTests = testDAO.countTestsByName(searchQuery);
        } else {
            tests = testDAO.getTestsPaginated(currentPage, testsPerPage, sortBy, sortOrder);
            totalTests = testDAO.countAllTests();
        }

        for (Test test : tests) {
            int questionCount = testDAO.countQuestionsInTest(test.getTestId());
            test.setQuestionCount(questionCount);
        }

        int totalPages = (int) Math.ceil((double) totalTests / testsPerPage);

        request.setAttribute("tests", tests);
        request.setAttribute("searchQuery", searchQuery);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("sortOrder", sortOrder);
        request.getRequestDispatcher("testList.jsp").forward(request, response); 
    }
}
