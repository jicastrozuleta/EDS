/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.servicentroguerrero.controler;

import co.com.formulavolumen.VolumenCombustible;
import co.com.reports.Reports;
import co.com.servicentro.util.Util;
import co.com.servicentroguerrero.backup.BackUp;
import co.com.servicentroguerrero.model.Model;
import co.com.servicentroguerrero.modelos.Calibraciones;
import co.com.servicentroguerrero.modelos.Cilindro;
import co.com.servicentroguerrero.modelos.Combustible;
import co.com.servicentroguerrero.modelos.Empleado;
import co.com.servicentroguerrero.modelos.Existencias;
import co.com.servicentroguerrero.modelos.Liquidacion;
import co.com.servicentroguerrero.modelos.LiquidacionDispensador;
import co.com.servicentroguerrero.modelos.Surtidores;
import co.com.servicentroguerrero.modelos.Volumenes;
import java.io.File;
import java.util.ArrayList;

/**
 * Controlador donde se valida la logica de negocio para comunicacion entre BD y
 * vistas
 *
 * @author JICZ4
 */
public class ControllerBO {

    /**
     * Modelo de conexcion a la base de datos.
     */
    private static final Model MODELO = new Model();

    /**
     * Generar el backup de la BD.
     *
     * @return true si se genera correctamente, false en caso contrario.
     */
    public static boolean generarBackUp() {
        return BackUp.generarBackUp();
    }

    /**
     * Metodo que permite restaurar la BD a partir de un archivo de BackUp.
     *
     * @param path
     * @throws Exception
     */
    public static void restaurarBackUp(String path) throws Exception {

        /*Validar que la ruta del archivo sea valida.*/
        if (path == null || path.length() == 0) {
            throw new Exception("Ruta del archivo no valida.");
        }

        /*cargar el archivo y restaurar o generar excepcion si el archivo no existe*/
        File file = new File(path);
        if (file.exists()) {
            BackUp.restaurarBackup(file.getAbsolutePath());
        } else {
            throw new Exception("El archivo de BackUp no existe.");
        }
    }

    /**
     * Metodo para insertar volumenes de combustible calculados por el usuario.
     *
     * @param codigoSurtidor
     * @param medidaReglaMojada
     * @return el volumen insertado en la base de datos.
     * @throws Exception si hay datos null.
     */
    public static double insertarVolumenes(String codigoSurtidor, double medidaReglaMojada) throws Exception {

        /*Validar informacion de codigo de cilindro*/
        if (codigoSurtidor == null || codigoSurtidor.length() == 0) {
            throw new Exception("Codigo de surtidor no valido");
        }

        /*Validar la medida de la regla, profundidad de combustible*/
        if (medidaReglaMojada == 0 || medidaReglaMojada == Double.NaN) {
            throw new Exception("Codigo de surtidor no valido");
        }

        /*Objeto cilindro al que se le calcula el volumen*/
        Cilindro cilindro = MODELO.buscarCilindroPorCodigoSurtidor(codigoSurtidor);

        /*Validar el estado del objeto*/
        if (cilindro == null) {
            throw new Exception("Error Cargando Cilindro del surtidor.");
        }

        /**
         * Validar que el objeto este completo
         */
        if (cilindro.getCodigo() == null
                || cilindro.getCombustible() == null
                || cilindro.getIdCilindro() == 0
                || cilindro.getRadio() == 0
                || cilindro.getVolumenFijo() == 0
                || cilindro.getLongitud() == 0) {
            throw new Exception("Error cargando datos del cilindro.");
        }

        /*crear el objeto que realiza los calculos de volumen*/
        VolumenCombustible v = new VolumenCombustible();
        /*pasar a metros la medida de la regla*/
        medidaReglaMojada /= 100;
        double volumen = v.calcularVolumen(medidaReglaMojada, cilindro.getLongitud(), cilindro.getRadio());
        /*Validar que la medida de regla sea valida*/
        if (volumen == Double.NaN) {
            throw new Exception("El valor de lectura de regla no es valido.");
        }
        Volumenes volumenes = new Volumenes(Long.MIN_VALUE, cilindro.getIdCilindro(), Util.round(volumen, 7), null);

        /*Insertar la calibracion*/
        long rowid = MODELO.insertarVolumenes(volumenes);
        if (rowid < 0) {
            throw new Exception("Error insertando volumen.");
        } else {
            return Util.round(volumen, 7);
        }
    }

