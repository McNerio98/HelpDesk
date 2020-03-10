$(document).ready(function () {

    var formValido = false;
    var pnlParent = $('#pnlRegistros'); // es el div contenedor 

    // Datos para eliminar detalles 
    var idDetalle;
    var idEnlace;
    var idRequisicion;
    var obj;
    var pathApplication = $('#pathApp').val();


    function DescToMonto(e) {
        let monto = $(e.target).parent().next().children('.txtMonto');
        let desc = $(e.target);

        if ($(desc).val().length > 0) {
            $(monto).prop('required', true);
        } else {
            $(monto).prop('required', false);
        }
    }

    function MontoToDesc(e) {
        let desc = $(e.target).parent().prev().children('.txtDescripcion');
        let monto = $(e.target);

        if ($(monto).val().length > 0) {
            $(desc).prop('required', true);
        } else {
            $(desc).prop('required', false);
        }
    }



    function DescToLink(e) {
        let link = $(e.target).parent().next().children('.txtLink');
        let desc = $(e.target);

        if ($(desc).val().length > 0) {
            $(link).prop('required', true);
        } else {
            $(link).prop('required', false);
        }
    }

    function LinkToDesc(e) {
        let desc = $(e.target).parent().prev().children('.txtNombreAdjunto');
        let link = $(e.target);

        if ($(link).val().length > 0) {
            $(desc).prop('required', true);
        } else {
            $(desc).prop('required', false);
        }
    }

    function contarLinks(e) {
        let cnt = 0;
        let contador = $('#pnlLinks').children().length;
        let arrayRegistro = $('.enlace');
        let nom, link;
        for (let i = 0; i < contador; i++) {
            nom = $(arrayRegistro[i]).find('.txtNombreAdjunto').val();
            link = $(arrayRegistro[i]).find('.txtLink').val();

            if ((nom.length > 0) && (link.length > 0)) {
                cnt++;
            }
        }

        return cnt;
    }

    function setTotalLinks() {
        $('#totalLinks').text(contarLinks());
    }


    function deleteLink() {

        let lgtud = $('#pnlLinks').children().length;
        if (lgtud > 1) {
            $(this).parent().parent().remove();
            contarLinks();
        } else {
            alert("Limite alcanzado");
            console.log("Limite alcanzado");
        }

    }




    function getNewContainer() {
        let structura;
        structura = "<div class='row registro'><input type='hidden' value='0' id='idDet' class='idDet'/><div class='input-group mb-3 col-sm-8'><div class='input-group-prepend'>" +
                "<span class='input-group-text'><i class='fas fa-angle-right'></i></span></div>" +
                "<input type='text' class='form-control txtDescripcion' placeholder='Descripcion' maxlength='200'>" +
                "</div><div class='input-group mb-3 col-sm-3  col-9'><div class='input-group-prepend'>" +
                "<span class='input-group-text'>$</span></div>" +
                "<input type='text' class='form-control txtMonto' step='0.01' placeholder='Monto' pattern='[0.0-9]+'></div>" +
                "<div class='input-group mb-3 col-sm-1 col-3'>" +
                "<button type='button' class='btn btn-secondary btnDeleteRecord' id='btnDeleteRecord'>-</button></div>" +
                "</div>";
        return structura;
    }

    function contarRegistros(e) {
        let cnt = 0;
        let contador = $('#pnlRegistros').children().length;
        let arrayRegistro = $('.registro');

        for (let i = 0; i < contador; i++) {
            desc = $(arrayRegistro[i]).find('.txtDescripcion').val();
            sum = $(arrayRegistro[i]).find('.txtMonto').val();
            if ((desc.length > 0) && (sum.length > 0)) {
                cnt++;
            }

        }

        return cnt;
    }

    function setTotalRegistros() {
        console.log(contarRegistros());
        let nodo = $('#totalRecord');
        $(nodo).text(contarRegistros());
    }


    function contar(e) {
        let montos = $('.txtMonto');
        let total = 0;
        console.log();
        if (parseFloat($(e.target).val()) > 0) {
            for (let i = 0; i < montos.length; i++) {
                if ($(montos[i]).val().length > 0) {
                    total += parseFloat($(montos[i]).val());
                }
            }
            let auxSum = (!(total.toString()).includes(".") ? total + ".00" : total);
            $('#totalSum').text(auxSum);
        } else {
            alert("Valor no valido");
            $(e.target).val("");
        }
    }
    ;


    function deleteRecord() {
        let lgtud = pnlParent.children().length;
        if (lgtud > 1) {
            $(this).parent().parent().remove();
            contar();
            setTotalRegistros();

        } else {
            alert("Limite alcanzado");
        }
    }



    $('#btnAddRegistro').click(function () {
        $(getNewContainer()).appendTo($(pnlParent));
        $('.txtDescripcion').blur(DescToMonto);
        $('.txtMonto').blur(MontoToDesc);
        $('.txtMonto').change(setTotalRegistros);
        $('.txtDescripcion').change(setTotalRegistros);
        $('.txtMonto').change(contar);
        $(".btnDeleteRecord").off("click");
        $('.btnDeleteRecord').click(deleteRecord);
    });

    $('#btnDealRegistro').click(function () {
        let lgtud = pnlParent.children().length;
        if (lgtud > 1) {
            let lastedChild = pnlParent.children()[lgtud - 1];
            $(lastedChild).remove();
        }
    });

    $('.btnDeleteRecord').click(deleteRecord);





    $('.txtDescripcion').blur(DescToMonto);
    $('.txtMonto').blur(MontoToDesc);

    $('.txtMonto').change(setTotalRegistros);
    $('.txtDescripcion').change(setTotalRegistros);

    $('.txtMonto').change(contar);



    function addElementLink() {
        let pnlParent = $('#pnlLinks');

        let parent = $("<div class='row enlace'><input type='hidden' value='0' id='idLink' class='idLink'/></div>");
        let parent1_Def = "<div class='input-group mb-3 col-sm-6'><div class='input-group-prepend'>" +
                "<span class='input-group-text'><i class='fas fa-angle-right'></i></span></div></div>";
        let parent2_Def = "<div class='input-group mb-3 col-sm-5 col-9'><div class='input-group-prepend'>" +
                "<span class='input-group-text'><i class='fas fa-link'></i></span></div></div>";
        let parent3_Def = "<div class='input-group mb-3 col-sm-1 col-3'></div>";


        //Node parents 
        let newParent1 = $(parent1_Def);
        let newParent2 = $(parent2_Def);
        let newParent3 = $(parent3_Def);

        //Node Children 
        let newTxtDescription = $("<input type='text' class='form-control txtNombreAdjunto' placeholder='Nombre Archivo' maxlength='200'>");
        let newTxtLink = $("<input type='text' class='form-control txtLink' placeholder='Link' maxlength='500'>");
        let newbtnDeleteLink = $("<button type='button' class='btn btn-secondary btnDeleteLink' id='btnDeleteLink' >-</button>");

        //Add Events 
        newTxtDescription.on('blur', DescToLink).on('change', setTotalLinks);
        newTxtLink.on('blur', LinkToDesc).on('change', setTotalLinks);
        newbtnDeleteLink.on('click', deleteLink);


        //Add 
        $(newParent1).append(newTxtDescription);
        $(newParent2).append(newTxtLink);
        $(newParent3).append(newbtnDeleteLink);

        //Super add 
        $(parent).append(newParent1);
        $(parent).append(newParent2);
        $(parent).append(newParent3);
        $(pnlParent).append(parent);
    }

    $('#btnAddLink').click(addElementLink);

    $('.txtNombreAdjunto').on('blur', DescToLink).on('change', setTotalLinks);
    $('.txtLink').on('blur', LinkToDesc).on('change', setTotalLinks);
    $('.btnDeleteLink').on('click', deleteLink);



    $('#formRequisicion').submit(function (e) {

        if (contarRegistros() == 0) {
            e.preventDefault();
            alert("Debe llenar los campos");
        } else if ($('#slcPrioridad').val() == 0) {
            e.preventDefault();
            alert("Selecione una Prioridad");
            $('#slcPrioridad').focus();
        } else if ($('#auxDate').val() == 0) {
            alert("Debe seleccionar una fecha de estimacion");
        } else {

            //Aqui validar el formato de la fecha y que sea mayor a ahora 


            //Obtener iterador 
            let contador = $('#pnlRegistros').children().length;
            let arrayRegistro = $('.registro');

            var desc, sum, iden;
            var jsonMtx = [];

            for (let i = 0; i < contador; i++) {
                iden = $(arrayRegistro[i]).find('.idDet').val();
                desc = $(arrayRegistro[i]).find('.txtDescripcion').val();
                sum = $(arrayRegistro[i]).find('.txtMonto').val();
                if (desc.length > 0 && sum.length > 0) {
                    var obj = {id: iden, descripcion: desc, monto: sum};
                    jsonMtx.push(obj);
                }
            }

            $('#JsonReq').val(JSON.stringify(jsonMtx));
            auxDate = document.getElementById('auxDate');
            fechaFinal = document.getElementById('dateFechaFinal');
            fechaFinal.value = formatear(auxDate.value);

            //Si hay links se mandan

            if (contarLinks() > 0) {
                let contador = $('#pnlLinks').children().length;
                let arrayRegistro = $('.enlace');

                var desc, link, id, idReq;
                var jsonMtxLinks = [];

                for (let i = 0; i < contador; i++) {
                    idLink = $(arrayRegistro[i]).find('.txtId').val();
                    desc = $(arrayRegistro[i]).find('.txtNombreAdjunto').val();
                    link = $(arrayRegistro[i]).find('.txtLink').val();
                    if (desc.length > 0 && link.length > 0) {

                        var obj = {idEnlace: idLink, descripcion: desc, enlace: link, idRequisicion: 0};
                        jsonMtxLinks.push(obj);
                    }
                }

                $('#JsonLinks').val(JSON.stringify(jsonMtxLinks));

            }

        }

    });

    $('.btnDeleteFromDB').click(function () {
        idDetalle = $(this).val();
        idRequisicion = $('#idRequisicion').val();
        obj = $(this).parent().parent();

        let lgtud = $('.btnDeleteFromDB').length;

        if (lgtud > 1) {
            $('.alertDeleteRecord').modal();
        } else {
            $('#mesanjeByDeleteStatus').text("Se debe contender al menos 1 registro hasta Confirmar");
        }
    });

    $('.btnDeleteFromDBLink').click(function () {
        idEnlace = $(this).val();
        idRequisicion = $('#idRequisicion').val();
        obj = $(this).parent().parent();

        let lgtud = $('.btnDeleteFromDBLink').length;

        if (lgtud > 1) {
            $('.alertDeleteLink').modal();
        } else {
            $('#mesanjeByDeleteStatus').text("Se debe contender al menos 1 registro hasta Confirmar");
        }
    });




    $('#confirmDelete').click(function () {

        $.ajax({
            type: 'POST',
            data: {idReq: idRequisicion, idenDetalle: idDetalle},
            url: pathApplication + '/ProcesosReq?accion=deleteDetalle',
            success: function (result) {
                if (result == 'true') {
                    $('.alertDeleteRecord').modal('hide');
                    $(obj).remove();
                    contar();
                    setTotalRegistros();
                } else {
                    $('#mesanjeByDeleteStatus').text("Error al Eliminar");
                }
            }
        });
    });

    $('#confirmDelete').click(function () {

        $.ajax({
            type: 'POST',
            data: {idReq: idRequisicion, idenDetalle: idDetalle},
            url: pathApplication + '/ProcesosReq?accion=deleteEnlace',
            success: function (result) {
                if (result == 'true') {
                    $('.alertDeleteRecord').modal('hide');
                    $(obj).remove();
                    contar();
                    setTotalRegistros();
                } else {
                    $('#mesanjeByDeleteStatus').text("Error al Eliminar");
                }
            }
        });
    });

});