<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Add New Term</title>
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
                            <h2 class="form-header">Add New Term</h2>
                            <form action="addterm" method="post">
                                <input type="hidden" name="termSetId" value="${param.termSetId}">

                                <div class="form-group">
                                    <label for="term" class="form-label">Term</label>
                                    <input type="text" 
                                           id="term" name="term" 
                                           class="form-control" 
                                           placeholder="Enter term here..." 
                                           value="${term}" required>
                                </div>

                                <div class="form-group">
                                    <label for="definition" class="form-label">Definition</label>
                                    <textarea id="definition" 
                                              name="definition" 
                                              rows="4" 
                                              class="form-control" 
                                              placeholder="Enter definition here..." 
                                              required>${definition}</textarea>
                                </div>

                                <div class="form-footer">
                                    <button type="submit" class="btn btn-success">Add New Term</button>
                                    <a href="termlist?termSetId=${termSet.termSetId}" class="btn btn-secondary ml-2">Back to Term Set</a>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
