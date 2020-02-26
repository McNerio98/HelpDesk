
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<aside class="main-sidebar sidebar-dark-primary elevation-4">
    <!-- Brand Logo -->
    <a href="${pageContext.servletContext.contextPath}/Principal" class="brand-link">
        <img src="img/Logo.png" alt="HelpDesk Logo" class="brand-image img-circle elevation-3"
             style="opacity: .8">
        <span class="brand-text font-weight-light">HelpDesk</span>
    </a>

    <!-- Sidebar -->
    <div class="sidebar">
        <!-- Sidebar user panel (optional) -->
        <div class="user-panel mt-3 pb-3 mb-3 d-flex">
            <div class="image">
                <img src="framework/img/user2-160x160.jpg" class="img-circle elevation-2" alt="User Image">
            </div>
            <div class="info">
                <a href="${pageContext.servletContext.contextPath}/Perfil" class="d-block">${Usuario}</a>
            </div>
        </div>

        <!--Icons Arrays-->
        <%

            String[] icons = new String[11];
            icons[0] = "fas fa-columns";
            icons[1] = "fas fa-book-reader";
            icons[2] = "fas fa-book-reader";
            icons[3] = "fas fa-calendar-plus";
            icons[4] = "fas fa-users";
            icons[5] = "fas fa-users-cog";
            icons[6] = "fas fa-grip-horizontal";
            icons[7] = "fas fa-layer-group";
            icons[8] = "fas fa-chart-line";
            icons[9] = "fas fa-chart-line";
            icons[10] = "fas fa-chart-line";

            request.setAttribute("lcom", icons);
        %>
        <!-- Sidebar Menu -->

        <!-- Sidebar Menu -->
        <nav class="mt-2">
            <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
                <c:forEach var="iterador" items="${MenuPrincipal}">
                    <li class="nav-item">
                        <a href="${pageContext.servletContext.contextPath}${iterador.controller}" class="nav-link">
                            <i class="${lcom[iterador.idMenu-1]}"></i>    
                            <p>${iterador.menu}</p>
                        </a>
                    </li>
                </c:forEach>
                <c:if test="${typeSession == 'REQ'}"> 
                    <c:if test="${Rol == 9}">
                        <div class="input-group input-group-sm" style="border-radius: 7px; overflow: hidden;">
                            <input class="form-control form-control-navbar" type="search" placeholder="Buscar" aria-label="Buscar" style="background-color: #f2f4f6;">
                            <div class="input-group-append" style="background-color: #f2f4f6;">
                                <button class="btn btn-navbar" type="submit">
                                    <i class="fas fa-search"></i>
                                </button>
                            </div>
                        </div>
                        <c:forEach var="iterador" items="${ListEmpresas}">
                        <li class="nav-item has-treeview pb-1">
                            <a href="#" class="nav-link">
                                <i class="nav-icon fas fa-city"></i>
                                <p>
                                    ${iterador.getEmpresa().getNombre()}
                                    <i class="right fas fa-angle-left"></i>
                                </p>
                            </a>
                            <ul class="nav nav-treeview" style="display: none;">
                                <c:forEach var="deptos" items="${iterador.getListDeptos()}">
                                <li class="nav-item">
                                    <a href="${pageContext.servletContext.contextPath}/PrincipalRequisicion?accion=load&idemp=${iterador.getEmpresa().getIdEmpresa()}&iddep=${deptos.getIdDepto()}" class="nav-link">
                                        <i class="far fa-circle nav-icon"></i>
                                        <p>${deptos.getDeptoName()}</p>
                                    </a>
                                </li>
                                </c:forEach>
                            </ul>
                        </li>
                        </c:forEach>
                    </c:if>
                </c:if>
            </ul>
        </nav>
    </div>
</aside>
<!-- ./aside -->