/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.ClassDAO;
import dal.QuestionTypeDAO;
import dal.TestDAO;
import dal.SubjectDAO;
import dal.TeacherSubjectsDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import javax.security.auth.Subject;
import models.Classes;
import models.QuestionType;
import models.Test;
import models.User;
import models.subject;

/**
 *
 * @author Admin
 */
public class AddTestController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddTestController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddTestController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("account");
        int userId = loggedInUser.getUserId();

        SubjectDAO subjecdao = new SubjectDAO();
        TeacherSubjectsDAO teachersubjectdao = new TeacherSubjectsDAO();
        ClassDAO classdao = new ClassDAO();

        List<subject> allsubject = subjecdao.getAllSubject();
        List<Integer> assignedSubjectIds = teachersubjectdao.getAssignedSubjectIdsByTeacherId(userId);
        List<Classes> teacherClasses = classdao.getClassesByTeacherId(userId);

        List<subject> teacherSubjects = new ArrayList<>();
        for (subject subjects : allsubject) {
            if (assignedSubjectIds.contains(subjects.getSubjectId())) {
                teacherSubjects.add(subjects);
            }
        }

        request.setAttribute("teacherSubjects", teacherSubjects);
        request.setAttribute("teacherClasses", teacherClasses);

        request.getRequestDispatcher("addtest.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));
        int classId = Integer.parseInt(request.getParameter("class"));
        String testName = request.getParameter("testName");
        int duration = Integer.parseInt(request.getParameter("duration"));
        int questionTypeId = Integer.parseInt(request.getParameter("questionTypeId"));

        Test test = new Test();
        test.setSubjectId(subjectId);
        test.setClassId(classId);
        test.setTestName(testName);
        test.setDuration(duration);
        test.setQuestionTypeId(questionTypeId);

        TestDAO testdao = new TestDAO();
        boolean success = testdao.addTest(test);

        if (success) {
            int testId = testdao.getTestId(testName, subjectId, classId, duration);
            if (testId > 0) {
                response.sendRedirect("edittest?testId=" + testId + "&subjectId=" + subjectId);
            } else {
                response.sendRedirect("addtest.jsp");
            }
        } else {

            SubjectDAO subjecdao = new SubjectDAO();
            ClassDAO classdao = new ClassDAO();
            List<subject> subjects = subjecdao.getAllSubject();
            List<Classes> classlist = classdao.getAllClasses();

            request.setAttribute("subjects", subjects);
            request.setAttribute("classlist", classlist);

            request.getRequestDispatcher("addtest.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
