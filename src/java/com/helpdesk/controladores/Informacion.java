/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.controladores;

import com.helpdesk.conexion.Conexion;
import com.helpdesk.conexion.ConexionPool;
import com.helpdesk.operaciones.Operaciones;
import com.helpdesk.utilerias.DataIncidencia;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mack_
 */
/*Este servlet extrae e inserta informacion relacionada a la incidencia*/
public class Informacion extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        DataIncidencia din = getDefinicion(request, response);
        if(din!=null){
            request.setAttribute("ObjectInfo", din);
            request.getRequestDispatcher("Informacion.jsp").forward(request, response);
        }else{
            out.println("No existe");
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
    
    private DataIncidencia getDefinicion(HttpServletRequest request, HttpServletResponse response){
        DataIncidencia di = new DataIncidencia();
        
            int idIncidencia = Integer.parseInt(request.getParameter("idinc"));
            try{
                String sql =    "select d.deptoname,i.title,i.description, inc.status, i.creationday,\n" 
                                +"i.priority,u.username, cl.classification, i.totalcost \n"
                                +"from incidencebyreceptor inc, incidences i, departments d,\n"
                                +"users u, classifications cl \n"
                                +"where inc.idincidence = i.idincidence and \n" 
                                +"i.iddepto = d.iddepto and u.iduser = i.idcreator \n"
                                +"and cl.idclassification = i.idclassification \n"
                                +"and i.idincidence = ? \n";
                List<Object> params = new ArrayList();
                params.add(idIncidencia);
                
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                
                String[][] rs = Operaciones.consultar(sql, params);
                
                if(rs != null){
                    di.setDepartamento(rs[0][0]);
                    di.setTitulo(rs[1][0]);
                    di.setDescripcion(rs[2][0]);
                    di.setStatus(Integer.parseInt(rs[3][0]));
                    di.setFechaCreacion(rs[4][0]);
                    di.setPrioridad(Integer.parseInt(rs[5][0]));
                    di.setCreador(rs[6][0]);
                    di.setClasificacion(rs[7][0]);
                    di.setCostoTotal(rs[8][0]);
                }else{
                    di = null;
                }
                
            }catch(Exception ex){
                di = null;
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex1) {
                Logger.getLogger(Informacion.class.getName()).log(Level.SEVERE, null, ex1);
            }
            }
            
        return di;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
