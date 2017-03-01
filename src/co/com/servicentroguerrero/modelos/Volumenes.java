/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.servicentroguerrero.modelos;

/**
 * 
 * Clase para empaquetar el objeto volumenes, mapeado de la tabla volumenes.
 * @author JICZ4
 */
public class Volumenes {
    
    /**
     * identificador llave primaria del volumen registrado.
     */
    private long idVolumen;
    
    /**
     * identificador del cilindro al cual fue calculado el volumen
     */
    private int idCilindro;
    
    /**
     * volumen calculado del cilindro
     */
    private double volumen;
    
    /**
     * fecha y hora en que fue calculado el volumen de combustible en el cilindro
     */
    private String fecha;

    
    /**
     * Metodo constructor recomendado para conservar el objeto
     * @param idVolumen
     * @param idCilindro
     * @param volumen
     * @param fecha 
     */
    public Volumenes(long idVolumen, int idCilindro, double volumen, String fecha) {
        this.idVolumen = idVolumen;
        this.idCilindro = idCilindro;
        this.volumen = volumen;
        this.fecha = fecha;
    }

    public long getIdVolumen() {
        return idVolumen;
    }

    public void setIdVolumen(long idVolumen) {
        this.idVolumen = idVolumen;
    }

    public int getIdCilindro() {
        return idCilindro;
    }

    public void setIdCilindro(int idCilindro) {
        this.idCilindro = idCilindro;
    }

    public double getVolumen() {
        return volumen;
    }

    public void setVolumen(double volumen) {
        this.volumen = volumen;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}