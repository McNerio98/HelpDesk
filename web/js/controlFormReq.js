$(document).ready(function () {

    var formValido = false;
    var pnlParent = $('#pnlRegistros'); // es el div contenedor 

    // Datos para eliminar detalles 
    var idDetalle;
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

        for (let i = 0; i < montos.length; i++) {
            if ($(montos[i]).val().length > 0) {
                total += parseFloat($(montos[i]).val());
            }
        }
        let auxSum = (!(total.toString()).includes(".") ? total + ".00" : total)
        $('#totalSum').text(auxSum);
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


    $('#formRequisicion').submit(function (e) {

        if (contarRegistros() == 0) {
            e.preventDefault();
            alert("Debe llenar los campos");
        } else if ($('#slcPrioridad').val() == 0) {
            e.preventDefault();
            alert("Selecione una Prioridad");
            $('#slcPrioridad').focus();
        } else {
            //Obtener iterador 
            let contador = $('#pnlRegistros').children().length;
            let arrayRegistro = $('.registro');

            var desc, sum, id;
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

        }

    });

    $('.btnDeleteFromDB').click(function () {
        idDetalle = $(this).val();
        idRequisicion = $('#idRequisicion').val();
        obj = $(this).parent().parent();
        
        let lgtud = pnlParent.children().length;
        if (lgtud > 1) {
            $('.alertDeleteRecord').modal();            
        } else {
            $('#mesanjeByDeleteStatus').text("Se debe contender al menos 1 registro");
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


});