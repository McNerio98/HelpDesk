<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="_startPanel.jsp" %>
<script src="./js/momentjs.min.js"></script>


<!-- Content Header (Page header) Esto dependera de cada pagina-->
<div class="content-header">
    <div class="container-fluid">
        <div class="row mb-2">
            <div class="col-sm-6">
                <c:if test="${selected != null}">
                    <h4 class="m-0 text-dark">${selected}</h4>
                </c:if>
                <c:if test="${selected == null}">
                    <h1 class="m-0 text-dark">Dashboard</h1>
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


<!-- Main content Esto debe ir en todas-->
<section class="content">
    <div class="container-fluid">
        <!-- Comienda el contenido principal -->
        <c:if test="${typeSession == 'REQ'}"> 



            <c:if test="${Rol == 6}">

                <div class="col-lg-12 col-md-12 col-sm-12">
                    <c:if test="${listRequisiciones != null}">
                        <div class="col-lg-3 col-md-3  col-6 float-left">
                            <!-- small box -->
                            <div class="small-box bg-info">
                                <div class="inner">
                                    <h3>${fn:length(listRequisiciones)}</h3>

                                    <p>Todas</p>
                                </div>
                                <div class="icon">
                                    <i class="fas fa-bell"></i>
                                </div>
                                <a data-toggle="tooltip" data-placement="top" title="Se muestra todas las requisiciones de la empresa, por sus diferentes estados" onclick="getRequisiciones(1)"  href="#Requisiciones" class="small-box-footer">Ver <i class="fas fa-arrow-circle-right"></i></a>
                            </div>
                        </div>
                        <!-- ./col -->        
                    </c:if>
                    <c:if test="${listRequisiciones == null}">
                        <div class="col-lg-3  col-md-3  col-6 float-left">
                            <!-- small box -->
                            <div class="small-box bg-info">
                                <div class="inner">
                                    <h3>0</h3>

                                    <p>Todas</p>
                                </div>
                                <div class="icon">
                                    <i class="fas fa-bell"></i>
                                </div>
                                <a data-toggle="tooltip" data-placement="top" title="Se muestra todas las requisiciones de la empresa, por sus diferentes estados" onclick="getRequisiciones(1)"   href="#Requisiciones" class="small-box-footer">Ver <i class="fas fa-arrow-circle-right"></i></a>
                            </div>
                        </div>
                        <!-- ./col -->        
                    </c:if>
                    <c:if test="${processDiv != null}">
                        <div class="col-lg-3  col-md-3  col-6 float-left">
                            <!-- small box -->
                            <div class="small-box bg-success">
                                <div class="inner">
                                    <h3>${fn:length(processDiv)}</h3>

                                    <p>En revision</p>
                                </div>
                                <div class="icon">
                                    <i class="fas fa-cogs"></i>
                                </div>
                                <a data-toggle="tooltip" data-placement="top" title="Se muestra todas las requisiciones de la empresa en estado revision tomadas por los lideres" onclick="getRequisiciones(2)"  href="#Requisiciones" class="small-box-footer">Ver <i class="fas fa-arrow-circle-right"></i></a>
                            </div>
                        </div>
                        <!-- ./col -->        
                    </c:if>
                    <c:if test="${processDiv == null}">
                        <div class="col-lg-3  col-md-3  col-6 float-left">
                            <!-- small box -->
                            <div class="small-box bg-success">
                                <div class="inner">
                                    <h3>0</h3>

                                    <p>En revision</p>
                                </div>
                                <div class="icon">
                                    <i class="fas fa-cogs"></i>
                                </div>
                                <a data-toggle="tooltip" data-placement="top" title="Se muestra todas las requisiciones de la empresa en estado revision tomadas por los lideres" onclick="getRequisiciones(2)"  href="#Requisiciones" class="small-box-footer">Ver <i class="fas fa-arrow-circle-right"></i></a>
                            </div>
                        </div>
                        <!-- ./col -->        
                    </c:if>
                    <c:if test="${pendingDiv != null}">
                        <div class="col-lg-3  col-md-3  col-6 float-left">
                            <!-- small box -->
                            <div class="small-box bg-light">
                                <div class="inner">
                                    <h3>${fn:length(pendingDiv)}</h3>

                                    <p>Aceptadas</p>
                                </div>
                                <div class="icon">
                                    <i class="fas fa-toolbox"></i>
                                </div>
                                <a data-toggle="tooltip" data-placement="top" title="Se muestra todas las requisiciones de la empresa en estado aceptada tomadas por los lideres" onclick="getRequisiciones(3)"  href="#Requisiciones" class="small-box-footer">Ver <i class="fas fa-arrow-circle-right"></i></a>
                            </div>
                        </div>
                        <!-- ./col -->        
                    </c:if>
                    <c:if test="${pendingDiv == null}">
                        <div class="col-lg-3  col-md-3  col-6 float-left">
                            <!-- small box -->
                            <div class="small-box bg-light">
                                <div class="inner">
                                    <h3>0</h3>

                                    <p>Aceptadas</p>
                                </div>
                                <div class="icon">
                                    <i class="fas fa-toolbox"></i>
                                </div>
                                <a data-toggle="tooltip" data-placement="top" title="Se muestra todas las requisiciones de la empresa en estado revision tomadas por los lideres" onclick="getRequisiciones(3)"    href="#Requisiciones" class="small-box-footer">Ver <i class="fas fa-arrow-circle-right"></i></a>
                            </div>
                        </div>
                        <!-- ./col -->        
                    </c:if>
                    <c:if test="${finishDiv != null}">
                        <div class="col-lg-3  col-md-3  col-6 float-left">
                            <!-- small box -->
                            <div class="small-box bg-gray">
                                <div class="inner">
                                    <h3>${fn:length(finishDiv)}</h3>

                                    <p>Finalizadas</p>
                                </div>
                                <div class="icon">
                                    <i class="fas fa-check-double"></i>
                                </div>
                                <a data-toggle="tooltip" data-placement="top" title="Se muestra todas las requisiciones de la empresa en estado finalizada tomadas por los lideres" onclick="getRequisiciones(4)" href="#Requisiciones" class="small-box-footer">Ver <i class="fas fa-arrow-circle-right"></i></a>
                            </div>
                        </div>
                        <!-- ./col -->        
                    </c:if>
                    <c:if test="${finishDiv == null}">
                        <div class="col-lg-3  col-md-3  col-6 float-left">
                            <!-- small box -->
                            <div class="small-box bg-gray">
                                <div class="inner">
                                    <h3>0</h3>

                                    <p>Finalizadas</p>
                                </div>
                                <div class="icon">
                                    <i class="fas fa-check-double"></i>
                                </div>
                                <a data-toggle="tooltip" data-placement="top" title="Se muestra todas las requisiciones de la empresa en estado finalizada tomadas por los lideres" onclick="getRequisiciones(4)"   href="#Requisiciones" class="small-box-footer">Ver <i class="fas fa-arrow-circle-right"></i></a>
                            </div>
                        </div>
                        <!-- ./col -->        
                    </c:if>


                </div>



                <div class="col-lg-12 col-md-12 col-sm-12">
                    <div class="card col-12">
                        <input type="hidden" id="path" value="${pageContext.servletContext.contextPath}">
                        <!-- /.card-header -->
                        <div class="card-body">
                            <div class="col-md-12 table-responsive">
                                <table class="table table-striped table-bordered" id="table-requisicion">
                                    <thead>

                                        <tr>
                                            <th>Requisiciones</th>
                                        </tr>
                                    </thead>
                                    <tbody id="Requisiciones">
                                        <c:forEach var="list" items="${listRequisiciones}">
                                            <tr>
                                                <td>
                                                    <a href="${pageContext.servletContext.contextPath}/RequisicionInfo?idReq=${list.getId()}" class="list-group-item list-group-item-action">
                                                        <div class="d-flex w-100 justify-content-between">
                                                            <p style="font-size: 15px;font-weight:bold" class="mb-1">
                                                                <small style="font-size: 12px;font-weight:normal">Solicitante: </small>${list.getSolicitante()}
                                                            </p>
                                                            <small>Para el <script> document.write(moment('${list.getFechaEstimada()}', "DD-MM-YYYYHH:mm").format("LL"))</script></small>
                                                        </div>

                                                        <p class="mb-1 text-muted">
                                                            <i>
                                                                <small style="font-size: 12px;font-weight:normal">A nombre de: ${list.getaNombre()}</small>
                                                                <i>
                                                                    <span class="text-wrap badge badge-primary float-right">
                                                                        $ ${list.getMontoTotal()}</p>
                                                                    </span>

                                                                    <p class="mb-1">${list.getEmpresa()} - ${list.getDepto()}</p>
                                                                    </a>
                                                                    </td>
                                                                    </tr>
                                                                </c:forEach>
                                                                </tbody>
                                                                </table>
                                                                </div>
                                                                </div>
                                                                </div>
                                                                </div>  
                                                            </c:if> 



                                                        </c:if>    





                                                        <!-- /.No quitar esto, copiar en todos los demas -->          
                                                        </div>
                                                        <!-- /.container-fluid -->
                                                        </section>
                                                        <!-- /.content -->



                                                        <%@include file="_endPanel.jsp" %>
                                                        <script src="js/principalRequisicion.js"></script>



                                                        <script>
                                                                $(function () {
                                                                    $('[data-toggle="tooltip"]').tooltip()
                                                                })
                                                        </script>
                                                        <script>

                                                            $('#table-requisicion').DataTable({
                                                                responsive: true,
                                                                language:
                                                                        {
                                                                            "sProcessing": "Procesando...",
                                                                            "sLengthMenu": "Mostrar _MENU_ registros",
                                                                            "sZeroRecords": "No se encontraron resultados",
                                                                            "sEmptyTable": "Ningun dato disponible en esta tabla",
                                                                            "sInfo": "_START_ al _END_ de un total de _TOTAL_ registros",
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

