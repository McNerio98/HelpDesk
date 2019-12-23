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
@Entity(table="departments")
public class Departamento {
    @PrimaryKey
    @AutoIncrement
    private int idDepto;
    @NotNull
    private String deptoName;
    private String description;

    public Departamento() {
    }

    public Departamento(int idDepto, String deptoName, String description) {
        this.idDepto = idDepto;
        this.deptoName = deptoName;
        this.description = description;
    }

    public int getIdDepto() {
        return idDepto;
    }

    public void setIdDepto(int idDepto) {
        this.idDepto = idDepto;
    }

    public String getDeptoName() {
        return deptoName;
    }

    public void setDeptoName(String deptoName) {
        this.deptoName = deptoName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
