<%-- 
    Document   : assignteacher
    Created on : Nov 8, 2024, 6:11:44 AM
    Author     : dell
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Assign Teacher to Subject</title>
    </head>
    <body>
        <h1>Assign Teacher to Subject: ${subject.subjectName}</h1>
        
        <!-- Form để gán giáo viên vào môn học -->
        <form action="assign-teacher" method="POST">
            <input type="hidden" name="subjectId" value="${subject.subjectId}" />
            
            <c:forEach var="teacher" items="${teachers}">
                <div>
                    <input type="checkbox" name="teacherId" value="${teacher.getUserId()}" />
                    <label for="teacherId">${teacher.getFullName()}</label>
                </div>
            </c:forEach>

            <button type="submit" class="btn btn-primary">Assign</button>
        </form>
        
        <a href="subject-list" class="btn btn-secondary">Back to Subject List</a>
    </body>
</html>