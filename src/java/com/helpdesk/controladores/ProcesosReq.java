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
            String idReq = request.getParameter("idReq");
            String accion = request.getParameter("accion");

        if (accion == null ||  idReq == null) {
            response.sendRedirect("PrincipalRequisicion");
        } else {
            boolean seteado = this.setProceso(request, response);
            if(seteado){
                request.getSession().setAttribute("resultado", 1);
            }else{
                request.getSession().setAttribute("resultado", 2);
            }
            response.sendRedirect("RequisicionInfo?idReq="+idReq);
            
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

        Integer nuevoEstado = 0;

        switch (accion) {
            case "revision":
                nuevoEstado = Enums.ESTADO_REQ.REVISION;
                break;
            case "conceder":
                nuevoEstado = Enums.ESTADO_REQ.ACEPTADA;
                break;
            case "denegar":
                nuevoEstado = Enums.ESTADO_REQ.RECHAZADA;
                break;
            case "cerrar":
                nuevoEstado = Enums.ESTADO_REQ.FINALIZADA;
                break;
        }

        if (nuevoEstado == 0) { //No establecio ningun proceso especifico
            return seteado;
        }

        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();

            RequisicionPago pg = Operaciones.get(idReq, new RequisicionPago());
            switch(nuevoEstado){
                case Enums.ESTADO_REQ.REVISION:{
                    if(myRol == 6 && pg.getEstado()==Enums.ESTADO_REQ.SOLICITADA){
                        pg.setEstado(nuevoEstado);
                        pg.setIdAutorizador(myIdUsuario);
                        seteado = true;
                    }
                    break;
                }
                case Enums.ESTADO_REQ.ACEPTADA: {
                    if(myRol ==6 && pg.getIdAutorizador()!=null && pg.getIdAutorizador()==myIdUsuario && pg.getEstado()==Enums.ESTADO_REQ.REVISION){
                        //Generar doc PDF
                        //Gerenerar Notificacion al receptor y al contador
                        pg.setEstado(nuevoEstado);
                        seteado = true;
                    }
                    break;
                }
                case Enums.ESTADO_REQ.RECHAZADA: {
                    if(myRol ==6 && pg.getIdAutorizador()!=null && pg.getIdAutorizador()==myIdUsuario && pg.getEstado()==Enums.ESTADO_REQ.REVISION){
                        pg.setEstado(nuevoEstado);
                        seteado = true;
                    }
                    break;                    
                }
                case Enums.ESTADO_REQ.FINALIZADA: {
                    if(myRol ==9 && pg.getIdContador()==myIdUsuario && pg.getEstado()==Enums.ESTADO_REQ.ACEPTADA){
                        pg.setEstado(nuevoEstado);
                        seteado = true;
                    }
                    break;                    
                }                
            }
            
            if(seteado){
                pg = Operaciones.actualizar(pg.getIdRequisicion(), pg);
                Operaciones.commit();;
            }

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
