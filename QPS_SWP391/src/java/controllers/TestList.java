package controllers;

import dal.ClassDAO;
import dal.QuestionDAO;
import dal.SubjectDAO;
import dal.TeacherSubjectsDAO;
import dal.TestDAO;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import models.Classes;
import models.Test;
import models.User;
import models.subject;

public class TestList extends HttpServlet {

    TestDAO testDAO = new TestDAO();
    private static final int testsPerPage = 5;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String searchQuery = request.getParameter("searchQuery");
        String pageParam = request.getParameter("page");
        String subjectIdParam = request.getParameter("subjectId");
        String className = request.getParameter("className");

        int currentPage = 1;
        if (pageParam != null) {
            currentPage = Integer.parseInt(pageParam);
        }

        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("account");

        int userId = loggedInUser.getUserId();

        SubjectDAO subjectDao = new SubjectDAO();
        TeacherSubjectsDAO teacherSubjectDao = new TeacherSubjectsDAO();
        
        List<subject> allSubjects = subjectDao.getAllSubject();
        List<Integer> assignedSubjectIds = teacherSubjectDao.getAssignedSubjectIdsByTeacherId(userId);
        List<subject> teacherSubjects = new ArrayList<>();
        for (subject s : allSubjects) {
            if (assignedSubjectIds.contains(s.getSubjectId())) {
                teacherSubjects.add(s);
            }
        }
        
        ClassDAO classDAO = new ClassDAO();
        List<Classes> teacherClasses = classDAO.getClassesByTeacherId(userId);
        List<Classes> uniqueClasses = classDAO.getUniqueClasses(userId);
        if (uniqueClasses == null) {
            uniqueClasses = new ArrayList<>();
        }
        List<Test> tests = null;
        Integer parsedSubjectId = null;
        if (subjectIdParam != null && !subjectIdParam.isEmpty()) {
            parsedSubjectId = Integer.parseInt(subjectIdParam);
        }

            tests = testDAO.getAllTestsByClassNameAndSubjectId(userId, className, parsedSubjectId, searchQuery, currentPage, testsPerPage);

        if (tests != null) {
            for (Test test : tests) {
                int questionCount = testDAO.countQuestionsInTest(test.getTestId());
                test.setQuestionCount(questionCount);
            }
        }

        int totalTests = testDAO.countTotalTests(userId, subjectIdParam, className, searchQuery);
        int totalPages = (int) Math.ceil((double) totalTests / testsPerPage);

        request.setAttribute("teacherClasses", teacherClasses);
        request.setAttribute("teacherSubjects", teacherSubjects);
        request.setAttribute("tests", tests);
        request.setAttribute("searchQuery", searchQuery);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);

        request.getRequestDispatcher("testList.jsp").forward(request, response);
    }
}
