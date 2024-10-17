package controllers;

import dal.ChapterDAO;
import models.Chapter;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ChapterListController extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
         String subjectId = request.getParameter("id");
        
        try {
            ChapterDAO chapterDAO = new ChapterDAO();
            List<Chapter> chapterList = chapterDAO.getChaptersBySubjectId(Integer.parseInt(subjectId));
            // Gán danh sách câu hỏi vào request attribute để gửi về trang JSP
            request.setAttribute("chapterList", chapterList);
            // Chuyển hướng đến trang JSP để hiển thị danh sách câu hỏi
            request.getRequestDispatcher("chapterlist.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}