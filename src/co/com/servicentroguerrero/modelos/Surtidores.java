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
     * Maximo galonaje entregado por el surtidor
     */
    private double galonaje;
    
    /**
     * numero de serial del surtidor
     */
    private String serie;
    
    
    /**
     * Codigo para identificar el surtidor,
     * nombre del surtidor y combustible que entrega.
     */
    private String codigoIdentificador;
    
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
     * @param galonaje
     * @param codigoIdentificador 
     * @param serie
     * @param modelo
     * @param marca 
     * 
     */
    public Surtidores(int idSurtidor, int cantidadDispensadores, double galonaje, String codigoIdentificador, String serie, String modelo, String marca) {
        this.idSurtidor = idSurtidor;
        this.cantidadDispensadores = cantidadDispensadores;
        this.galonaje = galonaje;
        this.codigoIdentificador = codigoIdentificador;
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

    public double getGalonaje() {
        return galonaje;
    }

    public void setGalonaje(double galonaje) {
        this.galonaje = galonaje;
    }

    public String getCodigoIdentificador() {
        return codigoIdentificador;
    }

    public void setCodigoIdentificador(String codigoIdentificador) {
        this.codigoIdentificador = codigoIdentificador;
    }
}
