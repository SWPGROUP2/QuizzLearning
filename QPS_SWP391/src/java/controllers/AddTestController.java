package controllers;

import dal.ClassDAO;
import dal.TestDAO;
import dal.SubjectDAO;
import dal.TeacherSubjectsDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import models.Classes;
import models.Test;
import models.User;
import models.subject;

public class AddTestController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("account");
        int userId = loggedInUser.getUserId();

        SubjectDAO subjectDao = new SubjectDAO();
        TeacherSubjectsDAO teacherSubjectDao = new TeacherSubjectsDAO();
        ClassDAO classDao = new ClassDAO();

        List<subject> allSubjects = subjectDao.getAllSubject();
        List<Integer> assignedSubjectIds = teacherSubjectDao.getAssignedSubjectIdsByTeacherId(userId);
        List<Classes> teacherClasses = classDao.getClassesByTeacherId(userId);

        List<subject> teacherSubjects = new ArrayList<>();
        for (subject s : allSubjects) {
            if (assignedSubjectIds.contains(s.getSubjectId())) {
                teacherSubjects.add(s);
            }
        }

        request.setAttribute("teacherSubjects", teacherSubjects);
        request.setAttribute("teacherClasses", teacherClasses);
        request.getRequestDispatcher("addtest.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));
        int classId = Integer.parseInt(request.getParameter("classId"));
        String testName = request.getParameter("testName");
        int duration = Integer.parseInt(request.getParameter("duration"));
        Timestamp testStartDate = Timestamp.valueOf(request.getParameter("testStartDate").replace("T", " ") + ":00");
        Timestamp testEndDate = Timestamp.valueOf(request.getParameter("testEndDate").replace("T", " ") + ":00");

        Test test = new Test();
        test.setSubjectId(subjectId);
        test.setClassId(classId);
        test.setTestName(testName);
        test.setDuration(duration);
        test.setTestStartTime(testStartDate);
        test.setTestEndTime(testEndDate);
        test.setStatus(1);

        TestDAO testDao = new TestDAO();
        boolean success = testDao.addTest(test);

        if (success) {
            int testId = testDao.getTestId(testName, subjectId, classId, duration);
            if (testId > 0) {
                response.sendRedirect("edittest?testId=" + testId + "&subjectId=" + subjectId);
            } else {
                response.sendRedirect("addtest.jsp");
            }
        } else {
            SubjectDAO subjectDao = new SubjectDAO();
            ClassDAO classDao = new ClassDAO();
            List<subject> subjects = subjectDao.getAllSubject();
            List<Classes> classList = classDao.getAllClasses();

            request.setAttribute("subjects", subjects);
            request.setAttribute("classlist", classList);
            request.getRequestDispatcher("addtest.jsp").forward(request, response);
        }
    }
}