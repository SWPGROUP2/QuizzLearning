/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.ClassDAO;
import dal.QuestionDAO;
import dal.SubjectDAO;
import dal.TestDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import models.Classes;
import models.Question;
import models.subject;
import models.Test;

/**
 *
 * @author Admin
 */
public class EditTestController extends HttpServlet {

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
            out.println("<title>Servlet EditTestController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditTestController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String testIdParam = request.getParameter("testId");
        String subjectIdParam = request.getParameter("subjectId");

        // Kiểm tra xem cả hai tham số có tồn tại không
        if (testIdParam == null || subjectIdParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Test ID and Subject ID are required");
            return;
        }

        try {
            int testId = Integer.parseInt(testIdParam);
            int subjectId = Integer.parseInt(subjectIdParam);

            TestDAO testdao = new TestDAO();
            QuestionDAO quesdao = new QuestionDAO();
            ClassDAO classdao = new ClassDAO();
            SubjectDAO subjectdao = new SubjectDAO();

            Test test = testdao.getTestById(testId);
            List<Question> questions = quesdao.getQuestionsBySubjectId(subjectId);
            List<Classes> classes = classdao.getAllClasses();
            List<subject> subjects = subjectdao.getAllSubject();

            request.setAttribute("test", test);
            request.setAttribute("questions", questions);
            request.setAttribute("classes", classes);
            request.setAttribute("subjects", subjects);

            // Ghi nhận vào console để kiểm tra
            System.out.println("Questions retrieved: " + questions.size());

            request.getRequestDispatcher("edittest.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Test ID or Subject ID");
        }
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
        String testIdParam = request.getParameter("testId");
        int testId = Integer.parseInt(testIdParam);

        String testName = request.getParameter("testName");
        int duration = Integer.parseInt(request.getParameter("duration"));
        int classId = Integer.parseInt(request.getParameter("class"));
        int subjectId = Integer.parseInt(request.getParameter("subject"));

        Test test = new Test();
        test.setTestID(testId);
        test.setTestName(testName);
        test.setDuration(duration);
        test.setClassId(classId);
        test.setSubjectId(subjectId);

        TestDAO testdao = new TestDAO();
        testdao.updateTest(test);

        response.sendRedirect("test-list");
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
