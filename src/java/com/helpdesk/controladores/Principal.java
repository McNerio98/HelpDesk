/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.controladores;


import com.helpdesk.utilerias.DataList;
import com.helpdesk.utilerias.IncidenceByReceptor;

import com.helpdesk.utilerias.printIncidencesJson;
import java.io.IOException;
import java.io.PrintWriter;

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
        HttpSession s = request.getSession();
        String accion = request.getParameter("accion");
        int idUserSession = (int) s.getAttribute("idUsuario");
        int rol = (int) s.getAttribute("Rol");
        

        if (accion == null) {

            IncidenceByReceptor list = new IncidenceByReceptor(idUserSession);

            if (rol == 1) {
                s.setAttribute("requestEmpleado", DataList.getEmpleados(0));

            }
            if (rol == 2) {
                s.setAttribute("requestIncidencia", DataList.getIncidenciasSolicitadas(idUserSession));

            }
            
            request.setAttribute("idRol", rol);
            request.setAttribute("todasDiv", list.getAllIncidences());
            request.setAttribute("processDiv", list.getIncidencesByStatus(3));
            request.setAttribute("pendingDiv", list.getIncidencesByStatus(2));
            request.setAttribute("finishDiv", list.getIncidencesByStatus(4));
            request.setAttribute("requestDiv", list.getIncidencesByStatus(1));
            request.setAttribute("refuseDiv", list.getIncidencesByStatus(5));
            request.getRequestDispatcher("pnlPrincipal.jsp").forward(request, response);
        } else if (accion.equals("logout")) {
            cerrarSesion(request, response);
        } else {
            switch (accion) {
                case "todas": {
                   
                    int id = (int) s.getAttribute("idUsuario");
                    PrintWriter out = response.getWriter();
                    IncidenceByReceptor list = new IncidenceByReceptor(id);
                    if (list != null) {
                        printIncidencesJson.Render(list.getAllIncidences(), response);
                    } else {
                        out.print("null");
                    }
                    break;
                }
                case "enproceso": {
                    PrintWriter out = response.getWriter();
                    IncidenceByReceptor list = new IncidenceByReceptor(idUserSession);
                    if (list != null) {
                        printIncidencesJson.Render(list.getIncidencesByStatus(3), response);

                    } else {
                        out.print("null");
                    }
                    break;
                }
                case "urgente": {
                    PrintWriter out = response.getWriter();
                    IncidenceByReceptor list = new IncidenceByReceptor(idUserSession);
                    if (list != null) {
                        printIncidencesJson.Render(list.getIncidencesByPriority(3), response);
                    } else {
                        out.print("null");
                    }
                    break;
                }
                case "finalizadas": {
                    PrintWriter out = response.getWriter();
                    IncidenceByReceptor list = new IncidenceByReceptor(idUserSession);
                    if (list != null) {
                        printIncidencesJson.Render(list.getIncidencesByStatus(4), response);

                    } else {
                        out.print("null");
                    }
                    break;
                }
                case "solicitadas":
                {
                    PrintWriter out = response.getWriter();
                    IncidenceByReceptor list = new IncidenceByReceptor(idUserSession);
                    if (list != null) {
                        printIncidencesJson.Render(list.getIncidencesByStatus(1), response);

                    } else {
                        out.print("null");
                    }
                    break;
                }
                case "refuse":{
                    PrintWriter out = response.getWriter();
                    IncidenceByReceptor list = new IncidenceByReceptor(idUserSession);
                    if (list != null) {
                        printIncidencesJson.Render(list.getIncidencesByStatus(5), response);

                    } else {
                        out.print("null");
                    }
                    break;
                }
                
                case "pending":{
                    PrintWriter out = response.getWriter();
                    IncidenceByReceptor list = new IncidenceByReceptor(idUserSession);
                    if (list != null) {
                        printIncidencesJson.Render(list.getIncidencesByStatus(2), response);

                    } else {
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
