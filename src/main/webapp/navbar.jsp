<nav class="navbar navbar-inverse" id="navbar-top">
    <div class="container-fluid">
        <div class="row">
            <div class="col-xs-4">
                <div class="navbar-header">
                    <a class="navbar-brand" href="${pageContext.servletContext.contextPath}"><span class="glyphicon glyphicon-fire"></span> BuyBuy</a>
                </div>
            </div>
            <div class="col-xs-8">
                <ul class="nav navbar-nav navbar-right">
                    <c:choose>
                        <%-- Guest user --%>
                        <c:when test="${empty user}">
                            <li><a href="#"><span class="glyphicon glyphicon-shopping-cart"></span><span class="hidden-xs"> Carrello</span></a></li>
                            <li><a href="signup"><span class="glyphicon glyphicon-user"></span><span class="hidden-xs"> Registrati</span></a></li>
                            <li><a href="login"><span class="glyphicon glyphicon-log-in"></span><span class="hidden-xs"> Accedi</span></a></li>
                        </c:when>
                        <%-- Registered user --%>
                        <c:when test="${user.type == 'REGISTERED'}">
                            <li><a href="#"><span class="glyphicon glyphicon-shopping-cart"></span><span class="hidden-xs"> Carrello</span></a></li>
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-user"></span><span class="hidden-xs"> ${user.name} ${user.lastname}</span> <span class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <li><a href="#">Profilo</a></li>
                                    <li><a href="#">Segnala anomalia</a></li>
                                    <li role="separator" class="divider hidden-xs"></li>
                                    <li><a href="logout">Esci</a></li>
                                </ul>
                            </li>
                        </c:when>
                        <%-- Seller user --%>
                        <c:when test="${user.type == 'SELLER'}">
                            <li><a href="#"><span class="glyphicon glyphicon-shopping-cart"></span><span class="hidden-xs"> Carrello</span></a></li>
                            <li><a href="#"><span class="glyphicon glyphicon-bell"></span><span class="hidden-xs"> Notifiche</span></a></li>
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-user"></span><span class="hidden-xs"> ${user.name} ${user.lastname}</span> <span class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <li><a href="#">Profilo</a></li>
                                    <li><a href="#">Il mio negozio</a></li>
                                    <li><a href="#">Segnala anomalia</a></li>
                                    <li role="separator" class="divider hidden-xs"></li>
                                    <li><a href="logout">Esci</a></li>
                                </ul>
                            </li>
                        </c:when>
                        <%-- Administrator user --%>
                        <c:when test="${user.type == 'ADMINISTRATOR'}">
                            <li><a href="#"><span class="glyphicon glyphicon-bell"></span><span class="hidden-xs"> Notifiche</span></a></li>
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-user"></span><span class="hidden-xs"> ${user.name} ${user.lastname}</span> <span class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <li><a href="#">Profilo</a></li>
                                    <li role="separator" class="divider hidden-xs"></li>
                                    <li><a href="logout">Esci</a></li>
                                </ul>
                            </li>
                        </c:when>
                    </c:choose>
                </ul>
            </div>
        </div>
    </div>
</nav>
<nav class="navbar navbar-inverse" id="navbar-search">
    <div class="container-fluid">
        <div class="row">
            <div class="col-xs-12">
                <form class="navbar-form" action="${pageContext.servletContext.contextPath}/search" method="GET">
                    <input type="text" class="form-control" placeholder="Cosa cerchi?" name="query" value="${param.query}">
                    <select class="form-control margin-top" name="category">
                        <option value="">Tutte le categorie</option>
                        <c:forEach items="${applicationScope.categories}" var="cat">
                            <option value="${cat.id}" ${cat.id == param.category ? 'selected' : ''}>${cat.name}</option>
                        </c:forEach>
                    </select>
                    <input type="text" class="form-control margin-top" placeholder="Dove?" name="location" value="${param.location}">
                    <button type="submit" class="btn btn-default margin-top">Cerca</button>
                </form>
            </div>
        </div>
    </div>
</nav>