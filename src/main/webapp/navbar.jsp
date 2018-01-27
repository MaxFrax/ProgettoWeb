<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOException"%>
<%@page import="it.unitn.disi.buybuy.dao.entities.User.Type"%>
<%@page import="it.unitn.disi.buybuy.dao.UserDAO"%>
<%@page import="it.unitn.disi.buybuy.dao.entities.User"%>
<%@page import="it.unitn.disi.buybuy.dao.IssueDAO"%>
<%@page import="it.unitn.disi.buybuy.dao.IssueDAO"%>
<%@page import="it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOFactoryException"%>
<%@page import="it.unitn.aa1617.webprogramming.persistence.utils.dao.factories.DAOFactory"%>
<%
    User user = (User)request.getSession().getAttribute("user");
    if (user != null) {
        
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new RuntimeException(new ServletException("Impossible to get dao factory for user storage system"));
        }
        
        UserDAO userDao = null;
        try {
            userDao = daoFactory.getDAO(UserDAO.class);
            pageContext.setAttribute("userDao", userDao);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get dao factory for user storage system", ex));
        }
        
        IssueDAO issueDao = null;
        try {
            issueDao = daoFactory.getDAO(IssueDAO.class);
            pageContext.setAttribute("issueDao", issueDao);
        } catch (DAOFactoryException ex) {
            throw new RuntimeException(new ServletException("Impossible to get dao factory for user storage system", ex));
        }

        String contextPath = getServletContext().getContextPath();
        if (contextPath.endsWith("/")) {
            contextPath = contextPath.substring(0, contextPath.length() - 1);
        }
        pageContext.setAttribute("contextPath", contextPath);
        
        try {
            user = userDao.getByPrimaryKey(user.getId());
            Integer count = 0;
            if(user.getType() == Type.SELLER){
                count = issueDao.getCountNotReadBySellerId(user.getId());
            }
            if(user.getType() == Type.ADMINISTRATOR){
                count = issueDao.getCountNotReadForAdmin();
            }
            pageContext.setAttribute("notifications", count);
        } catch (DAOException ex) {
            throw new RuntimeException(new ServletException("Impossible to get the number of notifications", ex));
        }
    }
%>

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
                            <li><a href="${pageContext.servletContext.contextPath}/cart"><span class="glyphicon glyphicon-shopping-cart"></span><span class="hidden-xs"> Carrello</span></a></li>
                            <li><a href="signup"><span class="glyphicon glyphicon-user"></span><span class="hidden-xs"> Registrati</span></a></li>
                            <li><a href="login"><span class="glyphicon glyphicon-log-in"></span><span class="hidden-xs"> Accedi</span></a></li>
                        </c:when>
                        <%-- Registered user --%>
                        <c:when test="${user.type == 'REGISTERED'}">
                            <li><a href="${pageContext.servletContext.contextPath}/cart"><span class="glyphicon glyphicon-shopping-cart"></span><span class="hidden-xs"> Carrello</span></a></li>
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-user"></span><span class="hidden-xs"> ${user.name} ${user.lastname}</span> <span class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <li><a href="profile.jsp">Profilo</a></li>
                                    <li><a href="${pageContext.servletContext.contextPath}/orders">I miei ordini</a></li>
                                    <li role="separator" class="divider hidden-xs"></li>
                                    <li><a href="logout">Esci</a></li>
                                </ul>
                            </li>
                        </c:when>
                        <%-- Seller user --%>
                        <c:when test="${user.type == 'SELLER'}">
                            <li><a href="${pageContext.servletContext.contextPath}/cart"><span class="glyphicon glyphicon-shopping-cart"></span><span class="hidden-xs"> Carrello</span></a></li>
                            <c:choose>
                                <c:when test="${count == 0}">
                                    <li><a href="${pageContext.servletContext.contextPath}/notification"><span class="glyphicon glyphicon-bell"></span><span class="hidden-xs"> Notifiche </span></a></li>
                                </c:when>
                                <c:otherwise>
                                        <li><a href="${pageContext.servletContext.contextPath}/notification"><span class="glyphicon glyphicon-bell"></span><span class="hidden-xs"> Notifiche (<c:out value="${notifications}"/>)</span></a></li>
                                </c:otherwise>
                            </c:choose>
                            
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-user"></span><span class="hidden-xs"> ${user.name} ${user.lastname}</span> <span class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <li><a href="${pageContext.servletContext.contextPath}/profile">Profilo</a></li>
                                    <li><a href="#">Il mio negozio</a></li>
                                    <li><a href="${pageContext.servletContext.contextPath}/orders">I miei ordini</a></li>
                                    <li role="separator" class="divider hidden-xs"></li>
                                    <li><a href="logout">Esci</a></li>
                                </ul>
                            </li>
                        </c:when>
                        <%-- Administrator user --%>
                        <c:when test="${user.type == 'ADMINISTRATOR'}">
                            <c:choose>
                                <c:when test="${count == 0}">
                                    <li><a href="${pageContext.servletContext.contextPath}/notification"><span class="glyphicon glyphicon-bell"></span><span class="hidden-xs"> Notifiche </span></a></li>
                                </c:when>
                                <c:otherwise>
                                        <li><a href="${pageContext.servletContext.contextPath}/notification"><span class="glyphicon glyphicon-bell"></span><span class="hidden-xs"> Notifiche (<c:out value="${notifications}"/>)</span></a></li>
                                </c:otherwise>
                            </c:choose>
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-user"></span><span class="hidden-xs"> ${user.name} ${user.lastname}</span> <span class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <li><a href="${pageContext.servletContext.contextPath}/profile">Profilo</a></li>
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
                    <input type="hidden" name="sort" value="price_asc">
                    <button type="submit" class="btn btn-default margin-top">Cerca</button>
                </form>
            </div>
        </div>
    </div>
</nav>