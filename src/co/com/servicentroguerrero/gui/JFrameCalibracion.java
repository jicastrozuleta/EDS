/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.servicentroguerrero.gui;

import co.com.servicentro.util.DateLabelFormatter;
import co.com.servicentroguerrero.controler.ControllerBO;
import java.util.Properties;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

/**
 *
 * @author JICZ4
 */
public class JFrameCalibracion extends javax.swing.JFrame {

    /**
     *
     * Constante para indicar una posicion por defecto en combobox.
     */
    private static final int POSITION_DEFAULT = 0x00;

    /**
     * Creates new form JFrameCalibracion
     */
    public JFrameCalibracion() {
        initComponents();
        mostrarPicker();
        cargarSurtidores();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelCalibracion = new javax.swing.JPanel();
        jButtonIngresarCalibracion = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jComboBoxSurtidores = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanelPicker = new javax.swing.JPanel();
        jPanelFecha = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Calibracion");
        getContentPane().setLayout(new java.awt.GridLayout(1, 1, 1, 1));

        jButtonIngresarCalibracion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButtonIngresarCalibracion.setText("Ingresar Calibracion");
        jButtonIngresarCalibracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIngresarCalibracionActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de Calibracion", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jComboBoxSurtidores.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Galones Usados: ");

        jTextField1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jPanelPicker.setLayout(new java.awt.GridLayout(1, 1));

        jPanelFecha.setLayout(new java.awt.GridLayout(1, 1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Fecha:");
        jPanelFecha.add(jLabel2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBoxSurtidores, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanelFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                            .addComponent(jPanelPicker, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBoxSurtidores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanelPicker, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelCalibracionLayout = new javax.swing.GroupLayout(jPanelCalibracion);
        jPanelCalibracion.setLayout(jPanelCalibracionLayout);
        jPanelCalibracionLayout.setHorizontalGroup(
            jPanelCalibracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCalibracionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCalibracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonIngresarCalibracion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelCalibracionLayout.setVerticalGroup(
            jPanelCalibracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCalibracionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonIngresarCalibracion, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        getContentPane().add(jPanelCalibracion);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonIngresarCalibracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIngresarCalibracionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonIngresarCalibracionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonIngresarCalibracion;
    private javax.swing.JComboBox<String> jComboBoxSurtidores;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelCalibracion;
    private javax.swing.JPanel jPanelFecha;
    private javax.swing.JPanel jPanelPicker;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

    
    /**
     * cargar los surtidores disponibles para hacer la calibracion.
     */
    private void cargarSurtidores() {
        /*Marcar posicion por defecto y asignarla al combo*/
        jComboBoxSurtidores.insertItemAt("Seleccione Surtidor - ", POSITION_DEFAULT);
        jComboBoxSurtidores.setSelectedIndex(POSITION_DEFAULT);

        /*Agregar los items con los datos de los empleados con rol islero.*/
        ControllerBO.cargarListaSurtidores().forEach((surtidor) -> {
            jComboBoxSurtidores.addItem(surtidor.getCodigoIdentificador());
        });
    }

    /**
     * MOstrar el picker en la ventana de calibracion
     */
    private void mostrarPicker() {
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        /*Propiedades para idioma español*/
        p.put("text.today", "hoy ");
        p.put("text.month", "mes");
        p.put("text.year", "a\u00f1o");
        p.put("text.clear", "Clear");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        jPanelPicker.add(datePicker);
    }
}
