<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- Show login form if user is not logged in --%>
<c:if test="${empty user}">
    <jsp:forward page="/login.jsp"/>
</c:if>

<%-- Get context path --%>
<c:set var="contextPath" value="${pageContext.servletContext.contextPath}"></c:set>
<c:set var="isSeller" value="${user.type == 'SELLER'}"></c:set>
<c:set var="shopView" value="${param.shop_view != null && isSeller}"></c:set>

    <!DOCTYPE html>
    <html>
        <head>
            <meta charset="utf-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <title>Profilo</title>
            <!-- Bootstrap Core CSS -->
            <link href="css/bootstrap.min.css" rel="stylesheet">
            <!-- Custom CSS -->
            <link href="css/profile.css" rel="stylesheet">
        </head>
        <body>
            <!-- Navbar -->
        <%@include file="navbar.jsp"%>

        <div class="container">

            <div class="page-header">
                <h1>Il mio profilo</h1>
            </div>

            <%-- Show message if there is one --%>
            <c:if test="${!empty message}">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="alert alert-info">
                            <c:out value="${message}"/>
                        </div>
                    </div>
                </div>
            </c:if>

            <%-- Nav tabs --%>
            <div class="row nav">
                <div class="col-xs-12">
                    <ul class="nav nav-tabs">
                        <c:choose>
                            <c:when test="${isSeller}">
                                <li role="presentation" ${shopView ? '' : 'class="active"'}><a href="${contextPath}/profile">Dettagli account</a></li>
                                <li role="presentation" ${shopView ? 'class="active"' : ''}><a href="${contextPath}/profile?shop_view">Negozio</a></li>
                                </c:when>
                                <c:otherwise>
                                <li role="presentation" class="active"><a href="${contextPath}/profile">Dettagli account</a></li>
                                </c:otherwise>
                            </c:choose>
                    </ul>
                </div>
            </div>

            <%-- Content --%>
            <div class="row">
                <c:choose>
                    <c:when test="${shopView}">
                        <%-- Shop view --%>
                        <%@include file="profile_shop.jsp"%>
                    </c:when>
                    <c:otherwise>
                        <%-- Account view --%>
                        <%@include file="profile_account.jsp"%>
                    </c:otherwise>
                </c:choose>
            </div>
            <!-- Footer -->
            <%@include file="footer.jsp"%>         
        </div>
        <!-- jQuery -->
        <script src="js/jquery.js"></script>
        <!-- Bootstrap Core JavaScript -->
        <script src="js/bootstrap.min.js"></script>
        <!-- Dropzone -->
        <script src="js/dropzone.js"></script>
        <!-- Google places -->
        <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyALUGh67rEnW78pYXegEI32DSMg-NFo2D4&libraries=places&language=it"></script>
        <script>
            // Dropzone.js
            Dropzone.autoDiscover = false;
            $("div#dropzone").dropzone({
                url: "/path/to/servlet",
                dictDefaultMessage: "Trascina qui un immagine per cambiarla. Puoi anche fare click."
            });
            // Google place search
            var input = document.getElementById("place-search");
            var options = {
                componentRestrictions: {
                    country: "it"
                },
                types: ["address"]
            };
            var autocomplete = new google.maps.places.Autocomplete(input, options);
            autocomplete.addListener('place_changed', fillInAddress);
            // Place search listener
            function fillInAddress() {
                $("#shop-address").slideDown();
                var formComponents = {
                    route: 'long_name',
                    street_number: 'long_name',
                    administrative_area_level_2: 'long_name',
                    administrative_area_level_3: 'long_name',
                    postal_code: 'long_name'
                };
                var place = autocomplete.getPlace();
                var location = place.geometry.location;
                $("input[name='lat']").val(location.lat());
                $("input[name='lng']").val(location.lng());
                place.address_components.forEach((addrComponent) => {
                    var addrType = addrComponent.types[0];
                    if (formComponents[addrType]) {
                        $("input[name='" + addrType + "']").val(addrComponent.long_name);
                    }
                });
                // Remove unnecessary text from province
                var provinceInput = $("input[name='administrative_area_level_2']");
                var text = provinceInput.val().replace(/^.*?\s+di\s+/,"");
                provinceInput.val(text);
            }
        </script>
    </body>
</html>
