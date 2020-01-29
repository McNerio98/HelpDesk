/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.controladores;

import com.helpdesk.conexion.Conexion;
import com.helpdesk.conexion.ConexionPool;
import com.helpdesk.entidades.Gestion;
import com.helpdesk.operaciones.Operaciones;
import com.helpdesk.utilerias.DataControl;
import com.helpdesk.utilerias.DataGestion;
import com.helpdesk.utilerias.DataIncidencia;
import com.helpdesk.utilerias.DataNotes;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
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

        if (idIncidencia == null) {
            //Sin parametro de identificacion 
            response.sendRedirect("Principal");
        } else { //Se extrae informacion de la incidencia 
            
            if (request.getSession().getAttribute("statusUpdate") != null) {
                request.setAttribute("statusUpdate", request.getSession().getAttribute("statusUpdate"));
                request.getSession().removeAttribute("statusUpdate");
            }            
            
            ArrayList<DataControl> LstControl = getListControl(idIncidencia);
            if (LstControl != null) { //Siempre existira un registro por la incidencia 
                //Se procebe a obtener el idIBR del ultimo registro 
                int idIBR = LstControl.get(LstControl.size() - 1).getIdIBR(); //Toma el id del ultimo control

                DataIncidencia dataInfo = getDefinicion(idIncidencia);

                if (dataInfo != null) {
                    ArrayList<DataNotes> LstNotes = getNotes(idIncidencia);//Puedo o no haber notas 
                    request.setAttribute("ObjectInfo", dataInfo); //
                    request.setAttribute("LstControl", LstControl); //Colecion
                    request.setAttribute("LstNotes", LstNotes); //Colecion
                    request.setAttribute("idIncidence", idIncidencia); // 
                    request.setAttribute("ibr", idIBR); // 
                    request.getRequestDispatcher("Informacion.jsp").forward(request, response);
                } else {
                    out.print("Error al Solicitar la Definicion");
                }

            } else {
                out.print("Problema al solicitar el Control");
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        String accion = request.getParameter("accion");
        String idIBR = request.getParameter("idibr");

        if (accion.equals("listGestiones")) {
            request.setAttribute("LstGestiones", getAllGestiones(Integer.parseInt(idIBR)));
            request.getRequestDispatcher("Gestiones.jsp").forward(request, response);
        }

    }

    private DataIncidencia getDefinicion(Integer idIncidencia) {
        DataIncidencia di = new DataIncidencia();

        try {

            String sql = "select d.deptoname, i.title, i.description, i.status, to_char(i.creationday,'DD-MM-YY HH:MI AM'), "
                    + "u.username rname, u.iduser rid,concat(u.firstname, ' ',u.lastname) as receptor, "
                    + "dbu.iddepto as depReceptor, u2.username cname, concat(u2.firstname, ' ', u2.lastname) as creador, "
                    + "c.classification, i.totalcost from departments d, incidences i, users u, users u2, "
                    + "classifications c, deptobyusers dbu where i.iddepto = d.iddepto and "
                    + "c.idclassification = i.idclassification and i.idreceptor = u.iduser and "
                    + "u.iduser = dbu.iduser and i.idcreator = u2.iduser and i.idincidence = ?";

            List<Object> params = new ArrayList();
            params.add(idIncidencia);

            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);

            String[][] rs = Operaciones.consultar(sql, params);

            if (rs != null) {
                di.setDepartamento(rs[0][0]);
                di.setTitulo(rs[1][0]);
                di.setDescripcion(rs[2][0]);
                di.setStatus(Integer.parseInt(rs[3][0]));
                di.setFechaCreacion(rs[4][0]);
                di.setTecnico(rs[5][0]);
                di.setIdTecnico(Integer.parseInt(rs[6][0]));
                di.setNomTecnico(rs[7][0]);
                di.setIdDeptoTecnico(Integer.parseInt(rs[8][0]));
                di.setCreador(rs[9][0]);
                di.setNomCreador(rs[10][0]);
                di.setClasificacion(rs[11][0]);
                di.setCostoTotal(rs[12][0]);
            } else {
                di = null;
            }

        } catch (Exception ex) {
            di = null;
            Logger.getLogger(Informacion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex1) {
                Logger.getLogger(Informacion.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

        return di;
    }

    private ArrayList<DataControl> getListControl(Integer idIncidencia) {
        ArrayList<DataControl> LstControl = new ArrayList<>();

        String sql = "select ibr.idibr, u.username, ibr.status, \n"
                + "to_char(ibr.startdate,'DD-MM-yy HH:MI PM'), to_char(i.finaldate,'DD-MM-yy HH:MI PM'), to_char(ibr.finaldate,'DD-MM-yy HH:MI PM') from users u, \n"
                + "incidencebyreceptor ibr, incidences i where \n"
                + "ibr.idreceptor = u.iduser and \n"
                + "ibr.idincidence = i.idincidence and ibr.idincidence = ? order by idibr";

        List<Object> params = new ArrayList();
        params.add(idIncidencia);

        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            String[][] rs = Operaciones.consultar(sql, params);
            if (rs != null) { //Si hay valores y se crea la lista 
                for (int i = 0; i < rs[0].length; i++) {
                    DataControl ct = new DataControl();
                    ct.setIdIBR(Integer.parseInt(rs[0][i]));
                    ct.setReceptor(rs[1][i]);
                    ct.setStatus(Integer.parseInt(rs[2][i]));
                    ct.setInicioReal(rs[3][i]);
                    ct.setFinPrev(rs[4][i]);
                    ct.setFinReal(rs[5][i]);
                    LstControl.add(ct);
                }
            }
        } catch (Exception ex) {
            LstControl = null;
            Logger.getLogger(Informacion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex1) {
                Logger.getLogger(Informacion.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return LstControl;
    }

    private ArrayList<DataNotes> getNotes(Integer idIncidencia) {
        ArrayList<DataNotes> ListNotes = new ArrayList<>();

        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            String sql = "select CONCAT(u.firstname,' ',u.lastname) as fullname,\n"
                    + "r.rolename, n.description, n.notetype \n"
                    + "from roles r, users u, notes n \n"
                    + "where n.idholder = u.iduser and u.idrole = r.idrol and \n"
                    + "n.idincidence = " + idIncidencia + " order by n.idnota";
            String[][] rs = Operaciones.consultar(sql, null);

            if (rs != null) {
                for (int i = 0; i < rs[0].length; i++) {
                    DataNotes dn = new DataNotes();
                    dn.setFullname(rs[0][i]);
                    dn.setRoleName(rs[1][i]);
                    dn.setDescription(rs[2][i]);
                    dn.setNoteType(Integer.parseInt(rs[3][i]));
                    ListNotes.add(dn);
                }
            } else {
                ListNotes = null;
            }

        } catch (Exception ex) {
            Logger.getLogger(Informacion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex1) {
                Logger.getLogger(Informacion.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

        return ListNotes;
    }

    private ArrayList<DataGestion> getAllGestiones(Integer idIBR) {
        ArrayList<DataGestion> LstGestiones = new ArrayList<>();

        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);

            String sql = "select typemanagement,title,description,to_char(correctionday,'dd-MM-yy HH:MI AM'),attachfile,costmsg,idmanagement from managements where idibr = ? order by idmanagement desc";
            List<Object> params = new ArrayList<>();
            params.add(idIBR);
            String[][] rs = Operaciones.consultar(sql, params);

            if (rs != null) {
                for (int i = 0; i < rs[0].length; i++) {
                    DataGestion dg = new DataGestion();
                    dg.setType(Integer.parseInt(rs[0][i]));
                    dg.setTitle(rs[1][i]);
                    dg.setDescription(rs[2][i]);
                    dg.setFecha(rs[3][i]);
                    dg.setAttach(rs[4][i]);
                    dg.setCosto(rs[5][i]);

                    LstGestiones.add(dg);
                }
            } else {
                LstGestiones = null; //No gay gestiones
            }

        } catch (Exception ex) {
            Logger.getLogger(Informacion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Informacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return LstGestiones;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
