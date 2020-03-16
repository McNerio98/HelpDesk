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
            <form id="loginForm" class="form-sigin" action="${pageContext.servletContext.contextPath}/Login?accion=entrar" method="post">
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
                <div class="form-label-group" id="nextValue">


                </div>
                <div class="custom-control custom-checkbox mr-sm-2">
                    <input class="custom-control-input" type="checkbox" name="loginReq" id="ifcheckbox" value="true">
                    <label class="custom-control-label" for="ifcheckbox">Iniciar como Requisicion Cheque</label>
                </div>
                <c:if test="${error != null && error == 1}">
                    <p class="text-danger">Usuario o contraseña incorrectos!</p>
                </c:if>
                <c:if test="${error != null && error == 2}">
                    <p class="text-danger">No estas registrado para esta opcion!</p>
                </c:if>
                <div class="checkbox mb-3">
                    <a href="${pageContext.servletContext.contextPath}/Login?accion=recover&opc=b19498e29da193d545f4072e58687845a894bcd6" class="float-left text-muted">¿Olvidaste tu contraseña?</a>
                    </br>
                </div>

                <button id="entrarBtn" class="btn btn-lg btn-dark btn-block" type="submit"><span id="empSelected">Entrar</span></button>
                <p class="mt-4 mb-2 text-muted text-center">&copy; 2019-2020</p>
            </form>
        </div>  



        <!-- Modal para iniciar sescion con empresa  -->
        <div class="modal fade" id="modalemp" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header bg-color1 text-white"> 
                        <span style="font-size: 20px;
                              line-height: 28px;
                              font-weight: 500;
                              flex: 1 1 auto;
                              font-family: 'Google Sans',sans-serif;" class="modal-title" id="exampleModalCenterTitle">
                            Elige la empresa para iniciar sesion</span>

                    </div>
                    <div class="modal-body">
                        <ul class="list-group col-12 list-group-flush" id="listEmps">

                        </ul>
                    </div>
                    <div class="modal-footer">
                        <button onclick="closeMd()" type="button" class="btn btn-light" data-dismiss="modal">Cerrar</button>
                        <button id="selectBtn" type="button" class="btn bg-color1 text-white" data-dismiss="modal">Seleccionar</button>
                    </div>
                </div>
            </div>
        </div>
        <script src="js/jquery-3.4.1.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script>
            
            function closeMd(){
                document.getElementById('entrarBtn').removeAttribute("disabled");
                document.getElementById('empSelected').innerHTML = `Entrar
                        `;
            }
            var cuenta = document.getElementById('inputEmail');
            var pass = document.getElementById('inputPassword');
            var selectBtn = document.getElementById('selectBtn');

            selectBtn.addEventListener('click', function () {
                var radios = document.getElementsByClassName("radios");
                for (var i = 0; i < radios.length; i++) {
                    if (radios[i].checked && i == 0) {
                        document.getElementById("loginForm").action = "${pageContext.servletContext.contextPath}/Login?accion=entrar";
                        document.getElementById('nextValue').innerHTML = ``;
                        document.getElementById('empSelected').innerHTML = "Entrar (" + radios[i].getAttribute("empresa") + ")";
                    } else {
                        if (radios[i].checked) {
                            document.getElementById('nextValue').innerHTML = `
                            <input type="hidden"  name="idempReq"  value="` + radios[i].value + `" >
                            `;
                            document.getElementById('empSelected').innerHTML = " Entrar (" + radios[i].getAttribute("empresa") + ")";
                        }

                    }
                    document.getElementById('entrarBtn').removeAttribute("disabled");
                }
            });


            function verifyData() {
                

                fetch("${pageContext.servletContext.contextPath}/Login?accion=verificarRequisitor&cuenta=" + cuenta.value, {
                    method: 'post',
                    body: 'cuenta=' + cuenta.value,
                    headers: {'Content-type': 'application/x-www-form-urlencoded'}
                })
                        .then((response) => response.text())
                        .then((responseText) => {

                            console.log("Server:" + responseText);
                            var empresas = JSON.parse(responseText);
                            console.log(empresas);
                            if (responseText != "[]") {
                                document.getElementById('listEmps').innerHTML = "";
                                for (var i = 0; i < empresas.length; i++) {
                                    if (i == 0) {
                                        document.getElementById('listEmps').innerHTML += `
                                            <li class="list-group-item">
                                                <div class="custom-control custom-radio">
                                                    <input checked empresa="` + empresas[i].Nombre + `" value="` + empresas[i].idEmpresa + `" type="radio" id="customRadio` + i + `" name="radioemp" class="radios custom-control-input">
                                                    <label class="custom-control-label " for="customRadio` + i + `">` + empresas[i].Nombre + ` (Por defecto)</label>
                                                </div>
                                            </li>
                                        `;
                                    } else {
                                        document.getElementById('listEmps').innerHTML += `
                                            <li class="list-group-item">
                                                <div class="custom-control custom-radio">
                                                    <input empresa="` + empresas[i].Nombre + `" value="` + empresas[i].idEmpresa + `" type="radio" id="customRadio` + i + `" name="radioemp" class="radios custom-control-input">
                                                    <label class="custom-control-label " for="customRadio` + i + `">` + empresas[i].Nombre + `</label>
                                                </div>
                                            </li>
                                        `;
                                    }

                                }
                                document.getElementById("loginForm").action = "${pageContext.servletContext.contextPath}/Login?accion=entrarReq";
                                $('#modalemp').modal('show');
                            }


                        })
                        .catch((error) => {
                            console.log(error);
                            
                        });
            }


            var checkbox = document.getElementById("ifcheckbox");
            checkbox.addEventListener("change", function () {
                if (checkbox.checked) {
                    if (cuenta.value.length > 0 && pass.value.length > 0) {
                        verifyData();

                    }
                }


            });
        </script>
    </body>
</html>