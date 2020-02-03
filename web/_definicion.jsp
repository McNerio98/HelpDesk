<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
    <!--Aqui se valida para mostrar diferentes opciones-->
    <%@include file="_validaciones.jsp"%>
    <c:choose>
        <c:when test="${statusUpdate != null && statusUpdate == 1}">
            <p class="text-success text-center" style="width: 100%;">Actualizacion Confirmada</p>
        </c:when>
        <c:when test="${statusUpdate != null && statusUpdate == 2}">
            <p class="text-danger text-center" style="width: 100%;">Problemas al Actualizar</p>
        </c:when>        
    </c:choose>
</div>
<table class="table mt-4">
    <tbody>
        <tr class="border-none">
            <td class="text-primary"><b>Departamento:</b></td>
            <td>${ObjectInfo.departamento}</td>
        </tr>
        <tr>
            <td class="text-primary"><b>Titulo:</b></td>
            <td>${ObjectInfo.titulo}</td>
        </tr>
    </tbody>
</table>
<p><b class="text-primary">Descripcion: </b>${ObjectInfo.descripcion}</p>
<div class="row mt-4">
    <div class="col-sm-6 col-lg-4">
        <div class="info-box mh-inherit shadow-none p-0">
            <span class="info-box-icon bg-info w-60px"><i class="fas fa-cogs"></i></span>
            <div class="info-box-content">
                <span class="info-box-text">Estado</span>
                <c:choose>
                    <c:when test="${ObjectInfo.status == 'En Ejecucion'}">
                        <span class="info-box-number text-success">${ObjectInfo.status}</span>
                    </c:when>
                    <c:when test="${ObjectInfo.status == 'Rechazada'}">
                        <span class="info-box-number text-danger">${ObjectInfo.status}</span>
                    </c:when>
                    <c:otherwise>
                        <span class="info-box-number">${ObjectInfo.status}</span>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    <div class="col-sm-6 col-lg-4">
        <div class="info-box mh-inherit shadow-none p-0">
            <span class="info-box-icon bg-info w-60px"><i class="fas fa-calendar-alt"></i></span>
            <div class="info-box-content">
                <span class="info-box-text">Tecnico</span>
                <span class="info-box-number" data-toggle="tooltip" data-placement="top" title="${ObjectInfo.nomTecnico}">${ObjectInfo.tecnico}</span>                
            </div>
        </div>
    </div>
    <div class="col-sm-6 col-lg-4">
        <div class="info-box mh-inherit shadow-none p-0">
            <span class="info-box-icon bg-info w-60px"><i class="far fa-clock"></i></span>
            <div class="info-box-content">
                <span class="info-box-text">Creador</span>
                <span class="info-box-number" data-toggle="tooltip" data-placement="top" title="${ObjectInfo.nomCreador}">${ObjectInfo.creador}</span>
            </div>
        </div>
    </div>
</div>                        
<div class="row mt-4">
    <div class="col-sm-6 col-lg-4">
        <div class="info-box mh-inherit shadow-none p-0">
            <span class="info-box-icon bg-info w-60px"><i class="fas fa-user-tag"></i></span>
            <div class="info-box-content">
                <span class="info-box-text">Fecha de Creacion</span>
                <span class="info-box-number">${ObjectInfo.fechaCreacion}</span>
            </div>
        </div>
    </div>
    <div class="col-sm-6 col-lg-4">
        <div class="info-box mh-inherit shadow-none p-0">
            <span class="info-box-icon bg-info w-60px"><i class="fab fa-buffer"></i></span>
            <div class="info-box-content">
                <span class="info-box-text">Clasificacion</span>
                <span class="info-box-number">${ObjectInfo.clasificacion}</span>
            </div>
        </div>
    </div>
    <div class="col-sm-6 col-lg-4">
        <div class="info-box mh-inherit shadow-none p-0">
            <span class="info-box-icon bg-info w-60px"><i class="fas fa-donate"></i></span>
            <div class="info-box-content">
                <span class="info-box-text">Costo Total</span>
                <span class="info-box-number">$ <span>${ObjectInfo.costoTotal}</span></span>
            </div>
        </div>
    </div>
</div>
            
