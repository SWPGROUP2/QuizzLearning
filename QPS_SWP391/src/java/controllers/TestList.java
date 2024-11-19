package controllers;

import dal.ClassDAO;
import dal.QuestionDAO;
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

        if (loggedInUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = loggedInUser.getUserId();
        int roleId = loggedInUser.getRoleId();

        QuestionDAO questionDAO = new QuestionDAO();
        Map<Integer, String> uniqueSubjects = questionDAO.getUniqueSubjects(userId);

        ClassDAO classDAO = new ClassDAO();
        List<Classes> uniqueClasses = classDAO.getUniqueClasses(userId);
        if (uniqueClasses == null) {
            uniqueClasses = new ArrayList<>();
        }
        List<Test> tests = null;
        Integer parsedSubjectId = null;
        if (subjectIdParam != null && !subjectIdParam.isEmpty()) {
            parsedSubjectId = Integer.parseInt(subjectIdParam);
        }

        if (roleId == 3) {
            tests = testDAO.getAllTestsByClassNameAndSubjectId(
                    userId, className, parsedSubjectId, searchQuery, currentPage, testsPerPage
            );
        } else {

            List<String> classNames = testDAO.getClassNamesByUserId(userId);
            List<Integer> subjectIds = testDAO.getSubjectIdsByUserId(userId);

            if (className != null && !className.isEmpty()) {
                tests = testDAO.getAllTestsByClassNamesAndSubjectIds(userId, classNames, subjectIds, searchQuery, currentPage, testsPerPage);
            } else {
                if (!classNames.isEmpty() && !subjectIds.isEmpty()) {
                    tests = testDAO.getAllTestsByClassNamesAndSubjectIds(userId, classNames, subjectIds, searchQuery, currentPage, testsPerPage);
                }
            }
        }

        if (tests != null) {
            for (Test test : tests) {
                int questionCount = testDAO.countQuestionsInTest(test.getTestId());
                test.setQuestionCount(questionCount);
            }
        }

        int totalTests = testDAO.countTotalTests(userId, subjectIdParam, className, searchQuery);
        int totalPages = (int) Math.ceil((double) totalTests / testsPerPage);

        request.setAttribute("uniqueClasses", uniqueClasses);
        request.setAttribute("uniqueSubjects", uniqueSubjects);
        request.setAttribute("tests", tests);
        request.setAttribute("searchQuery", searchQuery);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);

        request.getRequestDispatcher("testList.jsp").forward(request, response);
    }
}
