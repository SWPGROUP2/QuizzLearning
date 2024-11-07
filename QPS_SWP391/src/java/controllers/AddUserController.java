//package controllers;
//
//import dal.UserDAO;
//import java.io.IOException;
//import java.sql.Date;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import models.User;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class AddUserController extends HttpServlet {
//
//    private static final Logger LOGGER = Logger.getLogger(AddUserController.class.getName());
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        request.getRequestDispatcher("/signup.jsp").forward(request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String mess;
//        try {
//            UserDAO dao = new UserDAO();
//
//            String fullname = request.getParameter("fullname");
//            String email = request.getParameter("email");
//            String role = request.getParameter("role");
//            String password = request.getParameter("password");
//            String phoneNumber = request.getParameter("phonenumber");
//            String place = request.getParameter("place");
//            String dateString = request.getParameter("date");
//            String userCode = request.getParameter("usercode");
//
//            //check blank space
//            if (isBlank(fullname) || isBlank(email) || isBlank(password)
//                    || isBlank(phoneNumber) || isBlank(place) || isBlank(userCode)
//                    || isBlank(dateString)) {
//                mess = "All fields are required and cannot be blank or spaces.";
//                request.setAttribute("mess", mess);
//                request.getRequestDispatcher("/adduser.jsp").forward(request, response);
//                return;
//            }
//
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            java.util.Date utilDate;
//            try {
//                utilDate = sdf.parse(dateString);
//            } catch (ParseException e) {
//                LOGGER.log(Level.SEVERE, "Date parsing failed", e);
//                mess = "Invalid date format.";
//                request.setAttribute("mess", mess);
//                request.getRequestDispatcher("/adduser.jsp").forward(request, response);
//                return;
//            }
//
//            //check user age
//            Date sqlDate = new Date(utilDate.getTime());
//            int age = new java.util.Date().getYear() - utilDate.getYear();
//            if (age < 10) {
//                mess = "You must be at least 10 years old to sign up.";
//                request.setAttribute("mess", mess);
//                request.getRequestDispatcher("/adduser.jsp").forward(request, response);
//                return;
//            }
//            String userName = "User";
//            int roleId;
//            if (role.equals("student")) {
//                roleId = 1;
//            } else {
//                roleId = 3;
//            }
//
//            // Check email exists
//            if (dao.getUserByEmail(email) != null) {
//                mess = "Email is already in use. Please try another email.";
//                request.setAttribute("mess", mess);
//                request.getRequestDispatcher("/adduser.jsp").forward(request, response);
//            }
//            // Check phone number exists
//            if (dao.isPhoneNumberExists(phoneNumber)) {
//                mess = "Phone number is already in use. Please try another number.";
//                request.setAttribute("mess", mess);
//                request.getRequestDispatcher("/adduser.jsp").forward(request, response);
//                return;
//            }
//
//            // Check usercode exists
//
//        } catch (Exception ex) {
//            LOGGER.log(Level.SEVERE, "Add failed", ex);
//            request.setAttribute("mess", "An error occurred during sign up. Please try again.");
//            request.getRequestDispatcher("/adduser.jsp").forward(request, response);
//        }
//    }
//
//    private boolean isBlank(String value) {
//        return value == null || value.trim().isEmpty();
//    }
//
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }//
//}
