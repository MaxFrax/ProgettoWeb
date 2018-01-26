<%-- 
    Document   : issue
    Created on : 26-gen-2018, 12.12.04
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
        <!--jquery -->
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
        <title>Segnala anomalia</title>
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
                <h1 class="text-center">Segnala un anomalia riguardante l'ordine numero</h1>
              </div>
            </div>
            <div class="row">
              <div class="col-md-12">
                <h3 class="text-center">${purchase.getId()}</h3>
              </div>
            </div>
            <div class="row">
              <div class="col-md-12">
                <form action="issue?id=${purchase.getId()}" method="POST" class="form-horizontal" role="form">
                  <div class="form-group">
                    <textarea class="form-control" name="description" placeholder="Descrivi la tua anomalia qui..." rows="7"></textarea>
                  </div>
                  <div class="form-group text-center">
                    <input class="btn btn-primary input" value="Invia" type="submit">
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
