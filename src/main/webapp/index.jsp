<%@page import="it.unitn.disi.buybuy.dao.entities.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>BuyBuy</title>
        <!-- Bootstrap Core CSS -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom CSS -->
        <link href="css/index.css" rel="stylesheet">
        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
                    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
                    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
            <![endif]-->
    </head>

    <body>
        <!-- Navigation -->
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#"><span class="glyphicon glyphicon-fire"></span> BuyBuy</a>
                </div>
                <div class="collapse navbar-collapse" id="myNavbar">
                    <div class="col-sm-3 col-md-3">
                        <form class="navbar-form" role="search">
                            <div class="input-group">
                                <input type="text" class="form-control" placeholder="Search" name="q">
                                <div class="input-group-btn">
                                    <button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <ul class="nav navbar-nav navbar-right">
                        <% if (request.getSession().getAttribute("user") == null) { %>
                        <li><a href="Signup"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
                        <li><a href="Login"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
                            <% } else {
                                User user = (User) request.getSession().getAttribute("user");
                            %>
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#"><%= user.getName()%> <%= user.getLastname()%><span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="#">Page 1-1</a></li>
                                <li><a href="#">Page 1-2</a></li>
                                <li><a href="#">Page 1-3</a></li>
                            </ul>
                        </li>
                        <% }%>
                    </ul>                        
                </div>
            </div>
        </nav>
        <!-- Page Content -->
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <div class="row carousel-holder">
                        <div id="carousel-products" class="carousel slide" data-ride="carousel">
                            <ol class="carousel-indicators">
                                <li data-target="#carousel-products" data-slide-to="0" class="active"></li>
                                <li data-target="#carousel-products" data-slide-to="1"></li>
                                <li data-target="#carousel-products" data-slide-to="2"></li>
                            </ol>
                            <div class="carousel-inner">
                                <div class="item active">
                                    <img class="slide-image" src="img/jackets.jpg" alt="">
                                </div>
                                <div class="item">
                                    <img class="slide-image" src="img/raspberry_pi.png" alt="">
                                </div>
                                <div class="item">
                                    <img class="slide-image" src="img/skateboard.jpg" alt="">
                                </div>
                            </div>
                            <a class="left carousel-control" href="#carousel-products" data-slide="prev">
                                <span class="glyphicon glyphicon-chevron-left"></span>
                            </a>
                            <a class="right carousel-control" href="#carousel-products" data-slide="next">
                                <span class="glyphicon glyphicon-chevron-right"></span>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- /.container -->
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