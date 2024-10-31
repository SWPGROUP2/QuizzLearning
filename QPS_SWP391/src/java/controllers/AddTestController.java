/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.ClassDAO;
import dal.TestDAO;
import dal.SubjectDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import javax.security.auth.Subject;
import models.Classes;
import models.Test;
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
        SubjectDAO subjecdao = new SubjectDAO();
        ClassDAO classdao = new ClassDAO();

        List<subject> subjects = subjecdao.getAllSubject();
        List<Classes> classlist = classdao.getAllClasses();

        request.setAttribute("subjects", subjects);
        request.setAttribute("classlist", classlist);
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

        Test test = new Test();
        test.setSubjectId(subjectId);
        test.setClassId(classId);
        test.setTestName(testName);
        test.setDuration(duration);

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
            request.setAttribute("error", "Failed to add test. Please try again.");

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
