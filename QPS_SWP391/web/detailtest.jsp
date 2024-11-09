<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Test Details</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-2">
                    <%@include file="Components/Sidebar.jsp" %>
                </div>
                <div class="col-md-10">
                    <div class="d-flex align-items-center mb-3">
                        <a href="teacherhome" class="btn btn-dark mr-3">Back to HomePage</a>
                        <h1 class="flex-grow-1 text-center mb-0">Test Details</h1>
                    </div>

                    <div class="card mb-4">
                        <div class="card-header">
                            <h4>Test Information</h4>
                        </div>
                        <div class="card-body">
                            <table class="table table-bordered">
                                <tr>
                                    <th style="width: 200px">Test Name:</th>
                                    <td>${test.testName}</td>
                                </tr>
                                <tr>
                                    <th>Subject:</th>
                                    <td>${subjectName}</td>
                                </tr>
                                <tr>
                                    <th>Class:</th>
                                    <td>${className}</td>  
                                </tr>
                                <tr>
                                    <th>Duration:</th>
                                    <td>${test.duration} minutes</td>
                                </tr>

                            </table>
                        </div>
                    </div>

                    <div class="card">
                        <div class="card-header">
                            <h4>Questions List</h4>
                        </div>
                        <div class="card-body">
                            <table class="table table-striped table-bordered">
                                <thead class="thead-dark">
                                    <tr>
                                        <th>#</th>
                                        <th>Chapter</th>
                                        <th>Question</th>
                                        <th>Question Type</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="question" items="${questions}" varStatus="status">
                                        <tr>
                                            <td>${status.index + 1}</td>
                                            <td>Chapter ${question.chapterId}</td>
                                            <td>${question.question}</td>
                                            <td>${question.questionTypeName}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>