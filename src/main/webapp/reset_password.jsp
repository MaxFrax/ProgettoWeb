<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="it">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <title>Password dimenticata</title>
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
                    <form action="${pageContext.request.contextPath}/reset_password" id="form-reset-password" method="POST">
                        <h3 id="h3-reset-password">Reimpostazione password</h3>
                        <c:if test="${!empty message}">
                            <c:choose>
                                <c:when test="${message.type == 'ERROR'}">
                                    <div class="alert alert-danger alert-dismissible" role="alert">
                                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                </c:when>
                                <c:otherwise>
                                    <div class="alert alert-success" role="alert">
                                </c:otherwise>
                            </c:choose>
                            ${message}
                            </div>
                        </c:if>
                        <p>Inserisci una nuova password per il tuo account e clicca su "Conferma".</p>
                        <div class="form-group">
                            <input type="password" class="form-control" id="password" name="password" placeholder="Inserisci password">
                            <input type="hidden" value="${param.id}" name="id">
                        </div>
                        <button type="submit" class="btn btn-default">Conferma</button>
                    </form>
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
