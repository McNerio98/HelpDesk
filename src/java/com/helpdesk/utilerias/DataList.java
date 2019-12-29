/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.utilerias;

import com.helpdesk.conexion.Conexion;
import com.helpdesk.conexion.ConexionPool;
import com.helpdesk.entidades.Clasificacion;
import com.helpdesk.entidades.Departamento;
import com.helpdesk.operaciones.Operaciones;
import java.sql.SQLException;
import java.util.ArrayList;
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
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex1) {
                Logger.getLogger(DataList.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return deptos;
    }
    
}
