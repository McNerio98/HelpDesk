<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="_startPanel.jsp" %>


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
                                            <a class="btn btn-primary" href="Procesos?accion=aceptar&idbr=${ibr}&ic=${idIncidence}">Aceptar</a>
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
                                            <a class="btn btn-danger mr-2" href="Procesos?accion=denegar&id=${ibr}">Denegar</a>
                                        </div>                                
                                    </div>                                     
                                </c:when>
                                <c:when test="${ObjectInfo.creador == Usuario && ObjectInfo.status == 'Rechazada'}">
                                    <div class="col-12">
                                        <div class=" float-sm-right mt-2">
                                            <a class="btn btn-primary" href="Procesos?accion=rasig&id=${ibr}">Reasignar</a>
                                        </div>                                
                                    </div>                                     
                                </c:when>                                

                            </c:choose>

                        </div>
                        <table class="table table-no-b mt-4">
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
                                        <span class="info-box-number">${ObjectInfo.getFechaCreacion()}</span>
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
                                            <td>${ic.inicioPrev}</td>
                                            <td>${ic.inicioReal}</td>
                                            <td>${ic.finPrev}</td>
                                            <td>${ic.finReal}</td>                                            
                                        </tr>
                                    </c:forEach>

                                </tbody>
                            </table>
                        </div>                  
                    </div>
                    <div class="tab-pane fade" id="nav-contact" role="tabpanel" aria-labelledby="nav-contact-tab">
                        <div class="border border-primary rounded  overflow-hidden mt-2">
                            <div class="p-2 text-primary titleGestion" id="titleGestion">
                                <div class="row showHide" >
                                    <div class="col-7">
                                        <span class="bg-primary p-1 rounded">Procedimiento</span> Informe de Cierre de incidencia
                                    </div>
                                    <div class="col-5">
                                        <span class="float-right">12/14/13</span>
                                    </div>                                    
                                </div>
                            </div>
                            <div class="p-2 border-top descripInc" id="descripInc">
                                <p><b>Descripcion: </b> Lorem ipsum dolor sit amet consectetur, adipisicing elit. Dolores recusandae culpa asperiores harum, omnis ipsum exercitationem voluptas earum quod, iusto ratione amet itaque corrupti eaque consequuntur obcaecati magnam facere molestias.</p>
                                <p><i>Adjunto: </i><a href="">000124778.pdf</a> / <i>Costo: </i>$ 6.75</p>
                            </div>                            
                        </div>
                        <div class="border border-success rounded  overflow-hidden mt-2">
                            <div class="p-2 text-success titleGestion" id="titleGestion">
                                <div class="row showHide">
                                    <div class="col-7">
                                        <span class="bg-success p-1 rounded">Solicitud</span> Informe de Cierre de incidencia
                                    </div>
                                    <div class="col-5">
                                        <span class="float-right">12/14/13</span>
                                    </div>                                    
                                </div>
                            </div>
                            <div class="p-2 border-top descripInc" id="descripInc">
                                <p><b>Descripcion: </b> Lorem ipsum dolor sit amet consectetur, adipisicing elit. Dolores recusandae culpa asperiores harum, omnis ipsum exercitationem voluptas earum quod, iusto ratione amet itaque corrupti eaque consequuntur obcaecati magnam facere molestias.</p>
                                <p><i>Adjunto: </i><a href="">000124778.pdf</a> / <i>Costo: </i>$ 6.75</p>
                            </div>                            
                        </div>                        
                        <div class="border border-dark rounded  overflow-hidden mt-2">
                            <div class="p-2 text-dark titleGestion" id="titleGestion">
                                <div class="row showHide">
                                    <div class="col-7">
                                        <span class="bg-dark p-1 rounded">Email</span> Informe de Cierre de incidencia
                                    </div>
                                    <div class="col-5">
                                        <span class="float-right">12/14/13</span>
                                    </div>                                    
                                </div>
                            </div>
                            <div class="p-2 border-top descripInc" id="descripInc">
                                <p><b>Descripcion: </b> Lorem ipsum dolor sit amet consectetur, adipisicing elit. Dolores recusandae culpa asperiores harum, omnis ipsum exercitationem voluptas earum quod, iusto ratione amet itaque corrupti eaque consequuntur obcaecati magnam facere molestias.</p>
                                <p><i>Adjunto: </i><a href="">000124778.pdf</a> / <i>Costo: </i>$ 6.75</p>
                            </div>                            
                        </div>                                                
                    </div>
                </div>  
            </div>
            <div class="card col-md-4 mt-4 mt-md-0">
                <div class="card-header">
                    <h3 class="card-title">Notas y Observaciones</h3>
                    <div class="card-tools">
                        <span class="badge badge-primary">9</span>
                    </div>
                    <!-- /.card-tools -->
                </div>
                <!-- /.card-header -->
                <div class="card-body p-2">

                    <div class="direct-chat-msg">
                        <div class="direct-chat-infos clearfix">
                            <span class="direct-chat-name float-left">Alexander Pierce</span>
                        </div>
                        <!-- /.direct-chat-infos -->
                        <img class="direct-chat-img" src="dist/img/user1-128x128.jpg" alt="message user image">
                        <!-- /.direct-chat-img -->
                        <div class="direct-chat-text">
                            Is this template really for free? That's unbelievable!
                        </div>
                        <!-- /.direct-chat-text -->
                    </div>                                        
                </div>
                <!-- /.card-body -->
                <c:if test="${(ObjectInfo.idTecnico == idUsuario || idRol != 3)&& ObjectInfo.status == 'Finalizada'}">
                    <div class="card-footer p-2">
                        <form action="${pageContext.servletContext.contextPath}/Procesos?accion=observacion" method="POST">
                            <div class="input-group">
                                <input type="text" name="message" placeholder="Observacion..." class="form-control">
                                <span class="input-group-append">
                                    <button type="button" class="btn btn-primary">Enviar</button>
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
<%@include file="_endPanel.jsp" %>        
