/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.entidades;

import com.helpdesk.anotaciones.AutoIncrement;
import com.helpdesk.anotaciones.Entity;
import com.helpdesk.anotaciones.NotNull;
import com.helpdesk.anotaciones.PrimaryKey;

/**
 *
 * @author ALEX
 */
@Entity(table = "USUARIOREQBYEMPRESAS")
public class UsuarioReqByEmpresa {
    @PrimaryKey
    @AutoIncrement
    private int idure;
    @NotNull
    private int idUsuario;
    @NotNull
    private int idEmpresa;
    
    public UsuarioReqByEmpresa(){
        
    }

    public int getIdure() {
        return idure;
    }

    public void setIdure(int idure) {
        this.idure = idure;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
    

}
