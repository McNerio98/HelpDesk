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
@Entity(table="CLASSIFICATIONS")
public class Clasificacion {
    @PrimaryKey
    private int idClassification;
    @NotNull
    private String classification;
    private String description;

    public Clasificacion() {
    }

    public Clasificacion(int idClassification, String classification, String description) {
        this.idClassification = idClassification;
        this.classification = classification;
        this.description = description;
    }

    public int getIdClassification() {
        return idClassification;
    }

    public void setIdClassification(int idClassification) {
        this.idClassification = idClassification;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
