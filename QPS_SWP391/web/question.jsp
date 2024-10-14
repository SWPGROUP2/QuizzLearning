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
    <!-- Bootstrap CSS for styling and button -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    
    <style>
        /* Position the edit button at the top-right corner of the webpage */
        .top-right-edit-btn {
            position: fixed;
            top: 20px;
            right: 20px;
            z-index: 999;
        }
    </style>
</head>
<body>
    <!-- Edit button at the top-right corner -->
    <a href="editAllQuestions.jsp" class="btn btn-success top-right-edit-btn">Edit</a>
    
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
                        <!-- Start a row for the grid -->
                        <div class="row">
                            <!-- Loop through the question list -->
                            <c:forEach var="q" items="${questionList}" varStatus="status">
                                <!-- Define column width for each card (col-md-4 for 3 columns per row) -->
                                <div class="col-md-4 mb-4">
                                    <!-- Card structure to hold question data -->
                                    <div class="card">
                                        <div class="card-body">
                                            <h5 class="card-title">Question ${q.getQuestionID()}</h5>
                                            <p class="card-text"><strong>Question:</strong> ${q.getQuestion()}</p>
                                            <p class="card-text"><strong>Definition:</strong> ${q.getDefinition()}</p>
                                        </div>
                                    </div>
                                </div>
                                
                                <!-- Create a new row every 3 cards -->
                                <c:if test="${status.index % 3 == 2}">
                                    </div><div class="row">
                                </c:if>
                            </c:forEach>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</body>
</html>
