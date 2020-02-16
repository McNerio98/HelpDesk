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
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *
 * @author ALEX
 */
@Entity(table = "RequisicionesPagos")
public class RequisicionPago {

    /**
     * @return the Total
     */
    public BigDecimal getTotal() {
        return Total;
    }

    /**
     * @param Total the Total to set
     */
    public void setTotal(BigDecimal Total) {
        this.Total = Total;
    }

    @PrimaryKey
    @AutoIncrement
    private int idRequisicion;
    @NotNull
    private int idCreador;
    @NotNull
    private int idAutorizador;
    @NotNull
    private int idContador;
    @NotNull
    private Timestamp Fecha;
    @NotNull
    private BigDecimal Total;
    @NotNull
    private int estado;
    @NotNull
    private int idEmpresa;
    @NotNull
    private int idDepto;

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

    /**
     * @return the idCreador
     */
    public int getIdCreador() {
        return idCreador;
    }

    /**
     * @param idCreador the idCreador to set
     */
    public void setIdCreador(int idCreador) {
        this.idCreador = idCreador;
    }

    /**
     * @return the idAutorizador
     */
    public int getIdAutorizador() {
        return idAutorizador;
    }

    /**
     * @param idAutorizador the idAutorizador to set
     */
    public void setIdAutorizador(int idAutorizador) {
        this.idAutorizador = idAutorizador;
    }

    /**
     * @return the idContador
     */
    public int getIdContador() {
        return idContador;
    }

    /**
     * @param idContador the idContador to set
     */
    public void setIdContador(int idContador) {
        this.idContador = idContador;
    }

    /**
     * @return the fecha
     */
    public Timestamp getFecha() {
        return Fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Timestamp fecha) {
        this.Fecha = fecha;
    }

    /**
     * @return the estado
     */
    public int getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(int estado) {
        this.estado = estado;
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
