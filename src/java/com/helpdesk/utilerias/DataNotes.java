/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.utilerias;

/**
 *
 * @author ZEUS
 */
public class DataNotes {
    private String fullname;
    private String roleName;
    private String description;
    private String noteType;

    public DataNotes() {
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNoteType() {
        return noteType;
    }

    public void setNoteType(int noteType) {
        String notetp = "";
        switch(noteType){
            case 1:
                notetp = "Observacion";
                break;
            case 2:
                notetp = "Rechazo";
                break;
            case 3:
                notetp = "Denegacion";
                break;
        }
        
        this.noteType = notetp;
    }
    
    
}
