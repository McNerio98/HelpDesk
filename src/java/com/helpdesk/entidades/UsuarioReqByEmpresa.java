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
@Entity(table = "contadorByEmpresas")
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
    /**
     * @return the idcbe
     */
    public int getIdure() {
        return idure;
    }

    /**
     * @param idcbe the idcbe to set
     */
    public void setIdcbe(int idcbe) {
        this.idure = idcbe;
    }

    /**
     * @return the idContador
     */
    public int getIdContador() {
        return idUsuario;
    }

    /**
     * @param idContador the idContador to set
     */
    public void setIdContador(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * @return the idEmpresa
     */
    public int getIdEmpresa() {
        return idEmpresa;
    }

    /**
     * @param idEmpresa the idEmpresa to set
     */
    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
    
}
