<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Change Password</title>
        <link rel="stylesheet" href="assets/css/change-password.css"> <!-- Link to the external CSS file -->
    </head>
    <body>
        <div class="container">
            <a href="${sessionScope.account.roleId == 1 ? 'homes' : 'teacherhome'}" class="atag">Go home</a>

            <h2>Change Password</h2>
            <form action="changepassword" method="post" class="form-container">
                <div class="form-group">
                    <label for="currentPassword">Current Password:</label>
                    <input type="password" id="currentPassword" name="currentpass" placeholder="Enter your current password" required>
                </div>
                
                <div class="form-group">
                    <label for="newPassword">New Password:</label>
                    <input type="password" id="newPassword" name="newpass" placeholder="Enter a new password" required>
                </div>
                
                <div class="form-group">
                    <label for="confirmPassword">Confirm New Password:</label>
                    <input type="password" id="confirmPassword" name="confirmpass" placeholder="Confirm your new password" required>
                </div>
                
                <input type="hidden" id="hiddenId" name="userId" value="${sessionScope.account.userId}">
                
                <div class="form-group">
                    <button type="submit" class="custom-button">Change Password</button>
                </div>
            </form>

            <div><h3 style="color: red">${requestScope.mess}</h3></div>
        </div>
    </body>
</html>
