<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="/Components/AllAccess.jsp"%>
        <title>QPS 04</title>
        <script src="js/script.js"></script>
        <style>
            body, html {
                overflow-x: hidden;
                height: 100%;
            }
            .image-container {
                position: relative;
                width: 100%;
                height: 100%;
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
                background-color: #e55065; /* Thay đổi màu khi hover */
            }
            /* Dropdown Menu */
            .dropdown {
                position: relative;
                display: inline-block;
            }
            .dropdown-content {
                display: none;
                position: absolute;
                background-color: #f9f9f9;
                min-width: 160px;
                box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
                z-index: 1;
            }
            .dropdown-content a {
                color: black;
                padding: 12px 16px;
                text-decoration: none;
                display: block;
                text-align: left;
            }
            .dropdown-content a:hover {background-color: #f1f1f1}
            .dropdown:hover .dropdown-content {
                display: block;
            }
            .dropdown:hover .dropbtn {
                background-color: #3e8e41;
            }
        </style>
    </head>
    <body class="container-fluid">
        <div class="row" style="height: 639px;">
            <!-- Sidebar  -->
            <div class="col-md-2" style="border-right: 1px solid #1a1e21; background-color: #343a40">
                <%@include file="/Components/Sidebar.jsp" %>
            </div>
            <!-- Page Content  -->
            <div class="col-md-10 px-0">
                <div class="image-container">
                    <img src="assets/img/sanhAlpha.jpg" alt="Background Image">
                    <div class="overlay-text">
                        <h1>EDUCATION</h1>
                        <h2>QUIZ MANAGEMENT SYSTEM</h2>
                        <p href="#" class="learn-more-btn">Quiz History</p>

                        <!-- Dropdown Menu -->
                        <div class="dropdown">
                            <button class="dropbtn">User</button>
                            <div class="dropdown-content">
                                <a href="user-profile.jsp">Profile</a>
                                <a href="login.jsp">Logout</a>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
