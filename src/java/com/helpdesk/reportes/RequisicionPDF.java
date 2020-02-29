/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.reportes;

import com.helpdesk.conexion.Conexion;
import com.helpdesk.conexion.ConexionPool;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 *
 * @author desarrollo
 */
public class RequisicionPDF extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String rutaAbs = getServletConfig().getServletContext().getRealPath("/");//Se obtiene la ruta absoluta de la app
        String folderTemp = rutaAbs + "pdfTemp";//folder donde se guardaran los pdf para luego subirlos a la nube
        String pathSource = rutaAbs + "reportes/Requisicion.jasper";
        String pathSubSource = rutaAbs + "reportes/";
        String nameFile = generarNombre(2);
        Integer idRequisicion = (Integer)request.getSession().getAttribute("idReqForPDF");
        
        if(idRequisicion == null){
            response.sendRedirect("PrincipalRequisicion");
            return;
        }

        try {
            Conexion cn = new ConexionPool();
            cn.conectar();// conexion desde el pool 
            Connection conn = cn.getConexion(); //se obtiene el objeto connection,se usara como parametro 

            //Enlistar otros parametros
            Map params = new HashMap();
            params.put("ID_Req", idRequisicion);
            params.put("SUBREPORT_DIR", pathSubSource);

            byte[] bytes = null;

            bytes = JasperRunManager.runReportToPdf(pathSource, params, conn);

            //File DocPDF = new File(folderTemp + "/"+nameFile+".pdf");
            //FileOutputStream flujo = new FileOutputStream(DocPDF);
            //flujo.write(bytes);
            //flujo.close();

            //Configuraciones 
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename="+nameFile+".pdf");
            response.setContentLength(bytes.length);

            ServletOutputStream ouputStream = response.getOutputStream();
            ouputStream.write(bytes, 0, bytes.length);
            ouputStream.flush();
            
            request.getSession().removeAttribute("idReqForPDF");

        } catch (Exception e) {
            Logger.getLogger(ReportGerente.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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

    private String generarNombre(Integer numReq) {
        String cod = "Requisicion"+ numReq;
        LocalDate now = LocalDate.now();
        cod += now.getDayOfMonth();
        cod += now.getMonthValue();
        cod += now.getYear();
        return cod;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
