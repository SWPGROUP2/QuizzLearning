<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="models.*" %>
<%@ page import="dal.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chapter List</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <style>
            .top-right-add-btn {
                position: absolute;
                top: 20px;
                right: 20px;
                z-index: 999;
            }
            .card-btns {
                position: absolute;
                top: 10px;
                right: 10px;
                z-index: 1;
            }
            .card-btns a {
                margin-left: 5px;
            }
            .card {
                position: relative;
                z-index: 1; 
            }
        </style>
    </head>
    <body>
        <!-- Add button, visible only if the user's role is not 1 (student) -->
        <c:if test="${sessionScope.account.roleId != 1}">
            <a href="addchapter?subjectId=${param.id}" class="btn btn-primary top-right-add-btn">Add</a>
        </c:if>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-2" style="border-right: 1px solid #1a1e21; background-color: #343a40">
                    <%@include file="Components/Sidebar.jsp" %>
                </div>

                <div class="col-md-10">
                    <h1>Chapter List</h1>
                    <c:choose>
                        <c:when test="${chapterList == null || chapterList.size() == 0}">
                            <p>No chapters found for this subject.</p>
                        </c:when>
                        <c:otherwise>                        
                            <div class="row">
                                <!-- Define startIndex to ensure continuous numbering of chapters -->
                                <c:set var="startIndex" value="${(currentPage - 1) * itemsPerPage + 1}" /> 
                                <!-- Loop through the chapter list -->
                                <c:forEach var="c" items="${chapterList}" varStatus="status">                              
                                    <div class="col-md-4 mb-4">                                       
                                        <div class="card">
                                            <!-- Chapter details -->
                                            <div class="card-body">
                                                <h5 class="card-title">Chapter ${startIndex + status.index}</h5> <!-- Continuous numbering -->
                                                <p class="card-text"><strong>Chapter Name:</strong> ${q.getChapterName()}</p>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- Create a new row after every 3 chapters -->
                                    <c:if test="${status.index % 3 == 2}">
                                        </div><div class="row">
                                    </c:if>
                                </c:forEach>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </body>
</html>
