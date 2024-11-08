package controllers;

import dal.TermDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Term;

/**
 * Servlet EditTermController to handle term editing.
 */
public class EditTermController extends HttpServlet {

    /**
     * Handles the HTTP GET method to display the edit term page.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String termIdParam = request.getParameter("termId");

        // Check if termId is valid
        if (termIdParam != null && !termIdParam.isEmpty()) {
            try {
                int termId = Integer.parseInt(termIdParam);
                TermDAO termDAO = new TermDAO();
                Term term = termDAO.getTermById(termId);
                
                if (term != null) {
                    request.setAttribute("term", term);
                    request.getRequestDispatcher("editterm.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Term not found");
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid termId format");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "termId is missing or invalid");
        }
    }

    /**
     * Handles the HTTP POST method to update term information.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String termIdParam = request.getParameter("termId");
        String termName = request.getParameter("term");
        String definition = request.getParameter("definition");
        String termSetIdParam = request.getParameter("termSetId");

        // Validate the input parameters
        if (termIdParam == null || termName == null || definition == null || termSetIdParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required fields");
            return;
        }

        try {
            int termId = Integer.parseInt(termIdParam);
            int termSetId = Integer.parseInt(termSetIdParam);

            Term updatedTerm = new Term(termId, termSetId, termName, definition);
            TermDAO termDAO = new TermDAO();
            boolean success = termDAO.updateTerm(updatedTerm);

            if (success) {
                response.sendRedirect("termlist?termSetId=" + termSetId); 
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update term");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format for termId or termSetId");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "EditTermController handles the editing of terms.";
    }
}
