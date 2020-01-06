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
            <c:if test="${todasDiv != null}">
                <div class="col-lg-3 col-6">
                    <!-- small box -->
                    <div class="small-box bg-info">
                        <div class="inner">
                            <h3>${fn:length(todasDiv)}</h3>

                            <p>Todas la incidencias</p>
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
                <div class="col-lg-3 col-6">
                    <!-- small box -->
                    <div class="small-box bg-info">
                        <div class="inner">
                            <h3>0</h3>

                            <p>Todas la incidencias</p>
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
                <div class="col-lg-3 col-6">
                    <!-- small box -->
                    <div class="small-box bg-success">
                        <div class="inner">
                            <h3>${fn:length(processDiv)}</h3>

                            <p>En proceso</p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-md-settings"></i>
                        </div>
                        <a onclick="getIncidences(2)" href="#" class="small-box-footer">Ver <i class="fas fa-arrow-circle-right"></i></a>
                    </div>
                </div>
                <!-- ./col -->        
            </c:if>
            <c:if test="${processDiv == null}">
                <div class="col-lg-3 col-6">
                    <!-- small box -->
                    <div class="small-box bg-success">
                        <div class="inner">
                            <h3>0</h3>

                            <p>En proceso</p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-md-settings"></i>
                        </div>
                        <a  href="#" class="small-box-footer">Ver <i class="fas fa-arrow-circle-right"></i></a>
                    </div>
                </div>
                <!-- ./col -->        
            </c:if>
            <c:if test="${urgenteDiv != null}">
                <div class="col-lg-3 col-6">
                    <!-- small box -->
                    <div class="small-box bg-danger">
                        <div class="inner">
                            <h3>${fn:length(urgenteDiv)}</h3>

                            <p>Urgente</p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-md-flame"></i>
                        </div>
                        <a onclick="getIncidences(3)" href="#" class="small-box-footer">Ver <i class="fas fa-arrow-circle-right"></i></a>
                    </div>
                </div>
                <!-- ./col -->        
            </c:if>
            <c:if test="${urgenteDiv == null}">
                <div class="col-lg-3 col-6">
                    <!-- small box -->
                    <div class="small-box bg-danger">
                        <div class="inner">
                            <h3>0</h3>

                            <p>Urgente</p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-md-flame"></i>
                        </div>
                        <a  href="#" class="small-box-footer">Ver <i class="fas fa-arrow-circle-right"></i></a>
                    </div>
                </div>
                <!-- ./col -->        
            </c:if>
            <c:if test="${finishDiv != null}">
                <div class="col-lg-3 col-6">
                    <!-- small box -->
                    <div class="small-box bg-gray">
                        <div class="inner">
                            <h3>${fn:length(finishDiv)}</h3>

                            <p>Finalizadas</p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-md-clipboard"></i>
                        </div>
                        <a onclick="getIncidences(4)" href="#" class="small-box-footer">Ver <i class="fas fa-arrow-circle-right"></i></a>
                    </div>
                </div>
                <!-- ./col -->        
            </c:if>
            <c:if test="${finishDiv == null}">
                <div class="col-lg-3 col-6">
                    <!-- small box -->
                    <div class="small-box bg-gray">
                        <div class="inner">
                            <h3>0</h3>

                            <p>Finalizadas</p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-md-clipboard"></i>
                        </div>
                        <a  href="#" class="small-box-footer">Ver <i class="fas fa-arrow-circle-right"></i></a>
                    </div>
                </div>
                <!-- ./col -->        
            </c:if>
                
            

        </div>
        <input type="hidden" id="path" value="${pageContext.servletContext.contextPath}">
        
        <ul class="list-group" id="listIncidences">
            
        </ul>

        <!-- /.No quitar esto, copiar en todos los demas -->          
    </div>
    <!-- /.container-fluid -->
</section>
<!-- /.content -->

<script src="js/principal.js"></script>

<%@include file="_endPanel.jsp" %>