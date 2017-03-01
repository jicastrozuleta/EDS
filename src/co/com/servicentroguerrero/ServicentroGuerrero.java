/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.servicentroguerrero;

import co.com.servicentroguerrero.controler.ControllerBO;

/**
 *
 * @author JICZ4
 */
public class ServicentroGuerrero {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ControllerBO.cargarCombustibles().forEach((e) -> {
            System.out.println(e.getCombustible() + " " + e.getPrecioVigente() + " " + e.getFechaEntradaVigencia() );
        });
    }
}