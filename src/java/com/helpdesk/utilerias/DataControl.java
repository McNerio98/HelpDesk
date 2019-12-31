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
public class DataControl {
    private int idIBR;
    private String receptor;
    private String status;
    private String inicioPrev;
    private String inicioReal;
    private String finPrev;
    private String finReal;

    public DataControl() {
    }

    public int getIdIBR() {
        return idIBR;
    }

    public void setIdIBR(int idIBR) {
        this.idIBR = idIBR;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(int status) {
        String estado = "";
        switch(status){
            case 1://Solicitud 
                estado = "En Solicitud";
                break;
            case 2: //Asignada 
                estado = "Asignada";
                break;
            case 3://Aceptada
                estado = "En Ejecucion";
                break;
            case 4:
                estado = "Finalizada";
                break;
            case 5: 
                estado = "Rechazada";
                break;
        }
        this.status = estado;
    }

    public String getInicioPrev() {
        return inicioPrev;
    }

    public void setInicioPrev(String inicioPrev) {
        this.inicioPrev = inicioPrev;
    }

    public String getInicioReal() {
        return inicioReal;
    }

    public void setInicioReal(String inicioReal) {
        this.inicioReal = inicioReal;
    }

    public String getFinPrev() {
        return finPrev;
    }

    public void setFinPrev(String finPrev) {
        this.finPrev = finPrev;
    }

    public String getFinReal() {
        return finReal;
    }

    public void setFinReal(String finReal) {
        this.finReal = finReal;
    }
    
    
    
    
}
