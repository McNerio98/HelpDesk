<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<%@include file="_startPanel.jsp" %>

<script src="js/momentjs.min.js"></script>

<!-- Content Header (Page header) Esto dependera de cada pagina-->
<div class="content-header">
    <div class="container-fluid">
        <div class="row mb-2">
            <div class="col-sm-6">
                <h1 class="m-0 text-dark">Informacion | Incidencia</h1>
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

        <div class="row">
            <div class="col-md-8">
                <nav>
                    <div class="nav nav-tabs" id="nav-tab" role="tablist">
                        <a class="nav-item nav-link active" id="nav-home-tab" data-toggle="tab" href="#nav-home" role="tab" aria-controls="nav-home" aria-selected="true">Definicion</a>
                        <a class="nav-item nav-link" id="nav-profile-tab" data-toggle="tab" href="#nav-profile" role="tab" aria-controls="nav-profile" aria-selected="false">Control</a>
                        <a class="nav-item nav-link" id="nav-contact-tab" data-toggle="tab" href="#nav-contact" role="tab" aria-controls="nav-contact" aria-selected="false">Gestion</a>
                    </div>
                </nav>
                <div class="tab-content" id="nav-tabContent">
                    <div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
                        <div class="row">
                            <c:choose>
                                <c:when test="${ObjectInfo.idTecnico == idUsuario && ObjectInfo.status == 'Asignada'}">
                                    <div class="col-12">
                                        <div class=" float-sm-right mt-2">
                                            <a class="btn btn-primary" id="linkAceptar"  href="${pageContext.servletContext.contextPath}/Procesos?accion=aceptar&idbr=${ibr}&ic=${idIncidence}">Aceptar</a>
                                            <button type="button" class="btn btn-danger mr-2" data-toggle="modal" data-target="#modalrechazo">Rechazar</button>

                                            <!-- Modal -->
                                            <div class="modal fade" id="modalrechazo" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                                <div class="modal-dialog" role="document">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h5 class="modal-title" id="exampleModalLabel">NOTA DE RECHAZO</h5>
                                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                <span aria-hidden="true">&times;</span>
                                                            </button>
                                                        </div>

                                                        <div class="modal-body">
                                                            <form action="${pageContext.servletContext.contextPath}/Procesos?accion=rechazar&idbr=${ibr}&ic=${idIncidence}" method="POST">
                                                                <div class="form-group">
                                                                    <label for="exampleFormControlTextarea1">Especifique motivo de rechazo</label>
                                                                    <textarea class="form-control" id="txtContenido" name="txtContenido" rows="3" required="required"></textarea>
                                                                </div>
                                                                <div class="modal-footer">
                                                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                                                                    <input class="btn btn-primary" type="submit" value="Enviar">
                                                                </div>                                                
                                                            </form>
                                                        </div>

                                                    </div>
                                                </div>
                                            </div>
                                            <!-- / Modal -->

                                        </div>                                
                                    </div>                                    
                                </c:when>
                                <c:when test="${ObjectInfo.idDeptoTecnico == idDepUser && ObjectInfo.status == 'En Solicitud' && Rol == 2}">
                                    <div class="col-12">
                                        <div class=" float-sm-right mt-2">
                                            <a class="btn btn-primary" href="Procesos?accion=conceder&idbr=${ibr}&iddc=${ObjectInfo.idDeptoTecnico}&ic=${idIncidence}">Conceder</a>
                                            <button type="button" class="btn btn-danger mr-2" data-toggle="modal" data-target="#modaldenegar">Denegar</button>

                                            <!-- Modal -->
                                            <div class="modal fade" id="modaldenegar" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                                <div class="modal-dialog" role="document">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h5 class="modal-title" id="exampleModalLabel">DENEGACION DE PERMISO</h5>
                                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                <span aria-hidden="true">&times;</span>
                                                            </button>
                                                        </div>

                                                        <div class="modal-body">
                                                            <form action="${pageContext.servletContext.contextPath}/Procesos?accion=denegar&idbr=${ibr}&ic=${idIncidence}&iddc=${ObjectInfo.idDeptoTecnico}" method="POST">
                                                                <div class="form-group">
                                                                    <label for="exampleFormControlTextarea1">Especifique motivo de la denegacion</label>
                                                                    <textarea class="form-control" id="txtContenido" name="txtContenido" rows="3" required="required" maxlength="500"></textarea>
                                                                </div>
                                                                <div class="modal-footer">
                                                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                                                                    <input class="btn btn-primary" type="submit" value="Enviar">
                                                                </div>                                                
                                                            </form>
                                                        </div>

                                                    </div>
                                                </div>
                                            </div>
                                            <!-- / Modal -->

                                        </div>                                
                                    </div>                                     
                                </c:when>
                                <c:when test="${ObjectInfo.creador == Usuario && (ObjectInfo.status == 'Rechazada' || ObjectInfo.status == 'Denegada')}">
                                    <div class="col-12">
                                        <div class=" float-sm-right mt-2">
                                            <a class="btn btn-primary" href="Informacion?accion=update&ic=${idIncidence}">Reasignar</a>
                                        </div>                                
                                    </div>                                     
                                </c:when>                                

                            </c:choose>

                        </div>
                        <table class="table mt-4">
                            <tbody>
                                <tr class="border-none">
                                    <td class="text-primary"><b>Departamento:</b></td>
                                    <td>${ObjectInfo.getDepartamento()}</td>
                                </tr>
                                <tr>
                                    <td class="text-primary"><b>Titulo:</b></td>
                                    <td>${ObjectInfo.getTitulo()}</td>
                                </tr>
                            </tbody>
                        </table>
                        <p><b class="text-primary">Descripcion: </b>${ObjectInfo.getDescripcion()}</p>
                        <div class="row mt-4">
                            <div class="col-sm-6 col-lg-4">
                                <div class="info-box mh-inherit shadow-none p-0">
                                    <span class="info-box-icon bg-info w-60px"><i class="fas fa-cogs"></i></span>
                                    <div class="info-box-content">
                                        <span class="info-box-text">Estado</span>
                                        <c:choose>
                                            <c:when test="${ObjectInfo.status == 'En Ejecucion'}">
                                                <span class="info-box-number text-success">${ObjectInfo.status}</span>
                                            </c:when>
                                            <c:when test="${ObjectInfo.status == 'Rechazada'}">
                                                <span class="info-box-number text-danger">${ObjectInfo.status}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="info-box-number">${ObjectInfo.status}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-lg-4">
                                <div class="info-box mh-inherit shadow-none p-0">
                                    <span class="info-box-icon bg-info w-60px"><i class="fas fa-calendar-alt"></i></span>
                                    <div class="info-box-content">
                                        <span class="info-box-text">Fecha de Creacion</span>
                                         <script>
                                                moment.locale('es');
                                                var date = "${ObjectInfo.getFechaCreacion()}";
                                                var result = moment(date).format('L') + " " + moment().format('LT');
                                                document.write(result);
                                            </script></span>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-lg-4">
                                <div class="info-box mh-inherit shadow-none p-0">
                                    <span class="info-box-icon bg-info w-60px"><i class="far fa-clock"></i></span>
                                    <div class="info-box-content">
                                        <span class="info-box-text">Prioridad</span>
                                        <span class="info-box-number">${ObjectInfo.getPrioridad()}</span>
                                    </div>
                                </div>
                            </div>
                        </div>                        
                        <div class="row mt-4">
                            <div class="col-sm-6 col-lg-4">
                                <div class="info-box mh-inherit shadow-none p-0">
                                    <span class="info-box-icon bg-info w-60px"><i class="fas fa-user-tag"></i></span>
                                    <div class="info-box-content">
                                        <span class="info-box-text">Creador</span>
                                        <span class="info-box-number">${ObjectInfo.getCreador()}</span>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-lg-4">
                                <div class="info-box mh-inherit shadow-none p-0">
                                    <span class="info-box-icon bg-info w-60px"><i class="fab fa-buffer"></i></span>
                                    <div class="info-box-content">
                                        <span class="info-box-text">Clasificacion</span>
                                        <span class="info-box-number">${ObjectInfo.getClasificacion()}</span>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-lg-4">
                                <div class="info-box mh-inherit shadow-none p-0">
                                    <span class="info-box-icon bg-info w-60px"><i class="fas fa-donate"></i></span>
                                    <div class="info-box-content">
                                        <span class="info-box-text">Costo Total</span>
                                        <span class="info-box-number">$ <span>${ObjectInfo.getCostoTotal()}</span></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">
                        <!--CONTENIDO DE CONTROL-->
                        <div class="table-responsive-lg">
                            <table class="table mt-4 table-sm">
                                <thead>
                                    <tr>
                                        <th scope="col">Asignado a</th>
                                        <th scope="col">Estado</th>
                                        <th scope="col">Inicio Prev.</th>
                                        <th scope="col">Inicio Real</th>
                                        <th scope="col">Fin Previsto.</th>
                                        <th scope="col">Fin Real</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="ic" items="${LstControl}">
                                        <tr>
                                            <td>${ic.receptor}</td>
                                            <td>${ic.status}</td>
                                            <td><script>
                                                if ("${ic.inicioPrev}" != "") {
                                                    moment.locale('es');
                                                    var date = "${ic.inicioPrev}";
                                                    var result = moment(date).format('L') + " " + moment().format('LT');
                                                    document.write(result);
                                                }
                                                </script></td>
                                            <td><script>
                                                if ("${ic.inicioReal}" != "") {
                                                    moment.locale('es');
                                                    var date = "${ic.inicioReal}";
                                                    var result = moment(date).format('L') + " " + moment().format('LT');
                                                    document.write(result);
                                                }
                                                </script></td>
                                            <td><script>
                                                if ("${ic.finPrev}" != "") {
                                                    moment.locale('es');
                                                    var date = "${ic.finPrev}";
                                                    var result = moment(date).format('L') + " " + moment().format('LT');
                                                    document.write(result);
                                                }
                                                </script></td>
                                            <td><script>
                                                if ("${ic.finReal}" != "") {
                                                    moment.locale('es');
                                                    var date = "${ic.finReal}";
                                                    var result = moment(date).format('L') + " " + moment().format('LT');
                                                    document.write(result);
                                                }
                                                </script>--</td>                                              
                                        </tr>
                                    </c:forEach>

                                </tbody>
                            </table>
                        </div>                  
                    </div>
                    <div class="tab-pane fade" id="nav-contact" role="tabpanel" aria-labelledby="nav-contact-tab">
                        <c:if test="${ObjectInfo.idTecnico == idUsuario && ObjectInfo.status == 'En Ejecucion'}">
                            <div class="row p-2">
                                <div class="col-12">
                                    <a href="${pageContext.servletContext.contextPath}/Procesos?accion=finalizar&idbr=${ibr}&ic=${idIncidence}" class="btn btn-outline-primary float-sm-right ml-2" id="btnFinalizar">Finalizar</a>
                                    <button class="btn btn-primary float-sm-right ml-2" id="nuevaGestion" data-toggle="modal" data-target="#ModalNuevaGestion">+ Nueva Gestion</button>
                                </div>
                            </div>                            
                        </c:if>
                        <div id="contenidoGestiones">

                            <div class="p-3 text-center" id="pnlLoad" style="display:none;">
                                <img src="img/load.gif" style="width: 100px;height: auto;">
                            </div>

                        </div>

                    </div>
                </div>  
            </div>
            <div class="card col-md-4 mt-4 mt-md-0">
                <div class="card-header">
                    <h3 class="card-title">Notas y Observaciones</h3>
                    <div class="card-tools">
                        <span class="badge badge-primary" id="cantidadMsg">0</span>
                    </div>
                    <!-- /.card-tools -->
                </div>
                <!-- /.card-header -->
                <div class="card-body p-2" style="max-height: 350px !important;overflow-y: auto;" id="contentNotes">

                    <c:choose>
                        <c:when test="${LstNotes!= null}">
                            <c:forEach var="itr" items="${LstNotes}">
                                <div class="direct-chat-msg">
                                    <div class="direct-chat-infos clearfix">
                                        <span class="direct-chat-name float-left">${itr.fullname}</span>
                                        <span class="direct-chat-timestamp float-right">${itr.roleName}</span>
                                    </div>
                                    <!-- /.direct-chat-infos -->
                                    <img class="direct-chat-img" src="dist/img/user1-128x128.jpg" alt="message user image">
                                    <!-- /.direct-chat-img -->
                                    <c:choose>
                                        <c:when test="${itr.noteType == 'Rechazo' || itr.noteType == 'Denegacion'}">
                                            <div class="direct-chat-text bg-danger">
                                            </c:when>
                                            <c:otherwise>
                                                <div class="direct-chat-text">
                                                </c:otherwise>
                                            </c:choose>
                                            ${itr.description}
                                        </div>
                                        <!-- /.direct-chat-text -->
                                    </div>                                 
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <h6 class="text-muted text-center m-3">Actualmente no se ha registrado ninguna nota o observacion de esta incidencia</h6>
                            </c:otherwise>
                        </c:choose>                                       
                    </div>
                    <!-- /.card-body -->
                    <c:if test="${(ObjectInfo.idTecnico == idUsuario || idRol != 3)&& ObjectInfo.status == 'Finalizada'}">
                        <div class="card-footer p-2">
                            <form action="${pageContext.servletContext.contextPath}/Procesos?accion=observacion&ic=${idIncidence}" method="POST">
                                <div class="input-group">
                                    <input type="text" name="txtContentObs" placeholder="Observacion..." class="form-control" required="required" maxlength="500">
                                    <span class="input-group-append">
                                        <input class="btn btn-primary" type="submit" value="Enviar">
                                    </span>
                                </div>
                            </form>
                        </div>                    
                    </c:if>

                </div>
                <!-- /.card -->
            </div>


            <!-- /.No quitar esto, copiar en todos los demas -->          
        </div>
        <!-- /.container-fluid -->
