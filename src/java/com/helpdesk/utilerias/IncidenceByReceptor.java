/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.utilerias;

import com.helpdesk.conexion.ConexionPool;
import com.helpdesk.controladores.Login;
import com.helpdesk.entidades.Incidencia;
import com.helpdesk.entidades.Usuario;
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
    private int idrol;
    private int iddepto;

    public IncidenceByReceptor(int idu) {
        
        try {
            ConexionPool conexion = new ConexionPool();
            conexion.conectar();
            Operaciones.abrirConexion(conexion);
            
            this.iduser = idu;
            this.iddepto = DataList.getIdDepto(this.iduser);
        
            this.idrol = Operaciones.get(this.iduser, new Usuario()).getIdRole();
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

    public ArrayList<Incidencia> getIncidencesByStatus(int id) {
        ArrayList<Incidencia> listStatus = new ArrayList<>();
        //En el caso  para un receptor
        String query = "select idincidence from incidencebyreceptor \n" +
                       "where idreceptor = ? and status = ?  group by idincidence";
        //En el caso para un gerente
        String query2 = "select\n"
                + "a.idincidence\n"
                + "from incidences a, incidencebyreceptor b\n"
                + "where\n"
                + "a.idincidence=b.idincidence and a.status=? group by a.idincidence";
        //En el caso para un lider
        String query3 = "select\n"
                + "a.idincidence\n"
                + "from incidences a, incidencebyreceptor b\n"
                + "where\n"
                + "a.idincidence=b.idincidence and a.status=? and a.iddepto=? group by a.idincidence";

        switch (this.idrol) {
            case 1: {
                List<Object> params = new ArrayList<>();
                params.add(id);
                listStatus = listIncidencia(query2, params);
                break;
            }
            case 2: {
                List<Object> params = new ArrayList<>();
                params.add(id);
                params.add(this.iddepto);
                listStatus = listIncidencia(query3, params);
                break;
            }
            case 3: {
                List<Object> params = new ArrayList<>();
                params.add(this.iduser);
                params.add(id);
                listStatus = listIncidencia(query, params);
                break;
            }
        }

        return listStatus;
    }

    public ArrayList<Incidencia> getIncidencesByPriority(int id) {
        ArrayList<Incidencia> listPrty = new ArrayList<>();
        //En el caso para un receptor
        String query = "select\n"
                + "a.idincidence\n"
                + "from incidences a, incidencebyreceptor b\n"
                + "where \n"
                + "a.idincidence=b.idincidence and a.idreceptor=? and a.priority=? group by a.idincidence";
        //En el caso para un gerente
        String query2 = "select\n"
                + "a.idincidence\n"
                + "from incidences a, incidencebyreceptor b\n"
                + "where \n"
                + "a.idincidence=b.idincidence and a.priority=? group by a.idincidence";
        //En el caso para un lider
        String query3 = "select\n"
                + "a.idincidence\n"
                + "from incidences a, incidencebyreceptor b\n"
                + "where \n"
                + "a.idincidence=b.idincidence and a.priority=? and a.iddepto=? group by a.idincidence";

        switch (this.idrol) {
            case 1: {
                List<Object> params = new ArrayList<>();
                params.add(id);
                listPrty = listIncidencia(query2, params);
                break;
            }
            case 2: {
                List<Object> params = new ArrayList<>();
                params.add(id);
                params.add(this.iddepto);
                listPrty = listIncidencia(query3, params);
                break;
            }
            case 3: {
                List<Object> params = new ArrayList<>();
                params.add(this.iduser);
                params.add(id);
                listPrty = listIncidencia(query3, params);
                break;
            }
        }

        return listPrty;
    }

    public ArrayList<Incidencia> getAllIncidences() {
        ArrayList<Incidencia> listAll = new ArrayList<>();
        //En el caso de un receptor
        String query = "select\n" +
                       "a.idincidence\n" +
                       "from  incidencebyreceptor a\n" +
                       "where \n" +
                       "a.idreceptor= "+this.iduser+" group by a.idincidence";
        //En el caso de un gerente
        String query2 = "select\n"
                + "a.idincidence\n"
                + "from incidences a, incidencebyreceptor b\n"
                + "where \n"
                + "a.idincidence=b.idincidence group by a.idincidence";
        //En el caso de un lider
        String query3 = "select\n"
                + "a.idincidence\n"
                + "from incidences a, incidencebyreceptor b\n"
                + "where \n"
                + "a.idincidence=b.idincidence and a.iddepto=? group by a.idincidence";
        switch (this.idrol) {
            case 1: {
                listAll = listIncidencia(query2, null);
                break;
            }
            case 2: {
                List<Object> params = new ArrayList<>();
                params.add(this.iddepto);
                listAll = listIncidencia(query3, params);
                break;
            }
            case 3: {

                listAll = listIncidencia(query, null);
                break;
            }
        }
        return listAll;
    }

    public ArrayList<Incidencia> listIncidencia(String query, List<Object> params) {
        ArrayList<Incidencia> list = new ArrayList<>();
        try {
            ConexionPool conexion = new ConexionPool();
            conexion.conectar();
            Operaciones.abrirConexion(conexion);
            

            String[][] array = Operaciones.consultar(query, params);

            if (array != null) {

                for (int i = 0; i < array[0].length; i++) {
                    Incidencia inctmp = new Incidencia();
                    inctmp = Operaciones.get(Integer.parseInt(array[0][i]), new Incidencia());
                    list.add(inctmp);
                }

            } else {
                list = null;
            }

        } catch (Exception ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex2) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex2);
            }
        }

        return list;
    }

    public ArrayList<Incidencia> getIncidenciaSolicitada() {
        ArrayList<Incidencia> listAll = new ArrayList<>();
        String query = "select\n"
                + "                a.idincidence\n"
                + "                from incidences a, incidencebyreceptor b, deptobyusers c\n"
                + "                where \n"
                + "                a.idincidence=b.idincidence  and \n"
                + "				b.status=1 \n"
                + "				and b.idreceptor=c.iduser and\n"
                + "				c.iddepto=" + this.iddepto;
        listAll = listIncidencia(query, null);
        return listAll;
    }
}
