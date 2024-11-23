/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.ClassDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import models.Classes;

/**
 *
 * @author LENOVO
 */
public class ClassList extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchKeyword = request.getParameter("searchKeyword"); 
        ClassDAO classDAO = new ClassDAO();

        List<Classes> classList = classDAO.getAllClassesList(searchKeyword);

        request.setAttribute("classList", classList);
        request.setAttribute("searchKeyword", searchKeyword); 
        request.getRequestDispatcher("classlist.jsp").forward(request, response);

  }
}
