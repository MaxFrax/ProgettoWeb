<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- If there is no message to show, redirect to home --%>
<c:if test="${empty message}">
    <c:redirect url="/"></c:redirect>
</c:if>

<!DOCTYPE html>
<html lang="it">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <title>${message.type == 'ERROR' ? 'Errore' : 'Informazione'}</title>
        <!-- Bootstrap Core CSS -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom CSS -->
        <link href="css/style.css" rel="stylesheet">
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
            <div class="row panel-message">
                <div class="col-md-6 col-md-offset-3">
                    <div class="panel ${message.type == 'ERROR' ? 'panel-danger' : 'panel-info'}">
                        <div class="panel-heading">
                            <h3 class="panel-title">${message.type == 'ERROR' ? 'Errore' : 'Informazione'}</h3>
                        </div> 
                        <div class="panel-body">
                            <c:choose>
                                <c:when test="${empty message.text}">
                                    Se hai bisogno di aiuto, <a href="#">contattaci</a>.
                                </c:when>
                                <c:otherwise>
                                    ${message.text}
                                </c:otherwise>
                            </c:choose>
                        </div> 
                    </div>
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
