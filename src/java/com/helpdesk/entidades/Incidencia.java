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
    private Timestamp creationDay;
    @NotNull
    private Timestamp finalDate;
    @NotNull
    private BigDecimal totalCost;
    @NotNull
    private int priority;
    @NotNull
    private int idClassification;
    @NotNull
    private int idCreator;
    @NotNull
    private int idDepto;
    @NotNull
    private int status;
    @NotNull
    private int idreceptor;

    public Incidencia() {
    }

    public Incidencia(int idIncidence, String title, String description, Timestamp creationDay, Timestamp finalDate, BigDecimal totalCost, int priority, int idClassification, int idCreator, int idDepto) {
        this.idIncidence = idIncidence;
        this.title = title;
        this.description = description;
        this.creationDay = creationDay;
        this.finalDate = finalDate;
        this.totalCost = totalCost;
        this.priority = priority;
        this.idClassification = idClassification;
        this.idCreator = idCreator;
        this.idDepto = idDepto;
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

    public Timestamp getCreationDay() {
        return creationDay;
    }

    public void setCreationDay(Timestamp creationDay) {
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
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

    public int getIdDepto() {
        return idDepto;
    }

    public void setIdDepto(int idDepto) {
        this.idDepto = idDepto;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIdreceptor() {
        return idreceptor;
    }

    public void setIdreceptor(int idreceptor) {
        this.idreceptor = idreceptor;
    }

}


