<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Add New Question</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <style>
            body {
                background-color: #f8f9fa;
            }
            .card {
                border: none;
                border-radius: 15px;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            }
            .btn-success {
                border-radius: 30px;
            }
            .form-control {
                border-radius: 20px;
            }
            .alert-warning {
                color: #856404;
                background-color: #fff3cd;
                border-color: #ffeeba;
                padding: 15px;
                margin-bottom: 20px;
                border-radius: 4px;
            }
        </style>
        <script>
            function showOptions() {
                const questionTypeId = document.querySelector('input[name="questionTypeId"]:checked').value;
                document.getElementById('singleChoiceOptions').style.display = questionTypeId === '1' ? 'block' : 'none';
                document.getElementById('matchingOptions').style.display = questionTypeId === '2' ? 'block' : 'none';
            }

            window.onload = function () {
                showOptions();
            }
        </script>
    </head>
    <body class="container-fluid">
        <div class="row">
            <div class="col-md-2" >
                <%@ include file="Components/Sidebar.jsp" %>
            </div>
            <div class="col-md-10 px-4 py-4" style="margin-top: 20px">
                <h2 class="text-center mb-4">Add New Question</h2>
                
                <c:choose>
                    <c:when test="${empty teacherSubjects}">
                        <div class="alert alert-warning">
                            <strong>Notice:</strong> You haven't been assigned to any subjects yet. 
                            Please contact the administrator to assign you to subjects before creating questions.
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="card">
                            <div class="card-body">
                                <c:if test="${not empty successMessage}">
                                    <div class="alert alert-success">
                                        <p>${successMessage}</p>
                                    </div>
                                </c:if>

                                <c:if test="${not empty errorMessage}">
                                    <div class="alert alert-danger">
                                        <p>${errorMessage}</p>
                                    </div>
                                </c:if>

                        <form action="addquestion" method="post">
                            <div class="form-group">
                                <label for="subjectId">Subject</label>
                                <select class="form-control" id="subjectId" name="subjectId" required>
                                    <c:forEach var="subject" items="${teacherSubjects}">
                                        <option value="${subject.subjectId}">${subject.subjectName}</option>
                                    </c:forEach>

                                </select>
                            </div>


                            <div class="form-group">
                                <label for="chapterId">Chapter</label>
                                <select class="form-control" id="chapterId" name="chapterId" required>
                                    <option value="">Select a chapter</option>
                                    <c:forEach var="i" begin="1" end="5">
                                        <option value="${i}" ${param.chapterId == i ? 'selected' : ''}>Chapter ${i}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="question">Question</label>
                                <input type="text" class="form-control ${not empty questionError ? 'is-invalid' : ''}" 
                                       id="question" name="question" 
                                       placeholder="Enter question text here..." 
                                       value="${question}">
                                <c:if test="${not empty questionError}">
                                    <div class="invalid-feedback">
                                        ${questionError}
                                    </div>
                                </c:if>
                            </div>

                            <div class="form-group">
                                <label>Question Type</label>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="questionTypeId" id="singleChoice" value="1" 
                                           ${param.questionTypeId == '1' ? 'checked' : ''} onchange="showOptions()">
                                    <label class="form-check-label" for="singleChoice">Multiple Choice</label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="questionTypeId" id="matching" value="2" 
                                           ${empty param.questionTypeId || param.questionTypeId == '2' ? 'checked' : ''} onchange="showOptions()">
                                    <label class="form-check-label" for="matching">Short Answer</label>
                                </div>
                            </div>

                            <div id="singleChoiceOptions" style="display:none;">
                                <div class="form-group">
                                    <label>Options</label>
                                    <c:forEach var="i" begin="1" end="4">
                                        <div class="form-group">
                                            <input type="text" 
                                                   class="form-control mb-1 ${not empty requestScope['optionError'.concat(i)] ? 'is-invalid' : ''}" 
                                                   name="optionText${i}" 
                                                   placeholder="Option ${i}" 
                                                   value="${requestScope['optionText'.concat(i)]}">
                                            <c:if test="${not empty requestScope['optionError'.concat(i)]}">
                                                <div class="invalid-feedback">
                                                    ${requestScope['optionError'.concat(i)]}
                                                </div>
                                            </c:if>
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" 
                                                       name="isCorrect${i}" value="1"
                                                       ${requestScope['isCorrect'.concat(i)] ? 'checked' : ''}>
                                                <label class="form-check-label">Correct</label>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>

                            <div id="matchingOptions" style="display:none;">
                                <div class="form-group">
                                    <label for="optionText">Short Answer Option</label>
                                    <input type="text" 
                                           class="form-control ${not empty matchingError ? 'is-invalid' : ''}" 
                                           id="optionText" name="optionText" 
                                           placeholder="Enter matching option" 
                                           value="${optionText}">
                                    <c:if test="${not empty matchingError}">
                                        <div class="invalid-feedback">
                                            ${matchingError}
                                        </div>
                                    </c:if>
                                </div>
                            </div>

                            <div class="text-center">
                                        <button type="submit" class="btn btn-success">Add New Question</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </body>
</html>