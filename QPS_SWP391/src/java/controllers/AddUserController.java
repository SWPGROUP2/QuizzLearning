package controllers;

import dal.ClassDAO;
import dal.UserDAO;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import models.User;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Classes;

public class AddUserController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(AddUserController.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ClassDAO classdao = new ClassDAO();
        List<Classes> allclass = classdao.getUniqueClasses();

        request.setAttribute("allclass", allclass);
        request.getRequestDispatcher("adduser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            UserDAO dao = new UserDAO();
            ClassDAO classdao = new ClassDAO();

            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String role = request.getParameter("role");
            String password = request.getParameter("password");
            String phoneNumber = request.getParameter("phonenumber");
            String userName = request.getParameter("userName");
            String userCode = request.getParameter("usercode");
            String dateString = request.getParameter("date");
            String startDateString = request.getParameter("startDate");
            String endDateString = request.getParameter("endDate");
            String classIdStr = request.getParameter("classId");

            boolean hasError = false;

            request.setAttribute("fullName", fullName);
            request.setAttribute("email", email);
            request.setAttribute("role", role);
            request.setAttribute("password", password);
            request.setAttribute("phoneNumber", phoneNumber);
            request.setAttribute("userName", userName);
            request.setAttribute("userCode", userCode);
            request.setAttribute("date", dateString);
            request.setAttribute("startDate", startDateString);
            request.setAttribute("endDate", endDateString);
            request.setAttribute("classId", classIdStr);

            if (isBlank(fullName)) {
                request.setAttribute("fullNameError", "Họ và tên không được để trống");
                hasError = true;
            }

            if (isBlank(email)) {
                request.setAttribute("emailError", "Email không được để trống");
                hasError = true;
            } else if (dao.getUserByEmail(email) != null) {
                request.setAttribute("emailError", "Email đã được sử dụng");
                hasError = true;
            }

            if (isBlank(password)) {
                request.setAttribute("passwordError", "Mật khẩu không được để trống");
                hasError = true;
            }

            if (isBlank(phoneNumber)) {
                request.setAttribute("phoneError", "Số điện thoại không được để trống");
                hasError = true;
            } else if (!phoneNumber.matches("\\d{10}")) {
                request.setAttribute("phoneError", "Số điện thoại phải có 10 chữ số");
                hasError = true;
            } else if (dao.isPhoneNumberExists(phoneNumber)) {
                request.setAttribute("phoneError", "Số điện thoại đã được sử dụng");
                hasError = true;
            }

       

            if (isBlank(userCode)) {
                request.setAttribute("userCodeError", "Mã học sinh không được để trống");
                hasError = true;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (!isBlank(dateString)) {
                try {
                    java.util.Date utilDate = sdf.parse(dateString);
                    int age = new java.util.Date().getYear() - utilDate.getYear();
                    if (age < 10) {
                        request.setAttribute("dateError", "Tuổi phải lớn hơn 10");
                        hasError = true;
                    }
                } catch (ParseException e) {
                    request.setAttribute("dateError", "Ngày không hợp lệ");
                    hasError = true;
                }
            } else {
                request.setAttribute("dateError", "Ngày sinh không được để trống");
                hasError = true;
            }

            if (hasError) {
                List<Classes> allclass = classdao.getUniqueClasses();
                request.setAttribute("allclass", allclass);
                request.getRequestDispatcher("adduser.jsp").forward(request, response);
                return;
            }

            java.util.Date utilDate = sdf.parse(dateString);
            Date sqlDate = new Date(utilDate.getTime());

            Date startDate = null;
            Date endDate = null;
            if (!isBlank(startDateString)) {
                startDate = new Date(sdf.parse(startDateString).getTime());
            }
            if (!isBlank(endDateString)) {
                endDate = new Date(sdf.parse(endDateString).getTime());
            }

            int roleId = role.equals("student") ? 1 : 3;
            int classId = Integer.parseInt(classIdStr);

            User newUser = new User();
            newUser.setFullName(fullName);
            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.setPhoneNumber(phoneNumber);
            newUser.setUserName(userName);
            newUser.setUserCode(userCode);
            newUser.setDob(sqlDate);
            newUser.setStartDate(startDate);
            newUser.setEndDate(endDate);
            newUser.setRoleId(roleId);
            newUser.setStatus("Active");

            if (dao.addUser(newUser)) {
                classdao.addUserToClass(newUser.getUserId(), classId);
                response.sendRedirect("adminhome");
            } else {
                request.setAttribute("errorMessage", "Thêm người dùng thất bại. Vui lòng thử lại.");
                request.getRequestDispatcher("adduser.jsp").forward(request, response);
            }

        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Đã xảy ra lỗi. Vui lòng thử lại.");
            request.getRequestDispatcher("adduser.jsp").forward(request, response);
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }//
}
