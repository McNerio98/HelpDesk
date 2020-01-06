/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.utilerias;

import com.helpdesk.conexion.Conexion;
import com.helpdesk.conexion.ConexionPool;
import com.helpdesk.controladores.Login;
import com.helpdesk.entidades.Clasificacion;
import com.helpdesk.entidades.Departamento;
import com.helpdesk.entidades.Incidencia;
import com.helpdesk.entidades.Rol;
import com.helpdesk.entidades.Usuario;
import com.helpdesk.operaciones.Operaciones;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ZEUS
 */
public class DataList {

    public static ArrayList<Clasificacion> getAllClassifications() {
        ArrayList<Clasificacion> clasf = new ArrayList<>();
        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            clasf = Operaciones.getTodos(new Clasificacion());
        } catch (Exception ex) {
            Logger.getLogger(DataList.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex1) {
                Logger.getLogger(DataList.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return clasf;
    }

    public static ArrayList<Departamento> getAllDeptos() {
        ArrayList<Departamento> deptos = new ArrayList<>();
        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            deptos = Operaciones.getTodos(new Departamento());
        } catch (Exception ex) {
            Logger.getLogger(DataList.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex1) {
                Logger.getLogger(DataList.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return deptos;
    }

    public static Integer getIdDepto(Integer idUsuario) {
        Integer idDepto = 0;

        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            String sql = "select iddepto from deptobyusers where iduser = ?";
            List<Object> params = new ArrayList();
            params.add(idUsuario);

            String[][] rs = Operaciones.consultar(sql, params);
            idDepto = Integer.parseInt(rs[0][0]);//Solo devuelve un valor 

        } catch (Exception ex) {
            Logger.getLogger(DataList.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex1) {
                Logger.getLogger(DataList.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

        return idDepto;
    }

    /*Esta funcion trae todos los usuarios que tienen el rol de empleado*/
    public static ArrayList<listarEmpleado> getEmpleados() {
        String query = "select iduser from users where idrole = 4";
        ArrayList<listarEmpleado> list = new ArrayList<>();
        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();

            String[][] array = Operaciones.consultar(query, null);

            for (int i = 0; i < array[0].length; i++) {
                Usuario user = new Usuario();
                Departamento depto = new Departamento();
                Rol rol = new Rol();
                listarEmpleado emp = new listarEmpleado();

                user = Operaciones.get(Integer.parseInt(array[0][i]), new Usuario());
                rol = Operaciones.get(user.getIdRole(), new Rol());
                depto = Operaciones.get(DataList.getIdDepto(user.getIdUser()), new Departamento());

                emp.setDepto(depto);
                emp.setRol(rol);
                emp.setUsuario(user);

                list.add(emp);
            }

        } catch (Exception ex) {
            try {
                Operaciones.cerrarConexion();

            } catch (SQLException ex1) {
                Logger.getLogger(Login.class
                        .getName()).log(Level.SEVERE, null, ex1);
            }
        }

        return list;
    }

    /*Esta funcion trae las incidencias solicitadas para el lider de un depto*/
    public static ArrayList<Incidencia> getIncidenciasSolicitadas(int iduser) {
        IncidenceByReceptor receptor = new IncidenceByReceptor(iduser);
        ArrayList<Incidencia> list = new ArrayList<>();
        list = receptor.getIncidenciaSolicitada();
        return list;
    }

}
