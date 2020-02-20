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
                        <p class="text-danger m-0" ><strong>Problemas al registrar!</strong></p>
                    </c:if>
                    <c:if test="${resultado==2}">
                        <p class="text-success m-0"><strong>Se ha registro con exito</strong></p>
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
                                        <h5 class="description-header text-success">Total Registros</h5>
                                        <span class="description-text">${generalData.numRegistros}</span>
                                    </div>                        
                                </div>
                            </div>
                            <p class="text-center">
                                <strong>Detalles de Requisicion</strong>
                            </p>                    

                            <div id="pnlRegistros">

                                <table class="table table-bordered">
                                    <thead>                  
                                        <tr>
                                            <th style="width: 10px">#</th>
                                            <th>Descripcion</th>
                                            <th style="width: 40px">Monto</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:set var="contador" value="${1}" />
                                        <c:forEach var="req" items="${LstDetalles}">
                                            <tr>
                                                <td>${contador}</td>
                                                <td>${req.descripcion}</td>
                                                <td><span class="badge bg-warning"> $ ${req.monto}</span></td>
                                            </tr>                          
                                            <c:set var="contador" value="${contador + 1}" />
                                        </c:forEach>
                                    </tbody>
                                </table>

                            </div>                                                    

                        </div>
                        <div class="col-md-4">
                            <div class="card card-sucress cardutline direct-chat direct-chat-success">
                                <div class="card-header">
                                    <h3 class="card-title">Chat </h3>

                                    <div class="card-tools">
                                        <span data-toggle="tooltip" title="3 New Messages" class="badge bg-success">3</span>

                                    </div>
                                </div>
                                <!-- /.card-header -->
                                <div class="card-body" style="overflow: overflow-y; height: 400px;">
                                    <!-- Conversations are loaded here -->
                                    <div class="direct-chat-messages">
                                        <!-- Message. Default to the left -->
                                        <div class="direct-chat-msg">
                                            <div class="direct-chat-infos clearfix">
                                                <span class="direct-chat-name float-left">Alexander Pierce</span>
                                                <span class="direct-chat-timestamp float-right">23 Jan 2:00 pm</span>
                                            </div>
                                            <!-- /.direct-chat-infos -->
                                            <img class="direct-chat-img" src="framework/img/user1-128x128.jpg" alt="Message User Image">
                                            <!-- /.direct-chat-img -->
                                            <div class="direct-chat-text">
                                                Is this template really for free? That's unbelievable!
                                            </div>
                                            <!-- /.direct-chat-text -->
                                        </div>
                                        <!-- /.direct-chat-msg -->

                                        <!-- Message to the right -->
                                        <div class="direct-chat-msg right">
                                            <div class="direct-chat-infos clearfix">
                                                <span class="direct-chat-name float-right">Sarah Bullock</span>
                                                <span class="direct-chat-timestamp float-left">23 Jan 2:05 pm</span>
                                            </div>
                                            <!-- /.direct-chat-infos -->
                                            <img class="direct-chat-img" src="framework/img/user3-128x128.jpg" alt="Message User Image">
                                            <!-- /.direct-chat-img -->
                                            <div class="direct-chat-text">
                                                You better believe it!
                                            </div>
                                            <!-- /.direct-chat-text -->
                                        </div>
                                        <!-- /.direct-chat-msg -->
                                    </div>
                                    <!--/.direct-chat-messages-->

                                    <!-- /.direct-chat-pane -->
                                </div>
                                <!-- /.card-body -->
                                <div class="card-footer">
                                    <form action="#" method="post">
                                        <div class="input-group">
                                            <input type="text" name="message" placeholder="Escribir mensaje..." class="form-control">
                                            <span class="input-group-append">
                                                <button type="submit" class="btn btn-success">Enviar</button>
                                            </span>
                                        </div>
                                    </form>
                                </div>
                                <!-- /.card-footer-->
                            </div>

                            <div style="text-align: right;">
                                <button type="submit" class="btn btn-dark">Aceptar</button>                    
                                <button type="button" class="btn btn-warning">Rechazar</button>                      
                            </div>            

                        </div>
                    </div>
                </div>


            </div>          
        </div>        


        <!-- /.No quitar esto, copiar en todos los demas -->          
    </div>
    <!-- /.container-fluid -->
</section>
<!-- /.content -->        
<%@include file="_endPanel.jsp" %>
