package controllers;

import dal.TermSetDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteTermSetServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy termSetId từ tham số URL
        String termSetIdStr = request.getParameter("id");
        if (termSetIdStr != null) {
            try {
                int termSetId = Integer.parseInt(termSetIdStr);

                // Tạo đối tượng DAO để xóa termSet
                TermSetDAO termSetDAO = new TermSetDAO();
                termSetDAO.deleteTermSet(termSetId);

                // Sau khi xóa, chuyển hướng về trang danh sách term sets
                response.sendRedirect("termset");

            } catch (NumberFormatException e) {
                // Nếu id không hợp lệ, chuyển hướng về trang danh sách với thông báo lỗi
                response.sendRedirect("termsetlist.jsp?error=Invalid TermSet ID");
            }
        } else {
            // Nếu không có id trong URL, chuyển hướng về trang danh sách
            response.sendRedirect("termsetlist.jsp?error=Missing TermSet ID");
        }
    }
}