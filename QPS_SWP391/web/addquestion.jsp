<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Add Question</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container mt-5">
            <h2 class="text-center mb-4">Add New Question</h2>
            <div class="card">
                <div class="card-body">
                    <form action="addquestion" method="post">
                        <input type="hidden" name="subjectId" value="${subjectId}" />
                        <div class="form-group">
                            <label for="question">Question</label>
                            <input type="text" class="form-control" id="question" name="question" required placeholder="Enter question text here...">
                        </div>
                        <div class="form-group">
                            <label for="definition">Definition</label>
                            <input type="text" class="form-control" id="definition" name="definition" required placeholder="Enter definition text here...">
                        </div>
                        <div class="text-center">
                            <button type="submit" class="btn btn-success">Add New Question</button>
                        </div>
                    </form>


                </div>
            </div>
        </div>
                        
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
<% String errorMessage = (String) request.getAttribute("errorMessage"); %>
<% if (errorMessage != null) { %>
    <p style="color: red;"><%= errorMessage %></p>
<% } %>
