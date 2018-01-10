<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#"><span class="glyphicon glyphicon-fire"></span> BuyBuy</a>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <div class="col-sm-3 col-md-3">
                <form class="navbar-form" role="search">
                    <div class="input-group">
                        <input type="text" class="form-control" placeholder="Cerca" name="q">
                        <div class="input-group-btn">
                            <button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>
                        </div>
                    </div>
                </form>
            </div>
            <ul class="nav navbar-nav navbar-right">
                <c:choose>
                    <%-- Guest user --%>
                    <c:when test="${empty user}">
                        <li><a href="#"><span class="glyphicon glyphicon-shopping-cart"></span> Carrello</a></li>
                        <li><a href="signup.jsp"><span class="glyphicon glyphicon-user"></span> Registrati</a></li>
                        <li><a href="login.jsp"><span class="glyphicon glyphicon-log-in"></span> Accedi</a></li>
                    </c:when>
                    <%-- Registered user --%>
                    <c:when test="${user.type == 'REGISTERED'}">
                        <li><a href="#"><span class="glyphicon glyphicon-shopping-cart"></span> Carrello</a></li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-user"></span> ${user.name} ${user.lastname} <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="#">Profilo</a></li>
                                <li><a href="#">Segnala anomalia</a></li>
                                <li role="separator" class="divider"></li>
                                <li><a href="#">Esci</a></li>
                            </ul>
                        </li>
                    </c:when>
                    <%-- Seller user --%>
                    <c:when test="${user.type == 'SELLER'}">
                        <li><a href="#"><span class="glyphicon glyphicon-shopping-cart"></span> Carrello</a></li>
                        <li><a href="#"><span class="glyphicon glyphicon-bell"></span> Notifiche</a></li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-user"></span> ${user.name} ${user.lastname} <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="#">Profilo</a></li>
                                <li><a href="#">Il mio negozio</a></li>
                                <li><a href="#">Segnala anomalia</a></li>
                                <li role="separator" class="divider"></li>
                                <li><a href="#">Esci</a></li>
                            </ul>
                        </li>
                    </c:when>
                    <%-- Administrator user --%>
                    <c:when test="${user.type == 'ADMINISTRATOR'}">
                        <li><a href="#"><span class="glyphicon glyphicon-bell"></span> Notifiche</a></li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-user"></span> ${user.name} ${user.lastname} <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="#">Profilo</a></li>
                                <li role="separator" class="divider"></li>
                                <li><a href="#">Esci</a></li>
                            </ul>
                        </li>
                    </c:when>
                </c:choose>
            </ul>                        
        </div>
    </div>
</nav>
