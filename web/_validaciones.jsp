<c:choose>
    <c:when test="${ObjectInfo.idTecnico == idUsuario && ObjectInfo.status == 'Asignada'}">
        <div class="col-12">
            <div class=" float-sm-right mt-2">
                <a class="btn btn-primary" id="linkAceptar"  href="${pageContext.servletContext.contextPath}/Procesos?accion=aceptar&idbr=${ibr}&ic=${idIncidence}">Aceptar</a>
                <button type="button" class="btn btn-danger mr-2" data-toggle="modal" data-target="#modalrechazo">Rechazar</button>

                <!-- Modal -->
                <div class="modal fade" id="modalrechazo" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel">NOTA DE RECHAZO</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>

                            <div class="modal-body">
                                <form action="${pageContext.servletContext.contextPath}/Procesos?accion=rechazar&idbr=${ibr}&ic=${idIncidence}" method="POST">
                                    <div class="form-group">
                                        <label for="exampleFormControlTextarea1">Especifique motivo de rechazo</label>
                                        <textarea class="form-control" id="txtContenido" name="txtContenido" rows="3" required="required"></textarea>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                                        <input class="btn btn-primary" type="submit" value="Enviar">
                                    </div>                                                
                                </form>
                            </div>

                        </div>
                    </div>
                </div>
                <!-- / Modal -->
            </div>                                
        </div>                                    
    </c:when>
    <c:when test="${ObjectInfo.idDeptoTecnico == idDepUser && ObjectInfo.status == 'En Solicitud' && Rol == 2}">
        <div class="col-12">
            <div class=" float-sm-right mt-2">
                <a class="btn btn-primary" href="Procesos?accion=conceder&idbr=${ibr}&iddc=${ObjectInfo.idDeptoTecnico}&ic=${idIncidence}">Conceder</a>
                <button type="button" class="btn btn-danger mr-2" data-toggle="modal" data-target="#modaldenegar">Denegar</button>

                <!-- Modal -->
                <div class="modal fade" id="modaldenegar" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel">DENEGACION DE PERMISO</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>

                            <div class="modal-body">
                                <form action="${pageContext.servletContext.contextPath}/Procesos?accion=denegar&idbr=${ibr}&ic=${idIncidence}&iddc=${ObjectInfo.idDeptoTecnico}" method="POST">
                                    <div class="form-group">
                                        <label for="exampleFormControlTextarea1">Especifique motivo de la denegacion</label>
                                        <textarea class="form-control" id="txtContenido" name="txtContenido" rows="3" required="required" maxlength="500"></textarea>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                                        <input class="btn btn-primary" type="submit" value="Enviar">
                                    </div>                                                
                                </form>
                            </div>

                        </div>
                    </div>
                </div>
                <!-- / Modal -->

            </div>                                
        </div>                                     
    </c:when>
    <c:when test="${ObjectInfo.creador == Usuario && (ObjectInfo.status == 'Rechazada' || ObjectInfo.status == 'Denegada')}">
        <div class="col-12">
            <div class=" float-sm-right mt-2">
                <a class="btn btn-primary" href="Incidencias?accion=update&ic=${idIncidence}">Reasignar</a>
            </div>                                
        </div>                                     
    </c:when>                                

</c:choose>