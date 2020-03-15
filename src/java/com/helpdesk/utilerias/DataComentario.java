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
public class DataComentario {

    private String titular;
    private String fecha;
    private String mensaje;
    private int rol;
    private String rolName;

    public DataComentario() {
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
        switch (rol) {
            case Enums.ROL.CONTADOR_REQ:
                this.rolName = "Contador";
                break;

            case Enums.ROL.GERENTE_REQ:
                this.rolName = "Gerente";
                break;

            case Enums.ROL.LIDER_REQ:
                this.rolName = "Lider";
                break;

            case Enums.ROL.RECEPTOR_REQ:
                this.rolName = "Requisitor";
                break;
            default:
                this.rolName = "NONE";
                break;
        }
    }

    public String getRolName() {
        return rolName;
    }

    public void setRolName(String rolName) {
        this.rolName = rolName;
    }

}
