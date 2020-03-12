/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.utilerias;

import com.helpdesk.conexion.Conexion;
import com.helpdesk.conexion.ConexionPool;
import com.helpdesk.controladores.Login;
import com.helpdesk.controladores.PrincipalRequisicion;
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
    private int idemp;

    public RequisicionByRol(int id) {
        try {
            ConexionPool conexion = new ConexionPool();
            conexion.conectar();
            Operaciones.abrirConexion(conexion);

            this.iduser = id;
            this.iddepto = DataList.getIdDepto(this.iduser);
            this.idemp = DataList.getIdEmpresa(iduser);
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
    
    /*Funcion para filtrar los estados de las requisiciones por empresa*/
    
    public  ArrayList<RequisicionPago> getRequisicionByStatusByEmpresa(int status, int idemp, int idcase){
        ArrayList<RequisicionPago> list = new ArrayList<>();
        String query = "select idrequisicion from requisicionespagos where idempresa = " + idemp + " and estado=" + status;
        String query2 = "select idrequisicion from requisicionespagos where idempresa = " + idemp;
        list = this.listRequisicion(query, null);
        switch(idcase){
            case 1:{
                list = this.listRequisicion(query, null);
                break;
            }
            case 2:{
                list = this.listRequisicion(query2, null);
            }
        }
        return list;
    }

    public ArrayList<RequisicionPago> getRequisicionByStatus(int id) {
        ArrayList<RequisicionPago> list = new ArrayList<>();
        ///En el caso que es un receptor requisicion
        String query = "select idrequisicion from requisicionespagos where idcreador = " + this.iduser + " and estado=" + id;
        ///En el caso que es un lider requisicion 
        String query2 = "select idrequisicion from requisicionespagos where idautorizador = "+this.iduser+" and estado = " + id;
        //En el caso para un contador
        String query3 = "select idrequisicion from requisicionespagos where idcontador = " + this.iduser + " and estado=" + id;
        
        switch (this.idrol) {
            case 7: {
                list = this.listRequisicion(query, null);
                break;
            }
            case 6: {
                list = this.listRequisicion(query2, null);
                break;
            }
            case 9:{
                list = this.listRequisicion(query3, null);
                break;
            }
        }

        return list;
    }

    
    

    public ArrayList<Object> getAllRequisicionesByContadorAndPriority() {
        ArrayList<Object> mainlist = new ArrayList<>();
        ArrayList<RequisicionPago> listBaja = new ArrayList<>();
        ArrayList<RequisicionPago> listMedia = new ArrayList<>();
        ArrayList<RequisicionPago> listAlta = new ArrayList<>();
        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);

            for (int i = 1; i < 4; i++) {
                String query = "select idrequisicion\n"
                        + "from requisicionespagos\n"
                        + "where estado = 3 and prioridad = " + i + " and idcontador = " + this.iduser;
                if (i == 1) {
                    String array[][] = Operaciones.consultar(query, null);
                    if (array != null) {
                        for (int j = 0; j < array[0].length; j++) {
                            RequisicionPago rp = new RequisicionPago();
                            rp = Operaciones.get(Integer.parseInt(array[0][j]), new RequisicionPago());
                            listBaja.add(rp);
                        }

                    } else {
                        listBaja = null;
                    }

                } else if (i == 2) {
                    String array[][] = Operaciones.consultar(query, null);
                    if (array != null) {
                        for (int j = 0; j < array[0].length; j++) {
                            RequisicionPago rp = new RequisicionPago();
                            rp = Operaciones.get(Integer.parseInt(array[0][j]), new RequisicionPago());
                            listMedia.add(rp);
                        }

                    } else {
                        listMedia = null;
                    }

                } else {
                    String array[][] = Operaciones.consultar(query, null);
                    if (array != null) {
                        for (int j = 0; j < array[0].length; j++) {
                            RequisicionPago rp = new RequisicionPago();
                            rp = Operaciones.get(Integer.parseInt(array[0][j]), new RequisicionPago());
                            listAlta.add(rp);
                        }

                    } else {
                        listAlta = null;
                    }
                }
            }
            mainlist.add(listBaja);
            mainlist.add(listMedia);
            mainlist.add(listAlta);
        } catch (Exception ex) {
            Logger.getLogger(PrincipalRequisicion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex1) {
                Logger.getLogger(PrincipalRequisicion.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return mainlist;
    }

    public ArrayList<RequisicionPago> getAllRequisiciones() {
        ArrayList<RequisicionPago> list = new ArrayList<>();
        //En el caso que sea Receptor Requisicion rol = 7
        String query1 = "select idrequisicion from requisicionespagos where idcreador = " + this.iduser;
        //En el caso que sea Lider Requisicion rol = 6
        String query2 = "select idrequisicion from requisicionespagos where estado = 1 and idempresa = " + this.idemp;

        switch (this.idrol) {
            case 7: {
                list = this.listRequisicion(query1, null);
                break;
            }
            case 6: {
                list = this.listRequisicion(query2, null);
                break;
            }
        }

        return list;
    }

    public ArrayList<RequisicionPago> listRequisicion(String query, List<Object> params) {
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
