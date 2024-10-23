<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="utils.*" %>
<c:set var="role" value="${empty param.role ? -1 : param.role}"/>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin Dashboard</title>
        <link rel="stylesheet" href="../assets/css/admin-user-list.css"/>
        <link rel="stylesheet" href="../assets/css/common.css">
    </head>
    <style>
        .pagination {
            display: flex;
            justify-content: center;
            align-items: center;
            margin: 10px;
        }
        .pagination a {
            color: black;
            font-size: 18px;
            padding: 8px 16px;
            text-decoration: none;
            margin: 5px;
            border: 1px solid #A162FD;
            border-radius: 5px;
        }
        .pagination a.active {
            background-color: #A162FD;
            color: white;
        }
        .pagination a:hover:not(.active) {
            background-color: #A162FD;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        table th, table td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        table th {
            background-color: #f2f2f2;
        }
        .search-bar {
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
        }
        .search-bar input[type="text"] {
            width: 300px;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        .search-bar button {
            padding: 8px 16px;
            background-color: #A162FD;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .search-bar button:hover {
            background-color: #8044d3;
        }
        .btn-add-user {
            padding: 8px 16px;
            background-color: #28a745;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .btn-add-user:hover {
            background-color: #218838;
        }
    </style>
    <body class="container-fluid">
        <div class="row">
            <div class="col-md-2" style="border-right: 1px solid #1a1e21; background-color: #343a40">
                <%@include file="/Components/Sidebar.jsp" %>
            </div>
            <main class="col-md-10 p-3">
                <div class="">
                    <h1>User Manager</h1>
                    <small>Home / User Manager</small>
                </div>
                <a class="button" href = 'adduser.jsp'>Add User</a>

                <div class="search-bar" style="align-items: center; margin-top: 15px">
                    <!-- Role filter -->
                    <div class="col-md-6">
                        <form class="form-inline">
                            <div class="form-group mb-2">
                                <label for="roleSelect" class="mr-2">Role:</label>
                                <select class="form-control" id="roleSelect" name="role" onchange="applyFilter('role', event.target.value)">
                                    <option value="-1" ${role == -1 ? 'selected' : ''}>All roles</option>
                                    <option value="1" ${role == 1 ? 'selected' : ''}>Student</option>
                                    <option value="2" ${role == 2 ? 'selected' : ''}>Admin</option>
                                    <option value="3" ${role == 3 ? 'selected' : ''}>Teacher</option>
                                </select>
                            </div>
                        </form>
                    </div>

                    <!-- Search bar with button -->
                    <form class="form" onsubmit="event.preventDefault()">
                        <label for="search">
                            <input
                                autocomplete="off"
                                placeholder="Search by name"
                                id="search"
                                type="text"
                                name="search"
                                value="${param.search}"
                                onkeypress="handleSearch(event)"
                                />
                        </label>
                        <button type="button" onclick="handleSearchClick()">Search</button>
                    </form>
                </div>

                <div>
                    <table>
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>USER</th>
                                <th>FULL NAME</th>
                                <th>ROLE</th>
                                <th>DOB</th>
                                <th>PHONE NUMBER</th>
                                <th>PLACE WORK</th>
                                <th>USER CODE</th>
                                <th>ACTIONS</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${listUser}" var="user">
                                <tr>
                                    <td>${user.userId}</td>
                                    <td>
                                        <div class="d-flex align-items-center">
                                            <div class="" style="background-image: url(../${user.avatar}); width: 50px; height: 50px; background-size: cover; border-radius: 50%;"></div>
                                            <div class="ml-3">
                                                <h4>${user.email}</h4>
                                            </div>
                                        </div>
                                    </td>
                                    <td>${user.fullName}</td>
                                    <td>${user.role}</td>
                                    <td>${user.dob}</td>
                                    <td>${user.phoneNumber}</td>
                                    <td>${user.place}</td>
                                    <td>${user.userCode}</td>
                                    <td>
                                        <form action="delete-user" method="POST" style="display:inline;">
                                            <input type="hidden" name="userId" value="${user.getUserId()}"/>
                                            <button type="submit" class="btn btn-danger btn-lg">Delete</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <!-- Pagination -->
                    <c:set var="page" value="${requestScope.page}"/>
                    <div class="pagination">
                        <c:forEach begin="1" end="${requestScope.num}" var="i">
                            <a class="${i==page ? 'active' : ''}" href="adminhome?page=${i}">${i}</a>
                        </c:forEach>
                    </div>
                </div>
            </main>
        </div>
    </body>
</html>

<script>
    function applyFilter(name, value) {
        let searches = new URLSearchParams(location.search);
        searches.set(name, value);
        location.search = searches.toString();
    }

    function handleSearch(e) {
        if (e.key === "Enter") {
            const target = e.target;
            const name = target.getAttribute("name");
            const value = target.value;
            applyFilter(name, value);
        }
    }

    function handleSearchClick() {
        const searchInput = document.getElementById('search');
        applyFilter('search', searchInput.value);
    }
</script>