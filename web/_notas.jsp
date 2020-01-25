<div class="card-header">
    <h3 class="card-title">Notas y Observaciones</h3>
    <div class="card-tools">
        <span class="badge badge-primary" id="cantidadMsg">0</span>
    </div>
    <!-- /.card-tools -->
</div>
<!-- /.card-header -->
<div class="card-body p-2" style="max-height: 350px !important;overflow-y: auto;" id="contentNotes">

    <c:choose>
        <c:when test="${LstNotes!= null}">
            <c:forEach var="itr" items="${LstNotes}">
                <div class="direct-chat-msg">
                    <div class="direct-chat-infos clearfix">
                        <span class="direct-chat-name float-left">${itr.fullname}</span>
                        <span class="direct-chat-timestamp float-right">${itr.roleName}</span>
                    </div>
                    <!-- /.direct-chat-infos -->
                    <img class="direct-chat-img" src="framework/img/user1-128x128.jpg" alt="message user image">
                    <!-- /.direct-chat-img -->
                    <c:choose>
                        <c:when test="${itr.noteType == 'Rechazo' || itr.noteType == 'Denegacion'}">
                            <div class="direct-chat-text bg-danger">
                            </c:when>
                            <c:otherwise>
                                <div class="direct-chat-text">
                                </c:otherwise>
                            </c:choose>
                            ${itr.description}
                        </div>
                        <!-- /.direct-chat-text -->
                    </div>                                 
                </c:forEach>
            </c:when>
            <c:otherwise>
                <h6 class="text-muted text-center m-3">Actualmente no se ha registrado ninguna nota o observacion de esta incidencia</h6>
            </c:otherwise>
        </c:choose>                                       
    </div>
    <!-- /.card-body -->
    <c:if test="${(ObjectInfo.idTecnico == idUsuario || idRol != 3)&& ObjectInfo.status == 'Finalizada'}">
        <div class="card-footer p-2">
            <form action="${pageContext.servletContext.contextPath}/Procesos?accion=observacion&ic=${idIncidence}" method="POST">
                <div class="input-group">
                    <input type="text" name="txtContentObs" placeholder="Observacion..." class="form-control" required="required" maxlength="500">
                    <span class="input-group-append">
                        <input class="btn btn-primary" type="submit" value="Enviar">
                    </span>
                </div>
            </form>
        </div>                    
    </c:if>

</div>
<!-- /.card -->