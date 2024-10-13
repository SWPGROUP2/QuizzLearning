package controllers;

import dal.QuestionDAO;
import models.Question;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class QuestionList extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String subjectId = request.getParameter("id");
        

        try {
            QuestionDAO questionDAO = new QuestionDAO();
            List<Question> questionList = questionDAO.getQuestionsBySubjectId(Integer.parseInt(subjectId));

            // Gán danh sách câu hỏi vào request attribute để gửi về trang JSP
            request.setAttribute("questionList", questionList);

            // Chuyển hướng đến trang JSP để hiển thị danh sách câu hỏi
            request.getRequestDispatcher("question.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}