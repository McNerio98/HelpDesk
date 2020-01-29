/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function newForm() {
    $('#modaltoNewClass').modal('show');
}

function updateClass(id) {
    var array = document.getElementById(id).getElementsByTagName('td');
    document.getElementById('Idclass').setAttribute('value', array[0].textContent);
    document.getElementById('inputName').setAttribute('value', array[1].textContent);
    document.getElementById('inputDescription').innerHTML =  array[2].textContent;
    $('#modaltoUpdateClass').modal('show');
}



