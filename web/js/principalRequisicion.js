/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var root = document.getElementById("path");

function getRequisiciones(opc) {
    
    switch (opc) {
        case 1:
        {
            displayDataTables(root.value + "/PrincipalRequisicion?accion=todas");
            break;
        }
        case 2:
        {
            displayDataTables(root.value + "/PrincipalRequisicion?accion=enproceso");
            break;
        }
        case 3:
        {
            //Pendientes
            displayDataTables(root.value + "/PrincipalRequisicion?accion=pending");
            break;
        }
        case 4:
        {

            displayDataTables(root.value + "/PrincipalRequisicion?accion=finalizadas");
            break;
        }
        case 5:
        {
            ///Solicitadas
            //alert("Hello");
            console.log(root.value + "/PrincipalRequisicion?accion=solicitadas");
            displayDataTables(root.value + "/PrincipalRequisicion?accion=solicitadas");
            break;
        }
        case 6:
        {
            ///Rechazadas
            displayDataTables(root.value + "/PrincipalRequisicion?accion=refuse");
            break;
        }

    }
}


function displayDataTables(url) {
    $.fn.dataTable.ext.errMode = 'throw';
    $("#table-requisicion").dataTable().fnDestroy()
    $('#table-requisicion').DataTable({
        ajax: {
            url: url,
            dataSrc: ''
        },
        
        columns: [
            {
                data: null,
                render: function (data, type, row) {
                    // Combine the first and last names into a single table field
                    return `<a href="`+root.value+`/RequisicionInfo?idReq=`+data.idRequisicion+`">Numero - `+data.idRequisicion+`</a>`;
                }

            }
            
        ],
        language:
                {
                    "sProcessing": "Procesando...",
                    "sLengthMenu": "Mostrar _MENU_ registros",
                    "sZeroRecords": "No se encontraron resultados",
                    "sEmptyTable": "Ningún dato disponible en esta tabla",
                    "sInfo": "_END_ de _TOTAL_ registros",
                    "sInfoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
                    "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
                    "sInfoPostFix": "",
                    "sSearch": "Buscar:",
                    "sUrl": "",
                    "sInfoThousands": ",",
                    "sLoadingRecords": "No se encontraron resultados",
                    "oPaginate": {
                        "sFirst": "Primero",
                        "sLast": "Último",
                        "sNext": "Siguiente",
                        "sPrevious": "Anterior"
                    },
                    "oAria": {
                        "sSortAscending": ": Activar para ordenar la columna de manera ascendente",
                        "sSortDescending": ": Activar para ordenar la columna de manera descendente"
                    },
                    "buttons": {
                        "copy": "Copiar",
                        "colvis": "Visibilidad"
                    }
                }
    });
}