    /**
     * Metodo para insertar una calibracion de un surtidor
     *
     * @param idSurtidor
     * @param galonesUsados
     * @param descripcion
     * @return el rowid del registro incertado en la BD.
     * @throws Exception
     */
    public static long insertarCalibracion(int idSurtidor, double galonesUsados, String descripcion) throws Exception {

        /*verificar que el surtidor sea valido*/
        if (idSurtidor <= 0) {
            throw new Exception("El surtidor no es valido.");
        }

        /*verificar que la descripcion no supere 500 caracteres*/
        if (descripcion.length() > 499) {
            descripcion = descripcion.substring(0, 499);
        }

        /*Crear la calibracion*/
        Calibraciones calibraciones = new Calibraciones(Long.MIN_VALUE, idSurtidor, galonesUsados, null, descripcion);

        /*Insertar la calibracion*/
        long rowid = MODELO.insertarCalibracion(calibraciones);
        if (rowid < 0) {
            throw new Exception("Error insertando calibracion.");
        } else {
            return rowid;
        }
    }

    /**
     * cargar la lista de combustibles disponibles por el EDS
     *
     * @return
     */
    public static ArrayList<Combustible> cargarCombustibles() {
        return MODELO.cargarCombustibles();
    }

    /**
     * Buscar un combustible por tipo de combustible.
     *
     * @param tipoCombustible
     * @return Combustible
     * @throws java.lang.Exception si el combustible es null;
     */
    public static Combustible cargarCombustiblePorTipo(final String tipoCombustible) throws Exception {
        Combustible combustible = null;
        for (Combustible c : ControllerBO.cargarCombustibles()) {
            if (c.getCombustible().contains(tipoCombustible)) {
                combustible = c;
            }
        }
        if(combustible == null)
            throw new Exception("Error cargando precios de combustibles.");
        return combustible;
    }

    /**
     * metodo que permite actualiza el precio de un combustible
     *
     * @param idCombustible
     * @param nuevoPrecio
     * @return true si es actualizado, false en caso contrario.
     * @throws Exception si los datos de entrada no son validos
     */
    public static boolean actualizarPrecioCombustible(int idCombustible, double nuevoPrecio) throws Exception {

        /*validar que el combustible es valido*/
        if (idCombustible < 1 || idCombustible == Integer.MIN_VALUE) {
            throw new Exception("El combustible no es valido.");
        }

        /*validar que el precio sea valido*/
        if (nuevoPrecio == 0 || nuevoPrecio == Double.NaN) {
            throw new Exception("El precio no es valido.");
        }

        /*actualizar el precio del combustible y verifica si se inserta correctamente*/
        return MODELO.actualizarPrecioCombustible(idCombustible, nuevoPrecio) == 1;
    }

    /**
     * Metodo para cargar una lista con los empleados correspondientes al rol de
     * isleros. la lista contiene empleados activos.
     *
     * @return lista de empleados o lista vacia si no hay empleados con este rol
     * disponibles.
     */
    public static ArrayList<Empleado> cargarListaEmpleadosIsleros() {
        return MODELO.cargarEmpleadosPorRol(Empleado.ISLERO);
    }
    
    
    /**
     * Crear una lista con los surtidores disponibles
     * @return 
     */
    public static ArrayList<Surtidores> cargarListaSurtidores() {
        return MODELO.cargarSurtidores();
    }
    
    /**
     * Crear una lista con los surtidores disponibles sin medida de regla
     * @return 
     */
    public static ArrayList<Surtidores> cargarSurtidoresSinMedidaRegla() {
        return MODELO.cargarSurtidoresSinMedidaRegla();
    }
    
    
    /**
     * Metodo para cargar la ultima liquidacion disponible.
     * @return Liquidacion
     */
    public static Liquidacion cargarUltimaLiquidacion(){
        return MODELO.cargarUltimaLiquidacion();
    }
    
    /**
     * Cargar los detalles por dispensador de la ultima liquidacion.
     * @param liquidacion
     * @return 
     */
    public static ArrayList<LiquidacionDispensador> cargarLiquidacionDispensadores(Liquidacion liquidacion){
        return MODELO.cargarDetallesUltimaLiquidacion(liquidacion);
    }
    
    
    /**
     * Insertar la cabecera de liquidacion en la base de datos.
     * @param liquidacion
     * @param liquidacionesPorDispensador string donde estan concatenados todos los datos de liquidacion de cada dispensador.
     * @return el numeroLiquidacion para insertar los detalles de la liquidacion actual, o Long.MIN_VALUE si hay valores no validos
     */
    public static long insertarCabeceraDeLiquidacion(final Liquidacion liquidacion, final String liquidacionesPorDispensador){
        /*validar que los datos de la liquidacion basicos sean validos*/
//        if(liquidacion.getIdEmpleadoLiquidador() <= 0 || liquidacion.getTotalCombustibles() == 0 || liquidacion.getTotalLiquidado() == 0 || liquidacion.getDineroEntregado() == 0)
//            return Long.MIN_VALUE;
//        else
            return MODELO.insertarCabeceraDeLiquidacion(liquidacion, liquidacionesPorDispensador);
    }
    
    
   /**
    * Metodo para iniciar la autenticacion de usuarios.
    * @param user
    * @param password
    * @return
    * @throws Exception 
    */ 
    public static Empleado login(String user, String password) throws Exception{
        if(user == null || user.length() == 0)
            throw new Exception("Usuario invalido.");
        if (password == null || password.length() == 0)
            throw new Exception("Contraseña invalido.");
        
        /*intentar cargar el usuario*/
        Empleado empleado = MODELO.login(user, password);
        
        if(empleado == null)    
            throw new Exception("Usuario o Contraseña invalidos.");
        else
            return empleado;
    }
    
