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
                <div class="card-header">
                    <h5 class="card-title">Informacion</h5>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-8 border-right">
                            <div class="row">
                                <div class="col-md-6">
                                    <p class="text-center">
                                        <strong>Datos Generales</strong>
                                    </p>                                  
                                    <label class="d-block col-form-label p-0" for="inputSuccess"><i class="fas fa-check"></i> Fecha: <span class="text-custom1">${generalData.fecha}</span> </label>
                                    <label class="d-block col-form-label p-0" for="inputSuccess"><i class="fas fa-check"></i> Empresa: <span class="text-custom1">${generalData.empresa}</span> </label>
                                    <label class="d-block col-form-label p-0" for="inputSuccess"><i class="fas fa-check"></i> Departamento: <span class="text-custom1">${generalData.depto}</span></label>
                                    <label class="d-block col-form-label p-0" for="inputSuccess"><i class="fas fa-check"></i> Solicitante: <span class="text-custom1">${generalData.solicitante}</span></label>
                                    <label class="d-block col-form-label p-0" for="inputSuccess"><i class="fas fa-check"></i> Autorizador: <span class="text-custom1">${generalData.superior}</span></label>                      
                                    <label class="d-block col-form-label p-0" for="inputSuccess"><i class="fas fa-check"></i> Contador: <span class="text-custom1">${generalData.contador}</span></label>                                          
                                </div>
                                <div class="col-md-6">
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
                            <p class="text-center">
                                <strong>Detalles de Requisicion</strong>
                            </p>                    

                            <div id="pnlRegistros" style="max-height: 400px; overflow-y: auto;">


                            </div>
                                        <button class="btn btn-warning" id="reloadDetail">
                                            <i class="fas fa-sync"></i>
                                            Recargar
                                        </button>
                        </div>
                        <div class="col-md-4">
                            <div class="card card-sucress cardutline direct-chat direct-chat-success">
                                <div class="card-header">
                                    <h3 class="card-title">Chat </h3>

                                    <div class="card-tools">
                                        <span data-toggle="tooltip" title="Mensajes Nuevos" class="badge bg-success" id="countMessages">0</span>

                                    </div>
                                </div>
                                <!-- /.card-header -->
                                <div class="card-body" style="height: 400px;" id="bodyMesagges">
                                    <div class="cnt-loading">
                                        <div class="spinner-border" role="status" style="width: 4rem !important; height: 4rem !important;">
                                            <span class="sr-only">Loading...</span>
                                        </div>                                        
                                    </div>
                                </div>
                                <!-- /.card-body -->
                                <c:if  test="${(pg.idAutorizador == idUsuario || pg.idCreador == idUsuario)&& pg.estado==2}">
                                    <div class="card-footer">
                                        <form action="#" method="post" id="formMesagges">
                                            <div class="input-group">
                                                <input type="text" name="message" placeholder="Escribir mensaje..." class="form-control" id="txtContentMsg" maxlength="50">
                                                <span class="input-group-append">
                                                    <button type="button" class="btn btn-success" id="btnSendMsg">Enviar</button>
                                                </span>
                                            </div>
                                        </form>
                                    </div>                                    
                                </c:if>
                                <!-- /.card-footer-->
                            </div>

                            <div style="text-align: right;">

                                
                                <c:choose>
                                    <c:when test = "${(pg.idCreador == idUsuario) && (pg.estado == 1 || pg.estado == 2)}">
                                        <a href="${pageContext.servletContext.contextPath}/RequisicionInfo?accion=update&idReq=${idReq}" class="btn btn-warning">Modificar</a>
                                    </c:when>
                                    <c:when test = "${Rol == 6 && pg.estado == 1}">
                                        <a href="${pageContext.servletContext.contextPath}/ProcesosReq?idReq=${idReq}&accion=revision" class="btn btn-warning">Revision</a>
                                        <a href="${pageContext.servletContext.contextPath}/PrincipalRequisicion" class="btn btn btn-dark">Volver</a>
                                    </c:when>
                                    <c:when test = "${Rol == 6 && pg.estado == 2 && pg.idAutorizador == idUsuario}">
                                        <a href="${pageContext.servletContext.contextPath}/ProcesosReq?idReq=${idReq}&accion=conceder" class="btn btn-warning">Conceder</a>
                                        <a href="${pageContext.servletContext.contextPath}/ProcesosReq?idReq=${idReq}&accion=denegar" class="btn btn btn-dark">Denegar</a>
                                    </c:when>
                                    <c:when test = "${Rol == 9 && pg.estado == 3 && pg.idContador == idUsuario}">
                                        <a href="${pageContext.servletContext.contextPath}/ProcesosReq?idReq=${idReq}&accion=cerrar" class="btn btn-warning">Finalizar</a>
                                        <a href="${pageContext.servletContext.contextPath}/PrincipalRequisicion" class="btn btn btn-dark">Volver</a>
                                    </c:when>                                        
                                </c:choose>
                            </div>            

                        </div>
                    </div>
                    <c:if test="${idReqForPDF!=null}">
                        <div id="viewReportPDF">
                            <iframe  src="${pageContext.servletContext.contextPath}/RequisicionPDF" frameborder="0" width="100%" height="500" id="pnlLoadPDF"></iframe>
                        </div>                                            
                    </c:if>

                </div>


            </div>          
        </div>        


        <!-- /.No quitar esto, copiar en todos los demas -->          
    </div>
    <!-- /.container-fluid -->
</section>
<!-- /.content -->
<script src="plugins/jquery/jquery.min.js"></script>
<script>
    $(document).ready(function () {
        function refreshMessages() {
            $.ajax({
                type: 'POST',
                url: '${pageContext.servletContext.contextPath}/RequisicionInfo?accion=getAllMsg' + '&idReq=' + ${idReq},
                success: function (result) {
                    $('#bodyMesagges').html(result);
                    $('#panelChat').scrollTop($('#panelChat').prop('scrollHeight'));
                    $('#countMessages').text($('.direct-chat-msg').length);

                }
            });
        }

        refreshMessages();
        setInterval(refreshMessages,30000); //Cada 30 segundos


        
        function refreshDetail() {
            $.ajax({
                type: 'POST',
                url: "${pageContext.servletContext.contextPath}/RequisicionInfo?accion=loadDetalles&idReq=${idReq}",
                success: function (result) {
                    $('#pnlRegistros').html(result);
                }
            });
        }
        
        refreshDetail();
        
        $('#reloadDetail').click(refreshDetail);
        
        function sendMessage() {
            let contentMsg = $('#txtContentMsg').val();
            $.ajax({
                type: 'POST',
                data: {msg: contentMsg},
                url: '${pageContext.servletContext.contextPath}/ProcesosReq?accion=newMessage' + '&idReq=' + ${idReq},
                success: function (result) {
                    if (result == 'true') {
                        refreshMessages();
                        $('#txtContentMsg').val("");
                    } else {
                        console.log("ERROR en envio de mensaje");
                    }
                }
            });
        }

        function enviarNewMesagge() {
            let contentMsg = $('#txtContentMsg').val();
            if (contentMsg.length == 0) {
                alert("No hay mensajes");
            } else {
                sendMessage();
            }
        }

        $('#btnSendMsg').click(enviarNewMesagge);
        
        
        $('#formMesagges').submit(function(e){
            e.preventDefault();
            enviarNewMesagge();
        });



    });




</script>
<%@include file="_endPanel.jsp" %>
