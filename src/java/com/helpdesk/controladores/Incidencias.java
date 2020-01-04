/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.controladores;

import com.helpdesk.conexion.Conexion;
import com.helpdesk.conexion.ConexionPool;
import com.helpdesk.entidades.Incidencia;
import com.helpdesk.entidades.IncidenciaPorEncargado;
import com.helpdesk.entidades.Usuario;
import com.helpdesk.operaciones.Operaciones;
import com.helpdesk.utilerias.DataList;
import com.helpdesk.utilerias.Enums;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion == null) {
            if (request.getSession().getAttribute("resultado") != null) {
                request.setAttribute("resultado", request.getSession().getAttribute("resultado"));
                request.getSession().removeAttribute("resultado");
            }            
            request.setAttribute("DeptosList", DataList.getAllDeptos());
            request.setAttribute("ClasfList", DataList.getAllClassifications());
            request.getRequestDispatcher("NuevaIncidencia.jsp").forward(request, response);
        }else if(accion.equals("update")){
            //Se procede a obtener la incidencia 
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        PrintWriter out = response.getWriter();

        switch (accion) {
            case "nueva": {
                if(insertarIncidencia(request, response)){
                    request.getSession().setAttribute("resultado", 2); //Se inserto 
                }else{
                    request.getSession().setAttribute("resultado", 1); //No se inserto 
                }
                response.sendRedirect("Incidencias");
                break;
            }

        }

    }

    private boolean insertarIncidencia(HttpServletRequest request, HttpServletResponse response) {
        boolean estado = true;
        
        String titulo = request.getParameter("txtTitle");
        String idclasf = request.getParameter("slcClasificacion");
        String prioridad = request.getParameter("slcPrioridad");
        String desc = request.getParameter("txtDescripcion");
        String idReceptor = request.getParameter("txtReceptor"); //esta quemado con id 3 que corresponde a NOVA 
        String fechafinal = request.getParameter("dateFechaFinal");
        int idCreador = (int) request.getSession().getAttribute("idUsuario");
        int idDepto = 0;
        
        int status = Enums.ESTADO.ASIGNADA;  
        int idRol = (int)request.getSession().getAttribute("Rol");
        
        if( idRol == 2){ //Si es un lider verificar si el receptor pertenece al mismo depto
            if(!SameDepto(idCreador,Integer.parseInt(idReceptor))){
                status = Enums.ESTADO.SOLICITADA; //Se agrega como una solicitud             
            }
        }
        
        
        try{
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = simpleDateFormat.parse(fechafinal);
            
            if(idRol == 2){
                String sql = "select iddepto from deptobyusers where iduser = ?";
                List<Object> params = new ArrayList();
                params.add(idCreador);
                String[][] rs = Operaciones.consultar(sql, params);
                idDepto = Integer.parseInt(rs[0][0]);
                
            }else if(idRol == 1){ //Para gerente sera el que halla selecionado 
                idDepto = Integer.parseInt(request.getParameter("slcDeptoIncidence"));
            }
            
            Incidencia icn = new Incidencia();
            icn.setTitle(titulo);
            icn.setDescription(desc);
            icn.setCreationDay(new Timestamp(System.currentTimeMillis()));
            icn.setFinalDate(new Timestamp(date.getTime()));
            icn.setTotalCost(BigDecimal.valueOf(0.0));
            icn.setPriority(Integer.parseInt(prioridad));
            icn.setIdClassification(Integer.parseInt(idclasf));
            icn.setIdCreator(idCreador);
            icn.setIdDepto(idDepto); //Multimedia cambiar de forma dinamica
            
            icn = Operaciones.insertar(icn);
            
            IncidenciaPorEncargado ixp = new IncidenciaPorEncargado();
            ixp.setStatus(status);
            ixp.setIdreceptor(Integer.parseInt(idReceptor));
            ixp.setIdIncidence(icn.getIdIncidence());
            
            
            ixp = Operaciones.insertar(ixp);
            
            Operaciones.commit();
        }catch(Exception ex){
            estado = false;
            try {
                Operaciones.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Incidencias.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Incidencias.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return estado;
    }


    private boolean SameDepto(int a, int b){ //id de Creador e id de receptor 
        boolean sm = false;
        
        Integer da = DataList.getIdDepto(a);
        Integer db = DataList.getIdDepto(b);
        
        if(da == db){
            sm = true;
        }
        return sm;
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>


}
