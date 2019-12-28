/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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

