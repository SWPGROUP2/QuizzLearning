<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Question</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <h1>Edit Question</h1>
        <form action="editquestion" method="post"> 
            <input type="hidden" name="questionId" value="${question.getQuestionID()}"> 
            <input type="hidden" name="subjectId" value="${question.getSubjectId()}">

            <div class="form-group">
                <label for="question">Question:</label>
                <input type="text" class="form-control" name="question" value="${question.getQuestion()}" required> 
            </div>
            <div class="form-group">
                <label for="definition">Definition:</label>
                <input type="text" class="form-control" name="definition" value="${question.getDefinition()}" required> 
            </div>
            <button type="submit" class="btn btn-primary">Update Question</button>
        </form>
    </div>
</body>
</html>
