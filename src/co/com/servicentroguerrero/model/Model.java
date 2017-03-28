/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.servicentroguerrero.model;

import co.com.servicentroguerrero.conexion.Instance;
import co.com.servicentroguerrero.modelos.Calibraciones;
import co.com.servicentroguerrero.modelos.Cilindro;
import co.com.servicentroguerrero.modelos.Combustible;
import co.com.servicentroguerrero.modelos.Empleado;
import co.com.servicentroguerrero.modelos.Liquidacion;
import co.com.servicentroguerrero.modelos.LiquidacionDispensador;
import co.com.servicentroguerrero.modelos.Surtidores;
import co.com.servicentroguerrero.modelos.Volumenes;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Model es la clase desde la cual se van a gestionar todos los accesos
 * (query's, updates, insert, deletes) hacia la base de datos.
 *
 * @author JICZ4
 */
public class Model {

    /**
     * Metodo para hacer el login de un usuario.
     *
     * @param user
     * @param password
     * @return empleado logeado, null si hay error en login.
     */
    public Empleado login(String user, String password) {
        ResultSet resultSet;
        Empleado empleado = null;
        try {
            
            resultSet = Instance.getInstance().executeQuery("CALL sp_login('" + user + "','" + password + "');");

            if (resultSet.first()) {
                do {
                    empleado = new Empleado(
                            resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getInt(3),
                            resultSet.getString(4).trim(),
                            resultSet.getString(5).trim(),
                            resultSet.getString(6).trim(),
                            resultSet.getString(7).trim(),
                            resultSet.getString(8).trim(),
                            0,
                            ""
                    );
                } while (resultSet.next());
            }
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return empleado;
    }

    /**
     * Metodo para listar los combustibles disponibles en la EDS.
     *
     * @return lista con los combustibles disponibles, o una lista vacia si no
     * hay combustibles
     */
    public ArrayList<Combustible> cargarCombustibles() {

        /*Query para cargar la lista de combustibles disponibles*/
        String query = ""
                + "SELECT   c.idCombustible,"
                + "         c.combustible,"
                + "         p.idPrecio,"
                + "         p.precio, "
                + "         p.fechaEntrada, "
                + "         p.fechaCierre "
                + "FROM combustibles c "
                + "INNER JOIN precios p ON p.idCombustible = c.idCombustible AND p.vigente = 1 "
                + "ORDER BY c.idCombustible;";

        /*Objeto lista de combustibles que sera llenada.*/
        ArrayList<Combustible> listaCombustibles = new ArrayList<>();

        try {
            /*Ejecutar la consulta para obtener el set de datos*/
            ResultSet resultSet = Instance.getInstance().executeQuery(query);

            /*Capturar el resultado de la consulta*/
            if (resultSet != null && resultSet.first()) {
                do {

                    Combustible combustible = new Combustible (
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getInt(3),
                            resultSet.getDouble(4),
                            resultSet.getString(5),
                            resultSet.getString(6)
                    );

                    /*agregar el combustible a la lista*/
                    listaCombustibles.add(combustible);
                } while (resultSet.next());
            }
        } catch (SQLException e) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, e);
        }
        return listaCombustibles;
    }

    /**
     * Metodo que permite actualiza el precio de un combustible en la BD.
     *
     * @param idCombustible id del combustible al que se le actualiza el precio
     * @param nuevoPrecio nuevo precio del combustible
     * @return 1 si el precio fue actualizado, un numero diferente en caso
     * contrario.
     */
    public int actualizarPrecioCombustible(int idCombustible, double nuevoPrecio) {
        String update = "CALL actualizar_precio_vigente('" + idCombustible + "','" + nuevoPrecio + "');";
        int validate = Integer.MIN_VALUE;
        try {
            /*Ejecutar la consulta para obtener el set de datos*/
            ResultSet resultSet = Instance.getInstance().executeQuery(update);

            /*Capturar el resultado de la consulta*/
            if (resultSet != null && resultSet.first()) {
                validate = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return validate;
    }

    /**
     * Metodo que permite actualizar el password de un usuario existente.
     *
     * @param usuario
     * @param passwordActual
     * @param nuevoPassword
     */
    public void cambiarPassword(String usuario, String passwordActual, String nuevoPassword) {
        try {
            String updatePassword = "UPDATE USUARIOS SET PASSWORD = '" + nuevoPassword + "' WHERE USER = '" + usuario + "' AND  PASSWORD = '" + passwordActual + "'; ";
            int res = Instance.getInstance().executeUpdate(updatePassword);

            if (res > 0) {
                System.out.println("Password actualizado");
            } else {
                System.out.println("Error actualizando password");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * Metodo para buscar un cilindro por codigo unico asignado.
     *
     * @param codigo
     * @return el objeto cilindro, o null en caso contrario.
     */
    public Cilindro buscarCilindroPorCodigo(String codigo) {
        Cilindro cilindro = null;
        String query = ""
                + "SELECT c.idCilindro AS idCilindro, "
                + "		 c.idCombustible AS idCombustible,"
                + "		 b.combustible AS combustible,"
                + "		 c.longitud AS longitud,"
                + "		 c.radio AS radio,"
                + "		 c.codigo AS codigo,"
                + "              c.volumenFijo AS volumenFijo  "
                + "FROM cilindros c "
                + "INNER JOIN combustibles b ON b.idCombustible = c.idCombustible "
                + "WHERE codigo = '" + codigo + "';";

        try {
            /*Ejecutar la consulta para obtener el set de datos*/
            ResultSet resultSet = Instance.getInstance().executeQuery(query);

            /*Capturar el resultado de la consulta*/
            if (resultSet != null && resultSet.first()) {

                cilindro = new Cilindro(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getDouble(4),
                        resultSet.getDouble(5),
                        resultSet.getString(6),
                        resultSet.getString(3),
                        resultSet.getDouble(7)
                );
            }
        } catch (SQLException e) {
            cilindro = null;
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return cilindro;
    }

    /**
     * Metodo que permite la insercion de un nuevo volumen calculado por el
     * usuario.
     *
     * @param volumenes Objeto que contiene los datos a insertar. <br>
     * Solo son necesarios los datos de idCilindro y volumen calculado, los
     * demas pueden ser null o cualquier valor
     * @return el rowid del volumen insertado.
     */
    public long insertarVolumenes(Volumenes volumenes) {
        long rowid = -1l;
        String insert = "CALL insertar_volumenes('" + volumenes.getIdCilindro() + "','" + volumenes.getVolumen() + "');";
        try {
            /*Ejecutar la consulta para obtener el set de datos*/
            ResultSet resultSet = Instance.getInstance().executeQuery(insert);

            /*Capturar el resultado de la consulta*/
            if (resultSet != null && resultSet.first()) {
                rowid = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            rowid = -1l;
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return rowid;
    }

    /**
     * Metodo que permite cargar la lista de surtidores disponibles en la EDS
     *
     * @return una lista con los objetos disponibles.
     */
    public ArrayList<Surtidores> cargarSurtidores() {

        /*Query para cargar la lista de surtidores disponibles*/
        String query = ""
                + "SELECT   s.idSurtidor,"
                + "         s.cantidadDispensadores,"
                + "         s.galonaje, "
                + "         s.codigoIdentificador, "
                + "         s.serie,"
                + "         s.modelo,"
                + "         s.marca "                
                + "FROM surtidores s "
                + "ORDER BY s.idSurtidor;";

        /*Objeto lista que sera llenada.*/
        ArrayList<Surtidores> listaSurtidores = new ArrayList<>();

        try {
            /*Ejecutar la consulta para obtener el set de datos*/
            ResultSet resultSet = Instance.getInstance().executeQuery(query);

            /*Capturar el resultado de la consulta*/
            if (resultSet != null && resultSet.first()) {
                do {
                    Surtidores surtidor = new Surtidores(
                            resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getDouble(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getString(7)
                    );

                    /*agregar el combustible a la lista*/
                    listaSurtidores.add(surtidor);
                } while (resultSet.next());
            }
        } catch (SQLException e) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, e);
        }
        return listaSurtidores;
    }

    
    /**
     * Metodo que permite la insercion de una calibracion de surtidor en BD
     * @param calibracion
     * @return rowid del registro insertado, o un valor menor o igual a cero en caso de algun error en la insercion.
     */
    public long insertarCalibracion(Calibraciones calibracion) {
        long rowid = -1l;
        String insert = "CALL insertar_calibracion('" + calibracion.getIdSurtidor() + "','" + calibracion.getGalonesUsados() + "','" + calibracion.getDescripcion() + "');";
        try {
            /*Ejecutar la consulta para obtener el set de datos*/
            ResultSet resultSet = Instance.getInstance().executeQuery(insert);

            /*Capturar el resultado de la consulta*/
            if (resultSet != null && resultSet.first()) {
                rowid = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            rowid = -1l;
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return rowid;
    }
    
    
    /**
     * Metodo para cargar una lista con los empleados disponibles segun el rol especificado.
     * @param rol
     * @return ArrayList de empleados. o una lista vacia si no hay empleados disponibles.
     */
    public ArrayList<Empleado> cargarEmpleadosPorRol(String rol) {

        /*Query para cargar la lista de empleados activos por rol*/
        String query = "CALL sp_empleadosPorRol('" + rol + "')";

        /*Objeto lista que sera llenada.*/
        ArrayList<Empleado> listaEmpleados = new ArrayList<>();

        try {
            /*Ejecutar la consulta para obtener el set de datos*/
            ResultSet resultSet = Instance.getInstance().executeQuery(query);

            /*Capturar el resultado de la consulta*/
            if (resultSet != null && resultSet.first()) {
                do {
                    Empleado empleado = new Empleado(
                            resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getInt(3),
                            resultSet.getString(4).trim(),
                            resultSet.getString(5).trim(),
                            resultSet.getString(6).trim(),
                            resultSet.getString(7).trim(),
                            resultSet.getString(8).trim(),
                            resultSet.getInt(9),
                            resultSet.getString(10)
                    );

                    /*agregar el combustible a la lista*/
                    listaEmpleados.add(empleado);
                } while (resultSet.next());
            }
        } catch (SQLException e) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, e);
        }
        return listaEmpleados;
    }
    
    
    /**
     * Metodo que permite cargar la informacion de la ultima liquidacion 
     * ejecutada en el sistema.
     * @return Liquidacion, instancia de la clase con la informacioin de la liquidacion o null
     * si no se entuentra informacion.
     */
    public Liquidacion cargarUltimaLiquidacion(){
    

        /*Query para cargar la informacion de la ultima liquidacion*/
        String query = "CALL sp_ultimaLiquidacion()";

        /*instancia null para conservar la liquidacion*/
        Liquidacion liquidacion = null;
        try {
            /*Ejecutar la consulta para obtener el set de datos*/
            ResultSet resultSet = Instance.getInstance().executeQuery(query);

            /*Capturar el resultado de la consulta*/
            if (resultSet != null && resultSet.first()) {
                do {
                    liquidacion = new Liquidacion(
                            resultSet.getLong(1),
                            resultSet.getInt(2),
                            resultSet.getDouble(3),
                            resultSet.getDouble(4),
                            resultSet.getDouble(5),
                            resultSet.getDouble(6),
                            resultSet.getDouble(7),                            
                            resultSet.getString(8)
                    );
                } while (resultSet.next());
            }
        } catch (SQLException e) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, e);
        }
        return liquidacion;
    }
    
    
    /**
     * Metodo para cargar la liquidacion individual de cada dispensador disponible,
     * de la ultima liquidacion.
     * @param liquidacion
     * @return lista con la liquidacion individual por dispensador, de la ultima liquidacion disponible en 
     * el sistema.
     */
    public ArrayList<LiquidacionDispensador> cargarDetallesUltimaLiquidacion(Liquidacion liquidacion){
    
        /*Query para cargar la informacion de la ultima liquidacion de cada surtidor*/
        String query = "CALL sp_detalleLiquidacionDispensadores(" + liquidacion.getNumeroLiquidacion() + ")";
        
        /*Objeto lista que sera llenada.*/
        ArrayList<LiquidacionDispensador> listaLiquidacionDispensador = new ArrayList<>();

        try {
            /*Ejecutar la consulta para obtener el set de datos*/
            ResultSet resultSet = Instance.getInstance().executeQuery(query);

            /*Capturar el resultado de la consulta*/
            if (resultSet != null && resultSet.first()) {
                do {
                    LiquidacionDispensador liquidacionDispensador = new LiquidacionDispensador(
                            resultSet.getLong(1),
                            resultSet.getInt(2),
                            resultSet.getInt(3),
                            resultSet.getInt(4),
                            resultSet.getDouble(5),
                            resultSet.getDouble(6),
                            resultSet.getDouble(7),
                            resultSet.getDouble(8),
                            resultSet.getDouble(9),
                            resultSet.getDouble(10),
                            resultSet.getDouble(11),
                            resultSet.getInt(12),
                            resultSet.getString(13).trim()
                    );

                    /*agregar el combustible a la lista*/
                    listaLiquidacionDispensador.add(liquidacionDispensador);
                } while (resultSet.next());
            }
        } catch (SQLException e) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, e);
        }
        return listaLiquidacionDispensador;
    }
    
    /**
     * metodo para insertar la cabecera de liquidacion del dia.
     * @param liquidacion
     * @param liquidacionesPorDispensador
     * @return numeroLiquidacion, llave primaria para los detalles de la liquidacion actual.
     */
    public long insertarCabeceraDeLiquidacion(final Liquidacion liquidacion, final String liquidacionesPorDispensador) {
        
        /*Numero de liquidacion generado por la insersion en base de datos*/
        long numeroLiquidacion = Long.MIN_VALUE;
        
        /*LLamar el SP que inicia la transaccion de liquidacion, no confirma con commit 
        hasta que se termine de insertar el ultimo detalle de dispensador de la liquidacion actual*/
        String insert = "CALL sp_insertarLiquidacion("
                + " '" + liquidacion.getIdEmpleadoLiquidador() + "',"
                + " '" + liquidacion.getTotalCombustibles() + "',"
                + " '" + liquidacion.getTotalAceites() + "',"
                + " '" + liquidacion.getTotalLiquidado() + "',"
                + " '" + liquidacion.getDineroEntregado() + "',"
                + " '" + liquidacion.getDiferencia() + "',"
                + " '" + liquidacionesPorDispensador + "'"
                + ");";
        
        try {
            /*Ejecutar la consulta para obtener el set de datos*/
            ResultSet resultSet = Instance.getInstance().executeQuery(insert);

            /*Capturar el resultado de la consulta*/
            if (resultSet != null && resultSet.first()) {
                numeroLiquidacion = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, e);
        }
        return numeroLiquidacion;
    }
}