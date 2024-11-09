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

        // Get parameters from request
        String roleId = request.getParameter("roleId");
        String status = request.getParameter("status");
        String className = request.getParameter("classname");
        String fullNameSearch = request.getParameter("fullName");
        String pageParam = request.getParameter("page");

        // Set current page and number of users per page for pagination
        int currentPage = (pageParam != null) ? Integer.parseInt(pageParam) : 1;
        int usersPerPage = 10;

        // Get the filtered list of users from DAO
        List<User> userList = adminDAO.getFilteredUsers(roleId, status, className, currentPage, usersPerPage, fullNameSearch);

        // Use a Map to hold unique className -> classId
        Map<String, Integer> uniqueClassesMap = new LinkedHashMap<>();
        
        // Loop through the users and add their className and classId to the map
        for (User user : userList) {
            String userClassName = user.getClassName();
            Integer userClassId = user.getClassId();

            // Ensure that only non-null and non-empty class names are added
            if (userClassName != null && !userClassName.trim().isEmpty()) {
                uniqueClassesMap.put(userClassName, userClassId);
            }
        }

        // Set the attributes for the JSP page
        request.setAttribute("uniqueClassesMap", uniqueClassesMap);
        request.setAttribute("userList", userList);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("fullNameSearch", fullNameSearch);

        // Forward the request to the JSP page
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
