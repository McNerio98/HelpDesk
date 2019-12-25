
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
                <img src="dist/img/user2-160x160.jpg" class="img-circle elevation-2" alt="User Image">
            </div>
            <div class="info">
                <a href="#" class="d-block">${Usuario}</a>
            </div>
        </div>

        <!-- Sidebar Menu -->
        <nav class="mt-2">
            <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
                <li class="nav-item">
                    <a href="/Helpdesk/pnlPrincipal.jsp" class="nav-link">
                        <i class="fas fa-circle nav-icon"></i>
                        <p>
                            Incidencias
                        </p>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="pages/gallery.html" class="nav-link">
                        <i class="fas fa-circle nav-icon"></i>
                        <p>
                            Agregar Departamento
                        </p>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="/Helpdesk/asignarRol.jsp" class="nav-link">
                        <i class="fas fa-circle nav-icon"></i>
                        <p>
                            Asignar rol a usuario
                        </p>
                    </a>
                </li>
            </ul>
        </nav>
    </div>
</aside>
<!-- ./aside -->