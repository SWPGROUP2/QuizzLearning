<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Test List</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <style>
            body {
                font-family: Arial, sans-serif;
            }
            table {
                width: 100%;
                border-collapse: collapse;
            }
            th, td {
                padding: 10px;
                text-align: left;
            }
            th {
                background-color: #f2f2f2;
            }
            .text-center {
                text-align: center;
                flex: 1;
            }
        </style>
    </head>
    <body class="container-fluid">
        <div class="row">
            <div class="col-md-2" style="border-right: 1px solid #1a1e21; background-color: #343a40">
                <%@include file="Components/Sidebar.jsp" %>
            </div>
            <div class="col-md-10">
                <div class="d-flex align-items-center mb-3">
                    <c:choose>
                        <c:when test="${sessionScope.account.roleId == 3}">
                            <a href="teacherhome" class="btn btn-dark">Back to Homepage</a>
                        </c:when>
                        <c:when test="${sessionScope.account.roleId == 1}">
                            <a href="studenthome" class="btn btn-dark">Back to Homepage</a>
                        </c:when>
                        <c:otherwise>
                        </c:otherwise>
                    </c:choose>
                    <h1 class="text-center">Tests List</h1>
                    <c:if test="${sessionScope.account.roleId == 3}">
                        <a href="addtest" class="btn btn-primary">Add Test</a>
                    </c:if>
                </div>
                <c:if test="${sessionScope.account.roleId == 1}">
                    <div class="d-flex justify-content-end mb-3">
                        <a href="testHistory" class="btn btn-info">Test History</a>
                    </div>
                </c:if>
                <form class="form-inline mb-4" action="test-list" method="GET">
                    <input type="text" name="searchQuery" value="${searchQuery}" placeholder="Search test by name" class="form-control mr-2"/>
                    <button class="btn btn-primary" type="submit">Search</button>
                </form>
                <c:choose>
                    <c:when test="${not empty tests}">
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>
                                        <a href="test-list?sortBy=testID&sortOrder=${sortOrder == 'ASC' ? 'DESC' : 'ASC'}&searchQuery=${searchQuery}">Test No
                                            <c:if test="${sortBy == 'testID'}">
                                                <c:choose>
                                                    <c:when test="${sortOrder == 'ASC'}">&#9650;</c:when>
                                                    <c:otherwise>&#9660;</c:otherwise>
                                                </c:choose>
                                            </c:if>
                                        </a>
                                    </th>
                                    <th>Test Name</th>
                                    <th>
                                        <a href="test-list?sortBy=questionCount&sortOrder=${sortOrder == 'ASC' ? 'DESC' : 'ASC'}&searchQuery=${searchQuery}">Questions in Test
                                            <c:if test="${sortBy == 'questionCount'}">
                                                <c:choose>
                                                    <c:when test="${sortOrder == 'ASC'}">&#9650;</c:when>
                                                    <c:otherwise>&#9660;</c:otherwise>
                                                </c:choose>
                                            </c:if>
                                        </a>
                                    </th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="test" items="${tests}">
                                    <tr>
                                        <td>${test.subjectId}</td>
                                        <td>${test.testName}</td>
                                        <td>${test.questionCount}</td>
                                        <td>
                                            <c:if test="${sessionScope.account.roleId == 3}">
                                                <form action="detailtest" method="get" class="d-inline">
                                                    <input type="hidden" name="testId" value="${test.testId}">
                                                    <button type="submit" class="btn btn-info btn-sm">Detail</button>
                                                </form>
                                                <a class="btn btn-primary btn-sm" href="edittest?testId=${test.testId}&subjectId=${test.subjectId}">Edit</a>
                                                <form action="deleteTest" method="post" class="d-inline" onsubmit="return confirm('Are you sure you want to delete this test?');">
                                                    <input type="hidden" name="testID" value="${test.testId}">
                                                    <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                                                </form>
                                            </c:if>
                                            <c:if test="${sessionScope.account.roleId == 1}">
                                                <form action="doTest" method="get" class="d-inline">
                                                    <input type="hidden" name="testId" value="${test.testId}">
                                                    <button type="submit" class="btn btn-success btn-sm">Do Test</button>
                                                </form>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>

                        <div class="pagination">
                            <c:if test="${currentPage > 1}">
                                <a href="test-list?page=${currentPage - 1}&sortBy=${sortBy}&sortOrder=${sortOrder}&searchQuery=${searchQuery}" class="btn btn-secondary">Previous</a>
                            </c:if>
                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <a href="test-list?page=${i}&sortBy=${sortBy}&sortOrder=${sortOrder}&searchQuery=${searchQuery}" class="btn btn-secondary">${i}</a>
                            </c:forEach>
                            <c:if test="${currentPage < totalPages}">
                                <a href="test-list?page=${currentPage + 1}&sortBy=${sortBy}&sortOrder=${sortOrder}&searchQuery=${searchQuery}" class="btn btn-secondary">Next</a>
                            </c:if>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <p>No tests were found.</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </body>
</html>
