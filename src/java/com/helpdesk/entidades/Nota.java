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
@Entity(table="NOTES")
public class Nota {
    @PrimaryKey
    @AutoIncrement
    private int idNota;
    @NotNull
    private String description;
    @NotNull
    private int notetype;
    @NotNull
    private int idHolder;
    @NotNull
    private int idIncidence;

    public Nota() {
    }

    public Nota(int idNota, String description, int notetype, int idHolder, int idIncidence) {
        this.idNota = idNota;
        this.description = description;
        this.notetype = notetype;
        this.idHolder = idHolder;
        this.idIncidence = idIncidence;
    }

    public int getIdNota() {
        return idNota;
    }

    public void setIdNota(int idNota) {
        this.idNota = idNota;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNotetype() {
        return notetype;
    }

    public void setNotetype(int notetype) {
        this.notetype = notetype;
    }

    public int getIdHolder() {
        return idHolder;
    }

    public void setIdHolder(int idHolder) {
        this.idHolder = idHolder;
    }

    public int getIdIncidence() {
        return idIncidence;
    }

    public void setIdIncidence(int idIncidence) {
        this.idIncidence = idIncidence;
    }
       
}
