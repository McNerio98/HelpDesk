/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.entidades;

import com.helpdesk.anotaciones.*;
import java.sql.Timestamp;

/**
 *
 * @author ZEUS
 */
@Entity(table="INCIDENCEBYRECEPTOR")
public class IncidenciaPorEncargado {
    @PrimaryKey
    private int idIBR;
    private Timestamp startDate;
    private Timestamp finalDate;
    @NotNull
    private String status;
    @NotNull
    private int idreceptor;
    @NotNull
    private int idIncidence;

    public IncidenciaPorEncargado() {
    }

    public IncidenciaPorEncargado(int idIBR, Timestamp startDate, Timestamp finalDate, String status, int idreceptor, int idIncidence) {
        this.idIBR = idIBR;
        this.startDate = startDate;
        this.finalDate = finalDate;
        this.status = status;
        this.idreceptor = idreceptor;
        this.idIncidence = idIncidence;
    }

    public int getIdIBR() {
        return idIBR;
    }

    public void setIdIBR(int idIBR) {
        this.idIBR = idIBR;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Timestamp finalDate) {
        this.finalDate = finalDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIdreceptor() {
        return idreceptor;
    }

    public void setIdreceptor(int idreceptor) {
        this.idreceptor = idreceptor;
    }

    public int getIdIncidence() {
        return idIncidence;
    }

    public void setIdIncidence(int idIncidence) {
        this.idIncidence = idIncidence;
    }
    
    
}
