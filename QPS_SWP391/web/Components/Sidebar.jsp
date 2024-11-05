<%@ page import="models.User" %>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Sidebar</title>
        <%@include file="/Components/AllAccess.jsp"%>
        <style>
            .sidebar {
                height: 100vh;
                color: white;
            }
            .sidebar h3 {
                color: #FF5E73;
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
            .dropdown-content {
                display: none;
                position: absolute;
                background-color: #495057;
                min-width: 100%;
                z-index: 1;
                padding: 0;
                border-radius: 5px;
                overflow: hidden;
            }
            .dropdown-content a {
                color: white;
                padding: 10px 20px;
                text-decoration: none;
                display: block;
                background-color: #495057;
            }
            .dropdown-content a:hover {
                background-color: #FF5E73;
            }
            .nav-item:hover .dropdown-content {
                display: block;
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
            <div class="text-center mb-4 dropdown">
                <h3>QPS04</h3>
                <%
                    User account = (User) session.getAttribute("account");
                    String avatarUrl = account != null ? account.getAvatar() : "assets/img/default-avatar.png"; // Fallback to a default image
                %>
                <a href="user-profile.jsp" class="avatar-container">
                    <img src="<%= avatarUrl %>" alt="User Image">
                </a>
            </div>
            <ul class="list-unstyled">
                <li class="nav-item dropdown">
                    <a class="btn" href="<%= ((User) session.getAttribute("account")).getRoleId() == 1 ? "studenthome" : ((User) session.getAttribute("account")).getRoleId() == 2 ? "adminhome" : "teacherhome" %>">
                        Homepage
                    </a>
                    <div class="dropdown-content" style="left: 100%; top: 0;">
                        <%
                            if (account != null) {
                                int roleId = account.getRoleId();
                                if (roleId == 1) {
                        %>
                                    <a href="termset">Term Set List</a>
                                    <a href="test-list">Test List</a>
                        <%
                                } else if (roleId == 3) {
                        %>
                                    <a href="subject-list">Subject List</a>
                                    <a href="test-list">Test List</a>
                        <%
                                }
                            } else {
                        %>
                            <p>No account found!</p>
                        <%
                            }
                        %>
                    </div>
                </li>
                <li class="nav-item">
                    <a class="btn" href="login.jsp">Logout</a>
                </li>
            </ul>
        </nav>
    </body>
</html>
