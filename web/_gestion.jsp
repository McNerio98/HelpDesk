<c:if test="${ObjectInfo.idTecnico == idUsuario && ObjectInfo.status == 'En Ejecucion'}">
    <div class="row p-2">
        <div class="col-12">
            <a href="${pageContext.servletContext.contextPath}/Procesos?accion=finalizar&idbr=${ibr}&ic=${idIncidence}" class="btn btn-outline-primary float-sm-right ml-2" id="btnFinalizar">Finalizar</a>
            <button class="btn btn-primary float-sm-right ml-2" id="nuevaGestion" data-toggle="modal" data-target="#ModalNuevaGestion">+ Nueva Gestion</button>
        </div>
    </div>                            
</c:if>
<div id="contenidoGestiones">

    <div class="spinner-border" role="status" style="width: 4rem !important; height: 4rem !important;">
        <span class="sr-only">Loading...</span>
    </div>

</div>