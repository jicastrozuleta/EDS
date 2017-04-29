/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.servicentroguerrero.modelos;

import java.io.Serializable;

/**
 *
 * @author JICZ
 */
public class Rol implements Serializable{
       /**
     * identificador unico del tipo de combustible que contiene el cilindro
     */
    private int idRol;
    
    /**
     * Descripcion del rol
     */
    private String rol;

    
    /**
     * Constructor de clase.
     * @param idRol
     * @param rol 
     */
    public Rol(int idRol, String rol) {
        this.idRol = idRol;
        this.rol = rol;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
