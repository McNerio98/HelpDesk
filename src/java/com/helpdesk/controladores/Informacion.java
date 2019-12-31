/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.controladores;

import com.helpdesk.conexion.Conexion;
import com.helpdesk.conexion.ConexionPool;
import com.helpdesk.operaciones.Operaciones;
import com.helpdesk.utilerias.DataControl;
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
        
        Integer idIncidencia = Integer.parseInt(request.getParameter("idIncidencia"));
        
        if(idIncidencia == null){
            out.print("Se redirecciona, sin identificador");
        }else{ //Se extrae informacion de la incidencia 
            ArrayList<DataControl> LstControl = getListControl(idIncidencia);
            if(LstControl != null){ //Siempre existira un registro por la incidencia 
                //Se procebe a obtener el idIBR del ultimo registro 
                int idIBR = LstControl.get(LstControl.size() - 1).getIdIBR(); //Toma el id del ultimo control
                
                DataIncidencia dataInfo = getDefinicion(idIncidencia, idIBR);
                
                if(dataInfo!=null){
                    request.setAttribute("ObjectInfo", dataInfo); //
                    request.setAttribute("LstControl", LstControl); //Colecion
                    request.setAttribute("idIncidence", idIncidencia); // 
                    request.setAttribute("ibr", idIBR); // 
                    request.getRequestDispatcher("Informacion.jsp").forward(request, response);
                }else{
                    out.print("Error al Solicitar la Definicion");
                }
                
            }else{
                out.print("Problema al solicitar el Control");
            }
        
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
    
    private DataIncidencia getDefinicion(Integer idIncidencia, Integer idIBR){
        DataIncidencia di = new DataIncidencia();
        
            try{

                String sql =   "select d.deptoname,i.title,i.description, inc.status, i.creationday, i.priority, \n"
                                + "u.username, cl.classification, i.totalcost, dp.iddepto, inc.idreceptor from \n"
                                + "incidencebyreceptor inc, incidences i, departments d, users u, classifications cl, \n"
                                + "deptobyusers dp where inc.idincidence = i.idincidence and i.iddepto = d.iddepto \n"
                                + "and u.iduser = i.idcreator and cl.idclassification = i.idclassification and \n"
                                + "dp.iduser = inc.idreceptor and i.idincidence = ? and inc.idibr = ? ";
                
                List<Object> params = new ArrayList();
                params.add(idIncidencia);
                params.add(idIBR);
                
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
                    di.setIdDeptoTecnico(Integer.parseInt(rs[9][0]));
                    di.setIdTecnico(Integer.parseInt(rs[10][0]));
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
    
    private ArrayList<DataControl> getListControl(Integer idIncidencia){
        ArrayList<DataControl> LstControl = new ArrayList<>();
        
        String sql = "select ibr.idibr, u.username, ibr.status, i.creationday, \n"
                    +"ibr.startdate, i.finaldate, ibr.finaldate from users u, \n"
                    +"incidencebyreceptor ibr, incidences i where \n"
                    +"ibr.idreceptor = u.iduser and \n"
                    +"ibr.idincidence = i.idincidence and ibr.idincidence = ?";
        
        List<Object> params = new ArrayList();
        params.add(idIncidencia);
        
        try{
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            String[][] rs = Operaciones.consultar(sql, params);
            if(rs!=null){ //Si hay valores y se crea la lista 
                for(int i = 0; i < rs[0].length; i++){
                    DataControl ct = new DataControl();
                    ct.setIdIBR(Integer.parseInt(rs[0][i]));
                    ct.setReceptor(rs[1][i]);
                    ct.setStatus(Integer.parseInt(rs[2][i]));
                    ct.setInicioPrev(rs[3][i]);
                    ct.setInicioReal(rs[4][i]);
                    ct.setFinPrev(rs[5][i]);
                    ct.setFinReal(rs[6][i]);
                    LstControl.add(ct);
                }
            }
        }catch(Exception ex){
            LstControl = null;
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex1) {
                Logger.getLogger(Informacion.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return LstControl;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

