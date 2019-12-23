/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.entidades;

import com.helpdesk.anotaciones.*;

/**
 *
 * @author ZEUS
 */
@Entity(table="ROLES")
public class Rol {
    @PrimaryKey
    @AutoIncrement
    private int idRol;
    @NotNull
    private String roleName;
    private String description;

    public Rol() {
    }

    public Rol(int idRol, String roleName, String description) {
        this.idRol = idRol;
        this.roleName = roleName;
        this.description = description;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
