<%-- 
    Document   : test
    Created on : October 1, 2024, 8:15:23 PM
    Author     : ADMIN
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="models.*" %>
<%@page import="dal.*" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

        <%@include file="Components/AllAccess.jsp"%>
    </head>

    <body class="container-fluid">
        <div class="row">
            <div class="col-md-2">
                <%@include file="Components/Sidebar.jsp" %>
            </div>
            <div class="col-md-10 px-0">
                <div id="" class="mb-4">
                    <div id="">
                        <div class="container-fluid px-4 px-lg-5 row" style="margin-top: 10px">
                            <a href="adminhome" class="btn btn-dark">Back to Homepage</a> 
                            <h1>Subject List</h1>

                            <div class="mt-3 col-md-12">
                                <div class="row">
                                    <form class="form-inline mb-4 col-md-6" action="search-subject" method="GET">
                                        <div class="input-group">
                                            <input type="text" class="form-control" name="keyword" placeholder="Search by subject name" value="${param.search}">
                                            <div class="input-group-append">
                                                <button class="btn btn-primary" type="submit">Search</button>
                                            </div>
                                        </div>
                                    </form>
                                    <div class="col-md-6 d-flex justify-content-end mb-4">
                                        <a href="add-subject" class="btn btn-primary" type="submit">Add Subject</a>
                                    </div>

                                </div>
                                <c:forEach var="s" items="${listSubjectsByPagging}">
                                    <div class="col-md-12 mb-3 shadow bg-white rounded border" style="border-radius: 8px; width: 100%">
                                        <div>
                                            <div class="row align-items-center">
                                                <div class="col-md-3 text-center">
                                                    <img class="align-items-center" src="${s.getThumbnail()}" style="width: 50%; height: auto;" />
                                                </div>

                                                <div class="col-md-2" style="margin-left: -20px">
                                                    <h2 class="">${s.getSubjectName()}</h2>
                                                </div>

                                                <div class="col-md-2">
                                                    <li>${s.getTitle()}</li>
                                                </div>

                                                <!-- Cột: Nút Detail và Delete nằm cạnh nhau -->
                                                <div class="col-md-5 text-center">
                                                    <!-- Form View List Question -->


                                                    <form action="assign-teacher" method="GET" style="display:inline;">
                                                        <input type="hidden" name="subjectId" value="${s.getSubjectId()}"/>
                                                        <button type="submit" class="btn btn-primary btn-lg">Assign</button>
                                                    </form>


                                                    <a href="edit-subject?id=${s.getSubjectId()}" class="btn btn-primary btn-lg" style="display:inline;">Edit</a>
                                                    <!-- Form Delete -->
                                                    <form action="delete-subject" method="POST" style="display:inline;">
                                                        <input type="hidden" name="subjectId" value="${s.getSubjectId()}"/>
                                                        <button type="submit" class="btn btn-danger btn-lg">Delete</button>
                                                    </form>

                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
                <c:choose>
                    <c:when test="${listSubjectsByPagging==null || listSubjectsByPagging.size()==0}">
                        Not founds
                    </c:when>
                    <c:when test="${totalPage < 2}">
                        <nav aria-label="Page navigation example" class="d-flex justify-content-center">
                            <ul class="pagination">
                                <c:forEach begin="1" end="${totalPage}" var="i">
                                    <li class="page-item ${i == page?"active":""}"><a class="page-link" href="${pagination_url}page=${i}">${i}</a></li>
                                    </c:forEach>
                            </ul>
                        </nav>
                    </c:when>
                    <c:when test="${page < 2}">
                        <nav aria-label="Page navigation example" class="d-flex justify-content-center">
                            <ul class="pagination">                               
                                <c:forEach begin="1" end="${totalPage}" var="i">
                                    <li class="page-item ${i == page?"active":""}"><a class="page-link" href="${pagination_url}page=${i}">${i}</a></li>
                                    </c:forEach>
                                <li class="page-item"><a class="page-link" href="${pagination_url}page=${page+1}">Next</a></li>
                            </ul>
                        </nav>
                    </c:when>
                    <c:when test="${page+1 > totalPage}">
                        <nav aria-label="Page navigation example" class="d-flex justify-content-center">
                            <ul class="pagination">
                                <li class="page-item"><a class="page-link" href="${pagination_url}page=${page-1}">Previous</a></li>
                                    <c:forEach begin="1" end="${totalPage}" var="i">
                                    <li class="page-item ${i == page?"active":""}"><a class="page-link" href="${pagination_url}page=${i}">${i}</a></li>
                                    </c:forEach>
                            </ul>
                        </nav>
                    </c:when>
                    <c:otherwise>
                        <nav aria-label="Page navigation example" class="d-flex justify-content-center">
                            <ul class="pagination">
                                <li class="page-item"><a class="page-link" href="${pagination_url}page=${page-1}">Previous</a></li>
                                    <c:forEach begin="1" end="${totalPage}" var="i">
                                    <li class="page-item ${i == page?"active":""}"><a class="page-link" href="${pagination_url}page=${i}">${i}</a></li>
                                    </c:forEach>
                                <li class="page-item"><a class="page-link" href="${pagination_url}page=${page+1}">Next</a></li>
                            </ul>
                        </nav>
                    </c:otherwise>
                </c:choose>
            </div>

        </div>
    </body>
</html>

