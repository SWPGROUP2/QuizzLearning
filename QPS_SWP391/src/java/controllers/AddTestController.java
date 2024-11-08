package controllers;

import dal.ClassDAO;
import dal.QuestionTypeDAO;
import dal.TestDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import models.Classes;
import models.QuestionType;
import models.Test;
import models.User;
import models.subject;

public class AddTestController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("account");

        int userId = loggedInUser.getUserId();

        ClassDAO classDAO = new ClassDAO();
        QuestionTypeDAO questionTypeDAO = new QuestionTypeDAO();

        List<Classes> uniqueClasses = classDAO.getTeacherClasses(userId);
        List<subject> uniqueSubjects = classDAO.getTeacherSubjects(userId);

        // Remove duplicates from the unique classes list if any
        Set<Classes> uniqueClassSet = new HashSet<>(uniqueClasses);
        uniqueClasses.clear();
        uniqueClasses.addAll(uniqueClassSet);

        // Log the classes (optional)
        for (Classes c : uniqueClasses) {
            System.out.println("Class ID: " + c.getClassID() + " Name: " + c.getClassName());
        }

        // Assuming you fetch all question types for the test creation
        List<QuestionType> questionTypes = questionTypeDAO.getAllQuestionTypes();

        // Set the teacher-specific classes (which already include the subjects) as request attributes
        request.setAttribute("uniqueClasses", uniqueClasses);
        request.setAttribute("uniqueSubjects", uniqueSubjects);
        request.setAttribute("questionTypes", questionTypes);

        // Forward to the addtest.jsp page
        request.getRequestDispatcher("addtest.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Initialize variables for form parameters
        int subjectId = -1;
        int classId = -1;
        int duration = -1;
        int questionTypeId = -1;
        String testName = request.getParameter("testName");

        try {
            // Retrieve parameters from the form and safely parse them
            if (request.getParameter("subjectId") != null && !request.getParameter("subjectId").isEmpty()) {
                subjectId = Integer.parseInt(request.getParameter("subjectId"));
            }
            if (request.getParameter("class") != null && !request.getParameter("class").isEmpty()) {
                classId = Integer.parseInt(request.getParameter("class"));
            }
            if (request.getParameter("duration") != null && !request.getParameter("duration").isEmpty()) {
                duration = Integer.parseInt(request.getParameter("duration"));
            }
            if (request.getParameter("questionTypeId") != null && !request.getParameter("questionTypeId").isEmpty()) {
                questionTypeId = Integer.parseInt(request.getParameter("questionTypeId"));
            }

        } catch (NumberFormatException e) {
            // Handle invalid number format by setting an error message
            request.setAttribute("errorMessage", "Invalid input detected. Please ensure all fields are filled correctly.");
            forwardToAddTestPageWithError(request, response);
            return;
        }

        // Check if any of the required fields are missing
        if (subjectId == -1 || classId == -1 || duration == -1 || questionTypeId == -1 || testName == null || testName.isEmpty()) {
            // Set error message and forward back to the form
            request.setAttribute("errorMessage", "All fields are required. Please fill in all the details.");
            forwardToAddTestPageWithError(request, response);
            return;
        }

        // Create a new test object and set the form parameters
        Test test = new Test();
        test.setSubjectId(subjectId);
        test.setClassId(classId);
        test.setTestName(testName);
        test.setDuration(duration);
        test.setQuestionTypeId(questionTypeId);        

        // Instantiate TestDAO to add the test to the database
        TestDAO testDAO = new TestDAO();
        boolean success = testDAO.addTest(test);

        if (success) {
            // Retrieve the testId after successful insertion and redirect to the edit page
            int testId = testDAO.getTestId(testName, subjectId, classId, duration);
            if (testId > 0) {
                response.sendRedirect("edittest?testId=" + testId + "&subjectId=" + subjectId);
            } else {
                response.sendRedirect("addtest.jsp");
            }
        } else {
            // Re-fetch the teacher-specific classes and subjects in case of failure
            forwardToAddTestPageWithError(request, response);
        }
    }

    private void forwardToAddTestPageWithError(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Re-fetch the teacher's classes and subjects and set them as request attributes
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("account");

        if (loggedInUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Get teacher's classes and subjects by userId
        ClassDAO classDAO = new ClassDAO();
        List<Classes> uniqueClasses = classDAO.getTeacherClasses(loggedInUser.getUserId());
        List<subject> uniqueSubjects = classDAO.getTeacherSubjects(loggedInUser.getUserId());

        // Set the error message and classes and subjects for re-rendering
        request.setAttribute("uniqueClasses", uniqueClasses);
        request.setAttribute("uniqueSubjects", uniqueSubjects);

        // Forward the request to the addtest.jsp page with the error message
        request.getRequestDispatcher("addtest.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet to handle adding tests for a teacher";
    }
}
