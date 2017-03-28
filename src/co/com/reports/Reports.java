/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.reports;

import co.com.servicentroguerrero.conexion.Instance;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;


/**
 *
 * @author JICZ4
 */
public class Reports {
    
      public void toPDF(){

          try {
              
              /*archivo jasper*/
              String aJasper = "D:\\ProyectoEstacionServicio\\ServicentroGuerrero\\ServicentroGuerrero\\src\\co\\com\\reports\\report.jasper";
              /*genera instancia de BD*/
              Instance.getInstance();
              HashMap hm = new HashMap();
              // fills compiled report with parameters and a connection
              JasperPrint print = JasperFillManager.fillReport(aJasper, hm, Instance.getPrivateConexion().getConexion());
              // exports report to pdf
              JRExporter exporter = new JRPdfExporter();
              exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
              exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, new FileOutputStream(aJasper + ".pdf")); // your output goes here

              exporter.exportReport();
          } catch (JRException ex) {
              Logger.getLogger(Reports.class.getName()).log(Level.SEVERE, null, ex);
          } catch (FileNotFoundException ex) {
              Logger.getLogger(Reports.class.getName()).log(Level.SEVERE, null, ex);
          }
    }
    
}
