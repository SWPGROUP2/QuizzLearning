package controllers;

import dal.TermSetDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.TermSet;
import java.io.IOException;
import java.util.List;

public class TermSetController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TermSetDAO termSetDAO = new TermSetDAO();
        List<TermSet> termSets = termSetDAO.getAllTermSets();

        // Check if termSets is empty and log it
        System.out.println("Term sets in controller: " + termSets.size());

        request.setAttribute("termSets", termSets);
        request.getRequestDispatcher("termsetlist.jsp").forward(request, response);
    }
}

