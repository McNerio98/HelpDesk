/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    //VARIABLES GLOBALES
    var contexto = $('#contextoApp').val();
    var idRequisicion = $('#idRequisicion').val();
    var btnPDFModal = $('#btnPDFModal');
    var panelPDFModal = $('#pnlLoadPDF');
    var modalForPDF = $('#modalForPDF');





    //DECLARACION DE FUNCIONES

    function reloadPage() {
        window.location.reload(true);
    }
    ;

    function PDFModal() {
        $(panelPDFModal).attr('src', contexto + '/RequisicionPDF?idRequisicion=' + idRequisicion);
        $(modalForPDF).modal('show');
        $("#txtMotionPDF").text('Abrir PDF');
        $(btnPDFModal).off("click");

        $(btnPDFModal).on("click", function () {
            $(modalForPDF).modal('show');
        });

    }

    //funciones para comentarios, notificaciones
    function refreshComentarios() {
        $.ajax({
            type: 'POST',
            data: {idReq: idRequisicion},
            url: contexto + '/RequisicionInfo?accion=getAllComents',
            success: function (result) {
                $('#bodyComentarios').html(result);
                $('#panelComentarios').scrollTop($('#panelComentarios').prop('scrollHeight'));
            }
        });
    }


    function sendComment() {
        let contentMsg = $('#txtContentComment').val();
        $.ajax({
            type: 'POST',
            data: {msg: contentMsg, tipo: '2', idReq: idRequisicion},
            url: contexto + '/ProcesosReq?accion=newMessage',
            success: function (result) {
                if (result == 'true') {
                    refreshComentarios();
                    $('#txtContentComment').val("");
                } else {
                    reloadPage();
                }
            }
        });
    }

    function enviarNewComentario() {
        let contentMsg = $('#txtContentComment').val();
        if (contentMsg.length == 0) {
            alert("No hay mensajes");
        } else {
            sendComment();
        }
    }

    //funciones para chat
    function refreshMessages() {
        $.ajax({
            type: 'POST',
            data: {idReq: idRequisicion},
            url: contexto + '/RequisicionInfo?accion=getAllMsg',
            success: function (result) {
                $('#bodyMesagges').html(result);
                $('#panelChat').scrollTop($('#panelChat').prop('scrollHeight'));
                $('#countMessages').text($('.typeChats').length);

            }
        });
    }

    function sendMessage() {
        let contentMsg = $('#txtContentMsg').val();
        $.ajax({
            type: 'POST',
            data: {msg: contentMsg, tipo: '1', idReq: idRequisicion},
            url: contexto + '/ProcesosReq?accion=newMessage',
            success: function (result) {
                if (result == 'true') {
                    refreshMessages();
                    $('#txtContentMsg').val("");
                } else {
                    reloadPage();
                    console.log("ERROR en envio de mensaje");
                }
            }
        });
    }

    function enviarNewMesagge() {
        let contentMsg = $('#txtContentMsg').val();
        if (contentMsg.length == 0) {
            alert("No hay mensajes");
        } else {
            sendMessage();
        }
    }


    //funciones cargar links

    //funciones cargar detalles
    function refreshDetail() {
        $.ajax({
            type: 'POST',
            data: {idReq: idRequisicion},
            url: contexto + "/RequisicionInfo?accion=loadDetalles",
            success: function (result) {
                $('#pnlRegistros').html(result);
            }
        });
    }
    ;

    function refreshLinks() {
        $.ajax({
            type: 'POST',
            data: {idReq: idRequisicion},
            url: contexto + "/RequisicionInfo?accion=loadLinks",
            success: function (result) {
                $('#pnlEnlaces').html(result);
            }
        });
    }
    ;



    //LLAMADO DE FUNCIONES A METODOS
    refreshComentarios();
    refreshMessages();
    refreshDetail();
    refreshLinks();


    //ASIGNACION DE FUNCIONES A EVENTOS DE NODOS 
    $('#btnSendComment').click(enviarNewComentario);
    $('#btnSendMsg').click(enviarNewMesagge);

    $('#formMesagges').submit(function (e) {
        e.preventDefault();
        enviarNewMesagge();
    });

    $('#formComment').submit(function (e) {
        e.preventDefault();
        enviarNewComentario();
    });

    $(btnPDFModal).click(PDFModal);

    $('#pnlLoadPDF').on('load', function (e) {
        $('#spinerLoadPDF').fadeOut("slow", function () {
            $(e.target).css('display', 'block');
        });
    });
    
    $('#reloadDetail').click(function () {
        refreshDetail();
        refreshLinks();
    });

});

