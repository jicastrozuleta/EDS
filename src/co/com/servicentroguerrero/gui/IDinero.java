/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.servicentroguerrero.gui;

/**
 * Interface para controlar el total de dinero ingresado en una liquidacion
 * 
 * @author JICZ4
 */
public interface IDinero {
    
    /**
     * Metodo para calcular el cambio de dinero ingresado en las ventanas de ingreso de dinero
     * y transferido a la pantalla de liquidacion principal. 
     * @param totalDineroIngresado 
     */
    public void dineroIngresado(double totalDineroIngresado);
}
