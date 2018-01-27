<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- Redirect to error page if token is not set --%>
<c:if test="${empty checkoutOk}">
    <c:redirect url="/error"/>
</c:if>
<%-- Get context path --%>
<c:set var="contextPath" value="${pageContext.servletContext.contextPath}">
</c:set>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Completa l'ordine</title>
        <!-- Bootstrap Core CSS -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom CSS -->
        <link href="css/checkout.css" rel="stylesheet">
    </head>
    <body>

        <div class="container">
            <!-- Logo -->
            <div class="row logo">
                <div class="col-xs-12"><img class="img-responsive center-block" src="img/logo.png" alt="BuyBuy"></div>
            </div>
            <div class="row text-center">
                <div class="col-xs-12 col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3">
                    <h4 class="page-header">Ci siamo quasi! Inserisci i dati per completare l'ordine.</h4>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12 col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3">

                    <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                        <c:if test="${!empty shipping}">
                            <%-- Shipping information--%>
                            <div class="panel panel-default">
                                <div class="panel-heading" role="tab" id="headingOne">
                                    <h4 class="panel-title">
                                        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                            Indirizzo di spedizione
                                        </a>
                                    </h4>
                                </div>
                                <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
                                    <div class="panel-body">
                                        <p>
                                            <strong>Nota:</strong> l'indirizzo di spedizione si applica solo agli oggetti per i quali non è stato selezionato "Ritiro in negozio"
                                        </p>
                                        <hr>
                                        <form id="address">
                                            <div class="row">
                                                <div class="form-group col-xs-9">
                                                    <label for="street_name">Via</label>
                                                    <input type="text" class="form-control" id="street_name" name="street_name" placeholder="es. Via Garibaldi">
                                                </div>
                                                <div class="form-group col-xs-3 street-number">
                                                    <label for="street_number">Numero</label>
                                                    <input type="text" class="form-control" id="street_number" name="street_number" placeholder="es. 12">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label for="city">Città</label>
                                                <input type="text" class="form-control" id="city" name="city" placeholder="es. Imola">
                                            </div>
                                            <div class="row">
                                                <div class="form-group col-xs-8 province">
                                                    <label for="province">Provincia</label>
                                                    <input type="text" class="form-control" id="province" name="province" placeholder="es. Bologna">
                                                </div>
                                                <div class="form-group col-xs-4 postal-code">
                                                    <label for="postal_code" class="text-nowrap">Codice postale</label>
                                                    <input type="text" class="form-control" id="postal_code" name="postal_code" placeholder="es. 40026">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <button class="btn btn-primary pull-right" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">Continua</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <%-- Payment information --%>
                        <div class="panel panel-default">
                            <div class="panel-heading" role="tab" id="headingTwo">
                                <h4 class="panel-title">
                                    <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                        Dettagli di pagamento
                                    </a>
                                </h4>
                            </div>
                            <div id="collapseTwo" class="panel-collapse collapse ${empty shipping ? 'in':''}" role="tabpanel" aria-labelledby="headingTwo">
                                <div class="panel-body">
                                    <form id="payment">
                                        <div class="row">
                                            <div class="form-group col-xs-12">
                                                <label for="card_holder">Intestatario carta</label>
                                                <input class="form-control" type="text" id="card_holder" name="card_holder" placeholder="Mario Rossi">
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="form-group col-xs-12">
                                                <label for="card_number">Numero carta</label>
                                                <input class="form-control" type="text" id="card_number" name="card_number" placeholder="1234 1234 1234 1234" maxlength="19">
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="form-group col-xs-4">
                                                <label for="exp_month">Mese di scadenza</label>
                                                <input class="form-control" type="text" id="exp_month" name="exp_month" placeholder="MM" maxlength="2">
                                            </div>
                                            <div class="form-group col-xs-4">
                                                <label for="exp_year">Anno di scadenza</label>
                                                <input class="form-control" type="text" id="exp_year" name="exp_year" placeholder="AAAA" maxlength="4">
                                            </div>
                                            <div class="form-group col-xs-4">
                                                <label for="security_code">Cod. di sicurezza</label>
                                                <input class="form-control" type="text" id="securiy_code" name="security_code" placeholder="123" maxlength="3">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <button class="btn btn-primary pull-right" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" onclick="fillSummary()">Continua</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <%-- Confirmation --%>
                        <div class="panel panel-default">
                            <div class="panel-heading" role="tab" id="headingThree">
                                <h4 class="panel-title">
                                    <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree" onclick="fillSummary()">
                                        Conferma
                                    </a>
                                </h4>
                            </div>
                            <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <p>
                                                Controlla i tuoi dati prima di acquistare. Cliccando su "Acquista ora", verrà effettuato il pagamento.
                                            </p>
                                            <table id="summary">
                                                <c:if test="${!empty shipping}">
                                                    <tr>
                                                        <th colspan="2">Indirizzo di spedizione</th>
                                                    </tr>
                                                    <tr>
                                                        <td><strong>Via</strong></td>
                                                        <td class="street_name"></td>
                                                    </tr>
                                                    <tr>
                                                        <td><strong>Numero</strong></td>
                                                        <td class="street_number" ></td>
                                                    </tr>
                                                    <tr>
                                                        <td><strong>Città</strong></td>
                                                        <td class="city"></td>
                                                    </tr>
                                                    <tr>
                                                        <td><strong>Provincia</strong></td><td class="province"></td></tr>
                                                    <tr>
                                                        <td><strong>Codice postale</strong></td>
                                                        <td class="postal_code"></td>
                                                    </tr>
                                                </c:if>
                                                <tr>
                                                    <th colspan="2" class="payment">Dettagli di pagamento</th></tr>
                                                <tr>
                                                    <td><strong>Intestatario carta</strong></td>
                                                    <td class="card_holder"></td>
                                                </tr>
                                                <tr>
                                                    <td><strong>Numero carta</strong></td>
                                                    <td class="card_number"></td>
                                                </tr>
                                                <tr>
                                                    <td><strong>Mese di scadenza</strong></td>
                                                    <td class="exp_month"></td>
                                                </tr>
                                                <tr>
                                                    <td><strong>Anno di scadenza</strong></td>
                                                    <td class="exp_year"></td>
                                                </tr>
                                                <tr>
                                                    <td><strong>Cod. di sicurezza</strong></td>
                                                    <td class="security_code"></td>
                                                </tr>
                                            </table>
                                        </div>
                                    </div>
                                    <div class="row buy">
                                        <div class="form-group col-xs-12">
                                            <button class="btn btn-primary col-xs-12" onclick="submitForm(this)">Acquista ora</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%@include file="footer.jsp"%>
        </div>

        <script>
            function fillSummary() {
                var inputs = document.querySelectorAll("#accordion input");
                var table = document.getElementById("summary");
                for (i = 0; i < inputs.length; i++) {
                    // Inject value into table
                    var selector = "#summary td." + inputs[i].name;
                    var td = document.querySelector(selector);
                    td.textContent = inputs[i].value;
                }
            }
            function submitForm(btn) {
                $(btn).prop('disabled', true);
                var qs = $("form").serialize();
                var url = "${contextPath}/process_order"${!empty shipping ? " + \"?shipping=true\"":""};
                        $.ajax({
                            method: "POST",
                            url: url,
                            data: qs,
                            dataType: "text",
                            error: function () {
                                $(btn).text("Errore");
                            },
                            success: function (url) {
                                window.location.href = url;
                            }
                        });
            }
        </script>


        <!-- jQuery -->
        <script src="js/jquery.js"></script>
        <!-- Bootstrap Core JavaScript -->
        <script src="js/bootstrap.min.js"></script>
    </body>

</html>