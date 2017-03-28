/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.servicentroguerrero.conexion;

import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JICZ4
 */
public final class Instance {

    /**
     * Instancia para conexion a BD
     */
    private static Statement instance;

    /**
     * Conexion Driver JDBC para conexion al motor de BD
     */
    private static ConexionBD conexion;

    /**
     * COnstructor vacio
     */
    public Instance() {

    }

    /**
     * Generador de instancia unica para la conexcion a BD. Se intenta controlas
     * la creacion desde app multihilo haceindo uso de la palabra reservada
     * synchronized. Ademas se hace uso del patron Singleton para garantizar
     * unica instancia.
     */
    private synchronized static void createInstance() {
        try {
            if (instance == null) {
                close();
                Instance.conexion = new ConexionBD();
                setInstance(conexion.connect());
            }
        } catch (Exception ex) {
            Logger.getLogger(Instance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    /**
     * crear la instancia que sera usada durante todo el ciclo de vida de la aplicacioin
     * @param instance 
     */
    private static void setInstance(Statement instance) {
        Instance.instance = instance;
    }

    /**
     * Devuelve la instancia Statement, desde la cual se pueden ejecutar las
     * sentencias SQL en la BD.
     *
     * @return
     */
    public static Statement getInstance() {
        if (Instance.instance == null) {
            Instance.createInstance();
        }
        return Instance.instance;
    }

    /**
     * Permite desconectar la base de datos y dejar la conexion y la instancia
     * actuales a NULL
     */
    public static void close() {
        // Quitar instancia actual y dejar en null
        if (Instance.instance != null) {
            Instance.instance = null;
        }
        //Cerrar conexion actual y dejarla en null
        if (Instance.conexion != null) {
            Instance.conexion.close();
        }
        Instance.conexion = null;
    }

    public static ConexionBD getPrivateConexion() {
        if(conexion != null)
            return conexion;
        else
            return null;
    }
}
