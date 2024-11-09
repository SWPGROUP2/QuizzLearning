package controllers;

import dal.OptionDAO;
import dal.QuestionDAO;
import dal.SubjectDAO;
import dal.TeacherSubjectsDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import models.Option;
import models.Question;
import models.User;
import models.subject;

public class AddQuestionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            User loggedInUser = (User) session.getAttribute("account");
            int userId = loggedInUser.getUserId();

            SubjectDAO subjectDAO = new SubjectDAO();
            TeacherSubjectsDAO teachersubjectdao = new TeacherSubjectsDAO();

            List<subject> allSubjects = subjectDAO.getAllSubject();
            List<Integer> assignedSubjectIds = teachersubjectdao.getAssignedSubjectIdsByTeacherId(userId);

            List<subject> teacherSubjects = new ArrayList<>();
            for (subject subjects : allSubjects) {
                if (assignedSubjectIds.contains(subjects.getSubjectId())) {
                    teacherSubjects.add(subjects);
                }
            }

            request.setAttribute("teacherSubjects", teacherSubjects);
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("addquestion.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subjectIdStr = request.getParameter("subjectId");
        String chapterIdStr = request.getParameter("chapterId");
        String questionText = request.getParameter("question");
        String questionTypeIdStr = request.getParameter("questionTypeId");

        boolean hasError = false;

        // Validate basic fields
        if (subjectIdStr == null || subjectIdStr.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Vui lòng chọn môn học");
            hasError = true;
        }
        if (chapterIdStr == null || chapterIdStr.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Vui lòng chọn chương");
            hasError = true;
        }
        if (questionText == null || questionText.trim().isEmpty()) {
            request.setAttribute("questionError", "Nội dung câu hỏi không được để trống");
            hasError = true;
        }
        if (questionTypeIdStr == null || questionTypeIdStr.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Vui lòng chọn loại câu hỏi");
            hasError = true;
        }

        int questionTypeId = Integer.parseInt(questionTypeIdStr);
        if (questionTypeId == 1) { 
            boolean hasCorrectAnswer = false;
            for (int i = 1; i <= 4; i++) {
                String optionText = request.getParameter("optionText" + i);
                boolean isCorrect = request.getParameter("isCorrect" + i) != null;

                if (optionText == null || optionText.trim().isEmpty()) {
                    request.setAttribute("optionError" + i, "Option " + i + " không được để trống");
                    hasError = true;
                }
                request.setAttribute("optionText" + i, optionText);
                request.setAttribute("isCorrect" + i, isCorrect);

                if (isCorrect) {
                    hasCorrectAnswer = true;
                }
            }

            if (!hasCorrectAnswer) {
                request.setAttribute("errorMessage", "Phải chọn ít nhất một đáp án đúng");
                hasError = true;
            }
        } else if (questionTypeId == 2) { 
            String optionText = request.getParameter("optionText");
            if (optionText == null || optionText.trim().isEmpty()) {
                request.setAttribute("matchingError", "Đáp án không được để trống");
                request.setAttribute("optionText", optionText);
                hasError = true;
            }
        }

        request.setAttribute("question", questionText);
        request.setAttribute("subjectId", subjectIdStr);
        request.setAttribute("chapterId", chapterIdStr);
        request.setAttribute("questionTypeId", questionTypeIdStr);

        if (hasError) {
            doGet(request, response);
            return;
        }

        try {
            int subjectId = Integer.parseInt(subjectIdStr);
            int chapterId = Integer.parseInt(chapterIdStr);

            QuestionDAO quesdao = new QuestionDAO();

            if (quesdao.isQuestionExists(subjectId, chapterId, questionText)) {
                request.setAttribute("errorMessage", "Câu hỏi này đã tồn tại trong danh sách.");
                doGet(request, response);
                return;
            }

            Question question = new Question();
            question.setSubjectId(subjectId);
            question.setChapterId(chapterId);
            question.setQuestion(questionText.trim());
            question.setQuestionTypeId(questionTypeId);

            int questionId = quesdao.addQuestion(question);
            OptionDAO opdao = new OptionDAO();

            if (questionTypeId == 1) {
                for (int i = 1; i <= 4; i++) {
                    String optionText = request.getParameter("optionText" + i);
                    boolean isCorrect = request.getParameter("isCorrect" + i) != null;

                    Option option = new Option();
                    option.setQuestionID(questionId);
                    option.setOptionText(optionText.trim());
                    option.setIsCorrect(isCorrect ? 1 : 0);
                    opdao.addOption(option);
                }
            } else if (questionTypeId == 2) {
                String optionText = request.getParameter("optionText");
                Option option = new Option();
                option.setQuestionID(questionId);
                option.setOptionText(optionText.trim());
                option.setIsCorrect(1);
                opdao.addOption(option);
            }

            response.sendRedirect(request.getContextPath() + "/questionlist");

        } catch (NumberFormatException e) {
            
        } catch (Exception e) {
            e.printStackTrace();
            
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
