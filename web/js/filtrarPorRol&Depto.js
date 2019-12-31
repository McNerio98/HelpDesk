/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var path = document.getElementById('path');
var idRol = 1;
var idDepto = 1;
var nodata = `<div class="col-12 alert alert-warning" role="alert">
                No se encontro ninguna resultado
              </div>`;

//En el caso que sea Gerente

if (document.getElementById('fRol') != null && document.getElementById('fDepto') != null) {
    document.getElementById('fRol').addEventListener('change', function () {
        idRol = (document.getElementById('fRol').value);
        renderData(path.value,1);
    });

    document.getElementById('fDepto').addEventListener('change', function () {
        idDepto = (document.getElementById('fDepto').value);
        renderData(path.value,1);
    });
}



function renderData(path,idcase) {
    document.getElementById('nofound').innerHTML = "";
    document.getElementById('fUsers').innerHTML = "";
    fetch(path + "/Empleados?accion=userbyfilter", {
        method: 'post',
        body: 'idRol=' + idRol + '&idDepto=' + idDepto + '&idCase='+idcase,
        headers: {'Content-type': 'application/x-www-form-urlencoded'}
    })
            .then((response) => response.text())
            .then((responseText) => {
                var data = JSON.parse(responseText);
                console.log(data);
                for (i = 0; i < data.length; i++) {
                    document.getElementById('fUsers').innerHTML += `
                    <tr id="${data[i].id}">
                        <td>${data[i].id}</td>
                        <td>${data[i].name}</td>
                        <td>${data[i].lastname}</td>
                        <td>${data[i].email}</td>
                        <td><input id="isCheck${data[i].id}" value="input${data[i].id}" class="inputCheck" onclick="userChecked(${data[i].id})" type="checkbox"></td>
                    </tr>
                    `;
                }


            })
            .catch((error) => {
                document.getElementById('nofound').innerHTML = nodata;
            });
}

function userChecked(id) {
    var array = document.getElementsByClassName("inputCheck");
    var array2 = document.getElementById(id).getElementsByTagName('td');

    var isChecked = document.getElementById('isCheck' + id).checked;
    if (isChecked) {
        document.getElementById("idReceptor").setAttribute("value", array2[0].textContent);
        document.getElementById("Receptor").setAttribute("value", array2[1].textContent + " " + array2[2].textContent);
        for (i = 0; i < array.length; i++) {
            if (array[i].value != ("input" + id)) {
                //array[i].setAttribute('disabled', true);
                array[i].disabled = "true";
            }
        }
    } else {
        document.getElementById("idReceptor").setAttribute("value", "");
        document.getElementById("Receptor").setAttribute("value", "");
        for (i = 0; i < array.length; i++) {

            array[i].removeAttribute("disabled");


        }
    }


}

///En el caso que sea Lider

if (document.getElementById("radio1") != null && document.getElementById("radio2")) {
    
    document.getElementById("deptoList").style.display = "none";
    
    document.getElementById("radio1").addEventListener('click', function () {
        document.getElementById("deptoList").style.display = "none";
        idDepto = document.getElementById("idDepDef").value;
        renderData(path.value,2);
        
    });

    document.getElementById("radio2").addEventListener('click', function () {
        document.getElementById("deptoList").style.display = "block";
        idDepto = document.getElementById("fDepto").value;
        renderData(path.value,2);
    });
    
    document.getElementById("fDepto").addEventListener("change",function(){
        idDepto = document.getElementById("fDepto").value;
        renderData(path.value,2);
    });
}





