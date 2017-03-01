/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.servicentroguerrero.modelos;

import java.io.Serializable;

/**
 *
 * Clase para empaquetar el objeto surtidor.
 * @author JICZ4
 */
public class Surtidores implements Serializable{
    

    /**
     * Identificador unico del surtidor
     */
    private int idSurtidor;
    
    /**
     * cantidad de dispensadores (mangueras) con que cuenta el dispensador.
     */
    private int cantidadDispensadores;
    
    /**
     * numero de serial del surtidor
     */
    private String serie;
    
    /**
     * modelo o nombre de referencia del surtidor
     */
    private String modelo;
    
    /**
     * marca del surtidor.
     */
    private String marca;

    
    /**
     * Contructor recomendado para crear el objeto.
     * @param idSurtidor
     * @param cantidadDispensadores
     * @param serie
     * @param modelo
     * @param marca 
     */
    public Surtidores(int idSurtidor, int cantidadDispensadores, String serie, String modelo, String marca) {
        this.idSurtidor = idSurtidor;
        this.cantidadDispensadores = cantidadDispensadores;
        this.serie = serie;
        this.modelo = modelo;
        this.marca = marca;
    }

    public int getIdSurtidor() {
        return idSurtidor;
    }

    public void setIdSurtidor(int idSurtidor) {
        this.idSurtidor = idSurtidor;
    }

    public int getCantidadDispensadores() {
        return cantidadDispensadores;
    }

    public void setCantidadDispensadores(int cantidadDispensadores) {
        this.cantidadDispensadores = cantidadDispensadores;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }    
}
