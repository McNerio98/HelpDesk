<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="_startPanel.jsp" %>



<!-- Content Header (Page header) Esto dependera de cada pagina-->
<div class="content-header">
    <div class="container-fluid">
        <div class="row mb-2">
            <div class="col-sm-6">
                <h1 class="m-0 text-dark">Dashboard</h1>
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



        <div class="row">
            <div class="col-6 col-lg-6">
                <c:if test="${todasDiv != null}">
                    <div class="col-lg-4 col-sm-12 col-md-6 col-6 float-left">
                        <!-- small box -->
                        <div class="small-box bg-info">
                            <div class="inner">
                                <h3>${fn:length(todasDiv)}</h3>

                                <p>Todas</p>
                            </div>
                            <div class="icon">
                                <i class="far fa-file-alt"></i>
                            </div>
                            <a onclick="getIncidences(1)" href="#" class="small-box-footer">Ver <i class="fas fa-arrow-circle-right"></i></a>
                        </div>
                    </div>
                    <!-- ./col -->        
                </c:if>
                <c:if test="${todasDiv == null}">
                    <div class="col-lg-4 col-sm-12 col-md-6  col-6 float-left">
                        <!-- small box -->
                        <div class="small-box bg-info">
                            <div class="inner">
                                <h3>0</h3>

                                <p>Todas</p>
                            </div>
                            <div class="icon">
                                <i class="far fa-file-alt"></i>
                            </div>
                            <a  href="#" class="small-box-footer">Ver <i class="fas fa-arrow-circle-right"></i></a>
                        </div>
                    </div>
                    <!-- ./col -->        
                </c:if>
                <c:if test="${processDiv != null}">
                    <div class="col-lg-4 col-sm-12 col-md-6  col-6 float-left">
                        <!-- small box -->
                        <div class="small-box bg-success">
                            <div class="inner">
                                <h3>${fn:length(processDiv)}</h3>

                                <p>En proceso</p>
                            </div>
                            <div class="icon">
                                <i class="fas fa-cogs"></i>
                            </div>
                            <a onclick="getIncidences(2)" href="#" class="small-box-footer">Ver <i class="fas fa-arrow-circle-right"></i></a>
                        </div>
                    </div>
                    <!-- ./col -->        
                </c:if>
                <c:if test="${processDiv == null}">
                    <div class="col-lg-4 col-sm-12 col-md-6  col-6 float-left">
                        <!-- small box -->
                        <div class="small-box bg-success">
                            <div class="inner">
                                <h3>0</h3>

                                <p>En proceso</p>
                            </div>
                            <div class="icon">
                                <i class="fas fa-cogs"></i>
                            </div>
                            <a  href="#" class="small-box-footer">Ver <i class="fas fa-arrow-circle-right"></i></a>
                        </div>
                    </div>
                    <!-- ./col -->        
                </c:if>
                <c:if test="${pendingDiv != null}">
                    <div class="col-lg-4 col-sm-12 col-md-6  col-6 float-left">
                        <!-- small box -->
                        <div class="small-box bg-light">
                            <div class="inner">
                                <h3>${fn:length(pendingDiv)}</h3>

                                <p>Pendientes</p>
                            </div>
                            <div class="icon">
                                <i class="fas fa-toolbox"></i>
                            </div>
                            <a onclick="getIncidences(3)" href="#" class="small-box-footer">Ver <i class="fas fa-arrow-circle-right"></i></a>
                        </div>
                    </div>
                    <!-- ./col -->        
                </c:if>
                <c:if test="${pendingDiv == null}">
                    <div class="col-lg-4 col-sm-12 col-md-6  col-6 float-left">
                        <!-- small box -->
                        <div class="small-box bg-light">
                            <div class="inner">
                                <h3>0</h3>

                                <p>Pendientes</p>
                            </div>
                            <div class="icon">
                                <i class="fas fa-toolbox"></i>
                            </div>
                            <a onclick="#"   href="#" class="small-box-footer">Ver <i class="fas fa-arrow-circle-right"></i></a>
                        </div>
                    </div>
                    <!-- ./col -->        
                </c:if>

                <c:if test="${idRol != 3}">
                    <c:if test="${requestDiv != null}">
                        <div class="col-lg-4 col-sm-12 col-md-6  col-6 float-left">
                            <!-- small box -->
                            <div class="small-box bg-warning">
                                <div class="inner">
                                    <h3>${fn:length(requestDiv)}</h3>

                                    <p>En solicitud</p>
                                </div>
                                <div class="icon">
                                    <i class="fas fa-bell"></i>
                                </div>
                                <a onclick="getIncidences(5)" href="#" class="small-box-footer">Ver <i class="fas fa-arrow-circle-right"></i></a>
                            </div>
                        </div>
                        <!-- ./col -->        
                    </c:if>
                    <c:if test="${requestDiv == null}">
                        <div class="col-lg-4 col-sm-12 col-md-6  col-6 float-left">
                            <!-- small box -->
                            <div class="small-box bg-warning">
                                <div class="inner">
                                    <h3>0</h3>

                                    <p>En solicitud</p>
                                </div>
                                <div class="icon">
                                    <i class="fas fa-bell"></i>
                                </div>
                                <a  href="#" class="small-box-footer">Ver <i class="fas fa-arrow-circle-right"></i></a>
                            </div>
                        </div>
                        <!-- ./col -->        
                    </c:if>
                </c:if>

                <c:if test="${refuseDiv != null}">
                    <div class="col-lg-4 col-sm-12 col-md-6  col-6 float-left">
                        <!-- small box -->
                        <div class="small-box bg-danger">
                            <div class="inner">
                                <h3>${fn:length(refuseDiv)}</h3>

                                <p>Rechazadas</p>
                            </div>
                            <div class="icon">
                                <i class="fas fa-exclamation-triangle"></i>
                            </div>
                            <a onclick="getIncidences(6)" href="#" class="small-box-footer">Ver <i class="fas fa-arrow-circle-right"></i></a>
                        </div>
                    </div>
                    <!-- ./col -->        
                </c:if>
                <c:if test="${refuseDiv == null}">
                    <div class="col-lg-4 col-sm-12 col-md-6  col-6 float-left">
                        <!-- small box -->
                        <div class="small-box bg-danger">
                            <div class="inner">
                                <h3>0</h3>

                                <p>Rechazadas</p>
                            </div>
                            <div class="icon">
                                <i class="fas fa-exclamation-triangle"></i>
                            </div>
                            <a  href="#" class="small-box-footer">Ver <i class="fas fa-arrow-circle-right"></i></a>
                        </div>
                    </div>
                    <!-- ./col -->        
                </c:if>
                <c:if test="${finishDiv != null}">
                    <div class="col-lg-4 col-sm-12 col-md-6  col-6 float-left">
                        <!-- small box -->
                        <div class="small-box bg-gray">
                            <div class="inner">
                                <h3>${fn:length(finishDiv)}</h3>

                                <p>Finalizadas</p>
                            </div>
                            <div class="icon">
                                <i class="fas fa-clipboard-check"></i>
                            </div>
                            <a onclick="getIncidences(4)" href="#" class="small-box-footer">Ver <i class="fas fa-arrow-circle-right"></i></a>
                        </div>
                    </div>
                    <!-- ./col -->        
                </c:if>
                <c:if test="${finishDiv == null}">
                    <div class="col-lg-4 col-sm-12 col-md-6  col-6 float-left">
                        <!-- small box -->
                        <div class="small-box bg-gray">
                            <div class="inner">
                                <h3>0</h3>

                                <p>Finalizadas</p>
                            </div>
                            <div class="icon">
                                <i class="fas fa-clipboard-check"></i>
                            </div>
                            <a  href="#" class="small-box-footer">Ver <i class="fas fa-arrow-circle-right"></i></a>
                        </div>
                    </div>
                    <!-- ./col -->        
                </c:if>

            </div>

            <div class="col-6 col-lg-6">
                <input type="hidden" id="path" value="${pageContext.servletContext.contextPath}">

                <table id="table_incidences" class="display table">
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Incidencia</th>
                        </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
            </div>





        </div>




        <!-- /.No quitar esto, copiar en todos los demas -->          
    </div>
    <!-- /.container-fluid -->
</section>
<!-- /.content -->



<%@include file="_endPanel.jsp" %>
<script src="js/principal.js"></script>
<script>
                                $('#table_incidences').DataTable({});
</script>