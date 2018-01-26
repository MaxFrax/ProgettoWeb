<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- Redirect to login if user is not logged in --%>
<c:if test="${empty myShop}">
    <c:redirect url="/error"/>
</c:if>

<%-- Get context path --%>
<c:set var="contextPath" value="${pageContext.servletContext.contextPath}"></c:set>

    <!DOCTYPE html>
    <html>
        <head>
            <meta charset="utf-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <title>Il mio negozio</title>
            <!-- Bootstrap Core CSS -->
            <link href="css/bootstrap.min.css" rel="stylesheet">
            <!-- Custom CSS -->
            <link href="css/myshop.css" rel="stylesheet">
        </head>
        <body>
            <!-- Navbar -->
        <%@include file="navbar.jsp"%>

        <div class="container">

            <div class="page-header">
                <h2>Il mio negozio</h2>
            </div>

            <div class="row">
                <div class="col-xs-12 col-md-8 col-md-offset-2">
                    <%-- Update shop information --%>
                    <div class="page-header">
                        <h4>Informazioni</h4>
                    </div>
                    <c:choose>
                        <c:when test="${!empty errors}">
                            <div class="alert alert-danger alert-dismissible" role="alert">
                                <button type="button" class="close" data-dismiss="alert" aria-label="Chiudi"><span aria-hidden="true">&times;</span></button>
                                <ul class="errors">
                                    <c:forEach items="${errors}" var="error">
                                        <li>
                                            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                                            <span class="sr-only">Errore:</span>
                                            <span class="error">${error}</span>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </c:when>
                        <c:when test="${!empty successMsg}">
                            <div class="alert alert-success alert-dismissible" role="alert">
                                <button type="button" class="close" data-dismiss="alert" aria-label="Chiudi"><span aria-hidden="true">&times;</span></button>
                                <span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span>
                                <span class="sr-only">Successo:</span>
                                <span class="error">${successMsg.text}</span>
                            </div>
                        </c:when>
                    </c:choose>
                    <form action="${contextPath}/myshop" method="POST">
                        <div class="row">
                            <div class="form-group col-xs-12">
                                <label for="name">Nome del negozio</label>
                                <input type="text" class="form-control" id="name" name="name" placeholder="Aggiungi nome" value="${myShop.name}">
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-xs-12">
                                <label for="description">Descrizione</label>
                                <textarea class="form-control" rows="4" id="description" name="description" placeholder="Aggiungi descrizione">${myShop.description}</textarea>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-xs-12">
                                <label for="website">Sito web</label>
                                <input type="text" class="form-control" id="website" name="website" value="${myShop.website}" placeholder="Aggiungi sito web">
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-xs-12">
                                <button class="btn btn-primary pull-right" type="submit">Salva modifiche</button>
                            </div>
                        </div>
                    </form>
                    <%-- Update shop photos --%>
                    <div class="page-header">
                        <h4>Foto del negozio</h4>
                    </div> 
                    <form>
                        <div class="row">
                            <div class="form-group col-xs-12">
                                <label>Foto</label>
                                <div class="dropzone panel panel-default" id="dropzone"></div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-xs-12">
                                <button class="btn btn-primary pull-right" type="submit">Aggiungi foto</button>
                            </div>
                        </div>
                    </form>
                    <%-- Update retailer --%>
                    <div class="page-header">
                        <h4>Negozio fisico</h4>
                    </div>
                    <p>
                        Se possiedi un negozio fisico, puoi modificarne qui i dettagli. Tieni presente che le informazioni saranno visibili a tutti gli utenti.<br><br>
                        <b>Nota:</b> per modificare i campi sottostanti, devi prima selezionare un indirizzo nel campo di ricerca qui sotto.
                    </p>
                    <div class="row">
                        <div class="form-group col-xs-12">
                            <label>Cerca per indirizzo</label>
                            <div class="left-inner-addon">
                                <span class="glyphicon glyphicon-search"></span>
                                <input type="text" name="place" class="form-control" id="place-search" placeholder="es. Via Giuseppe Garibaldi, 12, Imola, BO, Italia">
                            </div>
                        </div>
                    </div>
                    <form id="shop-address" action="${contextPath}/update_retailer" method="POST">
                        <div class="row">
                            <div class="form-group col-xs-9 col-sm-10">
                                <label>Via</label>
                                <input type="text" class="form-control" name="route" value="${retailer.streetName}" placeholder="es. Via Garibaldi" readonly>
                            </div>
                            <div class="form-group col-xs-3 col-sm-2 street-number">
                                <label>Numero</label>
                                <input type="text" class="form-control" name="street_number" value="${retailer.streetNumber}" placeholder="es. 12" readonly>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>Città</label>
                            <input type="text" class="form-control" name="administrative_area_level_3" value="${retailer.city}" placeholder="es. Imola" readonly>
                        </div>
                        <div class="row">
                            <div class="form-group col-xs-8">
                                <label>Provincia</label>
                                <input type="text" class="form-control" name="administrative_area_level_2" value="${retailer.province}" placeholder="es. Bologna" readonly>
                            </div>
                            <div class="form-group col-xs-4 postal-code">
                                <label>Codice postale</label>
                                <input type="text" class="form-control" name="postal_code" value="${retailer.postalCode}" placeholder="es. 40026" readonly>
                            </div>
                        </div>
                        <input type="hidden" name="lat" value="46.068362799999996">
                        <input type="hidden" name="lng" value="46.068362799999996">
                        <div class="form-group">
                            <button class="btn btn-primary pull-right" type="submit">Salva modifiche</button>
                        </div>
                    </form>

                </div>

            </div>
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
                $("#shop-address :input").removeAttr("readonly");
                var formComponents = {
                    route: 'long_name',
                    street_number: 'long_name',
                    administrative_area_level_2: 'long_name',
                    administrative_area_level_3: 'long_name',
                    postal_code: 'long_name'
                };
                var place = autocomplete.getPlace();
                var location = place.geometry.location;
                //$("input[name='lat']").val(location.lat());
                //$("input[name='lng']").val(location.lng());
                place.address_components.forEach((addrComponent) => {
                    var addrType = addrComponent.types[0];
                    if (formComponents[addrType]) {
                        $("input[name='" + addrType + "']").val(addrComponent.long_name);
                    }
                });
                // Remove unnecessary text from province
                var provinceInput = $("input[name='administrative_area_level_2']");
                var text = provinceInput.val().replace(/^.*?\s+di\s+/, "");
                provinceInput.val(text);
            }
        </script>
    </body>
</html>