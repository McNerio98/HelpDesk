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
@Entity(table="PERMISSIONS")
public class Permiso {
    @PrimaryKey
    @AutoIncrement
    private int idPermissions;
    @NotNull
    private int idMenu;
    @NotNull
    private int role;

    public Permiso() {
    }

    public Permiso(int idPermissions, int idMenu, int role) {
        this.idPermissions = idPermissions;
        this.idMenu = idMenu;
        this.role = role;
    }

    public int getIdPermissions() {
        return idPermissions;
    }

    public void setIdPermissions(int idPermissions) {
        this.idPermissions = idPermissions;
    }

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
    
        
}
