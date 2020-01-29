<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="_startPanel.jsp" %>


<!-- Content Header (Page header) Esto dependera de cada pagina-->
<div class="content-header">
    <div class="container-fluid">
        <div class="row">
            <div class="col-12">
                <c:if test="${errorCharacters!=null}">
                    <div class="alert alert-danger" role="alert">
                        Error al ${errorCharacters} el registro. Uno o ambos de los campos sobrepasan la longitud de caracteres
                    </div>
                </c:if>
            </div>
        </div>
        <div class="row mb-2">
            <div class="col-sm-6">
                <div class="col-sm-6 col-md-6">

                    <h2 class=" text-dark">Lista de Clasificaciones</h2>

                </div><!-- /.col -->


            </div><!-- /.col -->
            <div class="col-sm-6">
                <ol class="breadcrumb float-sm-right">
                    <button onclick="newForm()" type="button" class="btn btn-primary">Nuevo</button>

                </ol>
            </div><!-- /.col -->
        </div><!-- /.row -->
    </div><!-- /.container-fluid -->
</div>
<!-- /.content-header -->


<!-- /.content-header -->

<!-- Main content -->
<section class="content">
    <div class="container-fluid">

        <!--Table-->
        <div class="col-md-12 table-responsive">
            <table class="display" id="table-class">
                <thead>

                    <tr>
                        <th>ID</th>
                        <th>Clasificacion</th>
                        <th>Descripcion</th>
                        <th>Opcion</th>
                    </tr>
                </thead>
                <tbody>





                </tbody>
            </table>
        </div>



        <!--Modal for Create Clasificaciones-->
        <div id="modaltoNewClass" class="modal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Nuevo Registro</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">

                        <form action="${pageContext.servletContext.contextPath}/Clasificaciones?accion=nuevo" method="post">
                            <div class="form-group">
                                <label for="exampleInputEmail1">Nombre</label><br>
                                <span id="alertInput" class="text-danger"></span>
                                <input onkeyup="validarCaracteres('exampleInputEmail1', 50, 'btnSave', 'alertInput')"  name="classname" type="text" class="form-control" id="exampleInputEmail1" placeholder="Ingrese el nombre de la clasificacion">
                            </div>
                            <div class="form-group">
                                <label for="exampleFormControlTextarea1">Agrega una descripcion general</label><br>
                                <span id="alertArea" class="text-danger"></span>
                                <textarea onkeyup="validarCaracteres('textAreClass', 500, 'btnSave', 'alertArea')"  name="descripcion" class="form-control" id="textAreClass" rows="3"></textarea>
                            </div>

                            <button id="btnSave" type="submit" class="btn btn-primary float-right">Crear</button>
                        </form>

                    </div>
                </div>
            </div>
        </div>

        <!--Modal for Update Departamento-->
        <div id="modaltoUpdateClass" class="modal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Actualizar</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">

                        <form action="${pageContext.servletContext.contextPath}/Clasificaciones?accion=actualizar" method="post">
                            <input value="" type="hidden" id="Idclass" name="Idclass">
                            <div class="form-group">
                                <label for="exampleInputEmail1">Nombre</label><br>
                                <span id="alertInput2" class="text-danger"></span>
                                <input  name="classname" type="text" class="form-control" id="inputName" value="" >

                            </div>
                            <div class="form-group">
                                <label for="exampleFormControlTextarea1">Agrega una descripcion general</label>
                                <br>
                                <span id="alertArea2" class="text-danger"></span>
                                <textarea onkeyup="validarCaracteres('inputDescription', 500, 'btnSaveChanges', 'alertArea2')" name="descripcion" class="form-control" id="inputDescription" rows="3"></textarea>
                            </div>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                <button id="btnSaveChanges"  type="submit" class="btn btn-primary">Guardar Cambios</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>



        <!-- /.No quitar esto, copiar en todos los demas -->          
    </div>
    <!-- /.container-fluid -->
</section>

<%@include file="_endPanel.jsp" %>
<script src="js/clasificaciones.js"></script>
<script>
                                    $(document).ready(function () {
                                        $('#table-class').DataTable({
                                            ajax: {
                                                url: '${pageContext.servletContext.contextPath}/Clasificaciones?accion=getAll',
                                                dataSrc: ''
                                            },
                                            "createdRow": function (row, data, index) {

                                                // Add identity if it specified

                                                row.id = "id" + data.idClassification;

                                            },
                                            columns: [
                                                {data: 'idClassification'},
                                                {data: 'classification'},
                                                {data: 'description'},
                                                {
                                                    data: null,
                                                    render: function (data, type, row) {
                                                        // Combine the first and last names into a single table field
                                                        return `<button type="button" class="btn btn-info" onclick="updateClass('id` + data.idClassification + `')">Actualizar</button>
                                        `;
                                                    }

                                                }
                                            ],
                                            language:
                                                    {
                                                        "sProcessing": "Procesando...",
                                                        "sLengthMenu": "Mostrar _MENU_ registros",
                                                        "sZeroRecords": "No se encontraron resultados",
                                                        "sEmptyTable": "Ningún dato disponible en esta tabla =(",
                                                        "sInfo": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
                                                        "sInfoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
                                                        "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
                                                        "sInfoPostFix": "",
                                                        "sSearch": "Buscar:",
                                                        "sUrl": "",
                                                        "sInfoThousands": ",",
                                                        "sLoadingRecords": "Cargando...",
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