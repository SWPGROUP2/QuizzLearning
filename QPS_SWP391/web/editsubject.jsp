<%-- 
    Document   : editsubject
    Created on : Nov 9, 2024, 4:34:07 AM
    Author     : dell
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.subject" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Edit Subject</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body class="container-fluid">
        <div class="row">
            <div class="col-md-2 p-0" style="border-right: 1px solid #1a1e21; background-color: #343a40;">
                <%@include file="Components/Sidebar.jsp" %>
            </div>
            <div class="col-md-10 px-4 py-5">
                <h2>Edit Subject</h2>
                <!-- Show error if any -->
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>

                <!-- Subject Update Form -->
                <form action="edit-subject" method="POST">
                    <input type="hidden" name="subjectId" value="${subject.subjectId}"/>

                    <div class="form-group">
                        <label for="name">Subject Name</label>
                        <input type="text" class="form-control" id="name" name="name" value="${subject.getSubjectName()}" required />
                    </div>

                    <div class="form-group">
                        <label for="description">Subject Description</label>
                        <textarea class="form-control" id="description" name="description" required>${subject.getTitle()}</textarea>
                    </div>

                    <button type="submit" class="btn btn-primary">Update Subject</button>
                </form>

                <a href="subject-list" class="btn btn-secondary mt-3">Back to Subject List</a>
            </div>
        </div>
    </body>
</html>