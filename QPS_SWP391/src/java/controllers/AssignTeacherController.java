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
        UserDAO userDAO = new UserDAO();
        List<User> teachers = userDAO.getAllTeachers();

        if (teachers != null && !teachers.isEmpty()) {
            request.setAttribute("teachers", teachers);
        } else {
            request.setAttribute("teachers", new ArrayList<>());
        }

        request.getRequestDispatcher("assignteacher.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy thông tin từ form
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));
        String[] teacherIds = request.getParameterValues("teacherIds");

        SubjectDAO subjectDAL = new SubjectDAO();

        // Gán giáo viên cho môn học
        for (String teacherId : teacherIds) {
            subjectDAL.assignTeacherToSubject(subjectId, Integer.parseInt(teacherId));
        }

        // Chuyển hướng về trang danh sách môn học
        response.sendRedirect("subject-list");
    }
}
