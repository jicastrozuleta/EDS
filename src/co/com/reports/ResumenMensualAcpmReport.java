/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.reports;

import co.com.servicentro.util.Util;
import co.com.servicentroguerrero.conexion.Instance;
import co.com.servicentroguerrero.model.Model;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase generadora del reporte de liquidacioin extra interna.
 * @author JICZ4
 */
public class ResumenMensualAcpmReport {

    /**
     * Generar los parametros del reporte de liquidacion extra
     * @param fechaReporte
     * @return 
     */
    public HashMap reporteMensualAcpm(final String fechaReporte) {
        HashMap hashMap = new HashMap();

        try {
            String queryBase = ""
                    + "SELECT SUM(v.aceites), "
                    + "		 SUM(v.galonesAcpm), "
                    + "		 SUM(v.totalDineroAcpm), "
                    + "		 SUM(v.total), "
                    + "		 SUM(v.bauches) "
                    + "FROM view_resumenacpm v "
                    + "WHERE v.f_filtro = '" + fechaReporte + "';";
            
            /*Ejecutar la consulta para obtener el set de datos*/
            ResultSet resultSet = Instance.getInstance().executeQuery(queryBase);
            
            /*Capturar el resultado de la consulta*/
            if (resultSet != null && resultSet.first()) {
                hashMap.put("totalaceites", "$" + Util.formatearMiles(resultSet.getDouble(1)));
                hashMap.put("vendidoacpm", Util.formatearMiles(resultSet.getDouble(2)) + " Gls.");
                hashMap.put("totalacpm", "$" + Util.formatearMiles(resultSet.getDouble(3)));
                hashMap.put("total", "$" + Util.formatearMiles(resultSet.getDouble(4)));
                hashMap.put("totalbauches", "$" + Util.formatearMiles(resultSet.getDouble(5)));                
            }
            
            /*cerrar el resultset */
            if (resultSet != null && !resultSet.isClosed()) {
                resultSet.close();
            }
            
             /*agregar fecha de reporte*/
            hashMap.put("fecha_reporte", fechaReporte);
            
             /*agregar existencias acpm*/
            hashMap.put("existenciaacpm", String.valueOf(new Model().calcularExistenciaActualSurtidor(3)) + " Gls.");
        } catch (SQLException e) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, e);
        }
        return (!hashMap.isEmpty()) ? hashMap : null;
    }
}
