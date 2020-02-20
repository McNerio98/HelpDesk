/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.controladores;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.helpdesk.utilerias.DetalleAux;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.helpdesk.conexion.Conexion;
import com.helpdesk.conexion.ConexionPool;
import com.helpdesk.entidades.DetalleRequisicion;
import com.helpdesk.entidades.RequisicionPago;
import com.helpdesk.operaciones.Operaciones;
import com.helpdesk.utilerias.DataList;
import com.helpdesk.utilerias.DataRequisicion;
import com.helpdesk.utilerias.Enums;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ZEUS
 */
public class Requisiciones extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion == null) {
            
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                String cmd = "select to_char(current_date,'dd-MM-yyyy') actual,concat(u.firstname, ' ',u.lastname) sol, \n"
                        + "e.nombre, d.deptoname from empresas e, users u, usuarioreqbyempresas uem, deptobyusers du, departments d \n"
                        + "where u.iduser = uem.idusuario and uem.idempresa = e.idempresa and d.iddepto = du.iddepto and u.iduser = du.iduser \n"
                        + "and u.iduser = ? ";
                
                Integer id = (Integer) request.getSession().getAttribute("idUsuario");
                List<Object> params = new ArrayList();
                params.add(id);                
                
                String[][] rs = Operaciones.consultar(cmd, params);
                DataRequisicion dr = new DataRequisicion();
                dr.setFecha(rs[0][0]);
                dr.setSolicitante(rs[1][0]);
                dr.setEmpresa(rs[2][0]);
                dr.setDepto(rs[3][0]);
                request.setAttribute("DataGeneral", dr);

                
            } catch (Exception e) {
                //Redirecciona al error 
                Logger.getLogger(Requisiciones.class.getName()).log(Level.SEVERE, null, e);                
            }finally{
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Requisiciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(request.getSession().getAttribute("resultado")!=null){
                request.setAttribute("resultado", request.getSession().getAttribute("resultado"));
                request.getSession().removeAttribute("resultado");
            }
            
            request.getRequestDispatcher("NuevaRequisicion.jsp").forward(request, response);
        }
        
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        switch (accion) {
            case "nueva": {
                if (nuevaRequisicion(request, response)) {
                    request.getSession().setAttribute("resultado", 1); //se inserto   
                } else {
                    request.getSession().setAttribute("resultado", 2); //No se inserto   
                }
                response.sendRedirect("Requisiciones");
                break;
            }
        }
        
    }
    
    private boolean nuevaRequisicion(HttpServletRequest request, HttpServletResponse response) {
        boolean estado = false;
        String jsonReq = request.getParameter("JsonReq");
        Integer idU = (Integer)request.getSession().getAttribute("idUsuario");
        
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
            List<DetalleAux> listDetallesAux = objectMapper.readValue(jsonReq, new TypeReference<List<DetalleAux>>() {
            });
            
            if (listDetallesAux.size() == 0) {
                throw new Exception("Alteracion en el Json");
            }
            
            BigDecimal montoTotal = new BigDecimal(0);
            
            for (int i = 0; i < listDetallesAux.size(); i++) {
                montoTotal = montoTotal.add(listDetallesAux.get(i).getMonto());
                System.out.println("montoTotal = " + montoTotal);
                System.out.println("montoUnit = " + listDetallesAux.get(i).getMonto());
            }
            
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            
            RequisicionPago rg = new RequisicionPago();
            rg.setIdCreador((int) request.getSession().getAttribute("idUsuario"));
            //El autorizador sera hasta que un lider tome la requisicion 
            rg.setIdAutorizador(null); //Dato queado
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date dt = new Date();
            rg.setFecha(new Timestamp(dt.getTime()));
            rg.setEstado(Enums.ESTADO_REQ.SOLICITADA);
            rg.setIdEmpresa(DataList.getIdEmpresa(idU));
            rg.setIdDepto(DataList.getIdDepto(idU));
            rg.setTotal(montoTotal);
            
            rg = Operaciones.insertar(rg);
            
            if (rg.getIdRequisicion() != 0) {
                //creando detalles 
                int idReq = rg.getIdRequisicion();
                for (int i = 0; i < listDetallesAux.size(); i++) {
                    DetalleRequisicion detalle = new DetalleRequisicion();
                    detalle.setDescripcion(listDetallesAux.get(i).getDescripcion());
                    detalle.setMonto(listDetallesAux.get(i).getMonto());
                    detalle.setIdRequisicion(idReq);
                    Operaciones.insertar(detalle);
                }
                Operaciones.commit();
                estado = true;
            }
        } catch (Exception e) {
            Logger.getLogger(Incidencias.class.getName()).log(Level.SEVERE, null, e);
            try {
                Operaciones.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(Requisiciones.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex2) {
                Logger.getLogger(Requisiciones.class.getName()).log(Level.SEVERE, null, ex2);
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