</section>
<!-- /.content -->
<!-- Acoordion personalizado -->

<!-- Modal sobre Error de incidencias activas -->
<div class="modal fade" id="errorModalActivas" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title text-danger" id="exampleModalLabel">SIN PODER ACEPTAR</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>          
            <div class="modal-body text-center">
                <h2 class=" text-danger display-2"><i class="fas fa-times"></i></h2>
                <p class="">Actualmente posee una Incidencia en proceso, para poder aceptar finaliza la actual.</p>
            </div> 
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Entendido</button>
            </div>               
        </div>
    </div>
</div>
<!-- / Modal sobre Error de incidencias activas -->

<input type="hidden" id="rutaPath" value="${pageContext.servletContext.contextPath}">
<input type="hidden" id="idenIBR" value="${ibr}">



<!-- Modal para nueva Gestion -->
<div class="modal fade" id="ModalNuevaGestion" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Nueva Gestion</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="${pageContext.servletContext.contextPath}/Procesos?accion=nuevaGestion&idbr=${ibr}&ic=${idIncidence}" method="POST">
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="inputtitle">Titulo</label>
                            <input type="text" class="form-control" id="inputtitle" name="txtTitle" required="required">
                        </div>
                        <div class="form-group col-md-6">
                            <label for="inputtype">Tipo</label>
                            <select id="inputtype" class="form-control" name="slcTipo">
                                <option value="1">Correo</option>
                                <option value="2">Solicitud</option>
                                <option value="3" selected>Procedimiento</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="txtDescription">Descripcion</label>
                        <textarea class="form-control" id="txtDescription" name="txtDescription" rows="2"></textarea>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-sm-7">
                            <label for="exampleFormControlFile1">Archivos Adjuntos</label>
                            <input type="file" class="form-control-file" id="exampleFormControlFile1">
                        </div>                       
                        <div class="col-sm-5">
                            <div class="form-group">
                                <label for="txtCost">Costo Adicionales</label>
                                <input type="text" class="form-control" id="txtCost" name="txtCost" placeholder="$ 0.00">
                            </div>                        
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                        <input type="submit" class="btn btn-primary" value="Confirmar">
                    </div>                    
                </form>          
            </div>

        </div>
    </div>
</div>
<!-- /Modal para nueva Gestion -->


<%@include file="_endPanel.jsp" %>        
<script src="js/accordionMc.js"></script>
