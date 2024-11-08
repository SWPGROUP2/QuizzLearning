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
        .sidebar {
            background-color: #343a40;
        }
        .filter-form {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
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
</head>
<body>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-2 sidebar">
                <%@include file="Components/Sidebar.jsp" %>
            </div>
            <div class="col-md-10">
                <div class="d-flex align-items-center mb-2">
                    <c:choose>
                        <c:when test="${sessionScope.account.roleId == 3}">
                            <a href="teacherhome" class="btn btn-dark mr-2">Back to Homepage</a>
                        </c:when>
                        <c:when test="${sessionScope.account.roleId == 1}">
                            <a href="studenthome" class="btn btn-dark mr-2">Back to Homepage</a>
                        </c:when>
                    </c:choose>
                    <h1 class="text-center flex-grow-1 mb-0">Tests List</h1>
                    <c:if test="${sessionScope.account.roleId == 3}">
                        <a href="addtest" class="btn btn-primary">Add Test</a>
                    </c:if>
                </div>

                <!-- Search and Filter Form -->
                <form class="filter-form mb-3" action="test-list" method="GET">
                    <label for="searchQuery">Search Test:</label>
                    <input type="text" id="searchQuery" name="searchQuery" value="${searchQuery}" placeholder="Search test by name" class="form-control"/>

                    <!-- Class Filter -->
                    <label for="classId">Class:</label>
                    <select id="classId" name="classId" class="form-control">
                        <option value="">All Classes</option>
                        <c:forEach var="entry" items="${uniqueClasses}">
                            <option value="${entry.classID}" ${entry.classID == selectedClassId ? 'selected' : ''}>${entry.className}</option>
                        </c:forEach>
                    </select>

                    <!-- Subject Filter -->
                    <label for="subjectId">Subject:</label>
                    <select id="subjectId" name="subjectId" class="form-control">
                        <option value="">All Subjects</option>
                        <c:forEach var="entry" items="${uniqueSubjects}">
                            <option value="${entry.key}" ${entry.key == selectedSubjectId ? 'selected' : ''}>${entry.value}</option>
                        </c:forEach>
                    </select>

                    <button class="btn btn-primary" type="submit">Filter</button>
                </form>

                <!-- Tests Table -->
                <c:choose>
                    <c:when test="${not empty tests}">
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Class</th>
                                    <th>Subject</th>
                                    <th>Test Name</th>
                                    <th>Duration</th>
                                    <th>Questions in Test</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="startIndex" value="${(currentPage - 1) * testsPerPage}" />
                                <c:forEach var="test" items="${tests}" varStatus="status">
                                    <tr>
                                        <td>${startIndex + status.index + 1}</td>
                                        <td>${test.className}</td>
                                        <td>${test.subjectName}</td>
                                        <td>${test.testName}</td>
                                         <td>${test.duration}</td>
                                        <td>${test.questionCount}</td>
                                        <td class="table-actions">
                                            <c:if test="${sessionScope.account.roleId == 3}">
                                                <a href="detailtest?testId=${test.testId}" class="btn btn-info btn-sm">Detail</a>
                                                <a href="edittest?testId=${test.testId}&subjectId=${test.subjectId}" class="btn btn-primary btn-sm">Edit</a>
                                                <form action="deletetest" method="post" class="d-inline" onsubmit="return confirm('Are you sure you want to delete this test?');">
                                                    <input type="hidden" name="testID" value="${test.testId}">
                                                    <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                                                </form>
                                            </c:if>
                                            <c:if test="${sessionScope.account.roleId == 1}">
                                                <a href="doTest?testId=${test.testId}" class="btn btn-success btn-sm">Do Test</a>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>

                        <!-- Pagination -->
                        <div class="pagination">
                            <c:if test="${currentPage > 1}">
                                <a href="test-list?page=${currentPage - 1}" class="btn btn-secondary">Previous</a>
                            </c:if>
                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <a href="test-list?page=${i}" class="btn btn-secondary">${i}</a>
                            </c:forEach>
                            <c:if test="${currentPage < totalPages}">
                                <a href="test-list?page=${currentPage + 1}" class="btn btn-secondary">Next</a>
                            </c:if>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <p>No tests were found.</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</body>
</html>
