
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:if test="${LstGestiones != null}">
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

        <div class="border border-${styleBoostrap} rounded  overflow-hidden mt-2">
            <div class="p-2 text-${styleBoostrap} titleGestion" id="titleGestion">
                <div class="row showHide" >
                    <div class="col-7">
                        <span class="bg-${styleBoostrap} p-1 rounded">${it.type}</span> ${it.title}
                    </div>
                    <div class="col-5">
                        <span class="float-right">${it.fecha}</span>
                    </div>                                    
                </div>
            </div>
            <div class="p-2 border-top descripInc" id="descripInc">
                <p><b>Descripcion: </b> ${it.description}</p>
                <p><i>Adjunto: </i>
                <c:if test="${it.getAttach() != null}">
                    <a href="">${it.Attach}</a>
                </c:if>                 
                <c:if test="${it.getAttach() == null}">
                    <span>-- --</span> 
                </c:if>                    
                / <i>Costo: </i>$ ${it.costo}</p>
            </div>                                                
        </div> 

    </c:forEach>           
</c:if>


<c:if test="${LstGestiones == null}">
    <h6 class="text-muted text-center m-3">
        Esta incidencia no posee aun ningun tipo de <b>Gestion</b>
    </h6>            
</c:if>