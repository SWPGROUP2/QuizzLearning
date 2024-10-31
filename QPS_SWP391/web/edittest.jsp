<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Edit Test</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-2" style="border-right: 1px solid #1a1e21; background-color: #343a40">
                    <%@include file="Components/Sidebar.jsp" %>
                </div>
                <div class="col-md-10">
                    <div class="d-flex align-items-center mb-2">
                        <a href="teacherhome" class="btn btn-dark mr-8">Back to HomePage</a>
                        <h1 class="flex-grow-1 text-center mb-0">Edit Test</h1>
                    </div>

                    <form action="edittest" method="post">
                        <input type="hidden" name="testId" value="${test.getTestId()}">
                        <input type="hidden" name="subjectId" value="${test.getSubjectId()}"> 

                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>Test Name</th>
                                    <th>Class</th>
                                    <th>Subject</th>
                                    <th>Duration (minutes)</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>
                                        <input type="text" name="testName" class="form-control" value="${test.getTestName()}" required>
                                    </td>
                                    <td>
                                        <select name="class" class="form-control" required>
                                            <c:forEach var="classItem" items="${classes}">
                                                <option value="${classItem.classID}" 
                                                        <c:if test="${classItem.classID == test.getClassId()}">selected</c:if>
                                                        >${classItem.getClassName()}</option>
                                            </c:forEach>
                                        </select>

                                    </td>
                                    <td>
                                        <select name="subject" class="form-control" required>
                                            <c:forEach var="subject" items="${subjects}">
                                                <option value="${subject.subjectId}" 
                                                        <c:if test="${subject.subjectId == test.getSubjectId()}">selected</c:if>
                                                        >${subject.getSubjectName()}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td>
                                        <input type="number" name="duration" class="form-control" value="${test.getDuration()}" required>
                                    </td>
                                </tr>
                            </tbody>
                        </table>

                        <h2>Questions in Test</h2>
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th></th>
                                    <th>Chapter No</th>
                                    <th>Question</th>
                                    <th>Question Type</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="question" items="${questions}">
                                    <tr>
                                        <td><input type="checkbox" name="questionIds" value="${question.questionID}"></td>
                                        <td>${question.chapterID}</td>
                                        <td>${question.question}</td>
                                        <td>${question.questionTypeName}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <button type="submit" class="btn btn-primary">Save Changes</button>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
