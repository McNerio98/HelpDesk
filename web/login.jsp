<%-- 
    Document   : index
    Created on : 17-dic-2019, 14:14:27
    Author     : ZEUS
--%>



<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Iniciar Sesion</title>
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/estilo1.css">
        <link rel="stylesheet" href="css/floating-labels.css">
    </head>

    <body>
        <div class="box-border">
            <div class=" bg-color1 text-center">
                <img src="img/Logo.png" alt="">
            </div>    
            <form class="form-sigin" action="${pageContext.servletContext.contextPath}/Login?accion=entrar" method="post">
                <div class="text-center mb-4">
                    <h1 class="h3 mb-3 font-weight-normal">Mesa de Ayuda</h1>
                    <p>Ofrecemos nuestros servicios para<code> gestionar y solucionar</code> toda posible incidencia, si aun no posee una cuenta puede <a href="${pageContext.servletContext.contextPath}/Login?accion=registro">Crear una</a></p>
                </div>

                <div class="form-label-group">
                    <input type="text" id="inputEmail" class="form-control" name="txtCuenta" placeholder="USUARIO O CORREO" required autofocus>
                    <label for="inputEmail">USUARIO O CORREO</label>
                </div>

                <div class="form-label-group">
                    <input type="password" id="inputPassword" class="form-control" name="txtClave" placeholder="Contraseña" required>
                    <label for="inputPassword">Contraseña</label>
                </div>

                <c:if test="${error != null && error == 1}">
                    <p class="text-danger">Usuario o contraseña incorrectos!</p>
                </c:if>
                <div class="checkbox mb-3">
                    <a href="${pageContext.servletContext.contextPath}/Login?accion=recover&opc=b19498e29da193d545f4072e58687845a894bcd6" class="float-left text-muted">¿Olvidaste tu contraseña?</a>
                    </br>
                </div>

                <button class="btn btn-lg btn-dark btn-block" type="submit">Sign in</button>
                <p class="mt-4 mb-2 text-muted text-center">&copy; 2019-2020</p>
            </form>
        </div>  

        <script src="js/jquery-3.4.1.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script>
            var checkbox = document.getElementById("ifcheckbox");
            checkbox.addEventListener("change",function(){
                
                if(checkbox.checked){
                    checkbox.setAttribute("value", "true");
                }else{
                    checkbox.setAttribute("value", "false");
                }
            });
        </script>
    </body>
</html>