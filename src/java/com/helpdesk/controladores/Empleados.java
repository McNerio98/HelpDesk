/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.controladores;

import com.google.gson.Gson;
import com.helpdesk.conexion.ConexionPool;
import com.helpdesk.entidades.Departamento;
import com.helpdesk.entidades.DeptoPorUsuario;
import com.helpdesk.entidades.Rol;
import com.helpdesk.entidades.Usuario;
import com.helpdesk.operaciones.Operaciones;
import com.helpdesk.utilerias.DataList;
import com.helpdesk.utilerias.listarEmpleado;
import com.helpdesk.utilerias.printUsuariosJsonByFilter;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author cnk
 */
public class Empleados extends HttpServlet {

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
            HttpSession s = request.getSession();
            String accion = request.getParameter("accion");
            int idUserSession = (int) s.getAttribute("idUsuario");
            int rolss = (int) s.getAttribute("Rol");

            if (accion == null) {
                if (rolss == 1) {
                    s.setAttribute("requestEmpleado", DataList.getEmpleados());
                }
                if (rolss == 2) {
                    s.setAttribute("requestIncidencia", DataList.getIncidenciasSolicitadas(idUserSession));

                }
                ArrayList<Departamento> listDepto = new ArrayList<>();
                ArrayList<Rol> listRol = new ArrayList<>();

                try {
                    ConexionPool conexion = new ConexionPool();
                    conexion.conectar();
                    Operaciones.abrirConexion(conexion);
                    Operaciones.iniciarTransaccion();
                    listDepto = Operaciones.getTodos(new Departamento());
                    listRol = Operaciones.getTodos(new Rol());

                } catch (Exception ex) {
                    Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex2) {
                        Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex2);
                    }
                }
                request.setAttribute("listDepto", listDepto);
                request.setAttribute("listRol", listRol);
                request.setAttribute("listEmpleados", DataList.getEmpleados());
                request.getRequestDispatcher("asignarRol.jsp").forward(request, response);
            } else {
                switch (accion) {
                    case "updateRolDepto": {
                        int rol = Integer.parseInt(request.getParameter("rol"));
                        int depto = Integer.parseInt(request.getParameter("depto"));
                        int iduser = Integer.parseInt(request.getParameter("iduser"));
                        Usuario user = new Usuario();
                        try {
                            ConexionPool conexion = new ConexionPool();
                            conexion.conectar();
                            Operaciones.abrirConexion(conexion);
                            Operaciones.iniciarTransaccion();
                            /*se setean los valores*/
                            user = Operaciones.get(iduser, new Usuario());
                            user.setIdRole(rol);

                            String query = "select updateDepto(?,?)";

                            List<Object> params = new ArrayList<>();
                            params.add(iduser);
                            params.add(depto);

                            String[][] iddep = Operaciones.consultar(query, params);

                            /*se actualiza respectivamente cada entidad*/
                            Operaciones.actualizar(user.getIdUser(), user);

                            Operaciones.commit();
                            response.sendRedirect(request.getContextPath() + "/Empleados");

                        } catch (Exception ex) {

                            try {
                                Operaciones.rollback();
                            } catch (SQLException ex1) {
                                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex1);
                            }
                        } finally {
                            try {
                                Operaciones.cerrarConexion();
                            } catch (SQLException ex) {
                                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        break;
                    }
                    case "buscar": {
                        try {

                            String fullname = request.getParameter("fullname");
                            String query = "select * from users where concat(firstname,lastname) like ?";
                            List<Object> params = new ArrayList<Object>();
                            params.add("%" + fullname + "%");

                            ConexionPool conexion = new ConexionPool();
                            conexion.conectar();
                            Operaciones.abrirConexion(conexion);
                            Operaciones.iniciarTransaccion();
                            String[][] array = Operaciones.consultar(query, params);
                            //System.out.print(array[0][0]);

                            if (array != null) {
                                ArrayList<listarEmpleado> lstEmpleado = new ArrayList<>();
                                //Array[numColumnas][numFilas]
                                for (int i = 0; i < array[0].length; i++) {
                                    Usuario usertmp = new Usuario();
                                    usertmp = Operaciones.get(Integer.parseInt(array[0][i]), new Usuario());

                                    /*Se instancian las entidades relacionadas 
                                      a usuarios (DeptoByUser, Departaments)
                                     */
                                    DeptoPorUsuario deptoUser = new DeptoPorUsuario();
                                    Departamento Depto = new Departamento();
//                                    deptoUser = Operaciones.get(usertmp.getIdUser(), new DeptoPorUsuario());
//                                    Depto = Operaciones.get(deptoUser.getIdDepto(), new Departamento());

                                    String query2 = "select * from deptobyusers where iduser =" + usertmp.getIdUser();

                                    String[][] array2 = Operaciones.consultar(query2, null);

                                    Depto = Operaciones.get(Integer.parseInt(array2[0][0]), new Departamento());

                                    Rol rol = new Rol();
                                    rol = Operaciones.get(usertmp.getIdRole(), new Rol());

                                    listarEmpleado empleado = new listarEmpleado();
                                    empleado.setUsuario(usertmp);
                                    empleado.setDepto(Depto);
                                    empleado.setRol(rol);
                                    lstEmpleado.add(empleado);

                                }
                                ArrayList<Departamento> listDepto = new ArrayList<>();
                                ArrayList<Rol> listRol = new ArrayList<>();

                                listDepto = Operaciones.getTodos(new Departamento());
                                listRol = Operaciones.getTodos(new Rol());

                                request.setAttribute("listEmpleado", lstEmpleado);
                                request.setAttribute("listDepto", listDepto);
                                request.setAttribute("listRol", listRol);
                                request.getRequestDispatcher("asignarRol.jsp").forward(request, response);
                            } else {
                                request.setAttribute("dataEmpty", "true");
                                request.getRequestDispatcher("asignarRol.jsp").forward(request, response);

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

                        break;
                    }
                    case "delete": {
                        int iduser = Integer.parseInt(request.getParameter("deleteiduser"));
                        try {
                            ConexionPool conexion = new ConexionPool();
                            conexion.conectar();
                            Operaciones.abrirConexion(conexion);
                            Operaciones.iniciarTransaccion();
                            String query = "select deleteDeptoByUsers(" + iduser + ")";

                            Operaciones.consultar(query, null);
                            Operaciones.commit();
                            response.sendRedirect(request.getContextPath() + "/Empleados?op=3");
                        } catch (Exception ex) {

                            try {
                                Operaciones.rollback();
                            } catch (SQLException ex1) {
                                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex1);
                            }
                        } finally {
                            try {
                                Operaciones.cerrarConexion();
                            } catch (SQLException ex) {
                                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                    }
                    case "userbyfilter": {

                        int idRol = Integer.parseInt(request.getParameter("idRol"));
                        int idDepto = Integer.parseInt(request.getParameter("idDepto"));
                        int idCase = Integer.parseInt(request.getParameter("idCase"));

                        switch (idCase) {
                            case 1: {
                                if (UsersByFilters(idRol, idDepto, 1) != null) {

                                    
                                    //String json = new Gson().toJson(UsersByFilters(idRol, idDepto, 1));
                                    
                                    //out.print(json);
                                    
                                     printUsuariosJsonByFilter.Render(UsersByFilters(idRol, idDepto, 1),response);
                                    
                                } else {
                                    out.print("null");
                                }
                                break;
                            }
                            case 2: {
                                if (UsersByFilters(idRol, idDepto, 2) != null) {

                                    
                                    //String json = new Gson().toJson(UsersByFilters(idRol, idDepto, 2));
                                    
                                    //out.print(json);
                                    
                                    printUsuariosJsonByFilter.Render(UsersByFilters(idRol, idDepto, 2),response);

                                } else {
                                    out.print("null");
                                }
                                break;
                            }
                        }

                        break;
                    }
                    
                    case "getAll":
                    {
                        this.getAllEmpleados(response);
                        break;
                    }
                }
            }
        }
    }

    public void getAllEmpleados(HttpServletResponse response) {

        ArrayList<Usuario> lstUsers = new ArrayList<>();
        ArrayList<listarEmpleado> lstEmpleado = new ArrayList<>();
        try {
            ConexionPool conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            lstUsers = Operaciones.getTodos(new Usuario());
            
            for(int i=0;i<lstUsers.size();i++){
                listarEmpleado lst = new listarEmpleado();
                lst.setUsuario(lstUsers.get(i));
                
                Departamento depto = new Departamento();
                depto = Operaciones.get(DataList.getIdDepto(lstUsers.get(i).getIdUser()), new Departamento());
                lst.setDepto(depto);
                
                Rol rol = new Rol();
                rol = Operaciones.get(lstUsers.get(i).getIdRole(), new Rol());
                
                lst.setRol(rol);
                lstEmpleado.add(lst);
            }
            
            printUsuariosJsonByFilter.Render(lstEmpleado, response);
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

    private ArrayList<listarEmpleado> UsersByFilters(int idRol, int idDepto, int idcase) {
        ArrayList<listarEmpleado> lstUsers = new ArrayList<>();
        String query = "select \n"
                + "a.iduser \n"
                + "from users a, deptobyusers b, roles c\n"
                + "where \n"
                + "a.iduser = b.iduser and \n"
                + "a.idrole = c.idrol and\n"
                + "b.iddepto=? and a.idrole=? ";

        String query2 = "select \n"
                + "a.iduser \n"
                + "from users a, deptobyusers b, roles c\n"
                + "where \n"
                + "a.iduser = b.iduser and \n"
                + "a.idrole = c.idrol and\n"
                + "b.iddepto=" + idDepto + " and a.idrole=3; ";
        List<Object> params = new ArrayList<>();
        params.add(idDepto);
        params.add(idRol);

        try {
            ConexionPool conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            String[][] array = null;
            if (idcase == 1) {
                array = Operaciones.consultar(query, params);
            } else {
                array = Operaciones.consultar(query2, null);
            }

            if (array == null) {
                lstUsers = null;
            } else {
                for (int i = 0; i < array[0].length; i++) {
                    listarEmpleado usertmp = new listarEmpleado();

                    usertmp.setUsuario(Operaciones.get(Integer.parseInt(array[0][i]), new Usuario()));
                    usertmp.setDepto(new Departamento());
                    usertmp.setRol(new Rol());
                    lstUsers.add(usertmp);
                }
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

        return lstUsers;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
