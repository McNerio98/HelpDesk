/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * 
 */

moment.locale('es');

function getPriority(opc) {
    switch (opc) {
        case 1:
        {

            displayDataTables("./PrincipalRequisicion?accion=priority&id=1");

            break;
        }
        case 2:
        {
            displayDataTables("./PrincipalRequisicion?accion=priority&id=2");
            break;
        }
        case 3:
        {
            displayDataTables("./PrincipalRequisicion?accion=priority&id=3");
            break;
        }
        case 4:
        {
            displayDataTables("./PrincipalRequisicion?accion=priority&id=4");
            break;
        }
    }
}

function getReq(opc) {
    switch (opc) {
        case 1:
        {

            displayDataTables("./PrincipalRequisicion?" + (location.search).substr(1) + "&priority=1");

            break;
        }
        case 2:
        {
            displayDataTables("./PrincipalRequisicion?" + (location.search).substr(1) + "&priority=2");
            break;
        }
        case 3:
        {
            displayDataTables("./PrincipalRequisicion?" + (location.search).substr(1) + "&priority=3");
            break;
        }
    }
}


var root = document.getElementById("path");

function load(opc) {

    switch (opc) {
        case 1:
        {
            displayDataTables(root.value + "/PrincipalRequisicion?accion=loadAll&opcion=todas");

            break;
        }
        case 2:
        {
            displayDataTables(root.value + "/PrincipalRequisicion?accion=loadAll&opcion=revision");
            break;
        }
        case 3:
        {

            displayDataTables(root.value + "/PrincipalRequisicion?accion=loadAll&opcion=aceptadas");
            break;
        }
        case 4:
        {

            displayDataTables(root.value + "/PrincipalRequisicion?accion=loadAll&opcion=finalizadas");
            break;
        }

    }
}


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

function displayData(url) {
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
                    return `<a href="` + root.value + `/RequisicionInfo?idReq=` + data.id + `">Requisicion n-` + data.id + `</a>`;
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




function displayDataTables(url) {
    $.fn.dataTable.ext.errMode = 'throw';
    $("#table-requisicion").dataTable().fnDestroy()
    $('#table-requisicion').DataTable({
        "scrollY": "200px",
        "scrollCollapse": true,
        "paging": false,
        ajax: {
            url: url,
            error: function (jqXHR, textStatus, errorThrown) {
                location.href = root.value;
            },
            dataSrc: ''
        },

        columns: [
            {
                data: null,
                render: function (data, type, row) {
                    console.log($.ajax.data);
                    if (data == "timeout") {
                        location.href = root.value;
                    } else { 
                        var f = "";
                        if (moment(data.fechaEstimada, "DD-MM-YYYYHH:mm").format("LL") == 'Invalid date') {
                            f = "No definida";
                        } else {
                            f = "Para el " + moment(data.fechaEstimada, "DD-MM-YYYYHH:mm").format("LL");
                        }
                        // Combine the first and last names into a single table field
                        return `
                        <a href="` + root.value + `/RequisicionInfo?idReq=` + data.id + `" class="list-group-item list-group-item-action">
                                                        <div class="d-flex w-100 justify-content-between">
                                                            <p style="font-size: 15px;font-weight:bold" class="mb-1">
                                                                <small style="font-size: 12px;font-weight:normal">Solicitante: </small>` + data.solicitante + `
                                                            </p>
                                                            <small>` + f + `</small>
                                                        </div>
                                                        
                                                            <p class="mb-1 text-muted">
                                                             <i>
                                                                <small style="font-size: 12px;font-weight:normal">A nombre de: ` + data.aNombre + `</small>
                                                             <i>
                                                             <span class="text-wrap badge badge-primary float-right">
                                                                $` + data.montoTotal + `</p>
                                                            </span>
                                                        
                                                            <p class="mb-1">` + data.empresa + "-" + data.depto + `</p>
                                                    </a>
                        
                        `;
                    }
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


