<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Add New Class</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-8 col-lg-6">
                    <div class="card">
                        <div class="card-body">
                            <h2 class="form-header">Add New Class</h2>
                            <form action="addclass" method="post">
                                <div class="form-group">
                                    <label for="className" class="form-label">Class Name</label>
                                    <input type="text" 
                                           id="className" 
                                           name="className" 
                                           class="form-control" 
                                           placeholder="Enter class name..." 
                                           required>
                                </div>

                                <div class="form-footer">
                                    <button type="submit" class="btn btn-success">Add New Class</button>
                                    <a href="classlist" class="btn btn-secondary ml-2">Back to Class List</a>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>