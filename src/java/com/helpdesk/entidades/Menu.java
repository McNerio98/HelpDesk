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

@Entity(table="MENUS")
public class Menu {
    @PrimaryKey
    @AutoIncrement
    private int idMenu;
    @NotNull
    private String menu;
    @NotNull
    private String controller;
    private int idParent;

    public Menu() {
    }

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public int getIdParent() {
        return idParent;
    }

    public void setIdParent(int idParent) {
        this.idParent = idParent;
    }
    
    
}
