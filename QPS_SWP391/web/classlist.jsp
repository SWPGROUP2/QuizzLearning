<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Class List</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <style>
            body {
                font-family: Arial, sans-serif;
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
                <div class="col-md-2">
                    <%@ include file="Components/Sidebar.jsp" %>
                </div>

                <div class="col-md-10">
                    <div class="d-flex align-items-center mb-2">
                        <a href="adminhome" class="btn btn-dark mr-2">Back to Homepage</a>
                        <h1 class="flex-grow-1 text-center mb-0">Class List</h1>
                    </div>

                    <form action="classlist" method="GET" class="mb-3">
                        <label for="classSearch">Search Class:</label>
                        <input type="text" id="classSearch" name="searchKeyword" value="${param.searchKeyword}" class="form-control d-inline-block" style="width: auto; min-width: 200px;" placeholder="Enter keyword">
                        <button type="submit" class="btn btn-primary">Search</button>
                    </form>

                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <a href="addclass" class="btn btn-primary">Add Class</a>
                    </div>

                    <c:choose>
                        <c:when test="${classList == null || classList.size() == 0}">
                            <p>No classes found.</p>
                        </c:when>
                        <c:otherwise>
                            <table class="table table-bordered">
                                <thead>
                                    <tr>
                                        <th style="width: 5%;">#</th>
                                        <th style="width: 40%;">Class Name</th>
                                        <th style="width: 20%;">Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:set var="startIndex" value="${(currentPage - 1) * 10}" />
                                    <c:forEach var="classObj" items="${classList}" varStatus="status">
                                        <tr>
                                            <td>${status.index + 1}</td>
                                            <td>${classObj.className}</td>
                                            <td class="table-actions">
                                                <a href="editclass?classId=${classObj.classID}" class="btn btn-primary btn-sm">Edit</a>
                                                <form action="deleteclass" method="POST" style="display:inline;">
                                                    <input type="hidden" name="ClassID" value="${classObj.classID}">
                                                    <button type="submit" class="btn btn-danger btn-sm" 
                                                            onclick="return confirm('Are you sure you want to delete this class?If you do all the class member will be deleted also');">
                                                        Delete
                                                    </button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:otherwise>
                    </c:choose>

                    <div class="pagination">
                        <c:if test="${currentPage > 1}">
                            <a href="?page=${currentPage - 1}" class="btn btn-secondary">Previous</a>
                        </c:if>
                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <a href="?page=${i}" class="btn btn-secondary">${i}</a>
                        </c:forEach>
                        <c:if test="${currentPage < totalPages}">
                            <a href="?page=${currentPage + 1}" class="btn btn-secondary">Next</a>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
