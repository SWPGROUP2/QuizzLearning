package controllers;

import dal.AdminDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import models.User;

public class AdminHomePageController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        AdminDAO adminDAO = new AdminDAO();

        String roleId = request.getParameter("roleId");
        String status = request.getParameter("status");
        String className = request.getParameter("classname");
        String fullNameSearch = request.getParameter("fullName");
        String pageParam = request.getParameter("page");

        int currentPage = (pageParam != null) ? Integer.parseInt(pageParam) : 1;
        int usersPerPage = 10;

        List<User> userList = adminDAO.getFilteredUsers(roleId, status, className, currentPage, usersPerPage, fullNameSearch);

        Map<String, Integer> uniqueClassesMap = new LinkedHashMap<>();

        for (User user : userList) {
            String userClassName = user.getClassName();
            Integer userClassId = user.getClassId();
            if (userClassName != null && !userClassName.trim().isEmpty()) {
                uniqueClassesMap.put(userClassName, userClassId);
            }
        }

        request.setAttribute("uniqueClassesMap", uniqueClassesMap);
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
