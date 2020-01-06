package com.helpdesk.reportes;

import com.helpdesk.conexion.Conexion;
import com.helpdesk.conexion.ConexionPool;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 *
 * @author ZEUS
 */
public class ReportGerente extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Creamos la conexion
        Conexion cn = new ConexionPool();
        cn.conectar();
        Connection conexion = cn.getConexion();
        ServletContext context = request.getServletContext();

        File reportFile = new File(context.getRealPath("/") + "reportes/ReporteGerente.jasper");

        Map parameters = new HashMap();
        String estado = request.getParameter("estado");
        String depto = request.getParameter("depto");
        String range_final = request.getParameter("fd");
        String range_inicio = request.getParameter("sd");
        
        parameters.put("idEstado", estado);
        parameters.put("idDepto", depto);
        parameters.put("fechaInicio", range_inicio);
        parameters.put("fechaFin", range_final+" 23:59");
        
        byte[] bytes = null;
        try {
            bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parameters, conexion);
        } catch (JRException ex) {
            Logger.getLogger(ReportGerente.class.getName()).log(Level.SEVERE, null, ex);
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=reporteIncidencias.pdf");
        
        response.setContentLength(bytes.length);
        try (ServletOutputStream ouputStream = response.getOutputStream()) {
            ouputStream.write(bytes, 0, bytes.length);
            ouputStream.flush();
        }catch(Exception ex){
            Logger.getLogger(ReportGerente.class.getName()).log(Level.SEVERE, null, ex);        
        }
        
    }

@Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
        public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
