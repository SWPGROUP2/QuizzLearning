package controllers;

import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.User;

public class UpdateUserProfile extends HttpServlet { 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String userId = request.getParameter("id");
        UserDAO userDAO = new UserDAO();
        HttpSession session = request.getSession();
        try {
            User u = userDAO.getUserById(Integer.parseInt(userId));
            session.setAttribute("account", u);
            request.getRequestDispatcher("user-profile.jsp").forward(request, response);
        } catch (ServletException | IOException | NumberFormatException ex) {
            Logger.getLogger(UpdateUserProfile.class.getName()).log(Level.SEVERE, null, ex);
        }    
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        String userName = request.getParameter("userName");
        String phoneNumber = request.getParameter("phoneNumber");
        String dob = request.getParameter("dob");
        String id = request.getParameter("userId");
        String placeWork = request.getParameter("placeWork");
        String schoolId = request.getParameter("schoolId");
        UserDAO userDAO = new UserDAO();

        try {
            if (isBlank(fullName) || isBlank(userName) || isBlank(phoneNumber) || 
                isBlank(dob)) {
                request.setAttribute("mess", "No blank");
                request.getRequestDispatcher("user-profile.jsp").forward(request, response);
                return;
            }

            if (!isValidPhoneNumber(phoneNumber)) {
                request.setAttribute("mess", "Invalid phone number");
                request.getRequestDispatcher("user-profile.jsp").forward(request, response);
                return;
            } 
            
            if (!isValidDob(dob)) {
                request.setAttribute("mess", "You're not old enough (>15Y)");
                request.getRequestDispatcher("user-profile.jsp").forward(request, response);
                return;
            } else {
                userDAO.updateUserById(fullName, userName, phoneNumber, dob, Integer.parseInt(id), placeWork, schoolId);              
            }
            
            if (userDAO.getUserById(Integer.parseInt(id)).getRoleId() == 1) {
                response.sendRedirect("studenthome");
            } else if (userDAO.getUserById(Integer.parseInt(id)).getRoleId() == 3) {
                response.sendRedirect("teacherhome");
            }
        } catch (ServletException | IOException | NumberFormatException ex) {
            Logger.getLogger(UpdateUserProfile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{10}");
    }

    private boolean isValidDob(String dobString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dob = dateFormat.parse(dobString);

            Date currentDate = new Date();
            long ageMilli = currentDate.getTime() - dob.getTime();
            long ageYear = ageMilli / (1000 * 60 * 60 * 24 * 365L);

            return ageYear >= 15;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
