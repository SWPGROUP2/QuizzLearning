<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Term" %>
<%@ page import="dal.TermDAO" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Terms List</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <style>
            body {
                font-family: Arial, sans-serif;
            }
            table {
                width: 100%;
                border-collapse: collapse;
            }
            th, td {
                padding: 10px;
                text-align: left;
            }
            th {
                background-color: #f2f2f2;
            }
            .search-form {
                width: 400px;
            }
        </style>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-2">
                    <%@include file="Components/Sidebar.jsp" %>
                </div>
                <div class="col-md-10">
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <a href="termset" class="btn btn-dark mr-8">Back to Term set</a>
                        <h2>Terms List</h2>
                        <a href="addterm?termSetId=${param.termSetId}" class="btn btn-primary">Add Term</a>
                    </div>

                    <form action="termlist?action=list" method="GET" class="search-form">
                        <input type="hidden" name="termSetId" value="${param.termSetId}" />
                        <div class="input-group">
                        <input type="text" name="searchQuery" value="${searchQuery}" class="form-control" placeholder="Search by Term or Definition" class="form-control"/>
                        <div class="input-group-append">
                        <button type="submit" class="btn btn-primary">Search</button>
                        </div>
                        </div>
                    </form>

                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>Term ID</th>
                                <th>Term</th>
                                <th>Definition</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="term" items="${terms}">
                                <tr>
                                    <td>${term.termId}</td>
                                    <td>${term.term}</td>
                                    <td>${term.definition}</td>
                                    <td>
                                        <a href="editterm?termId=${term.termId}" class="btn btn-primary">Edit</a>
                                        <a href="deleteterm?termId=${term.termId}" class="btn btn-danger" onclick="return confirm('Are you sure you want to delete this term?');">Delete</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>
