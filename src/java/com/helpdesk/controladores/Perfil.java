/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.controladores;

import com.helpdesk.conexion.Conexion;
import com.helpdesk.conexion.ConexionPool;
import com.helpdesk.entidades.Usuario;
import com.helpdesk.entidades.UsuarioRequisicion;
import com.helpdesk.operaciones.Operaciones;
import com.helpdesk.utilerias.Enums;
import com.helpdesk.utilerias.Hash;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ALEX
 */
public class Perfil extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        //Obtener informacion del perfil , El Obj 

        if (request.getSession().getAttribute("resultChange") != null) {
            request.setAttribute("resultado", request.getSession().getAttribute("resultChange"));
            request.getSession().removeAttribute("resultChange");
        }

        Integer idUsuario = (int) request.getSession().getAttribute("idUsuario");

        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Usuario u = Operaciones.get(idUsuario, new Usuario());
            //verificando el tipo de session 
            String typeSec = (String) request.getSession().getAttribute("typeSession");
            if (typeSec.equals("REQ")) {
                //verifica si esta registrado en HD
                //Si el usuario tiene algun rol de requisicion entonces no esta habilitado para hd 
                if (u.getIdRole() >= 5 && u.getIdRole() <= 9) {
                    //Habilitar para HD
                    request.setAttribute("activeCount", "activeHD");
                }

            } else if (typeSec.equals("HD")) {
                //verifica si esta registrado en REQ
                UsuarioRequisicion ur = Operaciones.get(u.getIdUser(), new UsuarioRequisicion());
                //Si es cero entonces no esta registrado
                if (ur.getIdUsuario() == 0) {
                    //Habilitar para REQ
                    request.setAttribute("activeCount", "activeREQ");
                }

            }
            request.setAttribute("Us", u);

        } catch (Exception ex) {

            Logger.getLogger(Perfil.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex1) {
                Logger.getLogger(Perfil.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        request.getRequestDispatcher("perfil.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        switch (accion) {
            case "update": {
                if (cngDataUser(request, response)) {
                    request.getSession().setAttribute("resultChange", 1); //done
                } else {
                    request.getSession().setAttribute("resultChange", 2); //fail
                }
                response.sendRedirect("Perfil");
            }
            case "activeMod": {
                String modToActive = request.getParameter("mod");
                Integer idUsuario = (int) request.getSession().getAttribute("idUsuario");
                String typeSec = (String) request.getSession().getAttribute("typeSession");
                response.setContentType("text/plain");
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    Usuario u = Operaciones.get(idUsuario, new Usuario());
                    
                    if (modToActive.equals("HD") && typeSec.equals("REQ")) {
                        u.setIdRole(Enums.ROL.EMPLEADO);
                        u = Operaciones.actualizar(u.getIdUser(), u);
                    } else if (modToActive.equals("REQ") && typeSec.equals("HD")) {
                        UsuarioRequisicion ur = new UsuarioRequisicion();
                        ur.setIdUsuario(u.getIdUser());
                        ur.setIdRol(Enums.ROL.EMPLEADO_REQ);
                        ur = Operaciones.insertar(ur);
                    }
                    
                    Operaciones.commit();
                    response.getWriter().print("true");
                } catch (Exception ex) {
                    response.getWriter().print("false");
                    Logger.getLogger(Perfil.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex1) {
                        Logger.getLogger(Perfil.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
        }

    }

    private boolean cngDataUser(HttpServletRequest request, HttpServletResponse response) {
        boolean estado = false;
        //Obtener el objeto usuario y comparar 
        Integer idUsuario = (int) request.getSession().getAttribute("idUsuario");
        String celular = request.getParameter("txtCel");
        String nombre = request.getParameter("txtNombre");
        String apellido = request.getParameter("txtApellido");
        String correo = request.getParameter("txtEmail");

        String clave1 = request.getParameter("txtClave");
        String clave2 = request.getParameter("txtClave2");

        String cambiarClave = request.getParameter("enableChangePass");
        boolean changePass = false;
        if (cambiarClave != null && "trueChange".equals(cambiarClave)) {
            changePass = true;
        }
        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            Usuario u = Operaciones.get(idUsuario, new Usuario());
            u.setTelephone(celular);
            u.setFirsName(nombre);
            u.setLastName(apellido);
            u.setEmail(correo);
            if (changePass) {
                if (u.getPassword().equals(Hash.generarHash(clave1, Hash.SHA256))) {
                    //Si es el propietario de la cuenta 
                    u.setPassword(Hash.generarHash(clave2, Hash.SHA256));
                } else {
                    return estado;
                }
            }
            u = Operaciones.actualizar(u.getIdUser(), u);

            Operaciones.commit();
            estado = true;
        } catch (Exception ex) {
            try {
                Operaciones.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Perfil.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex1) {
                Logger.getLogger(Perfil.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

        return estado;
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
