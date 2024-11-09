<%-- 
    Document   : testHistory
    Created on : Nov 6, 2024, 6:42:50 AM
    Author     : dell
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <title>Test History</title>
        <style>
            .table th, .table td {
                text-align: center;
            }
            .pagination {
                justify-content: center;
            }
        </style>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-2 p-0">
                    <%@include file="Components/Sidebar.jsp" %>
                </div>
                <div class="col-md-10">
                    <h1 class="text-center mb-4">Lịch sử Bài Kiểm Tra</h1>

                    <table class="table table-bordered table-striped">
                        <thead class="thead-dark">
                            <tr>
                                <th>Tên Bài Kiểm Tra</th>
                                <th>Điểm</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty testHistory}">
                                    <c:forEach var="test" items="${testHistory}">
                                        <tr>
                                            <td>${test.testName}</td>
                                            <td>${test.score}</td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="4">Không có bài kiểm tra nào được lưu trữ.</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>

                    <!-- Phân trang -->
                    <div class="pagination">
                        <c:if test="${currentPage > 1}">
                            <a href="testHistory?page=${currentPage - 1}" class="btn btn-secondary">Trang Trước</a>
                        </c:if>
                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <a href="testHistory?page=${i}" class="btn btn-secondary">${i}</a>
                        </c:forEach>
                        <c:if test="${currentPage < totalPages}">
                            <a href="testHistory?page=${currentPage + 1}" class="btn btn-secondary">Trang Sau</a>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>