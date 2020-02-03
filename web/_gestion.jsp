<c:if test="${ObjectInfo.idTecnico == idUsuario && ObjectInfo.status == 'En Ejecucion'}">
    <div class="row p-2">
        <div class="col-12">
            <a href="${pageContext.servletContext.contextPath}/Procesos?accion=finalizar&idbr=${ibr}&ic=${idIncidence}" class="btn btn-outline-primary float-sm-right ml-2" id="btnFinalizar">Finalizar</a>
            <button class="btn btn-primary float-sm-right ml-2" id="nuevaGestion" data-toggle="modal" data-target="#ModalNuevaGestion">+ Nueva Gestion</button>
        </div>
    </div>                            
</c:if>
<div id="contenidoGestiones">

    <div class="p-3 text-center" id="pnlLoad" style="display:none;">
        <img src="${pageContext.servletContext.contextPath}/img/load.gif" style="width: 100px;height: auto;">
    </div>

</div>