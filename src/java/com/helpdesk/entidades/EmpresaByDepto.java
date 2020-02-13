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
@Entity(table = "EmpresasByDeptos")
public class EmpresaByDepto {

    @PrimaryKey
    @AutoIncrement
    private int idEBD;
    @NotNull
    private int idEmpresa;
    @NotNull
    private int idDepto;

    /**
     * @return the idEBD
     */
    public int getIdEBD() {
        return idEBD;
    }

    /**
     * @param idEBD the idEBD to set
     */
    public void setIdEBD(int idEBD) {
        this.idEBD = idEBD;
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

    /**
     * @return the idDepto
     */
    public int getIdDepto() {
        return idDepto;
    }

    /**
     * @param idDepto the idDepto to set
     */
    public void setIdDepto(int idDepto) {
        this.idDepto = idDepto;
    }

}
