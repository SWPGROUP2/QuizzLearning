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
        TermDAO termDAO = new TermDAO();

        List<Term> terms = termDAO.getAllTerms();
        request.setAttribute("terms", terms);
        request.getRequestDispatcher("termlist.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
    }
}
