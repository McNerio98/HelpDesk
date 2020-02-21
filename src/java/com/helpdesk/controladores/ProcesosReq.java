/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.controladores;

import com.helpdesk.conexion.Conexion;
import com.helpdesk.conexion.ConexionPool;
import com.helpdesk.entidades.RequisicionPago;
import com.helpdesk.operaciones.Operaciones;
import com.helpdesk.utilerias.Enums;
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
 * @author Mack_
 */
public class ProcesosReq extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("accion") == null || request.getParameter("idReq") == null) {
            response.sendRedirect("PrincipalRequisicion");
        } else {
            boolean seteado = this.setProceso(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    private boolean setProceso(HttpServletRequest request, HttpServletResponse response) {
        boolean seteado = false;
        String accion = request.getParameter("accion");
        Integer idReq = Integer.parseInt(request.getParameter("idReq"));
        Integer myRol = (Integer) request.getSession().getAttribute("Rol");
        Integer myIdUsuario = (Integer) request.getSession().getAttribute("idUsuario");

        Integer proceso = 0;

        switch (accion) {
            case "revision":
                proceso = Enums.ESTADO_REQ.REVISION;
                break;
            case "conceder":
                proceso = Enums.ESTADO_REQ.ACEPTADA;
                break;
            case "denegar":
                proceso = Enums.ESTADO_REQ.RECHAZADA;
                break;
            case "cerrar":
                proceso = Enums.ESTADO_REQ.FINALIZADA;
                break;
        }

        if (proceso == 0) { //No establecio ningun proceso especifico
            return seteado;
        }

        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();

            RequisicionPago pg = Operaciones.get(idReq, new RequisicionPago());

            Operaciones.commit();;

        } catch (Exception e) {
            try {
                Operaciones.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(ProcesosReq.class.getName()).log(Level.SEVERE, null, ex);
            }

        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(ProcesosReq.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return seteado;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
