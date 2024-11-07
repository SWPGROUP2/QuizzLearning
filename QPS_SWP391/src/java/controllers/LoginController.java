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
        // Fetch the user only once
        User user = userDao.getUser(email, password);
        if (user != null) {
            int roleId = user.getRoleId(); // Fetch the roleId
            String role = user.getRole();
            int id = user.getUserId();

            // Store user data in session
            session.setAttribute("account", user);
            session.setAttribute("user_id", id);
            session.setAttribute("role_id", roleId); // Add roleId to session if needed

            // Redirect based on roleId
            if (roleId == 3) { // Teacher
                response.sendRedirect("teacherhome");
            } else if (roleId == 2) { // Admin
                response.sendRedirect("adminhome");
            } else { // Assume Student or other roles
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