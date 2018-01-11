<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="it">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <title>Ooops...</title>
        <!-- Bootstrap Core CSS -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom CSS -->
        <link href="css/error.css" rel="stylesheet">
    </head>

    <body>
        <!-- Main container -->
        <div class="container main">
            <!-- Logo -->
            <div class="row">
                <div class="col-xs-12">
                    <img class="img-responsive center-block logo" src="img/logo.png" alt="BuyBuy">
                </div>
            </div>
            <div class="row panel-error">
                <div class="col-md-6 col-md-offset-3">
                    <div class="panel panel-danger">
                        <div class="panel-heading">
                            <h3 class="panel-title">C'è stato un errore</h3>
                        </div> 
                        <div class="panel-body">
                            Se hai bisogno di aiuto, <a href="#">contattaci</a>.
                        </div> 
                    </div>
                </div>
            </div>
            <!-- Footer -->
            <div class="row">
                <div class="col-xs-12">
                    <footer class="text-center">
                        Copyright &copy; BuyBuy 2018
                    </footer>
                </div>
            </div>
        </div>

        <!-- jQuery -->
        <script src="js/jquery.js"></script>
        <!-- Bootstrap Core JavaScript -->
        <script src="js/bootstrap.min.js"></script>
    </body>

</html>
