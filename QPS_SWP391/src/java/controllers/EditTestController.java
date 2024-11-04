package controllers;

import dal.ClassDAO;
import dal.QuestionDAO;
import dal.QuestionTypeDAO;
import dal.SubjectDAO;
import dal.TestDAO;
import dal.TestQuestionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import models.Classes;
import models.Question;
import models.QuestionType;
import models.subject;
import models.Test;

public class EditTestController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String testIdParam = request.getParameter("testId");
        String subjectIdParam = request.getParameter("subjectId");

        try {
            int testId = Integer.parseInt(testIdParam);
            int subjectId = Integer.parseInt(subjectIdParam);

            TestDAO testdao = new TestDAO();
            QuestionDAO quesdao = new QuestionDAO();
            ClassDAO classdao = new ClassDAO();
            SubjectDAO subjectdao = new SubjectDAO();
            QuestionTypeDAO questypedao = new QuestionTypeDAO();
            TestQuestionDAO testquestiondao = new TestQuestionDAO();

            Test test = testdao.getTestById(testId);
            List<Question> questions = quesdao.getQuestionsBySubjectId(subjectId);
            List<Classes> classes = classdao.getAllClasses();
            List<subject> subjects = subjectdao.getAllSubject();
            List<QuestionType> questionTypes = questypedao.getAllQuestionTypes();
            List<Integer> selectedQuestionIds = testquestiondao.getQuestionIdsByTestId(testId);

            request.setAttribute("test", test);
            request.setAttribute("questions", questions);
            request.setAttribute("classes", classes);
            request.setAttribute("subjects", subjects);
            request.setAttribute("questionTypes", questionTypes);
            request.setAttribute("selectedQuestionIds", selectedQuestionIds);

            request.getRequestDispatcher("edittest.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid test ID or subject ID");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String testIdParam = request.getParameter("testId");
        if (testIdParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Test ID is missing");
            return;
        }

        int testId = Integer.parseInt(testIdParam);
        String testName = request.getParameter("testName");
        String durationParam = request.getParameter("duration");
        String classIdParam = request.getParameter("class");
        String subjectIdParam = request.getParameter("subject");

        if (durationParam == null || classIdParam == null || subjectIdParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Required parameters are missing");
            return;
        }

        int duration = Integer.parseInt(durationParam);
        int classId = Integer.parseInt(classIdParam);
        int subjectId = Integer.parseInt(subjectIdParam);

        Test test = new Test();
        test.setTestID(testId);
        test.setTestName(testName);
        test.setDuration(duration);
        test.setClassId(classId);
        test.setSubjectId(subjectId);

        TestDAO testdao = new TestDAO();
        testdao.updateTest(test);

        String[] questionIds = request.getParameterValues("questionIds");
        if (questionIds == null) {
            response.sendRedirect("test-list");
            return;
        }

        TestQuestionDAO testQuestionDAO = new TestQuestionDAO();
        try {
            testQuestionDAO.saveTestQuestions(testId, questionIds);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred");
            return;
        }

        response.sendRedirect("test-list");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
