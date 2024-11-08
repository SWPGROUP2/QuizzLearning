<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Add Test</title>
    </head>
    <body class="container-fluid">
        <div class="row">
            <div class="col-md-2" style="border-right: 1px solid #1a1e21; background-color: #343a40">
                <%@include file="Components/Sidebar.jsp" %>
            </div>
            <div class="col-md-10 px-4 py-4" style="margin-top: 20px">
                <h1 style="margin-bottom:20px">Add Test</h1>

                <form action="addtest" method="post">
                    <div class="form-group">
                        <label for="subject">Subject:</label>
                        <select class="form-control" id="subject" name="subjectId" required>
                            <c:forEach var="subject" items="${teacherSubjects}">
                                <option value="${subject.subjectId}">${subject.subjectName}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="class">Class:</label>
                        <select class="form-control" id="class" name="classId" required>
                            <c:forEach var="classItem" items="${teacherClasses}">
                                <option value="${classItem.classID}">${classItem.className}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="testName">Test Name:</label>
                        <input type="text" class="form-control" id="testName" name="testName" required>
                    </div>

                    <div class="form-group">
                        <label for="duration">Duration (minutes):</label>
                        <input type="number" class="form-control" id="duration" name="duration" required>
                    </div>

                    <div class="form-group">
                        <label for="testStartDate">Test Start Time:</label>
                        <input type="datetime-local" class="form-control" id="testStartDate" name="testStartDate" required>
                    </div>

                    <div class="form-group">
                        <label for="testEndDate">Test End Time:</label>
                        <input type="datetime-local" class="form-control" id="testEndDate" name="testEndDate" required>
                    </div>

                    <button type="submit" class="btn btn-primary">Continue</button>
                </form>
            </div>
        </div>
    </body>
</html>
