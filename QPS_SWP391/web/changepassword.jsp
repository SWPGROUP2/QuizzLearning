<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Change Password</title>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background: linear-gradient(to right, #fbc2eb, #a6c0fe);
                font-family: Arial, sans-serif;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
            }
            .container {
                max-width: 500px;
                width: 100%;
            }
            .card {
                border-radius: 15px;
                box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
                padding: 20px;
                background-color: #ffffff;
            }
            .card-title {
                font-size: 24px;
                font-weight: 600;
                color: #333;
            }
            .form-control {
                border-radius: 0;
                border-color: #dee2e6;
            }
            .form-label {
                font-weight: 500;
                color: #495057;
            }
            .btn-custom {
                background-color: #007bff;
                border-color: #007bff;
                border-radius: 8px;
                padding: 10px 20px;
                font-size: 16px;
                font-weight: 600;
            }
            .btn-custom:hover {
                background-color: #0056b3;
                border-color: #004085;
            }
            .mb-3 {
                margin-bottom: 1.5rem;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="card">
                <h2 class="card-title mb-4 text-center">Change Password</h2>
                <form action="changepassword" method="post">
                    <div class="mb-3">
                        <label for="currentPassword" class="form-label">Current Password</label>
                        <input type="password" class="form-control" id="currentPassword" name="currentpass" placeholder="Enter your current password" required>
                    </div>
                    <div class="mb-3">
                        <label for="newPassword" class="form-label">New Password</label>
                        <input type="password" class="form-control" id="newPassword" name="newpass" placeholder="Enter a new password" required>
                    </div>
                    <div class="mb-3">
                        <label for="confirmPassword" class="form-label">Confirm New Password</label>
                        <input type="password" class="form-control" id="confirmPassword" name="confirmpass" placeholder="Confirm your new password" required>
                    </div>
                    <button type="submit" class="btn btn-custom w-100">Change Password</button>
                    <a href="teacherhome" class="btn btn-home w-100">Return to Home</a> <!-- Nút quay lại trang chủ -->
                </form>
                <c:if test="${not empty mess}">
                    <div class="alert alert-info">${mess}</div>
                </c:if>

            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/5.3.0/js/bootstrap.min.js"></script>
    </body>
</html>