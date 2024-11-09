<%-- 
    Document   : assignteacher
    Created on : Nov 8, 2024, 6:11:44 AM
    Author     : dell
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="models.*" %>
<%@page import="dal.*" %>


<%
    String subjectIdParam = request.getParameter("subjectId");
    subject subject = null;
    if (subjectIdParam != null) {
        try {
            int subjectId = Integer.parseInt(subjectIdParam);
            // Giả sử subjectDAO đã được khởi tạo
            SubjectDAO subjectDAO = new SubjectDAO();
            subject = subjectDAO.getSubjectById(subjectId);
            request.setAttribute("subject", subject);
        } catch (NumberFormatException e) {
            // Xử lý lỗi khi subjectId không hợp lệ
            e.printStackTrace();
        }
    }
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Assign Teacher to Subject</title>
        <style>
            /* Bố cục chính */
            body {
                font-family: Arial, sans-serif;
                display: flex;
                min-height: 100vh;
                margin: 0;
            }
            .content {
                flex: 1;
                padding: 20px;
                background-color: #f4f4f4;
            }
            .form-container {
                background-color: white;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }

            .form-container h1 {
                font-size: 24px;
                margin-bottom: 20px;
            }

            .form-container div {
                margin-bottom: 10px;
            }

            .form-container label {
                margin-left: 10px;
            }

            .form-container button {
                background-color: #007bff;
                color: white;
                border: none;
                padding: 10px 20px;
                border-radius: 4px;
                cursor: pointer;
                font-size: 16px;
            }

            .form-container button:hover {
                background-color: #0056b3;
            }

            .btn-secondary {
                background-color: #6c757d;
                color: white;
                padding: 10px 20px;
                border-radius: 4px;
                text-decoration: none;
            }

            .btn-secondary:hover {
                background-color: #5a6268;
            }
        </style>
    </head>
    <body>

        <!-- Sidebar -->
        <div class="col-md-2 p-0"">
            <%@include file="Components/Sidebar.jsp" %>
        </div>

        <!-- Content -->
        <div class="content">
            <div class="form-container">
                <h1>Assign Teacher to Subject: ${subject.getSubjectName()}</h1>
                <form action="assign-teacher" method="POST">
                    <input type="hidden" name="subjectId" value="${subject.getSubjectId()}" />

                    <c:forEach var="teacher" items="${teachers}">
                        <div>
                            <input type="checkbox" name="teacherIds" value="${teacher.getUserId()}"
                                   <c:if test="${assignedTeacherIds.contains(teacher.getUserId())}">checked</c:if> />
                            <label>${teacher.getFullName()}</label>
                        </div>
                    </c:forEach>

                    <button type="submit" class="btn btn-primary">Assign Users</button>
                </form>

                <a href="subject-list" class="btn btn-secondary">Back to Subject List</a>
            </div>
        </div>
        <script>
            // Kiểm tra nếu URL chứa tham số 'alreadyAssigned=true'
            if (window.location.search.includes("alreadyAssigned=true")) {
                alert("Giáo viên đã được phân công cho môn học này!");
                // Xóa tham số này khỏi URL để không hiện lại khi tải lại trang
                const url = new URL(window.location.href);
                url.searchParams.delete("alreadyAssigned");
                window.history.replaceState({}, document.title, url.toString());
            }
        </script>
    </body>
</html>