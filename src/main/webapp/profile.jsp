<%-- 
    Document   : update_profile.jsp
    Created on : 23-gen-2018, 11.15.07
    Author     : apell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Profilo</title>
        <!-- Bootstrap Core CSS -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <!-- Custom CSS -->
        <link href="css/results.css" rel="stylesheet">
    </head>
    <body>
        <!-- Navbar -->
        <%@include file="navbar.jsp"%>
        
        <div class="container">
            <h1>Edit Profile</h1>
                <hr>
                <div class="row">
                    <c:if test="${not empty message}">
                        <!-- edit form column -->
                        <div class="alert alert-info">
                            <c:out value="${message}"/>
                        </div>
                    </c:if>
                    <h3>Informazioni personali</h3>

                    <form action="profile" method="POST" class="form-horizontal" role="form">
                        <div class="form-group">
                            <label class="col-lg-3 control-label">Nome:</label>
                            <div class="col-lg-8">
                                <input class="form-control" name="name" value="${user.getName()}" type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">Cognome:</label>
                            <div class="col-lg-8">
                                <input class="form-control" name="last_name" value="${user.getLastname()}" type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">Username:</label>
                            <div class="col-lg-8">
                                <input class="form-control" name="username" value="${user.getUsername()}" type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">Email:</label>
                            <div class="col-lg-8">
                                <input class="form-control" name="mail" value="${user.getEmail()}" type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label"></label>
                            <div class="col-md-8">
                                <h4 class="col-md-8">Cambio password:</h4>
                            </div>                            
                        </div> 
                        <div class="form-group">
                            <label class="col-md-3 control-label">New password:</label>
                            <div class="col-md-8">
                                <input class="form-control" name="new_password" type="password">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">Confirm new password:</label>
                            <div class="col-md-8">
                                <input class="form-control" name="new_confirm_password" type="password">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label"></label>
                            <div class="col-md-8">
                                <h4 class="col-md-8">Inserisci la password per confermare le modifiche effettuate:</h4>
                            </div>                            
                        </div>                        
                        <div class="form-group">
                            <label class="col-md-3 control-label">Password:</label>
                            <div class="col-md-8">
                                <input class="form-control" name="password" type="password">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">Confirm password:</label>
                            <div class="col-md-8">
                                <input class="form-control" name="confirm_password" type="password">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label"></label>
                            <div class="col-md-8">
                                <input class="btn btn-primary" value="Save Changes" type="submit">
                                <span></span>
                                <input class="btn btn-default" value="Cancel" type="reset">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <!-- Footer -->
        <%@include file="footer.jsp"%>
    </body>
</html>
