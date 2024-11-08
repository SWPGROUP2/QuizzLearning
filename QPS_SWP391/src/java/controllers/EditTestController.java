package controllers;

import dal.ClassDAO;
import dal.QuestionDAO;
import dal.QuestionTypeDAO;
import dal.SubjectDAO;
import dal.TeacherSubjectsDAO;
import dal.TestDAO;
import dal.TestQuestionDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Classes;
import models.Question;
import models.Test;
import models.User;

public class EditTestController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String testIdParam = request.getParameter("testId");
        String subjectIdParam = request.getParameter("subjectId");

        int testId = Integer.parseInt(testIdParam);
        int subjectId = Integer.parseInt(subjectIdParam);

        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("account");
        int userId = loggedInUser.getUserId();

        TestDAO testdao = new TestDAO();
        QuestionDAO quesdao = new QuestionDAO();
        ClassDAO classdao = new ClassDAO();
        TestQuestionDAO testquestiondao = new TestQuestionDAO();

        Test test = testdao.getTestById(testId);

        List<Question> questions = quesdao.getQuestionsBySubjectId(subjectId);
        List<Integer> selectedQuestionIds = testquestiondao.getQuestionIdsByTestId(testId);
        List<Classes> teacherClasses = classdao.getClassesByTeacherId(userId);

        request.setAttribute("test", test);
        request.setAttribute("questions", questions);
        request.setAttribute("teacherClasses", teacherClasses);
        request.setAttribute("selectedQuestionIds", selectedQuestionIds);
        request.setAttribute("testName", test.getTestName());
        request.setAttribute("duration", test.getDuration());
        request.setAttribute("startTime", test.getTestStartTime());
        request.setAttribute("endTime", test.getTestEndTime());
        request.setAttribute("status", test.getStatus());

        request.getRequestDispatcher("edittest.jsp").forward(request, response);
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
        String durationStr = request.getParameter("duration");
        String classIdStr = request.getParameter("classId");
        String statusStr = request.getParameter("status");
        String startTimeStr = request.getParameter("startTime");
        String endTimeStr = request.getParameter("endTime");

        if (classIdStr == null || classIdStr.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Class ID is missing");
            return;
        }

        int duration = Integer.parseInt(durationStr);
        int classId = Integer.parseInt(classIdStr);
        int status = Integer.parseInt(statusStr);

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date startDate = dateFormat.parse(startTimeStr);
            Date endDate = dateFormat.parse(endTimeStr);

            Test test = new Test();
            test.setTestID(testId);
            test.setTestName(testName);
            test.setDuration(duration);
            test.setClassId(classId);
            test.setTestStartTime(new java.sql.Timestamp(startDate.getTime()));
            test.setTestEndTime(new java.sql.Timestamp(endDate.getTime()));
            test.setStatus(status);

            TestDAO testdao = new TestDAO();
            testdao.updateTest(test);

            String[] questionIds = request.getParameterValues("questionIds");
            if (questionIds == null) {
                response.sendRedirect("test-list");
                return;
            }

            TestQuestionDAO testQuestionDAO = new TestQuestionDAO();
            testQuestionDAO.saveTestQuestions(testId, questionIds);

            response.sendRedirect("test-list");
        } catch (ParseException | SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error processing request");
        }
    }
}