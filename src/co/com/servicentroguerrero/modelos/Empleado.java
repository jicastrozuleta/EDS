/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.servicentroguerrero.modelos;

import java.io.Serializable;

/**
 *
 * @author JICZ4
 */
public class Empleado implements Serializable{
    
    /**
     * Constante para identificar empleados con rol de islero
     */
    public static final String ISLERO = "ISLERO";
    
    /**
     * Constante para identificar empleados con rol de administrador
     */
    public static final String ADMINISTRADOR = "ADMINISTRADOR";
    
    /**
     * Constante para identificar empleados con rol de secretaria
     */
    public static final String SECRETARIA = "SECRETARIA";
    
    /**
     * Identificador unico de empleado PK
     */
    private int idEmpleado;
    
    /**
     * Identificador unico del EDs donde es empleado.
     */
    private int idEds;
    
    /**
     * 1 = es empleado activo.
     * 0 = es empleado inactivo.
     */
    private int activo;
    
    /**
     * numero de identificacion del empleado Cedula.
     */
    private String identificacion;
    
    /**
     * nombre del empleado
     */
    private String nombres;
    
    /**
     * apellidos del empleado.
     */
    private String apellidos;
    
    /**
     * telefono del contacto del empleado
     */
    private String telefono;
    
    /**
     * direccion de residencia
     */
    private String direccion;
    
    /**
     * Identificador unico del rol que desempeña
     */
    private int idRol;
    
    /**
     * Descripcion del rol que desempeña.
     */
    private String rol;

    /**
     * Constructor recomendado para instanciar el objeto.
     * @param idEmpleado
     * @param idEds
     * @param activo
     * @param identificacion
     * @param nombres
     * @param apellidos
     * @param telefono
     * @param direccion
     * @param idRol
     * @param rol 
     */
    public Empleado(int idEmpleado, int idEds, int activo, String identificacion, String nombres, String apellidos, String telefono, String direccion, int idRol, String rol) {
        this.idEmpleado = idEmpleado;
        this.idEds = idEds;
        this.activo = activo;
        this.identificacion = identificacion;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.direccion = direccion;
        this.idRol = idRol;
        this.rol = rol;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdEds() {
        return idEds;
    }

    public void setIdEds(int idEds) {
        this.idEds = idEds;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}