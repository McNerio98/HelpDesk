/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.controladores;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.helpdesk.conexion.Conexion;
import com.helpdesk.conexion.ConexionPool;
import com.helpdesk.entidades.RequisicionPago;
import com.helpdesk.operaciones.Operaciones;
import com.helpdesk.utilerias.DataComentario;
import com.helpdesk.utilerias.DataList;
import com.helpdesk.utilerias.DataRequisicion;
import com.helpdesk.utilerias.DetalleAux;
import com.helpdesk.utilerias.Enums;
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
                String accion = request.getParameter("accion");
                if (accion == null) {
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
                    
                    if (request.getSession().getAttribute("resultado") != null) {
                        request.setAttribute("resultado", request.getSession().getAttribute("resultado"));
                        request.getSession().removeAttribute("resultado");
                    }
                    request.getRequestDispatcher("Def_Requisicion.jsp").forward(request, response);
                } else if (accion.equals("update")) {
                    RequisicionPago pg = getRequisicion(idRequisicion);
                    if (pg.getEstado() == Enums.ESTADO_REQ.REVISION || pg.getEstado() == Enums.ESTADO_REQ.SOLICITADA) {
                        ArrayList<DetalleAux> listDetalles = getListDetalles(idRequisicion);
                        DataRequisicion dataGeneral = DataList.getGeneralData(idRequisicion);
                        dataGeneral.setNumRegistros(String.valueOf(listDetalles.size()));
                        request.setAttribute("DataGeneral", dataGeneral);
                        request.setAttribute("lstDetalles", listDetalles);
                        request.setAttribute("idReq", idRequisicion);
                        request.getRequestDispatcher("NuevaRequisicion.jsp").forward(request, response);                        
                    } else {
                        response.sendRedirect("PrincipalRequisicion");
                    }
                    
                }
                
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
        String idRequisicion = request.getParameter("idReq");
        
        if (accion == null || idRequisicion == null) {
            response.getWriter().print("false");
        } else if (accion.equals("getAllMsg")) {
            //Obtener array con mensajes
            ArrayList<DataComentario> listMessages = getAllComentarios(Integer.parseInt(idRequisicion));
            request.setAttribute("listMessages", listMessages);
            request.getRequestDispatcher("_loadChat.jsp").forward(request, response);
        } else if (accion.equals("update")) {
            if (updateRequisicion(request, response)) {
                //aciones mandar error a la sesion
            }            
            
        }
    }
    
    private boolean updateRequisicion(HttpServletRequest request, HttpServletResponse response) {
        Integer idReq = Integer.parseInt(request.getParameter("idReq"));
        //Obtener el nuevo json 
        //Los que tengan id = 0 son nuevos detalles y se agregan 
        String jsonReq = request.getParameter("JsonReq");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
            List<DetalleAux> listDetallesAux = objectMapper.readValue(jsonReq, new TypeReference<List<DetalleAux>>() {
            });
            //Aqui me quede
        } catch (Exception e) {
            
        } finally {
            
        }
        
        return false;
    }
    
    private ArrayList<DataComentario> getAllComentarios(Integer idReq) {
        ArrayList<DataComentario> ListaMsg = new ArrayList<DataComentario>();
        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            String cmd = "select concat(u.firstname,' ',u.lastname) titular,to_char(c.fecha,'dd-MM-yyyy HH:MI AM'), \n"
                    + "c.contenido msg,u2.idrol from comentarios c inner join users u on u.iduser = c.idcreador \n"
                    + "inner join usuariosrequisicion u2 on u2.idusuario = c.idcreador \n"
                    + "where c.idrequisicion = ? order by c.idcomentario asc ";
            List<Object> params = new ArrayList();
            params.add(idReq);
            
            String result[][] = Operaciones.consultar(cmd, params);
            
            if (result != null) {
                for (int i = 0; i < result[0].length; i++) {
                    DataComentario com = new DataComentario();
                    com.setTitular(result[0][i]);
                    com.setFecha(result[1][i]);
                    com.setMensaje(result[2][i]);
                    com.setRol(Integer.parseInt(result[3][i]));
                    ListaMsg.add(com);
                }
            } else {
                ListaMsg = null;
            }
        } catch (Exception e) {
            Logger.getLogger(RequisicionInfo.class.getName()).log(Level.SEVERE, null, e);
            
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(RequisicionInfo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return ListaMsg;
    }
    
    private RequisicionPago getRequisicion(Integer idRequisicion) {
        RequisicionPago pg = new RequisicionPago();
        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            pg = Operaciones.get(idRequisicion, new RequisicionPago());
        } catch (Exception e) {
            Logger.getLogger(RequisicionInfo.class.getName()).log(Level.SEVERE, null, e);
            pg = null;
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(RequisicionInfo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return pg;
    }
    
    private ArrayList<DetalleAux> getListDetalles(Integer idReq) {
        ArrayList<DetalleAux> LstDetalles = new ArrayList<>();
        
        String cmd = "select iddetalle,descripcion,monto from detallesrequisiciones where idrequisicion = ? order by iddetalle asc";
        List<Object> params = new ArrayList();
        params.add(idReq);
        
        try {
            
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            String[][] rs = Operaciones.consultar(cmd, params);
            for (int i = 0; i < rs[0].length; i++) {
                DetalleAux detalle = new DetalleAux();
                detalle.setId(Integer.parseInt(rs[0][i]));
                detalle.setDescripcion(rs[1][i]);
                detalle.setMonto(new BigDecimal(rs[2][i]));
                LstDetalles.add(detalle);
            }
        } catch (Exception e) {
            Logger.getLogger(RequisicionInfo.class.getName()).log(Level.SEVERE, null, e);
            LstDetalles = null;
        } finally {
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
