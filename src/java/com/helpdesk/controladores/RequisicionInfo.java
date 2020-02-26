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
import com.helpdesk.utilerias.DataList;
import com.helpdesk.utilerias.DataRequisicion;
import com.helpdesk.utilerias.DetalleAux;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
public class RequisicionInfo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer idRequisicion = Integer.parseInt(request.getParameter("idReq"));

        if (idRequisicion == null) {
            response.sendRedirect("PrincipalRequisicion");
        } else {
            boolean grant = DataList.permisosSobreRequisicion(idRequisicion, request.getSession());
            if (grant) {
                //Informacion General 
                ArrayList<DetalleAux> LstDetalles = getListDetalles(idRequisicion);
                DataRequisicion dataGeneral = DataList.getGeneralData(idRequisicion);
                RequisicionPago pg = getRequisicion(idRequisicion);
                
                // Este objeto se usara para validar las acciones 

                //Comentarios 
                request.setAttribute("pg", pg);
                request.setAttribute("generalData", dataGeneral);
                request.setAttribute("LstDetalles", LstDetalles);
                request.setAttribute("idReq", idRequisicion);
                
                if(request.getSession().getAttribute("resultado")!=null){
                    request.setAttribute("resultado", request.getSession().getAttribute("resultado"));
                    request.getSession().removeAttribute("resultado");
                }
                if(request.getSession().getAttribute("idReqForPDF")!=null){
                    request.getSession().setAttribute("pdfGenerate", "true");
                }
                
                request.getRequestDispatcher("Def_Requisicion.jsp").forward(request, response);
            } else {
                //No tiene permisos o no existe 
                response.sendRedirect("PrincipalRequisicion");
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        
        if(accion==null){
            response.getWriter().print("false");
        }else if(accion.equals("getAllMsg")){
            request.getRequestDispatcher("_loadChat.jsp").forward(request, response);
        }
    }

    private RequisicionPago getRequisicion(Integer idRequisicion){
            RequisicionPago pg = new RequisicionPago();
                try{
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    pg = Operaciones.get(idRequisicion, new RequisicionPago());
                }catch(Exception e){
                    Logger.getLogger(RequisicionInfo.class.getName()).log(Level.SEVERE, null, e);   
                    pg = null;
                }finally{
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(RequisicionInfo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }        
    return pg;            
    }
    
    private ArrayList<DetalleAux> getListDetalles(Integer idReq){
            ArrayList<DetalleAux> LstDetalles = new ArrayList<>();
        
        String cmd = "select descripcion,monto from detallesrequisiciones where idrequisicion = ? order by iddetalle asc";
        List<Object> params = new ArrayList();
        params.add(idReq);                    
        
        
        try{
        
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            String[][] rs = Operaciones.consultar(cmd, params);                                    
            for(int i=0 ; i< rs[0].length; i++){
                DetalleAux detalle = new DetalleAux();
                detalle.setDescripcion(rs[0][i]);
                detalle.setMonto(new BigDecimal(rs[1][i]));
                LstDetalles.add(detalle);
            }
        }catch(Exception e){
            Logger.getLogger(RequisicionInfo.class.getName()).log(Level.SEVERE, null, e);
            LstDetalles = null;
        }finally{
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(RequisicionInfo.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        
        return LstDetalles;
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
