package controllers;

import dal.OptionDAO;
import dal.QuestionDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Option;
import models.Question;

public class AddQuestionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String subjectIdStr = request.getParameter("subjectId");
        request.setAttribute("subjectId", subjectIdStr);
        request.getRequestDispatcher("addquestion.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String subjectIdStr = request.getParameter("subjectId");
        String chapterIdStr = request.getParameter("chapterId");
        String questionText = request.getParameter("question");
        String questionTypeIdStr = request.getParameter("questionTypeId");

        boolean isValid = true;

        int subjectId = Integer.parseInt(subjectIdStr);

        int chapterId = Integer.parseInt(chapterIdStr);

        int questionTypeId = Integer.parseInt(questionTypeIdStr);

        if (questionText == null || questionText.trim().isEmpty()) {
            isValid = false;
            request.setAttribute("questionError", "Câu hỏi không được để trống.");
        }

        if (questionTypeId == 1) {
            for (int i = 1; i <= 4; i++) {
                String optionText = request.getParameter("optionText" + i);
                if (optionText == null || optionText.trim().isEmpty()) {
                    isValid = false;
                    request.setAttribute("optionError" + i, "Tùy chọn " + i + " không được để trống.");
                }
            }
        } else if (questionTypeId == 2) {
            String optionText = request.getParameter("optionText");
            if (optionText == null || optionText.trim().isEmpty()) {
                isValid = false;
                request.setAttribute("optionError", "Tùy chọn không được để trống.");
            }
        }

        if (!isValid) {
            request.setAttribute("subjectId", subjectId);
            request.setAttribute("chapterId", chapterId);
            request.setAttribute("question", questionText);
            request.setAttribute("questionTypeId", questionTypeId);
            request.getRequestDispatcher("addquestion.jsp").forward(request, response);
            return;
        }

        Question question = new Question();
        question.setSubjectId(subjectId);
        question.setChapterId(chapterId);
        question.setQuestion(questionText);
        question.setQuestionTypeId(questionTypeId);

        QuestionDAO quesdao = new QuestionDAO();
        OptionDAO opdao = new OptionDAO();
        int questionId = quesdao.addQuestion(question);

        if (questionTypeId == 1) { 
            for (int i = 1; i <= 4; i++) {
                String optionText = request.getParameter("optionText" + i);
                boolean isCorrect = request.getParameter("isCorrect" + i) != null;

                if (optionText != null && !optionText.trim().isEmpty()) {
                    Option option = new Option();
                    option.setQuestionID(questionId);
                    option.setOptionText(optionText);
                    option.setIsCorrect(isCorrect ? 1 : 0);
                    opdao.addOption(option);
                }
            }
        } else if (questionTypeId == 2) {
            String optionText = request.getParameter("optionText");

            Option option = new Option();
            option.setQuestionID(questionId);
            option.setOptionText(optionText);
            option.setIsCorrect(1); 
            opdao.addOption(option);
        }

        response.sendRedirect("questionlist?id=" + subjectId);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for adding questions";
    }
}
