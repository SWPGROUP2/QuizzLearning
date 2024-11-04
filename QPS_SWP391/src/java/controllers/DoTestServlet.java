package controllers;

import dal.TestDAO;
import models.Question;
import models.Option;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/doTest") 
public class DoTestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int testId;

        TestDAO testDAO = new TestDAO();

        // Lấy testId từ tham số của yêu cầu
        String testIdParam = request.getParameter("testId");
        if (testIdParam != null) {
            try {
                testId = Integer.parseInt(testIdParam);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid test ID.");
                return;
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Test ID is required.");
            return;
        }

        // Lấy danh sách câu hỏi cho bài kiểm tra
        List<Question> questions = testDAO.getQuestionsByTestId2(testId);
        Map<Question, List<Option>> questionOptionsMap = new HashMap<>();
        // Lặp qua từng câu hỏi để lấy các tùy chọn
        for (Question question : questions) {
            List<Option> options = testDAO.getOptionsByQuestionId(question.getQuestionID());
            questionOptionsMap.put(question, options);
        }
        // Đặt thuộc tính vào yêu cầu để gửi đến JSP
        request.setAttribute("questionOptionsMap", questionOptionsMap);
        request.setAttribute("testId", testId);
        request.getRequestDispatcher("/doTest.jsp").forward(request, response);
    }
}
