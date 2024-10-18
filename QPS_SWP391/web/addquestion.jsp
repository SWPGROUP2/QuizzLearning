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
    <body>
        <div class="container mt-5">
            <h2 class="text-center mb-4">Add New Question</h2>
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
                        <input type="hidden" name="subjectId" value="${subjectId}" />

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
                            <input type="text" class="form-control" id="question" name="question" 
                                   required placeholder="Enter question text here..." 
                                   value="${not empty param.question ? param.question : ''}">
                        </div>

                        <div class="form-group">
                            <label>Question Type</label>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="questionTypeId" id="singleChoice" value="1" 
                                       ${param.questionTypeId == '1' ? 'checked' : ''} onchange="showOptions()">
                                <label class="form-check-label" for="singleChoice">Single Choice</label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="questionTypeId" id="matching" value="2" 
                                       ${empty param.questionTypeId || param.questionTypeId == '2' ? 'checked' : ''} onchange="showOptions()">
                                <label class="form-check-label" for="matching">Matching</label>
                            </div>
                        </div>

                        <!-- Single Choice Options -->
                        <div id="singleChoiceOptions" style="display:none;">
                            <div class="form-group">
                                <label>Options</label>
                                <c:forEach var="i" begin="1" end="4">
                                    <div class="form-group">
                                        <input type="text" class="form-control mb-1" name="optionText${i}" 
                                               placeholder="Option ${i}" ${questionTypeId == '1' ? 'required' : ''}>
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" name="isCorrect${i}" value="1">
                                            <label class="form-check-label">Correct</label>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>

                        <!-- Matching Option -->
                        <div id="matchingOptions" style="display:none;">
                            <div class="form-group">
                                <label for="optionText">Matching Option</label>
                                <input type="text" class="form-control" id="optionText" name="optionText" 
                                       placeholder="Enter matching option" ${questionTypeId == '2' ? 'required' : ''}>
                            </div>
                        </div>

                        <div class="text-center">
                            <button type="submit" class="btn btn-success">Add New Question</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
