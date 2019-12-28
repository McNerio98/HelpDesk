/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.controladores;

import com.helpdesk.conexion.ConexionPool;
import com.helpdesk.entidades.Departamento;
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
 * @author cnk
 */
public class Departamentos extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String accion = request.getParameter("accion");
            if (accion == null) {

                try {
                    ArrayList<Departamento> lstDeptos = new ArrayList<>();
                    ConexionPool conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();

                    lstDeptos = Operaciones.getTodos(new Departamento());

                    if (lstDeptos != null) {
                        request.setAttribute("listDepto", lstDeptos);
                    } else {
                        request.setAttribute("listDepto", null);
                    }
                    Operaciones.commit();
                    request.getRequestDispatcher("departamentos.jsp").forward(request, response);

                    
                } catch (Exception ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex2) {
                        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex2);
                    }
                }
            } else {
                switch (accion) {
                    
                    case "nuevo": {
                        String deptoname = request.getParameter("deptoname");
                        String descripcion = request.getParameter("descripcion");

                        Departamento depto = new Departamento();
                        depto.setDeptoName(deptoname);
                        depto.setDescription(descripcion);

                        try {
                            ConexionPool conn = new ConexionPool();
                            conn.conectar();
                            Operaciones.abrirConexion(conn);
                            Operaciones.iniciarTransaccion();
                            Operaciones.insertar(depto);
                            Operaciones.commit();
                            response.sendRedirect(request.getContextPath() + "/Departamentos");
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
                    case "actualizar": {
                        String iddepto = request.getParameter("iddepto");
                        String deptoname = request.getParameter("deptoname");
                        String descripcion = request.getParameter("descripcion");

                        Departamento depto = new Departamento();
                        depto.setIdDepto(Integer.parseInt(iddepto));
                        depto.setDeptoName(deptoname);

                        depto.setDescription(descripcion);

                        try {
                            ConexionPool conn = new ConexionPool();
                            conn.conectar();
                            Operaciones.abrirConexion(conn);
                            Operaciones.iniciarTransaccion();
                            Operaciones.actualizar(depto.getIdDepto(), depto);
                            Operaciones.commit();
                            response.sendRedirect(request.getContextPath() + "/Departamentos");

                        }catch (Exception ex) {
                            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                        } finally {
                            try {
                                Operaciones.cerrarConexion();
                            } catch (SQLException ex2) {
                                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex2);
                            }
                        }
                        break;

                    }
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
        processRequest(request, response);
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
