/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.utilerias;

import com.helpdesk.entidades.Incidencia;
import com.helpdesk.entidades.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author cnk
 */
public class printIncidencesJson {
    public static void Render(ArrayList<Incidencia> list, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/plain");
        out.println("[");
        for (int i = 0; i < list.size(); i++) {
            if (i == (list.size() - 1)) {

                out.println("{");
                
                out.print("\"id\"" + ":" + "\"" + list.get(i).getIdIncidence()+ "\"" + ",");

                out.print("\"name\"" + ":" + "\"" + list.get(i).getTitle() + "\"");


                out.println("}");
            } else {
                out.println("{");

                out.print("\"id\"" + ":" + "\"" + list.get(i).getIdIncidence()+ "\"" + ",");

                out.print("\"name\"" + ":" + "\"" + list.get(i).getTitle() + "\"");

                out.println("},");
            }
        }

        out.println("]");
    }
}