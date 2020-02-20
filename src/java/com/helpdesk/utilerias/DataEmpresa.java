/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.utilerias;

import com.helpdesk.entidades.Departamento;
import com.helpdesk.entidades.Empresa;
import java.util.ArrayList;

/**
 *
 * @author ALEX
 */
public class DataEmpresa {
    private Empresa emp;
    private ArrayList<Departamento> listDepto;
    public DataEmpresa(){
        this.emp = new Empresa();
        this.listDepto = new ArrayList<>();
    }
    
    public void setEmpresa(Empresa e){
        this.emp = e;
    }
    
    public Empresa getEmpresa(){
        return this.emp;
    }
    
    public void setListDeptos(ArrayList<Departamento> list){
        this.listDepto = list;
    }
    public ArrayList<Departamento> getListDeptos(){
        return this.listDepto;
    }
    
}
