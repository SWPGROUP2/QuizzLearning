/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;
        
import dal.OptionDAO;
import dal.QuestionDAO;
import models.Question;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Option;


public class DetailQuestions extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int questionId = Integer.parseInt(request.getParameter("questionId"));
        
        QuestionDAO questionDAO = new QuestionDAO();
        Question question = null;

        question = questionDAO.getQuestionById(questionId);



        OptionDAO optionDAO = new OptionDAO();
        List<Option> options = optionDAO.getOptionsByQuestionId(questionId);
        request.setAttribute("question", question);
        request.setAttribute("options", options);
        request.getRequestDispatcher("detailquestion.jsp").forward(request, response);
        
    }

    @Override
    public String getServletInfo() {
        return "Short skdjajkdasjk";
    }
}




