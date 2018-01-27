<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- Get context path --%>
<c:set var="contextPath" value="${pageContext.servletContext.contextPath}">
</c:set>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Completa l'ordine</title>
        <!-- Bootstrap Core CSS -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom CSS -->
        <link href="css/order_success.css" rel="stylesheet">
    </head>
    <body>

        <div class="container">
            <!-- Logo -->
            <div class="row logo">
                <div class="col-xs-12"><img class="img-responsive center-block" src="img/logo.png" alt="BuyBuy"></div>
            </div>
            <div class="row">
                <div class="col-xs-12 col-sm-8 col-sm-offset-2">
                    <div class="alert alert-success text-center" role="alert">
                        <h4>Il tuo ordine è stato confermato!</h4>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-8 col-sm-offset-2 text-center">
                    <a href="${contextPath}"><button class="btn btn-default">Continua con gli acquisti</button></a>
                </div>
            </div>
            <%@include file="footer.jsp"%>
        </div>


        <!-- jQuery -->
        <script src="js/jquery.js"></script>
        <!-- Bootstrap Core JavaScript -->
        <script src="js/bootstrap.min.js"></script>
    </body>

</html>