/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.servicentroguerrero.gui;

import co.com.servicentroguerrero.controler.ControllerBO;
import co.com.servicentroguerrero.modelos.Empleado;
import java.awt.Color;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showConfirmDialog;

/**
 *
 * @author JICZ4
 */
public class JFrameEliminarEmpleados extends javax.swing.JFrame {

    /**
     * objeto que representa al empleado que se pretende desactivar del sistema
     */
    private Empleado empleadoEliminar = null;

    /**
     * Interface para actualizar datos de ventana.
     */
    private IRefresh refresh;
    /**
     * Creates new form JFrameAgregarEmpleados
     * @param iRefresh
     */
    public JFrameEliminarEmpleados(IRefresh iRefresh) {
        this.refresh = iRefresh;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelAgregarEmpleado = new javax.swing.JPanel();
        jButtonEliminar = new javax.swing.JButton();
        jPanelDatosPersonales = new javax.swing.JPanel();
        jPanelIngresoDatos = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldNombres = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldApellidos = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldTelefono = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldDireccion = new javax.swing.JTextField();
        jLabelEstado = new javax.swing.JLabel();
        jPanelBuscar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldIdentificacion = new javax.swing.JTextField();
        jButtonBuscar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 1, 1, 1));

        jButtonEliminar.setText("Eliminar");
        jButtonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarActionPerformed(evt);
            }
        });

        jPanelDatosPersonales.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos Personales", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jPanelIngresoDatos.setLayout(new java.awt.GridLayout(5, 2, 3, 3));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Nombres:");
        jPanelIngresoDatos.add(jLabel2);

        jTextFieldNombres.setEditable(false);
        jTextFieldNombres.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldNombres.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextFieldNombres.setEnabled(false);
        jPanelIngresoDatos.add(jTextFieldNombres);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Apellidos:");
        jPanelIngresoDatos.add(jLabel3);

        jTextFieldApellidos.setEditable(false);
        jTextFieldApellidos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldApellidos.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextFieldApellidos.setEnabled(false);
        jPanelIngresoDatos.add(jTextFieldApellidos);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Telefono:");
        jPanelIngresoDatos.add(jLabel4);

        jTextFieldTelefono.setEditable(false);
        jTextFieldTelefono.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldTelefono.setEnabled(false);
        jPanelIngresoDatos.add(jTextFieldTelefono);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Direccion:");
        jPanelIngresoDatos.add(jLabel5);

        jTextFieldDireccion.setEditable(false);
        jTextFieldDireccion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldDireccion.setEnabled(false);
        jPanelIngresoDatos.add(jTextFieldDireccion);

        jLabelEstado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelEstado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanelIngresoDatos.add(jLabelEstado);

        javax.swing.GroupLayout jPanelDatosPersonalesLayout = new javax.swing.GroupLayout(jPanelDatosPersonales);
        jPanelDatosPersonales.setLayout(jPanelDatosPersonalesLayout);
        jPanelDatosPersonalesLayout.setHorizontalGroup(
            jPanelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDatosPersonalesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelIngresoDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelDatosPersonalesLayout.setVerticalGroup(
            jPanelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDatosPersonalesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelIngresoDatos, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Identificacion:");

        jTextFieldIdentificacion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jButtonBuscar.setText("Buscar");
        jButtonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelBuscarLayout = new javax.swing.GroupLayout(jPanelBuscar);
        jPanelBuscar.setLayout(jPanelBuscarLayout);
        jPanelBuscarLayout.setHorizontalGroup(
            jPanelBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBuscarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldIdentificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonBuscar))
        );
        jPanelBuscarLayout.setVerticalGroup(
            jPanelBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBuscarLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jPanelBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonBuscar)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextFieldIdentificacion))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelAgregarEmpleadoLayout = new javax.swing.GroupLayout(jPanelAgregarEmpleado);
        jPanelAgregarEmpleado.setLayout(jPanelAgregarEmpleadoLayout);
        jPanelAgregarEmpleadoLayout.setHorizontalGroup(
            jPanelAgregarEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAgregarEmpleadoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelAgregarEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelDatosPersonales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelAgregarEmpleadoLayout.setVerticalGroup(
            jPanelAgregarEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAgregarEmpleadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelDatosPersonales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanelAgregarEmpleado);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
        if (this.empleadoEliminar != null && this.empleadoEliminar.getActivo() == Empleado.IS_ACTIVO) {
            /*validar la opcion seleccionada por el usuario*/
            int option = showConfirmDialog(this, "Seleccione aceptar para eliminar el empleado seleccionado.", "¿Eliminar Empleado?", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                if (ControllerBO.desactivarEmpleado(empleadoEliminar)) {
                    JOptionPane.showMessageDialog(this, "EL empleado ha sido desactivado.", "ELIMINADO CORRECTO", JOptionPane.ERROR_MESSAGE);
                    this.dispose();
                    this.refresh.refrescarVentanaLiquidacion();
                } else {
                    JOptionPane.showMessageDialog(this, "Error eliminando el empleado, intente nuevamente.", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un empleado activo para eliminar.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonEliminarActionPerformed

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        try {
            String identificacion = jTextFieldIdentificacion.getText().trim().toUpperCase();
            if (identificacion.length() > 0) {
                empleadoEliminar = ControllerBO.buscarEmpleadoPorIdentificacion(identificacion);
                if (empleadoEliminar != null) {
                    cargarInformacionDelEmpleado(empleadoEliminar);
                } else {
                    throw new Exception("Empleado no encontrado con este numero de identificacion.");
                }
            } else {
                throw new Exception("Ingrese la identificacion del empleado a eliminar");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Ingresar Busqueda", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonBuscarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelEstado;
    private javax.swing.JPanel jPanelAgregarEmpleado;
    private javax.swing.JPanel jPanelBuscar;
    private javax.swing.JPanel jPanelDatosPersonales;
    private javax.swing.JPanel jPanelIngresoDatos;
    private javax.swing.JTextField jTextFieldApellidos;
    private javax.swing.JTextField jTextFieldDireccion;
    private javax.swing.JTextField jTextFieldIdentificacion;
    private javax.swing.JTextField jTextFieldNombres;
    private javax.swing.JTextField jTextFieldTelefono;
    // End of variables declaration//GEN-END:variables

    /**
     * cargar en pantalla la informacion del empleado que ha sido encontrado.
     *
     * @param empleado objeto empleado con la informacion personal
     */
    private void cargarInformacionDelEmpleado(final Empleado empleado) {
        /*Identificar si el empleado encontrado es activo o no. 
        si el empleado ya no es activo no se puede volver a desactivar.*/
        if (empleado.getActivo() != Empleado.IS_ACTIVO) {
            jButtonEliminar.setEnabled(false);
            jLabelEstado.setForeground(Color.red);
            jLabelEstado.setText(" - ELIMINADO -");
        } else {
            jButtonEliminar.setEnabled(true);
            jLabelEstado.setForeground(Color.blue);
            jLabelEstado.setText(" - ACTIVO -");
        }

        /*setear los datos encontrados*/
        jTextFieldNombres.setText(empleado.getNombres());
        jTextFieldApellidos.setText(empleado.getApellidos());
        jTextFieldTelefono.setText(empleado.getTelefono());
        jTextFieldDireccion.setText(empleado.getDireccion());
    }
}
