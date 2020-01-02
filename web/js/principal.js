/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var root = document.getElementById("path");

function getIncidences(opc) {
    switch (opc) {
        case 1:
        {
            renderData(root.value + "/Principal?accion=todas");
            break;
        }
        case 2:
        {
            renderData(root.value + "/Principal?accion=enproceso");
            break;
        }
        case 3:
        {
            renderData(root.value + "/Principal?accion=urgente");
            break;
        }
        case 4:
        {
            renderData(root.value + "/Principal?accion=finalizadas");
            break;
        }
    }
}

function renderData(path) {
    document.getElementById('listIncidences').innerHTML = "";
    fetch(path)
            .then((response) => response.text())
            .then((responseText) => {
                console.log(responseText);
                var data = JSON.parse(responseText);
                console.log(data);
                for (i = 0; i < data.length; i++) {
                    document.getElementById('listIncidences').innerHTML += `
                        
                    
                        <a href="${root.value}/Informacion?id=${data[i].id}">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            ${data[i].name}
                            <i class="ion ion-md-arrow-forward"></i>
                        </li>
                        </a>
        `;
                }


            })
            .catch((error) => {
                document.getElementById('listIncidences').innerHTML = error;
            });
}