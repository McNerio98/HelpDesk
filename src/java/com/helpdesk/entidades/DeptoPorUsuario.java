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
@Entity(table="DEPTOBYUSERS")
public class DeptoPorUsuario {
    @PrimaryKey
    private int idDepto;
    @PrimaryKey
    private int idUser;

    public DeptoPorUsuario() {
    }

    public DeptoPorUsuario(int idDepto, int idUser) {
        this.idDepto = idDepto;
        this.idUser = idUser;
    }

    public int getIdDepto() {
        return idDepto;
    }

    public void setIdDepto(int idDepto) {
        this.idDepto = idDepto;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
    
    
}
