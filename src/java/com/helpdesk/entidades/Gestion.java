/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.entidades;

import com.helpdesk.anotaciones.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *
 * @author ZEUS
 */
@Entity(table="MANAGEMENT")
public class Gestion {
    @PrimaryKey
    @AutoIncrement
    private int idManagement;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private Timestamp correctionDay;
    @NotNull
    private String type;
    private String attach;
    private BigDecimal cost;
    @NotNull
    private int idIBR;

    public Gestion() {
    }

    public Gestion(int idManagement, String title, String description, Timestamp correctionDay, String type, String attach, BigDecimal cost, int idIBR) {
        this.idManagement = idManagement;
        this.title = title;
        this.description = description;
        this.correctionDay = correctionDay;
        this.type = type;
        this.attach = attach;
        this.cost = cost;
        this.idIBR = idIBR;
    }

    public int getIdManagement() {
        return idManagement;
    }

    public void setIdManagement(int idManagement) {
        this.idManagement = idManagement;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCorrectionDay() {
        return correctionDay;
    }

    public void setCorrectionDay(Timestamp correctionDay) {
        this.correctionDay = correctionDay;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public int getIdIBR() {
        return idIBR;
    }

    public void setIdIBR(int idIBR) {
        this.idIBR = idIBR;
    }
    
    
}
