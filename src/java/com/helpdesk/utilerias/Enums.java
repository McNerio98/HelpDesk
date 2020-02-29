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
public class Enums {

    public static final class ESTADO {

        public static final int SOLICITADA = 1;
        public static final int ASIGNADA = 2;
        public static final int ACEPTADA = 3;
        public static final int FINALIZADA = 4;
        public static final int RECHAZADA = 5;
        public static final int DENEGADA = 6;
    }

    public static final class NOTA {

        public static final int OBSERVACION = 1;
        public static final int RECHAZO = 2;
        public static final int DENEGACION = 3;
    }

    public static final class GESTION {

        public static final int CORREO = 1;
        public static final int SOLICITUD = 2;
        public static final int PROCEDIMIENTO = 3;
    }

    public static final class PRIORIDAD {

        public static final int BAJA = 1;
        public static final int MEDIA = 2;
        public static final int ALTA = 3;
    }
    
    public static final class ROL{
        // ROLES DE MODULO DE HELPDESK 
        public static final int GERENTE = 1;
        public static final int LIDER = 2;
        public static final int RECEPTOR = 3;
        public static final int EMPLEADO = 4;
        
        // ROLES DE MODULO DE REQUISICIONES
        public static final int GERENTE_REQ = 5;
        public static final int LIDER_REQ = 6;
        public static final int RECEPTOR_REQ = 7;
        public static final int EMPLEADO_REQ = 8;
        public static final int CONTADOR_REQ = 9;
    }
    
    public static final class ESTADO_REQ{
        public static final int SOLICITADA = 1;
        public static final int REVISION = 2;
        public static final int ACEPTADA = 3;
        public static final int RECHAZADA = 4;
        public static final int FINALIZADA = 5;
    }

}
