package controllers;

import dal.TermSetDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.TermSet;
import java.io.IOException;
import java.util.List;

public class TermSetController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("user_id");
        
        TermSetDAO termSetDAO = new TermSetDAO();
        List<TermSet> termSets = termSetDAO.getAllTermSetsByUser(userId);

        

        request.setAttribute("termSets", termSets);
        request.getRequestDispatcher("termsetlist.jsp").forward(request, response);
    }
}

