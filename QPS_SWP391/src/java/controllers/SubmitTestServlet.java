package controllers;

import dal.TestDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;

public class SubmitTestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user_id");

        if (userId == null) {
            response.sendRedirect("login");
            return;
        }

        TestDAO testDAO = new TestDAO();

        String testIdStr = request.getParameter("testId");
        if (testIdStr == null) {
            throw new ServletException("testId không có trong request");
        }

        int testId;
        try {
            testId = Integer.parseInt(testIdStr);
        } catch (NumberFormatException e) {
            throw new ServletException("Không thể chuyển đổi testId thành số nguyên", e);
        }

        float totalQuestions = 0;
        float correctAnswers = 0;
        Enumeration<String> parameterNames = request.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            if (paramName.startsWith("question_")) {
                totalQuestions++;
                int questionId = Integer.parseInt(paramName.split("_")[1]);
                String answerType = testDAO.getQuestionType(questionId); // Lấy loại câu hỏi từ DB

                if ("Multiple-choice".equalsIgnoreCase(answerType)) {
                    // Xử lý câu hỏi dạng multiple-choice
                    int selectedOptionId = Integer.parseInt(request.getParameter(paramName));
                    if (testDAO.isCorrectOption(questionId, selectedOptionId)) {
                        correctAnswers++;
                    }
                } else if ("Short-answer".equalsIgnoreCase(answerType)) {
                    // Xử lý câu hỏi dạng short-answer
                    String studentAnswer = request.getParameter(paramName).trim();
                    String correctAnswer = testDAO.getCorrectAnswerForShortAnswer(questionId);
                    if (studentAnswer.equalsIgnoreCase(correctAnswer)) {
                        correctAnswers++;
                    }
                }
            }
        }

        double score = (correctAnswers / totalQuestions) * 10;

        try {
            testDAO.saveTestResult(userId, testId, score);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Lỗi lưu kết quả vào cơ sở dữ liệu");
        }

        request.setAttribute("totalQuestions", totalQuestions);
        request.setAttribute("correctAnswers", correctAnswers);
        request.setAttribute("score", score);
        request.getRequestDispatcher("/testResult.jsp").forward(request, response);
    }
}