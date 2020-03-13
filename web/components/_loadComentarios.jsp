<%-- 
    Document   : _loadComentarios
    Created on : 12-mar-2020, 14:01:04
    Author     : ZEUS
    Description: Son los comentarios de tipo notificacion, estos se mandan por correo cuando se crea uno nuevo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${listComents == null}">
    <div class="cnt-loading">
        <h6 class="text-muted text-center m-3">
            No se han registrado <b>Comentarios</b>
        </h6>            
    </div>
</c:if>


<c:if test="${listComents != null}">
    <div class="direct-chat-messages" style="height: 100% !important; overflow-y: auto;" id="panelComentarios">
        <c:forEach var="com" items="${listComents}">
                <div class="direct-chat-msg">
                  <div class="direct-chat-infos clearfix">
                    <span class="direct-chat-name float-left">${com.titular} / ${com.rolName}</span>
                    <span class="direct-chat-timestamp float-right">${com.fecha}</span>
                  </div>
                  <img class="direct-chat-img" src="${pageContext.servletContext.contextPath}/framework/img/avatar5.png" alt="Message User Image">
                  <div class="direct-chat-text">
                    ${com.mensaje}
                  </div>
                </div>            
        </c:forEach>
    </div>
</c:if>
    
                

    
    