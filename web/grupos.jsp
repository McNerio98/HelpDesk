<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="_startPanel.jsp" %>


<!-- Content Header (Page header) Esto dependera de cada pagina-->
<div class="content-header">
    <div class="container-fluid">
        <div class="row mb-2">
            <div class="col-sm-6">
                <div class="col-sm-6">
                     <h1 class="m-0 text-dark">Mi grupo</h1>

                </div><!-- /.col -->


            </div><!-- /.col -->
            <div class="col-sm-6">
                <ol class="breadcrumb float-sm-right">
                   

                </ol>
            </div><!-- /.col -->
        </div><!-- /.row -->
    </div><!-- /.container-fluid -->
</div>


<!-- Main content -->
<section class="content">
    <div class="container-fluid">
        <!-- Small boxes (Stat box) -->
        <div class="row">
            <!-- Table row -->

            <div class="col-md-12 table-responsive">
                <table class="table table-striped">
                    <thead>

                        <tr>
                            <th>ID</th>
                            <th>Nombre completo</th>
                            <th>Rol Actual</th>
                        </tr>
                    </thead>
                    <tbody>

                        <c:if test="${listEmpleado != null}">
                            <c:forEach var="listUser" items="${listEmpleado}">
                                <tr id="${listUser.getUsuario().getIdUser()}">
                                    <td>${listUser.getUsuario().getIdUser()}</td>
                                    <td>${listUser.getUsuario().getFirsName()} ${listUser.getUsuario().getLastName()}</td>
                                    <td>${listUser.getRol().getRoleName()}</td>
                                </tr>
                            </c:forEach>
                        </c:if>
                        <c:if test="${dataEmpty == 'true'}">
                        <div class="alert alert-warning" role="alert">
                            No hay ningun resultado
                        </div>
                    </c:if>



                    </tbody>
                </table>
            </div>

            <!-- ./col -->
        </div>
        <!-- /.row -->
        <!-- Main row -->

        <!-- /.row (main row) -->
    </div><!-- /.container-fluid -->
</section>
<!-- /.content -->
</div>


        <!-- /.No quitar esto, copiar en todos los demas -->          
    </div>
    <!-- /.container-fluid -->
</section>


<%@include file="_endPanel.jsp" %>