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
        </style>
    </head>
    <body class="container-fluid">
        <div class="row">
            <div class="col-md-2" style="border-right: 1px solid #1a1e21; background-color: #343a40">
                <%@include file="/Components/Sidebar.jsp" %>
            </div>
            <div class="col-md-10">
                <h2>Term Sets</h2>
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>Term Set ID</th>
                            <th>Term Set Name</th>
                            <th>Description</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="termSet" items="${termSets}">
                            <tr>
                                <td>${termSet.termSetId}</td>
                                <td>${termSet.termSetName}</td>
                                <td>${termSet.termSetDescription}</td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty termSets}">
                            <tr>
                                <td colspan="5">No term sets found.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
