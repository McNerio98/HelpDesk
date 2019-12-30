/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
package com.helpdesk.controladores;
 */
package com.helpdesk.controladores;

import com.helpdesk.utilerias.Hash;
import com.helpdesk.conexion.Conexion;
import com.helpdesk.conexion.ConexionPool;
import com.helpdesk.entidades.Departamento;
import com.helpdesk.entidades.DeptoPorUsuario;
import com.helpdesk.entidades.Menu;
import com.helpdesk.entidades.Usuario;
import com.helpdesk.operaciones.Operaciones;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion == null) {
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else if (accion.equals("registro")) {
            ArrayList<Departamento> Deptos = new ArrayList<Departamento>();
            try {
                ConexionPool conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Deptos = Operaciones.getTodos(new Departamento());
                request.setAttribute("DeptosList", Deptos);
            } catch (Exception ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex2) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex2);
                }
            }
            request.getRequestDispatcher("registro.jsp").forward(request, response);
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

        switch (accion) {
            case "nuevo": {
                String userName = request.getParameter("txtUser");
                String nom = request.getParameter("txtNombres");
                String ape = request.getParameter("txtApellidos");
                String idDepto = request.getParameter("depto");
                String clave = request.getParameter("txtPassword");
                String correo = request.getParameter("txtEmail");
                String telefono = request.getParameter("txtTelephone");

                Usuario u = new Usuario();
                u.setUserName(userName.toUpperCase());
                u.setFirsName(nom);
                u.setLastName(ape);
                u.setEmail(correo);
                u.setTelephone(telefono);
                u.setPassword(Hash.generarHash(clave, Hash.SHA256));
                u.setIdRole(4); //role 4 Empleado

                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();

                    u = Operaciones.insertar(u);

                    DeptoPorUsuario dp = new DeptoPorUsuario();
                    dp.setIdDepto(Integer.parseInt(idDepto));
                    dp.setIdUser(u.getIdUser());

                    dp = Operaciones.insertar(dp);
                    Operaciones.commit();
                    response.sendRedirect("Principal");
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
            case "consultar_usuario": {
                response.setContentType("text/plain");
                String userName = request.getParameter("usName");
                PrintWriter out = response.getWriter();
                if (verificarUsuario(userName.toUpperCase())) {
                    out.print("true");
                } else {
                    out.print("false");
                }
                break;
            }
            case "consultar_correo":{
                response.setContentType("text/plain");
                String em = request.getParameter("usEmail");
                PrintWriter out = response.getWriter();
                if (verificarCorreo(em)) {
                    out.print("true");
                } else {
                    out.print("false");
                }
                break;            
            }
        }

    }

    private void iniciarSesion(HttpServletRequest request, HttpServletResponse response) {

        String cuenta = request.getParameter("txtCuenta").toUpperCase();
        String clave = request.getParameter("txtClave");
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
                    s.setAttribute("Rol", u.getIdRole());
                    s.setAttribute("idUsuario", u.getIdUser());
                    
                    
                    List<Menu> MenuPrincipal = getPermisos(u.getIdRole());
                    s.setAttribute("MenuPrincipal", MenuPrincipal);

                    response.sendRedirect("Principal");
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
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return es;
    }

    private List<Menu> getPermisos(Integer idRol){
        List<Menu> permisos = new ArrayList();
        try{
            String sql = "select * from menus where idMenu in (select idmenu from permissions where idrole = ?)";
            List<Object> parametros = new ArrayList();
            parametros.add(idRol);
            String[][] result = Operaciones.consultar(sql, parametros);
            //Array[Columna][Fila]
            for(int i=0; i<result[0].length; i++){
                Menu m = new Menu();
                m.setIdMenu(Integer.parseInt(result[0][i]));
                m.setMenu(result[1][i]);
                m.setController(result[2][i]);
                m.setIdParent(Integer.parseInt(result[3][i] == null ? "0":result[4][i]));
                permisos.add(m);
            }
        }catch(Exception ex){
            //permisos = null;
        }
        return permisos;
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
