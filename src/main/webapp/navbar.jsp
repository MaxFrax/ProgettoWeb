<%@page import="it.unitn.disi.buybuy.dao.entities.Category"%>
<%@page import="java.util.List"%>
<% List<Category> categories = (List<Category>)request.getAttribute("categories"); %>
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
                <form class="navbar-form" role="search" id="search-form" method="get" action="search">
                    <div class="input-group">
                        <div class="input-group-btn search-panel">
                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                <span id="selected_category">Categoria</span> <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu" role="category">
                                <li><a onclick="onCategoryChoice(null)">Tutte le categorie</a></li>
                                <c:forEach items="${categories}" var="cat">
                                    <li><a onclick="onCategoryChoice(this)">${cat.getName()}</a></li>
                                </c:forEach>
                            </ul>
                        </div>        
                        <input type="hidden" name="category">
                        <input type="text" class="form-control" name="query" placeholder="Search term...">
                        <span class="input-group-btn">
                            <button class="btn btn-default" type="submit"><span class="glyphicon glyphicon-search"></span></button>
                        </span>
                    </div>
                </form>
            </div>
            <ul class="nav navbar-nav navbar-right">
                <c:choose>
                    <%-- Guest user --%>
                    <c:when test="${empty user}">
                        <li><a href="#"><span class="glyphicon glyphicon-shopping-cart"></span> Carrello</a></li>
                        <li><a href="signup"><span class="glyphicon glyphicon-user"></span> Registrati</a></li>
                        <li><a href="login"><span class="glyphicon glyphicon-log-in"></span> Accedi</a></li>
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
<script>
    let onCategoryChoice = function (list_item) {
        let form = document.getElementById('search-form');
        if (list_item !== null) {
            console.log("a");
            form.elements.namedItem('category').value = list_item.innerText;
            let selected_category = document.getElementById('selected_category').innerText = list_item.innerText;
        } else {
            form.elements.namedItem('category').value = '';
            let selected_category = document.getElementById('selected_category').innerText = "Categoria";
        }
    };
</script>