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

    private TermDAO termDAO;

    @Override
    public void init() {
        termDAO = new TermDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("list".equals(action)) {
            listTerms(request, response);
        } else if ("edit".equals(action)) {
            editTerm(request, response);
        } else if ("delete".equals(action)) {
            deleteTerm(request, response);
        } else {
            listTerms(request, response);
        }
    }

    private void listTerms(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Term> terms = termDAO.getAllTerms();
        request.setAttribute("terms", terms);
        request.getRequestDispatcher("terms.jsp").forward(request, response);
    }

    private void editTerm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int termId = Integer.parseInt(request.getParameter("id"));
        Term term = termDAO.getTermById(termId); 
        request.setAttribute("term", term);
        request.getRequestDispatcher("editTerm.jsp").forward(request, response);
    }

    private void deleteTerm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int termId = Integer.parseInt(request.getParameter("id"));
        termDAO.deleteTerm(termId); 
        response.sendRedirect("terms?action=list");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            addTerm(request, response);
        } else if ("update".equals(action)) {
            updateTerm(request, response);
        }
    }

    private void addTerm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String term = request.getParameter("term");
        String definition = request.getParameter("definition");
        int termSetId = Integer.parseInt(request.getParameter("termSetId"));

        Term newTerm = new Term();
        newTerm.setTermSetId(termSetId);
        newTerm.setTerm(term);
        newTerm.setDefinition(definition);
        
        termDAO.addTerm(newTerm); 
        response.sendRedirect("terms?action=list");
    }

    private void updateTerm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int termId = Integer.parseInt(request.getParameter("termId"));
        String term = request.getParameter("term");
        String definition = request.getParameter("definition");

        Term updatedTerm = new Term();
        updatedTerm.setTermId(termId);
        updatedTerm.setTerm(term);
        updatedTerm.setDefinition(definition);

        termDAO.updateTerm(updatedTerm);
        response.sendRedirect("terms?action=list");
    }
}
