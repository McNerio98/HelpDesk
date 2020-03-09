<%-- 
    Document   : _listEnlaces
    Created on : 03-09-2020, 10:15:51 AM
    Author     : McNerio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${LstEnlaces == null}">
    <p class="text-danger p-3">ERROR al extraer enlaces</p>      
</c:if>

<c:if test="${LstEnlaces != null && LstEnlaces.size() == 0}">
    <p class="text-gray p-3">No se han anexado enlaces</p>    
</c:if>

<c:if test="${LstEnlaces != null && LstEnlaces.size() > 0}">
    <table class="table table-bordered">
        <thead>                  
            <tr>
                <th style="width: 10px">#</th>
                <th>Descripcion / Enlace</th>
            </tr>
        </thead>
        <tbody>
            <c:set var="contador2" value="${1}" />
            <c:forEach var="lnk" items="${LstEnlaces}">
                <tr>
                    <td>${contador}</td>
                    <td>
                        <a href="${lnk.enlace}" target="_blank">
                            ${lnk.descripcion}
                        </a>
                    </td>
                </tr>                          
                <c:set var="contador2" value="${contador2 + 1}" />
            </c:forEach>
        </tbody>

    </table>    
</c:if>


