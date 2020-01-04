$(document).ready(function () {

    function MosOcultar() {
        $(this).parent().next().slideToggle(500);
    }
    $('.showHide').click(MosOcultar);


    $('#linkAceptar').click(function (event) {
        event.preventDefault(); 
        let ruta = $('#rutaPath').val();
        $.ajax({
            type: 'POST',
            url: ruta+'/Procesos?accion=verificar',
            success: function (result) {
                if (result == 'true') {
                    $('#errorModalActivas').modal('show');
                } else {
                    event.run();
                }
            }
        });
    });
    
    
    
    $('#nav-contact-tab').click(function (event) {
        $('#pnlLoad').show();
        let ruta = $('#rutaPath').val();
        let idIBR = $('#idenIBR').val();
        $.ajax({
            type: 'POST',
            url: ruta+'/Informacion?accion=listGestiones'+'&idibr='+idIBR,
            success: function (result) {
                $('#pnlLoad').hide();
                $('#contenidoGestiones').html(result);                
            }
        });
    });    
    
    
    
    
})