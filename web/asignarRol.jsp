<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="_startPanel.jsp" %>
<script src="js/updateEmpleado.js"></script>


<!-- Content Header (Page header) Esto dependera de cada pagina-->
<div class="content-header">
    <div class="container-fluid">
        <div class="row mb-2">
            <div class="col-sm-6">
                <h1 class="m-0 text-dark">Buscar Empleado</h1>
                <!-- SEARCH FORM -->
                <div class="row">
                    <div class="col-md-12">
                        <form action="${pageContext.servletContext.contextPath}/Empleados?accion=buscar" method="post" class="form-inline ml-3">
                            <div class="input-group input-group-sm">
                                <input required autofocus name="fullname" class="form-control form-control-navbar" type="search" placeholder="Digite nombre completo" aria-label="Search">
                                <div class="input-group-append">
                                    <button class="btn btn-navbar" type="submit">
                                        <i class="fas fa-search"></i>
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>            
            </div><!-- /.col -->
            <div class="col-sm-6">
                <ol class="breadcrumb float-sm-right">

                </ol>
            </div><!-- /.col -->
        </div><!-- /.row -->
    </div><!-- /.container-fluid -->
</div>
<!-- /.content-header -->



<!-- Main content -->
<section class="content">
    <div class="container-fluid">
        <!-- Small boxes (Stat box) -->
        <div class="row">
            <!-- Table row -->

            <div class="col-md-12 table-responsive">
                <table class="table table-striped">
                    <thead>

                        <tr>
                            <th>ID</th>
                            <th>Nombre completo</th>
                            <th>Departamento</th>
                            <th>Rol</th>
                            <th>Opciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        
                        <c:if test="${listEmpleados != null}">
                            <c:forEach var="listUser" items="${listEmpleados}">
                                <tr id="id${listUser.getUsuario().getIdUser()}">
                                    <td>${listUser.getUsuario().getIdUser()}</td>
                                    <td hidden>${listUser.getDepto().getIdDepto()}</td>
                                    <td hidden>${listUser.getRol().getIdRol()}</td>
                                    <td>${listUser.getUsuario().getFirsName()} ${listUser.getUsuario().getLastName()}</td>
                                    <td>${listUser.getDepto().getDeptoName()}</td>
                                    <td>${listUser.getRol().getRoleName()}</td>
                                    <td>
                                        <button type="button" class="btn btn-info" onclick="updateUser('id${listUser.getUsuario().getIdUser()}', 1)">Actualizar</button>
                                        <button type="button" class="btn btn-danger" onclick="updateUser('id${listUser.getUsuario().getIdUser()}', 2)">Eliminar</button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:if>

                        <c:if test="${listEmpleado != null}">
                            <c:forEach var="listUser" items="${listEmpleado}">
                                <tr id="id${listUser.getUsuario().getIdUser()}">
                                    <td>${listUser.getUsuario().getIdUser()}</td>
                                    <td hidden>${listUser.getDepto().getIdDepto()}</td>
                                    <td hidden>${listUser.getRol().getIdRol()}</td>
                                    <td>${listUser.getUsuario().getFirsName()} ${listUser.getUsuario().getLastName()}</td>
                                    <td>${listUser.getDepto().getDeptoName()}</td>
                                    <td>${listUser.getRol().getRoleName()}</td>
                                    <td>
                                        <button type="button" class="btn btn-info" onclick="updateUser('id${listUser.getUsuario().getIdUser()}', 1)">Actualizar</button>
                                        <button type="button" class="btn btn-danger" onclick="updateUser('id${listUser.getUsuario().getIdUser()}', 2)">Eliminar</button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:if>
                        
                        
                        
                                <c:if test="${dataEmpty == 'true'}">
                        <div class="alert alert-warning" role="alert">
                            No se encontro ninguna resultado
                        </div>
                    </c:if>



                    </tbody>
                </table>
            </div>

            <!-- ./col -->
        </div>
        <!-- /.row -->
        <!-- Main row -->

        <!-- /.row (main row) -->
    </div><!-- /.container-fluid -->
</section>
<!-- /.content -->
</div>


<!--Modal for Update Empleado-->
<div id="modaltoUpdateUser" class="modal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Actualizar</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p>Cambia de Departamento o de Rol al Empleado: <br>
                    <span class="alert-link" id="empleado"></span>
                </p>
                <form action="${pageContext.servletContext.contextPath}/Empleados?accion=updateRolDepto" method="post">
                    <input id="inputuser" type="hidden" value="" name="iduser">
                    <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <label class="input-group-text" for="inputGroupSelect01">Departamentos</label>
                        </div>
                        <select name="depto" class="custom-select" id="inputGroupSelect01">

                            <c:forEach var="listDepto" items="${listDepto}">
                                <option value="${listDepto.getIdDepto()}">${listDepto.getDeptoName()}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <label class="input-group-text" for="inputGroupSelect01">Roles</label>
                        </div>
                        <select name="rol" class="custom-select" id="inputGroupSelect02">

                            <c:forEach var="listRol" items="${listRol}">
                                <option value="${listRol.getIdRol()}">${listRol.getRoleName()}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary">Guardar Cambios</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!--Modal for Delete Empleado-->
<div id="modaltoDeleteUser" class="modal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Elimnar Empleado</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form action="${pageContext.servletContext.contextPath}/Empleados?accion=delete" method="post">
                <div class="modal-body">

                    <input id="deleteiduser" type="hidden" value="" name="deleteiduser">

                    <p>Esta seguro de elimnar empleado?</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Confimar</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- /.No quitar esto, copiar en todos los demas -->          
</div>
<!-- /.container-fluid -->
</section>
<%@include file="_endPanel.jsp" %>