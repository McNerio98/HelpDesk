/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.controladores;

import com.helpdesk.utilerias.Hash;
import com.helpdesk.conexion.Conexion;
import com.helpdesk.conexion.ConexionPool;
import com.helpdesk.entidades.Departamento;
import com.helpdesk.entidades.DeptoPorUsuario;
import com.helpdesk.entidades.Usuario;
import com.helpdesk.operaciones.Operaciones;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        //PrintWriter out = response.getWriter();
        //out.print("Entre en el Post");
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
                u.setUserName(userName);
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
                    request.getRequestDispatcher("pnlPrincipal.jsp").forward(request, response);
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
                break;
            }
        }

    }

    private void iniciarSesion(HttpServletRequest request, HttpServletResponse response) {
        String nom = request.getParameter("txtNombres");
        String ape = request.getParameter("txtApellidos");
        String userName = request.getParameter("txtUser");
        String idDepto = request.getParameter("depto");
        String clave = request.getParameter("txtPassword");
        //String clave2 = request.getParameter("txtPassword2");
        String correo = request.getParameter("txtEmail");
        String telefono = request.getParameter("txtTelephone");
        //Validar desde aqui que las claves sean iguales 
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
