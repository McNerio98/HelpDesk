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
@Entity(table="INCIDENCES")
public class Incidencia {
    @PrimaryKey
    @AutoIncrement
    private int idIncidence;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private Timestamp creationDay;
    @NotNull
    private Timestamp finalDate;
    @NotNull
    private BigDecimal totalCost;
    @NotNull
    private String priority;
    @NotNull
    private int idClassification;
    @NotNull
    private int idCreator;

    public Incidencia() {
    }

    public Incidencia(int idIncidence, String title, String description, Timestamp creationDay, Timestamp finalDate, BigDecimal totalCost, String priority, int idClassification, int idCreator) {
        this.idIncidence = idIncidence;
        this.title = title;
        this.description = description;
        this.creationDay = creationDay;
        this.finalDate = finalDate;
        this.totalCost = totalCost;
        this.priority = priority;
        this.idClassification = idClassification;
        this.idCreator = idCreator;
    }

    public int getIdIncidence() {
        return idIncidence;
    }

    public void setIdIncidence(int idIncidence) {
        this.idIncidence = idIncidence;
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

    public Timestamp getCreateIonDay() {
        return creationDay;
    }

    public void setCreateIonDay(Timestamp creationDay) {
        this.creationDay = creationDay;
    }

    public Timestamp getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Timestamp finalDate) {
        this.finalDate = finalDate;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getIdClassification() {
        return idClassification;
    }

    public void setIdClassification(int idClassification) {
        this.idClassification = idClassification;
    }

    public int getIdCreator() {
        return idCreator;
    }

    public void setIdCreator(int idCreator) {
        this.idCreator = idCreator;
    }
        
    
}


