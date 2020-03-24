<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="_startPanel.jsp" %>
<script src="js/departamento.js"></script> 
<style>
    #modaltoAddingDeptoToEnterprise .modal-content
    {
        min-height:500px;
    }
</style>

<!-- Content Header (Page header) Esto dependera de cada pagina-->
<div class="content-header">
    <div class="container-fluid">
        <div class="col-12">
            <c:if test="${errorCharacters!=null}">
                <div class="alert alert-danger" role="alert">
                    Error al ${errorCharacters} el registro. Uno o ambos de los campos sobrepasan la longitud de caracteres
                </div>
            </c:if>
            <c:if test="${error!=null}">
                <div id="erroralert" class="alert alert-danger" role="alert">
                   ${error}
                </div>
            </c:if>
        </div>
        <div class="row mb-2">
            <div class="col-sm-6">

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

        <div class="card col-12">
            <div class="card-header">
                <h3 class="card-title">Lista de Empresas</h3>
            </div>
            <!-- /.card-header -->
            <div class="card-body">

                <!--Table-->
                <div class="col-md-12 table-responsive">
                    <table class="table table-striped table-bordered" id="table-depto">
                        <thead>

                            <tr>
                                <th>ID</th>
                                <th>Empresa</th>
                                <th>Direccion</th>
                                <th>Contador</th>
                                <th>Opcion</th>
                            </tr>
                        </thead>
                        <tbody>





                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!--Modal for New Empresa-->
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


                        <form id="CrearEmpresa" action="${pageContext.servletContext.contextPath}/Empresas?accion=nuevo" method="post">
                            <div class="form-group">
                                <label for="exampleInputEmail1">Nombre</label>
                                <br>
                                <span id="alertInput" class="text-danger"></span>
                                <input required onkeyup="validarCaracteres('exampleInputEmail1', 20, 'btnCreate', 'alertInput')" name="empresaname" type="text" class="form-control" id="exampleInputEmail1" placeholder="Ingrese el nombre de la empresa">
                            </div>
                            <div class="form-group">
                                <label for="exampleFormControlTextarea1">Agrega la direccion correspondiente</label>
                                <br>
                                <span id="alertArea" class="text-danger"></span>
                                <input required type="text" onkeyup="validarCaracteres('exampleFormControlTextarea1', 200, 'btnCreate', 'alertArea')" name="address" class="form-control" id="exampleFormControlTextarea1">
                                <br>
                            </div>

                            <button id="btnCreate" type="submit" class="btn btn-primary float-right">Crear</button>
                        </form>


                    </div>
                </div>
            </div>
        </div>



        <!--Modal for Update Empresa-->
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

                        <form action="${pageContext.servletContext.contextPath}/Empresas?accion=actualizar" method="post">
                            <input type="hidden" value="" id="IdEmpresa" name="IdEmpresa">
                            <div class="form-group">
                                <label for="exampleInputEmail1">Nombre</label>
                                <br>
                                <span class="text-danger" id="alertInput2"></span>
                                <input onkeyup="validarCaracteres('inputName', 20, 'btnSaveChanges', 'alertInput2')" name="empresaname" type="text" class="form-control" id="inputName" value="" >

                            </div>
                            <div class="form-group">
                                <label for="exampleFormControlTextarea1">Agrega una direccion</label>
                                <br>
                                <span class="text-danger" id="alertArea2"></span>
                                <textarea onkeyup="validarCaracteres('inputDescription', 200, 'btnSaveChanges', 'alertArea2')" name="address" class="form-control" id="inputDescription" rows="3"></textarea>
                            </div>
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <label class="input-group-text" for="inputGroupSelect01">Contador</label>
                                </div>
                                
                                <input type="hidden" value="" id="idcon" name="idconActual">
                                <select required class="custom-select" id="inputGroupSelect01" name="idcontador">
                                    <option selected value="0">Elegir...</option>
                                    <c:forEach var="Iterador" items="${ContadorList}">
                                        <option value="${Iterador.getIdUser()}">${Iterador.getFirsName()} ${Iterador.getLastName()}</option>
                                    </c:forEach>  
                                </select>
                            </div>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                <button id="btnSaveChanges" type="submit" class="btn btn-primary">Guardar Cambios</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!--Modal for Adding Contador to Empresa-->
        <div id="modaltoAddContador" class="modal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Agregar Contador</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">

                        <form id="formcontador" action="${pageContext.servletContext.contextPath}/Empresas?accion=addContador" method="post">
                            <input type="hidden" value="" id="IdEmpresas" name="IdEmpresa">
                            <div class="form-group">
                                <label for="exampleInputEmail1">Empresa</label>
                                <br>
                                <span class="text-danger" id="alertInput2"></span>
                                <input  name="empresaname" type="text" class="form-control" id="inputContador" value="" disabled>

                            </div>
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <label class="input-group-text" for="inputGroupSelect01">Contador</label>
                                </div>
                                <c:if test="${ContadorList != null}">
                                    <select required class="custom-select" id="inputGroupSelect011" name="idcontador">
                                        <option selected value="0">Elegir...</option>



                                        <c:forEach var="Iterador" items="${ContadorList}">
                                            <option value="${Iterador.getIdUser()}">${Iterador.getFirsName()} ${Iterador.getLastName()}</option>
                                        </c:forEach>  
                                    </select>
                                </c:if> 
                                <c:if test="${ContadorList == null}">
                                    <select required class="custom-select" id="inputGroupSelect01" name="idcontador">
                                        <option selected value="0">Aun no hay contadores</option>
                                    </select>
                                </c:if>
                            </div>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                <c:if test="${ContadorList != null}">
                                    <button id="btnnSaveChanges" type="submit" class="btn btn-primary">Guardar Cambios</button>
                                </c:if>
                                <c:if test="${ContadorList == null}">
                                    <button id="btnnSaveChanges" disabled id="btnSaveChanges"  class="btn btn-primary">Guardar Cambios</button>
                                </c:if>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!--Modal for adding Deptos to enterprise-->
        <div id="modaltoAddingDeptoToEnterprise" class="modal" tabindex="-1" role="dialog">
            <div class="modal-dialog modal-xl" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Agregar Departamentos</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">

                        <form>
                            <input type="hidden" value="" id="IdEmpresa" name="idempresa">
                            <div class="form-group">
                                <div class="row">
                                    <div class="col-md-6">
                                        <label for="exampleInputEmail1"><span id="nombreemp"></span></label>
                                    </div>
                                    <div class="col-md-6">
                                        <!-- SEARCH FORM -->
                                        <div class="form-group">
                                            <!-- SEARCH FORM -->
                                            <form action="#" class="form-inline ml-3">
                                                <div class="input-group input-group-sm">
                                                    <input id="searchDepto" onblur="displayNone()" class="form-control form-control-navbar" type="search" placeholder="Buscar departamento" aria-label="Search">
                                                    <div class="input-group-append">
                                                        <button class="btn btn-navbar" type="button">
                                                            <i class="fas fa-search"></i>
                                                        </button>
                                                    </div>
                                                </div>
                                            </form>
                                            <div class="row">
                                                <ul id="contentResult" class="list-group" style="position: absolute;width: 100%;display: none">
                                                    <li class="list-group-item d-flex justify-content-between align-items-center">
                                                        Cras justo odio
                                                        <a href="#">Agregar</a>
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <span class=" text-danger" id="alertDepto"></span>
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th scope="col">Id</th>
                                            <th scope="col">Departamento</th>
                                            <th scope="col">Opcion</th>
                                        </tr>
                                    </thead>
                                    <tbody id="bodyTable">

                                    </tbody>
                                </table>
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
    $(function () {
        $('[data-toggle="tooltip"]').tooltip()
    })

    var root = "${pageContext.servletContext.contextPath}";
    var btnSaveChanges = document.getElementById("btnnSaveChanges");
    var searchDepto = document.getElementById("searchDepto");
    var html = document.getElementById("contentResult");
    var bodyTable = document.getElementById("bodyTable");
    var idEmpresa = document.getElementById("IdEmpresa");

    btnSaveChanges.addEventListener('click', function (e) {
        e.preventDefault();
        if (document.getElementById("inputGroupSelect011").value == 0) {
            alert("Debe asociar un contador");
        } else {
            document.getElementById("formcontador").submit();
        }
    });

    function displayNone() {
        //html.style.display = "none";
        document.getElementById("alertDepto").innerHTML = "";
    }
    function deleteNode(iddep, idemp) {
        fetch("${pageContext.servletContext.contextPath}/Empresas?accion=deleteDeptoInEmpresa&idemp=" + idemp + "&iddep=" + iddep)
                .then((response) => response.text())
                .then((responseText) => {
                    console.log(responseText);
                    if (responseText == "false") {
                        document.getElementById("alertDepto").innerHTML = "Error, no se pudo hacer la peticion";
                    } else if (responseText == "true") {
                        fetchTable('${pageContext.servletContext.contextPath}', idemp);
                    } else {
                        document.getElementById("alertDepto").innerHTML = "Error, este departamento tiene requisiciones";
                    }


                })
                .catch((error) => {
                    
                    console.log(error);
                });
    }
    function Addtable(iddep) {

        fetch("${pageContext.servletContext.contextPath}/Empresas?accion=existDepto&idemp=" + idEmpresa.value + "&iddep=" + iddep)
                .then((response) => response.text())
                .then((responseText) => {

                    if (responseText == "true") {
                        document.getElementById("alertDepto").innerHTML = "Este departamento \n\
                        ya esta registrado para esta empresa";
                    } else {
                        fetch("${pageContext.servletContext.contextPath}/Empresas?accion=insertDeptoToEmpresa&idemp=" + idEmpresa.value + "&iddep=" + iddep)
                                .then((response) => response.text())
                                .then((responseText) => {

                                    if (responseText == "true") {
                                        fetchTable('${pageContext.servletContext.contextPath}', idEmpresa.value);
                                    } else {
                                        document.getElementById("alertDepto").innerHTML = "Hubo un problema al agregar";
                                    }

                                })
                                .catch((error) => {
                                    
                                    location.href = root;
                                    console.log(error);
                                });
                    }


                })
                .catch((error) => {
                    location.href = root;
                    console.log(error);
                });

        html.style.display = "none";
        searchDepto.value = "";
    }
    function showResults(items) {

        html.innerHTML = "";
        for (i = 0; i < items.length; i++) {
            html.innerHTML += `
            <li class="list-group-item d-flex justify-content-between align-items-center">
            ` + items[i].deptoName + `
            <a href="#"  onclick="Addtable(` + items[i].idDepto + `)">Agregar</a>
            </li>
    
            `;
        }

        html.style.display = "block";

    }

    searchDepto.addEventListener('keyup', function (e) {
        document.getElementById("alertDepto").innerHTML = "";
        var name = e.target.value;
        console.log(name);
        if (this.value.length == 0) {
            html.style.display = "none";
        } else {
            fetch("${pageContext.servletContext.contextPath}/Departamentos?accion=getAll")
                    .then((response) => response.text())
                    .then((responseText) => {
                        var matches = [];
                        var data = JSON.parse(responseText);
                        console.log(data);
                        for (i = 0; i < data.length; i++) {
                            let deptoname = data[i].deptoName;
                            if (deptoname.toLowerCase().indexOf(name.toLowerCase()) != -1) {
                                matches.push(data[i]);
                            }
                        }
                        showResults(matches);
                    })
                    .catch((error) => {
                        location.href = root;
                        console.log(error);
                    });
        }


    });
