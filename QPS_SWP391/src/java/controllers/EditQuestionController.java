package controllers;

import dal.OptionDAO;
import dal.QuestionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Option;
import models.Question;

@WebServlet(name = "EditQuestionController", urlPatterns = {"/editquestion"})
public class EditQuestionController extends HttpServlet {

    QuestionDAO qsdao = new QuestionDAO();
    OptionDAO opdao = new OptionDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String questionIdStr = request.getParameter("questionId");
            int questionId = Integer.parseInt(questionIdStr);
            Question question = qsdao.getQuestionById(questionId);

            List<Option> options = opdao.getOptionsByQuestionId(questionId);
            request.setAttribute("question", question);
            request.setAttribute("options", options);
            request.setAttribute("matchingOption", options.get(0));
            request.setAttribute("subjectId", question.getSubjectId());
            request.getRequestDispatcher("editquestion.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String questionIdStr = request.getParameter("questionId");
        String subjectIdStr = request.getParameter("subjectId");
        String chapterIdStr = request.getParameter("chapterId");
        String questionText = request.getParameter("question");
        String questionTypeIdStr = request.getParameter("questionTypeId");

        int questionId = Integer.parseInt(questionIdStr);
        int subjectId = Integer.parseInt(subjectIdStr);
        int chapterId = Integer.parseInt(chapterIdStr);
        int questionTypeId = Integer.parseInt(questionTypeIdStr);

        Question question = new Question();
        question.setQuestionID(questionId);
        question.setSubjectId(subjectId);
        question.setChapterId(chapterId);
        question.setQuestion(questionText);
        question.setQuestionTypeId(questionTypeId);

        qsdao.updateQuestion(question);

        try {
            if (questionTypeId == 1) {
                for (int i = 1; i <= 4; i++) {
                    String optionText = request.getParameter("optionText" + i);
                    String optionIdStr = request.getParameter("optionId" + i);
                    int isCorrect = request.getParameter("isCorrect" + i) != null ? 1 : 0;

                    Option option = new Option();
                    option.setOptionId(Integer.parseInt(optionIdStr));
                    option.setQuestionID(questionId);
                    option.setOptionText(optionText);
                    option.setIsCorrect(isCorrect);
                    opdao.updateOption(option);

                }
            } else if (questionTypeId == 2) {
                String matchingOptionText = request.getParameter("optionText");
                String matchingOptionIdStr = request.getParameter("matchingOptionId");

                Option matchingOption = new Option();
                matchingOption.setQuestionID(questionId);
                matchingOption.setOptionText(matchingOptionText);
                matchingOption.setIsCorrect(1);

                matchingOption.setOptionId(Integer.parseInt(matchingOptionIdStr));
                opdao.updateOption(matchingOption);

            }

            response.sendRedirect("questionlist?id=" + subjectId);

        } catch (Exception e) {

            request.getRequestDispatcher("editquestion.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "EditQuestionController handles editing of questions and their options";
    }
}
