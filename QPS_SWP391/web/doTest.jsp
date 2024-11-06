<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.Question, models.Option" %>
<%@ page import="java.util.List, java.util.Map" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <%@include file="Components/AllAccess.jsp"%>    
        <title>Do Test</title>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-2 p-0" style="border-right: 1px solid #1a1e21; background-color: #343a40;">
                    <%@include file="Components/Sidebar.jsp" %>
                </div>
                <div class="col-md-10">
                    <div class="container mt-5">
                        <h1 class="text-center mb-4">Bài kiểm tra ID: ${testId}</h1>
                        <form action="submitTest" method="post">
                            <input type="hidden" name="testId" value="${testId}">
                            <c:forEach var="entry" items="${questionOptionsMap.entrySet()}">
                                <c:set var="question" value="${entry.key}" />
                                <c:set var="options" value="${entry.value}" />

                                <div class="card mb-4">
                                    <div class="card-header">
                                        <h5 class="mb-0">Câu hỏi: ${question.getQuestion()}</h5>
                                    </div>
                                    <div class="card-body">
                                        <c:forEach var="option" items="${options}">
                                            <div class="form-check">
                                                <input type="radio" class="form-check-input" name="question_${question.questionID}" value="${option.optionId}" id="option_${option.optionId}">
                                                <label class="form-check-label" for="option_${option.optionId}">
                                                    ${option.optionText}
                                                </label>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </c:forEach>
                            <div class="text-center">
                                <button type="submit" class="btn btn-primary">Nộp bài</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>