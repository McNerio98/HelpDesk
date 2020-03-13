<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="_startPanel.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- Content Header (Page header) Esto dependera de cada pagina-->
<div class="content-header">
    <div class="container-fluid">
        <div class="row mb-2">
            <div class="col-sm-6">
                <h1 class="m-0 text-dark">Definicion de Requisicion</h1>
                <c:if test="${resultado!=null}">
                    <c:if test="${resultado==1}">
                        <p class="text-success m-0" ><strong>Accion exitosa!</strong></p>
                    </c:if>
                    <c:if test="${resultado==2}">
                        <p class="text-danger m-0"><strong>Accion Fallida!</strong></p>
                    </c:if>
                </c:if>                
            </div><!-- /.col -->
            <div class="col-sm-6">
                <ol class="breadcrumb float-sm-right">
                    <li class="breadcrumb-item"><a href="${pageContext.servletContext.contextPath}/PrincipalRequisicion">Inicio</a></li>
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


        <div class="col-md-12">
            <div class="card">
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-8 border-right">
                            <p class="text-center">
                                <strong>Detalles de Requisicion</strong>
                            </p>                    

                            <div id="pnlRegistros" style="max-height: 400px; overflow-y: auto;"></div>
                            <hr>
                            <p class="text-center">
                                <strong>Seccion de Enlaces / Documentos</strong>
                            </p>                            
                            <div id="pnlEnlaces" style="max-height: 400px; overflow-y: auto;"></div>
                            <div>
                                <a class="btn btn-app" id="reloadDetail">
                                    <i class="fas fa-sync"></i> Recargar
                                </a>                                
                                <c:choose>
                                    <c:when test = "${(pg.idCreador == idUsuario) && (pg.estado == 1 || pg.estado == 2)}">
                                        <a href="${pageContext.servletContext.contextPath}/RequisicionInfo?accion=update&idReq=${idReq}" class="btn btn-app text-dark">
                                            <i class="fas fa-edit"></i>
                                            Modificar
                                        </a>
                                    </c:when>
                                    <c:when test = "${Rol == 6 && pg.estado == 1}">
                                        <a href="${pageContext.servletContext.contextPath}/ProcesosReq?idReq=${idReq}&accion=revision" class="btn btn-app text-dark">
                                            <i class="fas fa-eye"></i>
                                            Revision
                                        </a>
                                    </c:when>
                                    <c:when test = "${Rol == 6 && pg.estado == 2 && pg.idAutorizador == idUsuario}">
                                        <a href="${pageContext.servletContext.contextPath}/ProcesosReq?idReq=${idReq}&accion=conceder" class="btn btn-app text-dark">
                                            <i class="fas fa-location-arrow"></i>
                                            Conceder
                                        </a>
                                        <a href="${pageContext.servletContext.contextPath}/ProcesosReq?idReq=${idReq}&accion=denegar" class="btn btn-app text-dark">
                                            <i class="far fa-hand-paper"></i>
                                            Denegar
                                        </a>
                                    </c:when>
                                    <c:when test = "${Rol == 9 && pg.estado == 3 && pg.idContador == idUsuario}">
                                        <a href="${pageContext.servletContext.contextPath}/ProcesosReq?idReq=${idReq}&accion=cerrar" class="btn btn-app text-dark">
                                            <i class="fas fa-calendar-check"></i>
                                            Finalizar
                                        </a>
                                    </c:when>                                        
                                    <c:when test = "${pg.estado == 3 && (pg.idAutorizador == idUsuario || pg.idContador == idUsuario)}">
                                        <a class="btn btn-app openPDFModal text-dark" id="btnPDFModal">
                                            <i class="fas fa-file-pdf"></i>
                                            <span id="txtMotionPDF">Generar PDF</span> 
                                        </a>
                                    </c:when>                                                                                
                                </c:choose>
                                <a href="${pageContext.servletContext.contextPath}/PrincipalRequisicion" class="btn btn-app text-dark">
                                    <i class="fas fa-arrow-left"></i>
                                    Volver
                                </a>                                
                            </div>

                        </div>
                        <div class="col-md-4">

                            <div class="col">
                                <p class="text-center">
                                    <strong>Datos Generales</strong>
                                </p>
                                <label class="d-block col-form-label p-0" for="inputSuccess"><i class="fas fa-check"></i> A nombre de: <span class="text-custom1">${pg.aNombre}</span> </label>
                                <label class="d-block col-form-label p-0" for="inputSuccess"><i class="fas fa-check"></i> Fecha estimada: <span class="text-custom1"><fmt:formatDate value="${pg.fechaEstimada}" pattern="dd/MM/yyyy"/></span> </label>
                                <label class="d-block col-form-label p-0" for="inputSuccess"><i class="fas fa-check"></i> Fecha creacion: <span class="text-custom1">${generalData.fecha}</span> </label>
                                <label class="d-block col-form-label p-0" for="inputSuccess"><i class="fas fa-check"></i> Empresa: <span class="text-custom1">${generalData.empresa}</span> </label>
                                <label class="d-block col-form-label p-0" for="inputSuccess"><i class="fas fa-check"></i> Departamento: <span class="text-custom1">${generalData.depto}</span></label>
                                <label class="d-block col-form-label p-0" for="inputSuccess"><i class="fas fa-check"></i> Solicitante: <span class="text-custom1">${generalData.solicitante}</span></label>
                                <label class="d-block col-form-label p-0" for="inputSuccess"><i class="fas fa-check"></i> Autorizador: <span class="text-custom1">${generalData.superior}</span></label>                      
                                <label class="d-block col-form-label p-0" for="inputSuccess"><i class="fas fa-check"></i> Contador: <span class="text-custom1">${generalData.contador}</span></label>                                          
                            </div>
                            <div class="col">
                                <div class="description-block  border-bottom mb-1 rounded" style="background-color:rgba(0,0,0,.03);">
                                    <h5 class="description-header text-success">ESTADO</h5>
                                    <span class="description-text">${generalData.estado}</span>
                                </div>                    
                                <div class="description-block  border-bottom mb-1 rounded" style="background-color:rgba(0,0,0,.03);">
                                    <h5 class="description-header text-success">Monto Total</h5>
                                    <span class="description-text">$ ${generalData.montoTotal}</span>
                                </div>                    
                                <div class="description-block  border-bottom mb-1 rounded" style="background-color:rgba(0,0,0,.03);">
                                    <h5 class="description-header text-success">Prioridad</h5>
                                    <span class="description-text">${generalData.prioridad}</span>
                                </div>                                                            
                            </div>


                        </div>
                    </div>
                    <%@include file="components/_ChatNnotificacion.jsp"%>

                    <div class="modal fade modalForPDF" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true" id="modalForPDF">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                   <iframe frameborder="0" width="100%" height="500" id="pnlLoadPDF"></iframe> 
                                </div>                                
                            </div>
                        </div>
                    </div>  

                </div>
            </div>          
        </div>

        <input type="hidden" value="${pageContext.servletContext.contextPath}" id="contextoApp">
        <input type="hidden" value="${idReq}" id="idRequisicion">
    </div>
</section>
<!-- /.content -->
<script src="plugins/jquery/jquery.min.js"></script>
<script src="js/operacionesDefinicionReq.js"></script>

<%@include file="_endPanel.jsp" %>
