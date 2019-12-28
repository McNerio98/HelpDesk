<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="_startPanel.jsp" %>
<script src="js/departamento.js"></script>                     
<div class="row mb-2">
    <div class="col-sm-6">
        <h1 class="m-0 text-dark">Lista de Departamentos</h1>

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

<!-- Main content -->
<section class="content">
    <div class="container-fluid">

        <!--Table-->
        <div class="col-md-12 table-responsive">
            <table class="table table-striped">
                <thead>

                    <tr>
                        <th>ID</th>
                        <th>Departamento</th>
                        <th>Descripcion</th>
                        <th>Opcion</th>
                    </tr>
                </thead>
                <tbody>

                    <c:if test="${listDepto != null}">
                        <c:forEach var="listDepto" items="${listDepto}">
                            <tr id="${listDepto.getIdDepto()}">
                                <td>${listDepto.getIdDepto()}</td>
                                <td>${listDepto.getDeptoName()}</td>
                                <td>${listDepto.getDescription()}</td>
                                <td>
                                    <button type="button" class="btn btn-info" onclick="updateDepto(${listDepto.getIdDepto()})">Actualizar</button>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    <c:if test="${listDepto == null}">
                    <div class="alert alert-warning" role="alert">
                        Aun no hay departamentos
                    </div>
                </c:if>



                </tbody>
            </table>
        </div>

        <!--Modal for New Departamento-->
        <div id="modaltoNewDepto" class="modal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Nuevo Registro</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <!--Formulario Nuevo-->

                        
                                <form action="${pageContext.servletContext.contextPath}/Departamentos?accion=nuevo" method="post">
                                    <div class="form-group">
                                        <label for="exampleInputEmail1">Nombre</label>
                                        <input name="deptoname" type="text" class="form-control" id="exampleInputEmail1" placeholder="Ingrese el nombre del departamento">
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
        <div id="modaltoUpdateDepto" class="modal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Actualizar</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">

                        <form action="${pageContext.servletContext.contextPath}/Departamentos?accion=actualizar" method="post">
                            <input type="hidden" value="" id="IdDepto" name="iddepto">
                            <div class="form-group">
                                <label for="exampleInputEmail1">Nombre</label>
                                <input name="deptoname" type="text" class="form-control" id="inputName" value="" >

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

        <%@include file="_endPanel.jsp" %>
