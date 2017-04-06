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
 * Clase generadora del reporte de liquidacioin diaria.
 * @author JICZ4
 */
public class LiquidacionDiariaReport {

    public HashMap reporteLiquidacionDispensador(String fechaLiquidacion) {
        HashMap hashMap = new HashMap();

        try {
            String queryBase = "CALL sp_baseLiquidacion('" + fechaLiquidacion + "');";
            long numeroLiquidacion = obtenerBaseReporte(queryBase, hashMap);
            obtenerTotalesPorSurtidor(hashMap, numeroLiquidacion);
            obtenerResumenCombustibles(hashMap, numeroLiquidacion);

        } catch (SQLException e) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, e);
        }

        return (!hashMap.isEmpty()) ? hashMap : null;
    }

    /**
     * Metodo para obtener la base del reporte de liquidacion diaria.
     *
     * @param queryBase
     * @param hashMap
     * @return
     * @throws SQLException
     */
    private long obtenerBaseReporte(String queryBase, HashMap hashMap) throws SQLException {
        /*Ejecutar la consulta para obtener el set de datos*/
        ResultSet resultSet = Instance.getInstance().executeQuery(queryBase);
        long numeroLiquidacion = 0;
        /*Capturar el resultado de la consulta*/
        if (resultSet != null && resultSet.first()) {
            numeroLiquidacion = resultSet.getLong(1);
            hashMap.put("totalDineroCombustible", "$" + Util.formatearMiles(resultSet.getDouble(2)));
            hashMap.put("fechaLiquidacion", resultSet.getString(3));
        }
        /*cerrar el resultset */
        if (resultSet != null && !resultSet.isClosed()) {
            resultSet.close();
        }
        return numeroLiquidacion;
    }

    /**
     * Metodo para obtener el total de dinero por dispensador
     *
     * @param hashMap
     * @param numeroLiquidacion
     * @throws SQLException
     */
    private void obtenerTotalesPorSurtidor(HashMap hashMap, long numeroLiquidacion) throws SQLException {
        String query = ""
                + "SELECT   ld.idSurtidor AS i,"
                + "         SUM(ld.dineroEntregado) AS s "
                + "FROM liquidaciondispensador ld "
                + "INNER JOIN precios p ON p.idPrecio = ld.idPrecio "
                + "WHERE ld.numeroLiquidacion = " + numeroLiquidacion + " "
                + "GROUP BY ld.idSurtidor "
                + "ORDER BY ld.idSurtidor, ld.idDispensador;";

        /*Ejecutar la consulta para obtener el set de datos*/
        ResultSet resultSet = Instance.getInstance().executeQuery(query);

        /*Capturar el resultado de la consulta*/
        if (resultSet != null && resultSet.first()) {
            do {
                switch (resultSet.getInt(1)) {
                    case 1:
                        hashMap.put("totalSurtidor1", "$" + Util.formatearMiles(resultSet.getDouble(2)));
                        break;
                    case 2:
                        hashMap.put("totalSurtidor2", "$" + Util.formatearMiles(resultSet.getDouble(2)));
                        break;
                    case 3:
                        hashMap.put("totalSurtidor3", "$" + Util.formatearMiles(resultSet.getDouble(2)));
                        break;
                }
            } while (resultSet.next());
        }
        /*cerrar el resultset */
        if (resultSet != null && !resultSet.isClosed()) {
            resultSet.close();
        }
    }

    /**
     * Metodo para calular el resumen de combustibles (galones y precio de
     * venta)
     *
     * @param hashMap
     * @param numeroLiquidacion
     * @throws SQLException
     */
    private void obtenerResumenCombustibles(HashMap hashMap, long numeroLiquidacion) throws SQLException {
        String query = ""
                + "SELECT   c.idCombustible AS c, "
                + "         SUM(ld.galones) AS g, "
                + "         p.precio AS p "
                + "FROM liquidaciondispensador ld "
                + "INNER JOIN precios p ON p.idPrecio = ld.idPrecio "
                + "INNER JOIN combustibles c ON c.idCombustible = p.idCombustible "
                + "WHERE ld.numeroLiquidacion = " + numeroLiquidacion + " "
                + "GROUP BY ld.idPrecio "
                + "ORDER BY ld.idPrecio;";

        /*Ejecutar la consulta para obtener el set de datos*/
        ResultSet resultSet = Instance.getInstance().executeQuery(query);

        /*Capturar el resultado de la consulta*/
        if (resultSet != null && resultSet.first()) {
            do {
                switch (resultSet.getInt(1)) {
                    case 1:
                        hashMap.put("totalGalonesCorriente", Util.formatearMiles(resultSet.getDouble(2)));
                        hashMap.put("precioCorriente", "$" + Util.formatearMiles(resultSet.getDouble(3)));
                        break;
                    case 2:
                        hashMap.put("totalGalonesAcpm", Util.formatearMiles(resultSet.getDouble(2)));
                        hashMap.put("precioAcpm", "$" + Util.formatearMiles(resultSet.getDouble(3)));
                        break;
                }
            } while (resultSet.next());
        }
        /*cerrar el resultset */
        if (resultSet != null && !resultSet.isClosed()) {
            resultSet.close();
        }
    }
}
