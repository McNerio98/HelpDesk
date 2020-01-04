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
@Entity(table="MANAGEMENTS")
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
    private int typemanagement;
    private String attachfile;
    private BigDecimal costmsg;
    @NotNull
    private int idIBR;

    public Gestion() {
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

    public int getTypemanagement() {
        return typemanagement;
    }

    public void setTypemanagement(int typemanagement) {
        this.typemanagement = typemanagement;
    }

    public String getAttachfile() {
        return attachfile;
    }

    public void setAttachfile(String attachfile) {
        this.attachfile = attachfile;
    }

    public BigDecimal getCostmsg() {
        return costmsg;
    }

    public void setCostmsg(BigDecimal costmsg) {
        this.costmsg = costmsg;
    }

    public int getIdIBR() {
        return idIBR;
    }

    public void setIdIBR(int idIBR) {
        this.idIBR = idIBR;
    }

    
}
