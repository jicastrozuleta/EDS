/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.servicentroguerrero.backup;

import co.com.servicentroguerrero.conexion.ConexionBD;
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que permite realizar backUp a una base de datos en motor mariaDB.
 * Basado en:
 * http://stackoverflow.com/questions/14924770/simple-backup-and-restore-for-mysql-database-from-java
 *
 * @author JICZ4
 */
public class BackUp {
    
    /**
     * Directorio que contiene los archivos de restauracion por defecto.
     * En este directorio se guardan los archivos de backup generados por 
     * el sistema de BackUp.
     */
    public static final String PATH_DAFULT_BACKUP = "C:\\Servicentro\\backup\\backUpServicentro";

    /**
     * fORMATO DE FECHA ACTUAL PARA COMPONER EL NOMBRE DEL ARCHIVO DE BACKUP
     */
    private static final String FORMATO_FECHA = "yyyy-MM-dd";

    /**
     * Directorio donde se almacena el archivo de backup en el pc local.
     */
    private static final String FOLDER_PATH = "C:\\Servicentro\\backup\\backUpServicentro";

    /**
     * Directorio donde esta el ejecutable mysqldump del motor de bd Mysql o
     * MariaDB
     */
    private static final String MYSQLDUMP_PATH = "C:\\Program Files\\MariaDB 10.1\\bin\\";

    /**
     * Metodo para generar un BackUp de la base de datos del proyecto actual
     * @return 
     */
    public static boolean generarBackUp() {
        boolean complete = false;
        try {

            /*NOTE: Creating Database Constraints*/
            String db = ConexionBD.getBD();
            String user = ConexionBD.getUSER();
            String pass = ConexionBD.getPASSWORD();

            /*NOTE: crear la carpeta si no existe*/
            File f1 = new File(FOLDER_PATH);
            f1.mkdir();
            /*Ruta del subproceso Mysqldump para ejecutar el backup*/
            File fileMysqlDump = new File(MYSQLDUMP_PATH);


            /*Generacion del nombre con la fecha del backuo generado*/
            String savePath = FOLDER_PATH + "\\backup_" + FechaActual(FORMATO_FECHA) + ".bkp";


            /*crear el comando que se ejecutara para generar el backUp*/
            String executeCmd = "mysqldump --user=" + user + " -p" + pass + " --routines " + db + " > " + savePath;
            String[] cmd = new String[]{"cmd.exe", "/C", executeCmd};
            /*Aqui se ejecuta el comando*/
            Process runtimeProcess = Runtime.getRuntime().exec(cmd, null, fileMysqlDump);

            /*Iniciar la lectura de buffer para garantizar que el proceso termine de forma completa*/
            BufferedReader stdOut = new BufferedReader(new InputStreamReader(runtimeProcess.getInputStream()));

            /*Ejecutar un while mientras el proceso termina de leer todas las lineas*/
            while ((stdOut.readLine()) != null) {
                //nope;
            }
            //Verificar si el proceso ha terminado completamente
            int processComplete = runtimeProcess.waitFor();
            /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
            if (processComplete == 0) {
                System.out.println("Backup Complete");
                complete = true;
            } else {
                System.out.println("Backup Failure");
            }
        } catch (IOException ex) {
            System.out.println("Error realizando el backup " + ex.getMessage());
        } catch (InterruptedException ex) {
            Logger.getLogger(BackUp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return complete;
    }

    
    /**
     * 
     * @param fileRestorePath
     */
    public static boolean restaurarBackup(String fileRestorePath) {
        boolean complete = false;
        try {
             /*NOTE: Creating Database Constraints*/
            String db = ConexionBD.getBD();
            String user = ConexionBD.getUSER();
            String pass = ConexionBD.getPASSWORD();
            
            /*Ruta del subproceso Mysqldump para ejecutar el backup*/
            File fileMysqlDump = new File(MYSQLDUMP_PATH);
            
            
            /*crear el comando que se ejecutara para generar el backUp*/
            String executeCmd = "mysql --user=" + user + " --password=" + pass + " " + db + " < " + fileRestorePath;
            System.out.println(executeCmd);
            String[] cmd = new String[]{"cmd.exe", "/C", executeCmd};

            /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
            Process runtimeProcess = Runtime.getRuntime().exec(cmd, null, fileMysqlDump);
            
            /*Iniciar la lectura de buffer para garantizar que el proceso termine de forma completa*/
            BufferedReader stdOut = new BufferedReader(new InputStreamReader(runtimeProcess.getInputStream()));

            /*Ejecutar un while mientras el proceso termina de leer todas las lineas*/
            while ((stdOut.readLine()) != null) {
                //nope;
            }
            //Verificar si el proceso ha terminado completamente
            int processComplete = runtimeProcess.waitFor();

            /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
            if (processComplete == 0) {
                System.out.println("Successfully restored from SQL : " + fileRestorePath);
                complete = true;
            } else {
                System.out.println("Error at restoring");
            }
        } catch (HeadlessException ex) {
            System.out.println("Error at Restoredbfromsql" + ex.getMessage());
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(BackUp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return complete;
    }

    /**
     * Obtener la fecha actual en formato establecido
     *
     * @param format
     * @return
     */
    public static String FechaActual(String format) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
}