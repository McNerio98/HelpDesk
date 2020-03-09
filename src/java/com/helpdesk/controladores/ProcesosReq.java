/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.controladores;

import com.helpdesk.conexion.Conexion;
import com.helpdesk.conexion.ConexionPool;
import com.helpdesk.entidades.Comentario;
import com.helpdesk.entidades.DetalleRequisicion;
import com.helpdesk.entidades.RequisicionPago;
import com.helpdesk.entidades.Usuario;
import com.helpdesk.operaciones.Operaciones;
import com.helpdesk.utilerias.DataList;
import com.helpdesk.utilerias.Enums;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.Date;
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
        //Comentarios 
        String accion = request.getParameter("accion");
        if(accion.equals("newMessage")){
            boolean status = sendMesagge(request,response);
            if(status){
                response.getWriter().print("true");
            }else{
                response.getWriter().print("false");
            }
        }else if(accion.equals("deleteDetalle")){
            boolean estado = deleteDetalleFromDB(request,response);
            if(estado){
                response.getWriter().print("true");
            }else{
                response.getWriter().print("false");
            }            
        }

    }
    
    private boolean deleteDetalleFromDB(HttpServletRequest request, HttpServletResponse response){
        boolean estado = false;
        String idDetalle = request.getParameter("idenDetalle");
        String idReq = request.getParameter("idReq");
        
        try{
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            DetalleRequisicion dt = Operaciones.eliminar(Integer.parseInt(idDetalle), new DetalleRequisicion());
            RequisicionPago pg = Operaciones.get(Integer.parseInt(idReq), new RequisicionPago());
            pg.setTotal(pg.getTotal().subtract(dt.getMonto()));
            pg = Operaciones.actualizar(pg.getIdRequisicion(), pg);
            if(dt.getIdDetalle()!=0 && pg.getIdRequisicion()!=0){
                estado = true;
            }
        }catch(Exception e){
            Logger.getLogger(ProcesosReq.class.getName()).log(Level.SEVERE, null, e);
        }finally{
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(ProcesosReq.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return estado;
    }
    
    
    private boolean sendMesagge(HttpServletRequest request, HttpServletResponse response){
        boolean status = false;
        String contenido = request.getParameter("msg");
        String idReq = request.getParameter("idReq");
        Integer idHolder = (Integer)request.getSession().getAttribute("idUsuario");
        
        try{
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            RequisicionPago rg = Operaciones.get(Integer.parseInt(idReq), new RequisicionPago());
            
            if(rg.getIdAutorizador() == idHolder || rg.getIdCreador() == idHolder){ //solo el lider y el receptor pueden comentar 
                Comentario com = new Comentario();
                com.setContenido(contenido);
                com.setIdCreador(idHolder);
                com.setIdRequisicion(Integer.parseInt(idReq));
                com.setFecha(new Timestamp(System.currentTimeMillis()));
                
                com = Operaciones.insertar(com);
                Operaciones.commit();
                status = true;
            }
        }catch(Exception e){
            Logger.getLogger(ProcesosReq.class.getName()).log(Level.SEVERE, null, e);
            try {
                Operaciones.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(ProcesosReq.class.getName()).log(Level.SEVERE, null, ex);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex2) {
                Logger.getLogger(ProcesosReq.class.getName()).log(Level.SEVERE, null, ex2);
            }
        }
        
        
        return status;
    }

    private boolean setProceso(HttpServletRequest request, HttpServletResponse response) {
        boolean seteado = false;
        String accion = request.getParameter("accion");
        Integer idReq = Integer.parseInt(request.getParameter("idReq"));
        Integer myRol = (Integer) request.getSession().getAttribute("Rol");
        Integer myIdUsuario = (Integer) request.getSession().getAttribute("idUsuario");
        ArrayList<Usuario> listUsers = new ArrayList<>();
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
        /*
        try{
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            System.out.print("Finalizo");
        }catch(Exception e){
            Logger.getLogger(ProcesosReq.class.getName()).log(Level.SEVERE, null, e);
        }finally{
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(ProcesosReq.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
        

        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();

            RequisicionPago pg = Operaciones.get(idReq, new RequisicionPago());
            listUsers.add(Operaciones.get(pg.getIdContador(), new Usuario()));
            listUsers.add(Operaciones.get(pg.getIdCreador(), new Usuario()));
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
                        request.getSession().setAttribute("idReqForPDF", pg.getIdRequisicion());
                        pg.setEstado(nuevoEstado);
                        Date dt = new Date();
                        pg.setFechaAprovacion(new Timestamp(dt.getTime()));
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
            Logger.getLogger(ProcesosReq.class.getName()).log(Level.SEVERE, null, e);
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
        
        if(nuevoEstado == Enums.ESTADO_REQ.ACEPTADA && seteado == true && myRol == 6){
            DataList.sendNotificationToContador(listUsers.get(0), idReq);
        }
        if(nuevoEstado == Enums.ESTADO_REQ.FINALIZADA && seteado == true && myRol == 9){
            DataList.sendNotificationToSolicitante(listUsers.get(1), idReq);
        }
        return seteado;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
