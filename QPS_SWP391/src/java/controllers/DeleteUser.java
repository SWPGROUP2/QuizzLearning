package controllers;

import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeleteUser extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy subjectId từ request
        String userIdParam = request.getParameter("userId");
        UserDAO dao = new UserDAO();
        if (userIdParam != null && !userIdParam.isEmpty()) {
            int userId = Integer.parseInt(userIdParam);

            dao.deleteUser(userId);
        }
        response.sendRedirect("adminhome");
    }
}

