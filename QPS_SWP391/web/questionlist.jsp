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
        <style>
            .top-right-add-btn {
                position: absolute;
                top: 20px;
                right: 20px;
                z-index: 999;
            }
            .card-btns {
                position: absolute;
                top: 10px;
                right: 10px;
                z-index: 1;
            }
            .card-btns a {
                margin-left: 5px;
            }
            .card {
                position: relative;
                z-index: 1; 
            }
        </style>
    </head>
    <body>
        <!-- Add button, visible only if the user's role is not 1 (student) -->
        <c:if test="${sessionScope.account.roleId != 1}">
            <a href="addquestion?subjectId=${param.id}" class="btn btn-primary top-right-add-btn">Add</a>
        </c:if>
        
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-2" style="border-right: 1px solid #1a1e21; background-color: #343a40">
                    <%@include file="Components/Sidebar.jsp" %>
                </div>

                <div class="col-md-10">
                    <h1>Question List</h1>
                    <c:choose>
                        <c:when test="${questionList == null || questionList.size() == 0}">
                            <p>No questions found for this subject.</p>
                        </c:when>
                        <c:otherwise>                        
                            <div class="row">                           
                                <c:set var="startIndex" value="${(currentPage - 1 ) * 15 + 2}" /> <!-- Calculate the starting index -->
                                <c:forEach var="q" items="${questionList}" varStatus="status">                              
                                    <div class="col-md-4 mb-4">                                       
                                        <div class="card">                                            
                                            <!-- Buttons for edit and delete, visible only if the user's role is not 1 (student) -->
                                            <c:if test="${sessionScope.account.roleId != 1}">
                                                <div class="card-btns">
                                                    <a href="editquestion?questionId=${q.getQuestionID()}&subjectId=${param.id}" class="btn btn-primary btn-sm">Edit</a>
                                                    <form action="DeleteQuestionServlet" method="POST" style="display:inline;">
                                                        <input type="hidden" name="id" value="${q.getQuestionID()}">
                                                        <input type="hidden" name="subjectId" value="${param.id}">
                                                        <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this question?');">Delete</button>
                                                    </form>
                                                </div>
                                            </c:if>
                                            
                                            <div class="card-body">
                                                <h5 class="card-title">Question ${startIndex + status.index - 1}</h5> <!-- Display continuous number -->
                                                <p class="card-text"><strong>Question:</strong> ${q.getQuestion()}</p>
                                                <p class="card-text"><strong>Chapter:</strong> ${q.getChapterId()}</p>
                                            </div>
                                        </div>
                                    </div>
                                    <c:if test="${status.index % 3 == 2}">
                                    </div><div class="row">
                                    </c:if>
                                </c:forEach>
                            </div>
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