    /**
     * Registrar un nuevo empleado
     * @param empleado
     * @param usuario
     * @param password
     * @return true si el empleado fue ingresado correctamente, false en caso contrario.
     * @throws Exception si alguno de los datos del empleado no son correctos.
     */
    public static boolean guardarEmpleado(Empleado empleado, String usuario, String password) throws Exception{
        
        if (empleado == null) 
            throw new Exception("Empleado no valido.");
        
        if (empleado.getIdentificacion() == null || empleado.getIdentificacion().length() == 0) 
            throw new Exception("Ingrese la identificacion del empleado");
        
        if (empleado.getNombres() == null || empleado.getNombres().length() == 0) 
            throw new Exception("Ingrese nombres del empleado");
        
        if (empleado.getApellidos() == null || empleado.getApellidos().length() == 0) 
            throw new Exception("Ingrese apellidos del empleado");
        
        if (empleado.getTelefono() == null || empleado.getTelefono().length() == 0) 
            throw new Exception("Ingrese numero de contacto del empleado");
        
        if (empleado.getDireccion() == null || empleado.getDireccion().length() == 0) 
            throw new Exception("Ingrese direccion del empleado");
        
        /* si se ingresa un usuario validar que se tenga una contraseña valida.*/
        if(usuario != null && usuario.length() > 0) {
            if(password == null || password.length() == 0)
                throw new Exception("Ingrese la contraseña del usuario.");
        }
        /*intentar la insercion en base de datos*/
        return MODELO.insertarEmpleado(empleado, usuario, password);
    }

    /**
     * BUscar un empleado por su numero de identificacion.
     * @param identificacion
     * @return objeto empleado con los datos encontrados.
     */
    public static Empleado buscarEmpleadoPorIdentificacion(final String identificacion) {
        if(identificacion != null && identificacion.length() > 0)
            return MODELO.buscarEmpleadoPorIdentificacion(identificacion);
        else
            return null;
    }

    /**
     * metodo para desactivar empleados del sistema.
     * @param empleadoEliminar
     * @return true si el empleado fue desactivado, false en caso contrario.
     */
    public static boolean desactivarEmpleado(final Empleado empleadoEliminar) {
        /*validar que el empleado se un objeto valido*/
        if(empleadoEliminar != null && empleadoEliminar.getIdEmpleado() > 0)
            return MODELO.desactivarEmpleado(empleadoEliminar.getIdEmpleado());
        else
            return false;
    }
    
    
    /**
     * generar el reporte de liquidacion diaria de la fecha seleccioanda
     * @param fechaReporte fecha del reporte.
     * @throws Exception si se genera algun error generando el reporte
     */
    public static void generarReporteLiquidacionDiaria(final String fechaReporte) throws Exception{
        if(fechaReporte == null || fechaReporte.length() == 0)
            throw new Exception("Ingrese la fecha para generar el reporte.");
        /*Iniciar la ejecucion del reporte*/
        Reports reports = new Reports();
        reports.liquidacionDiariaToPDF(fechaReporte);
    }
    
    
    /**
     * cargar la existencias de combustible que se han ingresado el dia actual
     * @return lista con las existencias registradas el dia actual, o una lista vacia si no se 
     * ha realizado registros de medida de regla mojada.
     */
    public static ArrayList<Existencias> cargarExistenciasDeCombustible(){
        return MODELO.cargarExistenciasDeCombustible();
    }

    
    /**
     * Metodo encargado de realizar el registro de los movimientos de combustibles del dia.
     * @param listaExistencias 
     */
    public static void registrarMovimientoDeCombustibles(final ArrayList<Existencias> listaExistencias) {
        if(listaExistencias != null && !listaExistencias.isEmpty())
            MODELO.registrarMovimientoDeExistenciasDeCombustibles(listaExistencias);
    }
}
