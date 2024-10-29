<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="models.Question" %>
<%@ page import="models.Option" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Question Details</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-2" style="border-right: 1px solid #1a1e21; background-color: #343a40">
                    <%@include file="Components/Sidebar.jsp" %>
                </div>
                <div class="col-md-10">
                    <div class="d-flex justify-content-between align-items-center mb-4">
                    <a href="questionlist?id=${question.getSubjectId()}" class="btn btn-secondary">Back to Questions</a>
                    <h1>Question Details</h1>
                    <a href="editquestion?questionId=${question.getQuestionID()}" class="btn btn-primary btn-sm">Edit</a>
                    </div>
                    <table class="table table-bordered">
                        <tr>
                            <th>Question ID</th>
                            <td>${question.getQuestionID()}</td>
                        </tr>
                        <tr>
                            <th>Chapter ID</th>
                            <td>${question.getChapterId()}</td>
                        </tr>
                        <tr>
                            <th>Question</th>
                            <td>${question.getQuestion()}</td>
                        </tr>
                        <tr>
                            <th>Options</th>
                            <td>
                                <c:forEach var="option" items="${options}">
                                    ${option.optionText} - Correct: ${option.isCorrect == 1 ? 'Yes' : 'No'} <br>
                                </c:forEach>
                            </td>
                        </tr>
                    </table>
                    
                </div>
            </div>
        </div>
    </body>
</html>
