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

           <c:if test="${typeSession == 'REQ'}"> 
        
           </c:if>    





        <!-- /.No quitar esto, copiar en todos los demas -->          
    </div>
    <!-- /.container-fluid -->
</section>
<!-- /.content -->



<%@include file="_endPanel.jsp" %>
<script src="js/principal.js"></script>
