/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.controladores;

import com.helpdesk.conexion.Conexion;
import com.helpdesk.conexion.ConexionPool;
import com.helpdesk.entidades.IncidenciaPorEncargado;
import com.helpdesk.entidades.Nota;
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
        Integer idIBR = Integer.parseInt(request.getParameter("idbr"));
        HttpSession s = request.getSession();

        if (accion == null || idIBR == null || idIncidence == null) {
            response.sendRedirect("Principal");
        } else{
            int newStatus = 0;
            switch (accion) {
                case "conceder":
                    newStatus = Enums.ESTADO.ASIGNADA;
                    break;
                case "aceptar":
                    newStatus = Enums.ESTADO.ACEPTADA;
                    break;
                case "finalizar":
                    newStatus = Enums.ESTADO.FINALIZADA;
                    break;
                case "rechazar":
                    newStatus = Enums.ESTADO.RECHAZADA;
                    break;
                case "denegar":
                    newStatus = Enums.ESTADO.DENEGADA;
                    break;
                case "reasignar": // Aqui puede que sea de su depto o de otro verificar 
                    newStatus = Enums.ESTADO.SOLICITADA;
                    break;
            }
            if (newStatus != 0) {
                if (setEstado(request, response, idIBR, newStatus)) {
                    response.sendRedirect("Informacion?idIncidencia=" + idIncidence);
                } else {
                    response.getWriter().print("No tiene Permisos");
                }
            }

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        String idIncidence = request.getParameter("ic");
        String idIBR = request.getParameter("idbr");
        String txtContenido = request.getParameter("txtContenido");
        HttpSession s = request.getSession();

        if (accion.equals("rechazar") || accion.equals("denegar")) {
            Nota nt = new Nota();
            nt.setDescription(txtContenido);
            nt.setIdIncidence(Integer.parseInt(idIncidence));
            

            if (accion.equals("rechazar")) {
                nt.setNotetype(Enums.NOTA.RECHAZO);
                s.setAttribute("nuevaNota", nt);
                response.sendRedirect("Procesos?accion=rechazar&ic=" + idIncidence + "&idbr=" + idIBR);
            } else {
                nt.setNotetype(Enums.NOTA.DENEGACION);
                s.setAttribute("nuevaNota", nt);
                response.sendRedirect("Procesos?accion=denegar&ic=" + idIncidence + "&idbr=" + idIBR);
            }
            
            
            
        }else if(accion.equals("verificar")){
            response.setContentType("text/plain");
            PrintWriter out = response.getWriter();
            Integer myIdUser = (Integer) s.getAttribute("idUsuario");
            if(verificarActivas(myIdUser)){
                out.print("true");
            }else{
                out.print("false");
            }
        }

    }

    /* Esta funcion cambio el estado de la incidencia 
    * Verifica si tienen permisos el que la modifica, 
    * Devuelve False si no los posee
     */
    private boolean setEstado(HttpServletRequest request, HttpServletResponse response, Integer idIBR, int newEstado) {
        boolean seteado = false;
        HttpSession s = request.getSession();
        Integer myRol = (Integer) s.getAttribute("Rol");

        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();

            IncidenciaPorEncargado ipe = new IncidenciaPorEncargado();
            ipe = Operaciones.get(idIBR, new IncidenciaPorEncargado());

            IncidenciaPorEncargado newIpe = new IncidenciaPorEncargado();
            newIpe.setIdIBR(idIBR);
            newIpe.setIdreceptor(ipe.getIdreceptor());
            newIpe.setIdIncidence(ipe.getIdIncidence()); //Los otros campos dependeran de la accion 

            switch (newEstado) {
                case Enums.ESTADO.ASIGNADA: {
                    Integer myDepto = (Integer) s.getAttribute("idDepUser");
                    Integer deptoReceptor = Integer.parseInt(request.getParameter("iddc"));
                    if (myRol == 2 && deptoReceptor == myDepto && ipe.getStatus() == Enums.ESTADO.SOLICITADA) {
                        newIpe.setStatus(newEstado);
                        seteado = true;
                    }
                    break;
                }
                case Enums.ESTADO.ACEPTADA: {
                    Integer myIdUser = (Integer) s.getAttribute("idUsuario");
                    if (myIdUser == ipe.getIdreceptor() && ipe.getStatus() == Enums.ESTADO.ASIGNADA) {
                        newIpe.setStatus(newEstado);
                        seteado = true;
                    }
                    break;
                }

                case Enums.ESTADO.RECHAZADA: {
                    Integer myIdUser = (Integer) s.getAttribute("idUsuario");
                    Nota nt = (Nota) s.getAttribute("nuevaNota");
                    if (myIdUser == ipe.getIdreceptor() && ipe.getStatus() == Enums.ESTADO.ASIGNADA && nt != null) {
                        //Si existe el objeto nota se procede a insertarlo y a realizar el cambio en la IBR 
                        nt.setIdHolder(myIdUser);//Se agrega el id del Titular 
                        nt = Operaciones.insertar(nt); //Se inserta 
                        newIpe.setStatus(newEstado);
                        seteado = true;
                        s.removeAttribute("nuevaNota");
                    }
                    break;
                }

                case Enums.ESTADO.DENEGADA: {
                    Integer myIdUser = (Integer) s.getAttribute("idUsuario");
                    Integer myDepto = (Integer) s.getAttribute("idDepUser");
                    Nota nt = (Nota) s.getAttribute("nuevaNota");
                    Integer deptoReceptor = Integer.parseInt(request.getParameter("iddc"));
                    if (myRol == 2 && deptoReceptor == myDepto && ipe.getStatus() == Enums.ESTADO.SOLICITADA && nt != null) {
                        nt.setIdHolder(myIdUser);//Se agrega el id del Titular 
                        nt = Operaciones.insertar(nt); //Se inserta 
                        newIpe.setStatus(newEstado);
                        seteado = true;
                        s.removeAttribute("nuevaNota");
                    }
                    break;
                }
            }

            if (seteado) {
                newIpe = Operaciones.actualizar(newIpe.getIdIBR(), newIpe);
                Operaciones.commit();
            }

        } catch (Exception ex) {
            seteado = false;
            try {
                Operaciones.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return seteado;
    }

    /*Esta funcion verifica si el usuario que esta aceptando 
    la incidencia posee una en curso, de ser asi devuleve true 
     */
    private boolean verificarActivas(Integer idReceptor) {
        boolean cola = false;
        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            String sql = "select count(*) from incidencebyreceptor where status = 3 and idreceptor = " + idReceptor;
            String[][] rs = Operaciones.consultar(sql, null);
            Integer numActivas = Integer.parseInt(rs[0][0]);
            if(numActivas != 0){
                cola = true;
            }
        } catch (Exception ex) {
            Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex2) {
                Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex2);
            }
        }
        return cola;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
