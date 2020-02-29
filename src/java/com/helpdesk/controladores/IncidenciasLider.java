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
 * @author ALEX
 */
public class IncidenciasLider extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        HttpSession s = request.getSession();
        String accion = request.getParameter("accion");
        int idUserSession = (int) s.getAttribute("idUsuario");
        int rol = (int) s.getAttribute("Rol");
        

        if (accion == null) {

            IncidenceByReceptor list = new IncidenceByReceptor(idUserSession);

            if (rol == 2) {
                s.setAttribute("requestIncidencia", DataList.getIncidenciasSolicitadas(idUserSession));

            }
            request.setAttribute("incidencesLider", true);
            request.setAttribute("idRol", rol);
            request.setAttribute("todasDiv", list.getIncidencesStatusByLider(0, 0));
            request.setAttribute("processDiv", list.getIncidencesStatusByLider(3, 1));
            request.setAttribute("pendingDiv", list.getIncidencesStatusByLider(2, 1));
            request.setAttribute("finishDiv", list.getIncidencesStatusByLider(4, 1));
            request.setAttribute("requestDiv", list.getIncidencesStatusByLider(1, 1));
            request.setAttribute("refuseDiv", list.getIncidencesStatusByLider(5, 1));
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
                        printIncidencesJson.Render(list.getIncidencesStatusByLider(0, 0), response);
                    } else {
                        out.print("null");
                    }
                    break;
                }
                case "enproceso": {
                    PrintWriter out = response.getWriter();
                    IncidenceByReceptor list = new IncidenceByReceptor(idUserSession);
                    if (list != null) {
                        printIncidencesJson.Render(list.getIncidencesStatusByLider(3, 1), response);

                    } else {
                        out.print("null");
                    }
                    break;
                }
                case "urgente": {
                    PrintWriter out = response.getWriter();
                    IncidenceByReceptor list = new IncidenceByReceptor(idUserSession);
                    if (list != null) {
                        printIncidencesJson.Render(list.getIncidencesStatusByLider(3, 1), response);
                    } else {
                        out.print("null");
                    }
                    break;
                }
                case "finalizadas": {
                    PrintWriter out = response.getWriter();
                    IncidenceByReceptor list = new IncidenceByReceptor(idUserSession);
                    if (list != null) {
                        printIncidencesJson.Render(list.getIncidencesStatusByLider(4, 1), response);

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
                        printIncidencesJson.Render(list.getIncidencesStatusByLider(1, 1), response);

                    } else {
                        out.print("null");
                    }
                    break;
                }
                case "refuse":{
                    PrintWriter out = response.getWriter();
                    IncidenceByReceptor list = new IncidenceByReceptor(idUserSession);
                    if (list != null) {
                        printIncidencesJson.Render(list.getIncidencesStatusByLider(5, 1), response);

                    } else {
                        out.print("null");
                    }
                    break;
                }
                
                case "pending":{
                    PrintWriter out = response.getWriter();
                    IncidenceByReceptor list = new IncidenceByReceptor(idUserSession);
                    if (list != null) {
                        printIncidencesJson.Render(list.getIncidencesStatusByLider(2, 1), response);

                    } else {
                        out.print("null");
                    }
                    break;
                }
            }
        }
        
    }
    
    private void cerrarSesion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession s = request.getSession();
        s.removeAttribute("Usuario");
        s.removeAttribute("idUsuario");
        s.removeAttribute("Rol");
        s.invalidate();
        response.sendRedirect("Login");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
