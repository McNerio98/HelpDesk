/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.controladores;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ZEUS
 */
public class Incidencias extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        PrintWriter out = response.getWriter();
        
        switch(accion){
            case "nueva":{
                String titulo = request.getParameter("txtTitle");
                String clasificacion = request.getParameter("slcClasificacion");
                String prioridad = request.getParameter("slcPrioridad");
                String descripcion = request.getParameter("txtDescripcion");
                String fechaFinal = request.getParameter("dateFechaFinal");
                
                out.println(titulo);
                out.println(clasificacion);
                out.println(prioridad);
                out.println(descripcion);
                out.println(fechaFinal);
                
            }
        }
        
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
