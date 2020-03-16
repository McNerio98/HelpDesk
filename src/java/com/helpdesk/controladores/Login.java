/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
package com.helpdesk.controladores;
 */
package com.helpdesk.controladores;

import com.google.gson.Gson;
import com.helpdesk.utilerias.Hash;
import com.helpdesk.conexion.Conexion;
import com.helpdesk.conexion.ConexionPool;
import com.helpdesk.entidades.Departamento;
import com.helpdesk.entidades.DeptoPorUsuario;
import com.helpdesk.entidades.Empresa;
import com.helpdesk.entidades.Menu;
import com.helpdesk.entidades.Usuario;
import com.helpdesk.entidades.UsuarioReqByEmpresa;
import com.helpdesk.entidades.UsuarioRequisicion;
import com.helpdesk.operaciones.Operaciones;
import com.helpdesk.utilerias.DataList;
import com.helpdesk.utilerias.Enums;
import com.helpdesk.utilerias.htmlTemplate;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ZEUS
 */
public class Login extends HttpServlet {

    public String generateRamdonString() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));

        return generatedString;
    }

    public String getAllDeptosInEmpresas(int id) {
        ArrayList list = DataList.getDeptosByEmpresa(id);

        String json = new Gson().toJson(list);
        return json;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion == null) {
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else if (accion.equals("registro")) {
            ArrayList<Departamento> Deptos = new ArrayList<Departamento>();
            ArrayList<Empresa> empresas = new ArrayList<>();
            empresas = DataList.getAllEmpresas();
            Deptos = DataList.getDeptosByEmpresa(2);/// Valor seteado en definicion a la db
            request.setAttribute("DeptosList", Deptos);
            request.setAttribute("EmpresasList", empresas);
            request.getRequestDispatcher("registro.jsp").forward(request, response);
        } else if (accion.equals("getDeptosByEmpresa")) {
            PrintWriter out = response.getWriter();
            int idemp = Integer.parseInt(request.getParameter("id"));
            out.print(this.getAllDeptosInEmpresas(idemp));
        } else if (accion.equals("recover")) {
            String opc = request.getParameter("opc");
            HttpSession s = request.getSession();
            PrintWriter out = response.getWriter();

            switch (opc) {

                //Cuando el usuario entre a la vista cambiar password
                case "b19498e29da193d545f4072e58687845a894bcd6": {
                    s.setAttribute("recoverCase", 1);
                    request.getRequestDispatcher("recover.jsp").forward(request, response);
                    break;
                }
                //cuando se capta el correo y se le envia un link con una key hash al mismo
                case "49381d2042ac93bb15942ecc3b2c1830d29ecdfa": {
                    String email = request.getParameter("email");
                    if (!email.equals("")) {
                        if (this.verificarCorreo(email)) {
                            String key = this.generateRamdonString();
                            s.setAttribute("emailUser", email);
                            s.setAttribute("keySesion", Hash.generarHash(key, Hash.SHA256));
                            htmlTemplate html = new htmlTemplate();

                            html.difineTag(
                                    "<p>Hola " + email + ",</p>"
                            );
                            html.difineTag(
                                    "<p class='colored'>Para restablecer su contraseña, "
                                    + "visite la siguiente dirección: </p>"
                            );
                            html.difineTag(
                                    "<a style='background-color: red;"
                                    + "  color: white;"
                                    + "  padding: 1em 1.5em;"
                                    + "  text-decoration: none;"
                                    + "  text-transform: uppercase;'"
                                    + " href='" + request.getRequestURL()
                                    + "?accion=recover&opc=e08e84916fb42ae1b61b75eb6fccf8a6eb98045e&key="
                                    + Hash.generarHash(key, Hash.SHA256) + "'>RESET MY PASSWORD</a>"
                            );

                            if (JavaMail.SendMessage(email, "Reset password", html.RenderHTML())) {
                                out.print("true");
                            } else {
                                out.print("serverError");
                            }

                        } else {
                            out.print("null");
                        }

                    } else {
                        out.print("false");
                    }

                    break;
                }
                //Javascript lo reddirecciona para que se muestre que se envio el link al coreeo
                case "56d3c9490be2608ac36f5a4805bfec2f21f7f982": {
                    s.setAttribute("recoverCase", 2);
                    request.getRequestDispatcher("recover.jsp").forward(request, response);
                    break;
                }
                ///Evalua si la key es la misma que la de session
                case "e08e84916fb42ae1b61b75eb6fccf8a6eb98045e": {
                    String currentkey = request.getParameter("key");
                    HttpSession x = request.getSession();
                    String keySession = (String) x.getAttribute("keySesion");
                    if (currentkey.equals(keySession)) {
                        s.setAttribute("recoverCase", 3);
                        request.getRequestDispatcher("recover.jsp").forward(request, response);
                    } else {
                        s.setAttribute("recoverCase", 4);
                        request.getRequestDispatcher("recover.jsp").forward(request, response);
                    }
                    break;
                }
                case "53a5687cb26dc41f2ab4033e97e13adefd3740d6": {
                    s.setAttribute("recoverCase", 5);
                    request.getRequestDispatcher("recover.jsp").forward(request, response);
                    break;
                }
            }

        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        PrintWriter out = response.getWriter();
        switch (accion) {
            //Para mostrar los departamentos seleccionados a esa empresa

            case "saveNewPas": {
                String usermail = request.getParameter("usermail");
                String newpass = request.getParameter("newpass");
                System.out.print(usermail);
                String query = "select iduser from users where email = ?";
                List<Object> params = new ArrayList<>();
                params.add(usermail);

                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();

                    String array[][] = Operaciones.consultar(query, params);
                    if (array != null) {
                        Usuario user = new Usuario();
                        user = Operaciones.get(Integer.parseInt(array[0][0]), new Usuario());
                        user.setPassword(Hash.generarHash(newpass, Hash.SHA256));
                        Operaciones.actualizar(user.getIdUser(), user);
                        HttpSession s = request.getSession();
                        s.removeAttribute("Usuario");
                        s.removeAttribute("idUsuario");
                        s.removeAttribute("Rol");
                        s.invalidate();
                    }
                    Operaciones.commit();
                    response.sendRedirect(request.getContextPath() + "/Login?accion=recover&opc=53a5687cb26dc41f2ab4033e97e13adefd3740d6");
                } catch (Exception ex) {

                    try {
                        Operaciones.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            }

            case "nuevo": {
                String userName = request.getParameter("txtUser");
                String nom = request.getParameter("txtNombres");
                String ape = request.getParameter("txtApellidos");
                String idDepto = request.getParameter("depto");
                String clave = request.getParameter("txtPassword");
                String correo = request.getParameter("txtEmail");
                String telefono = request.getParameter("txtTelephone");
                // Campo para saber si sera un usuario de requisicion 
                String req = request.getParameter("requisicion");
                boolean userReq = (req != null && req.equals("true")) ? true : false;
                String empresa = request.getParameter("empresa");
                String empresas[] = request.getParameterValues("empresa2");

                Usuario u = new Usuario();

                u.setUserName(userName.toUpperCase());
                u.setFirsName(nom);
                u.setLastName(ape);
                u.setEmail(correo);
                u.setTelephone(telefono);
                u.setPassword(Hash.generarHash(clave, Hash.SHA256));
                u.setIdRole(Enums.ROL.EMPLEADO);
                HttpSession s = request.getSession();

                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();

                    if (userReq) {
                        u.setIdRole(Enums.ROL.EMPLEADO_REQ);
                        u = Operaciones.insertar(u);
                        UsuarioRequisicion ur = new UsuarioRequisicion();
                        ur.setIdUsuario(u.getIdUser());
                        ur.setIdRol(Enums.ROL.EMPLEADO_REQ);
                        ur = Operaciones.insertar(ur);

                        UsuarioReqByEmpresa urb = new UsuarioReqByEmpresa();
                        urb.setIdUsuario(u.getIdUser());
                        urb.setIdEmpresa(Integer.parseInt(empresa));
                        urb = Operaciones.insertar(urb);

                        if (empresas != null) {
                            for (int i = 0; i < empresas.length; i++) {
                                if (!empresas[i].equals("0")) {
                                    UsuarioReqByEmpresa x = new UsuarioReqByEmpresa();
                                    x.setIdUsuario(u.getIdUser());
                                    x.setIdEmpresa(Integer.parseInt(empresas[i]));
                                    x = Operaciones.insertar(x);
                                }
                            }

                        }

                    } else {
                        u = Operaciones.insertar(u);
                    }

                    DeptoPorUsuario dp = new DeptoPorUsuario();
                    dp.setIdDepto(Integer.parseInt(idDepto));
                    dp.setIdUser(u.getIdUser());

                    dp = Operaciones.insertar(dp);
                    Operaciones.commit();
                    response.sendRedirect("Login");
                } catch (Exception ex) {

                    try {
                        Operaciones.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                break;
            }
            case "entrar": {
                iniciarSesion(request, response);
                break;
            }
            case "verificarRequisitor": {
                String cuenta = request.getParameter("cuenta");
                if (this.verifyUserIsRequisitor(cuenta) != null) {
                    String json = new Gson().toJson(this.verifyUserIsRequisitor(cuenta));
                    out.print(json);
                } else {
                    out.print("");
                }
                break;
            }
            case "entrarReq": {
                String emp = request.getParameter("idempReq");
                if (emp != null) {
                    HttpSession s = request.getSession();
                    s.setAttribute("empReq", Integer.parseInt(emp));
                }

                iniciarSesion(request, response);
                break;
            }
            case "consultar_usuario": {
                response.setContentType("text/plain");
                String userName = request.getParameter("usName");

                if (verificarUsuario(userName.toUpperCase())) {
                    out.print("true");
                } else {
                    out.print("false");
                }
                break;
            }
            case "consultar_correo": {
                response.setContentType("text/plain");
                String em = request.getParameter("usEmail");

                if (verificarCorreo(em)) {
                    out.print("true");
                } else {
                    out.print("false");
                }
                break;
            }

        }

    }

    private ArrayList<Empresa> verifyUserIsRequisitor(String cuenta) {
        ArrayList<Empresa> list = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        params.add(cuenta);
        params.add(cuenta);
        String query = "select a.idusuario\n"
                + "from usuariosrequisicion a, users b\n"
                + "where \n"
                + "a.idusuario=b.iduser and \n"
                + "(b.username = ?  or b.email = ?) and a.idrol = 7;";
        String query2 = "select idempresa from usuarioreqbyempresas where idusuario=?";
        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);

            String array[][] = Operaciones.consultar(query, params);

            if (array != null) {
                List<Object> params2 = new ArrayList<>();
                params2.add(Integer.parseInt(array[0][0]));
                String array2[][] = Operaciones.consultar(query2, params2);
                
                for(int i = 0; i < array2[0].length; i++){
                    Empresa emp = new Empresa();
                    System.out.print(array2[0][i]);
                    emp = Operaciones.get(Integer.parseInt(array2[0][i]), new Empresa());
                    list.add(emp);
                }

            }

        } catch (Exception ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex1) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

        return list;
    }

    private void iniciarSesion(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String cuenta = request.getParameter("txtCuenta");
        String clave = request.getParameter("txtClave");
        String logReq = request.getParameter("loginReq");
        boolean loginReq = (logReq != null && logReq.equals("true")) ? true : false;

        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            if (cuenta == null) {
                cuenta = "";
            }

            if (clave == null) {
                clave = "";
            }

            String sql = "select iduser from users where username = ? or email = ?";
            List<Object> params = new ArrayList<>();
            params.add(cuenta);
            params.add(cuenta);
            String[][] iden = Operaciones.consultar(sql, params);

            if (iden != null) {
                HttpSession s = request.getSession();
                Usuario u = Operaciones.get(Integer.parseInt(iden[0][0]), new Usuario());

                if (u.getPassword().equals(Hash.generarHash(clave, Hash.SHA256))) {
                    s.setAttribute("Usuario", u.getUserName());
                    s.setAttribute("idUsuario", u.getIdUser());
                    s.setAttribute("idDepUser", DataList.getIdDepto(u.getIdUser()));
                    List<Menu> MenuPrincipal = new ArrayList<>();

                    if (loginReq) {
                        UsuarioRequisicion ur = Operaciones.get(u.getIdUser(), new UsuarioRequisicion());
                        if (ur.getIdUsuario() == 0) {
                            request.setAttribute("error", 2);
                            request.getRequestDispatcher("login.jsp").forward(request, response);
                            return;
                        }
                        s.setAttribute("Rol", ur.getIdRol());
                        s.setAttribute("typeSession", "REQ");
                        MenuPrincipal = getPermisos(ur.getIdRol());
                        s.setAttribute("MenuPrincipal", MenuPrincipal);
                        response.sendRedirect("PrincipalRequisicion");
                    } else {
                        if (u.getIdRole() >= 5 && u.getIdRole() <= 9) {
                            request.setAttribute("error", 2);
                            request.getRequestDispatcher("login.jsp").forward(request, response);
                            return;
                        }
                        s.setAttribute("Rol", u.getIdRole());
                        s.setAttribute("typeSession", "HD");
                        MenuPrincipal = getPermisos(u.getIdRole());
                        s.setAttribute("MenuPrincipal", MenuPrincipal);
                        response.sendRedirect("Principal");
                    }
                } else {
                    //La clave es incorrecta
                    request.setAttribute("error", 1);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            } else {
                //El usuario no existe 
                request.setAttribute("error", 1);
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }

        } catch (Exception ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);  
            response.sendRedirect("Login");
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex1) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    private boolean verificarUsuario(String dato) {
        boolean es = false;
        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            String sql = "select iduser from users where username = ?";
            List<Object> params = new ArrayList<>();
            params.add(dato);
            String[][] result = Operaciones.consultar(sql, params);
            if (result != null) {
                es = true;
            }
        } catch (Exception e) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return es;
    }

    private boolean verificarCorreo(String dato) {
        boolean es = false;
        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            String sql = "select iduser from users where email = ?";
            List<Object> params = new ArrayList<>();
            params.add(dato);
            String[][] result = Operaciones.consultar(sql, params);
            if (result != null) {
                es = true;
            }
        } catch (Exception e) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return es;
    }

    private List<Menu> getPermisos(Integer idRol) throws Exception {
        List<Menu> permisos = new ArrayList();

        String sql = "select * from menus where idMenu in (select idmenu from permissions where idrole = ?) order by idmenu asc";
        List<Object> parametros = new ArrayList();
        parametros.add(idRol);
        String[][] result = Operaciones.consultar(sql, parametros);
        //Array[Columna][Fila]
        for (int i = 0; i < result[0].length; i++) {
            Menu m = new Menu();
            m.setIdMenu(Integer.parseInt(result[0][i]));
            m.setMenu(result[1][i]);
            m.setController(result[2][i]);
            permisos.add(m);
        }

        return permisos;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
