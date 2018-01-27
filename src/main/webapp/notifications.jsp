<%-- 
    Document   : notifications.jsp
    Created on : 27-gen-2018, 11.58.22
    Author     : apell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- Get context path --%>
<c:set var="contextPath" value="${pageContext.servletContext.contextPath}"></c:set>

<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Notifiche</title>
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
                <h1 class="text-center">${title}</h1>
              </div>
            </div>
          </div>
        </div>
        <c:choose>
            <c:when test="${user.getType()=='SELLER'}">
                <%@include file="notifications_seller.jsp"%>
            </c:when>
            <c:otherwise>
                <%@include file="notifications_admin.jsp"%>
            </c:otherwise>
        </c:choose>
        <hr>
        <c:if test="${title != 'Tutte le anomalie'}">
            <form action="${contextPath}/notification" method="POST" class="form-horizontal" role="form">
                <div class="form-group text-center">
                    <input class="btn btn-primary input" value="Vedi tutte" type="submit">
                </div>
            </form>
        </c:if>
        <!-- Footer -->
        <%@include file="footer.jsp"%>
    </body>
</html>