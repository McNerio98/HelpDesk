<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%@include file="_startPanel.jsp" %>
<!-- Content Header (Page header) Esto dependera de cada pagina-->
<div class="content-header">
    <div class="container-fluid">
        <div class="row mb-2">
            <div class="col-sm-6">
                <h1 class="m-0 text-dark">Seccion Reportes</h1>
            </div><!-- /.col -->
            <div class="col-sm-6">
                <ol class="breadcrumb float-sm-right">
                    <li class="breadcrumb-item"><a href="${pageContext.servletContext.contextPath}/Principal">Inicio</a></li>
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
        <div class="row mt-3">
            <c:if test="${Rol != null && Rol==1}">
                <form action="${pageContext.servletContext.contextPath}/Reportes" class="col-sm-8 m-auto" method="POST">
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label for="exampleFormControlSelect1">Fecha de Inicio</label>
                                <input type="date" class="form-control" name="dateFechaInicio" id="dateFechaInicio" required="required">
                            </div>
                            <div class="form-group mt-4">
                                <label for="exampleFormControlSelect1">Filtrar por Departamento</label>
                                <select class="form-control" id="slcDepartment" name="slcDepartment">
                                    <c:forEach var="idp" items="${DeptosList}">
                                        <option value="${idp.idDepto}">${idp.deptoName}</option>
                                    </c:forEach>                                
                                </select>
                            </div>                                         
                        </div>                     
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label for="exampleFormControlSelect1">Fecha Final</label>
                                <input type="date" class="form-control" name="dateFechaFinal" id="dateFechaFinal" required="required">
                            </div> 
                            <div class="form-group mt-4">
                                <label for="exampleFormControlSelect1">Filtrar Estado</label>
                                <select class="form-control" id="slcEstado" name="slcEstado">
                                    <option value="1">Solicitadas</option>
                                    <option value="2">Asignadas</option>
                                    <option value="3">En Ejecucion</option>
                                    <option value="4">Finalizadas</option>
                                    <option value="5">Rechazadas</option>
                                    <option value="6">Denegadas</option>
                                </select>
                            </div>                                                
                        </div>
                        <div class="col-12 mt-3">
                            <input type="submit" value="Generar" class="btn btn-success float-sm-right">
                        </div>
                    </div>
                </form>                
            </c:if>
        </div>        
        <!-- /.No quitar esto, copiar en todos los demas -->          
    </div>
    <!-- /.container-fluid -->
</section>
<!-- /.content -->

<%@include file="_endPanel.jsp" %>        
