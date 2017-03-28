/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.servicentroguerrero.controler;

import co.com.servicentroguerrero.modelos.Liquidacion;
import co.com.servicentroguerrero.modelos.LiquidacionDispensador;
import java.util.List;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

/**
 *
 * @author JICZ4
 */
public class WorkerBO extends SwingWorker<Boolean, String> {

    /**
     * Progressbar que ira mostrando el avance la insercion en la BD.
     */
    private JProgressBar progressBar;
    /**
     * identificador del empleado que genera la liquidacion
     */
    private final int idEmpleadoLiquidador;

    /**
     * total del monto en combustibles
     */
    private final double totalCombustibles;

    /**
     * total del monto en aceites
     */
    private final double totalAceites;

    /**
     * total del monto liquidado
     */
    private final double totalLiquidado;

    /**
     * total de dinero entregado por el islero
     */
    private final double dineroEntregado;

    /**
     * diferencia entre el dinero entregado y el monto liquidado
     */
    private final double diferencia;

    /**
     * array con los datos calculado de la liquidacion de cada dispensador.
     */
    private final String liquidacionesPorDispensador;

    /**
     * Constructor para istanciar el worker que se encargara de insertar en base
     * de datos la liquidacion. ademas de mostrar al usuario el avance de la
     * transaccion.
     *
     * @param idEmpleadoLiquidador
     * @param totalCombustibles
     * @param totalAceites
     * @param totalLiquidado
     * @param dineroEntregado
     * @param diferencia
     * @param liquidacionesPorDispensador
     */
    public WorkerBO(int idEmpleadoLiquidador, double totalCombustibles, double totalAceites, double totalLiquidado, double dineroEntregado, double diferencia, String liquidacionesPorDispensador) {
        this.idEmpleadoLiquidador = idEmpleadoLiquidador;
        this.totalCombustibles = totalCombustibles;
        this.totalAceites = totalAceites;
        this.totalLiquidado = totalLiquidado;
        this.dineroEntregado = dineroEntregado;
        this.diferencia = diferencia;
        this.liquidacionesPorDispensador = liquidacionesPorDispensador;
    }

    @Override
    protected Boolean doInBackground() throws Exception {

        publish("Creando cabecera de liquidacion...");
        /*crear la cabecera de liquidacion, que sera insertada en la tabla Liquidaciones*/
        if (generarCabecera() != Long.MIN_VALUE) {
            Thread.sleep(2000);
            return true;
        } else {
            publish("Error creando cabecera de liquidacion, intente nuevamente");
            Thread.sleep(2000);
            return false;
        }
    }

    @Override
    protected void process(List<String> chunks) {
        super.process(chunks); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Crear la cabecera de liquidacion
     *
     * @return numeroLIquidacion
     */
    private long generarCabecera() {
        /*crear el objeto liquidacion con los datos basicos para insertar en la base de datos*/
        Liquidacion liquidacion = new Liquidacion(0,
                this.idEmpleadoLiquidador,
                this.totalCombustibles,
                this.totalAceites,
                this.totalLiquidado,
                this.dineroEntregado,
                this.diferencia,
                null
        );
        /*solicitar a la base de datos un begin transaction*/
        return ControllerBO.insertarCabeceraDeLiquidacion(liquidacion, this.liquidacionesPorDispensador);
    }
    
    
    
    

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(JProgressBar progressBar) {
        this.progressBar = progressBar;
    }
}
