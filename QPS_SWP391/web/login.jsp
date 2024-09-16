<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Log in</title>
        <link rel="stylesheet" href="./assets/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <link rel="stylesheet" href="./assets/css/common.css">
        <style>
            body {
                background-image: url('assets/img/loginbackground.png');
                background-size: cover;
                background-position: center;
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 0;
                background-color: #f0f0f0;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }

            form {
                background-color: #fff;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0px 0px 10px 0px rgba(0, 0, 0, 0.1);
                width: 400px;
                text-align: center;
            }

            input[type="email"],
            input[type="password"] {
                width: 100%;
                padding: 10px;
                margin-bottom: 10px;
                border-radius: 5px;
                border: 1px solid #ddd;
                box-sizing: border-box;
            }

            input[type="checkbox"] {
                margin-right: 5px;
                margin-bottom: 15px;
            }

            input[type="submit"] {
                width: 100%;
                padding: 10px;
                border-radius: 5px;
                border: none;
                color: #fff;
                background-color: #000;
                color: #fff;
                cursor: pointer;
                transition: background-color 0.3s, color 0.3s, border-color 0.3s;
            }

            input[type="submit"]:hover {
                background-color: #4A3F8A;
            }

            h2 {
                margin-bottom: 20px;
                color: #333;
            }

            h5 {
                margin-bottom: 0;
                padding-top: 10px;
                color: #000;
                cursor: pointer;
                margin-top: 10px;

            }
        </style>
    </head>
    <body>

    <container>
        <row>
            <form action="login" method="post" style="background-color: rgba(255, 255, 255, 0.2); box-shadow: 3px 3px 3px 3px rgba(7, 7, 7, 0.6);">
                <h1 ><span style="color: black" class="badge badge-secondary">Login</span></h1>
                <row>
                    <row>
                        <div class="mb-2 mt-4">
                            <input  type="email" id="username" name="email" class="form-control" placeholder="Email Address" required>
                        </div> 
                    </row>
                    <row>
                        <div>
                            <input placeholder="Password" type="password" id="password" name="password" class="form-control" required>
                        </div>
                    </row>

                    <row> 
                        <hr class="mt-4"/>
                    </row>
                    <row> 
                        <div  class="mt-4">
                            <input type="submit" value="Log in">
                        </div>
                    </row>

                    <row> 
                        <div>
                            <h5>Don't have an account? <a href="/quiz/signup" class="text-decoration-none">Sign up</a></h5>
                        </div>
                    </row>

                </row>

                <row > 
                    <c:if test="${error!=null}">
                        <div class="message mt-4">
                            <h4 style="color: red;">${error}</h4>
                        </div>
                    </c:if>
                    <c:if test="${mess!=null}">
                        <div class="message mt-4">
                            <h4 style="color: greenyellow;">${mess}</h4>
                        </div>
                    </c:if>
                </row>



            </form>
        </row>
    </container>
</body>
</html>
