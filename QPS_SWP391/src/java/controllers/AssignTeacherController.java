package controllers;

import dal.SubjectDAO;
import dal.UserDAO;
import models.User;
import models.subject;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AssignTeacherController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String subjectIdParam = request.getParameter("subjectId");
        UserDAO userDAO = new UserDAO();
        SubjectDAO subjectDAO = new SubjectDAO();
        int subjectId = Integer.parseInt(subjectIdParam);

        subject subject = subjectDAO.getSubjectById(subjectId);
        List<User> teachers = userDAO.getAllTeachers();

        if (subject != null) {
            request.setAttribute("subject", subject);
        } else {
            request.setAttribute("subject", new subject());
        }

        if (teachers != null && !teachers.isEmpty()) {
            request.setAttribute("teachers", teachers);
        } else {
            request.setAttribute("teachers", new ArrayList<>());
        }

        request.getRequestDispatcher("assignteacher.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subjectIdParam = request.getParameter("subjectId");
        SubjectDAO subjectDAO = new SubjectDAO();

        if (subjectIdParam == null || subjectIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Subject ID is missing.");
            return;
        }

        int subjectId = Integer.parseInt(subjectIdParam);

        // Lấy danh sách teacherIds từ các checkbox
        String[] teacherIds = request.getParameterValues("teacherIds");

        if (teacherIds != null) {
            for (String teacherIdStr : teacherIds) {
                if (!teacherIdStr.isEmpty()) { 
                    int teacherId = Integer.parseInt(teacherIdStr);

                    if (!subjectDAO.isTeacherAssignedToSubject(subjectId, teacherId)) {
                        subjectDAO.assignTeacherToSubject(subjectId, teacherId);
                    } else {
                        response.sendRedirect("assignteacher.jsp?subjectId=" + subjectId + "&alreadyAssigned=true");
                        return;
                    }
                }
            }
        }
        response.sendRedirect("subject-list"); // Chuyển hướng lại trang danh sách môn học
    }
}
