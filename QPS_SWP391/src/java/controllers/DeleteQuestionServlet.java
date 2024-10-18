package controllers;

import dal.QuestionDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author LENOVO
 */

@WebServlet("/DeleteQuestionServlet")
public class DeleteQuestionServlet extends HttpServlet {
 @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String subjectIdStr = request.getParameter("subjectId");
        
            int questionID = Integer.parseInt(idParam);
            int subjectId = Integer.parseInt(subjectIdStr);
            QuestionDAO dao = new QuestionDAO();
            dao.deleteQuestion(questionID);                    
            response.sendRedirect("questionlist?id=" + subjectId);          
    }
 @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response); // Delegate to doPost for deletion logic
    }
     @Override
    public String getServletInfo() {
        return "Servlet for adding questions";
    }

}

