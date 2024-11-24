package controllers;

import dal.ClassDAO;
import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Classes;
import models.User;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateUserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userIdStr = request.getParameter("userId");
        int userId = Integer.parseInt(userIdStr);

        UserDAO userdao = new UserDAO();
        ClassDAO classdao = new ClassDAO();

        User user = userdao.getUserById(userId);
        List<Classes> allClasses = classdao.getAllClasses();

        request.setAttribute("user", user);
        request.setAttribute("allclass", allClasses);

        request.getRequestDispatcher("updateuser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            UserDAO dao = new UserDAO();
            ClassDAO classdao = new ClassDAO();

            int userId = Integer.parseInt(request.getParameter("userId"));
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String phoneNumber = request.getParameter("phonenumber");
            String dateString = request.getParameter("date");
            String classIdStr = request.getParameter("classId");
            String userName = request.getParameter("userName");
            String startDateString = request.getParameter("startDate");
            String endDateString = request.getParameter("endDate");
            String status = request.getParameter("status");
            String role = request.getParameter("role");

            boolean hasError = false;

            if (isBlank(fullName)) {
                request.setAttribute("fullNameError", "Họ và tên không được để trống");
                hasError = true;
            }

            if (isBlank(phoneNumber) || !phoneNumber.matches("\\d{10}")) {
                request.setAttribute("phoneError", "Số điện thoại phải có 10 chữ số");
                hasError = true;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dob = null;
            Date startDate = null;
            Date endDate = null;

            try {
                if (!isBlank(dateString)) {
                    dob = new Date(sdf.parse(dateString).getTime());
                    int age = new java.util.Date().getYear() - dob.getYear();
                    if (age < 10) {
                        request.setAttribute("dateError", "Tuổi phải lớn hơn 10");
                        hasError = true;
                    }
                } else {
                    request.setAttribute("dateError", "Ngày sinh không được để trống");
                    hasError = true;
                }

                if (!isBlank(startDateString)) {
                    startDate = new Date(sdf.parse(startDateString).getTime());
                }

                if (!isBlank(endDateString)) {
                    endDate = new Date(sdf.parse(endDateString).getTime());
                }
            } catch (ParseException e) {
                request.setAttribute("dateError", "Ngày không hợp lệ");
                hasError = true;
            }

            if (hasError) {
                User user = dao.getUserById(userId);
                List<Classes> allClasses = classdao.getAllClasses();

                request.setAttribute("user", user);
                request.setAttribute("allclass", allClasses);

                request.getRequestDispatcher("updateuser.jsp").forward(request, response);
                return;
            }

            User updatedUser = new User();
            updatedUser.setUserId(userId);
            updatedUser.setFullName(fullName);
            updatedUser.setEmail(email);
            updatedUser.setPhoneNumber(phoneNumber);
            updatedUser.setDob(dob);
            updatedUser.setClassId(Integer.parseInt(classIdStr));
            updatedUser.setUserName(userName);
            updatedUser.setStartDate(startDate);
            updatedUser.setEndDate(endDate);
            updatedUser.setStatus(status);
            updatedUser.setRoleId(role.equals("student") ? 1 : 3);

            dao.updateUser(updatedUser);
            response.sendRedirect("adminhome");

        } catch (Exception ex) {

        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
