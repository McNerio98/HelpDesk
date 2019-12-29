/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.controladores;

import com.helpdesk.conexion.Conexion;
import com.helpdesk.conexion.ConexionPool;
import com.helpdesk.entidades.Incidencia;
import com.helpdesk.operaciones.Operaciones;
import com.helpdesk.utilerias.DataList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
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
            request.setAttribute("DeptosList", DataList.getAllDeptos());
            request.setAttribute("ClasfList", DataList.getAllClassifications());
            request.getRequestDispatcher("NuevaIncidencia.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        PrintWriter out = response.getWriter();

        switch (accion) {
            case "nueva": {
                String titulo = request.getParameter("txtTitle");
                int clasf = Integer.parseInt(request.getParameter("slcClasificacion"));
                String prioridad = request.getParameter("slcPrioridad");
                String desc = request.getParameter("txtDescripcion");
                int idReceptor = Integer.parseInt(request.getParameter("txtReceptor")); //esta quemado con id 12 
                String fechafinal = request.getParameter("dateFechaFinal");
                int idCreador = (int) request.getSession().getAttribute("idUsuario");

                Conexion conn = new ConexionPool();
                conn.conectar();
                Connection con = conn.getConexion();

                if(insertarIncidencia(con,titulo, clasf, prioridad, desc, idReceptor, fechafinal, idCreador)){
                    out.print("Se inserto");
                }else{
                    out.print("No se inserto");
                }

                break;
            }

        }

    }

    private boolean insertarIncidencia(Connection cnn, String titulo, int clasf, String prioridad, String desc, int receptor, String fechaFinal, int idCreador) {
        boolean estado = false;
        try {
            //Iniciando transaccion 
            cnn.setAutoCommit(false);
            //Date date = new SimpleDateFormat("yyyy-MM-dd").parse(fechaFinal);
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            Date date = simpleDateFormat.parse(fechaFinal);            
            
            String sql = "insert into incidences\n"
                    + "(title,description,finaldate,totalcost,priority,idclassification,idcreator,iddepto)\n"
                    + "values(?,?,?,?,?::PRIORYTY,?,?,?)";
            PreparedStatement ps = cnn.prepareStatement(sql);
            ps.setObject(1, titulo);
            ps.setObject(2, desc);
            ps.setObject(3, new Timestamp(date.getTime()));
            ps.setObject(4, 0.0);
            ps.setObject(5, prioridad);
            ps.setObject(6, clasf);
            ps.setObject(7, idCreador);
            ps.setObject(8, 1); //Modificar este valor para que sea dinamico
          
            ps.executeUpdate(); //Se crea la incidencia y se procede a crear a quien se le dara tratamiento
            cnn.commit();
            estado = true;
        } catch (Exception ex) {
            try {
                cnn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Incidencias.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }finally{
            try {
                cnn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Incidencias.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return estado;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private Conexion ConexionPool() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
