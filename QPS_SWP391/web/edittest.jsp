<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
    <head>
        <title>Edit Test</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <style>
            .question-count {
                color: #007bff;
                font-weight: normal;
                margin-left: 10px;
            }
        </style>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-2" style="border-right: 1px solid #1a1e21; background-color: #343a40">
                    <%@include file="Components/Sidebar.jsp" %>
                </div>
                <div class="col-md-10">
                    <div class="d-flex align-items-center mb-2">
                        <a href="teacherhome" class="btn btn-dark mr-8">Back to HomePage</a>
                        <h1 class="flex-grow-1 text-center mb-0">Edit Test</h1>
                    </div>

                    <form action="edittest" method="post">
                        <input type="hidden" name="testId" value="${test.getTestId()}">
                        <input type="hidden" name="subjectId" value="${test.getSubjectId()}">

                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>Test Name</th>
                                    <th>Class</th>
                                    <th>Duration (minutes)</th>
                                    <th>Start Time</th>
                                    <th>End Time</th>
                                    <th>Status</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>
                                        <input type="text" name="testName" class="form-control" value="${test.testName}" required>
                                    </td>
                                    <td>
                                        <select class="form-control" id="class" name="classId" required>
                                            <c:forEach var="classItem" items="${teacherClasses}">
                                                <option value="${classItem.classID}">${classItem.className}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td>
                                        <input type="number" name="duration" class="form-control" value="${test.duration}" required>
                                    </td>
                                    <td>
                                        <input type="datetime-local" name="startTime" class="form-control" value="${fn:replace(test.testStartTime, ' ', 'T')}" required>
                                    </td>
                                    <td>
                                        <input type="datetime-local" name="endTime" class="form-control" value="${fn:replace(test.testEndTime, ' ', 'T')}" required>
                                    </td>
                                    <td>
                                        <select name="status" class="form-control" required>
                                            <option value="1" <c:if test="${test.status == 1}">selected</c:if>>Active</option>
                                            <option value="0" <c:if test="${test.status == 0}">selected</c:if>>Inactive</option>
                                        </select>
                                    </td>
                                </tr>
                            </tbody>
                        </table>

                        <h2>
                            Questions in Test
                            <span class="question-count">Selected: <span id="selectedCount">0</span></span>
                        </h2>
                        
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th></th>
                                    <th>Chapter No</th>
                                    <th>Question</th>
                                    <th>Question Type</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="question" items="${questions}">
                                    <tr>
                                        <td>
                                            <input type="checkbox" 
                                                   class="question-checkbox"
                                                   name="questionIds" 
                                                   value="${question.questionID}"
                                                   <c:if test="${fn:contains(selectedQuestionIds, question.questionID)}">checked</c:if> 
                                                   >
                                        </td>
                                        <td>${question.chapterId}</td>
                                        <td>${question.question}</td>
                                        <td>${question.questionTypeName}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>

                        <button type="submit" class="btn btn-primary">Save Changes</button>
                    </form>
                </div>
            </div>
        </div>

        <script>
            function updateSelectedCount() {
                const checkboxes = document.getElementsByClassName('question-checkbox');
                const selectedCount = Array.from(checkboxes).filter(cb => cb.checked).length;
                document.getElementById('selectedCount').textContent = selectedCount;
            }

            document.addEventListener('DOMContentLoaded', function() {
                const checkboxes = document.getElementsByClassName('question-checkbox');
                Array.from(checkboxes).forEach(checkbox => {
                    checkbox.addEventListener('change', updateSelectedCount);
                });
                
                updateSelectedCount();
            });
        </script>
    </body>
</html>