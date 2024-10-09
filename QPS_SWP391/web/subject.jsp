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
        <!-- Custom scripts -->
        <%@include file="Components/AllAccess.jsp"%>
      
     
    </head>

    <body class="sb-sidenav-toggled">
        <div>
            <div>
                <div id="layoutSidenav" class="mb-4">
                    <div id="layoutSidenav_content">
                        <div class="container-fluid px-4 px-lg-5 mb-5 row" style="margin-top: 91px">
                            <h1>Subject List</h1>
                            <div class="col-6">
                              
                            </div>

                            <div class ="row mt-5 col-md-12">
                                <c:forEach var="s" items="${listSubjectsByPagging}">
                                    <div class="col-md-4 mb-3 mx-auto d-block shadow p-3  bg-white rounded border" style="padding: 10px 0px 10px 10px; border-radius: 8px; width: 32%; margin-left: 10px">
                                        <form action="subject-list" method="POST">
                                            <img class=" d-block" src="${s.getThumbnail()}" />
                                            <h2 class="text-center"> ${s.getSubjectName()} </h2>
                                            <ul style="margin-left: 8%">
                                                <li><b>title: </b>${s.getTitle()}</li>
                                                <li><b>price  :</b>3200</li>
                                            </ul>
                                            <div class="text-center">
                                                <a href="subject-detail?id=${s.getSubjectId()}" class="btn btn-primary">Detail</a>
                                            </div>

                                        </form>
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

