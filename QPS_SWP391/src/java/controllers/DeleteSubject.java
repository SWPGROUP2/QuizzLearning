package controllers;

import dal.subjectListDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeleteSubject extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy subjectId từ request
        String subjectIdParam = request.getParameter("subjectId");
        subjectListDAO dao = new subjectListDAO();
        if (subjectIdParam != null && !subjectIdParam.isEmpty()) {
            int subjectId = Integer.parseInt(subjectIdParam);

            dao.deleteSubject(subjectId);
        }
        response.sendRedirect("subject-list");
    }
}
