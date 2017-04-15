/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.reports;

import co.com.servicentroguerrero.conexion.Instance;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

/**
 *
 * @author JICZ4
 */
public class Reports {
    
    /**
     * Archivo con el compilado del reporte JasperReports
     */
    private static final String JASPER = "C:\\Servicentro\\jasperReport\\liquidacionDiaria.jasper";

    /**
     * Directorio de salida donde se guarda el reporte
     */
    private static final String DIR_OUT = "C:\\Servicentro\\report\\reporte_lqd_";
    
    
    /**
     * extencion del archivo generado en el reporte
     */
    private static final String EXTENCION = ".pdf";
    
    /**
     * Metodo que genera el reporte de liquidacion diaria.
     * @param fechaReporte fecha en que se realizo la liquidacion
     * @throws JRException
     * @throws FileNotFoundException
     * @throws Exception 
     */
    public void liquidacionDiariaToPDF(String fechaReporte) throws JRException, FileNotFoundException, Exception {

        /*Directorio de salida del reporte generado*/
        String pathToPDF = DIR_OUT + fechaReporte + EXTENCION;
        /*genera instancia de BD*/
        Instance.getInstance();
        LiquidacionDiariaReport ldr = new LiquidacionDiariaReport();
        HashMap hm = ldr.reporteLiquidacionDispensador(fechaReporte);

        /*validar que si hay una liquidacion para generar el reporte*/
        if (hm == null || hm.isEmpty()) {
            throw new Exception("No hay liquidacioin en el dia seleccionado");
        }

        // fills compiled report with parameters and a connection
        JasperPrint print = JasperFillManager.fillReport(JASPER, hm, Instance.getPrivateConexion().getConexion());

        /*Exportar a tipo PDF*/
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(print));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pathToPDF));
        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
        exporter.setConfiguration(configuration);
        exporter.exportReport();

        /*Abrir el reporte generado*/
        abrirDocumento(pathToPDF);
    }

    
    /**
     * Intentar barir el reporte PDF.
     *
     * @param pathToPDF dir, al reporte en pdf
     */
    private void abrirDocumento(final String pathToPDF) throws Exception, IOException, InterruptedException {

        /*si el archivo existe se intenta abrir con la app configurada por defecto
        en el sistema operativo para abrir PDF */
        if ((new File(pathToPDF)).exists()) {
            Process p = Runtime
                    .getRuntime()
                    .exec("rundll32 url.dll,FileProtocolHandler " + pathToPDF);
            p.waitFor();
        } else {
            throw new Exception("El archivo no existe.");
        }
    }
}
