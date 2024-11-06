<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Log in</title>  
        <link rel="stylesheet" href="./assets/css/common.css">
        <style>
            body {
                background-image: url('./assets/img/sanhAlpha.jpg');
                background-size: cover;
                background-position: center;
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }

            form {
                background-color: rgba(255, 255, 255, 0.9);
                padding: 25px;
                border-radius: 12px;
                box-shadow: 0px 4px 15px rgba(0, 0, 0, 0.2);
                width: 100%;
                max-width: 400px;
                text-align: center;
            }

            input[type="email"],
            input[type="password"] {
                width: 100%;
                padding: 10px;
                margin-bottom: 15px;
                border-radius: 5px;
                border: 1px solid #ddd;
                box-sizing: border-box;
                font-size: 1rem;
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
                background-color: #000;
                color: #fff;
                font-size: 1rem;
                cursor: pointer;
                transition: background-color 0.3s, color 0.3s;
            }

            input[type="submit"]:hover {
                background-color: #4A3F8A;
            }

            h1 {
                color: #333;
                margin-bottom: 20px;
            }

            .message {
                margin-top: 10px;
                font-size: 0.9rem;
            }
            
            .error-message {
                color: #ff4d4d;
            }

            .success-message {
                color: #32CD32;
            }
        </style>
    </head>
    <body>
        <div>
            <form action="login" method="post">
                <h1>Login</h1>             
                <div class="mb-2 mt-4">
                    <input type="email" id="username" name="email" class="form-control" 
                           placeholder="Email Address" required
                           value="${email != null ? email : ''}">
                </div>                
                <div>
                    <input type="password" id="password" name="password" class="form-control" placeholder="Password" required>
                </div>

                <div style="text-align: left;">
                    <label class="checkbox-label">
                        <input type="checkbox" onclick="togglePassword()"> Show Password
                    </label>
                </div>
                <input type="submit" value="Log in">
                <c:if test="${error!=null}">
                    <div class="message error-message">
                        <h4>${error}</h4>
                    </div>
                </c:if>
                <c:if test="${mess!=null}">
                    <div class="message success-message">
                        <h4>${mess}</h4>
                    </div>
                </c:if>
            </form>
        </div>

        <script>
            function togglePassword() {
                const passwordField = document.getElementById("password");
                passwordField.type = (passwordField.type === "password") ? "text" : "password";
            }
        </script>
    </body>
</html>
