package controllers;

import dal.AdminDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;
import utils.ParseUtils;

public class AdminHomePageController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        AdminDAO adminDAO = new AdminDAO();
        
        String roleId = request.getParameter("roleId");
        String status = request.getParameter("status");
        String classId = request.getParameter("classId");
        String fullNameSearch = request.getParameter("fullName");
        String pageParam = request.getParameter("page");
        
        int currentPage = (pageParam != null) ? Integer.parseInt(pageParam) : 1;
        int usersPerPage = 10;  

        List<User> userList = adminDAO.getFilteredUsers(roleId, status, classId, currentPage, usersPerPage, fullNameSearch);
        
        request.setAttribute("userList", userList);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("fullNameSearch", fullNameSearch);

        request.getRequestDispatcher("adminhome.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "AdminHomePageController handles filtering and displaying users.";
    }
}
