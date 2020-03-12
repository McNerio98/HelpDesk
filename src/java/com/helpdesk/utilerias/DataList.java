/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.utilerias;

import com.helpdesk.conexion.Conexion;
import com.helpdesk.conexion.ConexionPool;
import com.helpdesk.controladores.Informacion;
import com.helpdesk.controladores.JavaMail;
import com.helpdesk.controladores.Login;
import com.helpdesk.entidades.Clasificacion;
import com.helpdesk.entidades.Departamento;
import com.helpdesk.entidades.Empresa;
import com.helpdesk.entidades.Incidencia;
import com.helpdesk.entidades.RequisicionPago;
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
    
    public static void sendNotificationToSolicitante(Usuario users, int idReq){
        htmlTemplate html = new htmlTemplate();
        DataRequisicion data = getGeneralData(idReq);
        //Correo para el solicitante
        
             html.difineTag(
                    "<h1>En hora buena! Tu solicitud ha sido autorizada y finalizada.</h1>"
                     +"<p>Se ha autorizado tu requisicion por: " + data.getSuperior()
                     +", entra a helpdesk para verificar tu requisicion.</p>"
            ); 
            html.difineTag(" \n \n"
                    + "© 2019-2020 HelpDesk McNerio & CnkBlanco USO. All Rights Reserved.\n");
            
            JavaMail.SendMessage(users.getEmail(), "Tienes una nueva requisicion por finalizar", html.RenderHTML());
   
    }
    public static void sendNotificationToContador(Usuario users, int idReq){
        htmlTemplate html = new htmlTemplate();
        DataRequisicion data = getGeneralData(idReq);
        //Correo para el contador
        html.difineTag(
                    "<h1>Hola, " + users.getFirsName()
                    + " " + users.getLastName() + "</h1>"
            );
            html.difineTag(
                    "<strong>" + data.getSolicitante() + "</strong>"
                    + " de la empresa " + data.getEmpresa()
                    + " del departamento de " + data.getDepto()
                    + " se le ha autorizado la requisicion por <strong>"+data.getSuperior()+".</strong><br>"
            );
            html.difineTag("<h3>Monto: $" + data.getMontoTotal() + " - Prioridad: "+data.getPrioridad()+"</h3>");
            
            
            
            JavaMail.SendMessage(users.getEmail(), "Tienes una nueva requisicion por finalizar", html.RenderHTML());
    }

    public static void SendNotificationsToLiders(ArrayList<Usuario> listLideres, int idReq) {
        DataRequisicion data = getGeneralData(idReq);
        for (int i = 0; i < listLideres.size(); i++) {
            htmlTemplate html = new htmlTemplate();
            html.difineTag(
                    "<h1>Hola, " + listLideres.get(i).getFirsName()
                    + " " + listLideres.get(i).getLastName() + "</h1>"
            );
            html.difineTag(
                    "<strong>" + data.getSolicitante() + "</strong>"
                    + " de la empresa " + data.getEmpresa()
                    + " del departamento de " + data.getDepto()
                    + " ha solicitado una nueva requisicion.<br>"
            );
            html.difineTag("<h3>Monto: $" + data.getMontoTotal() + " - Prioridad: "+data.getPrioridad()+"</h3>");
            html.difineTag("<p style='text-align:justify'>Entra a Helpdesk y decide tomar la requisicion.</p>");
            if (JavaMail.SendMessage(listLideres.get(i).getEmail(), "Solicitud de Requisicion", html.RenderHTML())) {
                System.out.print("Notificacion Enviada");
            } else {
                System.out.print("Notificacion no enviada");
            }
        }
    }

    public static DataRequisicion getGeneralData(Integer idReq) {

        DataRequisicion dt = new DataRequisicion();
        dt.setId(idReq);
        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            RequisicionPago rg = Operaciones.get(idReq, new RequisicionPago());
            List<Object> params = new ArrayList();
            params.add(idReq);
            params.add(idReq);
            params.add(idReq);

            String cmd = "select concat(u1.firstname,' ',u1.lastname) creador, to_char(rg.fecha,'dd-MM-yyyy HH:MI') fecha, \n"
                    + "to_char(rg.fechaestimada,'dd-MM-yyyy HH:MI') fechaestimada,  \n"
                    + "rg.anombre, \n"
                    + "rg.total, rg.estado, e.nombre, d.deptoname, (select concat(u2.firstname,' ', u2.lastname) from users u2, \n"
                    + "requisicionespagos rg2 where rg2.idautorizador = u2.iduser and rg2.idrequisicion =?) Superior, \n"
                    + "(select concat(u3.firstname,' ', u3.lastname) from users u3,requisicionespagos rg3 \n"
                    + "where rg3.idcontador = u3.iduser and rg3.idrequisicion =?) Contador, rg.prioridad from requisicionespagos rg, \n"
                    + "users u1, empresas e, departments d where rg.idcreador = u1.iduser and rg.idempresa = e.idempresa \n"
                    + "and rg.iddepto = d.iddepto and idrequisicion = ? ";
            String[][] rs = Operaciones.consultar(cmd, params);

            dt.setSolicitante(rs[0][0]);
            dt.setFecha(rs[1][0]);
            dt.setFechaEstimada(rs[2][0]);
            dt.setaNombre(rs[3][0]);
            dt.setMontoTotal(rs[4][0]);
            dt.setEstado(Integer.parseInt(rs[5][0]));
            dt.setEmpresa(rs[6][0]);
            dt.setDepto(rs[7][0]);
            dt.setSuperior(rs[8][0]);
            dt.setContador(rs[9][0]);
            dt.setPrioridad(Integer.parseInt(rs[10][0]));
        } catch (Exception e) {
            Logger.getLogger(DataList.class.getName()).log(Level.SEVERE, null, e);
            dt = null;
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(DataList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return dt;
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
            if (array != null) {
                for (int i = 0; i < array[0].length; i++) {
                    Departamento depto = new Departamento();
                    //array[][] Columna-fila
                    depto = Operaciones.get(Integer.parseInt(array[0][i]), new Departamento());
                    list.add(depto);
                }
            } else {
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

    public static boolean existsDeptoInEmpresa(int idemp, int iddep) {
        boolean exists = false;
        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            String query = "select * from empresasbydeptos where iddepto = " + iddep + " and  idempresa=" + idemp;
            String array[][] = Operaciones.consultar(query, null);
            if (array != null) {
                exists = true;
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
            String query = "select idempresa from empresas where nombre !='x' and direccion !='x';";
            String array[][] = Operaciones.consultar(query, null);
            if (array != null) {
                for (int i = 0; i < array[0].length; i++) {
                    list.add(Operaciones.get(Integer.parseInt(array[0][i]), new Empresa()));
                }
            } else {
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

    //El metodo getIdDepto y getIdEmpresa deben estar dentro una  estructura de conexion 
    public static Integer getIdDepto(Integer idUsuario) throws Exception {
        Integer idDepto = 0;

        String sql = "select iddepto from deptobyusers where iduser = ?";
        List<Object> params = new ArrayList();
        params.add(idUsuario);

        String[][] rs = Operaciones.consultar(sql, params);
        idDepto = Integer.parseInt(rs[0][0]);//Solo devuelve un valor 

        return idDepto;
    }

    public static Integer getIdEmpresa(Integer idUsuario) throws Exception {
        Integer idEmpresa = 0;

        String sql = "select idempresa from usuarioreqbyempresas where idusuario = ?";
        List<Object> params = new ArrayList();
        params.add(idUsuario);

        String[][] rs = Operaciones.consultar(sql, params);
        idEmpresa = Integer.parseInt(rs[0][0]);//Solo devuelve un valor 
        return idEmpresa;
    }

    public static Integer getIdContador(Integer idEmpresa) throws Exception {
        Integer idContador = 0;
        String sql = "select ue.idusuario from usuarioreqbyempresas ue, usuariosrequisicion rq \n"
                + "where ue.idusuario = rq.idusuario and rq.idrol = 9 and ue.idempresa = ? ";
        List<Object> params = new ArrayList();
        params.add(idEmpresa);

        String[][] rs = Operaciones.consultar(sql, params);
        if (rs != null) {
            idContador = Integer.parseInt(rs[0][0]);//Solo devuelve un valor 
        }

        return idContador;
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
    
    

    public static boolean permisosSobreRequisicion(int idReq, HttpSession sesion) {
        boolean grant = false;
        Integer Rol = (int) sesion.getAttribute("Rol");
        Integer IdUs = (int) sesion.getAttribute("idUsuario");

        try {
            ConexionPool conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            RequisicionPago pg = Operaciones.get(idReq, new RequisicionPago());
            if (pg.getIdRequisicion() == 0) {
                throw new Exception("No se recupero o no existe la requisicion");
            }

            switch (Rol) {
                case 5:// gerente req
                    grant = true; //Tiene acceso a todas las requisiciones 
                    break;
                case 6:  //lider req Para el lider podra si el la acepto sin importar en desenlace que tuvo 
                    if (pg.getEstado() == 1 || (pg.getIdAutorizador() != null && pg.getIdAutorizador() == IdUs)) {
                        grant = true;
                    }
                    break;
                case 7: //receptor 
                    if (pg.getIdCreador() == IdUs) {
                        grant = true;
                    } // Siempre estara el creador 
                    break;
                case 9: // Contador
                    if (pg.getIdContador() != null && pg.getIdContador() == IdUs) {
                        grant = true;
                    }
                    break;
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
