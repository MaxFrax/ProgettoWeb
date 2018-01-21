<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <title>Registrati - BuyBuy</title>
        <!-- Bootstrap Core CSS -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom CSS -->
        <link href="css/style.css" rel="stylesheet">
    </head>

    <body>
        <div class="container" id="container-form">
            <!-- Logo -->
            <div class="row">
                <div class="col-md-12"><img class="img-responsive center-block" src="img/logo.png" alt="BuyBuy"></div>
            </div>
            <!-- Form -->
            <div class="row">
                <div class="col-md-6 col-md-offset-3">
                    <form action="signup" id="form-signup" method="post">
                        <h3 id="h3-signup">Registrazione</h3>
                        <c:choose>
                            <c:when test="${not empty requestScope.errors}">
                                <div class="alert alert-danger alert-dismissible" role="alert">
                                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                    <c:forEach var="message" items="${requestScope.errors}">
                                        <li>${message}</li>
                                    </c:forEach>
                                </div>
                            </c:when>
                            <c:when test="${requestScope.signup_success}">
                                <div class="alert alert-success" role="alert">Registrazione avvenuta con successo. Ti abbiamo inviato una email di conferma per attivare l'account.</div>
                            </c:when>
                        </c:choose>
                        <div class="form-group">
                            <label for="name">Nome</label>
                            <input type="text" class="form-control" id="name" placeholder="Inserisci nome" name="name" value="${param.name != null ? param.name : ""}">
                        </div>
                        <div class="form-group">
                            <label for="lastname">Cognome</label>
                            <input type="text" class="form-control" id="lastname" placeholder="Inserisci cognome" name="lastname" value="${param.lastname != null ? param.lastname : ""}">
                        </div>
                        <div class="form-group">
                            <label for="username">Username</label>
                            <input type="text" class="form-control" id="username" placeholder="Inserisci username" name="username" value="${param.username != null ? param.username : ""}">
                        </div>
                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="text" class="form-control" id="email" placeholder="Inserisci email" name="email" value="${param.email != null ? param.email : ""}">
                        </div>
                        <div class="form-group">
                            <label for="pass">Password</label>
                            <input type="password" class="form-control" id="password" placeholder="Inserisci password" name="password">
                        </div>
                        <div class="checkbox">
                            <label>
                                <input type="checkbox" name="privacy" value="privacy">Accetto la normativa sulla privacy</label>
                        </div>
                        <button type="submit" class="btn btn-default">Registrati</button><span id="span-cancel">oppure <a href="javascript:history.back()">annulla</a></span>
                    </form>
                </div>
            </div>
        </div>
        <div class="container">
            <!-- Footer -->
            <%@include file="footer.jsp"%>
        </div>
        <!-- /.container -->
        <!-- jQuery -->
        <script src="js/jquery.js"></script>
        <!-- Bootstrap Core JavaScript -->
        <script src="js/bootstrap.min.js"></script>
    </body>

</html>
