/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.entidades;

import com.helpdesk.anotaciones.AutoIncrement;
import com.helpdesk.anotaciones.Entity;
import com.helpdesk.anotaciones.NotNull;
import com.helpdesk.anotaciones.PrimaryKey;

/**
 *
 * @author ALEX
 */
@Entity(table = "Empresas")
public class Empresa {
    
    @PrimaryKey
    @AutoIncrement
    private int idEmpresa;
    @NotNull
    private String Nombre;
    @NotNull
    private String Direccion;
    
    public Empresa(){
        
    }

    /**
     * @return the idEmpresa
     */
    public int getIdEmpresa() {
        return idEmpresa;
    }

    /**
     * @param idEmpresa the idEmpresa to set
     */
    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return Nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return Direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.Direccion = direccion;
    }
    
    
}
