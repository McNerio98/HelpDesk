<%-- 
    Document   : _Details
    Created on : 03-mar-2020, 1:40:43
    Author     : ZEUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<table class="table table-bordered">
    <thead>                  
        <tr>
            <th style="width: 10px">#</th>
            <th>Descripcion</th>
            <th style="width: 40px">Monto</th>
        </tr>
    </thead>
    <tbody>
        <c:set var="contador" value="${1}" />
        <c:forEach var="req" items="${LstDetalles}">
            <tr>
                <td>${contador}</td>
                <td>${req.descripcion}</td>
                <td><span class="badge bg-warning"> $ ${req.monto}</span></td>
            </tr>                          
            <c:set var="contador" value="${contador + 1}" />
        </c:forEach>
    </tbody>

</table>