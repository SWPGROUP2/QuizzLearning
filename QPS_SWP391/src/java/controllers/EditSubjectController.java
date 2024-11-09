package controllers;

import dal.SubjectDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import models.subject;

public class EditSubjectController extends HttpServlet {

    // Method to handle GET request to show the edit form with existing data
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subjectIdStr = request.getParameter("id");
        if (subjectIdStr != null) {
            int subjectId = Integer.parseInt(subjectIdStr);

            SubjectDAO subjectDAO = new SubjectDAO();
            subject subject = subjectDAO.getSubjectById(subjectId);

            request.setAttribute("subject", subject); // Pass the subject to JSP
            request.getRequestDispatcher("editsubject.jsp").forward(request, response);
        }
    }

    // Method to handle POST request to update the subject data
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String thumbnail = "";

        subject subject = new subject(subjectId, name, description, thumbnail);

        SubjectDAO subjectDAO = new SubjectDAO();
        boolean isUpdated = subjectDAO.updateSubject(subject);

        if (isUpdated) {
            response.sendRedirect("subject-list");
        } else {
            request.setAttribute("error", "Failed to update subject");
            request.getRequestDispatcher("editsubject.jsp").forward(request, response);
        }
    }
}