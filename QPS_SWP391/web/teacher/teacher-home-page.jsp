<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="/Components/AllAccess.jsp"%>
        <title>QPS 04</title>
        <script src="js/script.js"></script>
        <style>
            html, body {
                height: 100%;
                margin: 0;
                padding: 0;
            }

            .image-container {
                position: relative;
                width: 100%;
                height: 100vh;
                text-align: center;
            }
            .image-container img {
                width: 100%;
                height: 100%;
                object-fit: cover;
            }
            .overlay-text {
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                color: white;
                text-align: center;
            }
            .overlay-text h1 {
                font-size: 60px;
                font-weight: bold;
                letter-spacing: 5px;
                margin-bottom: 20px;
            }
            .overlay-text h2 {
                font-size: 24px;
                margin-bottom: 30px;
                letter-spacing: 2px;
            }
            .learn-more-btn {
                display: inline-block;
                padding: 10px 30px;
                background-color: #FF5E73;
                color: white;
                font-size: 16px;
                font-weight: bold;
                text-transform: uppercase;
                border-radius: 5px;
                text-decoration: none;
            }
            .learn-more-btn:hover {
                background-color: #e55065;
            }
        </style>
    </head>
    <body class="container-fluid">
        <div class="row" style="height: 639px;">
            <div class="col-md-2">
                <%@include file="/Components/Sidebar.jsp" %>
            </div>
            <div class="col-md-10 px-0">
                <div class="image-container">
                    <img src="assets/img/sanhAlpha.jpg" alt="Background Image">
                    <div class="overlay-text">
                        <h1>EDUCATION</h1>
                        <h2>QUIZ MANAGEMENT SYSTEM</h2>
                        <a href="questionlist" class="btn btn-primary">Question Bank</a>
                        <a href="test-list" class="btn btn-primary">Test List</a>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
