<%-- 
    Document   : orders.jsp
    Created on : 23-gen-2018, 19.23.26
    Author     : apell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- Get context path --%>
<c:set var="contextPath" value="${pageContext.servletContext.contextPath}"></c:set>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="http://pingendo.github.io/pingendo-bootstrap/themes/default/bootstrap.css"
        rel="stylesheet" type="text/css">
        <title>I miei ordini</title>
        <!-- Bootstrap Core CSS -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css"
        rel="stylesheet" type="text/css">
        <!-- jQuery -->
        <script src="js/jquery.js"></script>
        <!-- Bootstrap Core JavaScript -->
        <script src="js/bootstrap.min.js"></script>
        <!-- Custom CSS -->
        <link href="css/navbar.css" rel="stylesheet">

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
                <h1 class="text-center">I miei ordini</h1>
              </div>
            </div>
          </div>
        </div>
        <div class="section">
          <div class="container">
            <div class="row">
              <div class="col-md-2">ID</div>
              <div class="col-md-8">OGGETTO</div>
              <div class="col-md-2">AZIONE</div>
            </div>
            <c:forEach items="${items}" var="item">
                <hr>
                <div class="row">
                  <div class="col-md-2">
                    <p>
                        <c:out value="${item.id}" />
                    </p>
                  </div>
                  <div class="col-md-8">                 
                      <a href="${pageContext.servletContext.contextPath}/item?id=${item.id}"><c:out value="${item.name}" /></a>
                  </div>
                  <div class="col-md-2">
                    <a href="${pageContext.servletContext.contextPath}/review?id=${item.id}">Scrivi una recensione</a>
                    <br>
                    <a href="${pageContext.servletContext.contextPath}/issue?id=${item.id}">Segnala un' anomalia</a>
                  </div>
                </div>
            </c:forEach>
          </div>
        </div>
        <!-- Footer -->
        <%@include file="footer.jsp"%>
    </body>
</html>
