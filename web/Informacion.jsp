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
                        <%@include file="_definicion.jsp"%>
                    </div>
                    <div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">
                        <%@include file="_control.jsp"%>
                    </div>
                    <div class="tab-pane fade" id="nav-contact" role="tabpanel" aria-labelledby="nav-contact-tab">
                        <%@include file="_gestion.jsp"%>
                    </div>
                </div>  
            </div>
            <div class="card col-md-4 mt-4 mt-md-0" style="max-height: 500px;">
                <%@include file="_notas.jsp"%>
            </div>


            <!-- /.No quitar esto, copiar en todos los demas -->          
        </div>
        <!-- /.container-fluid -->
</section>
<!-- /.content -->


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
