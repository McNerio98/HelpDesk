<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="_startPanel.jsp" %>
<script src="js/updateEmpleado.js"></script>


<!-- Content Header (Page header) Esto dependera de cada pagina-->
<div class="content-header">
    <div class="container-fluid">
        <div class="row mb-2">
            <div class="col-sm-6">
                <c:if test="${error!=null}">
                    <div class="alert alert-danger" role="alert">
                        ${error}
                    </div>
                </c:if>
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

            <div class="card col-12">
                <div class="card-header">
                    <h3 class="card-title">Lista de Empleados</h3>
                </div>
                <!-- /.card-header -->
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered" id="table_empleados">
                            <thead>

                                <tr>
                                    <th>ID</th>
                                    <th>Nombre completo</th>
                                    <th>Correo Electronico</th>
                                    <th>Contacto</th>
                                    <th>Departamento</th>
                                    <th>Rol</th>
                                    <th>Opciones</th>
                                </tr>
                            </thead>
                            <tbody>





                            </tbody>
                        </table>
                    </div>
                </div>
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
                    <input type="hidden" value="${typeSession}" name="sessiontype">
                    <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <label class="input-group-text" for="inputGroupSelect01">Departamentos</label>
                        </div>
                        <select name="depto" class="custom-select" id="inputGroupSelect01" value="">

                            <c:forEach var="listDepto" items="${listDepto}">
                                <option value="${listDepto.getIdDepto()}">${listDepto.getDeptoName()}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <label class="input-group-text" for="inputGroupSelect01">Roles</label>
                        </div>
                        <select name="rol" class="custom-select" id="inputGroupSelect02" value="">
                            <c:if test="${typeSession == 'HD'}">
                                <c:forEach var="listRol" items="${listRol}">

                                    <c:if test="${listRol.getIdRol() == 1 || listRol.getIdRol() == 2 || listRol.getIdRol() == 3 || listRol.getIdRol() == 4}">
                                        <option value="${listRol.getIdRol()}">${listRol.getRoleName()}</option>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                            <c:if test="${typeSession == 'REQ'}">
                                <c:forEach var="listRol" items="${listRol}">

                                    <c:if test="${listRol.getIdRol() == 5 || listRol.getIdRol() == 6 || listRol.getIdRol() == 7 || listRol.getIdRol() == 8 || listRol.getIdRol() == 9}">
                                        <option value="${listRol.getIdRol()}">${listRol.getRoleName()}</option>
                                    </c:if>
                                </c:forEach>
                            </c:if>
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

<script>
    var sesion = '${typeSession}';
    console.log(sesion);
    var idCaseSesion = 0;
    if (sesion != "HD") {
        idCaseSesion = 1;
    }
    console.log("CASE: " + idCaseSesion);
    $(document).ready(function () {
        $('#table_empleados').DataTable({
            responsive: true,
            ajax: {
                url: '${pageContext.servletContext.contextPath}/Empleados?accion=getAll&idcase=' + idCaseSesion,
                dataSrc: ''
            },
            "createdRow": function (row, data, index) {

                // Add identity if it specified
                if (data.hasOwnProperty("id")) {
                    row.id = "id" + data.id;
                }
            },
            columns: [
                {data: 'id'},
                {
                    data: null,
                    render: function (data, type, row) {
                        // Combine the first and last names into a single table field
                        return data.name + ' ' + data.lastname;
                    },
                    editField: ['name', 'lastname']
                },
                {data: 'email'},
                {data: 'contacto'},
                {data: 'departamento'},
                {data: 'rol'},
                {
                    data: null,
                    render: function (data, type, row) {
                        // Combine the first and last names into a single table field
                        return `<button type="button" class="btn btn-info" onclick="updateUser('id` + data.id + `', 1)">Actualizar</button>
                                        `;
                    },

                },
            ],
            language:
                    {
                        "sProcessing": "Procesando...",
                        "sLengthMenu": "Mostrar _MENU_ registros",
                        "sZeroRecords": "No se encontraron resultados",
                        "sEmptyTable": "Ningun dato disponible en esta tabla",
                        "sInfo": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
                        "sInfoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
                        "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
                        "sInfoPostFix": "",
                        "sSearch": "Buscar:",
                        "sUrl": "",
                        "sInfoThousands": ",",
                        "sLoadingRecords": "Ningun dato disponible en esta tabla",
                        "oPaginate": {
                            "sFirst": "Primero",
                            "sLast": "Último",
                            "sNext": "Siguiente",
                            "sPrevious": "Anterior"
                        },
                        "oAria": {
                            "sSortAscending": ": Activar para ordenar la columna de manera ascendente",
                            "sSortDescending": ": Activar para ordenar la columna de manera descendente"
                        },
                        "buttons": {
                            "copy": "Copiar",
                            "colvis": "Visibilidad"
                        }
                    }

        });
    });

</script>