<%-- 
    Document   : updatetermset
    Created on : Nov 9, 2024, 3:17:35 AM
    Author     : dell
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Update TermSet</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body class="container-fluid">
        <div class="row">
            <div class="col-md-2 p-0">
                <%@include file="Components/Sidebar.jsp" %>
            </div>
            <div class="col-md-10 px-4 py-5">
                <h2>Update TermSet</h2>
                <form action="UpdateTermSetController" method="post">
                    <input type="hidden" name="termSetId" value="${termSet.termSetId}">
                    <div class="form-group">
                        <label for="termSetName">TermSet Name</label>
                        <input type="text" class="form-control" id="termSetName" name="termSetName" value="${termSet.termSetName}" required>
                    </div>
                    <div class="form-group">
                        <label for="termSetDescription">Description</label>
                        <textarea class="form-control" id="termSetDescription" name="termSetDescription" required>${termSet.termSetDescription}</textarea>
                    </div>
                    <button type="submit" class="btn btn-primary">Update</button>
                </form>
                <a href="termset" class="btn btn-secondary mt-3">Back to List</a>
            </div>
        </div>
    </body>
</html>