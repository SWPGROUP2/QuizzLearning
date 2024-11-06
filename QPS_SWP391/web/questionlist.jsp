<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Question List</title>
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
        </style>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-2" style="border-right: 1px solid #1a1e21; background-color: #343a40">
                    <%@ include file="Components/Sidebar.jsp" %>
                </div>

                <div class="col-md-10">
                    <div class="d-flex align-items-center mb-2">
                        <a href="teacherhome" class="btn btn-dark mr-8">Back to Homepage</a>
                        <h1 class="flex-grow-1 text-center mb-0">Question List</h1>
                        <form action="questionlist" method="GET" class="form-inline mb-3">
                            <label for="subjectFilter" class="mr-2">Subject:</label>
                            <select id="subjectFilter" name="subjectId" class="form-control mr-2">
                                <option value="">All Subjects</option>
                                <c:forEach var="entry" items="${uniqueSubjects}">
                                    <option value="${entry.key}" 
                                            <c:if test="${param.subjectId  == entry.key}">selected</c:if>>
                                        ${entry.value}
                                    </option>
                                </c:forEach>
                            </select>


                            <label for="chapterFilter" class="mr-2">Chapter:</label>
                            <select id="chapterFilter" name="chapterId" class="form-control mr-2">
                                <option value="">All Chapters</option>
                                <c:forEach var="chapterId" items="${uniqueChapters}">
                                    <option value="${chapterId}" 
                                            <c:if test="${param.chapterId == chapterId}">selected</c:if>>
                                        Chapter ${chapterId}
                                    </option>
                                </c:forEach>
                            </select>

                            <label for="questionTypeFilter" class="mr-2">Question Type:</label>
                            <select id="questionTypeFilter" name="questionTypeId" class="form-control mr-2">
                                <option value="">All Types</option>
                                <c:forEach var="entry" items="${uniqueQuestionTypes}">
                                    <option value="${entry.key}" 
                                            <c:if test="${param.questionTypeId == entry.key}">selected</c:if>>
                                        ${entry.value}
                                    </option>
                                </c:forEach>
                            </select>

                            <button type="submit" class="btn btn-primary">Filter</button>
                        </form>
                    </div>
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <c:if test="${sessionScope.account.roleId != 1}">
                            <a href="addquestion?subjectId=${param.id}" class="btn btn-primary">Add</a>
                        </c:if>
                    </div>
                    <c:choose>
                        <c:when test="${questionList == null || questionList.size() == 0}">
                            <p>No questions found for this subject.</p>
                        </c:when>
                        <c:otherwise>
                            <table class="table table-bordered">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Subject</th>
                                        <th>Chapter</th>
                                        <th>Questions</th>
                                        <th>Question Type</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:set var="startIndex" value="${(currentPage - 1) * 10}" />
                                    <c:forEach var="q" items="${questionList}" varStatus="status">
                                        <tr>
                                            <td>${startIndex + status.index + 1}</td>    
                                            <td>${q.subjectName}</td>
                                            <td>${q.chapterId}</td>
                                            <td>${q.question}</td>
                                            <td>${q.questionTypeName}</td>
                                            <td>
                                                <a href="editquestion?questionId=${q.questionID}&subjectId=${param.id}" class="btn btn-primary btn-sm">Edit</a>
                                                <form action="DeleteQuestionServlet" method="POST" style="display:inline;">
                                                    <input type="hidden" name="id" value="${q.questionID}">
                                                    <input type="hidden" name="subjectId" value="${param.id}">
                                                    <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this question?');">Delete</button>
                                                </form>
                                                <a href="detailquestion?questionId=${q.questionID}&subjectId=${param.id}" class="btn btn-info btn-sm">Detail</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:otherwise>
                    </c:choose>

                    <div class="pagination">
                        <c:if test="${currentPage > 1}">
                            <a href="?id=${param.id}&page=${currentPage - 1}" class="btn btn-secondary">Previous</a>
                        </c:if>
                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <a href="?id=${param.id}&page=${i}" class="btn btn-secondary">${i}</a>
                        </c:forEach>
                        <c:if test="${currentPage < totalPages}">
                            <a href="?id=${param.id}&page=${currentPage + 1}" class="btn btn-secondary">Next</a>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
