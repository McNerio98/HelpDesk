/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.controladores;

import com.helpdesk.conexion.Conexion;
import com.helpdesk.conexion.ConexionPool;
import com.helpdesk.entidades.Departamento;
import com.helpdesk.entidades.Incidencia;
import com.helpdesk.entidades.IncidenciaPorEncargado;
import com.helpdesk.entidades.Usuario;
import com.helpdesk.operaciones.Operaciones;
import com.helpdesk.utilerias.DataList;
import com.helpdesk.utilerias.Enums;
import com.helpdesk.utilerias.htmlTemplate;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ZEUS
 */
//Esta Servet Esta relacionado a la accion de crear una incidencia 
public class Incidencias extends HttpServlet {

    public void sendNotification(List<Object> params) {
           
            htmlTemplate html = new htmlTemplate();
            html.difineTag(
                    "<h1>Hola, "+((Usuario) params.get(0)).getFirsName()
                    +" "+ ((Usuario) params.get(0)).getLastName() +"</h1>"
            );
            html.difineTag(
                    "<strong>"+((Usuario) params.get(1)).getFirsName()+" "
                    +((Usuario) params.get(1)).getLastName()+"</strong>"
                    +" del Departamento de " + ((Departamento) params.get(2)).getDeptoName()
                    +" te ha asignado una incidencia<br>"
            );
            html.difineTag("<h3>"+(String) params.get(3)+"</h3>");
            html.difineTag("<p style='text-align:justify'>"+(String) params.get(4)+"</p>");
            
            if(JavaMail.SendMessage(((Usuario) params.get(0)).getEmail(), "Tienes una nueva incidencia", html.RenderHTML()))
            {
                System.out.print("Notificacion Enviada");
            }else{
                System.out.print("Ha ocurrido un problema");
            }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        String idIncidencia = request.getParameter("ic");
        if (accion == null) {
            if (request.getSession().getAttribute("resultado") != null) {
                request.setAttribute("resultado", request.getSession().getAttribute("resultado"));
                request.getSession().removeAttribute("resultado");
            }
        } else if (accion.equals("update")) {
            Incidencia ie = obtenerIncidencia(Integer.parseInt(idIncidencia));
            //Verificando permisos
            int myIdUser = (int) request.getSession().getAttribute("idUsuario");
            if (ie != null && (ie.getStatus() == Enums.ESTADO.RECHAZADA || ie.getStatus() == Enums.ESTADO.DENEGADA) && ie.getIdCreator() == myIdUser) {
                request.setAttribute("ie", ie);
                request.setAttribute("accionProcess", "update");
            } else {
                request.getSession().setAttribute("statusUpdate", 2);
                response.sendRedirect("Informacion?idIncidencia=" + idIncidencia);
                return;
            }

        }

        request.setAttribute("DeptosList", DataList.getDeptosByEmpresa(2));
        request.setAttribute("ClasfList", DataList.getAllClassifications());
        request.getRequestDispatcher("NuevaIncidencia.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idIncidencia = request.getParameter("txtIdIncidencia");
        String accion = "nueva";

        if (idIncidencia != null && !"".equals(idIncidencia)) {
            accion = "reasignar";
        }

        PrintWriter out = response.getWriter();

        switch (accion) {
            case "nueva": {
                if (insertarIncidencia(request, response)) {
                    request.getSession().setAttribute("resultado", 2); //Se inserto 
                    
                } else {
                    request.getSession().setAttribute("resultado", 1); //No se inserto 
                }
                response.sendRedirect("Incidencias");
                break;
            }

            case "reasignar": {
                if (reasignar(request, response)) {
                    request.getSession().setAttribute("statusUpdate", 1); //se Actualizo  
                } else {
                    request.getSession().setAttribute("statusUpdate", 2); //No se Actualizo  
                }
                response.sendRedirect("Informacion?idIncidencia=" + idIncidencia);
                break;
            }

        }

    }

    private boolean reasignar(HttpServletRequest request, HttpServletResponse response) {
        boolean reasing = false;
        int idIncidencia = Integer.parseInt(request.getParameter("txtIdIncidencia"));
        int nuevoReceptor = Integer.parseInt(request.getParameter("txtReceptor"));
        int idRol = (int) request.getSession().getAttribute("Rol");

        Incidencia inc = obtenerIncidencia(idIncidencia);

        if (inc != null) { //Si existe una incidencia con ese id 
            int idCreador = inc.getIdCreator(); //aqui ya se valido en otra parte 
            int status = Enums.ESTADO.ASIGNADA;

            if (idRol == 2) { //Si es un lider verificar si el receptor pertenece al mismo depto
                if (!SameDepto(idCreador, nuevoReceptor)) {
                    status = Enums.ESTADO.SOLICITADA; //Se agrega como una solicitud             
                }
            }

            IncidenciaPorEncargado ibr = new IncidenciaPorEncargado();
            ibr.setStatus(status);
            ibr.setIdreceptor(nuevoReceptor);
            ibr.setIdIncidence(idIncidencia);

            //Actualizando datos de la incidencia 
            inc.setStatus(status);
            inc.setIdreceptor(nuevoReceptor);

            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                ibr = Operaciones.insertar(ibr);

                inc = Operaciones.actualizar(inc.getIdIncidence(), inc);

                Operaciones.commit();
                reasing = true;
            } catch (Exception e) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex) {
                    Logger.getLogger(Incidencias.class.getName()).log(Level.SEVERE, null, ex);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex2) {
                    Logger.getLogger(Incidencias.class.getName()).log(Level.SEVERE, null, ex2);
                }
            }
        }

        return reasing;
    }

    private Incidencia obtenerIncidencia(int idIncidencia) {
        Incidencia inc = new Incidencia();
        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);

            inc = Operaciones.get(idIncidencia, new Incidencia());
        } catch (Exception e) {
            Logger.getLogger(Incidencias.class.getName()).log(Level.SEVERE, null, e);
            inc = null;
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Incidencias.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return inc;
    }

    private boolean insertarIncidencia(HttpServletRequest request, HttpServletResponse response) {
        boolean estado = true;

        String titulo = request.getParameter("txtTitle");
        String idclasf = request.getParameter("slcClasificacion");
        String prioridad = request.getParameter("slcPrioridad");
        String desc = request.getParameter("txtDescripcion");
        String idReceptor = request.getParameter("txtReceptor");
        String fechafinal = request.getParameter("dateFechaFinal");
        int idCreador = (int) request.getSession().getAttribute("idUsuario");
        int idDepto = 0;

        int status = Enums.ESTADO.ASIGNADA;
        int idRol = (int) request.getSession().getAttribute("Rol");

        if (idRol == 2) { //Si es un lider verificar si el receptor pertenece al mismo depto
            if (!SameDepto(idCreador, Integer.parseInt(idReceptor))) {
                status = Enums.ESTADO.SOLICITADA; //Se agrega como una solicitud             
            }
        }

        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();

            if (idRol == 2) { //para el lider sera en el depto que tiene a cargo 
                idDepto = DataList.getIdDepto(idCreador);
            } else if (idRol == 1) { //Para gerente sera el que halla selecionado 
                idDepto = Integer.parseInt(request.getParameter("slcDeptoIncidence"));
            }

            Incidencia icn = new Incidencia();
            icn.setTitle(titulo);
            icn.setDescription(desc);
            icn.setCreationDay(new Timestamp(System.currentTimeMillis()));
            icn.setTotalCost(BigDecimal.valueOf(0.0));
            icn.setPriority(Integer.parseInt(prioridad));
            icn.setIdClassification(Integer.parseInt(idclasf));
            icn.setIdCreator(idCreador);
            icn.setIdDepto(idDepto);
            icn.setStatus(status);
            icn.setIdreceptor(Integer.parseInt(idReceptor));

            //Se establece la fecha si la seleciono el usuario 
            if (fechafinal != null && !"".equals(fechafinal)) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = simpleDateFormat.parse(fechafinal);
                icn.setFinalDate(new Timestamp(date.getTime()));
            }

            icn = Operaciones.insertar(icn);

            IncidenciaPorEncargado ixp = new IncidenciaPorEncargado();
            ixp.setStatus(status);
            ixp.setIdreceptor(Integer.parseInt(idReceptor));
            ixp.setIdIncidence(icn.getIdIncidence());

            ixp = Operaciones.insertar(ixp);
            
            ///Se envia la notificacion al correo
            List<Object> params = new ArrayList<>();
            //Objecto receptor
            Usuario recp = Operaciones.get(Integer.parseInt(idReceptor), new Usuario());
            params.add(recp);
            //Objecto emisor
            Usuario emi = Operaciones.get(idCreador, new Usuario());
            params.add(emi);
            //Objecto departamento
            Departamento depp = Operaciones.get(DataList.getIdDepto(idCreador), new Departamento());
            params.add(depp);
            /// String de titulo
            params.add(titulo);
            ///String de descripcion
            params.add(desc);
            this.sendNotification(params);
            Operaciones.commit();
        } catch (Exception ex) {
            estado = false;
            try {
                Operaciones.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Incidencias.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Incidencias.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return estado;
    }

    private boolean SameDepto(int a, int b) { //id de Creador e id de receptor 
        boolean sm = false;

        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Integer da = DataList.getIdDepto(a);
            Integer db = DataList.getIdDepto(b);

            if (da == db) {
                sm = true;
            }
        } catch (Exception ex) {
            Logger.getLogger(Incidencias.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex1) {
                Logger.getLogger(Incidencias.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

        return sm;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
