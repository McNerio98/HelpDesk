/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.controladores;

import com.helpdesk.conexion.ConexionPool;
import com.helpdesk.entidades.Departamento;
import com.helpdesk.entidades.Rol;
import com.helpdesk.entidades.Usuario;
import com.helpdesk.operaciones.Operaciones;
import com.helpdesk.utilerias.listarEmpleado;
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
public class Grupo extends HttpServlet {

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

            int idSesionUser = (int) (request.getSession().getAttribute("idUsuario"));
            //Con esta consulta se obtiene el id departamento que pertene al usuario logueado
            String query = "select a.iddepto from departments a, deptobyusers b where\n"
                    + "a.iddepto=b.iddepto and b.iduser=" + idSesionUser;

            try {
                ConexionPool conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);

                String[][] array = Operaciones.consultar(query, null);

                int iddepto = Integer.parseInt(array[0][0]);

                //Se obtiene el id de todos los usuarios que pertenezcan al departamento del usuario logueado
                String query2 = "select b.iduser\n"
                        + "from deptobyusers a, users b\n"
                        + "where\n"
                        + "a.iduser=b.iduser and a.iddepto = " + iddepto;

                String[][] array2 = Operaciones.consultar(query2, null);
                
                //out.println(array2[0][3]);
                
                //Se instancia una lista a la clase ListarEmpleado
                ArrayList<listarEmpleado> lstEmpleado = new ArrayList<>();
                for(int i=0;i<array2[0].length;i++){
                    
                    Usuario usertmp = new Usuario();
                    Rol roltmp = new Rol();
                    
                    usertmp = Operaciones.get(Integer.parseInt(array2[0][i]), new Usuario());
                    roltmp = Operaciones.get(usertmp.getIdRole(), new Rol());
                    
                    listarEmpleado emp = new listarEmpleado();
                    emp.setUsuario(usertmp);
                    emp.setRol(roltmp);
                    
                    lstEmpleado.add(emp);
                }
                request.getSession().setAttribute("idUsuario", idSesionUser);
                request.setAttribute("listEmpleado", lstEmpleado);
                request.getRequestDispatcher("grupos.jsp").forward(request, response);
                
                
                
                        
            } catch (Exception ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex2) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex2);
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
