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
public class LiquidacionDispensador implements Serializable {

    /**
     * Numero de liquidacion unico
     */
    private long numeroLiquidacion;

    /**
     * Identificador del dispensador liquidado
     */
    private int idDispensador;

    /**
     * Identificador del precio de combustible que usa el dispensador
     */
    private int idPrecio;

    /**
     * Identificador del empleado que entrega la liquidacion
     */
    private int idEmpleadoLiquidado;

    /**
     * Numero de lectura del dispensador
     */
    private double numeroEntregado;

    /**
     * Numero de lectura de dispensador anterior
     */
    private double numeroRecibido;

    /**
     * gelones de combustible vendidos
     */
    private double galones;

    /**
     * galones calculados por sistema
     */
    private double galonesCalculados;

    /**
     * Dinero entregado por el empleado
     */
    private double dineroEntregado;

    /**
     * dinero calculado por el sistema
     */
    private double dineroCalculado;

    /**
     * diferencia de dinero ente entregado y calcuado por el sistema.
     */
    private double diferenciaDinero;

    /**
     * Identificador del surtidor al que pertenece el dispensador
     */
    private int idSurtidor;

    /**
     * Codigo para identificar el surtidor, nombre del surtidor y combustible
     * que entrega.
     */
    private String codigoIdentificador;

    /**
     * Constructor recomendado para crear el objeto
     *
     * @param numeroLiquidacion
     * @param idDispensador
     * @param idPrecio
     * @param idEmpleadoLiquidado
     * @param numeroEntregado
     * @param numeroRecibido
     * @param galones
     * @param galonesCalculados
     * @param dineroEntregado
     * @param dineroCalculado
     * @param diferenciaDinero
     * @param idSurtidor
     * @param codigoIdentificador
     */
    public LiquidacionDispensador(long numeroLiquidacion, int idDispensador, int idPrecio, int idEmpleadoLiquidado, double numeroEntregado, double numeroRecibido, double galones, double galonesCalculados, double dineroEntregado, double dineroCalculado, double diferenciaDinero, int idSurtidor, String codigoIdentificador) {
        this.numeroLiquidacion = numeroLiquidacion;
        this.idDispensador = idDispensador;
        this.idPrecio = idPrecio;
        this.idEmpleadoLiquidado = idEmpleadoLiquidado;
        this.numeroEntregado = numeroEntregado;
        this.numeroRecibido = numeroRecibido;
        this.galones = galones;
        this.galonesCalculados = galonesCalculados;
        this.dineroEntregado = dineroEntregado;
        this.dineroCalculado = dineroCalculado;
        this.diferenciaDinero = diferenciaDinero;
        this.idSurtidor = idSurtidor;
        this.codigoIdentificador = codigoIdentificador;
    }

    public long getNumeroLiquidacion() {
        return numeroLiquidacion;
    }

    public void setNumeroLiquidacion(long numeroLiquidacion) {
        this.numeroLiquidacion = numeroLiquidacion;
    }

    public int getIdDispensador() {
        return idDispensador;
    }

    public void setIdDispensador(int idDispensador) {
        this.idDispensador = idDispensador;
    }

    public int getIdPrecio() {
        return idPrecio;
    }

    public void setIdPrecio(int idPrecio) {
        this.idPrecio = idPrecio;
    }

    public int getIdEmpleadoLiquidado() {
        return idEmpleadoLiquidado;
    }

    public void setIdEmpleadoLiquidado(int idEmpleadoLiquidado) {
        this.idEmpleadoLiquidado = idEmpleadoLiquidado;
    }

    public double getNumeroEntregado() {
        return numeroEntregado;
    }

    public void setNumeroEntregado(double numeroEntregado) {
        this.numeroEntregado = numeroEntregado;
    }

    public double getNumeroRecibido() {
        return numeroRecibido;
    }

    public void setNumeroRecibido(double numeroRecibido) {
        this.numeroRecibido = numeroRecibido;
    }

    public double getGalones() {
        return galones;
    }

    public void setGalones(double galones) {
        this.galones = galones;
    }

    public double getGalonesCalculados() {
        return galonesCalculados;
    }

    public void setGalonesCalculados(double galonesCalculados) {
        this.galonesCalculados = galonesCalculados;
    }

    public double getDineroEntregado() {
        return dineroEntregado;
    }

    public void setDineroEntregado(double dineroEntregado) {
        this.dineroEntregado = dineroEntregado;
    }

    public double getDineroCalculado() {
        return dineroCalculado;
    }

    public void setDineroCalculado(double dineroCalculado) {
        this.dineroCalculado = dineroCalculado;
    }

    public double getDiferenciaDinero() {
        return diferenciaDinero;
    }

    public void setDiferenciaDinero(double diferenciaDinero) {
        this.diferenciaDinero = diferenciaDinero;
    }

    public int getIdSurtidor() {
        return idSurtidor;
    }

    public void setIdSurtidor(int idSurtidor) {
        this.idSurtidor = idSurtidor;
    }

    public String getCodigoIdentificador() {
        return codigoIdentificador;
    }

    public void setCodigoIdentificador(String codigoIdentificador) {
        this.codigoIdentificador = codigoIdentificador;
    }
}
