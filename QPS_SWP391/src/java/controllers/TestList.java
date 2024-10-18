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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String searchQuery = request.getParameter("searchQuery");

        List<Test> tests;
        if (searchQuery != null && !searchQuery.isEmpty()) {
            
            tests = testDAO.searchTestsByName(searchQuery);
        } else {
            
            tests = testDAO.getAllTests();
        }

        request.setAttribute("tests", tests);
        request.setAttribute("searchQuery", searchQuery); 
        request.getRequestDispatcher("testList.jsp").forward(request, response); 
    }
}