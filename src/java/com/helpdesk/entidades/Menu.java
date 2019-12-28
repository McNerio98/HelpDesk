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
    private String description;
    @NotNull
    private String controller;
    private int idParent;

    public Menu() {
    }

    public Menu(int idMenu, String menu, String description, String controller, int idParent) {
        this.idMenu = idMenu;
        this.menu = menu;
        this.description = description;
        this.controller = controller;
        this.idParent = idParent;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
