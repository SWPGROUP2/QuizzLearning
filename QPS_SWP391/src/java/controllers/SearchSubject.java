package controllers;

import dal.subjectListDAO;
import java.util.List;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.subject;

/**
 *
 * @author dungmuahahaha
 */
public class SearchSubject extends HttpServlet {

    // Số lượng kết quả trên mỗi trang (có thể chỉnh sửa theo yêu cầu)
    private static final int PAGE_SIZE = 10;

    // Phương thức xử lý request GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");
        
        if (keyword == null || keyword.trim().isEmpty()) {
            keyword = ""; // Mặc định tìm tất cả khi không có từ khóa
        }
        
        String pageStr = request.getParameter("page");
        int page = 1; // Trang mặc định
        if (pageStr != null) {
            try {
                page = Integer.parseInt(pageStr);
            } catch (NumberFormatException e) {
                page = 1; // Nếu parse không thành công, giữ mặc định là trang 1
            }
        }

        // Gọi phương thức DAO để tìm kiếm với phân trang
        subjectListDAO dao = new subjectListDAO();
        List<subject> subjectList = dao.searchSubjectsByNameWithPaging(keyword, page, PAGE_SIZE);

        // Tính tổng số kết quả (tổng số trang để hiển thị pagination)
        int totalSubjects = dao.getTotalSubject(keyword);
        int totalPages = (int) Math.ceil((double) totalSubjects / PAGE_SIZE);
        
        request.setAttribute("listSubjectsByPagging", subjectList);
        request.setAttribute("keyword", keyword);
        request.setAttribute("page", page);
        request.setAttribute("totalPage", totalPages);

        request.getRequestDispatcher("subject.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); 
    }
}