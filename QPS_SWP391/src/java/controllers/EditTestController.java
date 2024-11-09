package controllers;

import dal.ClassDAO;
import dal.QuestionDAO;
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

        request.getRequestDispatcher("edittest.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String testIdParam = request.getParameter("testId");
       

        int testId = Integer.parseInt(testIdParam);
        String testName = request.getParameter("testName");
        String durationStr = request.getParameter("duration");
        String classIdStr = request.getParameter("classId");
        String startTimeStr = request.getParameter("startTime");
        String endTimeStr = request.getParameter("endTime");
        String subjectId = request.getParameter("subjectId");

        boolean hasError = false;

        request.setAttribute("testName", testName);
        request.setAttribute("duration", durationStr);
        request.setAttribute("classId", classIdStr);
        request.setAttribute("startTime", startTimeStr);
        request.setAttribute("endTime", endTimeStr);

        if (isBlank(testName)) {
            request.setAttribute("testNameError", "Test name không được để trống");
            hasError = true;
        }

        if (hasError) {
            TestDAO testdao = new TestDAO();
            QuestionDAO quesdao = new QuestionDAO();
            ClassDAO classdao = new ClassDAO();
            TestQuestionDAO testquestiondao = new TestQuestionDAO();
            
            Test test = testdao.getTestById(testId);
            List<Question> questions = quesdao.getQuestionsBySubjectId(Integer.parseInt(subjectId));
            HttpSession session = request.getSession();
            User loggedInUser = (User) session.getAttribute("account");
            List<Classes> teacherClasses = classdao.getClassesByTeacherId(loggedInUser.getUserId());
            List<Integer> selectedQuestionIds = testquestiondao.getQuestionIdsByTestId(testId);

            request.setAttribute("test", test);
            request.setAttribute("questions", questions);
            request.setAttribute("teacherClasses", teacherClasses);
            request.setAttribute("selectedQuestionIds", selectedQuestionIds);

            request.getRequestDispatcher("edittest.jsp").forward(request, response);
            return;
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date startDate = dateFormat.parse(startTimeStr);
            Date endDate = dateFormat.parse(endTimeStr);

            Test test = new Test();
            test.setTestID(testId);
            test.setTestName(testName);
            test.setDuration(Integer.parseInt(durationStr));
            test.setClassId(Integer.parseInt(classIdStr));
            test.setTestStartTime(new java.sql.Timestamp(startDate.getTime()));
            test.setTestEndTime(new java.sql.Timestamp(endDate.getTime()));

            TestDAO testdao = new TestDAO();
            testdao.updateTest(test);

            String[] questionIds = request.getParameterValues("questionIds");
            if (questionIds != null) {
                TestQuestionDAO testQuestionDAO = new TestQuestionDAO();
                testQuestionDAO.saveTestQuestions(testId, questionIds);
            }

            response.sendRedirect("test-list");
        } catch (ParseException | SQLException e) {
            e.printStackTrace();
            request.getRequestDispatcher("edittest.jsp").forward(request, response);
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}