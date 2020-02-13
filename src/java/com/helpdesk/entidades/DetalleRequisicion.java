/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.entidades;

import com.helpdesk.anotaciones.Entity;
import com.helpdesk.anotaciones.NotNull;
import com.helpdesk.anotaciones.PrimaryKey;
import java.math.BigDecimal;

/**
 *
 * @author ALEX
 */
@Entity(table = "DetallesRequisiciones")
public class DetalleRequisicion {

    @PrimaryKey
    @NotNull
    private int idDetalle;
    @NotNull
    private int Descripcion;
    @NotNull
    private BigDecimal Monto;
    @NotNull
    private int idRequisicion;

    /**
     * @return the idDetalle
     */
    public int getIdDetalle() {
        return idDetalle;
    }

    /**
     * @param idDetalle the idDetalle to set
     */
    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    /**
     * @return the Descripcion
     */
    public int getDescripcion() {
        return Descripcion;
    }

    /**
     * @param Descripcion the Descripcion to set
     */
    public void setDescripcion(int Descripcion) {
        this.Descripcion = Descripcion;
    }

    /**
     * @return the Monto
     */
    public BigDecimal getMonto() {
        return Monto;
    }

    /**
     * @param Monto the Monto to set
     */
    public void setMonto(BigDecimal Monto) {
        this.Monto = Monto;
    }

    /**
     * @return the idRequisicion
     */
    public int getIdRequisicion() {
        return idRequisicion;
    }

    /**
     * @param idRequisicion the idRequisicion to set
     */
    public void setIdRequisicion(int idRequisicion) {
        this.idRequisicion = idRequisicion;
    }

}
