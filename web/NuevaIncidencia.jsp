<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="_startPanel.jsp" %>


<!-- Content Header (Page header) Esto dependera de cada pagina-->
<div class="content-header">
    <div class="container-fluid">
        <div class="row mb-2">
            <div class="col-sm-6">
                <h1 class="m-0 text-dark">Crear Incidencia</h1>
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
                    <li class="breadcrumb-item active">HerpDesk</li>
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
            <div class="col-md-6">
                <div class="card card-primary">
                    <div class="card-header">
                        <h3 class="card-title">Datos Generales</h3>
                    </div>
                    <!-- /.card-header -->
                    <!-- form start -->
                    <form role="form" action="${pageContext.servletContext.contextPath}/Incidencias?accion=nueva" method="POST">
                        <div class="card-body">
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text"><i class="fas fa-sticky-note"></i></span>
                                </div>
                                <input type="text" class="form-control" placeholder="Titulo" name="txtTitle" required="true">
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <!-- select -->
                                    <div class="form-group">
                                        <label>Clasificacion</label>
                                        <select class="form-control" name="slcClasificacion">
                                            <c:forEach var="Iterador" items="${ClasfList}">
                                                <option value="${Iterador.idClassification}">${Iterador.classification}</option>
                                            </c:forEach>                                                
                                        </select>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label>Prioridad</label>
                                        <select class="form-control" name="slcPrioridad">
                                            <option value="1">BAJA</option>
                                            <option value="2">MEDIA</option>
                                            <option value="3">ALTA</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <c:if test="${Rol!= null && Rol==1}">
                                <div class="form-group row">
                                    <label>Departamento</label>
                                    <select class="form-control" name="slcDeptoIncidence">
                                        <c:forEach var="idp" items="${DeptosList}">
                                            <option value="${idp.idDepto}">${idp.deptoName}</option>
                                        </c:forEach>
                                    </select>                                    
                                </div>                                                            
                            </c:if>

                            <div class="form-group row">
                                <label>Descripcion</label>
                                <textarea class="form-control" rows="3" placeholder="Enter ..." required="true" name="txtDescripcion"></textarea>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="exampleInputEmail1"><a href="">Asignar a:</a></label>
                                        <input type="hidden" value="2" name="txtReceptor" id="txtReceptor">
                                        <input type="text" class="form-control" id="" placeholder="Ninguno Selecionado" disabled="">
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="exampleInputEmail1">Fecha Final</label><br>
                                        <input type="date" class="form-control" name="dateFechaFinal" id="dateFechaFinal">
                                    </div>                                
                                </div>
                            </div>                                                
                        </div>
                        <!-- /.card-body -->

                        <div class="card-footer">
                            <button type="submit" class="btn btn-primary">Crear</button>
                        </div>
                    </form>
                </div>

            </div>
            <div class="col-md-6">

                <div class="card card-secondary">
                    <div class="card-header">
                        <h3 class="card-title">Tecnico/Receptor</h3>
                    </div>
                    <!-- /.card-header -->
                    <div class="card-body">
                        <div class="row">
                            <div class="col-sm-6">
                                <c:choose>
                                    <c:when test="${Rol!= null && Rol==2}">
                                        <div class="form-group">
                                            <div class="custom-control custom-radio">
                                                <input class="custom-control-input" type="radio" id="customRadio1" name="radioDepto" checked="">
                                                <label for="customRadio1" class="custom-control-label">Depto a cargo</label>
                                            </div>
                                            <div class="custom-control custom-radio">
                                                <input class="custom-control-input" type="radio" id="customRadio2" name="radioDepto" >
                                                <label for="customRadio2" class="custom-control-label">Otros Deptos</label>
                                            </div>
                                        </div>                                                                    
                                    </c:when>
                                    <c:when test="${Rol!=null && Rol==1}">
                                        <div class="form-group">
                                            <label>Filtrar por Roles</label>
                                            <select class="custom-select">
                                                <option>option 1</option>
                                                <option>option 2</option>
                                                <option>option 3</option>
                                                <option>option 4</option>
                                                <option>option 5</option>
                                            </select>
                                        </div>                                    
                                    </c:when>
                                </c:choose>
                            </div>
                            <div class="col-sm-6">
                                <div class="form-group">
                                    <label>Filtrar por Departamentos</label>
                                    <select class="custom-select">
                                        <c:forEach var="Iterador" items="${DeptosList}">
                                            <option value="${Iterador.idDepto}">${Iterador.deptoName}</option>
                                        </c:forEach>                                        
                                    </select>
                                </div>                                
                            </div>
                        </div>
                    </div>
                    <!-- /.card-body -->
                </div>                

            </div>
        </div>



        <!-- /.No quitar esto, copiar en todos los demas -->          
    </div>
    <!-- /.container-fluid -->
</section>
<!-- /.content -->
<%@include file="_endPanel.jsp" %>