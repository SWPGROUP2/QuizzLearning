<%-- 
    Document   : addTermSet
    Created on : Nov 9, 2024, 2:36:37 AM
    Author     : dell
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Add Term Set</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body class="container-fluid">
        <div class="row">
            <div class="col-md-2 p-0" >
                <%@include file="Components/Sidebar.jsp" %>
            </div>
            <div class="col-md-10 px-4 py-5">
                <h2 class="my-4">Add New Term Set</h2>
                <form action="addTermSet" method="post">
                    <div class="form-group">
                        <label for="termSetName">Term Set Name</label>
                        <input type="text" class="form-control" id="termSetName" name="termSetName" required>
                    </div>
                    <div class="form-group">
                        <label for="termSetDescription">Term Set Description</label>
                        <textarea class="form-control" id="termSetDescription" name="termSetDescription" rows="3" required></textarea>
                    </div>
                    <button type="submit" class="btn btn-primary">Add Term Set</button>
                    <a href="termset" class="btn btn-secondary">Cancel</a>
                </form>
            </div>
        </div>
    </body>
</html>
