package co.com.servicentroguerrero.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que permite una conexion a la base de datos. MariaDB. Se hace uso de
 * DriverManager para geenrar una coexion local a la base de datos
 *
 * @author JICZ4
 */
public class ConexionBD {

    private Statement sentencia;
    private static final String BD = "servicentro_guerrero";
    private static final String USER = "servicentro";
    private static final String PASSWORD = "87sErv01";
    private static final String SERVER = "jdbc:mariadb://localhost:3306/" + BD;
    private Connection conexion;

    /**
     * Metodo que retorna una conexion a la instancia de la base de datos.
     *
     * @return
     * @throws Exception
     */
    public Statement connect() throws Exception {

        // Se carga el driver JDBC-ODBC
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("No se cargo JDBC-ODBC.");
            return null;
        }// Cierra el catch

        try {
            // Se establece la conexiOn con la base de datos
            conexion = DriverManager.getConnection(SERVER, USER, PASSWORD);
            // Se crea un objeto Statement.
            sentencia = conexion.createStatement();
            return sentencia;
        } catch (SQLException e) {
            throw new Exception("No se logra conexion a la base de datos. Verificar si el motor esta en ejecucion...");
        }
    }

    /**
     * Permite cerrar una conexion con la base de datos.
     */
    public void close() {
        try {
            this.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error desconectando BD -> " + ex.getMessage());
        }
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public static String getBD() {
        return BD;
    }

    public static String getUSER() {
        return USER;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }    
    
}
