<%-- 
    Document   : registro
    Created on : 20-dic-2019, 16:34:23
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
        <title>Reset password</title>
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="plugins/fontawesome-free/css/all.min.css">
        <style>
            body {
                background-image: url("img/bg.jpg");
                background-repeat: no-repeat;
                background-attachment: fixed;
                background-size: cover;
            }
        </style>
    </head>
    <body>

        <div class="container-fluid">
            <div class="row">
                <div class="col-12">

                    <c:if test="${recoverCase == 1}">
                        <div class="d-flex justify-content-center">
                            <div class="card text-center" style="margin-top: 100px; padding: 50px; border-radius: 10px">
                                <div class="card-body">
                                    <form action="${pageContext.servletContext.contextPath}/Login?accion=recover&opc=49381d2042ac93bb15942ecc3b2c1830d29ecdfa" method="post">
                                        <h5 class="card-title">¿Olvidaste tu contraseña?</h5>
                                        <p class="card-text">
                                            Ingrese el correo que utiliza para iniciar sesión en su cuenta de Helpdesk.
                                        <p class="text-danger" id="alert"></p>
                                        </p>
                                        <div class="form-group">

                                            <input type="text" class="form-control text-center" id="email">
                                        </div>
                                        <center>
                                            <button id="submit" type="submit" class="btn btn-primary">Submit</button>
                                        </center>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${recoverCase == 2}">
                        <div class="d-flex justify-content-center">
                            <div class="card text-center" style="margin-top: 150px; padding: 50px; border-radius: 10px">
                                <div class="card-body">
                                    <form action="${pageContext.servletContext.contextPath}/Login?accion=recover&opc=49381d2042ac93bb15942ecc3b2c1830d29ecdfa" method="post">
                                        <h5 class="card-title">Consultar su correo electrónico</h5>
                                        <p class="card-text" style="max-width: 300px; text-align: justify;">
                                            Si tenemos una cuenta para el nombre de usuario o la dirección 
                                            de correo electrónico <span class="text-muted">(${emailUser})</span> que proporcionó, le hemos enviado un enlace por correo
                                            electrónico para restablecer su contraseña.
                                        </p>
                                    </form>
                                </div>
                            </div>

                        </div>
                    </c:if>
                    
                    <c:if test="${recoverCase == 3}">
                        <div class="d-flex justify-content-center">
                            <div class="card text-center" style="margin-top: 150px; padding: 50px; border-radius: 10px">
                                <div class="card-body">
                                    <form action="${pageContext.servletContext.contextPath}/Login?accion=saveNewPas" method="post">
                                        <h5 class="card-title">Nueva contraseña.</h5>
                                        <p class="card-text" style="max-width: 300px;">
                                            Digite su nueva credencial  <span class="text-muted">${emailUser}</span>.
                                        </p>
                                        <div class="form-group">
                                            <input type="hidden" value="${emailUser}" name="usermail">
                                            <input type="password" class="form-control" id="password1" name="newpass">
                                        </div>
                                        <button  type="submit" class="btn btn-primary">Guardar cambios</button>
                                    </form>
                                </div>
                            </div>

                        </div>
                    </c:if>
                    
                    
                    
                     <c:if test="${recoverCase == 4}">
                        <div class="d-flex justify-content-center">
                            <div class="card text-center" style="margin-top: 150px; padding: 50px; border-radius: 10px">
                                <div class="card-body">
                                    <form action="#" method="post">
                                        <h5 class="card-title">Ups! link corrupto.</h5>
                                        <center><i class="fas fa-unlink"></i></center>
                                        <p class="card-text" style="max-width: 300px;">
                                            Parece que el link esta corrupto, intenta volver
                                            confirmar tu correo.
                                        </p>
                                        
                                    </form>
                                </div>
                            </div>

                        </div>
                    </c:if>
                    
                    <c:if test="${recoverCase == 5}">
                        <div class="d-flex justify-content-center">
                            <div class="card text-center" style="margin-top: 150px; padding: 50px; border-radius: 10px">
                                <div class="card-body">
                                    <form action="#" method="post">
                                        <h5 class="card-title">Tu credencial ha sido actualizada</h5>
                                        
                                        <p class="card-text" style="max-width: 300px;">
                                            Good for you!
                                        </p>
                                        <center>
                                            <a href="${pageContext.servletContext.contextPath}/Login" class="btn btn-primary">
                                                Continuar
                                            </a>
                                        </center>
                                        
                                    </form>
                                </div>
                            </div>

                        </div>
                    </c:if>

                </div>
            </div>
        </div>
        <script>
            document.getElementById("submit").addEventListener('click', function (e) {
                e.preventDefault();
                var email = document.getElementById('email');
                document.getElementById("submit").setAttribute("disabled",true);
                document.getElementById("submit").innerHTML = `
                                <span class="float-left">Verificando...</span>
                                <div class="float-left d-flex justify-content-center">
                                    <div class="spinner-border spinner-border-sm" role="status">
                                      <span class="sr-only">Loading...</span>
                                    </div>
                                  </div>
                                `;
                console.log(email.value);
                fetch("${pageContext.servletContext.contextPath}/Login?accion=recover&opc=49381d2042ac93bb15942ecc3b2c1830d29ecdfa&email=" + email.value, {

                })
                        .then((response) => response.text())
                        .then((responseText) => {
                            console.log(responseText);
                            if (responseText === "true") {


                                window.location.href = "${pageContext.servletContext.contextPath}/Login?accion=recover&opc=56d3c9490be2608ac36f5a4805bfec2f21f7f982";


                            } else if (responseText === "false") {
                                document.getElementById("submit").setAttribute("disabled",false);
                                document.getElementById("submit").innerHTML = `Submit`;
                                document.getElementById('alert').innerHTML = "Debe ingresar su correo";
                            } else if(responseText === "null") {
                                document.getElementById("submit").setAttribute("disabled",false);
                                document.getElementById("submit").innerHTML = `Submit`;
                                document.getElementById('alert').innerHTML = "No se encontro ningun usuario asociado con este correo";
                            }else{
                                document.getElementById("submit").setAttribute("disabled",false);
                                document.getElementById("submit").innerHTML = `Submit`;
                                document.getElementById('alert').innerHTML = "Error de Servidor intentelo mas tarde";
                            }





                        })
                        .catch((error) => {
                            document.getElementById('alert').innerHTML = "Error! " + error;
                        });

            });
        </script>
    </body>
</html>