<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="_startPanel.jsp" %>
<script src="js/clasificaciones.js"></script>

<!-- Content Header (Page header) Esto dependera de cada pagina-->
<div class="content-header">
    <div class="container-fluid">
        <div class="row mb-2">
            <div class="col-sm-6">
                <div class="col-sm-6">
                    <h1 class="m-0 text-dark">Lista de Clasificaciones</h1>

                </div><!-- /.col -->


            </div><!-- /.col -->
            <div class="col-sm-6">
                <ol class="breadcrumb float-sm-right">
                    <button onclick="newForm()" type="button" class="btn btn-primary">Nuevo</button>

                </ol>
            </div><!-- /.col -->
        </div><!-- /.row -->
    </div><!-- /.container-fluid -->
</div>
<!-- /.content-header -->


<!-- /.content-header -->

<!-- Main content -->
<section class="content">
    <div class="container-fluid">

        <!--Table-->
        <div class="col-md-12 table-responsive">
            <table class="table table-striped">
                <thead>

                    <tr>
                        <th>ID</th>
                        <th>Clasificacion</th>
                        <th>Descripcion</th>
                        <th>Opcion</th>
                    </tr>
                </thead>
                <tbody>

                    <c:if test="${listClass != null}">
                        <c:forEach var="listClass" items="${listClass}">
                            <tr id="${listClass.getIdClassification()}">
                                <td>${listClass.getIdClassification()}</td>
                                <td>${listClass.getClassification()}</td>
                                <td>${listClass.getDescription()}</td>
                                <td>
                                    <button type="button" class="btn btn-info" onclick="updateClass(${listClass.getIdClassification()})">Actualizar</button>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    <c:if test="${listClass == null}">
                    <div class="alert alert-warning" role="alert">
                        Aun no hay clasificaciones
                    </div>
                </c:if>



                </tbody>
            </table>
        </div>



        <!--Modal for Update Departamento-->
        <div id="modaltoNewClass" class="modal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Nuevo Registro</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">

                        <form action="${pageContext.servletContext.contextPath}/Clasificaciones?accion=nuevo" method="post">
                            <div class="form-group">
                                <label for="exampleInputEmail1">Nombre</label>
                                <input name="classname" type="text" class="form-control" id="exampleInputEmail1" placeholder="Ingrese el nombre de la clasificacion">
                            </div>
                            <div class="form-group">
                                <label for="exampleFormControlTextarea1">Agrega una descripcion general</label>
                                <textarea name="descripcion" class="form-control" id="exampleFormControlTextarea1" rows="3"></textarea>
                            </div>

                            <button type="submit" class="btn btn-primary float-right">Crear</button>
                        </form>

                    </div>
                </div>
            </div>
        </div>

        <!--Modal for Update Departamento-->
        <div id="modaltoUpdateClass" class="modal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Actualizar</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">

                        <form action="${pageContext.servletContext.contextPath}/Clasificaciones?accion=actualizar" method="post">
                            <input value="" type="hidden" id="Idclass" name="Idclass">
                            <div class="form-group">
                                <label for="exampleInputEmail1">Nombre</label>
                                <input name="classname" type="text" class="form-control" id="inputName" value="" >

                            </div>
                            <div class="form-group">
                                <label for="exampleFormControlTextarea1">Agrega una descripcion general</label>
                                <textarea name="descripcion" class="form-control" id="inputDescription" rows="3"></textarea>
                            </div>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                <button type="submit" class="btn btn-primary">Guardar Cambios</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>



        <!-- /.No quitar esto, copiar en todos los demas -->          
    </div>
    <!-- /.container-fluid -->
</section>

<%@include file="_endPanel.jsp" %>
