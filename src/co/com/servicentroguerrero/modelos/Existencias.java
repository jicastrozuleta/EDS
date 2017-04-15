/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.servicentroguerrero.modelos;

import java.io.Serializable;

/**
 * Clase que conforma el modelo para conservar la informacion de existencias de
 * combustibles
 *
 * @author JICZ
 */
public class Existencias implements Serializable {

    /**
     * Medida calculada por medida de regla mojada.
     */
    private final double medidaGalonesInicial;

    /**
     * Galones de combustible comprados del dia.
     */
    private double galonesComprados;

    /**
     * galones vendidos del dia
     */
    private double galonesVendidos;

    /**
     * galones existentes, medidaGalonesInicial + galonesComprados -
     * galonesVendidos
     */
    private double galonesExistentes;

    /**
     * fecha de la medida inicial
     */
    private String fechaMedida;


    /**
     * Surtidor del cual es tomada la medida de existencia
     */
    private final int idSurtidor;

    /**
     * Cilindro del cual es tomada la medida de existencia
     */
    private final int idCilindro;

    /**
     * Constructor del objeto que se crea con inicialmente con la medida de
     * regla.
     *
     * @param medidaGalonesInicial
     * @param idSurtidor
     * @param idCilindro
     */
    public Existencias(double medidaGalonesInicial, int idSurtidor, int idCilindro) {
        this.medidaGalonesInicial = medidaGalonesInicial;
        this.idSurtidor = idSurtidor;
        this.idCilindro = idCilindro;
    }

    public double getMedidaGalonesInicial() {
        return medidaGalonesInicial;
    }

    public double getGalonesComprados() {
        return galonesComprados;
    }

    public void setGalonesComprados(double galonesComprados) {
        this.galonesComprados = galonesComprados;
    }

    public double getGalonesVendidos() {
        return galonesVendidos;
    }

    public void setGalonesVendidos(double galonesVendidos) {
        this.galonesVendidos = galonesVendidos;
    }

    public double getGalonesExistentes() {
        return galonesExistentes;
    }

    public void setGalonesExistentes(double galonesExistentes) {
        this.galonesExistentes = galonesExistentes;
    }

    public String getFechaMedida() {
        return fechaMedida;
    }

    public void setFechaMedida(String fecha) {
        this.fechaMedida = fecha;
    }

    public int getIdSurtidor() {
        return idSurtidor;
    }

    public int getIdCilindro() {
        return idCilindro;
    }

}
