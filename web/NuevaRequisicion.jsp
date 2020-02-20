<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="_startPanel.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- Content Header (Page header) Esto dependera de cada pagina-->
<div class="content-header">
    <div class="container-fluid">
        <div class="row mb-2">
            <div class="col-sm-6">
                <h1 class="m-0 text-dark">Nueva Requisicion</h1>
                <c:if test="${resultado!=null}">
                    <c:if test="${resultado==1}">
                        <p class="text-success m-0" ><strong>Se ha registro con exito</strong></p>
                    </c:if>
                    <c:if test="${resultado==2}">
                        <p class="text-danger m-0"><strong>Problemas al registrar!</strong></p>
                    </c:if>
                </c:if>                
            </div><!-- /.col -->
            <div class="col-sm-6">
                <ol class="breadcrumb float-sm-right">
                    <li class="breadcrumb-item"><a href="#">Home</a></li>
                    <li class="breadcrumb-item active">HelpDesk</li>
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
        <form class="row" action="Requisiciones?accion=nueva" id="formRequisicion" method="POST">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-header">
                        <h5 class="card-title">Complete los siguientes campos</h5>
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-8 border-right">
                                <p class="text-center">
                                    <strong>Detalles de Requisicion</strong>
                                </p>

                                <div id="pnlRegistros">
                                    <div class="row registro">
                                        <div class="input-group mb-3 col-sm-8">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text">
                                                    <i class="fas fa-angle-right"></i>
                                                </span>
                                            </div>
                                            <input type="text" class="form-control txtDescripcion" placeholder="Descripcion"
                                                   maxlength="200">
                                        </div>
                                        <div class="input-group mb-3 col-sm-4">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text">$</span>
                                            </div>
                                            <input type="text" class="form-control txtMonto" placeholder="Monto" pattern="[0.0-9]+">
                                        </div>
                                    </div>
                                    <div class="row registro">
                                        <div class="input-group mb-3 col-sm-8">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text">
                                                    <i class="fas fa-angle-right"></i>
                                                </span>
                                            </div>
                                            <input type="text" class="form-control txtDescripcion" placeholder="Descripcion"
                                                   maxlength="200">
                                        </div>
                                        <div class="input-group mb-3 col-sm-4">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text">$</span>
                                            </div>
                                            <input type="text" class="form-control txtMonto" placeholder="Monto" pattern="[0.0-9]+">
                                        </div>
                                    </div>                                    

                                </div>

                                <div class="text-center">
                                    <button type="button" class="btn btn-outline-dark rounded-0" id="btnAddRegistro">+ Nuevo
                                        Detalle</button>
                                </div>

                            </div>
                            <div class="col-md-4">
                                <p class="text-center">
                                    <strong>Datos Generales</strong>
                                </p>
                                <div class="form-group">
                                    <label class="d-block col-form-label" for="inputSuccess"><i class="fas fa-check"></i> Fecha:
                                        <span class="text-custom1"> ${DataGeneral.fecha}</span> </label>
                                    <label class="d-block col-form-label" for="inputSuccess"><i class="fas fa-check"></i> Empresa:
                                        <span class="text-custom1">${DataGeneral.empresa}</span> </label>
                                    <label class="d-block col-form-label" for="inputSuccess"><i class="fas fa-check"></i>
                                        Departamento: <span class="text-custom1">${DataGeneral.depto}</span></label>
                                    <label class="d-block col-form-label" for="inputSuccess"><i class="fas fa-check"></i>
                                        Solicitante: <span class="text-custom1">${DataGeneral.solicitante}</span></label>
                                    <label class="d-block col-form-label" for="inputSuccess"><i class="fas fa-check"></i>
                                        Autorizador: <span class="text-custom1">${DataGeneral.superior}</span></label>
                                    <div style="text-align: right;">
                                        <button type="submit" class="btn btn-success">Solicitar</button>
                                        <button type="button" class="btn btn-secondary">Cancelar</button>
                                        <input type="hidden" name="JsonReq" value="" id="JsonReq">

                                    </div>

                                </div>

                            </div>
                        </div>
                    </div>
                    <div class="card-footer">
                        <div class="row">
                            <div class="col-sm-4">
                                <div class="description-block border-right">
                                    <span class="description-percentage"><i class="fas fa-eye"></i></span>
                                    <h5 class="description-header text-success">ESTADO</h5>
                                    <span class="description-text">Solicitada</span>
                                </div>
                                <!-- /.description-block -->
                            </div>

                            <div class="col-sm-4">
                                <div class="description-block border-right">
                                    <span class="description-percentage"><i class="fas fa-money-bill-wave"></i></span>
                                    <h5 class="description-header text-success">MONTO TOTAL</h5>
                                    <span class="description-text">$ <span id="totalSum">0</span></span>
                                </div>
                                <!-- /.description-block -->
                            </div>

                            <!-- /.col -->
                            <div class="col-sm-4">
                                <div class="description-block border-right">
                                    <span class="description-percentage "><i class="fas fa-stream"></i></span>
                                    <h5 class="description-header text-success">TOTAL REGISTROS</h5>
                                    <span class="description-text" id="totalRecord">0</span>
                                </div>
                                <!-- /.description-block -->
                            </div>
                        </div>
                        <!-- /.row -->
                    </div>

                </div>
            </div>
        </form>



        <!-- /.No quitar esto, copiar en todos los demas -->          
    </div>
    <!-- /.container-fluid -->
</section>
<!-- /.content -->

<!-- jQuery -->
<script src="plugins/jquery/jquery.min.js"></script>
<script src="js/controlFormReq.js"></script>

<%@include file="_endPanel.jsp" %>