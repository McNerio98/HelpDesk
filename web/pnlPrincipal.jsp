<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="_startPanel.jsp" %>


<!-- Content Header (Page header) Esto dependera de cada pagina-->
<div class="content-header">
    <div class="container-fluid">
        <div class="row mb-2">
            <div class="col-sm-6">
                <h1 class="m-0 text-dark">Dashboard</h1>
            </div><!-- /.col -->
            <div class="col-sm-6">
                <ol class="breadcrumb float-sm-right">
                    <li class="breadcrumb-item"><a href="#">Home</a></li>
                    <li class="breadcrumb-item active">Dashboard v1</li>
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
            <c:forEach var="iterador" items="${PermisosAsignados}">
                <div class="col-lg-3 col-6">
                    <!-- small box -->
                    <div class="small-box bg-info">
                        <div class="inner">
                            <h3>150</h3>

                            <p>${iterador.menu}</p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-bag"></i>
                        </div>
                        <a href="${pageContext.servletContext.contextPath}${iterador.controller}" class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
                    </div>
                </div>
                <!-- ./col -->        
            </c:forEach>
        </div>




<!-- /.No quitar esto, copiar en todos los demas -->          
    </div>
    <!-- /.container-fluid -->
</section>
<!-- /.content -->
<%@include file="_endPanel.jsp" %>