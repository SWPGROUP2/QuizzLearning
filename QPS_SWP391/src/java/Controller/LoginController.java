package Controller;

import dal.ClassDAO;
import dal.TermDAO;
import dal.TermSetDAO;
import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Classes;
import models.TermSet;

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
            if (userDao.getUser(email, password) != null) {
                String role = userDao.getUser(email, password).getRole();
                int id = userDao.getUser(email, password).getUserId();
                session.setAttribute("account", userDao.getUser(email, password));
                session.setAttribute("user_id", id);

                if (role.equalsIgnoreCase("teacher")) {
                    response.sendRedirect("teacherhome");
//                    response.sendRedirect("teacher/teacher-home-page.jsp");
                
                } else if (role.equalsIgnoreCase("admin")) {
                    response.sendRedirect("admin/admin-user-list");
                } else {
                    TermSetDAO tDao = new TermSetDAO();                   
                    ClassDAO classDao = new ClassDAO();
                    List<Classes> c = classDao.getTop3ClassesByUserID(id);
                    session.setAttribute("classList", c);
                    List<TermSet> listTermSet = tDao.getTop3TermSets(id);

                    session.setAttribute("listTermSet", listTermSet);
                    response.sendRedirect("homes");
                }
            } else {
                String error = "Wrong email or password!!!";
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