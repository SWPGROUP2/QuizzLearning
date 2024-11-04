package controllers;

import dal.TestDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;


public class SubmitTestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TestDAO testDAO = new TestDAO();
        int totalQuestions = 0;
        int correctAnswers = 0;

        Enumeration<String> parameterNames = request.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            if (paramName.startsWith("question_")) {
                totalQuestions++;
                int questionId = Integer.parseInt(paramName.split("_")[1]);
                int selectedOptionId = Integer.parseInt(request.getParameter(paramName));

                try {
                    if (testDAO.isCorrectOption(questionId, selectedOptionId)) {
                        correctAnswers++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing the answers.");
                    return;
                }
            }
        }

        double score = (double) correctAnswers / totalQuestions * 10;
        request.setAttribute("totalQuestions", totalQuestions);
        request.setAttribute("correctAnswers", correctAnswers);
        request.setAttribute("score", score);

        request.getRequestDispatcher("/testResult.jsp").forward(request, response);
    }
}