/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.helpdesk.controladores;

import com.google.gson.Gson;
import com.helpdesk.entidades.RequisicionPago;
import com.helpdesk.utilerias.DataList;
import com.helpdesk.utilerias.DataRequisicion;
import com.helpdesk.utilerias.Enums;
import com.helpdesk.utilerias.RequisicionByRol;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Mack_
 */
public class PrincipalRequisicion extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            /*request.getRequestDispatcher("pnlRequisicion.jsp").forward(request, response);*/
        }
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        HttpSession s = request.getSession();
        String accion = request.getParameter("accion");
        int idUserSession = (int) s.getAttribute("idUsuario");
        int rol = (int) s.getAttribute("Rol");
        if(accion == null){
            if(rol == Enums.ROL.LIDER_REQ){
                RequisicionByRol r = new RequisicionByRol(idUserSession);
                ArrayList<RequisicionPago> list = r.getAllRequisiciones();
                ArrayList<DataRequisicion> datalist = new ArrayList<>();
                if(list!=null){
                   for(int i=0;i<list.size();i++){
                       datalist.add(DataList.getGeneralData(list.get(i).getIdRequisicion()));
                   }
                   s.setAttribute("listRequisiciones", datalist);
                }else{
                   s.setAttribute("listRequisiciones", null); 
                }      
                
            }
            if(rol == Enums.ROL.GERENTE_REQ){
                s.setAttribute("requestEmpleado", DataList.getEmpleados(1));
            }
            if(rol == Enums.ROL.RECEPTOR_REQ){
                /// RequisicionInfo?idReq=1
                RequisicionByRol rbr = new RequisicionByRol(idUserSession);
                s.setAttribute("todasDiv", rbr.getAllRequisiciones());
                s.setAttribute("requestDiv", rbr.getRequisicionByStatus(Enums.ESTADO_REQ.SOLICITADA));
                s.setAttribute("refuseDiv", rbr.getRequisicionByStatus(Enums.ESTADO_REQ.RECHAZADA));
                s.setAttribute("finishDiv", rbr.getRequisicionByStatus(Enums.ESTADO_REQ.FINALIZADA));

                s.setAttribute("processDiv", rbr.getRequisicionByStatus(Enums.ESTADO_REQ.REVISION));
                s.setAttribute("pendingDiv", rbr.getRequisicionByStatus(Enums.ESTADO_REQ.ACEPTADA));
                
            }
            request.getRequestDispatcher("pnlRequisicion.jsp").forward(request, response);
        }else{
            PrintWriter out = response.getWriter();
            RequisicionByRol r = new RequisicionByRol(idUserSession);
            switch(accion){
                case "solicitadas":{
                    if(r.getRequisicionByStatus(Enums.ESTADO_REQ.SOLICITADA)!=null){
                        String json = new Gson().toJson(r.getRequisicionByStatus(Enums.ESTADO_REQ.SOLICITADA));
                        out.print(json);
                    }else{
                        out.print("null");
                    }
                    break;
                }
                case "todas":{
                    if(r.getAllRequisiciones()!=null){
                        String json = new Gson().toJson(r.getAllRequisiciones());
                        out.print(json);
                    }else{
                        out.print("null");
                    }
                    break;
                }
                case "enproceso":{
                    if(r.getRequisicionByStatus(Enums.ESTADO_REQ.REVISION)!=null){
                        String json = new Gson().toJson(r.getRequisicionByStatus(Enums.ESTADO_REQ.REVISION));
                        out.print(json);
                    }else{
                        out.print("null");
                    }
                    break;
                }
                case "pending":{
                    if(r.getRequisicionByStatus(Enums.ESTADO_REQ.ACEPTADA)!=null){
                        String json = new Gson().toJson(r.getRequisicionByStatus(Enums.ESTADO_REQ.ACEPTADA));
                        out.print(json);
                    }else{
                        out.print("null");
                    }
                    break;
                }
                case "finalizadas":{
                    if(r.getRequisicionByStatus(Enums.ESTADO_REQ.FINALIZADA)!=null){
                        String json = new Gson().toJson(r.getRequisicionByStatus(Enums.ESTADO_REQ.FINALIZADA));
                        out.print(json);
                    }else{
                        out.print("null");
                    }
                    break;
                }
                case "refuse":{
                    if(r.getRequisicionByStatus(Enums.ESTADO_REQ.RECHAZADA)!=null){
                        String json = new Gson().toJson(r.getRequisicionByStatus(Enums.ESTADO_REQ.RECHAZADA));
                        out.print(json);
                    }else{
                        out.print("null");
                    }
                    break;
                }
                
            }
        }
        
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
