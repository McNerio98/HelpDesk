
</div>
<!-- /.content-wrapper -->
<footer class="main-footer">
    <strong>Copyright &copy; 2019-2020 <a href="http://helpdesk.com">HelpDesk</a>.</strong>
    McNerio & CnkBlanco USO
    <div class="float-right d-none d-sm-inline-block">
        <b>Version</b> 1.1
    </div>
</footer>

<!-- Control Sidebar -->
<aside class="control-sidebar control-sidebar-dark">
    <!-- Control sidebar content goes here -->
</aside>
<!-- /.control-sidebar -->
</div>
<!-- ./wrapper -->

<!-- jQuery -->
<script src="plugins/jquery/jquery.min.js"></script>
<!-- jQuery UI 1.11.4 -->
<!-- Bootstrap 4 -->
<script src="plugins/bootstrap/js/bootstrap.bundle.min.js"></script>



<!-- Tempusdominus Bootstrap 4 -->
<script src="plugins/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>

<!-- overlayScrollbars -->
<script src="plugins/overlayScrollbars/js/jquery.overlayScrollbars.min.js"></script>
<!-- AdminLTE App -->
<script src="framework/js/adminlte.js"></script>
<!-- AdminLTE dashboard demo (This is only for demo purposes) -->
<script src="framework/js/pages/dashboard.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="framework/js/demo.js"></script>
<script src="js/jquery.dataTables.min.js"></script>

<script>
    function validarCaracteres(input, max, btn, alert) {
        document.getElementById(input).addEventListener("keyup", function (event) {
            if (event.target.value.length >= max) {
                document.getElementById(btn).setAttribute("disabled", true);
                document.getElementById(alert).innerHTML = "El campo debe ser menos de " + max + " caracteres";
            }
            if (event.target.value.length <= max) {
                document.getElementById(btn).removeAttribute("disabled", false);
                document.getElementById(alert).innerHTML = "";
            }
        });
    }

</script>

<script src="js/bootstrap-datepicker.js"></script>


</body>

</html>
