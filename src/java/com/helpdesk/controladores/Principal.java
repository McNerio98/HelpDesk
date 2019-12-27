/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.controladores;

import com.helpdesk.entidades.Menu;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ZEUS
 */
public class Principal extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if(accion == null){
            HttpSession s = request.getSession();
            List<Menu> permisosLst = (List<Menu>)s.getAttribute("Permisos");
            String op = request.getParameter("op");

            if(op!=null){
                List<Menu> PermisosAsignados = permisosLst.stream().filter(field -> field.getIdParent()==Integer.parseInt(op)).collect(Collectors.toList());
                request.setAttribute("PermisosAsignados",PermisosAsignados);
            }
            
            request.getRequestDispatcher("pnlPrincipal.jsp").forward(request, response);
        }else if(accion.equals("logout")){
            cerrarSesion(request, response);
        }

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
    
    private void cerrarSesion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession s = request.getSession();
        s.removeAttribute("Usuario");
        s.invalidate();
        response.sendRedirect("Login");
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
