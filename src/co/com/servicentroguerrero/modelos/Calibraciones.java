/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.servicentroguerrero.modelos;

import java.io.Serializable;

/**
 * Clase que empaqueta un objeto de tipo Calibraciones.
 * @author JICZ4
 */
public class Calibraciones implements Serializable{
    
    /**
     * Identificador unico de la calibracion
     */
    private long idCalibracion;
    
    /**
     * identificador del surtidor calibrado
     */
    private int idSurtidor;
    
    /**
     * galones de combustible usados para la calibracion
     */
    private double galonesUsados;
    
    /**
     * fecha de la calibracion
     */
    private String fecha;
    
    /**
     * descripcion de la novedad registrada, si existe alguna novedad.
     */
    private String descripcion;

    
    /**
     * constructor recomendado para crear el objeto 
     * @param idCalibracion
     * @param idSurtidor
     * @param galonesUsados
     * @param fecha
     * @param descripcion 
     */
    public Calibraciones(long idCalibracion, int idSurtidor, double galonesUsados, String fecha, String descripcion) {
        this.idCalibracion = idCalibracion;
        this.idSurtidor = idSurtidor;
        this.galonesUsados = galonesUsados;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }

    public long getIdCalibracion() {
        return idCalibracion;
    }

    public void setIdCalibracion(long idCalibracion) {
        this.idCalibracion = idCalibracion;
    }

    public int getIdSurtidor() {
        return idSurtidor;
    }

    public void setIdSurtidor(int idSurtidor) {
        this.idSurtidor = idSurtidor;
    }

    public double getGalonesUsados() {
        return galonesUsados;
    }

    public void setGalonesUsados(double galonesUsados) {
        this.galonesUsados = galonesUsados;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }   
}
