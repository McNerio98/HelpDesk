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
import com.helpdesk.entidades.Empresa;
import com.helpdesk.entidades.Enlace;
import com.helpdesk.entidades.RequisicionPago;
import com.helpdesk.entidades.Usuario;
import com.helpdesk.entidades.UsuarioRequisicion;
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

                Usuario contador = Operaciones.get(DataList.getIdContador(DataList.getIdEmpresa(id)), new Usuario());

                String[][] rs = Operaciones.consultar(cmd, params);
                DataRequisicion dr = new DataRequisicion();
                dr.setFecha(rs[0][0]);
                dr.setSolicitante(rs[1][0]);
                if (request.getSession().getAttribute("empReq") != null) {
                    int ide = (int) request.getSession().getAttribute("empReq");
                    Empresa emx = Operaciones.get(ide, new Empresa());
                    dr.setEmpresa(emx.getNombre());
                    dr.setDepto("Requisicion Pago");
                } else {
                    dr.setEmpresa(rs[2][0]);
                    dr.setDepto(rs[3][0]);
                }

                if (contador.getIdUser() != 0) {
                    dr.setContador(contador.getFirsName() + " " + contador.getLastName());
                }
                request.setAttribute("DataGeneral", dr);

            } catch (Exception e) {
                //Redirecciona al error 
                Logger.getLogger(Requisiciones.class.getName()).log(Level.SEVERE, null, e);
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Requisiciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (request.getSession().getAttribute("resultado") != null) {
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
            case "update": {
                if (updateRequisicion(request, response)) {
                    request.getSession().setAttribute("resultado", 1); //se inserto  
                } else {
                    request.getSession().setAttribute("resultado", 2); //No se inserto   
                }
                response.sendRedirect("RequisicionInfo?idReq=" + request.getParameter("idReq"));
            }
        }

    }

    private boolean updateRequisicion(HttpServletRequest request, HttpServletResponse response) {
        boolean estado = false;
        String jsonReq = request.getParameter("JsonReq");
        String idRequisicion = request.getParameter("idReq");

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
            List<DetalleAux> listDetallesAux = objectMapper.readValue(jsonReq, new TypeReference<List<DetalleAux>>() {
            });

            if (listDetallesAux != null && listDetallesAux.size() == 0) {
                throw new Exception("Alteracion en el Json");
            }

            BigDecimal montoTotal = new BigDecimal(0);

            for (int i = 0; i < listDetallesAux.size(); i++) {
                montoTotal = montoTotal.add(listDetallesAux.get(i).getMonto());
            }

            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();

            RequisicionPago rg = Operaciones.get(Integer.parseInt(idRequisicion), new RequisicionPago());
            rg.setTotal(montoTotal);
            rg = Operaciones.actualizar(rg.getIdRequisicion(), rg);

            //Actualizando detalles 
            for (int i = 0; i < listDetallesAux.size(); i++) {
                DetalleRequisicion detalle = new DetalleRequisicion();
                int IdAux = listDetallesAux.get(i).getId();
                if (IdAux != 0) { //Es una actualizacion
                    detalle = Operaciones.get(IdAux, new DetalleRequisicion());
                    detalle.setDescripcion(listDetallesAux.get(i).getDescripcion());
                    detalle.setMonto(listDetallesAux.get(i).getMonto());
                    detalle = Operaciones.actualizar(detalle.getIdDetalle(), detalle);
                } else { // Es un nuevo detalle 
                    detalle.setDescripcion(listDetallesAux.get(i).getDescripcion());
                    detalle.setMonto(listDetallesAux.get(i).getMonto());
                    detalle.setIdRequisicion(Integer.parseInt(idRequisicion));
                    detalle = Operaciones.insertar(detalle);
                }
            }

            Operaciones.commit();
            estado = true;

        } catch (Exception e) {
            Logger.getLogger(Incidencias.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Requisiciones.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return estado;
    }

    private boolean nuevaRequisicion(HttpServletRequest request, HttpServletResponse response) {
        boolean estado = false;
        String jsonReq = request.getParameter("JsonReq");
        String jsonLinks = request.getParameter("JsonLinks");
        Integer idU = (Integer) request.getSession().getAttribute("idUsuario");
        Integer prioridad = Integer.parseInt(request.getParameter("slcPrioridad"));
        String finalDate = request.getParameter("finalDate");

        int idReqs = 0;
        ArrayList<Usuario> listLiders = new ArrayList<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
            List<DetalleAux> listDetallesAux = objectMapper.readValue(jsonReq, new TypeReference<List<DetalleAux>>() {
            });

            if (listDetallesAux != null && listDetallesAux.size() == 0) {
                throw new Exception("Alteracion en el Json");
            }

            BigDecimal montoTotal = new BigDecimal(0);

            for (int i = 0; i < listDetallesAux.size(); i++) {
                montoTotal = montoTotal.add(listDetallesAux.get(i).getMonto());
            }

            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();

            RequisicionPago rg = new RequisicionPago();
            rg.setIdCreador((int) request.getSession().getAttribute("idUsuario"));
            //El autorizador sera hasta que un lider tome la requisicion 
            rg.setIdAutorizador(null);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date dt = new Date();
            rg.setFecha(new Timestamp(dt.getTime()));
            rg.setEstado(Enums.ESTADO_REQ.SOLICITADA);
            if (request.getSession().getAttribute("empReq") != null) {
                int ide = (int) request.getSession().getAttribute("empReq");
                
                rg.setIdEmpresa(ide);
                rg.setIdDepto(7);//se debe actualizar
            } else {
                rg.setIdEmpresa(DataList.getIdEmpresa(idU));
                rg.setIdDepto(DataList.getIdDepto(idU));
            }

            rg.setTotal(montoTotal);
            rg.setPrioridad(prioridad);
            rg.setIdContador(DataList.getIdContador(DataList.getIdEmpresa(idU)));
            rg.setFechaEstimada(new Timestamp(simpleDateFormat.parse(finalDate).getTime()));

            rg = Operaciones.insertar(rg);

            if (jsonLinks != null) {//Hay enlaces
                List<Enlace> listEnlaces = objectMapper.readValue(jsonLinks, new TypeReference<List<Enlace>>() {
                });

                for (int i = 0; i < listEnlaces.size(); i++) {
                    Enlace e = new Enlace();
                    e.setDescripcion(listEnlaces.get(i).getDescripcion());
                    e.setEnlace(listEnlaces.get(i).getEnlace());
                    e.setIdRequisicion(rg.getIdRequisicion());
                    Operaciones.insertar(e);
                }
            }

            String query = "select a.idusuario from usuarioreqbyempresas a, usuariosrequisicion b where a.idusuario=b.idusuario and b.idrol=6";

            String[][] array = Operaciones.consultar(query, null);

            for (int i = 0; i < array[0].length; i++) {
                Usuario user = new Usuario();
                user = Operaciones.get(Integer.parseInt(array[0][i]), new Usuario());
                listLiders.add(user);
            }

            idReqs = rg.getIdRequisicion();

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

        if (estado) {
            DataList.SendNotificationsToLiders(listLiders, idReqs);
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
