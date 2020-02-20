/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.utilerias;

import com.helpdesk.conexion.ConexionPool;
import com.helpdesk.controladores.Login;
import com.helpdesk.entidades.RequisicionPago;
import com.helpdesk.entidades.Usuario;
import com.helpdesk.entidades.UsuarioRequisicion;
import com.helpdesk.operaciones.Operaciones;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ALEX
 */
public class RequisicionByRol {
    private int iduser;
    private int idrol;
    private int iddepto;
    
    public RequisicionByRol(int id){
         try {
            ConexionPool conexion = new ConexionPool();
            conexion.conectar();
            Operaciones.abrirConexion(conexion);
            
            this.iduser = id;
            this.iddepto = DataList.getIdDepto(this.iduser);
        
            this.idrol = Operaciones.get(this.iduser, new UsuarioRequisicion()).getIdRol();
        } catch (Exception ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex2) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex2);
            }
        }
    }
    
    public ArrayList<RequisicionPago> getRequisicionByStatus(int id){
        String query = "select idrequisicion from requisicionespagos where idcreador = "+this.iduser+" and estado="+id;
        return this.listRequisicion(query, null);
    }
    public ArrayList<RequisicionPago> getAllRequisiciones(){
        ArrayList<RequisicionPago> list = new ArrayList<>();
        //En el caso que sea Receptor Requisicion rol = 7
        String query1 = "select idrequisicion from requisicionespagos where idcreador = "+this.iduser;
        //En el caso que sea Lider Requisicion rol = 6
        String query2 = "select idrequisicion from requisicionespagos where estado = 1";
        //En el caso que sea Contador rol = 9
        String query3 = "select idrequiscion from requisicionespagos where id";
         
        switch(this.idrol){
            case 7:{
                list = this.listRequisicion(query1, null);
                break;
            }
            case 6:{
                list = this.listRequisicion(query2, null);
                break;
            }
        }
        
        return list;
    }
    
    public ArrayList<RequisicionPago> listRequisicion(String query, List<Object> params){
        ArrayList<RequisicionPago> list = new ArrayList<>();
        try {
            ConexionPool conexion = new ConexionPool();
            conexion.conectar();
            Operaciones.abrirConexion(conexion);
            

            String[][] array = Operaciones.consultar(query, params);

            if (array != null) {

                for (int i = 0; i < array[0].length; i++) {
                    RequisicionPago inctmp = new RequisicionPago();
                    inctmp = Operaciones.get(Integer.parseInt(array[0][i]), new RequisicionPago());
                    list.add(inctmp);
                    
                    
                }

            } else {
                list = null;
            }

        } catch (Exception ex) {
            Logger.getLogger(RequisicionPago.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex2) {
                Logger.getLogger(RequisicionPago.class.getName()).log(Level.SEVERE, null, ex2);
            }
        }
        return list;
    }
}

