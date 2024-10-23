<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="models.*" %>
<%@ page import="dal.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Question List</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-2" style="border-right: 1px solid #1a1e21; background-color: #343a40">
                    <%@include file="Components/Sidebar.jsp" %>
                </div>

                <div class="col-md-10">
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <h1>Question List</h1>
                        <c:if test="${sessionScope.account.roleId != 1}">
                            <a href="addquestion?subjectId=${param.id}" class="btn btn-primary">Add</a>
                        </c:if>
                    </div>

                    <form action="questionlist" method="GET" class="form-inline mb-3">
                        <input type="hidden" name="id" value="${param.id}"> <!-- Include subjectId as 'id' -->
                        <label for="chapterFilter" class="mr-2">Filter by Chapter:</label>
                        <select id="chapterFilter" name="chapterId" class="form-control mr-2">
                            <option value="">All Chapters</option>
                            <c:forEach var="chapter" items="${chapterSet}">
                                <option value="${chapter}" 
                                    <c:if test="${param.chapterId == chapter}">selected</c:if>>Chapter ${chapter}</option>
                            </c:forEach>
                        </select>
                        <button type="submit" class="btn btn-primary">Filter</button>
                    </form>

                    <c:choose>
                        <c:when test="${questionList == null || questionList.size() == 0}">
                            <p>No questions found for this subject.</p>
                        </c:when>
                        <c:otherwise>
                            <table class="table table-bordered">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Chapters</th>
                                        <th>Questions</th>
                                        <th>Question Type</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:set var="startIndex" value="${(currentPage - 1) * 15 + 1}" /> 
                                    <c:forEach var="q" items="${questionList}" varStatus="status">
                                        <tr>
                                            <td>${status.index + 1}</td> 
                                            <td>${q.getChapterId()}</td>
                                            <td>${q.getQuestion()}</td>
                                            <td>${q.getQuestionTypeId()}</td>                                         

                                            <td>
                                                <c:if test="${sessionScope.account.roleId != 1}">
                                                    <a href="editquestion?questionId=${q.getQuestionID()}&subjectId=${param.id}" class="btn btn-primary btn-sm">Edit</a>
                                                    <form action="DeleteQuestionServlet" method="POST" style="display:inline;">
                                                        <input type="hidden" name="id" value="${q.getQuestionID()}">
                                                        <input type="hidden" name="subjectId" value="${param.id}">
                                                        <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this question?');">Delete</button>
                                                    </form>
                                                </c:if>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:otherwise>
                    </c:choose>

                    <div class="pagination">
                        <c:if test="${currentPage > 1}">
                            <a href="?id=${param.id}&chapterId=${param.chapterId}&page=${currentPage - 1}" class="btn btn-secondary">Previous</a>
                        </c:if>
                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <a href="?id=${param.id}&chapterId=${param.chapterId}&page=${i}" class="btn btn-secondary">${i}</a>
                        </c:forEach>
                        <c:if test="${currentPage < totalPages}">
                            <a href="?id=${param.id}&chapterId=${param.chapterId}&page=${currentPage + 1}" class="btn btn-secondary">Next</a>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
