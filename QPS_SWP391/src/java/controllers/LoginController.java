package controllers;

import dal.ClassDAO;
import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import models.User;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name="LoginController", urlPatterns={"/login"})
public class LoginController extends HttpServlet {

    public LoginController() {
        super();
    }

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
            User user = userDao.getUserWithStatus(email, password);
            if (user != null) {
                String status = user.getStatus();

                if ("Inactive".equals(status)) {
                    String error = "Your account is currently inactive.";
                    request.setAttribute("email", email);
                    request.setAttribute("error", error);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
                ClassDAO classDAO = new ClassDAO();
                String className = classDAO.getClassNameById(user.getClassId());
                user.setClassName(className); 

                int roleId = user.getRoleId();
                int id = user.getUserId();
                session.setAttribute("account", user);
                session.setAttribute("user_id", id);
                session.setAttribute("role_id", roleId);

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
        return "Login Controller";
        
    }
}
