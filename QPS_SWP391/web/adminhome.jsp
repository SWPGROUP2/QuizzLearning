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
            font-size: 22px;
            float: left;
            padding: 8px 16px;
            text-decoration: none;
            margin: 5px;
        }
        .pagination a.active {
            background-color: #A162FD;
            color: white;
        }
        .pagination a:hover:not(.active) {
            background-color: #A162FD;
        }
    </style>
    <body>

        <div class="main-content">
            <main>
                <div class="page-header">
                    <h1>User manager</h1>
                    <small>Home / User manager</small>
                </div>
                <div class="page-content">
                    <div class="records table-responsive">

                        <div class="record-header">
                            <div class="add">
                                <form>
                                    Role:  
                                    <select value="${empty param.role ? -1 : param.role}" onchange="applyFilter('role', event.target.value)">
                                        <option value="-1">All roles</option>
                                        <option value="1" ${role == 1 ? 'selected' : ''}> Student </option>
                                        <option value="2" ${role == 2 ? 'selected' : ''}> Admin </option>
                                        <option value="3" ${role == 3 ? 'selected' : ''}> Teacher </option>
                                    </select>
                                </form>
                            </div>

                            <div class="search">
                                <form class="form" onsubmit="event.preventDefault()">
                                    <label for="search">
                                        <input
                                            autocomplete="off"
                                            placeholder="Search"
                                            id="search"
                                            type="text"
                                            name="search"
                                            value="${param.search}"
                                            onkeypress="handleSearch(event)"
                                            />
                                    </label>
                                </form>
                            </div>
                        </div>

                        <div>
                            <table width="100%">
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
                                                <div class="client">
                                                    <div class="client-img bg-img" style="background-image: url(../${user.avatar})"></div>
                                                    <div class="client-info">
                                                        <h4>${user.userName}</h4>
                                                        <small>${user.email}</small>
                                                    </div>
                                                </div>
                                            </td>
                                            <td>${user.fullName}</td>
                                            <td>
                                                ${user.role}
                                            </td>
                                            <td>
                                                ${user.dob}
                                            </td>
                                            <td>
                                                ${user.phoneNumber}
                                            </td>
                                            <td>${user.place}</td>
                                            <td>${user.userCode}</td>
                                            <td>
                                                <button class="btn-primary" style="border-radius:10px "><a class="btn-primary" href="/quiz/admin/admin-user-detail?id=${user.userId}">Detail</a></button> 
                                            </td>
                                        </tr>
                                    </c:forEach> 
                                </tbody>
                            </table>
                            <c:set var="page" value="${requestScope.page}"/>
                            <div class="pagination">
                                <c:forEach begin="${1}" end="${requestScope.num}" var="i">
                                    <a class="${i==page?"active":""}" href="admin-user-list?page=${i}">${i}</a>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </body>
</html>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        // Gắn sự kiện nghe (event listener) cho các phần tử filter
        const filterButtons = document.querySelectorAll('[name="filterValue"]');
        filterButtons.forEach(function (button) {
            button.addEventListener('click', selectFilter);
        });
    });

    function selectFilter(e) {
        let target = e.target;
        let name = target.getAttribute("name");
        let value = target.getAttribute("value");
        applyFilter(name, value);
    }


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
</script>
