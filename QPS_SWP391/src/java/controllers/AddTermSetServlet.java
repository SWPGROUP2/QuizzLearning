package controllers;

import dal.TermSetDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class AddTermSetServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy dữ liệu từ form
        String termSetName = request.getParameter("termSetName");
        String termSetDescription = request.getParameter("termSetDescription");
        
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("user_id");

        // Gọi DAO để thêm TermSet
        TermSetDAO termSetDAO = new TermSetDAO();
        termSetDAO.addTermSet(termSetName, termSetDescription, userId);

        // Chuyển hướng về trang danh sách TermSet
        response.sendRedirect("termset");
    }
}