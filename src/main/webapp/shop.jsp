<%@page import="it.unitn.disi.buybuy.dao.entities.Retailer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${empty retailer}">
    <jsp:useBean id="message" class="it.unitn.disi.buybuy.types.Message" scope="request"/>
    <jsp:setProperty name="message" property="type" value="ERROR"/>
    <jsp:forward page="/message.jsp" />
</c:if>

<!DOCTYPE html>
<html lang="it">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>${retailer.shop.name}</title>
        <!-- Bootstrap Core CSS -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom CSS -->
        <link href="css/shop.css" rel="stylesheet">
    </head>

    <body>

        <!-- Navbar -->
        <%@include file="navbar.jsp"%>

        <!-- Main container -->
        <div class="container">
            <div class="row">
                <div class="col-xs-12">
                    <h4 class="seller">${retailer.shop.name}</h4>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-7">
                    <div class="thumbnail">
                        
                        <div id="carousel-shop" class="carousel slide" data-ride="carousel">
                            <ol class="carousel-indicators">
                                <li data-target="#carousel-shop" data-slide-to="0" class="active"></li>
                                <li data-target="#carousel-shop" data-slide-to="1"></li>
                                <li data-target="#carousel-shop" data-slide-to="2"></li>
                            </ol>
                            <div class="carousel-inner">
                                <div class="item active">
                                    <img class="slide-image" src="img/Apple_Store.jpg" alt="">
                                </div>
                                <div class="item">
                                    <img class="slide-image" src="img/Apple_Store2.jpg" alt="">
                                </div>
                                <div class="item">
                                    <img class="slide-image" src="img/Apple_Store3.jpg" alt="">
                                </div>
                            </div>
                            <a class="left carousel-control" href="#carousel-shop" data-slide="prev">
                                <span class="glyphicon glyphicon-chevron-left"></span>
                            </a>
                            <a class="right carousel-control" href="#carousel-shop" data-slide="next">
                                <span class="glyphicon glyphicon-chevron-right"></span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="col-sm-5">
                    <div class="row">
                        <div class="col-xs-12 col-sm-9">
                            <div class="panel panel-default address">
                                <div class="panel-body">
                                    <form class="form-inline">
                                        <table>
                                            <tr>
                                                <td class="address-label">Indirizzo:</td>
                                                <td class="address-label2">${retailer.streetName} ${retailer.streetNumber}, ${retailer.postalCode}, ${retailer.city} ${retailer.province}</td>
                                            </tr>
                                            <tr>
                                                <td class="address-label">Sito Web:       </td>
                                                <td class="address-label2"><a href="${retailer.shop.website}">${retailer.shop.name}</a></td>
                                            </tr>
                                        </table>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <p>
                                <strong>Descrizione:</strong>
                                ${retailer.shop.description}
                            </p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12">
                    <c:choose>
                        <c:when test="${empty reviews}">
                            Non ci sono ancora recensioni per oggetti venduti da questo negozio.
                        </c:when>
                        <c:otherwise>
                            <div class="panel panel-default">
                                <div class="panel-heading">Recensioni</div>
                                <div class="panel-body">
                                    <c:forEach items="${reviews}" var="review">
                                        <strong>${review.user.username}</strong>
                                        (
                                        <c:forEach var="i" begin="1" end="${review.rating}">
                                            <span class="glyphicon glyphicon-star"></span>
                                        </c:forEach>
                                        <c:forEach var="i" begin="${review.rating+1}" end="5">
                                            <span class="glyphicon glyphicon-star-empty"></span>
                                        </c:forEach>
                                        )<br>
                                        ${review.description}
                                        <hr>
                                    </c:forEach>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
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
