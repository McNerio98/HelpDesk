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
                DataRequisicion dataGeneral = getGeneralData(idRequisicion);
                dataGeneral.setNumRegistros(String.valueOf(LstDetalles.size()));
                //Detalles de Requisicion
                //Comentarios 
                request.setAttribute("generalData", dataGeneral);
                request.setAttribute("LstDetalles", LstDetalles);
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
    }

    private DataRequisicion getGeneralData(Integer idReq) {
        
        DataRequisicion dt = new DataRequisicion();
        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            RequisicionPago rg = Operaciones.get(idReq, new RequisicionPago());
            List<Object> params = new ArrayList();
            params.add(idReq);
            params.add(idReq);
            params.add(idReq);
             
            String cmd ="select concat(u1.firstname,' ',u1.lastname) creador, to_char(rg.fecha,'dd-MM-yyyy HH:MI') fecha, \n"
                        +"rg.total, rg.estado, e.nombre, d.deptoname, (select concat(u2.firstname,' ', u2.lastname) from users u2, \n"
                        +"requisicionespagos rg2 where rg2.idautorizador = u2.iduser and rg2.idrequisicion =?) Superior, \n"
                        +"(select concat(u3.firstname,' ', u3.lastname) from users u3,requisicionespagos rg3 \n"
                        +"where rg3.idcontador = u3.iduser and rg3.idrequisicion =?) Contador from requisicionespagos rg, \n"
                        +"users u1, empresas e, departments d where rg.idcreador = u1.iduser and rg.idempresa = e.idempresa \n"
                        +"and rg.iddepto = d.iddepto and idrequisicion = ? ";
            String[][] rs = Operaciones.consultar(cmd, params);                        
            
            dt.setSolicitante(rs[0][0]);
            dt.setFecha(rs[1][0]);
            dt.setMontoTotal(rs[2][0]);
            dt.setEstado(Integer.parseInt(rs[3][0]));
            dt.setEmpresa(rs[4][0]);
            dt.setDepto(rs[5][0]);
            dt.setSuperior(rs[6][0]);
            dt.setContador(rs[7][0]);
            
        } catch (Exception e) {
            Logger.getLogger(RequisicionInfo.class.getName()).log(Level.SEVERE, null, e);
            dt = null;
        }finally{
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(RequisicionInfo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return dt;
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
