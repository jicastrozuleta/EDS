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
public class LiquidacionExtraReport {

    /**
     * Generar los parametros del reporte de liquidacion extra
     * @param fechaLiquidacion
     * @return 
     */
    public HashMap reporteLiquidacionExtra(String fechaLiquidacion) {
        HashMap hashMap = new HashMap();

        try {
            String queryBase = ""
                    + "SELECT	ROUND(SUM(l.dineroEntregado),2)," 
                    + "         ROUND(lq.totalAceites,2),"
                    + "		ROUND((SUM(l.dineroEntregado) + lq.totalAceites),2), "
                    + "         lq.fechaLiquidacion "
                    + "FROM liquidaciondispensador l "
                    + "INNER JOIN liquidaciones lq ON lq.numeroLiquidacion = l.numeroLiquidacion "
                    + "INNER JOIN dispensadores d ON d.idDispensador = l.idDispensador "
                    + "INNER JOIN cilindros c ON c.idCilindro = d.idCilindro "
                    + "INNER JOIN combustibles cb ON cb.idCombustible = c.idCombustible "
                    + "WHERE cb.idCombustible = 2 AND CAST(DATE_FORMAT(lq.fechaLiquidacion,'%Y-%m-%d') AS CHAR) = '" + fechaLiquidacion + "' "
                    + "GROUP BY cb.idCombustible;";
            
            /*Ejecutar la consulta para obtener el set de datos*/
            ResultSet resultSet = Instance.getInstance().executeQuery(queryBase);
            
            /*Capturar el resultado de la consulta*/
            if (resultSet != null && resultSet.first()) {
                hashMap.put("totalAcpm", "$" + Util.formatearMiles(resultSet.getDouble(1)));
                hashMap.put("totalAceites", "$" + Util.formatearMiles(resultSet.getDouble(2)));
                hashMap.put("totalDia", "$" + Util.formatearMiles(resultSet.getDouble(3)));
                hashMap.put("fechaLiquidacion", Util.formatoTextoFecha(resultSet.getString(4)));
            }
            
            /*cerrar el resultset */
            if (resultSet != null && !resultSet.isClosed()) {
                resultSet.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, e);
        }
        return (!hashMap.isEmpty()) ? hashMap : null;
    }
}
