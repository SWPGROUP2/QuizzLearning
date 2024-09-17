package controllers;

import dal.UserDAO;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignUpController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(SignUpController.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/signup.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String mess;
        try {
            UserDAO dao = new UserDAO();

            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String role = request.getParameter("role");
            String phoneNumber = request.getParameter("phoneNumber");
            String place = request.getParameter("place");
            String dateString = request.getParameter("date");
            String userCode = request.getParameter("usercode");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate;
            try {
                utilDate = sdf.parse(dateString);
            } catch (ParseException e) {
                LOGGER.log(Level.SEVERE, "Date parsing failed", e);
                mess = "Invalid date format.";
                request.setAttribute("mess", mess);
                request.getRequestDispatcher("/signup.jsp").forward(request, response);
                return;
            }

            Date sqlDate = new Date(utilDate.getTime());
            int roleId = "student".equals(role) ? 1 : 3;

            // Check if email already exists
            if (dao.getUserByEmail(email) != null) {
                mess = "Email is already in use. Please try another email.";
                request.setAttribute("mess", mess);
                request.getRequestDispatcher("/signup.jsp").forward(request, response);
            } else {
                // Create and add the user
                User user = new User(username, roleId, email, password, role, phoneNumber, sqlDate, place, userCode);
                dao.addUser(user);
                mess = "Sign Up Successful! You can now log in.";
                request.setAttribute("mess", mess);
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Sign up failed", ex);
            request.setAttribute("mess", "An error occurred during sign up. Please try again.");
            request.getRequestDispatcher("/signup.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Handles user sign up.";
    }
}
