<%@page import="it.unitn.disi.buybuy.dao.entities.Item"%>
<%@page import="java.util.List"%>
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
            <div class="row">
                <div class="col-xs-8 col-md-9">
                    <h4><b>${results.size()}</b> oggetti trovati</h4>
                </div>
                <div class="col-xs-4 col-md-3">
                    <select class="form-control" id="sort-results" onchange="sortResults()">
                        <option value="price_asc" ${param.sort == 'price_asc' ? 'selected' : ''}>Prezzo: crescente</option>
                        <option value="price_desc" ${param.sort == 'price_desc' ? 'selected' : ''}>Prezzo: decrescente</option>
                        <option value="rating" ${param.sort == 'rating' ? 'selected' : ''}>Media recensioni clienti</option>
                    </select>
                </div>
            </div>

            <hr class="no-margin">

            <div class="row search-result">
                <div class="col-xs-12">

                    <!-- Items -->
                    <c:forEach items="${results}" var="item">
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
                                                <h4><a href="${pageContext.request.contextPath}/item?id=${item.id}">${item.name}</a></h4>
                                                <ul>
                                                    <li>di <a href="${item.seller.website}">${item.seller.name}</a></li>
                                                    <li>
                                                        <a href="#"><span class="glyphicon glyphicon-map-marker"></span>&nbsp;TODO: link mappa</a>
                                                    </li>
                                                </ul>
                                            </div>

                                            <div class="col-sm-4">
                                                <h4><b>${item.price}&euro;</b></h4>
                                                <span>
                                                    <c:forEach var="i" begin="1" end="${item.rating}">
                                                        <span class="glyphicon glyphicon-star"></span>
                                                    </c:forEach>
                                                    <c:forEach var="i" begin="${item.rating+1}" end="5">
                                                        <span class="glyphicon glyphicon-star-empty"></span>
                                                    </c:forEach>
                                                </span>
                                                (${item.reviewCount})
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <!-- Footer -->
            <%@include file="footer.jsp"%>
        </div>
        <!-- jQuery -->
        <script src="js/jquery.js"></script>
        <!-- Bootstrap Core JavaScript -->
        <script src="js/bootstrap.min.js"></script>
        <script>
            function sortResults() {
                // Get current search url
                var url = window.location.href;
                // Get select value
                var selector = document.getElementById('sort-results');
                var value = selector[selector.selectedIndex].value;
                // Replace sort parameter in url
                var new_url = url.replace(/&sort=.*?(?=\&|$)/g, "");
                new_url += "&sort=" + value;
                // Re-do search with new url
                window.location.replace(new_url);
            }
        </script>
    </body>

</html>