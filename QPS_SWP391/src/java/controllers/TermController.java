package controllers;

import dal.TermDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Term;

import java.io.IOException;
import java.util.List;

public class TermController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("list".equals(action)) {
            listTerms(request, response);
        } else {
            listTerms(request, response); 
        }
    }

    private void listTerms(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String termSetIdStr = request.getParameter("termSetId");
        String searchQuery = request.getParameter("searchQuery");

        if (termSetIdStr == null || termSetIdStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Term Set ID is required");
            return;  
        }

        try {
            int termSetId = Integer.parseInt(termSetIdStr);
            TermDAO termDAO = new TermDAO();
            List<Term> terms = termDAO.getTermsByTermSetId(termSetId, searchQuery);  
            request.setAttribute("terms", terms);
            request.setAttribute("searchQuery", searchQuery);
            request.getRequestDispatcher("termlist.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Term Set ID format");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
    }
}
