/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.utilerias;

import com.helpdesk.conexion.ConexionPool;
import com.helpdesk.controladores.Login;
import com.helpdesk.entidades.Incidencia;
import com.helpdesk.operaciones.Operaciones;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cnk
 */
public class IncidenceByReceptor {

    private int iduser;
    
    public IncidenceByReceptor(int idu) {
        this.iduser = idu;
    }

    public ArrayList<Incidencia> getIncidencesByStatus(int id) {
        ArrayList<Incidencia> listStatus = new ArrayList<>();
        String query = "select\n"
                + "a.idincidence\n"
                + "from incidences a, incidencebyreceptor b\n"
                + "where \n"
                + "a.idincidence=b.idincidence and idreceptor=? and b.status=?";
        List<Object> params = new ArrayList<>();
        params.add(this.iduser);
        params.add(id);

        try {
            ConexionPool conexion = new ConexionPool();
            conexion.conectar();
            Operaciones.abrirConexion(conexion);
            Operaciones.iniciarTransaccion();

            String[][] array = Operaciones.consultar(query, params);

            if (array != null) {

                for (int i = 0; i < array[0].length; i++) {
                    Incidencia inctmp = new Incidencia();
                    inctmp = Operaciones.get(Integer.parseInt(array[0][i]), new Incidencia());
                    listStatus.add(inctmp);
                }

            } else {
                listStatus = null;
            }

            Operaciones.commit();

        } catch (Exception ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex2) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex2);
            }
        }

        return listStatus;
    }

    public ArrayList<Incidencia> getIncidencesByPriority(int id) {
        ArrayList<Incidencia> listPrty = new ArrayList<>();
        String query = "select\n"
                + "a.idincidence\n"
                + "from incidences a, incidencebyreceptor b\n"
                + "where \n"
                + "a.idincidence=b.idincidence and idreceptor=? and a.priority=?";
        List<Object> params = new ArrayList<>();
        params.add(this.iduser);
        params.add(id);

        try {
            ConexionPool conexion = new ConexionPool();
            conexion.conectar();
            Operaciones.abrirConexion(conexion);
            Operaciones.iniciarTransaccion();

            String[][] array = Operaciones.consultar(query, params);

            if (array != null) {
                for (int i = 0; i < array[0].length; i++) {
                    Incidencia tmp = new Incidencia();
                    tmp = Operaciones.get(Integer.parseInt(array[0][i]), new Incidencia());
                    listPrty.add(tmp);
                }
            } else {
                listPrty = null;
            }

            Operaciones.commit();

        } catch (Exception ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex2) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex2);
            }
        }
        return listPrty;
    }

    public ArrayList<Incidencia> getAllIncidences() {
        ArrayList<Incidencia> listAll = new ArrayList<>();
        String query = "select\n"
                + "a.idincidence\n"
                + "from incidences a, incidencebyreceptor b\n"
                + "where \n"
                + "a.idincidence=b.idincidence and b.idreceptor=" + this.iduser;
        try {
            ConexionPool conexion = new ConexionPool();
            conexion.conectar();
            Operaciones.abrirConexion(conexion);
            Operaciones.iniciarTransaccion();

            String[][] array = Operaciones.consultar(query, null);

            if (array != null) {
                for (int i = 0; i < array[0].length; i++) {
                    Incidencia tmp = new Incidencia();
                    tmp = Operaciones.get(Integer.parseInt(array[0][i]), new Incidencia());
                    listAll.add(tmp);
                }
            } else {
                listAll = null;
            }

            Operaciones.commit();
        } catch (Exception ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex2) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex2);
            }
        }
        return listAll;
    }
}