<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="utils.*" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin Dashboard</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="../assets/css/common.css">
        <style>
            body {
                font-family: Arial, sans-serif;
                
            }
            .filter-form {
                display: flex;
                gap: 10px;
                flex-wrap: wrap;
            }
            .filter-form label {
                margin-right: 5px;
            }
            .filter-form .form-control {
                width: auto;
                min-width: 150px;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                table-layout: fixed;
            }
            th, td {
                padding: 10px;
                text-align: left;
                border: 1px solid #ddd;
                word-wrap: break-word;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
            }
            th {
                background-color: #f2f2f2;
            }
            .pagination a {
                margin: 0 2px;
            }
            .table-actions {
                display: flex;
                gap: 5px;
            }
            .table-actions form {
                display: inline;
            }
        </style>
        <script>
            function autoSubmitForm() {
                document.getElementById("filterForm").submit();
            }
        </script>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-2 ">
                    <%@include file="/Components/Sidebar.jsp" %>
                </div>

                <div class="col-md-10">
                    <div class="d-flex align-items-center mb-2">
                        <a href="adminhome" class="btn btn-dark mr-2">Back to Homepage</a>
                        <a href="adduser" class="btn btn-success mr-2">Add User</a>
                        <h1 class="flex-grow-1 text-center mb-0">User Manager</h1>
                    </div>
                    <form id="filterForm" action="adminhome" method="GET" class="filter-form mb-3">
                        <label for="classFilter">Class:</label>
                        <select id="classFilter" name="classId" class="form-control" onchange="autoSubmitForm()">
                            <option value="">All Classes</option>
                            <c:forEach items="${uniqueClassesMap}" var="entry">
                                <!-- Using entry.value as the option value (classId) and entry.key as the display text (className) -->
                                <option value="${entry.value}" ${param.classId == entry.value ? "selected" : ""}>${entry.key}</option>
                            </c:forEach>
                        </select>


                    <label for="roleFilter">Role:</label>
                    <select id="roleFilter" name="roleId" class="form-control" onchange="autoSubmitForm()">
                        <option value="">All Roles</option>
                        <option value="1" ${param.roleId == '1' ? "selected" : ""}>Student</option>
                        <option value="3" ${param.roleId == '3' ? "selected" : ""}>Teacher</option>
                    </select>

                    <label for="statusFilter">Status:</label>
                    <select id="statusFilter" name="status" class="form-control" onchange="autoSubmitForm()">
                        <option value="">All Statuses</option>
                        <option value="Active" ${param.status == 'Active' ? "selected" : ""}>Active</option>
                        <option value="Inactive" ${param.status == 'Inactive' ? "selected" : ""}>Inactive</option>
                    </select>

                    <label for="fullNameSearch">Full Name:</label>
                    <input type="text" id="fullNameSearch" name="fullName" class="form-control" placeholder="Search by Full Name" value="${param.fullName}" style="width:auto; min-width:150px;">

                    <button type="submit" class="btn btn-primary">Filter</button>
                    </form>

                    <div>
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>AVATAR</th>
                                    <th>EMAIL</th>
                                    <th>FULL NAME</th>
                                    <th>ROLE</th>
                                    <th>CLASS</th>
                                    <th>STATUS</th>
                                    <th>ACTIONS</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${userList}" var="user">
                                    <tr>
                                        <td>
                                            <div style="background-image: url(../${user.avatar}); width: 50px; height: 50px; background-size: cover; border-radius: 50%;"></div>
                                        </td>
                                        <td>${user.email}</td>
                                        <td>${user.fullName}</td>
                                        <td>${user.role}</td>
                                        <td>${user.className}</td>
                                        <td>${user.status}</td>
                                        <td class="table-actions">
                                            <form action="update-user" method="POST" style="display:inline;">
                                                <input type="hidden" name="userId" value="${user.userId}"/>
                                                <button type="submit" class="btn btn-primary btn-sm">Edit</button>
                                            </form>
                                            <form action="delete-user" method="POST" style="display:inline;">
                                                <input type="hidden" name="userId" value="${user.userId}"/>
                                                <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>

                        <!-- Pagination -->
                        <div class="pagination">
                            <c:set var="page" value="${requestScope.page}"/>
                            <c:forEach begin="1" end="${requestScope.num}" var="i">
                                <a href="adminhome?page=${i}" class="btn btn-secondary ${i == page ? 'active' : ''}">${i}</a>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
