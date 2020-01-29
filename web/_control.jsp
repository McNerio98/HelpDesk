<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--CONTENIDO DE CONTROL-->
<div class="table-responsive-lg">
    <table class="table mt-4 table-sm">
        <thead>
            <tr>
                <th scope="col">Asignado a</th>
                <th scope="col">Estado</th>
                <th scope="col">Inicio</th>
                <th scope="col">Fin Previsto.</th>
                <th scope="col">Fin Real</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="ic" items="${LstControl}">
                <tr>
                    <td>${ic.receptor}</td>
                    <td>${ic.status}</td>
                    <td>
                        <c:if test="${ic.inicioReal == null}">- - -</c:if>
                        <c:if test="${ic.inicioReal != null}">${ic.inicioReal}</c:if>
                    </td>
                    <td>
                        ${ic.finPrev}
                    </td>
                    <td>
                        <c:if test="${ic.finReal == null}">- - -</c:if>
                        <c:if test="${ic.finReal != null}">${ic.finReal}</c:if>
                    </td>                                              
                </tr>
            </c:forEach>

        </tbody>
    </table>
</div> 