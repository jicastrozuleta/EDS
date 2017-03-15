/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.servicentroguerrero.modelos;

import java.io.Serializable;

/**
 * Clase que reprsenta la liquidacion total de un dia.
 * @author JICZ4
 */
public class Liquidacion implements Serializable{
    
    /**
     * NUmero identificador unico de la transaccion de liquidacion
     */
    private long numeroLiquidacion;
    
    /**
     * Id del empleado que realizo la liquidacion.
     */
    private int idEmpleadoLiquidador;
    
    /**
     * Total de dinero de combustibles
     */
    private double totalCombustibles;
    
    /**
     * Total de dinero de aceites y demas elementos de venta.
     */
    private double totalAceites;
    
    /**
     * Total liquidacion, combustibles mas aceites
     */
    private double totalLiquidado;
    
    /**
     * Total de dinero que entrega el islero
     */
    private double dineroEntregado;
    
    /**
     * Diferencia respecto al total de ventas.
     */
    private double diferencia;
    
    /**
     * Fecha en que se realizo la liquidacion
     */
    private String fechaLiquidacion;

    
    /**
     * Constructo recomendado para crear la entidad.
     * @param numeroLiquidacion
     * @param idEmpleadoLiquidador
     * @param totalCombustibles
     * @param totalAceites
     * @param totalLiquidado
     * @param dineroEntregado
     * @param diferencia
     * @param fechaLiquidacion 
     */
    public Liquidacion(long numeroLiquidacion, int idEmpleadoLiquidador, double totalCombustibles, double totalAceites, double totalLiquidado, double dineroEntregado, double diferencia, String fechaLiquidacion) {
        this.numeroLiquidacion = numeroLiquidacion;
        this.idEmpleadoLiquidador = idEmpleadoLiquidador;
        this.totalCombustibles = totalCombustibles;
        this.totalAceites = totalAceites;
        this.totalLiquidado = totalLiquidado;
        this.dineroEntregado = dineroEntregado;
        this.diferencia = diferencia;
        this.fechaLiquidacion = fechaLiquidacion;
    }

    public long getNumeroLiquidacion() {
        return numeroLiquidacion;
    }

    public void setNumeroLiquidacion(long numeroLiquidacion) {
        this.numeroLiquidacion = numeroLiquidacion;
    }

    public int getIdEmpleadoLiquidador() {
        return idEmpleadoLiquidador;
    }

    public void setIdEmpleadoLiquidador(int idEmpleadoLiquidador) {
        this.idEmpleadoLiquidador = idEmpleadoLiquidador;
    }

    public double getTotalCombustibles() {
        return totalCombustibles;
    }

    public void setTotalCombustibles(double totalCombustibles) {
        this.totalCombustibles = totalCombustibles;
    }

    public double getTotalAceites() {
        return totalAceites;
    }

    public void setTotalAceites(double totalAceites) {
        this.totalAceites = totalAceites;
    }

    public double getTotalLiquidado() {
        return totalLiquidado;
    }

    public void setTotalLiquidado(double totalLiquidado) {
        this.totalLiquidado = totalLiquidado;
    }

    public double getDineroEntregado() {
        return dineroEntregado;
    }

    public void setDineroEntregado(double dineroEntregado) {
        this.dineroEntregado = dineroEntregado;
    }

    public double getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(double diferencia) {
        this.diferencia = diferencia;
    }

    public String getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    public void setFechaLiquidacion(String fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }   
}
