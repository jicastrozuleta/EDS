/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.servicentro.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

/**
 *
 * Clase donde se crean metodo genericos para utilidades especificas.
 *
 * @author JICZ4
 */
public class Util {

    /**
     * Metodo que redondea un numero a la cantidad de decimales especificada.
     *
     * @param numero numero que sera redondeado.
     * @param decimales decimales de precision.
     * @return el numero redondeado a su vecino mas cercano.
     */
    public static double round(double numero, int decimales) {
        try {
            DecimalFormat df = new DecimalFormat("#.#", DecimalFormatSymbols.getInstance(Locale.US));
            df.setRoundingMode(RoundingMode.HALF_UP);
            df.setMaximumFractionDigits(decimales);
            return Double.parseDouble(df.format(numero));
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            return Double.NaN;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Double.NaN;
        }
    }

    /**
     * Metodo que permite realizar la multiplicacion de billetes de una
     * denominacion por su cantidad y conocer el monto en dinero que hay en esa
     * denominacion.
     *
     * @param cantidad cantidad de billetes de una denominacion
     * @param denominacion denominacion del billete.
     * @return
     */
    public static double calcularMontoPorDenominacion(int cantidad, double denominacion) {
        return denominacion * cantidad;
    }

    /**
     * Metodo que permite validar si un texto es un numero valido.
     *
     * @param numero
     * @return true si el numero es valido, false en caso contrario.
     */
    public static boolean isNumeric(String numero) {
        try {
            Double.parseDouble(numero);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Formatear un numero para separar en miles, decenas, etc.
     *
     * @param number numero a formatear
     * @return un string con el formato de separacion especificado en Locale (es
     * CO)
     */
    public static String formatearMiles(double number) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("es", "CO"));
        return nf.format(number);
    }

    /**
     * Metodo para obtener la fecha actual del sistema
     *
     * @return
     */
    public static String getFechaActual() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("es", "CO"));
        return formateador.format(ahora);
    }

    /**
     * Dar un formato de texto a una fecha ingresada. 
     * ej: viernes, 13 diciembre 2017
     *
     * @param fecha
     * @return
     */
    public static String formatoTextoFecha(String fecha) {
        String newDate = "";
        try {
            /*capturar el formato de fecha ingresado*/
            SimpleDateFormat formatIn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date ahora = formatIn.parse(fecha);

            /*dar nuevo formato de fecha*/
            SimpleDateFormat formateador = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("es", "CO"));
            newDate = formateador.format(ahora);
        } catch (ParseException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newDate;
    }
    
    /**
     * Devuelve cadena con el nombre de la fecha ingresada
     * @param fecha
     * @return 
     */
    public static String devolverMesDeFecha(String fecha){
        String mes = "";
        try {
            /*capturar el formato de fecha ingresado*/
            SimpleDateFormat formatIn = new SimpleDateFormat("yyyy-MM-dd");
            Date ahora = formatIn.parse(fecha);

            /*dar nuevo formato de fecha*/
            SimpleDateFormat formateador = new SimpleDateFormat("MMMM", new Locale("es", "CO"));
            mes = formateador.format(ahora);
        } catch (ParseException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mes;
    }

    /**
     * Metodo para obtener la fecha actual del sistema
     *
     * @return
     */
    public static String getHoraActual() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("hh:mm a", new Locale("es", "CO"));
        return formateador.format(ahora);
    }
    
    /**
     * Generar el formato numerico decimal para las cajas de texto.
     * @return 
     */
    public static javax.swing.text.DefaultFormatterFactory formatoNumericoDecimal(){
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("es","CO"));
        symbols.setDecimalSeparator('.');
        symbols.setGroupingSeparator(','); 
        NumberFormat numberFormat = new DecimalFormat("#0.00#", symbols );
        NumberFormatter numberFormatter = new NumberFormatter(numberFormat);
        return(new javax.swing.text.DefaultFormatterFactory(numberFormatter));    
    }
}
