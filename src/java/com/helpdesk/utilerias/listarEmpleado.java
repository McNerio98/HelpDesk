/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.utilerias;

import com.helpdesk.entidades.Departamento;
import com.helpdesk.entidades.Rol;
import com.helpdesk.entidades.Usuario;

/**
 *
 * @author cnk
 */
public class listarEmpleado {

    /**
     * @return the depto
     */
    public Departamento getDepto() {
        return depto;
    }

    /**
     * @param depto the depto to set
     */
    public void setDepto(Departamento depto) {
        this.depto = depto;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the rol
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * @param rol the rol to set
     */
    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    public listarEmpleado(){
        
    }
    private Departamento depto;
    private Usuario usuario;
    private Rol rol;
    
}
