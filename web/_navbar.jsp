
<!-- Navbar -->
<nav class="main-header navbar navbar-expand navbar-white navbar-light">
    <!-- Left navbar links -->
    <ul class="navbar-nav">
        <li class="nav-item">
            <a class="nav-link" data-widget="pushmenu" href="#"><i class="fas fa-bars"></i></a>
        </li>
        <li class="nav-item d-none d-sm-inline-block">
            <a href="${pageContext.servletContext.contextPath}/Principal" class="nav-link">Inicio</a>
        </li>
        <li class="nav-item d-none d-sm-inline-block">
            <a href="${pageContext.servletContext.contextPath}/Perfil" class="nav-link">Configuracion</a>

        </li>
    </ul>


    <!-- Right navbar links -->
    <ul class="navbar-nav ml-auto">

        <!-- Notificacion en el caso que sea gerente -->
        <c:if test="${requestEmpleado != null}">
            <li class="nav-item dropdown">
                <a class="nav-link" data-toggle="dropdown" href="#">
                    <i class="far fa-bell"></i>
                    <span class="badge badge-warning navbar-badge">${fn:length(requestEmpleado)}</span>
                </a>
                <div class="dropdown-menu dropdown-menu-lg dropdown-menu-right">
                    <span class="dropdown-item dropdown-header">${fn:length(requestEmpleado)} Solicitudes de Empleado</span>
                    <div class="dropdown-divider"></div>
                    <ul class="list-group">
                        <c:forEach var="listUser" items="${requestEmpleado}">


                            <li id="${listUser.getUsuario().getIdUser()}" class="list-group-item d-flex justify-content-between align-items-center">
                                ${listUser.getUsuario().getFirsName()} ${listUser.getUsuario().getLastName()}
                                <a onclick="changeRol(${listUser.getUsuario().getIdUser()},${listUser.getDepto().getIdDepto()})" href="#">
                                    Aceptar</a>
                            </li>



                        </c:forEach>
                    </ul>

                    <div class="dropdown-divider"></div>
                    <a href="${pageContext.servletContext.contextPath}/Empleados" class="dropdown-item dropdown-footer">Ver todas</a>
                </div>
            </li>
        </c:if>
        <!-- Notificacion en el caso que sea lider -->
        <c:if test="${requestIncidencia != null}">
            <li class="nav-item dropdown">
                <a class="nav-link" data-toggle="dropdown" href="#">
                    <i class="far fa-bell"></i>
                    <span class="badge badge-warning navbar-badge">${fn:length(requestIncidencia)}</span>
                </a>
                <div class="dropdown-menu dropdown-menu-lg dropdown-menu-right">
                    <span class="dropdown-item dropdown-header">${fn:length(requestIncidencia)} Solicitudes de Incidencia</span>
                    <div class="dropdown-divider"></div>
                    <ul class="list-group">
                        <c:forEach var="listIncidencia" items="${requestIncidencia}">


                            <div class="dropdown-divider"></div>
                            <a href="${pageContext.servletContext.contextPath}/Informacion?idIncidencia=${listIncidencia.getIdIncidence()}" class="dropdown-item">
                                <i class="fas fa-file mr-2"></i>
                                ${listIncidencia.getTitle()}
                                <span class="float-right text-muted text-sm">2 days</span>
                            </a>



                        </c:forEach>
                    </ul>

                    
                </div>
            </li>
        </c:if>
        <li class="nav-item"> 
            <a href="Principal?accion=logout" class="btn btn-link d-none d-sm-block">Cerrar Sesion</a>
            <a href="Principal?accion=logout" class="btn btn-link d-block d-sm-none"><i class="fas fa-sign-out-alt"></i></a
        </li>

    </ul>
</nav>

<script>

    function changeRol(iduser, iddepto) {
        fetch("${pageContext.servletContext.contextPath}/Empleados?accion=updateRolDepto", {
            method: 'post',
            body: 'rol=' + 3 + '&depto=' + iddepto + '&iduser=' + iduser,
            headers: {'Content-type': 'application/x-www-form-urlencoded'}
        })
                .then((response) => response.text())
                .then((responseText) => {
                    document.getElementById(iduser).style.display = "none";
                    location.href = "${pageContext.servletContext.contextPath}${requestScope['javax.servlet.forward.servlet_path']}";

                })
                .catch((error) => {
                    alert(error);
                });
    }

</script>

<!-- /.navbar -->