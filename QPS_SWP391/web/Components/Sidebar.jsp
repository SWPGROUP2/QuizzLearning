<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Sidebar</title>
        <!-- Include Bootstrap CSS -->
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
                background-color: #f9f9f9;
                min-width: 160px;
                z-index: 1;
            }
            .dropdown-content a {
                color: black;
                padding: 12px 16px;
                text-decoration: none;
                display: block;
                text-align: left;
            }
            .dropdown-content a:hover {
                background-color: #f1f1f1;
            }
            .dropdown:hover .dropdown-content {
                display: block;
            }
            .sidebar img {
                width: 50px;
                height: 50px;
                border-radius: 50%;
                margin: 0 auto;
                display: block;
            }
        </style>
    </head>
    <body>
        <nav class="sidebar p-3">
            <div class="text-center mb-4 dropdown">
                <h3>QPS04</h3>
                <img class="dropbtn" src="assets/img/roll.jpg" alt="User Image">
                <div class="dropdown-content">
                    <a href="user-profile.jsp">Profile</a>
                    <a href="login.jsp">Logout</a>
                </div>
            </div>

            <ul class="list-unstyled">
                <li class="nav-item text-center mb-3">
                    <p>HomePage</p>
                </li>
                <li class="nav-item">
                    <button class="btn">Mathematics</button>
                </li>
                <li class="nav-item">
                    <button class="btn">Science</button>
                </li>
                <li class="nav-item">
                    <button class="btn">History</button>
                </li>
                <li class="nav-item">
                    <button class="btn">Literature</button>
                </li>
                <li class="nav-item">
                    <button class="btn">Geography</button>
                </li>
                <li class="nav-item">
                    <button class="btn">English</button>
                </li>
            </ul>
        </nav>
    </body>
</html>