<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="_startPanel.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- Content Header (Page header) Esto dependera de cada pagina-->
<div class="content-header">
    <div class="container-fluid">
        <div class="row mb-2">
            <div class="col-sm-6">
                <h1 class="m-0 text-dark">Crear Incidencia</h1>
                <c:if test="${resultado!=null}">
                    <c:if test="${resultado==1}">
                        <p class="text-danger m-0" ><strong>Problemas al registrar!</strong></p>
                    </c:if>
                    <c:if test="${resultado==2}">
                        <p class="text-success m-0"><strong>Se ha registro con exito</strong></p>
                    </c:if>
                </c:if>                
            </div><!-- /.col -->
            <div class="col-sm-6">
                <ol class="breadcrumb float-sm-right">
                    <li class="p-2 border border-success" style="border-radius: 5px 0px 5px 0px;"><a href="${pageContext.servletContext.contextPath}/PrincipalRequisicion">Volver Inicio</a></li>
                </ol>
            </div><!-- /.col -->
        </div><!-- /.row -->
    </div><!-- /.container-fluid -->
</div>
<!-- /.content-header -->


<!-- Main content Esto debe ir en todas-->
<section class="content">
    <div class="container-fluid">
        <!-- Comienda el contenido principal -->




        <div class="row">
            <div class="col-md-6">
                <div class="card card-primary">
                    <div class="card-header">
                        <h3 class="card-title">Datos Generales</h3>
                    </div>
                    <!-- /.card-header -->
                    <!-- form start -->
                    <form role="form" action="${pageContext.servletContext.contextPath}/Incidencias?accion=nueva" method="POST" onsubmit="return validar();" >
                        <div class="card-body">
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text"><i class="fas fa-sticky-note"></i></span>
                                </div>
                                <input type="hidden" value="${ie.idIncidence}" name="txtIdIncidencia"/>
                                <input type="text" class="form-control" placeholder="Titulo" name="txtTitle" required="true" maxlength="50" value="${ie.title}" id="txtTitle">
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <!-- select -->
                                    <div class="form-group">
                                        <label>Clasificacion</label>
                                        <select class="form-control" name="slcClasificacion" id="slcClasificacion">
                                            <c:forEach var="Iterador" items="${ClasfList}">
                                                <option value="${Iterador.idClassification}">${Iterador.classification}</option>
                                            </c:forEach>                                                
                                        </select>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label>Prioridad</label>
                                        <select class="form-control" name="slcPrioridad" id="slcPrioridad">
                                            <option value="1">BAJA</option>
                                            <option value="2">MEDIA</option>
                                            <option value="3">ALTA</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <c:if test="${Rol!= null && Rol==1}">
                                <div class="form-group row">
                                    <label>Departamento</label>
                                    <select  class="form-control" name="slcDeptoIncidence" id="slcDeptoIncidence">
                                        <c:forEach var="idp" items="${DeptosList}">
                                            <option value="${idp.idDepto}">${idp.deptoName}</option>
                                        </c:forEach>
                                    </select>                                    
                                </div>                                                            
                            </c:if>

                            <div class="form-group row">
                                <label>Descripcion</label>
                                <textarea class="form-control" rows="3" placeholder="Enter ..." required="true" name="txtDescripcion" maxlength="500" id="txtDescripcion">${ie.description}</textarea>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="exampleInputEmail1"><a href="">Asignar a:</a></label>
                                        <input type="hidden" value="" name="txtReceptor" id="idReceptor">
                                        <input type="text" class="form-control" id="Receptor" placeholder="Ninguno Selecionado" disabled="">
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="exampleInputEmail1">Fecha Final</label><br>
                                        <div id="datepicker-group" class="input-group date" data-date-format="dd-mm-yyyy">
                                            <input class="form-control" name="auxDate" type="text" placeholder="DD/MM/YYYY" id="auxDate"/>
                                            <input type="hidden" value="" id="dateFechaFinal" name="dateFechaFinal">
                                            <span class="input-group-addon"></span>
                                        </div>                                                                            
                                    </div>                                
                                </div>
                            </div>                                                
                        </div>
                        <!-- /.card-body -->

                        <div class="card-footer">
                            <button type="submit" class="btn btn-primary">Aceptar</button>
                            <a class="btn btn-secondary" href="${pageContext.servletContext.contextPath}/Principal">Cancelar</a>
                        </div>
                    </form>
                </div>

            </div>
            <div class="col-md-6">

                <div class="card card-secondary">
                    <div class="card-header">
                        <h3 class="card-title">Tecnico/Receptor</h3>
                    </div>
                    <!-- /.card-header -->
                    <div class="card-body">
                        <div class="row">
                            <div class="col-sm-6">
                                <c:choose>
                                    <c:when test="${Rol!= null && Rol==2}">
                                        <div class="form-group">
                                            <div class="custom-control custom-radio">
                                                <input type="hidden" value="${idDepUser}" id="idDepDef">
                                                <input  class="custom-control-input" id="radio1" type="radio"  name="radioDepto" >
                                                <label for="radio1" class="custom-control-label">Depto a cargo</label>
                                            </div>
                                            <div class="custom-control custom-radio">
                                                <input value="" class="custom-control-input" id="radio2" type="radio"  name="radioDepto" >
                                                <label for="radio2" class="custom-control-label">Otros Deptos</label>
                                            </div>
                                        </div>                                                                    
                                    </c:when>
                                    <c:when test="${Rol!=null && Rol==1}">
                                        <div class="form-group">
                                            <label>Filtrar por Roles</label>
                                            <select id="fRol" class="custom-select">
                                                <option value="1">Gerente</option>
                                                <option value="2">Lider</option>
                                                <option value="3">Receptor</option>
                                            </select>
                                        </div>                                    
                                    </c:when>
                                </c:choose>
                            </div>
                            <div class="col-sm-6" id="deptoList">
                                <div class="form-group">
                                    <label>Filtrar por Departamentos</label>
                                    <select id="fDepto" class="custom-select">
                                        <c:forEach var="Iterador" items="${DeptosList}">
                                            <option value="${Iterador.idDepto}">${Iterador.deptoName}</option>
                                        </c:forEach>                                        
                                    </select>
                                </div>                                
                            </div>
                        </div>

                    </div>
                    <!-- /.card-body -->
                </div> 

                <div class="row">
                    <!-- Table row -->
                    <div class="col-md-12" id="nofound"></div>

                    <div class="card col-12">
                        <div class="card-header">
                            <h3 class="card-title">Lista de Empleados</h3>
                        </div>
                        <!-- /.card-header -->
                        <div class="card-body">

                            <div class="col-md-12 table-responsive">
                                <table class="table table-striped table-bordered" id="table_emp">
                                    <thead>

                                        <tr>
                                            <th>Nombres</th>
                                            <th>Apellidos</th>
                                            <th>Email</th>
                                            <th><i class="fas fa-check-square"></i></th>
                                        </tr>
                                    </thead>
                                    <input id="path" type="hidden" value="${pageContext.servletContext.contextPath}">
                                    <tbody>








                                    </tbody>
                                </table>
                            </div>
                        </div>
                        </div>
                    </div>

                    </div>
                </div>



            <script>

                function formatear(inv) {
                    let dia = inv[0] + inv[1];
                    let mes = inv[3] + inv[4];
                    let anio = inv[6] + inv[7] + inv[8] + inv[9];
                    return anio + "-" + mes + "-" + dia;
                }

                var titulo, clasificacion, prioridad, descripcion, tecnico, finalDate, auxDate;
                titulo = document.getElementById('txtTitle');
                clasificacion = document.getElementById('slcClasificacion');
                prioridad = document.getElementById('slcPrioridad');
                descripcion = document.getElementById('txtDescripcion');
                depto = document.getElementById('slcDeptoIncidence');
                tecnico = document.getElementById('idReceptor');
                fechaFinal = document.getElementById('dateFechaFinal');
                hiddenReceptorId = document.getElementById('idReceptor');
                auxDate = document.getElementById('auxDate');



                // metodo para validar 
                function validar() {
                    if (hiddenReceptorId.value.length == 0) {
                        alert("Debe seleccionar un Tecnico");
                        return false;
                    } else if (clasificacion.value.length == 0) {
                        alert("Debe registrar al menos una Clasificacion");
                        return false;
                    }

                    if (auxDate.value.length > 0) {
                        fechaFinal.value = formatear(auxDate.value);
                    }

                    return true;
                }

                var accion = "${accionProcess}";
                clId = 1;
                if (accion == 'update') {
                    titulo.disabled = true;
                    clasificacion.disabled = true;
                    clasificacion.value = "${ie.idClassification}";
                    prioridad.disabled = true;
                    prioridad.value = "${ie.priority}";
                    descripcion.disabled = true;
                    if (depto != null) {
                        depto.disabled = true;
                        depto.value = "${ie.idDepto}";
                    }
                    fechaFinal.disabled = true;
                    fechaFinal.value = "<fmt:formatDate value="${ie.finalDate}" pattern="yyyy-MM-dd"/>";
                }

            </script>

            <%@include file="_endPanel.jsp" %>
            <script src="js/filtrarPorRol&Depto.js"></script>
            <script>
                $('#table_emp').DataTable({
                    responsive: true,
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

            </script>

            <script type="text/javascript">
                $(document).ready(function () {
                    $("#datepicker-group").datepicker({
                        format: "dd/mm/yyyy",
                        todayHighlight: true,
                        autoclose: true,
                        clearBtn: true
                    });
                });

            </script>