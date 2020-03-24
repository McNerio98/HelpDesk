/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var root = document.getElementById("path");

function getMyIncidences(opc) {
    switch (opc) {
        case 1:
        {
            displayDataTables(root.value + "/IncidenciasLider?accion=todas");
            break;
        }
        case 2:
        {
            displayDataTables(root.value + "/IncidenciasLider?accion=enproceso");
            break;
        }
        case 3:
        {
            //Pendientes
            displayDataTables(root.value + "/IncidenciasLider?accion=pending");
            break;
        }
        case 4:
        {

            displayDataTables(root.value + "/IncidenciasLider?accion=finalizadas");
            break;
        }
        case 5:
        {
            ///Solicitadas
            displayDataTables(root.value + "/IncidenciasLider?accion=solicitadas");
            break;
        }
        case 6:
        {
            ///Rechazadas
            displayDataTables(root.value + "/IncidenciasLider?accion=refuse");
            break;
        }

    }
}

function getIncidences(opc) {
    switch (opc) {
        case 1:
        {
            displayDataTables(root.value + "/Principal?accion=todas");
            break;
        }
        case 2:
        {
            displayDataTables(root.value + "/Principal?accion=enproceso");
            break;
        }
        case 3:
        {
            //Pendientes
            displayDataTables(root.value + "/Principal?accion=pending");
            break;
        }
        case 4:
        {

            displayDataTables(root.value + "/Principal?accion=finalizadas");
            break;
        }
        case 5:
        {
            ///Solicitadas
            displayDataTables(root.value + "/Principal?accion=solicitadas");
            break;
        }
        case 6:
        {
            ///Rechazadas
            displayDataTables(root.value + "/Principal?accion=refuse");
            break;
        }

    }
}


function displayDataTables(url) {
    $.fn.dataTable.ext.errMode = 'throw';
    $("#table_incidences").dataTable().fnDestroy()
    $('#table_incidences').DataTable({
        "order": [[0,"desc" ]],
        ajax: {
            url: url,
            error: function (jqXHR, textStatus, errorThrown) {
                if(textStatus == "parsererror"){
                    location.href = root.value;
                }else{
                    location.href = location.hostname + ":"+ location.port + "/" + (location.pathname+location.search).substr(1);
                }
            },
            dataSrc: ''
        },
        
        columns: [
            {data: 'id'},
            {
                data: null,
                render: function (data, type, row) {
                    // Combine the first and last names into a single table field
                    return `<a href="` + root.value + "/Informacion?idIncidencia=" + data.id + `">` + data.name + `</a>
                                        `;
                }

            },
            {data: 'status'}
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