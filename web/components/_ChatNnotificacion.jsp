<%-- 
    Document   : _ChatNnotificacion
    Created on : 12-mar-2020, 15:08:57
    Author     : ZEUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<div class="row">
    <div class="col-md-8 border-right pt-4 border-top">
        <div class="pnlComentarios">
            <div class="header">
                <p class="title"><b><i class="fas fa-envelope-open-text icon"></i>Seccion Comentarios</p></b>
                <span>Los comentarios se notifican por correo electronico</span>
            </div>
            <div class="card-body "style="height: 300px;" id="bodyComentarios">
                <div class="cnt-loading">
                    <div class="spinner-border" role="status" style="width: 4rem !important; height: 4rem !important;">
                        <span class="sr-only">Loading...</span>
                    </div>                                        
                </div>
            </div>
            <c:if  test="${(pg.idCreador == idUsuario || pg.idContador == idUsuario)&& pg.estado==3}">
                <div class="card-footer">
                    <form action="#" method="post" id="formComment">
                        <div class="input-group">
                            <input type="text" name="message" placeholder="Escribir..." class="form-control" id="txtContentComment" maxlength="500">
                            <span class="input-group-append">
                                <button type="button" class="btn btn-warning" id="btnSendComment"><i class="fas fa-paper-plane"></i></button>
                            </span>
                        </div>
                    </form>
                </div>            
            </c:if>

        </div>   
    </div>
    <div class="col-md-4 pt-4">
        <div class="card card-sucress cardutline direct-chat direct-chat-success">
            <div class="card-header">
                <h3 class="card-title">Chat </h3>

                <div class="card-tools">
                    <span data-toggle="tooltip" title="Mensajes Nuevos" class="badge bg-success" id="countMessages">0</span>
                </div>
            </div>
            <!-- /.card-header -->
            <div class="card-body" style="height: 300px;" id="bodyMesagges">
                <div class="cnt-loading">
                    <div class="spinner-border" role="status" style="width: 4rem !important; height: 4rem !important;">
                        <span class="sr-only">Loading...</span>
                    </div>                                        
                </div>
            </div>
            <!-- /.card-body -->
            <c:if  test="${(pg.idAutorizador == idUsuario || pg.idCreador == idUsuario)&& pg.estado==2}">
                <div class="card-footer">
                    <form action="#" method="post" id="formMesagges">
                        <div class="input-group">
                            <input type="text" name="message" placeholder="Escribir mensaje..." class="form-control" id="txtContentMsg" maxlength="50">
                            <span class="input-group-append">
                                <button type="button" class="btn btn-success" id="btnSendMsg">Enviar</button>
                            </span>
                        </div>
                    </form>
                </div>                                    
            </c:if>
            <!-- /.card-footer-->
        </div>
    </div>    
</div>