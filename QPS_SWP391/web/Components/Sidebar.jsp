<%@ page import="models.User" %>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Sidebar</title>
        <%@include file="/Components/AllAccess.jsp"%>
        <style>
            .sidebar {
                height: 100%;
                min-height: 500px;
                width: 215px;
                position: fixed;
                color: white;
                display: flex;
                flex-direction: column;
                background-color: #343a40;
                left: 0;
                top: 0;
            }

            .sidebar .nav-item {
                margin-bottom: 15px;
            }
            .sidebar .btn {
                width: 100%;
                color: white;
                background-color: #495057;
                border: none;
                text-align: left;
                padding: 10px 20px;
                font-size: 16px;
                transition: all 0.3s ease;
            }
            .sidebar .btn:hover {
                background-color: #FF5E73;
                color: white;
                transform: translateX(5px);
            }
            .avatar-container {
                width: 70px;
                height: 75px;
                border-radius: 50%;
                border: 3px solid rgba(255, 94, 94, 0.5);
                display: flex;
                justify-content: center;
                align-items: center;
                margin: 0 auto;
                cursor: pointer;
            }
            .sidebar img {
                width: 65px;
                height: 70px;
                border-radius: 50%;
            }


        </style>
    </head>
    <body>
        <nav class="sidebar p-3">
            <div class="text-center mb-4">
                <h3>QPS03</h3>
                <% 
                    User account = (User) session.getAttribute("account");
                    String avatarUrl = (account != null && account.getAvatar() != null) ? account.getAvatar() : "assets/avatar/default-image.png";
                %>
                <a href="user-profile.jsp" class="avatar-container">
                    <img src="<%= avatarUrl %>" >
                </a>
            </div>
            <ul class="list-unstyled">
                <li class="nav-item">
                    <a class="btn" href="<%= 
                        (account != null) ? 
                        (account.getRoleId() == 1 ? "studenthome" : 
                        (account.getRoleId() == 2 ? "adminhome" : "teacherhome")) : 
                        "#" %>">
                        Homepage
                    </a>
                </li>
                <% 
                    if (account != null) {
                        int roleId = account.getRoleId();
                        if (roleId == 1) {
                %>
                <li class="nav-item">
                    <a class="btn" href="termset">Term Set List</a>
                </li>
                <li class="nav-item">
                    <a class="btn" href="test-list">Test List</a>
                </li>
                <% 
                        } else if (roleId == 2) {
                %>
                <li class="nav-item">
                    <a class="btn" href="subject-list">Subject List</a>
                </li>
                <% 
                        } else if (roleId == 3) {
                %>
                <li class="nav-item">
                    <a class="btn" href="questionlist">Question Bank</a>
                </li>
                <li class="nav-item">
                    <a class="btn" href="test-list">Test List</a>
                </li>
                <% 
                        }
                    } else {
                %>
                <p>No account found!</p>
                <% 
                    }
                %>
                <li class="nav-item">
                    <a class="btn" href="login.jsp">Logout</a>
                </li>
            </ul>
        </nav>
    </body>
</html>
