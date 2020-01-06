
package com.helpdesk.controladores;

import com.helpdesk.entidades.Departamento;
import com.helpdesk.utilerias.DataList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Reportes extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if(accion == null){
            ArrayList<Departamento> LstDeptos = DataList.getAllDeptos();
            request.setAttribute("DeptosList", LstDeptos);
            request.getRequestDispatcher("reportesView.jsp").forward(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String depto = request.getParameter("slcDepartment");
        String estado = request.getParameter("slcEstado");
        String fechaInicio = request.getParameter("dateFechaInicio");
        String fechaFin = request.getParameter("dateFechaFinal");
        response.sendRedirect("ReportGerente?estado="+estado+"&depto="+depto+"&fd="+fechaFin+"&sd="+fechaInicio);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
