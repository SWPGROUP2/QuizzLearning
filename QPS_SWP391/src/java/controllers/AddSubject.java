package controllers;

import dal.SubjectDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.subject;

public class AddSubject extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("addSubject.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SubjectDAO dao = new SubjectDAO();

        String name = request.getParameter("name");
        String title = request.getParameter("title");

        subject newSubject = new subject();
        newSubject.setSubjectName(name);
        newSubject.setTitle(title);

        try {
            dao.addSubject(newSubject);
            request.setAttribute("message", "Subject added successfully!");
            request.getRequestDispatcher("addSubject.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error adding subject");
            request.getRequestDispatcher("addSubject.jsp").forward(request, response);
        }
    }
}
