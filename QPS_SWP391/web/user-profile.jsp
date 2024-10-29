<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head
        <meta charset="UTF-8">
        <title>Test List</title>
        <style>
            body {
                font-family: Arial, sans-serif;
            }
            table {
                width: 100%;
                border-collapse: collapse;
            }
            table, th, td {
                border: 1px solid black;
            }
            th, td {
                padding: 10px;
                text-align: left;
            }
            th {
                background-color: #f2f2f2;
            }
            button {
                padding: 5px 10px;
                background-color: blue;
                color: white;
                border: none;
                cursor: pointer;
            }
            input[type="text"] {
                padding: 5px;
                width: 200px;
                margin-right: 10px;
            }
            input[type="submit"] {
                padding: 5px 10px;
                background-color: green;
                color: white;
                border: none;
                cursor: pointer;
            }
        </style>
    </head>
    <body class="container-fluid">
        <div class="row">
            <div class="col-md-2" style="border-right: 1px solid #1a1e21; background-color: #343a40">
                <%@include file="Components/Sidebar.jsp" %>
            </div>
            <div class="col-md-10 px-4 py-4" style="margin-top: 20px">
                <h1 style="margin-bottom:20px">List Tests</h1>

                <form class="form-inline mb-4 col-md-6" action="test-list" method="GET">
                    <div class="input-group">
                        <input type="text" name="searchQuery" value="${searchQuery}" placeholder="Search test by name" />
                        <div class="input-group-append">
                            <button class="btn btn-primary" type="submit">Search</button>
                        </div>                    
                    </div>
                </form>

                <br/>

                <c:choose>
                    <c:when test="${not empty tests}">
                        <table>
                            <thead>
                                <tr>
                                    <th>Test ID</th>
                                    <th>Test Name</th>
                                    <th>Question in test</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="test" items="${tests}">
                                    <tr>
                                        <td>${test.testID}</td>
                                        <td>${test.testName}</td>
                                        <td>4</td>
                                        <td>
                                            <form action="testDetail" method="get">
                                                <input type="hidden" name="testID" value="${test.testID}">
                                                <button type="submit" class="btn btn-info btn-sm">Detail</button>
                                                <button type="submit" class="btn btn-primary btn-sm">Edit</button>
                                                <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <p>No test was found</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </body>
</html>