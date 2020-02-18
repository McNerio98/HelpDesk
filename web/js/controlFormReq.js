$(document).ready(function () {

    var formValido = false;

    function DescToMonto(e) {
      let monto = $(e.target).parent().next().children('.txtMonto');
      let desc = $(e.target);

      if ($(desc).val().length > 0) {
        $(monto).prop('required', true);
      } else {
        $(monto).prop('required', false);
      }
    }

    function MontoToDesc(e) {
      let desc = $(e.target).parent().prev().children('.txtDescripcion');
      let monto = $(e.target);

      if ($(monto).val().length > 0) {
        $(desc).prop('required', true);
      } else {
        $(desc).prop('required', false);
      }
    }




    function getNewContainer() {
      let structura;
      structura = "<div class='row registro'><div class='input-group mb-3 col-sm-8'><div class='input-group-prepend'>" +
        "<span class='input-group-text'><i class='fas fa-angle-right'></i></span></div>" +
        "<input type='text' class='form-control txtDescripcion' placeholder='Descripcion' maxlength='200'>" +
        "</div><div class='input-group mb-3 col-sm-4'><div class='input-group-prepend'>" +
        "<span class='input-group-text'>$</span></div>" +
        "<input type='text' class='form-control txtMonto' step='0.01' placeholder='Monto' pattern='[0.0-9]+'>" +
        "</div></div>";
      return structura;
    }

    function contarRegistros(e) {
      let cnt = 0;
      let contador = $('#pnlRegistros').children().length;
      let arrayRegistro = $('.registro');

      for (let i = 0; i < contador; i++) {
        desc = $(arrayRegistro[i]).find('.txtDescripcion').val();
        sum = $(arrayRegistro[i]).find('.txtMonto').val();
        if ((desc.length > 0) && (sum.length > 0)) {
          cnt++;
        }

      }

      return cnt;
    }

    function setTotalRegistros(){
      console.log(contarRegistros());
      let nodo = $('#totalRecord');
      $(nodo).text(contarRegistros());
    }


    function contar(e) {
      let montos = $('.txtMonto');
      let total = 0;

      for (let i = 0; i < montos.length; i++) {
        if ($(montos[i]).val().length > 0) {
          total += parseFloat($(montos[i]).val());
        }
      }
      let auxSum = (!(total.toString()).includes(".") ? total + ".00" : total)
      $('#totalSum').text(auxSum);
    };



    var pnlParent = $('#pnlRegistros'); // es el div contenedor 

    $('#btnAddRegistro').click(function () {
      $(getNewContainer()).appendTo($(pnlParent));
      $('.txtDescripcion').blur(DescToMonto);
      $('.txtMonto').blur(MontoToDesc);
      $('.txtMonto').change(setTotalRegistros);
      $('.txtDescripcion').change(setTotalRegistros);
      $('.txtMonto').change(contar);
    })


    $('.txtDescripcion').blur(DescToMonto);
    $('.txtMonto').blur(MontoToDesc);
    
    $('.txtMonto').change(setTotalRegistros);
    $('.txtDescripcion').change(setTotalRegistros);

    $('.txtMonto').change(contar);


    $('#formRequisicion').submit(function (e) {

      if (contarRegistros() == 0) {
        e.preventDefault();
        alert("Debe llenar los campos");
      } else {
        //Obtener iterador 
        let contador = $('#pnlRegistros').children().length;
        let arrayRegistro = $('.registro');

        var desc, sum;
        var jsonMtx = [];

        for (let i = 0; i < contador; i++) {
          desc = $(arrayRegistro[i]).find('.txtDescripcion').val();
          sum = $(arrayRegistro[i]).find('.txtMonto').val();
          var obj = { descripcion: desc, monto: sum };
          jsonMtx.push(obj);
        }

        $('#JsonReq').val(JSON.stringify(jsonMtx));

      }

    });


  })