<%-- 
    Document   : addSubject
    Created on : Oct 22, 2024, 9:14:29 PM
    Author     : dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Add Subject</title>
        <%@include file="Components/AllAccess.jsp"%>       
    </head>
    <body class="container-fluid bg-light">
        <div class="row min-vh-100">
            <!-- Sidebar -->
            <div class="col-md-2 p-0" style="border-right: 1px solid #1a1e21; background-color: #343a40;">
                <%@include file="Components/Sidebar.jsp" %>
            </div>

            <!-- Main content -->
            <div class="col-md-10 px-4 py-5">
                <div class="container">
                    <div class="card shadow-sm">
                        <div class="card-header bg-dark text-white">
                            <h2 class="mb-0">Add New Subject</h2>
                        </div>
                        <div class="card-body">
                            <form action="add-subject" method="post">
                                <!-- Name Input -->
                                <div class="form-group">
                                    <label for="name">Name</label>
                                    <input type="text" class="form-control" id="name" name="name" placeholder="Enter subject name" required>
                                </div>

                                <!-- Title Input -->
                                <div class="form-group">
                                    <label for="title">Title</label>
                                    <textarea class="form-control" id="title" name="title" rows="4" placeholder="Enter subject title" required></textarea>
                                </div>

                                <!-- Submit Button -->
                                <button type="submit" class="btn btn-primary">Add Subject</button>
                            </form>

                            <!-- Error Message -->
<!--                            <c:if test="${not empty error}">
                                <div class="alert alert-danger mt-3">${error}</div>
                            </c:if>-->
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script>
            <% if (request.getAttribute("message") != null) { %>
                alert("<%= request.getAttribute("message") %>");
                window.location.href = "subject-list"; // Chuyển hướng sau khi alert
            <% } else if (request.getAttribute("error") != null) { %>
                alert("<%= request.getAttribute("error") %>");
            <% } %>
        </script>
    </body>
</html>