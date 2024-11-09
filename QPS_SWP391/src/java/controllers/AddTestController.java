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
        
        String testName = request.getParameter("testName");
        String subjectIdStr = request.getParameter("subjectId");
        String classIdStr = request.getParameter("classId");
        String durationStr = request.getParameter("duration");
        String testStartDateStr = request.getParameter("testStartDate");
        String testEndDateStr = request.getParameter("testEndDate");

        // Save form values for repopulation
        request.setAttribute("testName", testName);
        request.setAttribute("subjectId", subjectIdStr);
        request.setAttribute("classId", classIdStr);
        request.setAttribute("duration", durationStr);
        request.setAttribute("testStartDate", testStartDateStr);
        request.setAttribute("testEndDate", testEndDateStr);

        boolean hasError = false;

        // Validate test name
        if (isBlank(testName)) {
            request.setAttribute("testNameError", "Test name không được để trống");
            hasError = true;
        }

        if (hasError) {
            // Repopulate the form data
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
            return;
        }

        // If validation passes, proceed with test creation
        int subjectId = Integer.parseInt(subjectIdStr);
        int classId = Integer.parseInt(classIdStr);
        int duration = Integer.parseInt(durationStr);
        Timestamp testStartDate = Timestamp.valueOf(testStartDateStr.replace("T", " ") + ":00");
        Timestamp testEndDate = Timestamp.valueOf(testEndDateStr.replace("T", " ") + ":00");

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

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}