<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Term Sets</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <style>
            body {
                font-family: Arial, sans-serif;
            }
            .card {
                margin-bottom: 20px;
            }
            .search-form {
                width: 400px; /* Adjust this value to control the form's width */
            }
        </style>
    </head>
    <body class="container-fluid">
        <div class="row">
            <div class="col-md-2">
                <%@include file="/Components/Sidebar.jsp" %>
            </div>
            <div class="col-md-10">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <a href="studenthome" class="btn btn-dark mr-8">Back to Homepage</a>
                    <h2>Term Sets</h2>
                    <a href="addTermSet.jsp" class="btn btn-primary">Add Term Set</a>
                </div>

                <!-- Search Form with Button to the Right -->
                <form method="GET" action="termsets" class="search-form mb-3">
                    <div class="input-group">
                        <input type="text" name="searchQuery" value="${param.searchQuery}" class="form-control" placeholder="Search by Term Set Name or Description">
                        <div class="input-group-append">
                            <button type="submit" class="btn btn-primary">Search</button>
                        </div>
                    </div>
                </form>

                <div class="row mt-3">
                    <c:forEach var="termSet" items="${termSets}">
                        <div class="col-md-4"> 
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">${termSet.termSetName}</h5>
                                    <p class="card-text">Description: ${termSet.termSetDescription}</p>
                                    <a href="termlist?termSetId=${termSet.termSetId}" class="btn btn-primary">View Details</a>
                                    <a href="deletetermset?id=${termSet.termSetId}" class="btn btn-danger float-right">Delete</a>
                                    <a href="updatetermset?id=${termSet.termSetId}" class="btn btn-primary float-right" style="margin-right:10px">Edit</a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>

                    <c:if test="${empty termSets}">
                        <div class="col-12">
                            <div class="alert alert-warning" role="alert">
                                No term sets found.
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </body>
</html>
