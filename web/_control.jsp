<!--CONTENIDO DE CONTROL-->
<div class="table-responsive-lg">
    <table class="table mt-4 table-sm">
        <thead>
            <tr>
                <th scope="col">Asignado a</th>
                <th scope="col">Estado</th>
                <th scope="col">Inicio Prev.</th>
                <th scope="col">Inicio Real</th>
                <th scope="col">Fin Previsto.</th>
                <th scope="col">Fin Real</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="ic" items="${LstControl}">
                <tr>
                    <td>${ic.receptor}</td>
                    <td>${ic.status}</td>
                    <td><script>
                        if ("${ic.inicioPrev}" != "") {
                            moment.locale('es');
                            var date = "${ic.inicioPrev}";
                            var result = moment(date).format('L') + " " + moment().format('LT');
                            document.write(result);
                        }
                        </script></td>
                    <td><script>
                        if ("${ic.inicioReal}" != "") {
                            moment.locale('es');
                            var date = "${ic.inicioReal}";
                            var result = moment(date).format('L') + " " + moment().format('LT');
                            document.write(result);
                        }
                        </script></td>
                    <td><script>
                        if ("${ic.finPrev}" != "") {
                            moment.locale('es');
                            var date = "${ic.finPrev}";
                            var result = moment(date).format('L') + " " + moment().format('LT');
                            document.write(result);
                        }
                        </script></td>
                    <td><script>
                        if ("${ic.finReal}" != "") {
                            moment.locale('es');
                            var date = "${ic.finReal}";
                            var result = moment(date).format('L') + " " + moment().format('LT');
                            document.write(result);
                        }
                        </script>--</td>                                              
                </tr>
            </c:forEach>

        </tbody>
    </table>
</div> 