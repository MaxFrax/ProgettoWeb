<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="it">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Risultati ricerca</title>
        <!-- Bootstrap Core CSS -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom CSS -->
        <link href="css/results.css" rel="stylesheet">
    </head>

    <body>
        <!-- Navbar -->
        <%@include file="navbar.jsp"%>
        <!-- Main container -->
        <div class="container main">
            <%= request.getAttribute("results")%>
            <div class="row">
                <div class="col-xs-12">
                    <h3 class="no-margin">Risultati di ricerca</h3>
                    <h4><b class="text-danger">3</b> oggetti trovati per "<b class="text-danger">lorem</b>"</h4>
                    <hr class="no-margin">
                </div>
            </div>

            <div class="row search-result">
                <div class="col-xs-12">

                    <!-- Item -->
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-xs-3">
                                    <a href="#" class="thumbnail">
                                        <img src="img/chainsaw.jpg" alt="Immagine non disponibile">
                                    </a>
                                </div>
                                <div class="col-xs-9">
                                    <div class="row">
                                        <div class="col-sm-8">
                                            <h4><a href="#">Pallone da calcio ufficiale mondiali 2006</a></h4>
                                            <ul>
                                                <li>di <a href="#">BravoVenditore</a></li>
                                                <li>
                                                    <a href="#"><span class="glyphicon glyphicon-map-marker"></span>&nbsp;Mappa</a>
                                                </li>
                                            </ul>
                                        </div>

                                        <div class="col-sm-4">
                                            <h4><b>46,30€</b></h4>
                                            <span>
                                                <span class="glyphicon glyphicon-star"></span>
                                                <span class="glyphicon glyphicon-star"></span>
                                                <span class="glyphicon glyphicon-star"></span>
                                                <span class="glyphicon glyphicon-star"></span>
                                                <span class="glyphicon glyphicon-star-empty"></span>
                                            </span>
                                            (15 recensioni)
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>



                </div>
            </div>
            <!-- Footer -->
            <%@include file="footer.jsp"%>
        </div>
        <!-- jQuery -->
        <script src="js/jquery.js"></script>
        <!-- Bootstrap Core JavaScript -->
        <script src="js/bootstrap.min.js"></script>
    </body>

</html>