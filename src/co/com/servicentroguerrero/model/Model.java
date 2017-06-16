/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.servicentroguerrero.model;

import co.com.servicentro.util.EncabezadoResumenExistencias;
import co.com.servicentro.util.Util;
import co.com.servicentroguerrero.conexion.Instance;
import co.com.servicentroguerrero.modelos.Calibraciones;
import co.com.servicentroguerrero.modelos.Cilindro;
import co.com.servicentroguerrero.modelos.Combustible;
import co.com.servicentroguerrero.modelos.Empleado;
import co.com.servicentroguerrero.modelos.Existencias;
import co.com.servicentroguerrero.modelos.Liquidacion;
import co.com.servicentroguerrero.modelos.LiquidacionDispensador;
import co.com.servicentroguerrero.modelos.Rol;
import co.com.servicentroguerrero.modelos.Surtidores;
import co.com.servicentroguerrero.modelos.Volumenes;
import java.sql.PreparedStatement;
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
     * @param codigoSurtidor
     * @return el objeto cilindro, o null en caso contrario.
     */
    public Cilindro buscarCilindroPorCodigoSurtidor(String codigoSurtidor) {
        Cilindro cilindro = null;
        String query = ""
                + "SELECT c.idCilindro AS idCilindro, "
                + "       c.idCombustible AS idCombustible, "
                + "       b.combustible AS combustible, "
                + "       c.longitud AS longitud, "
                + "       c.radio AS radio, "
                + "       c.codigo AS codigo, "
                + "       c.volumenFijo AS volumenFijo "
                + "FROM surtidores s "
                + "INNER JOIN dispensadores d ON d.idSurtidor = s.idSurtidor "
                + "INNER JOIN cilindros c ON c.idCilindro = d.idCilindro "
                + "INNER JOIN combustibles b ON b.idCombustible = c.idCombustible "
                + "WHERE s.idSurtidor = " + codigoSurtidor + " "
                + "GROUP BY s.idSurtidor";

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
     * Metodo que permite cargar la lista de surtidores disponibles que aun no se les ha realizado
     * medida de regla mojada en el dia actual.
     *
     * @return una lista con los objetos disponibles.
     */
    public ArrayList<Surtidores> cargarSurtidoresSinMedidaRegla() {

        /*Query para cargar la lista de surtidores disponibles*/
        String query = ""
                + "SELECT   s.idSurtidor, "
                + "         s.cantidadDispensadores, "
                + "         s.galonaje, "
                + "         s.codigoIdentificador, "
                + "         s.serie, "
                + "         s.modelo, "
                + "         s.marca, "
                + "         v.fecha "
                + "FROM surtidores s "
                + "INNER JOIN dispensadores d ON d.idSurtidor = s.idSurtidor "
                + "INNER JOIN cilindros c ON c.idCilindro = d.idCilindro "
                + "LEFT JOIN volumenes v ON v.idCilindro = c.idCilindro "
                + "WHERE c.idCilindro NOT IN ( "
                + "	SELECT v.idCilindro FROM volumenes v "
                + "	WHERE TIMESTAMPDIFF(DAY,DATE_FORMAT(v.fecha,'%Y-%m-%d'), CURRENT_DATE()) <= 0 "
                + ") "
                + "GROUP BY s.idSurtidor "
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

    
    /**
     * Metodo para insertar un nuevo empleado en la base de datos.
     * si tiene usuario y contraseña, tambien insertar en tabla de usuarios con acceso.
     * @param empleado
     * @param usuario
     * @param password
     * @return true si el empleado fue insertado de forma correcta.
     */
    public boolean insertarEmpleado(Empleado empleado, String usuario, String password) {
        /*Numero de id generado por la insersion en base de datos*/
        int nuevoIdEmpleado = 0;
        
        /*LLamar el SP que inicia la transaccion de liquidacion, no confirma con commit 
        hasta que se termine de insertar el ultimo detalle de dispensador de la liquidacion actual*/
        String insert = "CALL sp_insertarEmpleado("
                + "'" + empleado.getIdentificacion() + "', "
                + "'" + empleado.getNombres() + "', "
                + "'" + empleado.getApellidos() + "', "
                + "'" + empleado.getTelefono() + "', "
                + "'" + empleado.getDireccion() + "',  "
                + "'" + empleado.getIdRol() + "'  "
                + ");";
        
        try {
            /*Ejecutar la consulta para obtener el set de datos*/
            ResultSet resultSet = Instance.getInstance().executeQuery(insert);

            /*Capturar el resultado de la consulta*/
            if (resultSet != null && resultSet.first()) {
                nuevoIdEmpleado = resultSet.getInt(1);
            }
            
            /*cerrar el resultSet actual*/
            if(resultSet != null && !resultSet.isClosed())
                resultSet.close();
            
            /*Insertar usuario y contraseña si es necesario*/
            if(usuario != null && usuario.length() > 0 && password != null && password.length() > 0 && nuevoIdEmpleado > 0){
                String insertUser = "CALL sp_insertarUsuario( "
                        + "'" + nuevoIdEmpleado + "', "
                        + "'" + usuario + "', "
                        + "'" + password + "'  "
                        + ");";
                
                /*Ejecutar la consulta para obtener el set de datos*/
                resultSet = Instance.getInstance().executeQuery(insertUser);
                
                /*Capturar el resultado de la consulta*/
                if (resultSet != null && resultSet.first()) {
                    nuevoIdEmpleado = resultSet.getInt(1);
                }
                
                /*cerrar el resultSet actual*/
                if(resultSet != null && !resultSet.isClosed())
                    resultSet.close();
                }
        } catch (SQLException e) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, e);
        }
        return (nuevoIdEmpleado > 0);
    }

    
    /**
     * buscar el empleado por numero de identificacion en la base de datos.
     * @param identificacion
     * @return objeto empleado con los datos del empleado encontrado.
     */
    public Empleado buscarEmpleadoPorIdentificacion(final String identificacion) {
         Empleado empleado = null;
        String query = ""
                + "SELECT   e.idEmpleado,"
                + "         e.idEds,"
                + "         e.activo,"
                + "         e.identificacion,"
                + "         e.nombres,"
                + "         e.apellidos,"
                + "         e.telefono,"
                + "         e.direccion "
                + "FROM empleados e "
                + "WHERE e.identificacion = '" + identificacion + "';";

        try {
            /*Ejecutar la consulta para obtener el set de datos*/
            ResultSet resultSet = Instance.getInstance().executeQuery(query);

            /*Capturar el resultado de la consulta*/
            if (resultSet != null && resultSet.first()) {

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
                        null
                );
            }
            /*cerrar el resulset.*/
            if(resultSet != null && !resultSet.isClosed())
                resultSet.close();
        } catch (SQLException e) {
            empleado = null;
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return empleado;
    }

    
    /**
     * Metodo que actualiza el estado de un empleado, para eliminarlo del sistema.
     * @param idEmpleadoEliminar
     * @return true si es cambiado su estado, false en caso contrario.
     */
    public boolean desactivarEmpleado(final int idEmpleadoEliminar) {
        int desactivado = 0;
        
        /*Query para cargar la informacion de la ultima liquidacion*/
        String query = "CALL sp_desactivarEmpleado('" + idEmpleadoEliminar + "');";
        
        try {
            /*Ejecutar la consulta para obtener el set de datos*/
            ResultSet resultSet = Instance.getInstance().executeQuery(query);

            /*Capturar el resultado de la consulta*/
            if (resultSet != null && resultSet.first()) {
                do {
                    desactivado = resultSet.getInt(1);
                } while (resultSet.next());
            }
            
            /*cerrar el resulset.*/
            if(resultSet != null && !resultSet.isClosed())
                resultSet.close();
            
        } catch (SQLException e) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, e);
        }
        return (desactivado > 0);
    }
    
    
    
    /**
     * cargar las existencias iniciales de combustibles del dia.
     * 
     * @return lista con las existencias de combustible de cada cilindro.
     */
    public ArrayList<Existencias> cargarExistenciasDeCombustible() {
        
        /*lista vacia para conservar la existencias encontradas.*/
        ArrayList<Existencias> listaExistencias = new ArrayList<>();
        
        /*Query para cargar la lista de existencias del dia.*/
        
        String query = ""
                + "SELECT ROUND(g.existentes,2),"
                + "	  d.idSurtidor,"
                + "	  c.idCilindro "
                + "FROM galones g "
                + "INNER JOIN cilindros c ON c.idCilindro = g.idCilindro "
                + "INNER JOIN dispensadores d ON d.idCilindro = c.idCilindro "
                + "WHERE g.idGalon IN ( "
                + "	SELECT MAX(idGalon) "
                + "	FROM GALONES "
                + "	GROUP BY idCilindro "
                + ") "
                + "GROUP BY d.idCilindro "
                + "ORDER BY d.idCilindro;";

        try {
            /*Ejecutar la consulta para obtener el set de datos*/
            ResultSet resultSet = Instance.getInstance().executeQuery(query);

            /*Capturar el resultado de la consulta*/
            if (resultSet != null && resultSet.first()) {
                do {
                    Existencias existencias = new Existencias(
                            resultSet.getDouble(1),
                            resultSet.getInt(2),
                            resultSet.getInt(3)
                    );
                    /*agregar a la lista de existencias*/
                    listaExistencias.add(existencias);
                } while (resultSet.next());
            }
            /*cerrar el resulset.*/
            if(resultSet != null && !resultSet.isClosed())
                resultSet.close();
        } catch (SQLException e) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return listaExistencias;
    }

    
    /**
     * Insertar en base de datos los registros que controlan el movimiento de existencias
     * de combustibles que se realizaron en el dia.
     * @param listaExistencias 
     */
    public void registrarMovimientoDeExistenciasDeCombustibles(final ArrayList<Existencias> listaExistencias) {
        
        try {
            /*Query con prepareStatement*/
            String insert = "CALL sp_insertarExistenciasGalones(?, ?, ?, ?);";
            PreparedStatement pstmtSurtidores = Instance.getPrivateConexion().getConexion().prepareStatement(insert);

            /*insertar los movimientos de combustibles*/
            for (Existencias existencia : listaExistencias) {
                pstmtSurtidores.setInt(1, existencia.getIdCilindro());
                pstmtSurtidores.setDouble(2, existencia.getGalonesVendidos());
                pstmtSurtidores.setDouble(3, existencia.getGalonesComprados());
                pstmtSurtidores.setDouble(4, existencia.getGalonesExistentes());
                pstmtSurtidores.execute();
                /*Limpiar los parametros*/
                pstmtSurtidores.clearParameters();
            }
        } catch (SQLException e) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    
    /**
     * Insertar un nuevo registro de precios de planta de combustibles.
     * EL nuevo registro insertado sera el vigente.
     * @param precioCorriente
     * @param precioAcpm
     * @return 
     */
    public int actualizarPrecioCombustiblesPlanta(double precioCorriente, double precioAcpm) {
        int validate = 0;
        try {
            /*Query para actualizar el registro*/
            String insert = "CALL sp_actualizarPreciosPlanta('" + precioCorriente + "','" + precioAcpm + "');";
 
            /*Ejecutar la consulta para obtener el set de datos*/
            ResultSet resultSet = Instance.getInstance().executeQuery(insert);

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
     * 
     * @return 
     */
    public ArrayList<Object[]> cargarResumenLiquidacionesCorriente() {
        /*lista inicial vacia para conservar los objetos cargados de BD*/
        ArrayList<Object[]> lista = new ArrayList<>();
        try {
            /*Query para cargar la informacion necesaria*/
            String query = ""
                    + "SELECT 	v.fecha,"
                    + "		v.dia, "
                    + "		v.galonesCorriente,"
                    + "         v.pVenta,"
                    + "         v.pCompra, "
                    + "		v.gananciaUnitaria, "
                    + "		v.gananciaCorriente "
                    + "FROM view_resumenLiquidacion v;";
 
            /*Ejecutar la consulta para obtener el set de datos*/
            ResultSet resultSet = Instance.getInstance().executeQuery(query);

            /*Capturar el resultado de la consulta*/
            if (resultSet != null && resultSet.first()) {
                do {
                    /*llenar la lista con los datos obtenidos*/
                    lista.add(new Object[]{
                        Util.devolverMesDeFecha(resultSet.getString(1).trim()),
                        resultSet.getString(2).trim(),
                        resultSet.getString(3).trim(),
                        Util.formatearMiles(resultSet.getDouble(4)),
                        Util.formatearMiles(resultSet.getDouble(5)),
                        Util.formatearMiles(resultSet.getDouble(6)),
                        Util.formatearMiles(resultSet.getDouble(7))                        
                    });
                } while (resultSet.next());
            }
        } catch (SQLException e) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return lista;
    }

    
    public ArrayList<Object[]> cargarResumenLiquidacionesAcpm() {
        /*lista inicial vacia para conservar los objetos cargados de BD*/
        ArrayList<Object[]> lista = new ArrayList<>();
        try {
            /*Query para cargar la informacion necesaria*/
            String query = ""
                    + "SELECT   v.fecha,"
                    + "         v.dia, "
                    + "		 v.aceites, "
                    + "		 v.galonesAcpm, "
                    + "		 v.totalDineroAcpm, "
                    + "		 v.total,"
                    + "          v.pCompra, "
                    + "		 v.pVenta "
                    + "FROM view_resumenAcpm v;";
 
            /*Ejecutar la consulta para obtener el set de datos*/
            ResultSet resultSet = Instance.getInstance().executeQuery(query);

            /*Capturar el resultado de la consulta*/
            if (resultSet != null && resultSet.first()) {
                do {
                    /*llenar la lista con los datos obtenidos*/
                    lista.add(new Object[]{
                        Util.devolverMesDeFecha(resultSet.getString(1).trim()),
                        resultSet.getString(2).trim(),
                        "$" + Util.formatearMiles(resultSet.getDouble(3)),
                        Util.formatearMiles(resultSet.getDouble(4)),
                        "$" + Util.formatearMiles(resultSet.getDouble(5)),
                        "$" + Util.formatearMiles(resultSet.getDouble(6)),
                        "$" + Util.formatearMiles(resultSet.getDouble(7)),
                        "$" + Util.formatearMiles(resultSet.getDouble(8))                        
                    });
                } while (resultSet.next());
            }
        } catch (SQLException e) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return lista;
    }
    
    
    

    /**
     * Metodo para  generar los datos recumenidos globales de las liquidaciones
     * que se han realizado a fecha actual.
     * @return array con posicion cero = Acumulado de galones,
     * posicion 1 = Acumulado de ganancias percibidas por combustible de tipo corriente.
     */
    public String[] calcularDatosGeneralesResumenLiquidacionCorriente() {
        /*lista inicial vacia para conservar los objetos cargados de BD*/
        String[] datosGenerales = new String[]{"0","0"};
        try {
            /*Query para cargar la informacion necesaria*/
            String query = ""
                    + "SELECT SUM(vrl.galonesCorriente), "
                    + "       SUM(vrl.gananciaCorriente) "
                    + "FROM view_resumenliquidacion vrl;";
 
            /*Ejecutar la consulta para obtener el set de datos*/
            ResultSet resultSet = Instance.getInstance().executeQuery(query);

            /*Capturar el resultado de la consulta*/
            if (resultSet != null && resultSet.first()) {
                do {
                    /*llenar la lista con los datos obtenidos*/
                    datosGenerales[0] = Util.formatearMiles(resultSet.getDouble(1)) + " gls.";
                    datosGenerales[1] = "$" + Util.formatearMiles(resultSet.getDouble(2));
                } while (resultSet.next());
            }
            
            if(resultSet != null && !resultSet.isClosed())
                resultSet.close();
        } catch (SQLException e) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return datosGenerales;
    }

    
    /**
     * Metodo para  generar los datos recumenidos globales de las liquidaciones
     * que se han realizado a fecha actual. ACPM
     * @return 
     */
    public String[] calcularDatosGeneralesResumenLiquidacionAcpm() {
        /*lista inicial vacia para conservar los objetos cargados de BD*/
        String[] datosGenerales = new String[]{"0","0","0"};
        try {
            /*Query para cargar la informacion necesaria*/
            String query = ""
                    + "SELECT SUM(v.aceites), "
                    + "       SUM(v.totalDineroAcpm),"
                    + "       SUM(v.total) "
                    + "FROM view_resumenAcpm v;";
 
            /*Ejecutar la consulta para obtener el set de datos*/
            ResultSet resultSet = Instance.getInstance().executeQuery(query);

            /*Capturar el resultado de la consulta*/
            if (resultSet != null && resultSet.first()) {
                do {
                    /*llenar la lista con los datos obtenidos*/
                    datosGenerales[0] = Util.formatearMiles(resultSet.getDouble(1)) + " gls.";
                    datosGenerales[1] = "$" + Util.formatearMiles(resultSet.getDouble(2));
                    datosGenerales[2] = "$" + Util.formatearMiles(resultSet.getDouble(3));
                } while (resultSet.next());
            }
            
            if(resultSet != null && !resultSet.isClosed())
                resultSet.close();
        } catch (SQLException e) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return datosGenerales;
    }

    
    /**
     * CArgar la lista de roles disponible para crear empleados
     * @return lista con los roles identificados
     */
    public ArrayList<Rol> cargarRoles() {
        /*lista inicial vacia para conservar los objetos cargados de BD*/
        ArrayList<Rol> listaRoles = new ArrayList<>();
        try {
            /*Query para cargar la informacion necesaria*/
            String query = ""
                    + "SELECT idRol,"
                    + "	      rol "
                    + "FROM roles "
                    + "WHERE idRol IN (3,4,6);";
 
            /*Ejecutar la consulta para obtener el set de datos*/
            ResultSet resultSet = Instance.getInstance().executeQuery(query);

            /*Capturar el resultado de la consulta*/
            if (resultSet != null && resultSet.first()) {
                do {
                    /*llenar la lista con los datos obtenidos*/
                    listaRoles.add(new Rol(
                            resultSet.getInt(1),
                            resultSet.getString(2)
                    ));
                } while (resultSet.next());
            }
            if(resultSet != null && !resultSet.isClosed())
                resultSet.close();
        } catch (SQLException e) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return listaRoles;
    }

    
    /**
     * Metodo que permite conocer si ya se ha realizado una liquidacion el dia actual.
     * @return true si ya se realizo una liquidacion en dia actual, false en caso contrario.
     */
    public boolean isLiquidacionHoy(){
        boolean tieneLiquidacionHoy = false;
        try {
            /*Query para cargar la informacion necesaria*/
            String query = ""
                    + "SELECT COUNT(1) "
                    + "FROM liquidaciones l "
                    + "WHERE DATEDIFF(DATE_FORMAT(DATE_ADD(CURRENT_DATE(),INTERVAL -1 DAY),'%Y%m%d'), DATE_FORMAT(l.fechaLiquidacion,'%Y%m%d')) = 0;";
 
            /*Ejecutar la consulta para obtener el set de datos*/
            ResultSet resultSet = Instance.getInstance().executeQuery(query);

            /*Capturar el resultado de la consulta*/
            if (resultSet != null && resultSet.first()) {
                do {
                    tieneLiquidacionHoy = (resultSet.getInt(1) > 0);
                } while (resultSet.next());
            }
            if(resultSet != null && !resultSet.isClosed())
                resultSet.close();
        } catch (SQLException e) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return tieneLiquidacionHoy;
    }

    
    /**
     * Metodo que permite insertar la informacion de pago de la liquidacion Extra.
     * @param liquidacionExtra 
     * @return  
     */
    public double insertarLiquidacionExtra(double[] liquidacionExtra) {
        double validate = 0;
        try {
            /*Query para actualizar el registro*/
            String insert = ""
                    + "CALL sp_insertar_liquidacion_extra('" + liquidacionExtra[0] +"',"
                    + " '" + liquidacionExtra[1] + "',"
                    + " '" + liquidacionExtra[2] + "',"
                    + " '" + liquidacionExtra[3] + "',"
                    + " '" + liquidacionExtra[4] + "',"
                    + " '" + liquidacionExtra[5] + "'"
                    + ");";
 
            /*Ejecutar la consulta para obtener el set de datos*/
            ResultSet resultSet = Instance.getInstance().executeQuery(insert);

            /*Capturar el resultado de la consulta*/
            if (resultSet != null && resultSet.first()) {
                validate = resultSet.getDouble(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return validate;
    }
    
    
    /**
     * cargar la lista de calibraciones mas actualizadas de cada surtidor
     * @return lista de calibraciones
     */
    public ArrayList<Calibraciones> obtenerUltimaCalibracionSurtidor(){

        ArrayList<Calibraciones> listaCalibraciones = new ArrayList<>();
        try {
            /*Query para capturar registros*/
            String insert = ""
                    + "SELECT 	c.idCalibracion, "
                    + "		c.idSurtidor, "
                    + "		c.galonesUsados,  "
                    + "		MAX(c.fecha), "
                    + "		c.descripcion "
                    + "FROM calibraciones c "
                    + "GROUP BY c.idSurtidor "
                    + "ORDER BY c.idSurtidor;";

            /*Ejecutar la consulta para obtener el set de datos*/
            ResultSet resultSet = Instance.getInstance().executeQuery(insert);

            /*Capturar el resultado de la consulta*/
            if (resultSet != null && resultSet.first()) {
                do {                    
                    listaCalibraciones.add(new Calibraciones(
                            resultSet.getLong(1),
                            resultSet.getInt(2),
                            resultSet.getDouble(3),
                            resultSet.getString(4),
                            resultSet.getString(5)
                    ));
                } while (resultSet.next());
            }
        } catch (SQLException e) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return listaCalibraciones;
    }

    
    /**
     * Genrar un resumen del movimiento de combustibles que se tienen 
     * hasta la fecha actual.
     * @param idSurtidor
     * @return 
     */
    public ArrayList<Object[]> generarResumenExistenciasCombustible(int idSurtidor) {
         /*lista inicial vacia para conservar los objetos cargados de BD*/
        ArrayList<Object[]> lista = new ArrayList<>();
        try {
            /*Query para cargar la informacion necesaria*/
            String query = ""
                    + "SELECT  	comprados,"
                    + "		vendidos,"
                    + "		existenciaGalones AS CombustibleAcumulado,"
                    + "		existenciaRegla AS medidaRegla,"
                    + "		diferencia,"
                    + "		fecha "
                    + "from view_control_existencias "
                    + "where surtidor = " + idSurtidor + " "
                    + "ORDER BY fecha;";
 
            /*Ejecutar la consulta para obtener el set de datos*/
            ResultSet resultSet = Instance.getInstance().executeQuery(query);

            /*Capturar el resultado de la consulta*/
            if (resultSet != null && resultSet.first()) {
                do {
                    /*llenar la lista con los datos obtenidos*/
                    lista.add(new Object[]{
                        Util.formatearMiles(resultSet.getDouble(1)),
                        Util.formatearMiles(resultSet.getDouble(2)),
                        Util.formatearMiles(resultSet.getDouble(3)),
                        Util.formatearMiles(resultSet.getDouble(4)),
                        Util.formatearMiles(resultSet.getDouble(5)),
                        resultSet.getString(6).trim()                       
                    });
                } while (resultSet.next());
            }
        } catch (SQLException e) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return lista;
    }
    
    
    /**
     * metodo para cargar la informacion de vendido y comrpado de un surtidor
     * @param idSurtidor
     * @param encabezadoResumenExistencias 
     */
    public void generarResumenEncabezadoExistencias(int idSurtidor, EncabezadoResumenExistencias encabezadoResumenExistencias) {
         
        try {
            /*Query para cargar la informacion necesaria*/
            String query = ""
                    + "SELECT SUM(v.vendidos),"
                    + "		 SUM(v.comprados) "
                    + "FROM view_control_existencias v "
                    + "WHERE v.surtidor = " + idSurtidor + " "
                    + "GROUP BY v.surtidor;";
 
            /*Ejecutar la consulta para obtener el set de datos*/
            ResultSet resultSet = Instance.getInstance().executeQuery(query);

            /*Capturar el resultado de la consulta*/
            if (resultSet != null && resultSet.first()) {
                do {
                    encabezadoResumenExistencias.setVendido(resultSet.getDouble(1));
                    encabezadoResumenExistencias.setComprado(resultSet.getDouble(2));                    
                } while (resultSet.next());
            }
        } catch (SQLException e) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /**
     * metodo que permite calcular la cantidad de combustible en existencia actualmente.
     * @param idSurtidor
     * @return 
     */
    public double calcularExistenciaActualSurtidor(int idSurtidor) {
        double existencia = 0;
        try {
            /*Query para actualizar el registro*/
            String insert = ""
                    + "SELECT g.existentes "
                    + "FROM galones g "
                    + "INNER JOIN ( "
                    + "	SELECT MAX(idGalon) id "
                    + "	FROM galones "
                    + "	GROUP BY idCilindro "
                    + ") b ON b.id = g.idGalon "
                    + "WHERE g.idCilindro = " + idSurtidor + ";";
 
            /*Ejecutar la consulta para obtener el set de datos*/
            ResultSet resultSet = Instance.getInstance().executeQuery(insert);

            /*Capturar el resultado de la consulta*/
            if (resultSet != null && resultSet.first()) {
                existencia = resultSet.getDouble(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return existencia;
    }

    
    /**
     * consultar los galones usados en la calibracion de un surtidor.
     * @param idSurtidor
     * @return 
     */
    public double cargarGalonesUsadosCalibracion(int idSurtidor) {
        
        double existencia = 0;
        try {
            /*Query para actualizar el registro*/
            String insert = ""
                    + "SELECT galonesUsados "
                    + "FROM calibraciones "
                    + "WHERE DATE_FORMAT(fecha,'%Y-%m-%d') = DATE_FORMAT(CURRENT_DATE(),'%Y-%m-%d') "
                    + "AND idSurtidor = " + idSurtidor + " LIMIT 1;";
 
            /*Ejecutar la consulta para obtener el set de datos*/
            ResultSet resultSet = Instance.getInstance().executeQuery(insert);

            /*Capturar el resultado de la consulta*/
            if (resultSet != null && resultSet.first()) {
                existencia = resultSet.getDouble(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return existencia;
    }

   
}