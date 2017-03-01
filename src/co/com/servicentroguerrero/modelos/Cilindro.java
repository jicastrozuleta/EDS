/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.servicentroguerrero.modelos;

import java.io.Serializable;


/**
 * Clase que empaqueta un objeto de tipo cilindro. No se permite derivar de la
 * clase cilindro.
 *
 * @author JICZ4
 */
public final class Cilindro implements Serializable{

    /**
     * identificador unico del cilindro
     */
    private int idCilindro;

    /**
     * identificador unico del tipo de combustible que contiene el cilindro
     */
    private int idCombustible;

    /**
     * medida de longitud del cilindro
     */
    private double longitud;

    /**
     * medida del radio del cilindro.
     */
    private double radio;

    /**
     * Codigo de identificacion unico del cilindro
     */
    private String codigo;

    /**
     * Nombre del combustible que contiene el cilindro.
     */
    private String combustible;
        
    /**
     * Volumen fijo del cilindro
     */
    private double volumenFijo;

    /**
     * Constructor recomendado para crear un objeto del tipo.
     *
     * @param idCilindro
     * @param idCombustible
     * @param longitud
     * @param radio
     * @param codigo
     * @param combustible
     * @param volumenFijo
     */
    public Cilindro(int idCilindro, int idCombustible, double longitud, double radio, String codigo, String combustible, double volumenFijo) {
        this.idCilindro = idCilindro;
        this.idCombustible = idCombustible;
        this.longitud = longitud;
        this.radio = radio;
        this.codigo = codigo;
        this.combustible = combustible;
        this.volumenFijo = volumenFijo;
    }

    /**
     * Get id cilinddro
     *
     * @return
     */
    public int getIdCilindro() {
        return idCilindro;
    }

    public void setIdCilindro(int idCilindro) {
        this.idCilindro = idCilindro;
    }

    public int getIdCombustible() {
        return idCombustible;
    }

    public void setIdCombustible(int idCombustible) {
        this.idCombustible = idCombustible;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getRadio() {
        return radio;
    }

    public void setRadio(double radio) {
        this.radio = radio;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCombustible() {
        return combustible;
    }

    public void setCombustible(String combustible) {
        this.combustible = combustible;
    }

    public double getVolumenFijo() {
        return volumenFijo;
    }

    public void setVolumenFijo(double volumenFijo) {
        this.volumenFijo = volumenFijo;
    }
}
