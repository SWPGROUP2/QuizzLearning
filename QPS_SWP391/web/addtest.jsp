<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Add Test</title>
        <style>
            .invalid-feedback {
                display: block;
                color: #dc3545;
                margin-top: .25rem;
                font-size: 80%;
            }
            .alert {
                padding: 15px;
                margin-bottom: 20px;
                border: 1px solid transparent;
                border-radius: 4px;
            }
            .alert-warning {
                color: #856404;
                background-color: #fff3cd;
                border-color: #ffeeba;
            }
        </style>
    </head>
    <body class="container-fluid">
        <div class="row">
            <div class="col-md-2">
                <%@include file="Components/Sidebar.jsp" %>
            </div>
            <div class="col-md-10 px-4 py-4" style="margin-top: 20px">
                <h1 style="margin-bottom:20px">Add Test</h1>
                
                <c:choose>
                    <c:when test="${empty teacherSubjects}">
                        <div class="alert alert-warning">
                            <strong>Notice:</strong> You haven't been assigned to any subjects yet. 
                            Please contact the administrator to assign you to subjects before creating tests.
                        </div>
                    </c:when>
                    <c:otherwise>
                        <form action="addtest" method="post">
                            <div class="form-group">
                                <label for="subject">Subject:</label>
                                <select class="form-control" id="subject" name="subjectId" required>
                                    <c:forEach var="subject" items="${teacherSubjects}">
                                        <option value="${subject.subjectId}" ${subject.subjectId == subjectId ? 'selected' : ''}>
                                            ${subject.subjectName}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="class">Class:</label>
                                <select class="form-control" id="class" name="classId" required>
                                    <c:forEach var="classItem" items="${teacherClasses}">
                                        <option value="${classItem.classID}" ${classItem.classID == classId ? 'selected' : ''}>
                                            ${classItem.className}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="testName">Test Name:</label>
                                <input type="text" class="form-control ${not empty testNameError ? 'is-invalid' : ''}" 
                                       id="testName" name="testName" value="${testName}" required>
                                <c:if test="${not empty testNameError}">
                                    <div class="invalid-feedback">
                                        ${testNameError}
                                    </div>
                                </c:if>
                            </div>
                            <div class="form-group">
                                <label for="duration">Duration (minutes):</label>
                                <input type="number" class="form-control" id="duration" 
                                       name="duration" value="${duration}" required>
                            </div>
                            <div class="form-group">
                                <label for="testStartDate">Test Start Time:</label>
                                <input type="datetime-local" class="form-control" id="testStartDate" 
                                       name="testStartDate" value="${testStartDate}" required>
                            </div>
                            <div class="form-group">
                                <label for="testEndDate">Test End Time:</label>
                                <input type="datetime-local" class="form-control" id="testEndDate" 
                                       name="testEndDate" value="${testEndDate}" required>
                            </div>
                            <button type="submit" class="btn btn-primary">Continue</button>
                        </form>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </body>
</html>