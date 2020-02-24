
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${listMessages != null}">
    <div class="direct-chat-messages">
        <c:forEach var="it" items="${LstGestiones}">
            <!--Definiendo el estilo-->
            <c:choose>
                <c:when test="${it.type == 'Correo'}">
                    <c:set var="styleBoostrap" scope="application" value="dark"/>
                </c:when>
                <c:when test="${it.type == 'Solicitud'}">
                    <c:set var="styleBoostrap" scope="application" value="success"/>
                </c:when>
                <c:when test="${it.type == 'Procedimiento'}">
                    <c:set var="styleBoostrap" scope="application" value="primary"/>
                </c:when>                
            </c:choose>

            <div class="direct-chat-msg">
                <div class="direct-chat-infos clearfix">
                    <span class="direct-chat-name float-left">NerioNerio Pierce</span>
                    <span class="direct-chat-timestamp float-right">23 Jan 2:00 pm</span>
                </div>

                <img class="direct-chat-img" src="framework/img/user1-128x128.jpg" alt="Message User Image">

                <div class="direct-chat-text">
                    Is this template really for free? That's unbelievable!
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