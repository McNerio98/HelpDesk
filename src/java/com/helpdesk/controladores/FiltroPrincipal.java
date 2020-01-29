/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.controladores;

import com.helpdesk.entidades.Menu;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ZEUS
 */
public class FiltroPrincipal implements Filter {

    private static final boolean debug = true;

    private FilterConfig filterConfig = null;

    public FiltroPrincipal() {
    }

    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        ///BORRAR CACHE 
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        ///OMITIR ARCHIVOS DE ESOS TIPOS
        String path = request.getRequestURI();
        if (path.endsWith(".css") || path.endsWith(".min.js") || path.endsWith(".map") || path.endsWith(".js") || path.endsWith(".jpg") || path.endsWith(".png") || path.endsWith(".woff2") || path.endsWith(".ttf")) {
            chain.doFilter(request, response);
            return;
        }

        if (request.getSession().getAttribute("Usuario") != null || request.getRequestURI().equals(request.getContextPath() + "/Login")) {
            boolean encontrado = true;
            String url = request.getRequestURI().toString();
            String context = request.getContextPath();

            String pathPrincipal = request.getContextPath() + "/Principal";

            if (request.getParameter("accion") == null && !url.equals(pathPrincipal) && !url.equals(context + "/Login") && !url.equals(context + "/Informacion")) {
                List<Menu> permisos = (List<Menu>) request.getSession().getAttribute("MenuPrincipal");
                encontrado = false;
                int cont = 0;
                
                while (cont < permisos.size() && !encontrado) {
                    String menu = context + permisos.get(cont).getController();
                    if (url.equals(menu)) {
                        encontrado = true;
                    }
                    cont++;
                }

            }

            if (encontrado) {
                chain.doFilter(request, response);
            } else {
                response.sendRedirect("Principal");
            }

        } else {
            response.sendRedirect("Login");
        }

    }

    public void destroy() {
    }

    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("FiltroPrincipal:Initializing filter");
            }
        }
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
