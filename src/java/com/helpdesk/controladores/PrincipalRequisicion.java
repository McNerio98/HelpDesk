/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.controladores;

import com.google.gson.Gson;
import com.helpdesk.conexion.Conexion;
import com.helpdesk.conexion.ConexionPool;
import com.helpdesk.entidades.Empresa;
import com.helpdesk.entidades.RequisicionPago;
import com.helpdesk.operaciones.Operaciones;
import com.helpdesk.utilerias.DataEmpresa;
import com.helpdesk.utilerias.DataList;
import com.helpdesk.utilerias.DataRequisicion;
import com.helpdesk.utilerias.Enums;
import com.helpdesk.utilerias.RequisicionByRol;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Mack_
 */
public class PrincipalRequisicion extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
 /*request.getRequestDispatcher("pnlRequisicion.jsp").forward(request, response);*/
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        HttpSession s = request.getSession();
        String accion = request.getParameter("accion");
        int idUserSession = (int) s.getAttribute("idUsuario");
        int rol = (int) s.getAttribute("Rol");
        if (accion == null) {
            if (rol == Enums.ROL.LIDER_REQ) {
                RequisicionByRol r = new RequisicionByRol(idUserSession);
                ArrayList<RequisicionPago> list = r.getAllRequisiciones();
                ArrayList<DataRequisicion> datalist = new ArrayList<>();
                if (r.getRequisicionByStatus(Enums.ESTADO_REQ.REVISION) != null) {
                    s.setAttribute("processDiv", r.getRequisicionByStatus(Enums.ESTADO_REQ.REVISION));
                } else {
                    s.setAttribute("processDiv", null);
                }
                if (r.getRequisicionByStatus(Enums.ESTADO_REQ.ACEPTADA) != null) {
                    s.setAttribute("pendingDiv", r.getRequisicionByStatus(Enums.ESTADO_REQ.ACEPTADA));
                } else {
                    s.setAttribute("pendingDiv", null);
                }
                if(r.getRequisicionByStatus(Enums.ESTADO_REQ.FINALIZADA)!=null){
                    s.setAttribute("finishDiv", r.getRequisicionByStatus(Enums.ESTADO_REQ.FINALIZADA));
                }else{
                    s.setAttribute("finishDiv", null);
                }
                if (list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        datalist.add(DataList.getGeneralData(list.get(i).getIdRequisicion()));
                    }
                    s.setAttribute("listRequisiciones", datalist);

                } else {
                    s.setAttribute("listRequisiciones", null);
                }

                request.getRequestDispatcher("pnlRequisicion.jsp").forward(request, response);
            }
            if (rol == Enums.ROL.GERENTE_REQ) {
                s.setAttribute("requestEmpleado", DataList.getEmpleados(1));
                request.getRequestDispatcher("pnlRequisicion.jsp").forward(request, response);
            }
            if (rol == Enums.ROL.RECEPTOR_REQ) {
                /// RequisicionInfo?idReq=1
                RequisicionByRol rbr = new RequisicionByRol(idUserSession);
                s.setAttribute("todasDiv", rbr.getAllRequisiciones());
                s.setAttribute("requestDiv", rbr.getRequisicionByStatus(Enums.ESTADO_REQ.SOLICITADA));
                s.setAttribute("refuseDiv", rbr.getRequisicionByStatus(Enums.ESTADO_REQ.RECHAZADA));
                s.setAttribute("finishDiv", rbr.getRequisicionByStatus(Enums.ESTADO_REQ.FINALIZADA));

                s.setAttribute("processDiv", rbr.getRequisicionByStatus(Enums.ESTADO_REQ.REVISION));
                s.setAttribute("pendingDiv", rbr.getRequisicionByStatus(Enums.ESTADO_REQ.ACEPTADA));
                request.getRequestDispatcher("pnlRequisicion.jsp").forward(request, response);
            }
            if (rol == Enums.ROL.CONTADOR_REQ) {
                RequisicionByRol r = new RequisicionByRol(idUserSession);
                ArrayList<Empresa> listEmp = this.getListEmpresaByContador(idUserSession);
                ArrayList<DataEmpresa> listData = new ArrayList<>();
                ArrayList<Object> dataobject = r.getAllRequisicionesByContadorAndPriority();

                if (listEmp != null) {
                    for (int i = 0; i < listEmp.size(); i++) {
                        DataEmpresa data = new DataEmpresa();
                        data.setEmpresa(listEmp.get(i));
                        data.setListDeptos(DataList.getDeptosByEmpresa(listEmp.get(i).getIdEmpresa()));
                        listData.add(data);
                    }
                } else {
                    listData = null;
                }
                s.setAttribute("ListEmpresas", listData);
                s.setAttribute("listBaja", (ArrayList<RequisicionPago>) dataobject.get(0));
                s.setAttribute("listMedia", (ArrayList<RequisicionPago>) dataobject.get(1));
                s.setAttribute("listAlta", (ArrayList<RequisicionPago>) dataobject.get(2));
                s.setAttribute("listFinish", r.getRequisicionByStatus(5));
                request.getRequestDispatcher("pnlRequisicion.jsp").forward(request, response);
                //response.sendRedirect("./PrincipalRequisicion?accion=load&idemp=1&iddep=3");
            }
            if(rol == Enums.ROL.EMPLEADO_REQ){
                request.getRequestDispatcher("pnlRequisicion.jsp").forward(request, response);
            }

        } else {
            PrintWriter out = response.getWriter();
            RequisicionByRol r = new RequisicionByRol(idUserSession);
            switch (accion) {
                case "solicitadas": {
                    if (r.getRequisicionByStatus(Enums.ESTADO_REQ.SOLICITADA) != null) {
                        ArrayList<DataRequisicion> data = new ArrayList<>();
                        for (int i = 0; i < (r.getRequisicionByStatus(Enums.ESTADO_REQ.SOLICITADA)).size(); i++) {
                            DataRequisicion d = DataList.getGeneralData(r.getRequisicionByStatus(Enums.ESTADO_REQ.SOLICITADA).get(i).getIdRequisicion());
                            data.add(d);
                        }
                        String json = new Gson().toJson(data);
                        out.print(json);
                    } else {
                        out.print("null");
                    }
                    break;
                }
                case "todas": {
                    if (r.getAllRequisiciones() != null) {
                        ArrayList<DataRequisicion> data = new ArrayList<>();
                        for (int i = 0; i < (r.getAllRequisiciones()).size(); i++) {
                            DataRequisicion d = DataList.getGeneralData(r.getAllRequisiciones().get(i).getIdRequisicion());
                            data.add(d);
                        }
                        String json = new Gson().toJson(data);
                        out.print(json);
                    } else {
                        out.print("null");
                    }
                    break;
                }
                case "enproceso": {
                    if (r.getRequisicionByStatus(Enums.ESTADO_REQ.REVISION) != null) {
                        ArrayList<DataRequisicion> data = new ArrayList<>();
                        for (int i = 0; i < (r.getRequisicionByStatus(Enums.ESTADO_REQ.REVISION)).size(); i++) {
                            DataRequisicion d = DataList.getGeneralData(r.getRequisicionByStatus(Enums.ESTADO_REQ.REVISION).get(i).getIdRequisicion());
                            data.add(d);
                        }
                        String json = new Gson().toJson(data);
                        out.print(json);
                    } else {
                        out.print("null");
                    }
                    break;
                }
                case "pending": {
                    if (r.getRequisicionByStatus(Enums.ESTADO_REQ.ACEPTADA) != null) {
                        ArrayList<DataRequisicion> data = new ArrayList<>();
                        for (int i = 0; i < (r.getRequisicionByStatus(Enums.ESTADO_REQ.ACEPTADA)).size(); i++) {
                            DataRequisicion d = DataList.getGeneralData(r.getRequisicionByStatus(Enums.ESTADO_REQ.ACEPTADA).get(i).getIdRequisicion());
                            data.add(d);
                        }
                        String json = new Gson().toJson(data);
                        out.print(json);
                    } else {
                        out.print("null");
                    }
                    break;
                }
                case "finalizadas": {
                    if (r.getRequisicionByStatus(Enums.ESTADO_REQ.FINALIZADA) != null) {
                        ArrayList<DataRequisicion> data = new ArrayList<>();
                        for (int i = 0; i < (r.getRequisicionByStatus(Enums.ESTADO_REQ.FINALIZADA)).size(); i++) {
                            DataRequisicion d = DataList.getGeneralData(r.getRequisicionByStatus(Enums.ESTADO_REQ.FINALIZADA).get(i).getIdRequisicion());
                            data.add(d);
                        }
                        String json = new Gson().toJson(data);
                        out.print(json);
                    } else {
                        out.print("null");
                    }
                    break;
                }
                case "refuse": {
                    if (r.getRequisicionByStatus(Enums.ESTADO_REQ.RECHAZADA) != null) {
                        ArrayList<DataRequisicion> data = new ArrayList<>();
                        for (int i = 0; i < (r.getRequisicionByStatus(Enums.ESTADO_REQ.RECHAZADA)).size(); i++) {
                            DataRequisicion d = DataList.getGeneralData(r.getRequisicionByStatus(Enums.ESTADO_REQ.RECHAZADA).get(i).getIdRequisicion());
                            data.add(d);
                        }
                        String json = new Gson().toJson(data);
                        out.print(json);
                    } else {
                        out.print("null");
                    }
                    break;
                }
                case "load": {
                    if (rol == Enums.ROL.CONTADOR_REQ) {
                        int idemp = Integer.parseInt(request.getParameter("idemp"));
                        int iddep = Integer.parseInt(request.getParameter("iddep"));
                        ArrayList<Object> main = this.loadRequisiciones(request, response, idemp, iddep);

                        ArrayList<RequisicionPago> listBaja = (ArrayList<RequisicionPago>) main.get(0);
                        ArrayList<RequisicionPago> listMedia = (ArrayList<RequisicionPago>) main.get(1);
                        ArrayList<RequisicionPago> listAlta = (ArrayList<RequisicionPago>) main.get(2);
                        String prt = request.getParameter("priority");
                        if (prt != null) {
                            switch (Integer.parseInt(prt)) {
                                case 1: {
                                    ArrayList<DataRequisicion> data = new ArrayList<>();
                                    for (int i = 0; i < listBaja.size(); i++) {
                                        DataRequisicion d = DataList.getGeneralData(listBaja.get(i).getIdRequisicion());
                                        data.add(d);
                                    }
                                    String json = new Gson().toJson(data);
                                    out.print(json);
                                    break;
                                }
                                case 2: {
                                    ArrayList<DataRequisicion> data = new ArrayList<>();
                                    for (int i = 0; i < listMedia.size(); i++) {
                                        DataRequisicion d = DataList.getGeneralData(listMedia.get(i).getIdRequisicion());
                                        data.add(d);
                                    }
                                    String json = new Gson().toJson(data);
                                    out.print(json);
                                    break;
                                }
                                case 3: {
                                    ArrayList<DataRequisicion> data = new ArrayList<>();
                                    for (int i = 0; i < listAlta.size(); i++) {
                                        DataRequisicion d = DataList.getGeneralData(listAlta.get(i).getIdRequisicion());
                                        data.add(d);
                                    }
                                    String json = new Gson().toJson(data);
                                    out.print(json);
                                    break;
                                }
                                default: {
                                    out.print("undefined");
                                    break;
                                }
                            }
                        } else {

                            s.setAttribute("listBaja", listBaja);
                            s.setAttribute("listMedia", listMedia);
                            s.setAttribute("listAlta", listAlta);
                            request.getRequestDispatcher("pnlRequisicion.jsp").forward(request, response);
                        }
                    } else {
                        s.setAttribute("listBaja", null);
                        s.setAttribute("listMedia", null);
                        s.setAttribute("listAlta", null);
                        request.getRequestDispatcher("pnlRequisicion.jsp").forward(request, response);
                    }

                    break;
                }
                case "priority": {
                    String priority = request.getParameter("id");
                    ArrayList<Object> dataobject = r.getAllRequisicionesByContadorAndPriority();
                    switch (Integer.parseInt(priority)) {
                        case 1: {
                            ArrayList<DataRequisicion> data = new ArrayList<>();
                            ArrayList<RequisicionPago> list = (ArrayList<RequisicionPago>) dataobject.get(0);
                            for (int i = 0; i < list.size(); i++) {
                                DataRequisicion d = DataList.getGeneralData(list.get(i).getIdRequisicion());
                                data.add(d);
                            }
                            String json = new Gson().toJson(data);
                            out.print(json);
                            break;
                        }
                        case 2: {
                            ArrayList<DataRequisicion> data = new ArrayList<>();
                            ArrayList<RequisicionPago> list = (ArrayList<RequisicionPago>) dataobject.get(1);
                            for (int i = 0; i < list.size(); i++) {
                                DataRequisicion d = DataList.getGeneralData(list.get(i).getIdRequisicion());
                                data.add(d);
                            }
                            String json = new Gson().toJson(data);
                            out.print(json);
                            break;
                        }
                        case 3: {
                            ArrayList<DataRequisicion> data = new ArrayList<>();
                            ArrayList<RequisicionPago> list = (ArrayList<RequisicionPago>) dataobject.get(2);
                            for (int i = 0; i < list.size(); i++) {
                                DataRequisicion d = DataList.getGeneralData(list.get(i).getIdRequisicion());
                                data.add(d);
                            }
                            String json = new Gson().toJson(data);
                            out.print(json);
                            break;
                        }
                        case 4:{
                            ArrayList<DataRequisicion> data = new ArrayList<>();
                            ArrayList<RequisicionPago> list = r.getRequisicionByStatus(5);
                            for (int i = 0; i < list.size(); i++) {
                                DataRequisicion d = DataList.getGeneralData(list.get(i).getIdRequisicion());
                                data.add(d);
                            }
                            String json = new Gson().toJson(data);
                            out.print(json);
                            break;
                        }
                    }
                    break;
                }

            }
        }

    }

    public ArrayList<Object> loadRequisiciones(HttpServletRequest request, HttpServletResponse response, int idemp, int iddep) {

        HttpSession s = request.getSession();
        ArrayList<Object> mainlist = new ArrayList<>();
        ArrayList<RequisicionPago> listBaja = new ArrayList<>();
        ArrayList<RequisicionPago> listMedia = new ArrayList<>();
        ArrayList<RequisicionPago> listAlta = new ArrayList<>();

        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);

            for (int i = 1; i < 4; i++) {
                String query = "select idrequisicion \n"
                        + "from requisicionespagos \n"
                        + "where idempresa = " + idemp + " and iddepto = " + iddep + " and estado = 3 and prioridad =" + i;
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

    public ArrayList<Empresa> getListEmpresaByContador(int id) {
        ArrayList<Empresa> list = new ArrayList<>();
        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            String query = "select a.idempresa\n"
                    + "from empresas a, usuariosrequisicion b, usuarioreqbyempresas c\n"
                    + "where \n"
                    + "a.idempresa=c.idempresa and b.idusuario=c.idusuario and a.nombre != 'x'  and b.idrol = 9 and b.idusuario=" + id;

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

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
