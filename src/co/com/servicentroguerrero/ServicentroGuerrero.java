/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.servicentroguerrero;

import co.com.reports.Reports;
import co.com.servicentroguerrero.backup.BackUp;
import co.com.servicentroguerrero.controler.ControllerBO;
import co.com.servicentroguerrero.modelos.Existencias;
import java.util.ArrayList;

/**
 *
 * @author JICZ4
 */
public class ServicentroGuerrero {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    
     /*calcular movimiento de existencias de combustibles*/
        ArrayList<Existencias> listaExistencias = ControllerBO.cargarExistenciasDeCombustible();
        
        for (Existencias existencia : listaExistencias) {
                if(existencia.getIdSurtidor() == 1){
                    existencia.setGalonesComprados(0);
                    existencia.setGalonesVendidos(414.690);
                    existencia.setGalonesExistentes(existencia.getMedidaGalonesInicial() + 0 - 414.690);
                }
                
                if(existencia.getIdSurtidor() == 2){
                    existencia.setGalonesComprados(0);
                    existencia.setGalonesVendidos(443.530);
                    existencia.setGalonesExistentes(existencia.getMedidaGalonesInicial() + 0 - 443.530);
                }
                
                if(existencia.getIdSurtidor() == 3){
                    existencia.setGalonesComprados(0);
                    existencia.setGalonesVendidos(106.660);
                    existencia.setGalonesExistentes(existencia.getMedidaGalonesInicial() + 0 - 106.660);
                }
            }
        
        ControllerBO.registrarMovimientoDeCombustibles(listaExistencias);
    
    
    }

}