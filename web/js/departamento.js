  
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function addContador(id){
    var array = document.getElementById(id).getElementsByTagName('td');
    document.getElementById('IdEmpresas').setAttribute('value', array[0].textContent);
    document.getElementById('inputContador').setAttribute('value', array[1].textContent);
    $('#modaltoAddContador').modal('show');
}

function newForm() {

    
    
    
    $('#modaltoNewDepto').modal('show');

}

function updateDepto(id) {
    var array = document.getElementById(id).getElementsByTagName('td');
    document.getElementById('IdDepto').setAttribute('value', array[0].textContent);
    document.getElementById('inputName').setAttribute('value', array[1].textContent);
    document.getElementById('inputDescription').innerHTML = array[2].textContent;
    $('#modaltoUpdateDepto').modal('show');
}

function updateEmpresa(id) {
    var array = document.getElementById(id).getElementsByTagName('td');
    document.getElementById('IdEmpresa').setAttribute('value', array[0].textContent);
    document.getElementById('inputName').setAttribute('value', array[1].textContent);
    document.getElementById('inputDescription').innerHTML = array[2].textContent;
    $('#modaltoUpdateDepto').modal('show');
}

function fetchTable(root,id){
    fetch(root+"/Empresas?accion=getDeptosByEmpresa&id="+id)
                .then((response) => response.text())
                .then((responseText) => {
                    
                    var data = JSON.parse(responseText);
                    console.log(data);
                    document.getElementById("bodyTable").innerHTML = "";
                    for(i = 0;i<data.length;i++){
                        document.getElementById("bodyTable").innerHTML += `
                        <tr id="deptr`+data[i].idDepto+`">
                            <td>`+data[i].idDepto+`</td>
                            <td>` + data[i].deptoName + `</td>
                            <td><a href="#" onclick="deleteNode(`+data[i].idDepto+`,`+id+`)">Quitar</a><td>
                        </tr>
                        `;
                    }
                    
                    
                })
                .catch((error) => {

                    console.log(error);
                });
}

function addDeptoToEnterprise(root,id){
    var array = document.getElementById(id).getElementsByTagName('td');
    document.getElementById('IdEmpresa').setAttribute('value', array[0].textContent);
    console.log(array);
    document.getElementById("nombreemp").innerHTML = array[1].textContent;
    fetchTable(root,id.substr(2,id.length));
    
    $('#modaltoAddingDeptoToEnterprise').modal('show');
}