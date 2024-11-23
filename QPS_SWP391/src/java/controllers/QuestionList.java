package controllers;

import dal.QuestionDAO;
import dal.SubjectDAO;
import dal.TeacherSubjectsDAO;
import dal.UserDAO;
import models.Question;
import models.QuestionType;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import models.User;
import models.subject;

public class QuestionList extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subjectId = request.getParameter("subjectId");
        String chapterId = request.getParameter("chapterId");
        String questionTypeId = request.getParameter("questionTypeId");
        String currentPageParam = request.getParameter("page");
        String questionSearch = request.getParameter("questionSearch");
        String sortOrder = request.getParameter("sortOrder");
        int currentPage = (currentPageParam != null) ? Integer.parseInt(currentPageParam) : 1;
        int questionsPerPage = 10;

        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("account");

        int userId = loggedInUser.getUserId();  
        int roleId = loggedInUser.getRoleId();  
        QuestionDAO questionDAO = new QuestionDAO();
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
        List<Question> questionList = questionDAO.getFilteredQuestions(subjectId, chapterId, questionTypeId, currentPage, questionsPerPage, roleId, userId, questionSearch, sortOrder);


        Set<Integer> uniqueChapters = questionDAO.getUniqueChapters();
        List<QuestionType> questionTypeList = questionDAO.getAllQuestionTypes();
        Map<Integer, String> uniqueQuestionTypes = questionDAO.getUniqueQuestionTypes();
        int totalQuestions = questionDAO.getFilteredQuestionCount(subjectId, chapterId, questionTypeId, userId);
        int totalPages = (int) Math.ceil((double) totalQuestions / questionsPerPage);

        request.setAttribute("questionList", questionList);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("teacherSubjects", teacherSubjects);
        request.setAttribute("uniqueChapters", uniqueChapters);
        request.setAttribute("uniqueQuestionTypes", uniqueQuestionTypes);
        request.setAttribute("questionTypeList", questionTypeList);
        request.setAttribute("sortOrder", sortOrder);
        request.getRequestDispatcher("/questionlist.jsp").forward(request, response);
    }
}
