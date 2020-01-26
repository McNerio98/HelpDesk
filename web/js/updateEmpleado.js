/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function updateUser(id,op) {
    if (op==1) 
        //case 1:
        {
            var array = document.getElementById(id).getElementsByTagName('td');
            console.log(array);
            
            var elems2 = document.getElementById("inputGroupSelect02").getElementsByTagName('option');
            document.getElementById('empleado').innerHTML = array[1].textContent;
            document.getElementById('inputuser').setAttribute('value', array[0].textContent);
            setInputs(array,"inputGroupSelect01",4);
            setInputs(array,"inputGroupSelect02",5);
            $('#modaltoUpdateUser').modal('show');
        }
        else
        {
            var array = document.getElementById(id).getElementsByTagName('td');
            document.getElementById('empleado').innerHTML = array[1].textContent;
            document.getElementById('deleteiduser').setAttribute('value', array[0].textContent);
            $('#modaltoDeleteUser').modal('show');
        }
    

}

function setInputs(array,id,tag){
    var elems = document.getElementById(id).getElementsByTagName('option');
    for(i=0;i<elems.length;i++){
        console.log(elems[i]);
                if(elems[i].value==array[tag].textContent){
                    console.log(elems[i]);
                    elems[i].setAttribute('selected', true);
                    break;
                }
            }
}



