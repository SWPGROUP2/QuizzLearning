package controllers;

import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.User;

@WebServlet(name="LoginController", urlPatterns={"/login"})


public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    HttpSession session = request.getSession();
    UserDAO userDao = new UserDAO();

    String email = request.getParameter("email");
    String password = request.getParameter("password");
    session.setMaxInactiveInterval(1800);

    try {
        User user = userDao.getUser(email, password);
        if (user != null) {
            // Check start date and end date
            String status = user.getStatus();
            LocalDate currentDate = LocalDate.now();
            Date startDate = user.getStartDate();
            Date endDate = user.getEndDate();
            
            // Validate active status based on dates
            boolean isActive = "Active".equalsIgnoreCase(status) &&
                    (startDate == null || !currentDate.isBefore(startDate.toLocalDate())) &&
                    (endDate == null || !currentDate.isAfter(endDate.toLocalDate()));

            if (!isActive) {
                String error = "Your account is currently inactive.";
                request.setAttribute("email", email);
                request.setAttribute("error", error);
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }

            // Proceed with login if user is active
            int roleId = user.getRoleId();
            int id = user.getUserId();
            session.setAttribute("account", user);
            session.setAttribute("user_id", id);
            session.setAttribute("role_id", roleId);

            // Redirect based on roleId
            if (roleId == 3) {
                response.sendRedirect("teacherhome");
            } else if (roleId == 2) {
                response.sendRedirect("adminhome");
            } else {
                response.sendRedirect("studenthome");
            }
        } else {
            String error = "Wrong email or password!!!";
            request.setAttribute("email", email);
            request.setAttribute("error", error);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    } catch (Exception ex) {
        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
    }
}


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}