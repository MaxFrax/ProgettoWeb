<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
                        <input type="text" class="form-control" placeholder="Search" name="q">
                        <div class="input-group-btn">
                            <button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>
                        </div>
                    </div>
                </form>
            </div>
            <ul class="nav navbar-nav navbar-right">
                <c:choose>
                    <c:when test="${empty user}">
                        <li><a href="Signup"><span class="glyphicon glyphicon-user"></span> Registrati</a></li>
                        <li><a href="Login"><span class="glyphicon glyphicon-log-in"></span> Accedi</a></li>
                        <li><a href="#"><span class="glyphicon glyphicon-shopping-cart"></span> Carrello</a></li>
                    </c:when>       
                    <c:otherwise>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">${user.name} ${user.lastname} <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <!--<li><a href="#">Action</a></li>
                                <li><a href="#">Another action</a></li>
                                <li><a href="#">Something else here</a></li>
                                <li role="separator" class="divider"></li>
                                <li><a href="#">Separated link</a></li>
                                <li role="separator" class="divider"></li>
                                <li><a href="#">One more separated link</a></li>-->
                            </ul>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>                        
        </div>
    </div>
</nav>
