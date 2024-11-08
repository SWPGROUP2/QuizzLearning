<%@page import="java.util.ArrayList"%>
<%@page import="models.User"%>
<%@ page import="java.io.File" %>
<%@ page import="java.util.Arrays" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>User Profile</title>
        <link rel="stylesheet" href="assets/css/user-profile.css">  
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-2" style="border-right: 1px solid #1a1e21; background-color: #343a40">
                    <%@include file="Components/Sidebar.jsp" %>
                </div>

                <div class="col-md-10">
                    <div class="container">
                        <a href="${sessionScope.account.roleId == 1 ? 'studenthome' : (sessionScope.account.roleId == 2 ? 'adminhome' : 'teacherhome')}" class="atag">Go back</a>                      
                        <h2>Edit Profile</h2>
                        <div class="profile-picture" onclick="openPopup()">
                            <img src="${sessionScope.account.avatar}" alt="Profile Picture" id="previewImage">
                        </div>

                        <div class="popup" id="imagePopup">
                            <h2 style="margin-bottom: 20px;">Choose your new avatar:</h2>
                            <form id="avatarForm" action="ProcessAvatarSelection" method="post">
                                <%
                                String imgDirectoryPath = getServletContext().getRealPath("/assets/avatar");
                                String[] imageFileNames = new File(imgDirectoryPath).list();

                                for (String imageName : imageFileNames) {
                                    String relativePath = "assets/avatar/" + imageName;
                                %>
                                <input type="radio" id="<%= relativePath %>" name="selectedAvatar" value="<%= relativePath %>">
                                <label for="<%= relativePath %>" style="cursor: pointer;"><img src="<%= relativePath %>" alt="<%= relativePath %>"></label>
                                    <%
                                        }
                                    %>
                                <input type="hidden" id="hiddenId" name="userId" value="${sessionScope.account.userId}">
                                <br>
                                <button type="submit">Submit</button>
                            </form>
                        </div>

                        <form action="Updateuser" method="post">
                            <div class="profile-form">
                                <div style="margin-bottom: 8px;font-weight: bold;">
                                    Full Name:
                                </div>
                                <input type="text" id="fullname" name="fullName" value="${sessionScope.account.fullName}">

                                <div style="margin-bottom: 8px;font-weight: bold;">
                                    User Name:
                                </div>
                                <input type="text" id="username" name="userName" value="${sessionScope.account.userName}">

                                <div style="margin-bottom: 8px;font-weight: bold;">
                                    Phone Number:
                                </div>
                                <input type="number" id="phone" name="phoneNumber" value="${sessionScope.account.phoneNumber}">

                                <div style="margin-bottom: 8px;font-weight: bold;">
                                    Date of Birth:
                                </div>
                                <input type="date" id="dob" name="dob" value="${sessionScope.account.dob}">

                                <div style="margin-bottom: 8px;font-weight: bold;">
                                    Start Date:
                                </div>
                                <input type="date" id="startDate" name="startDate" value="${sessionScope.account.startDate != null ? sessionScope.account.startDate : ''}"readonly>

                                <div style="margin-bottom: 8px;font-weight: bold;">
                                    End Date:
                                </div>
                                <input type="date" id="endDate" name="endDate" value="${sessionScope.account.endDate != null ? sessionScope.account.endDate : ''}"readonly>

                                <input type="hidden" id="hiddenId" name="userId" value="${sessionScope.account.userId}">
                                <div><h3 style="color: red">${requestScope.mess}</h3></div>

                                <div>
                                    <input type="submit" value="Save Changes" class="custom-button"/>
                                </div>
                            </div>
                        </form>

                        <div class="change-password-button" style="margin-top: 20px;">
                            <a href="changepassword.jsp" class="custom-button" style="text-decoration: none; padding: 10px 20px; background-color: #FF5E73; color: white; border-radius: 5px;">Change Password</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script>
            function openPopup() {
                var popup = document.getElementById('imagePopup');
                popup.style.display = 'block';
            }

            document.getElementById('avatarForm').addEventListener('submit', function (event) {
                var selectedAvatar = document.querySelector('input[name="selectedAvatar"]:checked');
            });
        </script>

    </body>
</html>
