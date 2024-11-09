<%-- 
    Document   : testResult
    Created on : Nov 4, 2024, 9:03:56 PM
    Author     : dell
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Kết quả bài kiểm tra</title>
        <%@include file="Components/AllAccess.jsp"%>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <!-- Sidebar -->
                <div class="col-md-2 p-0">
                    <%@include file="Components/Sidebar.jsp" %>
                </div>

                <!-- Main content -->
                <div class="col-md-10 p-5">
                    <div class="container mt-5">
                        <h1 class="text-center mb-4">Kết quả bài kiểm tra</h1>
                        <div class="card shadow-lg">
                            <div class="card-body text-center">
                                <p class="display-4"><strong>Số câu đúng:</strong> ${correctAnswers} / ${totalQuestions}</p>
                                <p class="display-4"><strong>Điểm:</strong> ${score}</p>
                            </div>
                        </div>
                        <div class="text-center mt-4">
                            <a href="test-list" class="btn btn-primary btn-lg">Trở về danh sách bài kiểm tra</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>