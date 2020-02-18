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
import com.helpdesk.entidades.Empresa;
import com.helpdesk.entidades.Incidencia;
import com.helpdesk.entidades.Rol;
import com.helpdesk.entidades.Usuario;
import com.helpdesk.operaciones.Operaciones;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

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

    public static ArrayList<Departamento> getDeptosByEmpresa(int id) {
        ArrayList<Departamento> list = new ArrayList<>();
        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            String query = "select a.iddepto\n"
                    + "from empresasbydeptos a, departments b\n"
                    + "where a.iddepto = b.iddepto and a.idempresa =" + id;
            String array[][] = Operaciones.consultar(query, null);
            if(array != null){
                for(int i = 0; i<array[0].length;i++){
                    Departamento depto = new Departamento();
                    //array[][] Columna-fila
                    depto = Operaciones.get(Integer.parseInt(array[0][i]), new Departamento());
                    list.add(depto);
                }
            }else{
                list = null;
            }
            
        } catch (Exception ex) {
            Logger.getLogger(DataList.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex1) {
                Logger.getLogger(DataList.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return list;
    }
    
    public static boolean existsDeptoInEmpresa(int idemp,int iddep){
        boolean exists = false;
        try{
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            String query = "select * from empresasbydeptos where iddepto = "+ iddep + " and  idempresa="+idemp;
            String array[][] = Operaciones.consultar(query, null);
            if(array!=null){
                exists = true;
            }
        }catch (Exception ex) {
            Logger.getLogger(DataList.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex1) {
                Logger.getLogger(DataList.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return exists;
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

    public static ArrayList<Empresa> getAllEmpresas() {
        ArrayList<Empresa> list = new ArrayList<>();
        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            list = Operaciones.getTodos(new Empresa());
        } catch (Exception ex) {
            Logger.getLogger(DataList.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex1) {
                Logger.getLogger(DataList.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return list;
    }

    public static Integer getIdDepto(Integer idUsuario) throws Exception {
        Integer idDepto = 0;

        String sql = "select iddepto from deptobyusers where iduser = ?";
        List<Object> params = new ArrayList();
        params.add(idUsuario);

        String[][] rs = Operaciones.consultar(sql, params);
        idDepto = Integer.parseInt(rs[0][0]);//Solo devuelve un valor 

        return idDepto;
    }

    /*Esta funcion trae todos los usuarios que tienen el rol de empleado*/
    public static ArrayList<listarEmpleado> getEmpleados(int id) {
        String query = "select iduser from users where idrole = 4";
        String query2 = "select idusuario from usuariosrequisicion where idrol = 8";
        ArrayList<listarEmpleado> list = new ArrayList<>();
        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);

            String[][] array = null;

            if (id == 0) {
                //Empleado Helpdesk
                array = Operaciones.consultar(query, null);
            } else {
                //Empleado Requisicion
                array = Operaciones.consultar(query2, null);
            }

            if (array != null) {
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
            }

        } catch (Exception ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex1) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex1);
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

    public static boolean permisosSobreIncidencia(int idIncidencia, HttpSession sesion) {
        boolean grant = false;
        Integer Rol = (int) sesion.getAttribute("Rol");
        Integer IdUs = (int) sesion.getAttribute("idUsuario");
        Integer IdDepto = (int) sesion.getAttribute("idDepUser");

        try {
            ConexionPool conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Incidencia i = Operaciones.get(idIncidencia, new Incidencia());
            if (i.getIdIncidence() == 0) {
                return grant;
            }

            //Esta Query se usara para los lideres y los receptores 
            String sql = "select idibr from incidencebyreceptor where idreceptor = ? and idincidence = ?";
            List<Object> parametros = new ArrayList();
            parametros.add(IdUs);
            parametros.add(idIncidencia);
            String[][] result = Operaciones.consultar(sql, parametros);
            boolean ibrValido = (result != null) ? true : false; //Si es verdadero es porque pertenece a un registro del control y si la puede ver

            switch (Rol) {
                case 1: { //Para caso del admin 
                    grant = true;//Tiene privilegios para todas las incidencias 
                    break;
                }
                case 2: { //Para el caso el lider 
                    //la tercera condicion es para saber si estube implicado con esa incidencia
                    boolean grant2 = false;
                    if (IdDepto == getIdDepto(i.getIdreceptor())) {
                        grant2 = true;
                    }

                    if (i.getIdCreator() == IdUs || i.getIdreceptor() == IdUs || ibrValido || grant2) {
                        grant = true;
                    }
                    break;
                }

                case 3: { //para el caso del receptor 
                    if (i.getIdreceptor() == IdUs || ibrValido) {
                        grant = true;
                    }
                    break;
                }
            }

        } catch (Exception e) {
            Logger.getLogger(DataList.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(DataList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return grant;
    }

}
