<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Update User</title>
        <style>
            body {
                background-color: #f8f9fa;
            }
            .card {
                border: none;
                border-radius: 15px;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
                background-color: #fff;
                padding: 20px;
                margin: 20px auto;
                max-width: 600px;
            }
            .btn-success {
                border-radius: 30px;
            }
            .form-control {
                border-radius: 20px;
            }
            .form-group {
                margin-bottom: 20px;
            }
        </style>
    </head>
    <body class="container-fluid">
        <div class="row">
            <div class="col-md-2">
                <%@include file="Components/Sidebar.jsp" %>
            </div>
            <div class="col-md-10">
                <div class="card">
                    <h2 class="text-center mb-4">Update User</h2>

                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger">
                            <p>${errorMessage}</p>
                        </div>
                    </c:if>

                    <form action="updateuser" method="post">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="userId" value="${user.userId}">

                        <div class="form-group">
                            <div class="radio-group">
                                <input type="radio" name="role" value="student" ${user.roleId == 1 ? 'checked' : ''} required> Student
                                <input type="radio" name="role" value="teacher" style="margin-left: 30px" ${user.roleId == 3 ? 'checked' : ''} required> Teacher
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="class">Select Class:</label>
                            <select class="form-control" id="class" name="classId" required>
                                <c:forEach var="classItem" items="${allclass}">
                                    <option value="${classItem.classID}" 
                                            ${classItem.classID == user.classId ? 'selected="selected"' : ''}>
                                        ${classItem.className}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>


                        <div class="form-group">
                            <input type="text" class="form-control ${not empty param.action && not empty fullNameError ? 'is-invalid' : ''}" 
                                   name="fullName" placeholder="Full Name" value="${user.fullName}" required>
                            <c:if test="${not empty param.action && not empty fullNameError}">
                                <div class="invalid-feedback">
                                    ${fullNameError}
                                </div>
                            </c:if>
                        </div>

                        <div class="form-group">
                            <input type="email" class="form-control ${not empty param.action && not empty emailError ? 'is-invalid' : ''}" 
                                   name="email" placeholder="Email Address" value="${user.email}" required>
                            <c:if test="${not empty param.action && not empty emailError}">
                                <div class="invalid-feedback">
                                    ${emailError}
                                </div>
                            </c:if>
                        </div>

                        <div class="form-group">
                            <input type="text" class="form-control ${not empty param.action && not empty userNameError ? 'is-invalid' : ''}" 
                                   name="userName" placeholder="User Name" value="${user.userName}">
                            <c:if test="${not empty param.action && not empty userNameError}">
                                <div class="invalid-feedback">
                                    ${userNameError}
                                </div>
                            </c:if>
                        </div>

                        <div class="form-group">
                            <input type="text" class="form-control ${not empty param.action && not empty phoneError ? 'is-invalid' : ''}" 
                                   name="phonenumber" placeholder="Phone Number" value="${user.phoneNumber}" 
                                   pattern="\d{10}" title="Phone number must be 10 digits" required>
                            <c:if test="${not empty param.action && not empty phoneError}">
                                <div class="invalid-feedback">
                                    ${phoneError}
                                </div>
                            </c:if>
                        </div>

                        <div class="form-group">
                            <label>Date of birth:</label>
                            <input type="date" class="form-control ${not empty param.action && not empty dateError ? 'is-invalid' : ''}" 
                                   name="date" value="${user.dob}" required>
                            <c:if test="${not empty param.action && not empty dateError}">
                                <div class="invalid-feedback">
                                    ${dateError}
                                </div>
                            </c:if>
                        </div>

                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label>Start Date:</label>
                                <input type="date" class="form-control" 
                                       name="startDate" value="${user.startDate}" required>
                            </div>
                            <div class="form-group col-md-6">
                                <label>End Date:</label>
                                <input type="date" class="form-control" 
                                       name="endDate" value="${user.endDate}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label>Status:</label>
                            <select class="form-control" name="status" required>
                                <option value="Active" ${user.status == 'Active' ? 'selected' : ''}>Active</option>
                                <option value="Inactive" ${user.status == 'Inactive' ? 'selected' : ''}>Inactive</option>
                            </select>
                        </div>

                        <div class="text-center">
                            <button type="submit" class="btn btn-success">Update User</button>
                        </div>

                        <div class="text-center mt-3">
                            <h5>Back to <a href="adminhome" class="text-decoration-none">Home</a></h5>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>