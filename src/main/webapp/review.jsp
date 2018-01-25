<%-- 
    Document   : review
    Created on : 25-gen-2018, 14.17.40
    Author     : apell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--bootstrap -->
        <link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
        <link href="kartik-v-bootstrap-star-rating-19120d9/css/star-rating.css" media="all" rel="stylesheet" type="text/css"/>
        <!--jquery -->
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
        <script src="kartik-v-bootstrap-star-rating-19120d9/js/star-rating.js" type="text/javascript"></script>
        <title>Scrivi una recensione</title>
        <!-- Custom CSS -->
        <link href="css/results.css" rel="stylesheet">
    </head>
    <body>
        <!-- Navbar -->
        <%@include file="navbar.jsp"%>
        <div class="section">
          <div class="container">
            <div class="row">
              <c:if test="${not empty message}">
                <!-- edit form column -->
                <div class="alert alert-info">
                  <c:out value="${message}"/>
                </div>
              </c:if>
              <div class="col-md-12">
                <h1 class="text-center">Scrivi una recensione riguardante</h1>
              </div>
            </div>
            <div class="row">
              <div class="col-md-12">
                <h3 class="text-center">${item.getName()}</h3>
              </div>
            </div>
            <div class="row">
              <div class="col-md-12">
                <form action="ReviewServlet?id=${item.getId()}" method="POST" class="form-horizontal" role="form">
                  <div class="form-group">
                    <textarea class="form-control" name="user_review" placeholder="Scrivi la tua recensione qui..." rows="7"></textarea>
                  </div>
                  <div class="form-group text-center">
                    <input id="rating" name="user_rating" value="1" type="text" class="rating" data-min="0" data-max="5" data-step="1" data-size="sm" title="">
                  </div>
                  <div class="form-group text-center">
                    <input class="btn btn-primary input" value="Invia la tua recensione" type="submit">
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>       
        <!-- Footer -->
        <%@include file="footer.jsp"%>
    </body>
    
</html>
