/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.controladores;

import com.helpdesk.entidades.Menu;
import com.helpdesk.utilerias.IncidenceByReceptor;
import com.helpdesk.utilerias.printIncidencesJson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ZEUS
 */
public class Principal extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        int idUserSession = (int)request.getSession().getAttribute("idUsuario");
        
        if(accion == null){
            IncidenceByReceptor list = new IncidenceByReceptor(idUserSession);
            list.getAllIncidences();
            request.setAttribute("todasDiv", list.getAllIncidences());
            request.setAttribute("processDiv", list.getIncidencesByStatus(3));
            request.setAttribute("urgenteDiv", list.getIncidencesByPriority(3));
            request.setAttribute("finishDiv", list.getIncidencesByStatus(4));
            request.getRequestDispatcher("pnlPrincipal.jsp").forward(request, response);
        }else if(accion.equals("logout")){
            cerrarSesion(request, response);
        }else{
            switch(accion){
                case "todas":{
                    int id = (int)request.getSession().getAttribute("idUsuario");
                    PrintWriter out = response.getWriter();
                    IncidenceByReceptor list = new IncidenceByReceptor(id);
                    if(list!=null){
                        printIncidencesJson.Render(list.getAllIncidences(), response);
                    }else{
                        out.print("null");
                    }
                    break;
                }
                case "enproceso":{
                    PrintWriter out = response.getWriter();
                    IncidenceByReceptor list = new IncidenceByReceptor(idUserSession);
                    if(list!=null){
                        printIncidencesJson.Render(list.getIncidencesByStatus(3), response);
                        
                    }else{
                       out.print("null");
                    }
                    break;
                }
                case "urgente":{
                    PrintWriter out = response.getWriter();
                    IncidenceByReceptor list = new IncidenceByReceptor(idUserSession);
                    if(list!=null){
                        printIncidencesJson.Render(list.getIncidencesByPriority(3), response);
                    }else{
                        out.print("null");
                    }
                    break;
                }
                case "finalizadas":{
                    PrintWriter out = response.getWriter();
                    IncidenceByReceptor list = new IncidenceByReceptor(idUserSession);
                    if(list!=null){
                        printIncidencesJson.Render(list.getIncidencesByStatus(4), response);
                        
                    }else{
                        out.print("null");
                    }
                    break;
                }
            }
        }

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        

    }
    
    private void cerrarSesion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession s = request.getSession();
        s.removeAttribute("Usuario");
        s.removeAttribute("idUsuario");
        s.removeAttribute("Rol");
        s.invalidate();
        response.sendRedirect("Login");
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}