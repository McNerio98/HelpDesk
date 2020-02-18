/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.controladores;

import com.google.gson.Gson;
import com.helpdesk.conexion.ConexionPool;
import com.helpdesk.entidades.Departamento;
import com.helpdesk.entidades.Empresa;
import com.helpdesk.entidades.EmpresaByDepto;
import com.helpdesk.operaciones.Operaciones;
import com.helpdesk.utilerias.DataList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
 * @author ALEX
 */
public class Empresas extends HttpServlet {

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
            String accion = request.getParameter("accion");
            switch (accion) {
                case "nuevo": {
                    String empresaname = request.getParameter("empresaname");
                    String addres = request.getParameter("address");
                    if (empresaname.length() > 20 || addres.length() >= 200) {
                        request.setAttribute("errorCharacters", "crear");
                        request.getRequestDispatcher("empresas.jsp").forward(request, response);
                    } else {
                        Empresa enterprise = new Empresa();
                        enterprise.setNombre(empresaname);
                        enterprise.setDireccion(addres);
                        try {
                            ConexionPool conn = new ConexionPool();
                            conn.conectar();
                            Operaciones.abrirConexion(conn);
                            Operaciones.iniciarTransaccion();
                            Operaciones.insertar(enterprise);
                            Operaciones.commit();
                            response.sendRedirect(request.getContextPath() + "/Empresas");
                        } catch (Exception ex) {
                            try {
                                Operaciones.rollback();

                            } catch (SQLException ex1) {
                                Logger.getLogger(Empresas.class.getName()).log(Level.SEVERE, null, ex1);
                            }
                        } finally {
                            try {
                                Operaciones.cerrarConexion();
                            } catch (SQLException ex) {
                                Logger.getLogger(Empresas.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    break;
                }
                case "actualizar": {
                    String empresaname = request.getParameter("empresaname");
                    String addres = request.getParameter("address");
                    int idemp = Integer.parseInt(request.getParameter("IdEmpresa"));
                    if (empresaname.length() > 20 || addres.length() >= 200) {
                        request.setAttribute("errorCharacters", "crear");
                        request.getRequestDispatcher("empresas.jsp").forward(request, response);
                    } else {
                        Empresa enterprise = new Empresa();
                        enterprise.setNombre(empresaname);
                        enterprise.setDireccion(addres);
                        try {
                            ConexionPool conn = new ConexionPool();
                            conn.conectar();
                            Operaciones.abrirConexion(conn);
                            Operaciones.iniciarTransaccion();
                            Operaciones.actualizar(idemp, enterprise);
                            Operaciones.commit();
                            response.sendRedirect(request.getContextPath() + "/Empresas");
                        } catch (Exception ex) {
                            try {
                                Operaciones.rollback();

                            } catch (SQLException ex1) {
                                Logger.getLogger(Empresas.class.getName()).log(Level.SEVERE, null, ex1);
                            }
                        } finally {
                            try {
                                Operaciones.cerrarConexion();
                            } catch (SQLException ex) {
                                Logger.getLogger(Empresas.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    break;
                }
            }

        }
    }

    private boolean deleteDeptoInEmpresa(int iddep, int idemp) {
        boolean ok = false;
        try {
            ConexionPool conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            EmpresaByDepto emp = new EmpresaByDepto();
            String query = "select idebd from empresasbydeptos where iddepto = " + iddep + " and idempresa =" + idemp;

            String array[][] = Operaciones.consultar(query, null);

            if (array != null) {
                for (int i = 0; i < array[0].length; i++) {
                    emp = Operaciones.get(Integer.parseInt(array[0][i]), new EmpresaByDepto());
                }
                Operaciones.eliminar(emp.getIdEBD(), new EmpresaByDepto());
                Operaciones.commit();
                ok = true;
            } else {
                ok = false;
            }
        } catch (Exception ex) {
            ok = false;
            try {
                Operaciones.rollback();

            } catch (SQLException ex1) {
                Logger.getLogger(Empresas.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Empresas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ok;
    }

    private boolean insertDeptoInEmpresa(int iddep, int idemp) {
        boolean ok = true;
        try {
            ConexionPool conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            EmpresaByDepto emp = new EmpresaByDepto();
            emp.setIdDepto(iddep);
            emp.setIdEmpresa(idemp);
            Operaciones.insertar(emp);
            Operaciones.commit();
        } catch (Exception ex) {
            ok = false;
            try {
                Operaciones.rollback();

            } catch (SQLException ex1) {
                Logger.getLogger(Empresas.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Empresas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ok;
    }

    public String getAll() {
        ArrayList<Empresa> list = DataList.getAllEmpresas();
        String json = new Gson().toJson(list);

        return json;
    }

    public String getAllDeptoByEmpresa(int id) {
        ArrayList<Departamento> list = DataList.getDeptosByEmpresa(id);
        String json = new Gson().toJson(list);

        return json;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String accion = request.getParameter("accion");
        if (accion == null) {
            request.getRequestDispatcher("empresas.jsp").forward(request, response);
        }
        switch (accion) {
            case "getAll": {
                out.print(this.getAll());
                break;
            }
            case "getDeptosByEmpresa": {
                int id = Integer.parseInt(request.getParameter("id"));
                out.print(this.getAllDeptoByEmpresa(id));
                break;
            }
            case "existDepto": {
                int idemp = Integer.parseInt(request.getParameter("idemp"));
                int iddep = Integer.parseInt(request.getParameter("iddep"));
                if (DataList.existsDeptoInEmpresa(idemp, iddep)) {
                    out.print("true");
                } else {
                    out.print("false");
                }
                break;
            }
            case "insertDeptoToEmpresa": {
                int idemp = Integer.parseInt(request.getParameter("idemp"));
                int iddep = Integer.parseInt(request.getParameter("iddep"));
                if (this.insertDeptoInEmpresa(iddep, idemp)) {
                    out.print("true");
                } else {
                    out.print("false");
                }
                break;
            }
            case "deleteDeptoInEmpresa": {
                int idemp = Integer.parseInt(request.getParameter("idemp"));
                int iddep = Integer.parseInt(request.getParameter("iddep"));
                if (this.deleteDeptoInEmpresa(iddep, idemp)) {
                    out.print("true");
                } else {
                    out.print("false");
                }
                break;
            }
        }
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
        PrintWriter out = response.getWriter();
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
