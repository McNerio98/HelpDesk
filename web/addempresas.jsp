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
        <div class="col-9">
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
            <div class="col-sm-9">
                <div class="alert alert-warning" role="alert">
                    Cada solicitud de requisicion que hagas para la empresa que agregues,
                    sera revisada y aprobada por el lider de esa empresa.
                </div>
            </div><!-- /.col -->
            <div class="col-sm-3">
                <ol class="breadcrumb float-sm-right">
                    <button onclick="newForm()" type="button" class="btn btn-primary">Agregar Empresa</button>
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
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="list" items="${myListEmps}">
                                <tr>
                                    <td>${list.getIdEmpresa()}</td>
                                    <td>${list.getNombre()}</td>
                                    <td>${list.getDireccion()}</td>
                                </tr>
                            </c:forEach>




                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!--Modal for Adding Empresa-->
        <div id="modaltoNewDepto" class="modal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Agregar Empresa</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <!--Formulario Nuevo-->


                        <form action="${pageContext.servletContext.contextPath}/PrincipalRequisicion?accion=addmyemp" method="post">
                            
                            <div class="form-group col-md-12" id="pnlSelectEmpresa">
                                <label for="exampleFormControlSelect1">Selecccionar Empresa</label>
                                <select class="form-control" id="empresa" name="id">
                                    <c:forEach var="Iterador" items="${EmpresasList}">
                                        <option value="${Iterador.getIdEmpresa()}">${Iterador.getNombre()}</option>
                                    </c:forEach> 
                                </select>
                            </div> <br><br>  
                            <button id="btnCreate" type="submit" class="btn btn-primary float-right m-auto">Aceptar</button>
                            <button type="button" class="btn btn-secondary float-right" data-dismiss="modal">Cerrar</button>
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


</script>
<script>
    $(document).ready(function () {
        $('#table-depto').DataTable();

    });

</script>
