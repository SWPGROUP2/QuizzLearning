package controllers;

import dal.TermSetDAO;
import models.TermSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/UpdateTermSetController")
public class UpdateTermSetServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy termSetId từ tham số URL
        String termSetIdStr = request.getParameter("id");
        if (termSetIdStr != null) {
            try {
                int termSetId = Integer.parseInt(termSetIdStr);

                // Lấy TermSet hiện tại từ cơ sở dữ liệu
                TermSetDAO termSetDAO = new TermSetDAO();
                TermSet termSet = termSetDAO.getTermSetById(termSetId);

                // Gửi đối tượng termSet đến form cập nhật
                request.setAttribute("termSet", termSet);
                request.getRequestDispatcher("updatetermset.jsp").forward(request, response);

            } catch (NumberFormatException e) {
                response.sendRedirect("termsetlist.jsp?error=Invalid TermSet ID");
            }
        } else {
            response.sendRedirect("termsetlist.jsp?error=Missing TermSet ID");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy dữ liệu từ form
        String termSetIdStr = request.getParameter("termSetId");
        String termSetName = request.getParameter("termSetName");
        String termSetDescription = request.getParameter("termSetDescription");

        if (termSetIdStr != null && termSetName != null && termSetDescription != null) {
            try {
                int termSetId = Integer.parseInt(termSetIdStr);

                // Tạo đối tượng TermSet với dữ liệu đã nhận
                TermSet termSet = new TermSet(termSetId, termSetName, termSetDescription);

                // Cập nhật TermSet trong cơ sở dữ liệu
                TermSetDAO termSetDAO = new TermSetDAO();
                termSetDAO.updateTermSet(termSet);

                // Sau khi cập nhật, chuyển hướng về trang danh sách TermSets
                response.sendRedirect("termset");

            } catch (NumberFormatException e) {
                response.sendRedirect("termsetlist.jsp?error=Invalid TermSet ID");
            }
        } else {
            response.sendRedirect("termsetlist.jsp?error=Missing TermSet data");
        }
    }
}