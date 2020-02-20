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
public class DataRequisicion {
    private String fecha;
    private String empresa;
    private String depto;
    private String solicitante;
    private String superior;
    private String contador;
    private String estado;
    private String numRegistros;
    private String montoTotal;
    private String prioridad;
    

    public DataRequisicion() {
        this.superior = "--No Asignado--";
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getDepto() {
        return depto;
    }

    public void setDepto(String depto) {
        this.depto = depto;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public String getSuperior() {
        return superior;
    }

    public void setSuperior(String superior) {
        if(superior == null){
            this.superior = "--No Asignado--";
        }else{
            this.superior = superior;    
        }
        
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(Integer es) {
        String NuevoEstado = "";
        switch(es){
            case 1: 
                NuevoEstado = "Solicitud";
                break;
            case 2:
                NuevoEstado = "Revision";
                break;
            case 3:
                NuevoEstado = "Aceptada";
                break;
            case 4:
                NuevoEstado = "Rechazada";
                break;
            case 5:
                NuevoEstado = "Finalizada";
                break;
        }
        
        this.estado = NuevoEstado;
    }

    public String getNumRegistros() {
        return numRegistros;
    }

    public void setNumRegistros(String numRegistros) {
        this.numRegistros = numRegistros;
    }

    public String getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(String montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int priori) {
        String newPrioridad = "";
        switch(priori){
            case 1:
                newPrioridad = "Baja";
                break;
            case 2:
                newPrioridad = "Media";
                break;
            case 3:
                newPrioridad = "Alta";
                break;
            default:
                newPrioridad = "Baja";
                break;                
        }
        this.prioridad = newPrioridad;
    }

    public String getContador() {
        return contador;
    }

    public void setContador(String contador) {
        this.contador = contador;
    }
    
}
