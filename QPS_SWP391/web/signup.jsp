<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Sign up</title>
        <style>
            body {
                background-image: url('assets/img/anhnen.jpg');
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
            input[type="password"],
            input[type="text"]{
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
                background-color: #9B68FF;
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

        <form action="signup" method="post">
            <h2>Sign up</h2>

            <div>
                <input maxlength="50" type="email" id="email" name="email" placeholder="Email Address" required>
            </div>

            <div>
                <input placeholder="Password" type="password" id="password" name="password" required>
            </div>

            <div>
                <input maxlength="30" type="text" id="username" name="username" placeholder="Display Name" required>
            </div>
            <div>
                <input min="8" max="13" placeholder="Phone Number" type="text" id="phoneNumber" name="phoneNumber" required>
            </div>
            <div>
                <input maxlength="20" placeholder="Student/Teacher ID" type="text" id="phoneNumber" name="UserCode" required>
            </div>
            <div style="margin: 5px 0px;">
                Select Campus:    <select name = "place" > 
                    <option value="FU-Hoa Lac">FU-Hoa Lac </option>
                    <option value="FU-Ho Chi Minh ">FU-Ho Chi Minh </option>
                    <option value="FU-Da nang">FU-Da nang </option>
                    <option value="FU-Can Tho ">FU-Can Tho </option>
                    <option value="FU-Quy Nhon">FU-Quy Nhon </option>
                </select>
            </div>
            <div style="margin: 5px 0px;">
                Date of birth:    <input type="date" id="date" name="date"  required>
            </div>
            <div style="justify-content: space-around; padding: auto; margin-bottom: 10px">
                <input type="radio" name="role" value="student" required> Student
                <input type="radio" name="role" value="teacher" style="margin-left: 30px" required> Teacher
            </div>

            <div>
                <input type="submit" value="Sign up">
            </div>
            <div>
                <h5>Back to <a href="/quiz/login" class="text-decoration-none">Login</a></h5>
            </div>

            <c:if test="${mess!=null}">
                <div class="message">
                    <h4 style="color: red;">${mess}</h4>
                </div>
            </c:if>
        </form>

    </body>
</html>