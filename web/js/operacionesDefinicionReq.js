/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    //VARIABLES GLOBALES
    var contexto = $('#contextoApp').val();
    var idReq = $('#idRequisicion').val();
    var btnPDFModal = $('#btnPDFModal');
    var panelPDFModal = $('#pnlLoadPDF');
    var modalForPDF = $('#modalForPDF');
    
    
    


    //DECLARACION DE FUNCIONES
    
    function PDFModal(){
        $(panelPDFModal).attr('src',contexto+'/RequisicionPDF?idRequisicion='+idReq);
        $(modalForPDF).modal('show');
        $("#txtMotionPDF").text('Abrir PDF');
        $(btnPDFModal).off("click");
        
        $(btnPDFModal).on("click",function(){
            $(modalForPDF).modal('show');
        });
        
    }

    //funciones para comentarios, notificaciones
    function refreshComentarios() {
        console.log(contexto);
        console.log(idReq);
        $.ajax({
            type: 'POST',
            url: contexto + '/RequisicionInfo?accion=getAllComents&idReq=' + idReq,
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
            data: {msg: contentMsg, tipo: '2'},
            url: contexto + '/ProcesosReq?accion=newMessage&idReq=' + idReq,
            success: function (result) {
                if (result == 'true') {
                    refreshComentarios();
                    $('#txtContentComment').val("");
                } else {
                    console.log("ERROR en envio de comentario");
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
            console.log("Enviando mensaje");
        }
    }

    //funciones para chat
    function refreshMessages() {
        $.ajax({
            type: 'POST',
            url: contexto + '/RequisicionInfo?accion=getAllMsg&idReq=' + idReq,
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
            data: {msg: contentMsg, tipo: '1'},
            url: contexto + '/ProcesosReq?accion=newMessage&idReq=' + idReq,
            success: function (result) {
                if (result == 'true') {
                    refreshMessages();
                    $('#txtContentMsg').val("");
                } else {
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
            url: contexto + "/RequisicionInfo?accion=loadDetalles&idReq=" + idReq,
            success: function (result) {
                $('#pnlRegistros').html(result);
            }
        });
    }
    ;

    function refreshLinks() {
        $.ajax({
            type: 'POST',
            url: contexto + "/RequisicionInfo?accion=loadLinks&idReq=" + idReq,
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


});

