<%-- 
    Document   : notifications_admin
    Created on : 27-gen-2018, 15.46.11
    Author     : apell
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:if test="${title ne 'Tutte le anomalie'}">
    <c:choose>
        <c:when test="${issues.size() > 0}">
            <div class="section">
                <div class="container">
                    <div class="row">
                        <div class="col-md-1">ID</div>
                        <div class="col-md-1">NEGOZIO</div>
                        <div class="col-md-1">OGGETTO</div>
                        <div class="col-md-6">DESCRIZIONE DELL' UTENTE</div>
                        <div class="col-md-3">AZIONE</div>
                    </div>
                    <c:forEach items="${issues}" var="issue">
                        <hr>
                        <div class="row">
                            <div class="col-md-1">
                                <p>
                                    <c:out value="${issue.getId()}" />
                                </p>
                            </div>
                            <div class="col-md-1">
                                <p>
                                    <a href="${contextPath}/shop?id=${issue.getPurchase().getItem().getSeller().getId()}"><c:out value="${issue.getPurchase().getItem().getSeller().getId()}" /></a>
                                </p>
                            </div>
                            <div class="col-md-1">
                                <p>
                                    <a href="${contextPath}/item?id=${issue.getPurchase().getItem().getId()}"><c:out value="${issue.getPurchase().getItem().getId()}" /></a>
                                </p>
                            </div>
                            <div class="col-md-6">               
                                <c:out value="${issue.getUserDescription()}" />
                            </div>
                            <div class="col-md-3">
                                <form action="${contextPath}/resolveissue" method="GET" class="form-horizontal" role="form">
                                    <div class="form-group">
                                        <div class="col-md-6">
                                            <select class="form-control" name="choice${issue.getId()}">
                                                <option value="0" selected>Seleziona</option>
                                                <option value="1">Rimborso</option>
                                                <option value="2">Non fare nulla</option>
                                                <option value="3">Valutazione negativa</option>
                                                <option value="4">Non valida</option>
                                            </select>
                                        </div>
                                        <div class="col-md-6">
                                            <input class="btn btn-primary input" value="OK" type="submit">
                                        </div>
                                    </div>
                                </form>
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
                        <div class="col-md-12">Non sono presenti anomalie da processare</div>
                    </div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</c:if>
<c:if test="${title == 'Tutte le anomalie'}">
    <c:choose>
        <c:when test="${issues.size() > 0}">
            <div class="section">
                <div class="container">
                    <div class="row">
                        <div class="col-md-1">ID</div>
                        <div class="col-md-1">NEGOZIO</div>
                        <div class="col-md-1">OGGETTO</div>
                        <div class="col-md-6">DESCRIZIONE DELL' UTENTE</div>
                        <div class="col-md-3">SCELTA</div>
                    </div>
                    <c:forEach items="${issues}" var="issue">
                        <hr>
                        <div class="row">
                            <div class="col-md-1">
                                <p>
                                    <c:out value="${issue.getId()}" />
                                </p>
                            </div>
                            <div class="col-md-1">
                                <p>
                                    <a href="${contextPath}/shop?id=${issue.getPurchase().getItem().getSeller().getId()}"><c:out value="${issue.getPurchase().getItem().getSeller().getId()}" /></a>
                                </p>
                            </div>
                            <div class="col-md-1">
                                <p>
                                    <a href="${contextPath}/item?id=${issue.getPurchase().getItem().getId()}"><c:out value="${issue.getPurchase().getItem().getId()}" /></a>
                                </p>
                            </div>
                            <div class="col-md-6">               
                                <c:out value="${issue.getUserDescription()}" />
                            </div>
                            <div class="col-md-3">
                                <c:if test="${issue.getAdminChoice()!= 'PENDING'}">
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
                                        </c:choose>
                                    </p>
                                </c:if>                                
                                <c:if test="${issue.getAdminChoice()== 'PENDING'}">
                                    <form action="${contextPath}/resolveissue" method="GET" class="form-horizontal" role="form">
                                        <div class="form-group">
                                            <div class="col-md-6">
                                                <select class="form-control" name="choice${issue.getId()}">
                                                    <option value="0" selected>Seleziona</option>
                                                    <option value="1">Rimborso</option>
                                                    <option value="2">Non fare nulla</option>
                                                    <option value="3">Valutazione negativa</option>
                                                    <option value="4">Non valida</option>
                                                </select>
                                            </div>
                                            <div class="col-md-6">
                                                <input class="btn btn-primary input" value="OK" type="submit">
                                            </div>
                                        </div>
                                    </form>
                                </c:if>
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
                        <div class="col-md-12">Non sono presenti anomalie</div>
                    </div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</c:if>

