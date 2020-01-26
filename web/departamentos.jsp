<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="_startPanel.jsp" %>
<script src="js/departamento.js"></script> 

<!-- Content Header (Page header) Esto dependera de cada pagina-->
<div class="content-header">
    <div class="container-fluid">
        <div class="row mb-2">
            <div class="col-sm-6">
               <h1 class="m-0 text-dark">Lista de Departamentos</h1>              
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




<!-- Main content -->
<section class="content">
    <div class="container-fluid">

        <!--Table-->
        <div class="col-md-12 table-responsive">
            <table class="display" id="table-depto">
                <thead>

                    <tr>
                        <th>ID</th>
                        <th>Departamento</th>
                        <th>Descripcion</th>
                        <th>Opcion</th>
                    </tr>
                </thead>
                <tbody>

                    



                </tbody>
            </table>
        </div>

        <!--Modal for New Departamento-->
        <div id="modaltoNewDepto" class="modal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Nuevo Registro</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <!--Formulario Nuevo-->

                        
                                <form action="${pageContext.servletContext.contextPath}/Departamentos?accion=nuevo" method="post">
                                    <div class="form-group">
                                        <label for="exampleInputEmail1">Nombre</label>
                                        <input name="deptoname" type="text" class="form-control" id="exampleInputEmail1" placeholder="Ingrese el nombre del departamento">
                                    </div>
                                    <div class="form-group">
                                        <label for="exampleFormControlTextarea1">Agrega una descripcion general</label>
                                        <textarea name="descripcion" class="form-control" id="exampleFormControlTextarea1" rows="3"></textarea>
                                    </div>

                                    <button type="submit" class="btn btn-primary float-right">Crear</button>
                                </form>
                           

                    </div>
                </div>
            </div>
        </div>



        <!--Modal for Update Departamento-->
        <div id="modaltoUpdateDepto" class="modal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Actualizar</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">

                        <form action="${pageContext.servletContext.contextPath}/Departamentos?accion=actualizar" method="post">
                            <input type="hidden" value="" id="IdDepto" name="iddepto">
                            <div class="form-group">
                                <label for="exampleInputEmail1">Nombre</label>
                                <input name="deptoname" type="text" class="form-control" id="inputName" value="" >

                            </div>
                            <div class="form-group">
                                <label for="exampleFormControlTextarea1">Agrega una descripcion general</label>
                                <textarea name="descripcion" class="form-control" id="inputDescription" rows="3"></textarea>
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
        <!-- /.No quitar esto, copiar en todos los demas -->          
    </div>
    <!-- /.container-fluid -->
</section>
        <%@include file="_endPanel.jsp" %>
<script>
    $(document).ready(function () {
        $('#table-depto').DataTable({
            ajax: {
                url: '${pageContext.servletContext.contextPath}/Departamentos?accion=getAll',
                dataSrc: ''
            },
            "createdRow": function (row, data, index) {

                // Add identity if it specified
                
                    row.id = "id" + data.idDepto;
                
            },
            columns: [
                {data: 'idDepto'},
                {data: 'deptoName'},
                {data: 'description'},
                {
                    data: null,
                    render: function (data, type, row) {
                        // Combine the first and last names into a single table field
                        return `<button type="button" class="btn btn-info" onclick="updateDepto('id`+data.idDepto+`')">Actualizar</button>
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