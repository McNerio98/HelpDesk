/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.controladores;

import com.helpdesk.conexion.Conexion;
import com.helpdesk.conexion.ConexionPool;
import com.helpdesk.entidades.IncidenciaPorEncargado;
import com.helpdesk.operaciones.Operaciones;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ZEUS
 */
public class Procesos extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        String idIncidence = request.getParameter("ic");

        if (accion == null) {
            response.sendRedirect("Principal");
        } else if (accion.equals("con")) { //conceder 
            if(Conceder(request,response)){
                response.sendRedirect("Informacion?idIncidencia="+idIncidence);
            }
        }else if(accion.equals("aceptar")){
            if(Aceptar(request, response)){
                response.sendRedirect("Informacion?idIncidencia="+idIncidence);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    private boolean Conceder(HttpServletRequest request, HttpServletResponse response) {
        boolean concedido = false;
        
        HttpSession s = request.getSession();
        Integer myRol = (Integer) s.getAttribute("Rol");
        Integer deptoReceptor = Integer.parseInt(request.getParameter("iddc"));
        Integer idIBR = Integer.parseInt(request.getParameter("idbr"));
        
        if (myRol == 2) {
            Integer myDepto = (Integer) s.getAttribute("idDepUser");
            
            if(myDepto == deptoReceptor){ //Es un lider y puede conceder
                
                try{
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    
                    IncidenciaPorEncargado ipe = new IncidenciaPorEncargado();
                    ipe = Operaciones.get(idIBR, new IncidenciaPorEncargado());

                    IncidenciaPorEncargado newIpe = new IncidenciaPorEncargado();
                    newIpe.setIdIBR(idIBR);
                    newIpe.setStatus(2);
                    newIpe.setIdreceptor(ipe.getIdreceptor());
                    newIpe.setIdIncidence(ipe.getIdIncidence());
                    
                    newIpe = Operaciones.actualizar(newIpe.getIdIBR(), newIpe);
                    
                    
                    Operaciones.commit();
                    concedido = true;
                
                }catch(Exception ex){
                    try {
                        Operaciones.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }finally{
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return concedido;
    }
    
    private boolean Aceptar(HttpServletRequest request, HttpServletResponse response){
        boolean aceptada = false;
        Integer idIBR = Integer.parseInt(request.getParameter("idbr"));
        HttpSession s = request.getSession();
        Integer myIdUser = (Integer)s.getAttribute("idUsuario");
        
        
        try{
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            
            IncidenciaPorEncargado ipc = Operaciones.get(idIBR, new IncidenciaPorEncargado());
            
            if(myIdUser == ipc.getIdreceptor()){ //Si la Puede haceptar 
                IncidenciaPorEncargado newIpc = new IncidenciaPorEncargado();
                newIpc.setIdIBR(idIBR);
                newIpc.setStatus(3);
                newIpc.setIdreceptor(ipc.getIdreceptor());
                newIpc.setIdIncidence(ipc.getIdIncidence());
                
                newIpc = Operaciones.actualizar(newIpc.getIdIBR(), newIpc);
                
                Operaciones.commit();
                aceptada = true;
            }
        }catch(Exception ex){
            try {
                Operaciones.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return aceptada;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
