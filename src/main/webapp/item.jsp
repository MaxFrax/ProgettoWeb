<%@page import="it.unitn.disi.buybuy.dao.entities.Item"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${empty item}">
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
        <title>${item.name}</title>
        <!-- Bootstrap Core CSS -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom CSS -->
        <link href="css/item.css" rel="stylesheet">
    </head>

    <body>

        <!-- Navbar -->
        <%@include file="navbar.jsp"%>

        <!-- Main container -->
        <div class="container">
            <div class="row">
                <div class="col-xs-12">
                    <h4 class="seller">${item.name}</h4>
                    <h5 class="seller">di <a href="${item.seller.website}">${item.seller.name}</a></h5>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-4">
                    <div class="thumbnail">
                        <img src="img/thumbnail.jpg" alt="Immagine prodotto non disponibile">
                        <div class="caption">
                            <hr>
                            <c:forEach var="i" begin="1" end="${item.rating}">
                                <span class="glyphicon glyphicon-star"></span>
                            </c:forEach>
                            <c:forEach var="i" begin="${item.rating+1}" end="5">
                                <span class="glyphicon glyphicon-star-empty"></span>
                            </c:forEach>
                            (${item.reviewCount} recensioni)
                            
                            <c:choose>
                                <c:when test="${retailer.latitude eq null}">
                                    Mappa non disponibilie
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="btn btn-default btn-xs pull-right" onclick="Mappa()"><span class="glyphicon glyphicon-map-marker"></span> Mappa</button>
                                </c:otherwise>
                            </c:choose>
                                    
                            <script>
                                function Mappa(){
                                    var mappa = document.getElementById("map");
                                    if(mappa.style.display === "none"){
                                        mappa.style.display = ""; 
                                        initMap();
                                    }
                                    else{
                                        mappa.style.display = "none"; 
                                    }
                                }
                            </script>
                            <script>
                              function initMap() {
                                var retailer_pos = {lat: ${retailer.latitude}, lng: ${retailer.longitude}};
                                var map = new google.maps.Map(document.getElementById('map'), {
                                  zoom: 16,
                                  center: retailer_pos
                                });
                                var marker = new google.maps.Marker({
                                  position: retailer_pos,
                                  map: map
                                });
                              }
                            </script>
                            <script async defer
                            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD56DJzP0gVy_EbPuEJ8zDQIjVJsDi9DBQ">
                            </script>
                        </div>
                    </div>
                </div>
                <div class="col-sm-8">
                    <div class="row">
                        <div class="col-xs-12 col-sm-8">
                            <div class="panel panel-default price">
                                <div class="panel-body">
                                    <table>
                                        <tr>
                                            <td class="price-label">Prezzo:</td>
                                            <td class="price-val">${item.price} &euro;</td>
                                        </tr>
                                        <c:if test="${!empty retailer}">
                                            <tr class="pickup-label"><td></td><td><span class="label label-info"><span class="glyphicon glyphicon-ok"></span> Ritiro in negozio</span></td></tr>
                                        </c:if>
                                        <tr>
                                            <td>Quantità:</td>
                                            <td>
                                                <form class="form-inline" action="${pageContext.servletContext.contextPath}/update_cart" method="GET">
                                                    <div class="input-group">
                                                        <input type="number" class="form-control" name="${item.id}" value="1" min="1">
                                                        <span class="input-group-btn">
                                                            <button class="btn btn-default" type="submit"><span class="glyphicon glyphicon-shopping-cart"></span> Aggiungi al carrello</button>
                                                        </span>
                                                    </div>
                                                </form>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <p>
                                <strong>Descrizione:</strong>
                                ${item.description}
                            </p>
                        </div>
                    </div>
                </div>
            </div>
            <div id="map" style="height:300px; width:100%; display: none"></div>
            <div class="row">
                <div class="col-xs-12">
                    <c:choose>
                        <c:when test="${empty reviews}">
                            Non ci sono ancora recensioni per questo oggetto.
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
