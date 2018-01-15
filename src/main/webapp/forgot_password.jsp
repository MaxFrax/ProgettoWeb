<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="it">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <title>Password dimenticata - BuyBuy</title>
        <!-- Bootstrap Core CSS -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom CSS -->
        <link href="css/forgot_password.css" rel="stylesheet">
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
                    <form action="forgot_password" id="form-forgot-password" method="post">
                        <h3 id="h3-forgot-password">Password dimenticata</h3>
                        <c:choose>
                            <c:when test="${not empty errorMessage}">
                                <div class="alert alert-danger alert-dismissible" role="alert">
                                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                    ${errorMessage}
                                </div>
                            </c:when>
                            <c:when test="${not empty successMessage}">
                                <div class="alert alert-success" role="alert">${successMessage}</div>
                            </c:when>
                        </c:choose>
                        <p>Inserisci l'indirizzo email associato al tuo account BuyBuy per impostare una nuova password.</p>
                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="text" class="form-control" id="email" name="email">
                        </div>
                        <button type="submit" class="btn btn-default">Continua</button>
                    </form>
                </div>
            </div>
        </div>
        <div class="container">
            <hr>
            <!-- Footer -->
            <footer>
                <div class="row">
                    <div class="col-md-12" id="footer">
                        Copyright &copy; BuyBuy 2017
                    </div>
                </div>
            </footer>
        </div>
        <!-- /.container -->
        <!-- jQuery -->
        <script src="js/jquery.js"></script>
        <!-- Bootstrap Core JavaScript -->
        <script src="js/bootstrap.min.js"></script>
    </body>

</html>
