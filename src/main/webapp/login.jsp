<%-- 
    Document   : login
    Created on : 4-gen-2018, 17.42.12
    Author     : maxfrax
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Accedi - BuyBuy</title>
    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="css/login.css" rel="stylesheet">
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
                <form action="Login" id="form-login" method="post">
                    <h3 id="h3-login">Accesso</h3>
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" class="form-control" id="email" placeholder="Inserisci email" name="email">
                    </div>
                    <div class="form-group">
                        <label for="pass">Password</label><a class="pull-right" href="#">Password dimenticata</a>
                        <input type="password" class="form-control" id="pass" placeholder="Inserisci password" name="pass">
                    </div>
                    <button type="submit" class="btn btn-default">Accedi</button><span id="span-cancel">oppure <a href="#">annulla</a></span>
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
