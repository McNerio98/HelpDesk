
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
            String[] icons = new String[7];
            icons[0] = "fas fa-columns";
            icons[1] = "fas fa-calendar-plus";
            icons[2] = "fas fa-users";
            icons[3] = "fas fa-users-cog";
            icons[4] = "fas fa-grip-horizontal";
            icons[5] = "fas fa-layer-group";
            icons[6] = "fas fa-chart-line";

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
            </ul>
        </nav>
    </div>
</aside>
<!-- ./aside -->