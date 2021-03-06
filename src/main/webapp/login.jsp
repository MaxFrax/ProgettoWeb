<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
        <link href="css/form_page.css" rel="stylesheet">
    </head>

    <body>
        <div class="container main">
            <!-- Logo -->
            <div class="row">
                <div class="col-md-12"><img class="img-responsive center-block" src="img/logo.png" alt="BuyBuy"></div>
            </div>
            <!-- Form -->
            <div class="row">
                <div class="col-md-6 col-md-offset-3">
                    <form action="login" method="post">
                        <h3>Accesso</h3>
                        <c:if test="${not empty requestScope.error_message}">
                            <div class="alert alert-danger alert-dismissible" role="alert">
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                ${requestScope.error_message}
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="text" class="form-control" id="email" placeholder="Inserisci email" name="email">
                        </div>
                        <div class="form-group">
                            <label for="pass">Password</label><a class="pull-right" href="${pageContext.request.contextPath}/forgot_password.jsp">Password dimenticata</a>
                            <input type="password" class="form-control" id="pass" placeholder="Inserisci password" name="pass">
                        </div>
                        <button type="submit" class="btn btn-default">Accedi</button><span class="cancel">oppure <a href="javascript:history.back()">annulla</a></span>
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
