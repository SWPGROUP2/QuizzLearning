<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Edit Question</title>
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
    </head>
    <body>
        <div class="container mt-5">
            <h2 class="text-center mb-4">Edit Question</h2>

            <div class="card">
                <div class="card-body">
                    <form action="editquestion" method="post">
                        <!-- Hidden fields -->
                        <input type="hidden" name="questionId" value="${question.questionID}" />
                        <input type="hidden" name="subjectId" value="${subjectId}" />
                        <input type="hidden" name="questionTypeId" value="${question.questionTypeId}" />

                        <div class="form-group">
                            <label for="chapterId">Chapter</label>
                            <select class="form-control" id="chapterId" name="chapterId" required>
                                <option value="">Select a chapter</option>
                                <c:forEach var="i" begin="1" end="5">
                                    <option value="${i}" ${question.chapterId == i ? 'selected' : ''}>Chapter ${i}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="question">Question</label>
                            <input type="text" class="form-control" id="question" name="question" 
                                   required placeholder="Enter question text here..." 
                                   value="${question.question}">
                        </div>

                        <c:if test="${question.questionTypeId == 1}">
                            <div class="form-group">
                                <label>Options</label>
                                <c:forEach var="i" begin="1" end="4">
                                    <div class="form-group">
                                        <input type="text" class="form-control mb-1" name="optionText${i}" 
                                               placeholder="Option ${i}" value="${options[i-1].optionText}" required>
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" name="isCorrect${i}" value="1"
                                                   ${options[i-1].isCorrect == 1 ? 'checked' : ''}>
                                            <label class="form-check-label">Correct</label>
                                        </div>
                                        <input type="hidden" name="optionId${i}" value="${options[i-1].optionId}">
                                    </div>
                                </c:forEach>
                            </div>
                        </c:if>

                        <c:if test="${question.questionTypeId == 2}">
                            <div class="form-group">
                                <label for="optionText">Matching Option</label>
                                <input type="text" class="form-control" id="optionText" name="optionText" 
                                       placeholder="Enter matching option" value="${matchingOption.optionText}" required>
                                <input type="hidden" name="matchingOptionId" value="${matchingOption.optionId}">
                            </div>
                        </c:if>


                        <div class="text-center">
                            <button type="submit" class="btn btn-success">
                                Update Question
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>