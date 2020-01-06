/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.utilerias;

/**
 *
 * @author ZEUS
 */
public class DataGestion {
    private String type;
    private String title;
    private String description;
    private String fecha;
    private String Attach;
    private String costo;

    public DataGestion() {
    }

    public String getType() {
        return type;
    }

    public void setType(int type) {
        String tip = "";
        switch(type){
            case 1:
                tip = "Correo";
                break;
            case 2:
                tip = "Solicitud";
                break;
            case 3:
                tip = "Procedimiento";
            break;
        
        }
        this.type = tip;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getAttach() {
        return Attach;
    }

    public void setAttach(String Attach) {
        this.Attach = Attach;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }
    
    
}
