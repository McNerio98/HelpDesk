<%-- 
    Document   : registro
    Created on : 20-dic-2019, 16:34:23
    Author     : ZEUS
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Registrarse</title>
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/estilo1.css">
        <link rel="stylesheet" href="css/estilo1.css">
        <link rel="stylesheet" href="plugins/fontawesome-free/css/all.min.css">
    </head>
    <body>
        <nav class="navbar1 d-none d-md-block">
            <div class="container">
                <div class="row">
                    <div class="col-md-6 columna">
                        <img src="img/Logo.png">
                        Dentro de los campos deberas selecionar el departamento donde te han asignado, se creara una solicitud la cual debe aceptar tu encargado.
                    </div>
                    <div class="col-md-6">
                        <div class="bgimg-1">
                            <div class="bg-opa">

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </nav>
        <div class="container">
            <form id="registroForm" action="${pageContext.servletContext.contextPath}/Login?accion=nuevo" class="mt-5" method="post" onsubmit="return validar();">
                <div class="row">
                    <div class="col-md-6">
                        <h1>Registrarse</h1>
                        <h3>Llene todos los campos</h3>
                        <div class="form-row mt-3">
                            <div class="col">
                                <input type="text" class="form-control" placeholder="Nombres" name="txtNombres" required="true" maxlength="20"> 
                            </div>
                            <div class="col">
                                <input type="text" class="form-control" placeholder="Apellidos" name="txtApellidos" required="true" maxlength="20">
                            </div>
                        </div>                      
                        <div class="form-group mt-3">
                            <label for="exampleInputEmail1">Nombre del Usuario</label>
                            <input type="text" class="form-control" id="txtUser" style="text-transform:uppercase;" name="txtUser" required="true" maxlength="20"> 
                            <small id="emailHelp" class="form-text text-muted">Para iniciar sesion tambien podras utilizar tu correo electronico</small>
                            <p class="text-danger d-none" id="userExist">El Usuario ya existe</p>
                        </div>

                        <hr/>
                        <span class="text-danger" id="alerEmpresa"></span>
                        <div class="form-row mt-3">
                            <div class="form-group col-md-6" id="pnlSelectEmpresa" style="display:none;">
                                <label for="exampleFormControlSelect1" data-toggle="tooltip" data-placement="top" title="Selecciona la empresa y el departamento en el perteneces">Empresa por defecto <i class="far fa-question-circle"></i></label>
                                <select class="form-control" id="empresa" name="empresa">
                                    <option value="0">-- SELECIONAR EMPRESA --</option>
                                    <c:forEach var="Iterador" items="${EmpresasList}">
                                        <option value="${Iterador.getIdEmpresa()}">${Iterador.getNombre()}</option>
                                    </c:forEach> 
                                </select>
                            </div>                                                         
                            <div class="form-group col-md-12" id="pnlSelectDepto">
                                <label for="exampleFormControlSelect1">Departamento Asignado</label>
                                <select class="form-control" id="depto" name="depto">
                                    <option value="0">-- SELECIONAR DEPTTO --</option>
                                    <c:forEach var="Iterador" items="${DeptosList}">
                                        <option value="${Iterador.getIdDepto()}">${Iterador.getDeptoName()}</option>
                                    </c:forEach>   
                                </select>
                            </div>
                            <div class="form-group col-md-12" id="pnlSelectEmpresa2" style="display:none;">
                                <label for="exampleFormControlSelect1"  data-toggle="tooltip" data-placement="top" title="Puedes agregar mas empresas si necesitas hacer solicitudes de requisiciones para las que selecciones. Para seleccionar usa (CTRL + Clic)">Agregar mas empresas <i class="far fa-question-circle"></i></label>
                                <select class="form-control" id="empresa2" name="empresa2" multiple size="3">
                                    <option value="0">-- SELECIONAR EMPRESA --</option>
                                    <c:forEach var="Iterador" items="${EmpresasList}">
                                        <option value="${Iterador.getIdEmpresa()}">${Iterador.getNombre()}</option>
                                    </c:forEach> 
                                </select>

                            </div>
                        </div>
                        <div class="custom-control custom-checkbox mr-sm-2 mb-3">


                            <input class="custom-control-input" type="checkbox" name="requisicion" id="ifcheckbox" value="true">
                            <label class="custom-control-label" for="ifcheckbox">Requisicion Cheque</label>  
                        </div>                        

                    </div>
                    <div class="col-md-6 mt-5">
                        <label for="exampleFormControlInput1">Credenciales de Seguridad</label>
                        <div class="form-row">
                            <div class="col">
                                <input type="password" class="form-control" placeholder="Contraseña" name="txtPassword" id="txtPassword" required>
                            </div>
                            <div class="col">
                                <input type="password" class="form-control" placeholder="Repita Contraseña" name="txtPassword2" id="txtPassword2" required>
                            </div>
                        </div>                     
                        <div class="form-group mt-3">
                            <label for="exampleFormControlInput1">Correo Electronico</label>
                            <input type="email" class="form-control" id="txtEmail" placeholder="name@example.com" name="txtEmail" required="true" maxlength="60">
                            <p class="text-danger d-none" id="emailExist">El correo ya esta en uso!</p>                         
                        </div>                    
                        <div class="form-group mt-3">
                            <label for="exampleFormControlInput1">Numero de Contacto</label>
                            <input type="text" class="form-control" id="" name="txtTelephone" required="true" maxlength="20">
                        </div>
                        <button type="submit" class="btn btn-primary" id="btnSubmit">Aceptar</button>
                        <a href="${pageContext.servletContext.contextPath}/Login" class="btn btn-secondary">Cancelar</a> 
                    </div>                
                </div>
            </form>
        </div>

        <!--Modal para informacion-->
        <div id="modalsmall" class="modal fade bd-example-modal-sm" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog  modal-sm modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalCenterTitle">Advertencia</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body" id="info">



                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-dismiss="modal">Entendido</button>
                    </div>
                </div>
            </div>
        </div>


        <script src="js/jquery-3.4.1.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script>
                var btnAcept = document.getElementById("btnSubmit");
                var combobox = document.getElementById("empresa");
                var comboboxdep = document.getElementById("depto");
                var comboboxemps = document.getElementById("empresa2");
                var optionsemps = comboboxemps.getElementsByTagName("option");
                var registroF = document.getElementById('registroForm');

                comboboxemps.addEventListener('blur', function () {
                    if (comboboxemps.value != 0) {
                        $('#modalsmall').modal('show');
                        document.getElementById('info').innerHTML = `
                                
                                ¿Estas seguro de agregar estas empresas?
                                Puedes revisar para confirmar tus empresas agregadas.
    
                            `;
                    }
                });

                $(function () {
                    $('[data-toggle="tooltip"]').tooltip()
                })
                var tags;
                var jsonTags = [];
                window.onload = function () {
                    tags = comboboxdep.getElementsByTagName("option");
                    for (var i = 0; i < tags.length; i++) {
                        let jsonTemp = {id: tags[i].value, nombre: tags[i].label}
                        jsonTags.push(jsonTemp);
                    }
                }
                function validar() {
                    var clave1 = document.getElementById("txtPassword");
                    var clave2 = document.getElementById("txtPassword2");
                    var depto = document.getElementById("depto");
                    if (clave1.value != clave2.value) {
                        alert("Las Contraseñas no coinciden");
                        return false;
                    } else if (parseInt(depto.value) == 0) {
                        alert("No ha Seleciona el Departamento");
                        return false;
                    }


                    return true;
                }

                $(document).ready(function () {
                    document.getElementById("empresa2").disabled = "true";
                    $('#txtUser').change(function () {
                        $.ajax({
                            type: 'POST',
                            data: {usName: $(this).val()},
                            url: '${pageContext.servletContext.contextPath}/Login?accion=consultar_usuario',
                            success: function (result) {
                                if (result == 'true') {
                                    $('#userExist').removeClass('d-none');
                                    $("#btnSubmit").prop('disabled', true);
                                } else {
                                    $('#userExist').addClass('d-none');
                                    $("#btnSubmit").prop('disabled', false);
                                }
                            }
                        });
                    });

                    $('#txtEmail').change(function () {
                        $.ajax({
                            type: 'POST',
                            data: {usEmail: $(this).val()},
                            url: '${pageContext.servletContext.contextPath}/Login?accion=consultar_correo',
                            success: function (result) {
                                if (result == 'true') {
                                    $('#emailExist').removeClass('d-none');
                                    $("#btnSubmit").prop('disabled', true);
                                } else {
                                    $('#emailExist').addClass('d-none');
                                    $("#btnSubmit").prop('disabled', false);
                                }
                            }
                        });
                    });

                    $('#ifcheckbox').on('change', function () {
                        if ($(this).prop('checked')) {
                            $('#modalsmall').modal('show');
                            document.getElementById('info').innerHTML = `
                                
                                La siguiente opcion es para registrarse como usuario requisicion,
                                lo cual implica solicitar unicamente requisiciones de cheque y no
                                para la gestion de incidencias.
    
                            `;
                            document.getElementById("btnSubmit").setAttribute("disabled", "true");
                            comboboxdep.setAttribute("disabled", "true");
                            $('#pnlSelectEmpresa').css('display', 'block');
                            $('#pnlSelectEmpresa2').css('display', 'block');
                            $('#pnlSelectDepto').removeClass('col-md-12').addClass('col-md-6');

                        } else {
                            document.getElementById("empresa2").disabled = "true";
                            document.getElementById("empresa2").value = 0;
                            document.getElementById("btnSubmit").removeAttribute("disabled");
                            comboboxdep.innerHTML = "";
                            for (var i = 0; i < jsonTags.length; i++) {
                                comboboxdep.innerHTML += `
                                    <option value="` + jsonTags[i].id + `">` + jsonTags[i].nombre + `</option>
                                `;
                            }

                            combobox.getElementsByTagName("option")[0].selected = "selected";
                            document.getElementById("alerEmpresa").innerHTML = "";
                            comboboxdep.removeAttribute("disabled");
                            $('#pnlSelectEmpresa').css('display', 'none');
                            $('#pnlSelectEmpresa2').css('display', 'none');
                            $('#pnlSelectDepto').removeClass('col-md-6').addClass('col-md-12');
                        }
                    })
                });
        </script>
        <script>




            combobox.addEventListener("change", function () {

                for (var i = 0; i < optionsemps.length; i++) {
                    if (combobox.value == optionsemps[i].value) {
                        optionsemps[i].style.display = "none";
                    } else {
                        optionsemps[i].style.display = "block";
                    }
                }

                document.getElementById("empresa2").removeAttribute("disabled");
                comboboxdep.removeAttribute("disabled");
                document.getElementById("btnSubmit").removeAttribute("disabled");
                document.getElementById("alerEmpresa").innerHTML = "";


                fetch("${pageContext.servletContext.contextPath}/Login?accion=getDeptosByEmpresa&id=" + combobox.value, {
                    method: "POST"
                })
                        .then((response) => response.text())
                        .then((responseText) => {
                            var json = JSON.parse(responseText);
                            console.log(json);
                            comboboxdep.innerHTML = "";
                            if (json != null) {
                                comboboxdep.removeAttribute("disabled");
                                document.getElementById("btnSubmit").removeAttribute("disabled");
                                for (var i = 0; i < json.length; i++) {
                                    comboboxdep.innerHTML += `
                                    <option value=` + json[i].idDepto + `>
                                       ` + json[i].deptoName + `
                                    </option>
                                `;
                                }
                            } else {
                                comboboxdep.innerHTML = "";
                                comboboxdep.setAttribute("disabled", "true");
                                document.getElementById("btnSubmit").setAttribute("disabled", "true");
                                document.getElementById("alerEmpresa").innerHTML = "Esta empresa aun no tiene departamentos asignados";
                            }



                        })
                        .catch((error) => {
                            console.log(error);
                        });

            });
        </script>
    </body>
</html>
