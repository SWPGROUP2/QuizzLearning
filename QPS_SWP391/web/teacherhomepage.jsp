<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="Components/AllAccess.jsp"%>
        <title>QPS 04</title>
        <link rel="stylesheet" href="css/home.css">
        <script src="js/script.js"></script>

    </head>
    <body>
        <div class="wrapper">
            <!-- Sidebar  -->
            <div style="border-right: 1px solid #47748b;" id="header">
                <%@include file="Components/Sidebar.jsp" %>
                <img src="assets/img/school.jpg" alt="Avatar" style="width: 250px">
            </div>
            <!-- Page Content  -->
            <div id="content">

                <%@include file="Components/header.jsp" %>
                <div style="display: flex; align-items: center; margin-bottom: 30px; margin-left: 80px">
                    <div style="flex: 1; padding-right: 20px;">
                        <h2>Quiz based on Subjects</h2>
                        <p>Our quiz system provides a comprehensive selection of subjects, allowing learners of all levels to explore and challenge themselves with quizzes. Whether you're looking to test your knowledge in science, math, languages, or any other field, our platform has a wide range of topics to choose from. Dive into our extensive quiz library and enhance your learning experience today! </p>
                        <button id="customButton" style="width: 250px; height: 70px; background-color: #00FFFF; color: white; font-size: 18px; border-radius: 10px; border: none; cursor: pointer;">Try now!</button>
                    </div>
                    <div style="flex: 1">
                        <img src="assets/img/subject.png" alt="Avatar" style="width: 500px">
                    </div>
                </div>

                <div style="display: flex; align-items: center; margin-bottom: 30px; margin-left: 80px; margin-bottom: 50px">
                    <div style="flex: 1">
                        <img src="assets/img/quiz.png" alt="Avatar" style="width: 550px">
                    </div>
                    <div style="flex: 1; padding-right: 20px; margin-left: 80px">
                        <h2>Many types of quizzes</h2>
                        <p>Our quiz system provides a comprehensive selection of subjects, allowing learners of all levels to explore and challenge themselves with quizzes. Whether you're looking to test your knowledge in science, math, languages, or any other field, our platform has a wide range of topics to choose from. Dive into our extensive quiz library and enhance your learning experience today! </p>
                        <button id="customButton" style="width: 250px; height: 70px; background-color: #00FF00; color: white; font-size: 18px; border-radius: 10px; border: none; cursor: pointer;">Do Quiz now!</button>
                    </div>
                </div>

                <div class="footer" >
                    <%@include file="Components/footer.jsp" %>
                </div>
            </div>
        </div>
        <script></script>
    </body>
</html>
