/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.controladores;

import com.helpdesk.conexion.Conexion;
import com.helpdesk.conexion.ConexionPool;
import com.helpdesk.operaciones.Operaciones;
import com.helpdesk.utilerias.DataList;
import com.helpdesk.utilerias.DataRequisicion;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mack_
 */
public class RequisicionInfo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer idRequisicion = Integer.parseInt(request.getParameter("idReq"));

        if (idRequisicion == null) {
            response.sendRedirect("PrincipalRequisicion");
        } else {
            boolean grant = DataList.permisosSobreIncidencia(idRequisicion, request.getSession());
            if (grant) {
                //Informacion General 
                DataRequisicion dataGeneral = getGeneralData(idRequisicion);
                //Detalles de Requisicion
                //Comentarios 
                request.getRequestDispatcher("Def_Requisicion.jsp").forward(request, response);
            } else {
                //No tiene permisos o no existe 
                response.sendRedirect("PrincipalRequisicion");
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    private DataRequisicion getGeneralData(Integer idReq) {
        
        String sql = "";

        List<Object> params = new ArrayList();
        params.add(idReq);

        DataRequisicion dt = new DataRequisicion();
        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            String[][] rs = Operaciones.consultar(sql, params);
            
        } catch (Exception e) {

        }

        return dt;
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
