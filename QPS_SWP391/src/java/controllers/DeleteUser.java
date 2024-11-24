package controllers;

import static dal.DBContext.LOGGER;
import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;

public class DeleteUser extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userIdParam = request.getParameter("userId");

        if (userIdParam != null && !userIdParam.isEmpty()) {
            try {
                int userId = Integer.parseInt(userIdParam);
                UserDAO dao = new UserDAO();

                if (dao.deleteUser(userId)) {
                    response.sendRedirect("adminhome");
                } else {
                    request.setAttribute("errorMessage", "Failed to delete user. User may have related records.");
                    request.getRequestDispatcher("adminhome").forward(request, response);
                }
            } catch (NumberFormatException e) {
                LOGGER.log(Level.SEVERE, "Invalid user ID format", e);
                request.setAttribute("errorMessage", "Invalid user ID format");
                request.getRequestDispatcher("adminhome").forward(request, response);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error while deleting user", e);
                request.setAttribute("errorMessage", "An error occurred while deleting the user");
                request.getRequestDispatcher("adminhome").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", "User ID is required");
            request.getRequestDispatcher("adminhome").forward(request, response);
        }
    }
}
