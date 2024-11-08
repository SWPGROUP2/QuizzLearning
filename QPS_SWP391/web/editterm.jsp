<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Edit Term</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <style>
            body {
                background-color: #f8f9fa;
                font-family: Arial, sans-serif;
            }
            .card {
                border: none;
                border-radius: 12px;
                box-shadow: 0 6px 15px rgba(0, 0, 0, 0.1);
                margin-top: 60px;
                padding: 20px;
            }
            .form-label {
                font-weight: 600;
            }
            .form-control {
                border-radius: 8px;
                transition: border-color 0.3s, box-shadow 0.3s;
            }
            .form-control:focus {
                border-color: #28a745;
                box-shadow: 0 0 0 0.2rem rgba(40, 167, 69, 0.25);
            }
            .btn-success {
                border-radius: 20px;
                padding: 10px 20px;
                font-weight: 600;
                transition: background-color 0.3s, transform 0.3s;
            }
            .btn-success:hover {
                background-color: #218838;
                transform: translateY(-2px);
            }
            .btn-secondary {
                border-radius: 20px;
                padding: 10px 20px;
                font-weight: 600;
            }
            .form-header {
                font-size: 1.5rem;
                font-weight: 700;
                color: #343a40;
                text-align: center;
                margin-bottom: 1.5rem;
            }
            .form-footer {
                text-align: center;
                margin-top: 1.5rem;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-8 col-lg-6">
                    <div class="card">
                        <div class="card-body">
                            <h2 class="form-header">Edit Term</h2>
                            <form action="editterm" method="post">
                                <input type="hidden" name="termId" value="${term.termId}">
                                <input type="hidden" name="termSetId" value="${term.termSetId}">
                                <div class="form-group">
                                    <label for="term">Term:</label>
                                    <input type="text" class="form-control" id="term" name="term" value="${term.term}" required>
                                </div>
                                <div class="form-group">
                                    <label for="definition">Definition:</label>
                                    <textarea class="form-control" id="definition" name="definition" required>${term.definition}</textarea>
                                </div>
                                <button type="submit" class="btn btn-primary">Update Term</button>
                            </form>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
