<%@page import="it.unitn.disi.buybuy.dao.entities.Item"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="it">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Carrello</title>
        <!-- Bootstrap Core CSS -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom CSS -->
        <link href="css/cart.css" rel="stylesheet">
    </head>

    <body>

        <!-- Navbar -->
        <%@include file="navbar.jsp"%>

        <!-- Main container -->
        <div class="container">
            
            <div class="row">
                <div class="col-xs-12">
                    <h3 class="page-header">Carrello</h3>
                </div>
            </div>
            <%-- Item added to cart message --%>
            <c:if test="${!empty newItem}">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="alert alert-success" role="alert">
                            <span class="glyphicon glyphicon-ok-circle"></span>"<strong>${newItem.name}</strong>" aggiunto al carrello
                        </div>
                    </div>
                </div>
            </c:if>

            <c:choose>
                <c:when test="${empty cart}">
                    <div class="row">
                        <div class="col-xs-12">
                            Il tuo carrello è vuoto
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="row">
                        <div class="col-xs-12">
                            <form class="form-inline" id="cart" action="${pageContext.servletContext.contextPath}/update_cart" method="POST">
                                <div class="table-responsive">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th></th>
                                                <th></th>
                                                <th>Prezzo</th>
                                                <th class="text-center">Quantità</th>
                                            </tr>
                                        </thead>
                                        <c:set var="total" value="${0}"></c:set>
                                        <c:forEach items="${cart}" var="entry">
                                            <c:set var="item" value="${entry.value.item}"></c:set>
                                            <c:set var="quantity" value="${entry.value.quantity}"></c:set>
                                                <tr>
                                                    <td class="thumb">
                                                        <img src="img/chainsaw.jpg" class="thumbnail" alt="Immagine non disponibile">
                                                    </td>
                                                    <td>
                                                        <ul>
                                                            <li>${item.name}</li>
                                                        <li>di ${item.seller.name}</li>
                                                        <li><span class="label label-info"><span class="glyphicon glyphicon-ok"></span> Ritiro in negozio</span></li>
                                                    </ul>
                                                </td>
                                                <td class="price text-nowrap">
                                                    <!-- price -->
                                                    ${item.price} &euro;
                                                </td>
                                                <td class="text-center">
                                                    <!-- qty -->
                                                    <input type="number" class="form-control center-block" name="${item.id}" value="${quantity}" min="0">
                                                    <!-- remove btn -->
                                                    <button type="button" class="btn btn-default remove">
                                                        <span class="glyphicon glyphicon-remove" aria-hidden="true" aria-label="Rimuovi"></span>
                                                    </button>
                                                </td>
                                                <c:set var="total" value="${total + item.price*quantity}"></c:set>
                                            </c:forEach>
                                    </table>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12 text-right">
                            <hr>
                            Totale articoli: <h4 class="total"><strong><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${total}"/> &euro;</strong></h4>
                            <hr>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <button type="submit" form="cart" class="btn btn-default pull-right">Aggiorna carrello</button>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>

            <!-- Footer -->
            <%@include file="footer.jsp"%>
        </div>

        <!-- jQuery -->
        <script src="js/jquery.js"></script>
        <!-- Bootstrap Core JavaScript -->
        <script src="js/bootstrap.min.js"></script>

        <script>
            $("button.remove").click(function () {
                var itemId = $(this).siblings("input").attr("name");
                window.location.replace("${pageContext.servletContext.contextPath}/update_cart?" + itemId + "=0");
            });
        </script>

    </body>

</html>
