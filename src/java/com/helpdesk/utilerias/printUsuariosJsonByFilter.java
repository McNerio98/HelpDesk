/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.utilerias;

import com.helpdesk.entidades.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author cnk
 */
public class printUsuariosJsonByFilter {

    public static void Render(ArrayList<listarEmpleado> list, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.println("[");
        for (int i = 0; i < list.size(); i++) {
            if (i == (list.size() - 1)) {

                out.println("{");
                out.print("\"id\"" + ":" + "\"" + list.get(i).getUsuario().getIdUser() + "\"" + ",");

                out.print("\"name\"" + ":" + "\"" + list.get(i).getUsuario().getFirsName() + "\"" + ",");

                out.println("\"lastname\"" + ":" + "\"" + list.get(i).getUsuario().getLastName() + "\"" + ",");

                out.println("\"email\"" + ":" + "\"" + list.get(i).getUsuario().getEmail() + "\"" + ",");

                out.println("\"contacto\"" + ":" + "\"" + list.get(i).getUsuario().getTelephone() + "\"" + ",");
                
                out.println("\"departamento\"" + ":" + "\"" + list.get(i).getDepto().getDeptoName() + "\"" + ",");
                
                out.println("\"rol\"" + ":" + "\"" + list.get(i).getRol().getRoleName() + "\"");

                out.println("}");
            } else {
                out.println("{");

                out.print("\"id\"" + ":" + "\"" + list.get(i).getUsuario().getIdUser() + "\"" + ",");

                out.print("\"name\"" + ":" + "\"" + list.get(i).getUsuario().getFirsName() + "\"" + ",");

                out.println("\"lastname\"" + ":" + "\"" + list.get(i).getUsuario().getLastName() + "\"" + ",");

                out.println("\"email\"" + ":" + "\"" + list.get(i).getUsuario().getEmail() + "\"" + ",");

                out.println("\"contacto\"" + ":" + "\"" + list.get(i).getUsuario().getTelephone() + "\"" + ",");
                
                out.println("\"departamento\"" + ":" + "\"" + list.get(i).getDepto().getDeptoName() + "\"" + ",");
                
                out.println("\"rol\"" + ":" + "\"" + list.get(i).getRol().getRoleName() + "\"");

                out.println("},");
            }
        }

        out.println("]");
    }

}
