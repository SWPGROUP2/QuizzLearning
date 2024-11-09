<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="models.Question" %>
<%@ page import="models.Option" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Question Details</title>
        <style>
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }
            th, td {
                padding: 12px;
                border: 1px solid #ddd;
                text-align: left;
            }
            th {
                background-color: #f4f4f4;
            }
            td {
                word-wrap: break-word;
            }
            tr:nth-child(even) {
                background-color: #f9f9f9;
            }
            tr:hover {
                background-color: #f1f1f1;
            }
            .options {
                padding: 10px 0;
                margin-top: 10px;
                border-top: 1px solid #ddd;
                padding-left: 10px;
            }
            .options div {
                margin-bottom: 5px;
            }
        </style>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-2">
                    <%@include file="Components/Sidebar.jsp" %>
                </div>
                <div class="col-md-10">
                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <a href="questionlist" class="btn btn-dark">Back to Questions</a>
                        <h1>Question Details</h1>
                        <a href="editquestion?questionId=${question.questionID}" class="btn btn-primary btn-sm">Edit</a>
                    </div>
                    <table>
                        <tr>
                            <th>Question ID</th>
                            <td>${question.questionID}</td>
                        </tr>
                        <tr>
                            <th>Subject</th>
                            <td>${question.subjectName != null ? question.subjectName : "N/A"}</td>
                        </tr>
                        <tr>
                            <th>Chapter ID</th>
                            <td>${question.chapterId}</td>
                        </tr>
                        <tr>
                            <th>Question</th>
                            <td>${question.question}</td>
                        </tr>
                        <tr>
                            <th>Options</th>
                            <td class="options">
                                <c:forEach var="option" items="${options}">
                                    <div>${option.optionText} - Correct: ${option.isCorrect == 1 ? 'Yes' : 'No'}</div>
                                </c:forEach>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>
