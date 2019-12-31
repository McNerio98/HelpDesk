/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.utilerias;

import java.math.BigDecimal;

/**
 *
 * @author ZEUS
 */
public class DataIncidencia {
    private String departamento;
    private String titulo;
    private String descripcion;
    private String status;
    private String fechaCreacion;
    private String prioridad;
    private String creador;
    private String clasificacion;
    private String costoTotal;
    private int idDeptoTecnico;
    private int idTecnico;

    public DataIncidencia() {
    }


    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String title) {
        this.titulo = title;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int pri) {
        String prioridad = "";
        switch(pri){
            case 1:
                prioridad = "BAJA";
                break;
            case 2:
                prioridad = "MEDIA";
                break;
            case 3: 
                prioridad = "ALTA";
                break;
        }
        
        this.prioridad = prioridad;
    }

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(String costoTotal) {
        this.costoTotal = costoTotal;
    }

    public int getIdDeptoTecnico() {
        return idDeptoTecnico;
    }

    public void setIdDeptoTecnico(int idDepto) {
        this.idDeptoTecnico = idDepto;
    }

    public int getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(int idTecnico) {
        this.idTecnico = idTecnico;
    }
    
    
}
