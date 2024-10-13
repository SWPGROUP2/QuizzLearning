<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="models.*" %>
<%@page import="dal.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Question List</title>
</head>
<body>
    <div class="container">
        <h1>Question List</h1>

        <c:choose>
            <c:when test="${questionList == null || questionList.size() == 0}">
                <p>No questions found for this subject.</p>
            </c:when>
            <c:otherwise>
                <table class="table">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Question</th>
                            <th>Definition</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="q" items="${questionList}">
                            <tr>
                                <td>${q.getQuestionID()}</td>
                                <td>${q.getQuestion()}</td>
                                <td>${q.getDefinition()}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>