<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="_startPanel.jsp" %>
<script src="js/departamento.js"></script> 
<style>
    .contenedor{
        display: flex;
        align-items: center;
        height: 100%;
        width: 100%;
    }
    .profile-panel-main{
        width: 100%;
        max-width: 550px;
        margin: auto;
        padding: 10px;
        border-radius: 20px 0px 0px 0px;
        box-shadow:0 .4rem 1rem rgba(0, 0, 0, 0.59) !important;
    }

    .profileImg{
        border-radius: 50%;
        width: 70px;
        height: auto;
        border: 2px solid white;
    }
</style>
<!-- Content Header (Page header) Esto dependera de cada pagina-->
<div class="content-header">
    <div class="container-fluid">
        <div class="row mb-2">
            <div class="col-sm-6">
                <h1 class="m-0 text-dark">Perfil de Usuario</h1>
                <c:if test="${resultado!=null}">
                    <c:if test="${resultado == 1}">
                        <p class="text-success m-0" ><strong>Se actualizo Exitosamente</strong></p>
                    </c:if>
                    <c:if test="${resultado == 2}">
                        <p class="text-danger m-0"><strong>Se produjo un error</strong></p>
                    </c:if>
                </c:if>                
            </div><!-- /.col -->
            <div class="col-sm-6">
                <ol class="breadcrumb float-sm-right">
                    <li class="breadcrumb-item"><a href="Principal">Home</a></li>
                    <li class="breadcrumb-item active">HerpDesk</li>
                </ol>
            </div><!-- /.col -->
        </div><!-- /.row -->
    </div><!-- /.container-fluid -->
</div>
<!-- /.content-header -->



<!-- Main content Esto debe ir en todas-->
<section class="content">
    <div class="container-fluid">
        <!-- Comienda el contenido principal -->

        <div class="contenedor">
            <div class="profile-panel-main bg-light bg-dark">
                <form action="${pageContext.servletContext.contextPath}/Perfil?accion=update" style="width: 100%;" method="POST" id="formProfile">
                    <div class="form-row">
                        <div class="form-group col-md-6" style="text-align: center;">
                            <img src="${pageContext.servletContext.contextPath}/framework/img/user2-160x160.jpg" class="profileImg">
                        </div>
                        <div class="form-group col-md-6">
                            <label for="txtCel">Contacto Telefonico</label>
                            <input type="text" class="form-control" id="txtCel" name="txtCel" value="${Us.telephone}" disabled="true" maxlength="20">
                        </div>
                    </div>                    
                    <div class="form-row">
                        <div class="form-group col-md-6">   
                            <label for="txtNombre">Nombre</label>
                            <input type="text" class="form-control" id="txtNombre"  name="txtNombre" value="${Us.getFirsName()}" disabled="true" maxlength="20">
                        </div>
                        <div class="form-group col-md-6">
                            <label for="txtApellido">Apellido</label>
                            <input type="text" class="form-control" id="txtApellido" name="txtApellido" value="${Us.lastName}" disabled="true" maxlength="20">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="txtEmail">Correo Electronico</label>
                        <input type="text" class="form-control" id="txtEmail" name="txtEmail" value="${Us.email}" disabled="true" maxlength="30">
                    </div>
                    <div class="form-group">
                        <div class="form-check" id="pnlCnk" style="display: none;">
                            <input class="form-check-input" type="checkbox" id="enableChangePass" name="enableChangePass">
                            <label class="form-check-label" for="enableChangePass">
                                Cambiar mi contraseña
                            </label>
                        </div>
                    </div>
                    <div class="form-row" id="pnlPassChange" style="display: none;">
                        <div class="form-group col-md-6">
                            <label for="inputEmail4">Contraseña actual</label>
                            <input type="password" class="form-control" id="txtClave" placeholder="Clave" name="txtClave">
                        </div>
                        <div class="form-group col-md-6">
                            <label for="inputPassword4">Nueva Contraseña</label>
                            <input type="password" class="form-control" id="txtClave2" placeholder="Nueva Clave" name="txtClave2">
                        </div>
                    </div>
                    <button type="submit" class="btn btn-light" id="commitChange" style="display: none;">Confirmar</button>
                    <button type="button" class="btn btn-secondary" id="enabledEdit">Modificar</button>
                    <a href="${pageContext.servletContext.contextPath}/Principal" class="btn btn-secondary" id="abort" style="display:none;">Cancelar</a>
                </form>

            </div>
        </div>


        <!-- /.No quitar esto, copiar en todos los demas -->          
    </div>
    <!-- /.container-fluid -->
</section>

<%@include file="_endPanel.jsp" %>
<script>

    $(document).ready(function () {
        var formulario = $('#formProfile');


        var idUsuario = $('#txtIdUsuario');
        var celular = $('#txtCel');
        var nombre = $('#txtNombre');
        var apellido = $('#txtApellido');
        var correo = $('#txtEmail');
        var clave1 = $('#txtClave');
        var clave2 = $('#txtClave2');
        var btnUpdate = $('#enabledEdit');
        var cnkShow = $('#enableChangePass');
        var pnlPass = $('#pnlPassChange');
        var pnlCnk = $('#pnlCnk');
        var btnSubmit = $('#commitChange');
        var btnAbort = $('#abort');
        


        $(btnUpdate).click(function () {
            $(celular).attr('disabled', false);
            $(nombre).attr('disabled', false);
            $(apellido).attr('disabled', false);
            $(correo).attr('disabled', false);
            $(pnlCnk).css('display', 'flex');
            $(btnSubmit).css('display', 'inline-block');
            $(this).css('display','none');
            $(btnAbort).css('display','inline-block')
            
        });

        $(cnkShow).click(function () {
            if($(this).prop('checked')){
                $(pnlPass).css('display', 'flex');
            }else{
                $(pnlPass).css('display', 'none');
            }
            
        });


        $(formulario).submit(function (e) {
            if ($(celular).val().length == 0) {
                alert('Ingrese un celular valido');
                e.preventDefault();
            }else if($(nombre).val().length == 0){
                alert('Ingrese un Nombre valido');
                e.preventDefault();
            }else if($(apellido).val().length == 0){
                alert('Ingrese apellido valido');
                e.preventDefault();
            }else if($(correo).val().length == 0){
                alert('Ingrese un email valido');
                e.preventDefault();
            }
            
            if($(cnkShow).prop('checked')){
                $(cnkShow).val("trueChange");
            }
        });
        
    });


</script>  
