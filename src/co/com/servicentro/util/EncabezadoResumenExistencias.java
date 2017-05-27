/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.servicentro.util;

import co.com.servicentroguerrero.modelos.Calibraciones;
import java.io.Serializable;

/**
 * Empaqueta la informacion de encabzado de un resumen de existencias
 * @author JICZ
 */
public class EncabezadoResumenExistencias implements Serializable {

    /**
     * Atributo que representa la fecha de la ultima calibracion del surtidor.
     */
    private Calibraciones calibracion;

    /**
     * Cantidad en galones existentes en cilindro.
     */
    private double enExistencias;

    /**
     * galones vendidos de combustible
     */
    private double vendido;

    /**
     * galones comprados de combustible;
     */
    private double comprado;

    /**
     * surtidor al cual se le realiza encabzado de existencias
     */
    private int idSurtidor;

    /**
     * constructor sin parametros.
     */
    public EncabezadoResumenExistencias() {
    }

    public double getEnExistencias() {
        return enExistencias;
    }

    public void setEnExistencias(double enExistencias) {
        this.enExistencias = enExistencias;
    }

    public double getVendido() {
        return vendido;
    }

    public void setVendido(double vendido) {
        this.vendido = vendido;
    }

    public double getComprado() {
        return comprado;
    }

    public void setComprado(double comprado) {
        this.comprado = comprado;
    }

    public int getIdSurtidor() {
        return idSurtidor;
    }

    public void setIdSurtidor(int idSurtidor) {
        this.idSurtidor = idSurtidor;
    }

    public Calibraciones getCalibracion() {
        return calibracion;
    }

    public void setCalibracion(Calibraciones calibracion) {
        this.calibracion = calibracion;
    }
    
    
}
