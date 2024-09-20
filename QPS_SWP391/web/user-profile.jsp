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
    </head>
    <body>


        <div class="container">
            
            
            <a href="${sessionScope.account.roleId == 1 ? 'homes' : 'teacherhomepage'}" class="atag">Go back</a>
            
            
            
            
            <h2>Edit Profile</h2>
            <div class="profile-picture" onclick="openPopup()">
                <img src="${sessionScope.account.avatar}" alt="Profile Picture" id="previewImage">
            </div>

            <div class="popup" id="imagePopup">
                <h2 style="margin-bottom: 20px;">Choose your new avatar:</h2>

                <form id="avatarForm" action="processAvatarSelection" method="post">

                    <%
                    // Đường dẫn đến thư mục chứa ảnh
                    String imgDirectoryPath = getServletContext().getRealPath("/assets/avatar");

                    // Danh sách các tên file trong thư mục ảnh
                    String[] imageFileNames = new File(imgDirectoryPath).list();

                    // In ra đường dẫn tương đối của mỗi ảnh và hiển thị ảnh
                    for (String imageName : imageFileNames) {
                        String relativePath = "assets/avatar/" + imageName;
                    %>



                    <input type="radio" id="<%= relativePath %>" name="selectedAvatar" value="<%= relativePath %>">
                    <label  for="<%= relativePath %>" style="cursor: pointer;"><img src="<%= relativePath %>" alt="<%= relativePath %>"></label>

                    <%
                        }
                    %>
                    <input type="hidden" id="hiddenId" name="userId" value="${sessionScope.account.userId}">
                    <br>
                    <button type="submit">Submit</button>
                </form>
            </div>


            <form action="updateuser" method="post">



                <div class="profile-form"  >




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
                        Student/Teacher ID:
                    </div>
                    <input type="text" id="schoolId" name="schoolId" value="${sessionScope.account.userCode}">




                    <div style="margin-bottom: 8px;font-weight: bold;">
                        Select Campus:
                    </div>
                    <%
                        User user= (User) session.getAttribute("account");
                        String place = user.getPlace().trim();
                    %>

                    <div style="margin: 5px 0px;">
                        <select name = "placeWork" style="width: 200px; padding: 5px; border: 1px solid #ccc; border-radius: 5px;"> 
                            <option value="FU-Hoa Lac" <%= place.equals("FU-Hoa Lac") ? "selected" : "" %>>FU-Hoa Lac </option>
                            <option value="FU-Ho Chi Minh" <%= place.equals("FU-Ho Chi Minh") ? "selected" : "" %>>FU-Ho Chi Minh </option>
                            <option value="FU-Da nang" <%= place.equals("FU-Da nang") ? "selected" : "" %>>FU-Da nang </option>
                            <option value="FU-Can Tho" <%= place.equals("FU-Can Tho") ? "selected" : "" %>>FU-Can Tho </option>
                            <option value="FU-Quy Nhon" <%= place.equals("FU-Quy Nhon") ? "selected" : "" %>>FU-Quy Nhon </option>
                        </select>
                    </div>
                    <input type="hidden" id="hiddenId" name="userId" value="${sessionScope.account.userId}">
                    <div><h3 style="color: red">${requestScope.mess}</h3></div>


                    <div>
                        <input type="submit" value="Save Changes" class="custom-button"/>
                    </div>
                </div>
            </form>


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
