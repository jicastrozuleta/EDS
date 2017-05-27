/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.servicentro.util;

import co.com.servicentroguerrero.model.Model;
import co.com.servicentroguerrero.modelos.Calibraciones;
import co.com.servicentroguerrero.modelos.Surtidores;
import java.util.ArrayList;

/**
 *
 * @author JICZ
 */
public class ResumenExistencia {
    
    
    /**
     * Modelo de conexcion a la base de datos.
     */
    private static final Model MODELO = new Model();
    


    
    
    /**
     * generar los encabezados de resumen en existenccias por surtidor
     * @return 
     */
    public static ArrayList<EncabezadoResumenExistencias> cargarEncabezadosResumen() {
        /*Lista que contendra la informacion de resumen*/
        ArrayList<EncabezadoResumenExistencias> listaResumen = new ArrayList<>();
        
        /*lista de calibracion disponibles para todos los surtidores*/
        ArrayList<Calibraciones> listaCalibraciones = MODELO.obtenerUltimaCalibracionSurtidor();
        
        /*cargar la informacion por cada surtidor*/
        MODELO.cargarSurtidores().stream().map((surtidor) -> {
            EncabezadoResumenExistencias encabezadoResumenExistencias = new EncabezadoResumenExistencias();
            encabezadoResumenExistencias.setIdSurtidor(surtidor.getIdSurtidor());
            calcularResumenMovimientos(surtidor.getIdSurtidor(), encabezadoResumenExistencias);
            asignarCalibracionEncabezado(encabezadoResumenExistencias, listaCalibraciones);
            encabezadoResumenExistencias.setEnExistencias(MODELO.calcularExistenciaActualSurtidor(surtidor.getIdSurtidor()));
            return encabezadoResumenExistencias;
        }).forEachOrdered((encabezadoResumenExistencias) -> {
            /*agreagar el resumen a la lista*/
            listaResumen.add(encabezadoResumenExistencias);
        });    
        return listaResumen;
    }

    
    /**
     * Metodo que permite calcular movimeintos de total vendido y comprado de un surtidor hasta la fecha actual
     * @param idSurtidor 
     */
    private static void calcularResumenMovimientos(final int idSurtidor, EncabezadoResumenExistencias encabezadoResumenExistencias) {
        MODELO.generarResumenEncabezadoExistencias(idSurtidor, encabezadoResumenExistencias);
    }

    
    /**
     * asignar la calibracion al encabezado
     * @param encabezadoResumenExistencias
     * @param listaCalibraciones 
     */
    private static void asignarCalibracionEncabezado(EncabezadoResumenExistencias encabezadoResumenExistencias, ArrayList<Calibraciones> listaCalibraciones) {
        for (Calibraciones calibracion : listaCalibraciones) {
            if(encabezadoResumenExistencias.getIdSurtidor() == calibracion.getIdSurtidor()){
                encabezadoResumenExistencias.setCalibracion(calibracion);
                break;
            }
        }
    }
    
   
    
    
}
