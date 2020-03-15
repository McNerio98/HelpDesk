<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="_startPanel.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- Content Header (Page header) Esto dependera de cada pagina-->
<div class="content-header">
    <div class="container-fluid">
        <div class="row mb-2">
            <div class="col-sm-6">
                <h1 class="m-0 text-dark">Preview Documento PDF</h1>
                <p class="text-danger m-0" id="mesanjeByDeleteStatus"></p>
            </div><!-- /.col -->
            <div class="col-sm-6">
                <ol class="breadcrumb float-sm-right">
                    <li class="breadcrumb-item"><a href="${pageContext.servletContext.contextPath}/PrincipalRequisicion">Inicio</a></li>
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
        <c:if test="${idReqForPDF!=null}">
            <div id="viewReportPDF">
                <iframe  src="${pageContext.servletContext.contextPath}/RequisicionPDF" frameborder="0" width="100%" height="500" id="pnlLoadPDF"></iframe>
            </div>                                            
        </c:if>
    </div>
</section>
<!-- /.content -->
<%@include file="_endPanel.jsp" %>