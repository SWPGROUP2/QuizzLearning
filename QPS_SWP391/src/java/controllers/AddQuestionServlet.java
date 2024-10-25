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

        boolean hasError = false;

        request.setAttribute("subjectId", subjectIdStr);
        request.setAttribute("chapterId", chapterIdStr);
        request.setAttribute("question", questionText);
        request.setAttribute("questionTypeId", questionTypeIdStr);

        if (questionText == null || questionText.trim().isEmpty()) {
            request.setAttribute("questionError", "Question can not be empty");
            hasError = true;
        }

        int questionTypeId = Integer.parseInt(questionTypeIdStr);
        if (questionTypeId == 1) {
            for (int i = 1; i <= 4; i++) {
                String optionText = request.getParameter("optionText" + i);
                boolean isCorrect = request.getParameter("isCorrect" + i) != null;

                request.setAttribute("optionText" + i, optionText);
                request.setAttribute("isCorrect" + i, isCorrect);

                if (optionText == null || optionText.trim().isEmpty()) {
                    request.setAttribute("optionError" + i, "Option " + i + " can not be empty");
                    hasError = true;
                }
            }
        } else if (questionTypeId == 2) {
            String matchingText = request.getParameter("optionText");
            request.setAttribute("optionText", matchingText);

            if (matchingText == null || matchingText.trim().isEmpty()) {
                request.setAttribute("matchingError", "Option can not be empty");
                hasError = true;
            }
        }

        if (hasError) {
            request.getRequestDispatcher("addquestion.jsp").forward(request, response);
            return;
        }

        try {
            int subjectId = Integer.parseInt(subjectIdStr);
            int chapterId = Integer.parseInt(chapterIdStr);

            Question question = new Question();
            question.setSubjectId(subjectId);
            question.setChapterId(chapterId);
            question.setQuestion(questionText.trim());
            question.setQuestionTypeId(questionTypeId);

            QuestionDAO quesdao = new QuestionDAO();
            OptionDAO opdao = new OptionDAO();
            int questionId = quesdao.addQuestion(question);

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

            response.sendRedirect("questionlist?id=" + subjectId);

        } catch (Exception e) {
            request.getRequestDispatcher("addquestion.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for adding questions";
    }
}
