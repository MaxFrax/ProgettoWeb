<%-- 
    Document   : notifications_seller
    Created on : 27-gen-2018, 15.45.58
    Author     : apell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:choose>
    <c:when test="${issues.size() > 0}">
        <div class="section">
            <div class="container">
                <div class="row">
                    <div class="col-md-1">ID</div>
                    <div class="col-md-2">OGGETTO</div>
                    <div class="col-md-6">DESCRIZIONE DELL' UTENTE</div>
                    <div class="col-md-3">SCELTA DELL' AMMINISTRATORE</div>
                </div>
                <c:forEach items="${issues}" var="issue">
                    <hr>
                    <div class="row">
                        <div class="col-md-1">
                            <p>
                            <c:out value="${issue.getId()}" />
                            </p>
                        </div>
                        <div class="col-md-2">
                            <p>
                                <a href="${contextPath}/item?id=${issue.getPurchase().getItem().getId()}"><c:out value="${issue.getPurchase().getItem().getId()}" /></a>
                            </p>
                        </div>
                        <div class="col-md-6">               
                            <c:out value="${issue.getUserDescription()}" />
                        </div>
                        <div class="col-md-3">
                            <p>
                                <c:choose>
                                    <c:when test="${issue.getAdminChoice() == 'REFUND'}">
                                        <c:out value="Rimborso del cliente" />
                                    </c:when>
                                    <c:when test="${issue.getAdminChoice() == 'DO_NOTHING'}">
                                        <c:out value="Non fare nulla" />
                                    </c:when>
                                    <c:when test="${issue.getAdminChoice() == 'BAD_REVIEWS'}">
                                        <c:out value="Voto negativo al venditore" />
                                    </c:when>
                                    <c:when test="${issue.getAdminChoice() == 'NOT_VALID'}">
                                        <c:out value="Invalida" />
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="In lavorazione..." />
                                    </c:otherwise>
                                </c:choose>
                            </p>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="section">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">Non sono presenti notifiche non lette</div>
                </div>
            </div>
        </div>
    </c:otherwise>
</c:choose>
