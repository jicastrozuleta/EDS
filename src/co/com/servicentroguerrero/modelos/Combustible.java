/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.servicentroguerrero.modelos;

import java.io.Serializable;

/**
 *
 * @author JICZ4
 */
public class Combustible implements Serializable{
    
    /**
     * Constante para referenciar tipo combustible gasolina corriente
     */
    public static final String CORRIENTE = "CORRIENTE";
    
    /**
     * COnstante para referencias tipo Combustible acpm
     */
    public static final String ACPM = "ACPM";
    
    /**
     * COnstante para referencias tipo Combustible gasolina Extra.
     */
    public static final String EXTRA = "EXTRA";
    
    
    /**
     * COnstante para referencias tipo Combustible Diesel.
     */
    public static final String DIESEL = "DIESEL";
    
    
    /**
     * Identificador unico de combustible
     */
    private int idCombustible;
    
    /**
     * Descripcion o nombre del combustible.
     */
    private String combustible;
    
    /**
     * Identificador del precio vigente del combustible.
     */
    private int idPrecio;
    
    /**
     * Precio vigente del combustible.
     */
    private double precioVigente;
    
    /**
     * fecha de entrada en vigencia del actual precio de combustible.
     */
    private String fechaEntradaVigencia;
    
    /**
     * Fecha en que salio de vigencia un precio anterior del combustible.
     */
    private String fechaCierreVigencia;
    
     /**
     * Contructor recomendado para crear el objeto
     * @param idCombustible
     * @param combustible 
     * @param idPrecio 
     * @param precioVigente 
     * @param fechaEntradaVigencia 
     * @param fechaCierreVigencia 
     */
    public Combustible(int idCombustible, String combustible, int idPrecio, double precioVigente, String fechaEntradaVigencia, String fechaCierreVigencia) {
        this.idCombustible = idCombustible;
        this.combustible = combustible;
        this.idPrecio = idPrecio;
        this.precioVigente = precioVigente;
        this.fechaEntradaVigencia = fechaEntradaVigencia;
        this.fechaCierreVigencia = fechaCierreVigencia;
    }
    
    

    public int getIdCombustible() {
        return idCombustible;
    }

    public void setIdCombustible(int idCombustible) {
        this.idCombustible = idCombustible;
    }

    public String getCombustible() {
        return combustible;
    }

    public void setCombustible(String combustible) {
        this.combustible = combustible;
    }   

    public int getIdPrecio() {
        return idPrecio;
    }

    public void setIdPrecio(int idPrecio) {
        this.idPrecio = idPrecio;
    }

    public double getPrecioVigente() {
        return precioVigente;
    }

    public void setPrecioVigente(double precioVigente) {
        this.precioVigente = precioVigente;
    }

    public String getFechaEntradaVigencia() {
        return fechaEntradaVigencia;
    }

    public void setFechaEntradaVigencia(String fechaEntradaVigencia) {
        this.fechaEntradaVigencia = fechaEntradaVigencia;
    }

    public String getFechaCierreVigencia() {
        return fechaCierreVigencia;
    }

    public void setFechaCierreVigencia(String fechaCierreVigencia) {
        this.fechaCierreVigencia = fechaCierreVigencia;
    }
}