
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${listMessages != null}">
    <div id="panelChat" class="direct-chat-messages" style="height: 100% !important; overflow: overflow-y;">
        <c:forEach var="com" items="${listMessages}">
            <!--Definiendo el estilo-->
            <c:choose>
                <c:when test="${com.rol == 6}">
                    <c:set var="flot" scope="page" value="right"/>
                </c:when>
                <c:otherwise>
                    <c:set var="flot" scope="page" value=""/>
                </c:otherwise>                
            </c:choose>


            <div class="direct-chat-msg ${flot}">

                <div class="direct-chat-infos clearfix">
                    <c:choose>
                        <c:when test="${com.rol == 6}">
                            <span class="direct-chat-name float-right">${com.titular}</span>
                            <span class="direct-chat-timestamp float-left">${com.fecha}</span>
                        </c:when>
                        <c:otherwise>
                            <span class="direct-chat-name float-left">${com.titular}</span>
                            <span class="direct-chat-timestamp float-right">${com.fecha}</span>    
                        </c:otherwise>
                    </c:choose>
                </div>

                <img class="direct-chat-img" src="${pageContext.servletContext.contextPath}/framework/img/avatar5.png" alt="Message User Image">

                <div class="direct-chat-text">
                    ${com.mensaje}
                </div>
            </div>
        </c:forEach>
    </div>
</c:if>

<c:if test="${listMessages == null}">
    <div class="cnt-loading">
        <h6 class="text-muted text-center m-3">
            Actualmente no se ha realizado <b>Comentarios</b>
        </h6>            
    </div>
</c:if>