</script>
<script>
    $(document).ready(function () {
        $('#table-depto').DataTable({
            responsive: true,
            ajax: {
                url: '${pageContext.servletContext.contextPath}/Empresas?accion=getAll',
                dataSrc: ''
            },
            "createdRow": function (row, data, index) {

                // Add identity if it specified

                row.id = "id" + data.emp.idEmpresa;

            },
            columns: [
                {data: 'emp.idEmpresa'},
                {data: 'emp.Nombre'},
                {data: 'emp.Direccion'},
                {
                    data: null,
                    render: function (data, type, row) {
                        // Combine the first and last names into a single table field
                        return (data.contador.firstName == "null") ? "No Asignado" : data.contador.firstName + " " + data.contador.lastName;
                    }

                },
                {
                    data: null,
                    render: function (data, type, row) {
                        // Combine the first and last names into a single table field
                        return (data.contador.firstName == "null") ? `
                                <button data-toggle="tooltip" data-placement="top" title="Actualizar"  type="button" class="btn btn-info" onclick="updateEmpresa('id` + data.emp.idEmpresa + `', `+data.contador.idUser+`)"><i class="fas fa-pen-square"></i></button>
                                <button data-toggle="tooltip" data-placement="top" title="Agregar Departamento" type="button" class="btn btn-info" onclick="addDeptoToEnterprise('${pageContext.servletContext.contextPath}','id` + data.emp.idEmpresa + `')"><i class="fas fa-plus-square"></i></button>
                                <button data-toggle="tooltip" data-placement="top" title="Asociar Contador" type="button" class="btn btn-info" onclick="addContador('id` + data.emp.idEmpresa + `')"><i class="fas fa-user-plus"></i></button>
                        ` : `
                                <button data-toggle="tooltip" data-placement="top" title="Actualizar"  type="button" class="btn btn-info" onclick="updateEmpresa('id` + data.emp.idEmpresa + `', `+data.contador.idUser+`)"><i class="fas fa-pen-square"></i></button>
                                <button data-toggle="tooltip" data-placement="top" title="Agregar Departamento" type="button" class="btn btn-info" onclick="addDeptoToEnterprise('${pageContext.servletContext.contextPath}','id` + data.emp.idEmpresa + `')"><i class="fas fa-plus-square"></i></button>
                                <button disabled data-toggle="tooltip" data-placement="top" title="Contador Asignado" type="button" class="btn btn-secondary"><i class="fas fa-user-plus"></i></button>
                        `;
                    }

                }
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
                            "sLast": "\DAltimo",
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
