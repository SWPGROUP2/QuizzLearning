<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Sidebar</title>
        <!-- Include Bootstrap CSS -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .sidebar {
                height: 100vh;
                color: white;
            }
            .sidebar h3 {
                color: #FF5E73; /* Màu chữ tiêu đề nổi bật */
            }
            .sidebar ul {
                padding-left: 0;
            }
            .sidebar .nav-item {
                margin-bottom: 15px; /* Khoảng cách giữa các nút */
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
                background-color: #FF5E73; /* Thay đổi màu khi hover */
                color: white;
                transform: translateX(5px); /* Hiệu ứng trượt nhẹ khi hover */
            }
        </style>
    </head>
    <body>
        <nav class="sidebar p-3">
            <div class="text-center mb-4">
                <h3>QPS04</h3>
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

        <!-- Include Bootstrap JS (optional) -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>