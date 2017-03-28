/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.servicentroguerrero.gui;

import co.com.servicentro.util.DocumentTypeDouble;
import co.com.servicentro.util.Util;
import co.com.servicentroguerrero.controler.ControllerBO;
import co.com.servicentroguerrero.controler.WorkerBO;
import co.com.servicentroguerrero.modelos.Combustible;
import co.com.servicentroguerrero.modelos.Empleado;
import co.com.servicentroguerrero.modelos.Liquidacion;
import co.com.servicentroguerrero.modelos.LiquidacionDispensador;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author JICZ4
 */
public class JFrameLiquidacion extends javax.swing.JFrame implements IDinero {

    /*Constantes para identificar los surtidores disponibles.*/
    private static final int SURTIDOR1 = 0X01;
    private static final int SURTIDOR2 = 0X02;
    private static final int SURTIDOR3 = 0X03;

    /*Constantes para identificar el dispensador de cada surtidor.*/
    private static final int DISPENSADOR1 = 0X01;
    private static final int DISPENSADOR2 = 0X02;
    private static final int DISPENSADOR3 = 0X03;
    private static final int DISPENSADOR4 = 0X04;
    private static final int DISPENSADOR5 = 0X05;
    private static final int DISPENSADOR6 = 0X06;

    /**
     * Precio actual de la gasolina corriente.
     */
    private double precioCorriente;

    /**
     * Precio actual del ACPM
     */
    private double precioAcpm;

    /**
     * Ultima liquidacion encontrada en base de datos
     */
    private Liquidacion ultimaLiquidacion = null;

    /**
     * Detalle por dispensador de la ultima liquidacion encontrada.
     */
    private ArrayList<LiquidacionDispensador> listaUltimaLiquidacionDispensador;

    /**
     * Referencia de la lista de liquidaciones de cada dispensador que se esta
     * liquidando acualmente.
     */
    private LiquidacionDispensador[] liquidacionesPorDispensador;

    /**
     *
     * Constante para indicar una posicion por defecto en combobox.
     */
    private static final int POSITION_DEFAULT = 0x00;

    /**
     * Representa el total de dinero liquidado por surtidores y aceites
     * vendidos.
     */
    private double totalLiquidado = 0;

    private double totalDineroIngresado = 0;
    /**
     * Referencia para la ventana de ingreso de dinero.
     */
    private JFrameIngresoDinero jFrameIngresoDinero;

    
    private final Empleado empleadoLiquidador; 
    /**
     * Creates new form JFrameLiquidacion
     * @param empleadoLiquidador, empleado que realiza la liquidacion actual.
     */
    public JFrameLiquidacion(final Empleado empleadoLiquidador) {
        
        /*Empleado que va a realizar la liquidacion*/
        this.empleadoLiquidador = empleadoLiquidador;

        /*Inicializar componentes*/
        initComponents();

        /*cargar listener de cambio de texto para calcular liquidacion total*/
        jTextFieldTotalCombustibles.getDocument().addDocumentListener((JTextFieldChangedListener) e -> {
            calcularLiquidacionTotal();
        });

        /*Poner la fecha del dia*/
        cargarFechaActual();
        /*Llenar el combo de isleros */
        cargarComboIsleros();
        /*Cargar los precios vigentes de combustibles.*/
        cargarPreciosCombustiblesVigente();
        /*cargar identificacion de surtidores*/
        cargarIdentificadorDeSurtidores();
        /*Validar que los campos sean numericos tipo double*/
        setDocumentsDouble();

        /*Inicializar la lsta de liquidaciones por dispensador*/
        crearArrayLiquidaciones();

    }

    /**
     * Metodo para crear el objeto array y llenar con liquidaciones vacias
     * inicialmente. Se asigna a cada posicion del arreglo un dispensador por
     * cada surtidor.
     */
    private void crearArrayLiquidaciones() {

        liquidacionesPorDispensador = new LiquidacionDispensador[6];

        liquidacionesPorDispensador[0] = new LiquidacionDispensador();
        liquidacionesPorDispensador[0].setIdDispensador(DISPENSADOR1);
        liquidacionesPorDispensador[0].setIdSurtidor(SURTIDOR1);

        liquidacionesPorDispensador[1] = new LiquidacionDispensador();
        liquidacionesPorDispensador[1].setIdDispensador(DISPENSADOR2);
        liquidacionesPorDispensador[1].setIdSurtidor(SURTIDOR1);

        liquidacionesPorDispensador[2] = new LiquidacionDispensador();
        liquidacionesPorDispensador[2].setIdDispensador(DISPENSADOR3);
        liquidacionesPorDispensador[2].setIdSurtidor(SURTIDOR2);

        liquidacionesPorDispensador[3] = new LiquidacionDispensador();
        liquidacionesPorDispensador[3].setIdDispensador(DISPENSADOR4);
        liquidacionesPorDispensador[3].setIdSurtidor(SURTIDOR2);

        liquidacionesPorDispensador[4] = new LiquidacionDispensador();
        liquidacionesPorDispensador[4].setIdDispensador(DISPENSADOR5);
        liquidacionesPorDispensador[4].setIdSurtidor(SURTIDOR3);

        liquidacionesPorDispensador[5] = new LiquidacionDispensador();
        liquidacionesPorDispensador[5].setIdDispensador(DISPENSADOR6);
        liquidacionesPorDispensador[5].setIdSurtidor(SURTIDOR3);
    }

    private void setDocumentsDouble() {
        jTextFieldS1D1Entregado.setDocument(new DocumentTypeDouble());
        jTextFieldS1D1GalonesIngresados.setDocument(new DocumentTypeDouble());
        jTextFieldS1D1TotalDinero.setDocument(new DocumentTypeDouble());
        jTextFieldS1D2Entregado.setDocument(new DocumentTypeDouble());
        jTextFieldS1D2GalonesIngresados.setDocument(new DocumentTypeDouble());
        jTextFieldS1D2TotalDinero.setDocument(new DocumentTypeDouble());

        jTextFieldS2D1Entregado.setDocument(new DocumentTypeDouble());
        jTextFieldS2D1GalonesIngresados.setDocument(new DocumentTypeDouble());
        jTextFieldS2D1TotalDinero.setDocument(new DocumentTypeDouble());
        jTextFieldS2D2Entregado.setDocument(new DocumentTypeDouble());
        jTextFieldS2D2GalonesIngresados.setDocument(new DocumentTypeDouble());
        jTextFieldS2D2TotalDinero.setDocument(new DocumentTypeDouble());

        jTextFieldS3D1Entregado.setDocument(new DocumentTypeDouble());
        jTextFieldS3D1GalonesIngresados.setDocument(new DocumentTypeDouble());
        jTextFieldS3D1TotalDinero.setDocument(new DocumentTypeDouble());
        jTextFieldS3D2Entregado.setDocument(new DocumentTypeDouble());
        jTextFieldS3D2GalonesIngresados.setDocument(new DocumentTypeDouble());
        jTextFieldS3D2TotalDinero.setDocument(new DocumentTypeDouble());

        jTextFieldVentasAceites.setDocument(new DocumentTypeDouble());
        jTextFieldCompraCombustibleSurtidor1.setDocument(new DocumentTypeDouble());
        jTextFieldCompraCombustibleSurtidor2.setDocument(new DocumentTypeDouble());
        jTextFieldCompraCombustibleSurtidor3.setDocument(new DocumentTypeDouble());

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane = new javax.swing.JTabbedPane();
        jPanelLiquidacion = new javax.swing.JPanel();
        jPanelIngresoDinero = new javax.swing.JPanel();
        jButtonIngresarDinero = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jPanelResumenLiquidacion = new javax.swing.JPanel();
        jPanelPreciosActuales = new javax.swing.JPanel();
        jPanelPreciosActualesFecha = new javax.swing.JPanel();
        jLabelFecha = new javax.swing.JLabel();
        jPanelPreciosActualesGasolinaCorriente = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabelPrecioGasolinaCorriente = new javax.swing.JLabel();
        jPanelPreciosActualesAcpm = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabelPrecioAcpm = new javax.swing.JLabel();
        jPanelEnpleado = new javax.swing.JPanel();
        jPanelCambiarIslero = new javax.swing.JPanel();
        jComboBoxCambiarIslero = new javax.swing.JComboBox<>();
        jPanelAceites = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldVentasAceites = new javax.swing.JTextField();
        jPanelCombustibles = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldTotalCombustibles = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldCompraCombustibleSurtidor1 = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldCompraCombustibleSurtidor2 = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldCompraCombustibleSurtidor3 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jTextFieldTotalLiquidado = new javax.swing.JTextField();
        jTextFieldEntregado = new javax.swing.JTextField();
        jTextFieldDiferencia = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jButtonGuardarLiquidacion = new javax.swing.JButton();
        jPanelContenedorSurtidores = new javax.swing.JPanel();
        jPanelSurtidor1 = new javax.swing.JPanel();
        jPanelSurtUnoDispUno = new javax.swing.JPanel();
        jPanelS1D1Entregado = new javax.swing.JPanel();
        jLabelS1D1Entregado = new javax.swing.JLabel();
        jTextFieldS1D1Entregado = new javax.swing.JTextField();
        jPanelS1D1Recibido = new javax.swing.JPanel();
        jLabelS1D1Recibido = new javax.swing.JLabel();
        jTextFieldS1D1Recibido = new javax.swing.JTextField();
        jPanelS1D1Galones = new javax.swing.JPanel();
        jLabelS1D1GalonesIngresados = new javax.swing.JLabel();
        jTextFieldS1D1GalonesIngresados = new javax.swing.JTextField();
        jPanelS1D1GalonesCalculados = new javax.swing.JPanel();
        jLabelS1D1GalonesCalculados = new javax.swing.JLabel();
        jTextFieldS1D1GalonesCalculados = new javax.swing.JTextField();
        jPanelS1D1TotalDinero = new javax.swing.JPanel();
        jLabelS1D1TotalDinero = new javax.swing.JLabel();
        jTextFieldS1D1TotalDinero = new javax.swing.JTextField();
        jPanelS1D1TotalDineroCalculado = new javax.swing.JPanel();
        jLabelS1D1TotalDineroCalculado = new javax.swing.JLabel();
        jTextFieldS1D1TotalDineroCalculado = new javax.swing.JTextField();
        jPanelS1D1DiferenciaDinero = new javax.swing.JPanel();
        jLabelS1D1Diferencia = new javax.swing.JLabel();
        jTextFieldS1D1Diferencia = new javax.swing.JTextField();
        jPanelSurtUnoDispDos = new javax.swing.JPanel();
        jPanelS1D2Entregado = new javax.swing.JPanel();
        jLabelS1D2Entregado = new javax.swing.JLabel();
        jTextFieldS1D2Entregado = new javax.swing.JTextField();
        jPanelS1D2Recibido = new javax.swing.JPanel();
        jLabelS1D2Recibido = new javax.swing.JLabel();
        jTextFieldS1D2Recibido = new javax.swing.JTextField();
        jPanelS1D2Galones = new javax.swing.JPanel();
        jLabelS1D2GalonesIngresados = new javax.swing.JLabel();
        jTextFieldS1D2GalonesIngresados = new javax.swing.JTextField();
        jPanelS1D2GalonesCalculados = new javax.swing.JPanel();
        jLabelS1D2GalonesCalculados = new javax.swing.JLabel();
        jTextFieldS1D2GalonesCalculados = new javax.swing.JTextField();
        jPanelS1D2TotalDinero = new javax.swing.JPanel();
        jLabelS1D2TotalDinero = new javax.swing.JLabel();
        jTextFieldS1D2TotalDinero = new javax.swing.JTextField();
        jPanelS1D2TotalDineroCalculado = new javax.swing.JPanel();
        jLabelS1D2TotalDineroCalculado = new javax.swing.JLabel();
        jTextFieldS1D2TotalDineroCalculado = new javax.swing.JTextField();
        jPanelS1D2DiferenciaDinero = new javax.swing.JPanel();
        jLabelS1D2Diferencia = new javax.swing.JLabel();
        jTextFieldS1D2Diferencia = new javax.swing.JTextField();
        jPanelSurtidor2 = new javax.swing.JPanel();
        jPanelSurtDosDispUno = new javax.swing.JPanel();
        jPanelS2D1Entregado = new javax.swing.JPanel();
        jLabelS2D1Entregado = new javax.swing.JLabel();
        jTextFieldS2D1Entregado = new javax.swing.JTextField();
        jPanelS2D1Recibido = new javax.swing.JPanel();
        jLabelS2D1Recibido = new javax.swing.JLabel();
        jTextFieldS2D1Recibido = new javax.swing.JTextField();
        jPanelS2D1Galones = new javax.swing.JPanel();
        jLabelS2D1GalonesIngresados = new javax.swing.JLabel();
        jTextFieldS2D1GalonesIngresados = new javax.swing.JTextField();
        jPanelS2D1GalonesCalculados = new javax.swing.JPanel();
        jLabelS2D1GalonesCalculados = new javax.swing.JLabel();
        jTextFieldS2D1GalonesCalculados = new javax.swing.JTextField();
        jPanelS2D1TotalDinero = new javax.swing.JPanel();
        jLabelS2D1TotalDinero = new javax.swing.JLabel();
        jTextFieldS2D1TotalDinero = new javax.swing.JTextField();
        jPanelS2D1TotalDineroCalculado = new javax.swing.JPanel();
        jLabelS2D1TotalDineroCalculado = new javax.swing.JLabel();
        jTextFieldS2D1TotalDineroCalculado = new javax.swing.JTextField();
        jPanelS2D1DiferenciaDinero = new javax.swing.JPanel();
        jLabelS2D1Diferencia = new javax.swing.JLabel();
        jTextFieldS2D1Diferencia = new javax.swing.JTextField();
        jPanelSurtDosDispDos = new javax.swing.JPanel();
        jPanelS2D2Entregado = new javax.swing.JPanel();
        jLabelS2D2Entregado = new javax.swing.JLabel();
        jTextFieldS2D2Entregado = new javax.swing.JTextField();
        jPanelS2D2Recibido = new javax.swing.JPanel();
        jLabelS2D2Recibido = new javax.swing.JLabel();
        jTextFieldS2D2Recibido = new javax.swing.JTextField();
        jPanelS2D2Galones = new javax.swing.JPanel();
        jLabelS2D2GalonesIngresados = new javax.swing.JLabel();
        jTextFieldS2D2GalonesIngresados = new javax.swing.JTextField();
        jPanelS2D2GalonesCalculados = new javax.swing.JPanel();
        jLabelS2D2GalonesCalculados = new javax.swing.JLabel();
        jTextFieldS2D2GalonesCalculados = new javax.swing.JTextField();
        jPanelS2D2TotalDinero = new javax.swing.JPanel();
        jLabelS2D2TotalDinero = new javax.swing.JLabel();
        jTextFieldS2D2TotalDinero = new javax.swing.JTextField();
        jPanelS2D2TotalDineroCalculado = new javax.swing.JPanel();
        jLabelS2D2TotalDineroCalculado = new javax.swing.JLabel();
        jTextFieldS2D2TotalDineroCalculado = new javax.swing.JTextField();
        jPanelS2D2DiferenciaDinero = new javax.swing.JPanel();
        jLabelS2D2Diferencia = new javax.swing.JLabel();
        jTextFieldS2D2Diferencia = new javax.swing.JTextField();
        jPanelSurtidor3 = new javax.swing.JPanel();
        jPanelSurtTresDispUno = new javax.swing.JPanel();
        jPanelS3D1Entregado = new javax.swing.JPanel();
        jLabelS3D1Entregado = new javax.swing.JLabel();
        jTextFieldS3D1Entregado = new javax.swing.JTextField();
        jPanelS3D1Recibido = new javax.swing.JPanel();
        jLabelS3D1Recibido = new javax.swing.JLabel();
        jTextFieldS3D1Recibido = new javax.swing.JTextField();
        jPanelS3D1Galones = new javax.swing.JPanel();
        jLabelS3D1GalonesIngresados = new javax.swing.JLabel();
        jTextFieldS3D1GalonesIngresados = new javax.swing.JTextField();
        jPanelS3D1GalonesCalculados = new javax.swing.JPanel();
        jLabelS3D1GalonesCalculados = new javax.swing.JLabel();
        jTextFieldS3D1GalonesCalculados = new javax.swing.JTextField();
        jPanelS3D1TotalDinero = new javax.swing.JPanel();
        jLabelS3D1TotalDinero = new javax.swing.JLabel();
        jTextFieldS3D1TotalDinero = new javax.swing.JTextField();
        jPanelS3D1TotalDineroCalculado = new javax.swing.JPanel();
        jLabelS3D1TotalDineroCalculado = new javax.swing.JLabel();
        jTextFieldS3D1TotalDineroCalculado = new javax.swing.JTextField();
        jPanelS3D1DiferenciaDinero = new javax.swing.JPanel();
        jLabelS3D1Diferencia = new javax.swing.JLabel();
        jTextFieldS3D1Diferencia = new javax.swing.JTextField();
        jPanelSurtTresDispDos = new javax.swing.JPanel();
        jPanelS3D2Entregado = new javax.swing.JPanel();
        jLabelS3D2Entregado = new javax.swing.JLabel();
        jTextFieldS3D2Entregado = new javax.swing.JTextField();
        jPanelS3D2Recibido = new javax.swing.JPanel();
        jLabelS3D2Recibido = new javax.swing.JLabel();
        jTextFieldS3D2Recibido = new javax.swing.JTextField();
        jPanelS3D2Galones = new javax.swing.JPanel();
        jLabelS3D2GalonesIngresados = new javax.swing.JLabel();
        jTextFieldS3D2GalonesIngresados = new javax.swing.JTextField();
        jPanelS3D2GalonesCalculados = new javax.swing.JPanel();
        jLabelS3D2GalonesCalculados = new javax.swing.JLabel();
        jTextFieldS3D2GalonesCalculados = new javax.swing.JTextField();
        jPanelS3D2TotalDinero = new javax.swing.JPanel();
        jLabelS3D2TotalDinero = new javax.swing.JLabel();
        jTextFieldS3D2TotalDinero = new javax.swing.JTextField();
        jPanelS3D2TotalDineroCalculado = new javax.swing.JPanel();
        jLabelS3D2TotalDineroCalculado = new javax.swing.JLabel();
        jTextFieldS3D2TotalDineroCalculado = new javax.swing.JTextField();
        jPanelS3D2DiferenciaDinero = new javax.swing.JPanel();
        jLabelS3D2Diferencia = new javax.swing.JLabel();
        jTextFieldS3D2Diferencia = new javax.swing.JTextField();
        jMenuBar = new javax.swing.JMenuBar();
        jMenuArchivo = new javax.swing.JMenu();
        jMenuItemSalir = new javax.swing.JMenuItem();
        jMenuHerramientas = new javax.swing.JMenu();
        jMenuEmpleados = new javax.swing.JMenu();
        jMenuItemEmpleadosAgregar = new javax.swing.JMenuItem();
        jMenuItemEmpleadosEliminar = new javax.swing.JMenuItem();
        jMenuBaseDeDatos = new javax.swing.JMenu();
        jMenuItemBackUp = new javax.swing.JMenuItem();
        jMenuItemRestaurar = new javax.swing.JMenuItem();
        jMenuItemActualizarPrecios = new javax.swing.JMenuItem();
        jMenuItemCalibracion = new javax.swing.JMenuItem();
        jMenuItemExtraLiquidacion = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Servicentro Guerrero");
        setExtendedState(6);
        setMaximumSize(new java.awt.Dimension(0, 0));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanelLiquidacion.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelLiquidacion.setMaximumSize(new java.awt.Dimension(1024, 700));
        jPanelLiquidacion.setMinimumSize(new java.awt.Dimension(1024, 700));

        jPanelIngresoDinero.setFocusable(false);
        jPanelIngresoDinero.setMaximumSize(new java.awt.Dimension(1024, 32767));
        jPanelIngresoDinero.setPreferredSize(new java.awt.Dimension(768, 160));

        jButtonIngresarDinero.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonIngresarDinero.setText("Ingresar Dinero");
        jButtonIngresarDinero.setMaximumSize(new java.awt.Dimension(130, 25));
        jButtonIngresarDinero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIngresarDineroActionPerformed(evt);
            }
        });

        jPanelResumenLiquidacion.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Liquidacion", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPanelResumenLiquidacion.setPreferredSize(new java.awt.Dimension(1027, 110));
        jPanelResumenLiquidacion.setLayout(new java.awt.GridLayout(1, 5, 1, 1));

        jPanelPreciosActuales.setLayout(new java.awt.GridLayout(3, 1, 1, 1));

        jPanelPreciosActualesFecha.setLayout(new java.awt.GridLayout(1, 1, 1, 1));

        jLabelFecha.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelFecha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelFecha.setToolTipText("");
        jPanelPreciosActualesFecha.add(jLabelFecha);

        jPanelPreciosActuales.add(jPanelPreciosActualesFecha);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("G. Corriente :  ");

        jLabelPrecioGasolinaCorriente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelPrecioGasolinaCorriente.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jPanelPreciosActualesGasolinaCorrienteLayout = new javax.swing.GroupLayout(jPanelPreciosActualesGasolinaCorriente);
        jPanelPreciosActualesGasolinaCorriente.setLayout(jPanelPreciosActualesGasolinaCorrienteLayout);
        jPanelPreciosActualesGasolinaCorrienteLayout.setHorizontalGroup(
            jPanelPreciosActualesGasolinaCorrienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanelPreciosActualesGasolinaCorrienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelPreciosActualesGasolinaCorrienteLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(1, 1, 1)
                    .addComponent(jLabelPrecioGasolinaCorriente, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanelPreciosActualesGasolinaCorrienteLayout.setVerticalGroup(
            jPanelPreciosActualesGasolinaCorrienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanelPreciosActualesGasolinaCorrienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelPreciosActualesGasolinaCorrienteLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(jPanelPreciosActualesGasolinaCorrienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelPrecioGasolinaCorriente, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jPanelPreciosActuales.add(jPanelPreciosActualesGasolinaCorriente);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("ACPM :  ");

        jLabelPrecioAcpm.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelPrecioAcpm.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jPanelPreciosActualesAcpmLayout = new javax.swing.GroupLayout(jPanelPreciosActualesAcpm);
        jPanelPreciosActualesAcpm.setLayout(jPanelPreciosActualesAcpmLayout);
        jPanelPreciosActualesAcpmLayout.setHorizontalGroup(
            jPanelPreciosActualesAcpmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanelPreciosActualesAcpmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelPreciosActualesAcpmLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(1, 1, 1)
                    .addComponent(jLabelPrecioAcpm, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanelPreciosActualesAcpmLayout.setVerticalGroup(
            jPanelPreciosActualesAcpmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanelPreciosActualesAcpmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelPreciosActualesAcpmLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(jPanelPreciosActualesAcpmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelPrecioAcpm, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jPanelPreciosActuales.add(jPanelPreciosActualesAcpm);

        jPanelResumenLiquidacion.add(jPanelPreciosActuales);

        jPanelEnpleado.setLayout(new java.awt.GridLayout(3, 1, 1, 1));

        jPanelCambiarIslero.setLayout(new java.awt.GridLayout(1, 1));

        jComboBoxCambiarIslero.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jComboBoxCambiarIslero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCambiarIsleroActionPerformed(evt);
            }
        });
        jPanelCambiarIslero.add(jComboBoxCambiarIslero);

        jPanelEnpleado.add(jPanelCambiarIslero);

        jPanelAceites.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Aceites : ");
        jPanelAceites.add(jLabel1);

        jTextFieldVentasAceites.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldVentasAceites.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldVentasAceites.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldVentasAceitesFocusLost(evt);
            }
        });
        jTextFieldVentasAceites.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldVentasAceitesActionPerformed(evt);
            }
        });
        jPanelAceites.add(jTextFieldVentasAceites);

        jPanelEnpleado.add(jPanelAceites);

        jPanelCombustibles.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Combustibles : ");
        jPanelCombustibles.add(jLabel4);

        jTextFieldTotalCombustibles.setEditable(false);
        jTextFieldTotalCombustibles.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldTotalCombustibles.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldTotalCombustibles.setText("$0");
        jTextFieldTotalCombustibles.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextFieldTotalCombustibles.setEnabled(false);
        jPanelCombustibles.add(jTextFieldTotalCombustibles);

        jPanelEnpleado.add(jPanelCombustibles);

        jPanelResumenLiquidacion.add(jPanelEnpleado);

        jPanel2.setLayout(new java.awt.GridLayout(3, 1, 1, 1));

        jPanel6.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Compra Surtidor 1 : ");
        jPanel6.add(jLabel6);

        jTextFieldCompraCombustibleSurtidor1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldCompraCombustibleSurtidor1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldCompraCombustibleSurtidor1FocusLost(evt);
            }
        });
        jTextFieldCompraCombustibleSurtidor1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCompraCombustibleSurtidor1ActionPerformed(evt);
            }
        });
        jPanel6.add(jTextFieldCompraCombustibleSurtidor1);

        jPanel2.add(jPanel6);

        jPanel7.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Compra Surtidor 2 : ");
        jPanel7.add(jLabel7);

        jTextFieldCompraCombustibleSurtidor2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldCompraCombustibleSurtidor2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldCompraCombustibleSurtidor2FocusLost(evt);
            }
        });
        jTextFieldCompraCombustibleSurtidor2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCompraCombustibleSurtidor2ActionPerformed(evt);
            }
        });
        jPanel7.add(jTextFieldCompraCombustibleSurtidor2);

        jPanel2.add(jPanel7);

        jPanel8.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Compra Surtidor 3 : ");
        jPanel8.add(jLabel8);

        jTextFieldCompraCombustibleSurtidor3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldCompraCombustibleSurtidor3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldCompraCombustibleSurtidor3FocusLost(evt);
            }
        });
        jTextFieldCompraCombustibleSurtidor3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCompraCombustibleSurtidor3ActionPerformed(evt);
            }
        });
        jPanel8.add(jTextFieldCompraCombustibleSurtidor3);

        jPanel2.add(jPanel8);

        jPanelResumenLiquidacion.add(jPanel2);

        jPanel1.setLayout(new java.awt.GridLayout(3, 1, 1, 1));

        jPanel9.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Liquidado ");
        jPanel9.add(jLabel9);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Entregado");
        jPanel9.add(jLabel2);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Diferencia");
        jPanel9.add(jLabel10);

        jPanel1.add(jPanel9);

        jPanel10.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jTextFieldTotalLiquidado.setEditable(false);
        jTextFieldTotalLiquidado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldTotalLiquidado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldTotalLiquidado.setText("$0");
        jTextFieldTotalLiquidado.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextFieldTotalLiquidado.setEnabled(false);
        jPanel10.add(jTextFieldTotalLiquidado);

        jTextFieldEntregado.setEditable(false);
        jTextFieldEntregado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldEntregado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldEntregado.setText("$0");
        jTextFieldEntregado.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextFieldEntregado.setEnabled(false);
        jPanel10.add(jTextFieldEntregado);

        jTextFieldDiferencia.setEditable(false);
        jTextFieldDiferencia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldDiferencia.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldDiferencia.setText("$0");
        jTextFieldDiferencia.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextFieldDiferencia.setEnabled(false);
        jPanel10.add(jTextFieldDiferencia);

        jPanel1.add(jPanel10);

        jPanel3.setLayout(new java.awt.GridLayout(1, 1, 2, 2));

        jButtonGuardarLiquidacion.setBackground(new java.awt.Color(204, 204, 204));
        jButtonGuardarLiquidacion.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonGuardarLiquidacion.setText("Guardar");
        jButtonGuardarLiquidacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuardarLiquidacionActionPerformed(evt);
            }
        });
        jPanel3.add(jButtonGuardarLiquidacion);

        jPanel1.add(jPanel3);

        jPanelResumenLiquidacion.add(jPanel1);

        javax.swing.GroupLayout jPanelIngresoDineroLayout = new javax.swing.GroupLayout(jPanelIngresoDinero);
        jPanelIngresoDinero.setLayout(jPanelIngresoDineroLayout);
        jPanelIngresoDineroLayout.setHorizontalGroup(
            jPanelIngresoDineroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelIngresoDineroLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanelIngresoDineroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelResumenLiquidacion, javax.swing.GroupLayout.DEFAULT_SIZE, 1319, Short.MAX_VALUE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonIngresarDinero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelIngresoDineroLayout.setVerticalGroup(
            jPanelIngresoDineroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelIngresoDineroLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonIngresarDinero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelResumenLiquidacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(167, 167, 167))
        );

        jPanelContenedorSurtidores.setLayout(new java.awt.GridLayout(1, 3, 10, 10));

        jPanelSurtidor1.setBackground(new java.awt.Color(255, 255, 255));
        jPanelSurtidor1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Surtidor 1 - Corriente", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPanelSurtidor1.setLayout(new java.awt.GridLayout(2, 1, 3, 3));

        jPanelSurtUnoDispUno.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pistola 1", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPanelSurtUnoDispUno.setLayout(new java.awt.GridLayout(7, 1, 2, 2));

        jPanelS1D1Entregado.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS1D1Entregado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelS1D1Entregado.setText("Entregado:");
        jPanelS1D1Entregado.add(jLabelS1D1Entregado);

        jTextFieldS1D1Entregado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS1D1Entregado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS1D1Entregado.setMaximumSize(new java.awt.Dimension(0, 0));
        jTextFieldS1D1Entregado.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldS1D1EntregadoFocusLost(evt);
            }
        });
        jTextFieldS1D1Entregado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldS1D1EntregadoActionPerformed(evt);
            }
        });
        jPanelS1D1Entregado.add(jTextFieldS1D1Entregado);

        jPanelSurtUnoDispUno.add(jPanelS1D1Entregado);

        jPanelS1D1Recibido.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS1D1Recibido.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelS1D1Recibido.setText("Recibido:");
        jPanelS1D1Recibido.add(jLabelS1D1Recibido);

        jTextFieldS1D1Recibido.setEditable(false);
        jTextFieldS1D1Recibido.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS1D1Recibido.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS1D1Recibido.setText("0");
        jTextFieldS1D1Recibido.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextFieldS1D1Recibido.setEnabled(false);
        jPanelS1D1Recibido.add(jTextFieldS1D1Recibido);

        jPanelSurtUnoDispUno.add(jPanelS1D1Recibido);

        jPanelS1D1Galones.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS1D1GalonesIngresados.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelS1D1GalonesIngresados.setText("Galones:");
        jPanelS1D1Galones.add(jLabelS1D1GalonesIngresados);

        jTextFieldS1D1GalonesIngresados.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS1D1GalonesIngresados.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS1D1GalonesIngresados.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldS1D1GalonesIngresadosFocusLost(evt);
            }
        });
        jTextFieldS1D1GalonesIngresados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldS1D1GalonesIngresadosActionPerformed(evt);
            }
        });
        jPanelS1D1Galones.add(jTextFieldS1D1GalonesIngresados);

        jPanelSurtUnoDispUno.add(jPanelS1D1Galones);

        jPanelS1D1GalonesCalculados.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS1D1GalonesCalculados.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelS1D1GalonesCalculados.setText("Galones Calculados: ");
        jPanelS1D1GalonesCalculados.add(jLabelS1D1GalonesCalculados);

        jTextFieldS1D1GalonesCalculados.setEditable(false);
        jTextFieldS1D1GalonesCalculados.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS1D1GalonesCalculados.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS1D1GalonesCalculados.setText("0");
        jTextFieldS1D1GalonesCalculados.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextFieldS1D1GalonesCalculados.setEnabled(false);
        jPanelS1D1GalonesCalculados.add(jTextFieldS1D1GalonesCalculados);

        jPanelSurtUnoDispUno.add(jPanelS1D1GalonesCalculados);

        jPanelS1D1TotalDinero.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS1D1TotalDinero.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelS1D1TotalDinero.setText("Dinero:");
        jPanelS1D1TotalDinero.add(jLabelS1D1TotalDinero);

        jTextFieldS1D1TotalDinero.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS1D1TotalDinero.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS1D1TotalDinero.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldS1D1TotalDineroFocusLost(evt);
            }
        });
        jTextFieldS1D1TotalDinero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldS1D1TotalDineroActionPerformed(evt);
            }
        });
        jPanelS1D1TotalDinero.add(jTextFieldS1D1TotalDinero);

        jPanelSurtUnoDispUno.add(jPanelS1D1TotalDinero);

        jPanelS1D1TotalDineroCalculado.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS1D1TotalDineroCalculado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelS1D1TotalDineroCalculado.setText("Dinero Calculado:");
        jPanelS1D1TotalDineroCalculado.add(jLabelS1D1TotalDineroCalculado);

        jTextFieldS1D1TotalDineroCalculado.setEditable(false);
        jTextFieldS1D1TotalDineroCalculado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS1D1TotalDineroCalculado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS1D1TotalDineroCalculado.setText("$0");
        jTextFieldS1D1TotalDineroCalculado.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextFieldS1D1TotalDineroCalculado.setEnabled(false);
        jPanelS1D1TotalDineroCalculado.add(jTextFieldS1D1TotalDineroCalculado);

        jPanelSurtUnoDispUno.add(jPanelS1D1TotalDineroCalculado);

        jPanelS1D1DiferenciaDinero.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS1D1Diferencia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelS1D1Diferencia.setText("Diferencia: ");
        jPanelS1D1DiferenciaDinero.add(jLabelS1D1Diferencia);

        jTextFieldS1D1Diferencia.setEditable(false);
        jTextFieldS1D1Diferencia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS1D1Diferencia.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS1D1Diferencia.setText("$0");
        jTextFieldS1D1Diferencia.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextFieldS1D1Diferencia.setEnabled(false);
        jPanelS1D1DiferenciaDinero.add(jTextFieldS1D1Diferencia);

        jPanelSurtUnoDispUno.add(jPanelS1D1DiferenciaDinero);

        jPanelSurtidor1.add(jPanelSurtUnoDispUno);

        jPanelSurtUnoDispDos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pistola 2", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPanelSurtUnoDispDos.setLayout(new java.awt.GridLayout(7, 1, 2, 2));

        jPanelS1D2Entregado.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS1D2Entregado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelS1D2Entregado.setText("Entregado:");
        jPanelS1D2Entregado.add(jLabelS1D2Entregado);

        jTextFieldS1D2Entregado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS1D2Entregado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS1D2Entregado.setMaximumSize(new java.awt.Dimension(0, 0));
        jTextFieldS1D2Entregado.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldS1D2EntregadoFocusLost(evt);
            }
        });
        jTextFieldS1D2Entregado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldS1D2EntregadoActionPerformed(evt);
            }
        });
        jPanelS1D2Entregado.add(jTextFieldS1D2Entregado);

        jPanelSurtUnoDispDos.add(jPanelS1D2Entregado);

        jPanelS1D2Recibido.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS1D2Recibido.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelS1D2Recibido.setText("Recibido:");
        jPanelS1D2Recibido.add(jLabelS1D2Recibido);

        jTextFieldS1D2Recibido.setEditable(false);
        jTextFieldS1D2Recibido.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS1D2Recibido.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS1D2Recibido.setText("0");
        jTextFieldS1D2Recibido.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextFieldS1D2Recibido.setEnabled(false);
        jPanelS1D2Recibido.add(jTextFieldS1D2Recibido);

        jPanelSurtUnoDispDos.add(jPanelS1D2Recibido);

        jPanelS1D2Galones.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS1D2GalonesIngresados.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelS1D2GalonesIngresados.setText("Galones:");
        jPanelS1D2Galones.add(jLabelS1D2GalonesIngresados);

        jTextFieldS1D2GalonesIngresados.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS1D2GalonesIngresados.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS1D2GalonesIngresados.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldS1D2GalonesIngresadosFocusLost(evt);
            }
        });
        jTextFieldS1D2GalonesIngresados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldS1D2GalonesIngresadosActionPerformed(evt);
            }
        });
        jPanelS1D2Galones.add(jTextFieldS1D2GalonesIngresados);

        jPanelSurtUnoDispDos.add(jPanelS1D2Galones);

        jPanelS1D2GalonesCalculados.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS1D2GalonesCalculados.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelS1D2GalonesCalculados.setText("Galones Calculados: ");
        jPanelS1D2GalonesCalculados.add(jLabelS1D2GalonesCalculados);

        jTextFieldS1D2GalonesCalculados.setEditable(false);
        jTextFieldS1D2GalonesCalculados.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS1D2GalonesCalculados.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS1D2GalonesCalculados.setText("0");
        jTextFieldS1D2GalonesCalculados.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextFieldS1D2GalonesCalculados.setEnabled(false);
        jPanelS1D2GalonesCalculados.add(jTextFieldS1D2GalonesCalculados);

        jPanelSurtUnoDispDos.add(jPanelS1D2GalonesCalculados);

        jPanelS1D2TotalDinero.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS1D2TotalDinero.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelS1D2TotalDinero.setText("Dinero:");
        jPanelS1D2TotalDinero.add(jLabelS1D2TotalDinero);

        jTextFieldS1D2TotalDinero.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS1D2TotalDinero.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS1D2TotalDinero.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldS1D2TotalDineroFocusLost(evt);
            }
        });
        jTextFieldS1D2TotalDinero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldS1D2TotalDineroActionPerformed(evt);
            }
        });
        jPanelS1D2TotalDinero.add(jTextFieldS1D2TotalDinero);

        jPanelSurtUnoDispDos.add(jPanelS1D2TotalDinero);

        jPanelS1D2TotalDineroCalculado.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS1D2TotalDineroCalculado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelS1D2TotalDineroCalculado.setText("Dinero Calculado:");
        jPanelS1D2TotalDineroCalculado.add(jLabelS1D2TotalDineroCalculado);

        jTextFieldS1D2TotalDineroCalculado.setEditable(false);
        jTextFieldS1D2TotalDineroCalculado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS1D2TotalDineroCalculado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS1D2TotalDineroCalculado.setText("$0");
        jTextFieldS1D2TotalDineroCalculado.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextFieldS1D2TotalDineroCalculado.setEnabled(false);
        jPanelS1D2TotalDineroCalculado.add(jTextFieldS1D2TotalDineroCalculado);

        jPanelSurtUnoDispDos.add(jPanelS1D2TotalDineroCalculado);

        jPanelS1D2DiferenciaDinero.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS1D2Diferencia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelS1D2Diferencia.setText("Diferencia: ");
        jPanelS1D2DiferenciaDinero.add(jLabelS1D2Diferencia);

        jTextFieldS1D2Diferencia.setEditable(false);
        jTextFieldS1D2Diferencia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS1D2Diferencia.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS1D2Diferencia.setText("$0");
        jTextFieldS1D2Diferencia.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextFieldS1D2Diferencia.setEnabled(false);
        jPanelS1D2DiferenciaDinero.add(jTextFieldS1D2Diferencia);

        jPanelSurtUnoDispDos.add(jPanelS1D2DiferenciaDinero);

        jPanelSurtidor1.add(jPanelSurtUnoDispDos);

        jPanelContenedorSurtidores.add(jPanelSurtidor1);

        jPanelSurtidor2.setBackground(new java.awt.Color(255, 255, 255));
        jPanelSurtidor2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " Surtidor 2 -  Corriente", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPanelSurtidor2.setToolTipText("");
        jPanelSurtidor2.setLayout(new java.awt.GridLayout(2, 1, 3, 3));

        jPanelSurtDosDispUno.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pistola 1", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPanelSurtDosDispUno.setLayout(new java.awt.GridLayout(7, 1, 2, 2));

        jPanelS2D1Entregado.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS2D1Entregado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelS2D1Entregado.setText("Entregado:");
        jPanelS2D1Entregado.add(jLabelS2D1Entregado);

        jTextFieldS2D1Entregado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS2D1Entregado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS2D1Entregado.setMaximumSize(new java.awt.Dimension(0, 0));
        jTextFieldS2D1Entregado.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldS2D1EntregadoFocusLost(evt);
            }
        });
        jTextFieldS2D1Entregado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldS2D1EntregadoActionPerformed(evt);
            }
        });
        jPanelS2D1Entregado.add(jTextFieldS2D1Entregado);

        jPanelSurtDosDispUno.add(jPanelS2D1Entregado);

        jPanelS2D1Recibido.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS2D1Recibido.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelS2D1Recibido.setText("Recibido:");
        jPanelS2D1Recibido.add(jLabelS2D1Recibido);

        jTextFieldS2D1Recibido.setEditable(false);
        jTextFieldS2D1Recibido.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS2D1Recibido.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS2D1Recibido.setText("0");
        jTextFieldS2D1Recibido.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextFieldS2D1Recibido.setEnabled(false);
        jPanelS2D1Recibido.add(jTextFieldS2D1Recibido);

        jPanelSurtDosDispUno.add(jPanelS2D1Recibido);

        jPanelS2D1Galones.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS2D1GalonesIngresados.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelS2D1GalonesIngresados.setText("Galones:");
        jPanelS2D1Galones.add(jLabelS2D1GalonesIngresados);

        jTextFieldS2D1GalonesIngresados.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS2D1GalonesIngresados.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS2D1GalonesIngresados.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldS2D1GalonesIngresadosFocusLost(evt);
            }
        });
        jTextFieldS2D1GalonesIngresados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldS2D1GalonesIngresadosActionPerformed(evt);
            }
        });
        jPanelS2D1Galones.add(jTextFieldS2D1GalonesIngresados);

        jPanelSurtDosDispUno.add(jPanelS2D1Galones);

        jPanelS2D1GalonesCalculados.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS2D1GalonesCalculados.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelS2D1GalonesCalculados.setText("Galones Calculados: ");
        jPanelS2D1GalonesCalculados.add(jLabelS2D1GalonesCalculados);

        jTextFieldS2D1GalonesCalculados.setEditable(false);
        jTextFieldS2D1GalonesCalculados.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS2D1GalonesCalculados.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS2D1GalonesCalculados.setText("0");
        jTextFieldS2D1GalonesCalculados.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextFieldS2D1GalonesCalculados.setEnabled(false);
        jPanelS2D1GalonesCalculados.add(jTextFieldS2D1GalonesCalculados);

        jPanelSurtDosDispUno.add(jPanelS2D1GalonesCalculados);

        jPanelS2D1TotalDinero.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS2D1TotalDinero.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelS2D1TotalDinero.setText("Dinero:");
        jPanelS2D1TotalDinero.add(jLabelS2D1TotalDinero);

        jTextFieldS2D1TotalDinero.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS2D1TotalDinero.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS2D1TotalDinero.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldS2D1TotalDineroFocusLost(evt);
            }
        });
        jTextFieldS2D1TotalDinero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldS2D1TotalDineroActionPerformed(evt);
            }
        });
        jPanelS2D1TotalDinero.add(jTextFieldS2D1TotalDinero);

        jPanelSurtDosDispUno.add(jPanelS2D1TotalDinero);

        jPanelS2D1TotalDineroCalculado.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS2D1TotalDineroCalculado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelS2D1TotalDineroCalculado.setText("Dinero Calculado:");
        jPanelS2D1TotalDineroCalculado.add(jLabelS2D1TotalDineroCalculado);

        jTextFieldS2D1TotalDineroCalculado.setEditable(false);
        jTextFieldS2D1TotalDineroCalculado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS2D1TotalDineroCalculado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS2D1TotalDineroCalculado.setText("$0");
        jTextFieldS2D1TotalDineroCalculado.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextFieldS2D1TotalDineroCalculado.setEnabled(false);
        jPanelS2D1TotalDineroCalculado.add(jTextFieldS2D1TotalDineroCalculado);

        jPanelSurtDosDispUno.add(jPanelS2D1TotalDineroCalculado);

        jPanelS2D1DiferenciaDinero.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS2D1Diferencia.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelS2D1Diferencia.setText("Diferencia: ");
        jPanelS2D1DiferenciaDinero.add(jLabelS2D1Diferencia);

        jTextFieldS2D1Diferencia.setEditable(false);
        jTextFieldS2D1Diferencia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS2D1Diferencia.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS2D1Diferencia.setText("$0");
        jTextFieldS2D1Diferencia.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextFieldS2D1Diferencia.setEnabled(false);
        jPanelS2D1DiferenciaDinero.add(jTextFieldS2D1Diferencia);

        jPanelSurtDosDispUno.add(jPanelS2D1DiferenciaDinero);

        jPanelSurtidor2.add(jPanelSurtDosDispUno);

        jPanelSurtDosDispDos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pistola 2", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPanelSurtDosDispDos.setLayout(new java.awt.GridLayout(7, 1, 2, 2));

        jPanelS2D2Entregado.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS2D2Entregado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelS2D2Entregado.setText("Entregado:");
        jPanelS2D2Entregado.add(jLabelS2D2Entregado);

        jTextFieldS2D2Entregado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS2D2Entregado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS2D2Entregado.setMaximumSize(new java.awt.Dimension(0, 0));
        jTextFieldS2D2Entregado.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldS2D2EntregadoFocusLost(evt);
            }
        });
        jTextFieldS2D2Entregado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldS2D2EntregadoActionPerformed(evt);
            }
        });
        jPanelS2D2Entregado.add(jTextFieldS2D2Entregado);

        jPanelSurtDosDispDos.add(jPanelS2D2Entregado);

        jPanelS2D2Recibido.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS2D2Recibido.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelS2D2Recibido.setText("Recibido:");
        jPanelS2D2Recibido.add(jLabelS2D2Recibido);

        jTextFieldS2D2Recibido.setEditable(false);
        jTextFieldS2D2Recibido.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS2D2Recibido.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS2D2Recibido.setText("0");
        jTextFieldS2D2Recibido.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextFieldS2D2Recibido.setEnabled(false);
        jPanelS2D2Recibido.add(jTextFieldS2D2Recibido);

        jPanelSurtDosDispDos.add(jPanelS2D2Recibido);

        jPanelS2D2Galones.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS2D2GalonesIngresados.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelS2D2GalonesIngresados.setText("Galones:");
        jPanelS2D2Galones.add(jLabelS2D2GalonesIngresados);

        jTextFieldS2D2GalonesIngresados.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS2D2GalonesIngresados.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS2D2GalonesIngresados.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldS2D2GalonesIngresadosFocusLost(evt);
            }
        });
        jTextFieldS2D2GalonesIngresados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldS2D2GalonesIngresadosActionPerformed(evt);
            }
        });
        jPanelS2D2Galones.add(jTextFieldS2D2GalonesIngresados);

        jPanelSurtDosDispDos.add(jPanelS2D2Galones);

        jPanelS2D2GalonesCalculados.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS2D2GalonesCalculados.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelS2D2GalonesCalculados.setText("Galones Calculados: ");
        jPanelS2D2GalonesCalculados.add(jLabelS2D2GalonesCalculados);

        jTextFieldS2D2GalonesCalculados.setEditable(false);
        jTextFieldS2D2GalonesCalculados.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS2D2GalonesCalculados.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS2D2GalonesCalculados.setText("0");
        jTextFieldS2D2GalonesCalculados.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextFieldS2D2GalonesCalculados.setEnabled(false);
        jPanelS2D2GalonesCalculados.add(jTextFieldS2D2GalonesCalculados);

        jPanelSurtDosDispDos.add(jPanelS2D2GalonesCalculados);

        jPanelS2D2TotalDinero.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS2D2TotalDinero.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelS2D2TotalDinero.setText("Dinero:");
        jPanelS2D2TotalDinero.add(jLabelS2D2TotalDinero);

        jTextFieldS2D2TotalDinero.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS2D2TotalDinero.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS2D2TotalDinero.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldS2D2TotalDineroFocusLost(evt);
            }
        });
        jTextFieldS2D2TotalDinero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldS2D2TotalDineroActionPerformed(evt);
            }
        });
        jPanelS2D2TotalDinero.add(jTextFieldS2D2TotalDinero);

        jPanelSurtDosDispDos.add(jPanelS2D2TotalDinero);

        jPanelS2D2TotalDineroCalculado.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS2D2TotalDineroCalculado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelS2D2TotalDineroCalculado.setText("Dinero Calculado:");
        jPanelS2D2TotalDineroCalculado.add(jLabelS2D2TotalDineroCalculado);

        jTextFieldS2D2TotalDineroCalculado.setEditable(false);
        jTextFieldS2D2TotalDineroCalculado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS2D2TotalDineroCalculado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS2D2TotalDineroCalculado.setText("$0");
        jTextFieldS2D2TotalDineroCalculado.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextFieldS2D2TotalDineroCalculado.setEnabled(false);
        jPanelS2D2TotalDineroCalculado.add(jTextFieldS2D2TotalDineroCalculado);

        jPanelSurtDosDispDos.add(jPanelS2D2TotalDineroCalculado);

        jPanelS2D2DiferenciaDinero.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS2D2Diferencia.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelS2D2Diferencia.setText("Diferencia: ");
        jPanelS2D2DiferenciaDinero.add(jLabelS2D2Diferencia);

        jTextFieldS2D2Diferencia.setEditable(false);
        jTextFieldS2D2Diferencia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS2D2Diferencia.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS2D2Diferencia.setText("$0");
        jTextFieldS2D2Diferencia.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextFieldS2D2Diferencia.setEnabled(false);
        jPanelS2D2DiferenciaDinero.add(jTextFieldS2D2Diferencia);

        jPanelSurtDosDispDos.add(jPanelS2D2DiferenciaDinero);

        jPanelSurtidor2.add(jPanelSurtDosDispDos);

        jPanelContenedorSurtidores.add(jPanelSurtidor2);

        jPanelSurtidor3.setBackground(new java.awt.Color(255, 255, 255));
        jPanelSurtidor3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Surtidor 3 - ACPM", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPanelSurtidor3.setLayout(new java.awt.GridLayout(2, 1, 3, 3));

        jPanelSurtTresDispUno.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pistola 1", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPanelSurtTresDispUno.setLayout(new java.awt.GridLayout(7, 1, 2, 2));

        jPanelS3D1Entregado.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS3D1Entregado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelS3D1Entregado.setText("Entregado:");
        jPanelS3D1Entregado.add(jLabelS3D1Entregado);

        jTextFieldS3D1Entregado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS3D1Entregado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS3D1Entregado.setMaximumSize(new java.awt.Dimension(0, 0));
        jTextFieldS3D1Entregado.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldS3D1EntregadoFocusLost(evt);
            }
        });
        jTextFieldS3D1Entregado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldS3D1EntregadoActionPerformed(evt);
            }
        });
        jPanelS3D1Entregado.add(jTextFieldS3D1Entregado);

        jPanelSurtTresDispUno.add(jPanelS3D1Entregado);

        jPanelS3D1Recibido.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS3D1Recibido.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelS3D1Recibido.setText("Recibido:");
        jPanelS3D1Recibido.add(jLabelS3D1Recibido);

        jTextFieldS3D1Recibido.setEditable(false);
        jTextFieldS3D1Recibido.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS3D1Recibido.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS3D1Recibido.setText("0");
        jTextFieldS3D1Recibido.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextFieldS3D1Recibido.setEnabled(false);
        jPanelS3D1Recibido.add(jTextFieldS3D1Recibido);

        jPanelSurtTresDispUno.add(jPanelS3D1Recibido);

        jPanelS3D1Galones.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS3D1GalonesIngresados.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelS3D1GalonesIngresados.setText("Galones:");
        jPanelS3D1Galones.add(jLabelS3D1GalonesIngresados);

        jTextFieldS3D1GalonesIngresados.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS3D1GalonesIngresados.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS3D1GalonesIngresados.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldS3D1GalonesIngresadosFocusLost(evt);
            }
        });
        jTextFieldS3D1GalonesIngresados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldS3D1GalonesIngresadosActionPerformed(evt);
            }
        });
        jPanelS3D1Galones.add(jTextFieldS3D1GalonesIngresados);

        jPanelSurtTresDispUno.add(jPanelS3D1Galones);

        jPanelS3D1GalonesCalculados.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS3D1GalonesCalculados.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelS3D1GalonesCalculados.setText("Galones Calculados: ");
        jPanelS3D1GalonesCalculados.add(jLabelS3D1GalonesCalculados);

        jTextFieldS3D1GalonesCalculados.setEditable(false);
        jTextFieldS3D1GalonesCalculados.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS3D1GalonesCalculados.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS3D1GalonesCalculados.setText("0");
        jTextFieldS3D1GalonesCalculados.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextFieldS3D1GalonesCalculados.setEnabled(false);
        jPanelS3D1GalonesCalculados.add(jTextFieldS3D1GalonesCalculados);

        jPanelSurtTresDispUno.add(jPanelS3D1GalonesCalculados);

        jPanelS3D1TotalDinero.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS3D1TotalDinero.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelS3D1TotalDinero.setText("Dinero:");
        jPanelS3D1TotalDinero.add(jLabelS3D1TotalDinero);

        jTextFieldS3D1TotalDinero.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS3D1TotalDinero.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS3D1TotalDinero.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldS3D1TotalDineroFocusLost(evt);
            }
        });
        jTextFieldS3D1TotalDinero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldS3D1TotalDineroActionPerformed(evt);
            }
        });
        jPanelS3D1TotalDinero.add(jTextFieldS3D1TotalDinero);

        jPanelSurtTresDispUno.add(jPanelS3D1TotalDinero);

        jPanelS3D1TotalDineroCalculado.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS3D1TotalDineroCalculado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelS3D1TotalDineroCalculado.setText("Dinero Calculado:");
        jPanelS3D1TotalDineroCalculado.add(jLabelS3D1TotalDineroCalculado);

        jTextFieldS3D1TotalDineroCalculado.setEditable(false);
        jTextFieldS3D1TotalDineroCalculado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS3D1TotalDineroCalculado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS3D1TotalDineroCalculado.setText("$0");
        jTextFieldS3D1TotalDineroCalculado.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextFieldS3D1TotalDineroCalculado.setEnabled(false);
        jPanelS3D1TotalDineroCalculado.add(jTextFieldS3D1TotalDineroCalculado);

        jPanelSurtTresDispUno.add(jPanelS3D1TotalDineroCalculado);

        jPanelS3D1DiferenciaDinero.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS3D1Diferencia.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelS3D1Diferencia.setText("Diferencia: ");
        jPanelS3D1DiferenciaDinero.add(jLabelS3D1Diferencia);

        jTextFieldS3D1Diferencia.setEditable(false);
        jTextFieldS3D1Diferencia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS3D1Diferencia.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS3D1Diferencia.setText("$0");
        jTextFieldS3D1Diferencia.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextFieldS3D1Diferencia.setEnabled(false);
        jPanelS3D1DiferenciaDinero.add(jTextFieldS3D1Diferencia);

        jPanelSurtTresDispUno.add(jPanelS3D1DiferenciaDinero);

        jPanelSurtidor3.add(jPanelSurtTresDispUno);

        jPanelSurtTresDispDos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pistola 2", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPanelSurtTresDispDos.setLayout(new java.awt.GridLayout(7, 1, 2, 2));

        jPanelS3D2Entregado.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS3D2Entregado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelS3D2Entregado.setText("Entregado:");
        jPanelS3D2Entregado.add(jLabelS3D2Entregado);

        jTextFieldS3D2Entregado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS3D2Entregado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS3D2Entregado.setMaximumSize(new java.awt.Dimension(0, 0));
        jTextFieldS3D2Entregado.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldS3D2EntregadoFocusLost(evt);
            }
        });
        jTextFieldS3D2Entregado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldS3D2EntregadoActionPerformed(evt);
            }
        });
        jPanelS3D2Entregado.add(jTextFieldS3D2Entregado);

        jPanelSurtTresDispDos.add(jPanelS3D2Entregado);

        jPanelS3D2Recibido.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS3D2Recibido.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelS3D2Recibido.setText("Recibido:");
        jPanelS3D2Recibido.add(jLabelS3D2Recibido);

        jTextFieldS3D2Recibido.setEditable(false);
        jTextFieldS3D2Recibido.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS3D2Recibido.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS3D2Recibido.setText("0");
        jTextFieldS3D2Recibido.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextFieldS3D2Recibido.setEnabled(false);
        jPanelS3D2Recibido.add(jTextFieldS3D2Recibido);

        jPanelSurtTresDispDos.add(jPanelS3D2Recibido);

        jPanelS3D2Galones.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS3D2GalonesIngresados.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelS3D2GalonesIngresados.setText("Galones:");
        jPanelS3D2Galones.add(jLabelS3D2GalonesIngresados);

        jTextFieldS3D2GalonesIngresados.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS3D2GalonesIngresados.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS3D2GalonesIngresados.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldS3D2GalonesIngresadosFocusLost(evt);
            }
        });
        jTextFieldS3D2GalonesIngresados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldS3D2GalonesIngresadosActionPerformed(evt);
            }
        });
        jPanelS3D2Galones.add(jTextFieldS3D2GalonesIngresados);

        jPanelSurtTresDispDos.add(jPanelS3D2Galones);

        jPanelS3D2GalonesCalculados.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS3D2GalonesCalculados.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelS3D2GalonesCalculados.setText("Galones Calculados: ");
        jPanelS3D2GalonesCalculados.add(jLabelS3D2GalonesCalculados);

        jTextFieldS3D2GalonesCalculados.setEditable(false);
        jTextFieldS3D2GalonesCalculados.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS3D2GalonesCalculados.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS3D2GalonesCalculados.setText("0");
        jTextFieldS3D2GalonesCalculados.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextFieldS3D2GalonesCalculados.setEnabled(false);
        jPanelS3D2GalonesCalculados.add(jTextFieldS3D2GalonesCalculados);

        jPanelSurtTresDispDos.add(jPanelS3D2GalonesCalculados);

        jPanelS3D2TotalDinero.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS3D2TotalDinero.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelS3D2TotalDinero.setText("Dinero:");
        jPanelS3D2TotalDinero.add(jLabelS3D2TotalDinero);

        jTextFieldS3D2TotalDinero.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS3D2TotalDinero.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS3D2TotalDinero.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldS3D2TotalDineroFocusLost(evt);
            }
        });
        jTextFieldS3D2TotalDinero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldS3D2TotalDineroActionPerformed(evt);
            }
        });
        jPanelS3D2TotalDinero.add(jTextFieldS3D2TotalDinero);

        jPanelSurtTresDispDos.add(jPanelS3D2TotalDinero);

        jPanelS3D2TotalDineroCalculado.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS3D2TotalDineroCalculado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelS3D2TotalDineroCalculado.setText("Dinero Calculado:");
        jPanelS3D2TotalDineroCalculado.add(jLabelS3D2TotalDineroCalculado);

        jTextFieldS3D2TotalDineroCalculado.setEditable(false);
        jTextFieldS3D2TotalDineroCalculado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS3D2TotalDineroCalculado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS3D2TotalDineroCalculado.setText("$0");
        jTextFieldS3D2TotalDineroCalculado.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextFieldS3D2TotalDineroCalculado.setEnabled(false);
        jPanelS3D2TotalDineroCalculado.add(jTextFieldS3D2TotalDineroCalculado);

        jPanelSurtTresDispDos.add(jPanelS3D2TotalDineroCalculado);

        jPanelS3D2DiferenciaDinero.setLayout(new java.awt.GridLayout(1, 2, 1, 1));

        jLabelS3D2Diferencia.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelS3D2Diferencia.setText("Diferencia: ");
        jPanelS3D2DiferenciaDinero.add(jLabelS3D2Diferencia);

        jTextFieldS3D2Diferencia.setEditable(false);
        jTextFieldS3D2Diferencia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldS3D2Diferencia.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldS3D2Diferencia.setText("$0");
        jTextFieldS3D2Diferencia.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextFieldS3D2Diferencia.setEnabled(false);
        jPanelS3D2DiferenciaDinero.add(jTextFieldS3D2Diferencia);

        jPanelSurtTresDispDos.add(jPanelS3D2DiferenciaDinero);

        jPanelSurtidor3.add(jPanelSurtTresDispDos);

        jPanelContenedorSurtidores.add(jPanelSurtidor3);

        javax.swing.GroupLayout jPanelLiquidacionLayout = new javax.swing.GroupLayout(jPanelLiquidacion);
        jPanelLiquidacion.setLayout(jPanelLiquidacionLayout);
        jPanelLiquidacionLayout.setHorizontalGroup(
            jPanelLiquidacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLiquidacionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLiquidacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelIngresoDinero, 1333, 1333, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanelContenedorSurtidores, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelLiquidacionLayout.setVerticalGroup(
            jPanelLiquidacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLiquidacionLayout.createSequentialGroup()
                .addComponent(jPanelIngresoDinero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelContenedorSurtidores, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(218, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("Liquidacion", jPanelLiquidacion);

        jMenuArchivo.setText("Archivo");

        jMenuItemSalir.setText("Salir");
        jMenuItemSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSalirActionPerformed(evt);
            }
        });
        jMenuArchivo.add(jMenuItemSalir);

        jMenuBar.add(jMenuArchivo);

        jMenuHerramientas.setText("Herramientas");

        jMenuEmpleados.setText("Empleados");

        jMenuItemEmpleadosAgregar.setText("Agregar");
        jMenuItemEmpleadosAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEmpleadosAgregarActionPerformed(evt);
            }
        });
        jMenuEmpleados.add(jMenuItemEmpleadosAgregar);

        jMenuItemEmpleadosEliminar.setText("Eliminar");
        jMenuEmpleados.add(jMenuItemEmpleadosEliminar);

        jMenuHerramientas.add(jMenuEmpleados);

        jMenuBaseDeDatos.setText("Base de Datos");

        jMenuItemBackUp.setText("BackUp");
        jMenuBaseDeDatos.add(jMenuItemBackUp);

        jMenuItemRestaurar.setText("Restaurar");
        jMenuItemRestaurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRestaurarActionPerformed(evt);
            }
        });
        jMenuBaseDeDatos.add(jMenuItemRestaurar);

        jMenuHerramientas.add(jMenuBaseDeDatos);

        jMenuItemActualizarPrecios.setText("Actualizar Precios");
        jMenuItemActualizarPrecios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemActualizarPreciosActionPerformed(evt);
            }
        });
        jMenuHerramientas.add(jMenuItemActualizarPrecios);

        jMenuItemCalibracion.setText("Calibracion");
        jMenuItemCalibracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCalibracionActionPerformed(evt);
            }
        });
        jMenuHerramientas.add(jMenuItemCalibracion);

        jMenuItemExtraLiquidacion.setText("Extra Liquidacion");
        jMenuHerramientas.add(jMenuItemExtraLiquidacion);

        jMenuBar.add(jMenuHerramientas);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleDescription("COLID - Control de Liquidacion Diaria.\nSistema informatico para control de liquidacion diaria en estaciones de servicio EDS. ");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSalirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItemSalirActionPerformed

    private void jMenuItemRestaurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRestaurarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItemRestaurarActionPerformed

    private void jButtonIngresarDineroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIngresarDineroActionPerformed

        /*Cargar la ventana solo si es nula*/
        if (jFrameIngresoDinero == null) {
            jFrameIngresoDinero = new JFrameIngresoDinero(this);
            jFrameIngresoDinero.setLocationRelativeTo(this);
        }
        jFrameIngresoDinero.setVisible(true);
    }//GEN-LAST:event_jButtonIngresarDineroActionPerformed

    private void jButtonGuardarLiquidacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarLiquidacionActionPerformed
        
        /*re-calcular todas las liquidaciones*/
        iniciarRecalculoDeLiquidaciones();

        /*validar islero seleccionado*/
        int position = jComboBoxCambiarIslero.getSelectedIndex();
        if (position == JComboBox.UNDEFINED_CONDITION || position == 0) {
            JOptionPane.showMessageDialog(this, "Selecione un islero de la lista.", "ERROR DE ISLERO", JOptionPane.ERROR_MESSAGE);
            jComboBoxCambiarIslero.requestFocusInWindow();
            return;
        }

        /*validar que ya se ha ingreado el dinero de la liquidacion*/
        if (jTextFieldEntregado.getText().trim().length() <= 2) {
            jButtonIngresarDinero.requestFocusInWindow();
            JOptionPane.showMessageDialog(this, "No se ha ingresado el dinero.", "ERROR DE LIQUIDACION", JOptionPane.ERROR_MESSAGE);
            return;
        }

        /*validar que se ha ingresado el valor en aceites*/
        if (jTextFieldVentasAceites.getText().trim().length() == 0 || !Util.isNumeric(jTextFieldVentasAceites.getText().trim())) {
            jTextFieldVentasAceites.requestFocusInWindow();
            JOptionPane.showMessageDialog(this, "Valor no valido de aceites.\nPuede liquidar en cero.", "ERROR DE LIQUIDACION", JOptionPane.ERROR_MESSAGE);
            return;
        }

        /*validar que se ha ingresado el valor en combustibles del surtidor 1*/
        if (jTextFieldCompraCombustibleSurtidor1.getText().trim().length() == 0 || !Util.isNumeric(jTextFieldCompraCombustibleSurtidor1.getText().trim())) {
            jTextFieldCompraCombustibleSurtidor1.requestFocusInWindow();
            JOptionPane.showMessageDialog(this, "Valor no valido en compra de combustible del surtidor 1.\nPuede liquidar en cero.", "ERROR DE LIQUIDACION", JOptionPane.ERROR_MESSAGE);
            return;
        }

        /*validar que se ha ingresado el valor en combustibles del surtidor 2*/
        if (jTextFieldCompraCombustibleSurtidor2.getText().trim().length() == 0 || !Util.isNumeric(jTextFieldCompraCombustibleSurtidor2.getText().trim())) {
            jTextFieldCompraCombustibleSurtidor2.requestFocusInWindow();
            JOptionPane.showMessageDialog(this, "Valor no valido en compra de combustible del surtidor 2.\nPuede liquidar en cero.", "ERROR DE LIQUIDACION", JOptionPane.ERROR_MESSAGE);
            return;
        }

        /*validar que se ha ingresado el valor en combustibles del surtidor 3*/
        if (jTextFieldCompraCombustibleSurtidor3.getText().trim().length() == 0 || !Util.isNumeric(jTextFieldCompraCombustibleSurtidor3.getText().trim())) {
            jTextFieldCompraCombustibleSurtidor3.requestFocusInWindow();
            JOptionPane.showMessageDialog(this, "Valor no valido en compra de combustible del surtidor 3.\nPuede liquidar en cero.", "ERROR DE LIQUIDACION", JOptionPane.ERROR_MESSAGE);
            return;
        }

        /*Validar que todos los campos sean validos*/
        try {
            validarDatosDeLiquidaciones();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "ERROR DE LIQUIDACION", JOptionPane.ERROR_MESSAGE);
            return;
        }

        /*Si todas las validaciones son correctas se crear la cabecera de liquidacion
        para ser insertada.*/
        mostrarResumenDeLiquidacion();
    }//GEN-LAST:event_jButtonGuardarLiquidacionActionPerformed

    private void jMenuItemActualizarPreciosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemActualizarPreciosActionPerformed
        JFrameActualizarPrecios jFrameActualizarPrecios = new JFrameActualizarPrecios();
        jFrameActualizarPrecios.setLocationRelativeTo(this);
        jFrameActualizarPrecios.setVisible(true);
    }//GEN-LAST:event_jMenuItemActualizarPreciosActionPerformed

    /**
     * Evento de ventana abierta para cargar la informacion de la ultima
     * liquidacion registrada en base de datos.
     *
     * @param evt
     */
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        /*Poner el focus inicial en el islero a liquidar*/
        jButtonIngresarDinero.requestFocusInWindow();

        /*intentar cargar la infromacion de la ultima liquidacion registrada en base de datos*/
        try {
            this.ultimaLiquidacion = ControllerBO.cargarUltimaLiquidacion();
            if (this.ultimaLiquidacion != null) {
                this.listaUltimaLiquidacionDispensador = ControllerBO.cargarLiquidacionDispensadores(ultimaLiquidacion);
                if (this.listaUltimaLiquidacionDispensador.isEmpty()) {
                    throw new Exception("No se logra cargar informacion de la liquidacion de surtidores anterior.\nIntente restaurar la ultima base de datos.");
                }
            } else {
                throw new Exception("No se logra cargar informacion de la liquidacion anterior.\nIntente restaurar la ultima base de datos.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "ERROR DE LECTURA EN BASE DE DATOS", JOptionPane.ERROR_MESSAGE);
            jButtonIngresarDinero.setEnabled(false);
            jButtonGuardarLiquidacion.setEnabled(false);
        }
    }//GEN-LAST:event_formWindowOpened

    private void jTextFieldS1D1EntregadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldS1D1EntregadoActionPerformed
        jTextFieldS1D1GalonesIngresados.requestFocusInWindow();
    }//GEN-LAST:event_jTextFieldS1D1EntregadoActionPerformed

    private void jTextFieldS1D1GalonesIngresadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldS1D1GalonesIngresadosActionPerformed
        jTextFieldS1D1TotalDinero.requestFocusInWindow();
    }//GEN-LAST:event_jTextFieldS1D1GalonesIngresadosActionPerformed

    private void jTextFieldS1D1TotalDineroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldS1D1TotalDineroActionPerformed
        jTextFieldS1D2Entregado.requestFocusInWindow();
    }//GEN-LAST:event_jTextFieldS1D1TotalDineroActionPerformed

    private void jTextFieldS1D2EntregadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldS1D2EntregadoActionPerformed
        jTextFieldS1D2GalonesIngresados.requestFocusInWindow();
    }//GEN-LAST:event_jTextFieldS1D2EntregadoActionPerformed

    private void jTextFieldS1D2GalonesIngresadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldS1D2GalonesIngresadosActionPerformed
        jTextFieldS1D2TotalDinero.requestFocusInWindow();
    }//GEN-LAST:event_jTextFieldS1D2GalonesIngresadosActionPerformed

    private void jTextFieldS1D2TotalDineroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldS1D2TotalDineroActionPerformed
        jTextFieldS2D1Entregado.requestFocusInWindow();
    }//GEN-LAST:event_jTextFieldS1D2TotalDineroActionPerformed

    private void jTextFieldS2D1EntregadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldS2D1EntregadoActionPerformed
        jTextFieldS2D1GalonesIngresados.requestFocusInWindow();
    }//GEN-LAST:event_jTextFieldS2D1EntregadoActionPerformed

    private void jTextFieldS2D1GalonesIngresadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldS2D1GalonesIngresadosActionPerformed
        jTextFieldS2D1TotalDinero.requestFocusInWindow();
    }//GEN-LAST:event_jTextFieldS2D1GalonesIngresadosActionPerformed

    private void jTextFieldS2D1TotalDineroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldS2D1TotalDineroActionPerformed
        jTextFieldS2D2Entregado.requestFocusInWindow();
    }//GEN-LAST:event_jTextFieldS2D1TotalDineroActionPerformed

    private void jTextFieldS2D2EntregadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldS2D2EntregadoActionPerformed
        jTextFieldS2D2GalonesIngresados.requestFocusInWindow();
    }//GEN-LAST:event_jTextFieldS2D2EntregadoActionPerformed

    private void jTextFieldS2D2GalonesIngresadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldS2D2GalonesIngresadosActionPerformed
        jTextFieldS2D2TotalDinero.requestFocusInWindow();
    }//GEN-LAST:event_jTextFieldS2D2GalonesIngresadosActionPerformed

    private void jTextFieldS2D2TotalDineroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldS2D2TotalDineroActionPerformed
        jTextFieldS3D1Entregado.requestFocusInWindow();
    }//GEN-LAST:event_jTextFieldS2D2TotalDineroActionPerformed

    private void jTextFieldS3D1EntregadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldS3D1EntregadoActionPerformed
        jTextFieldS3D1GalonesIngresados.requestFocusInWindow();
    }//GEN-LAST:event_jTextFieldS3D1EntregadoActionPerformed

    private void jTextFieldS3D1GalonesIngresadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldS3D1GalonesIngresadosActionPerformed
        jTextFieldS3D1TotalDinero.requestFocusInWindow();
    }//GEN-LAST:event_jTextFieldS3D1GalonesIngresadosActionPerformed

    private void jTextFieldS3D1TotalDineroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldS3D1TotalDineroActionPerformed
        jTextFieldS3D2Entregado.requestFocusInWindow();
    }//GEN-LAST:event_jTextFieldS3D1TotalDineroActionPerformed

    private void jTextFieldS3D2EntregadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldS3D2EntregadoActionPerformed
        jTextFieldS3D2GalonesIngresados.requestFocusInWindow();
    }//GEN-LAST:event_jTextFieldS3D2EntregadoActionPerformed

    private void jTextFieldS3D2GalonesIngresadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldS3D2GalonesIngresadosActionPerformed
        jTextFieldS3D2TotalDinero.requestFocusInWindow();
    }//GEN-LAST:event_jTextFieldS3D2GalonesIngresadosActionPerformed

    private void jComboBoxCambiarIsleroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCambiarIsleroActionPerformed
        int position = jComboBoxCambiarIslero.getSelectedIndex() - 1;
        if (position != JComboBox.UNDEFINED_CONDITION) {
            int idEmpleado = ControllerBO.cargarListaEmpleadosIsleros().get(position).getIdEmpleado();
            for (LiquidacionDispensador liquidacionDispensador : liquidacionesPorDispensador) {
                liquidacionDispensador.setIdEmpleadoLiquidado(idEmpleado);
            }
        }
    }//GEN-LAST:event_jComboBoxCambiarIsleroActionPerformed

    private void jTextFieldCompraCombustibleSurtidor1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCompraCombustibleSurtidor1ActionPerformed
        jTextFieldCompraCombustibleSurtidor2.requestFocusInWindow();
    }//GEN-LAST:event_jTextFieldCompraCombustibleSurtidor1ActionPerformed

    private void jTextFieldCompraCombustibleSurtidor2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCompraCombustibleSurtidor2ActionPerformed
        jTextFieldCompraCombustibleSurtidor3.requestFocusInWindow();
    }//GEN-LAST:event_jTextFieldCompraCombustibleSurtidor2ActionPerformed

    private void jTextFieldCompraCombustibleSurtidor3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCompraCombustibleSurtidor3ActionPerformed
        jButtonGuardarLiquidacion.requestFocusInWindow();
    }//GEN-LAST:event_jTextFieldCompraCombustibleSurtidor3ActionPerformed

    /**
     * Iniciar el calculo de la liquidacion despues de ingresar un valor en el
     * numero de lectura del dispensador 1.
     *
     * @param evt
     */
    private void jTextFieldS1D1EntregadoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldS1D1EntregadoFocusLost
        String numeroEntregado = jTextFieldS1D1Entregado.getText().trim();
        if (jTextFieldS1D1Recibido.getText().trim().length() > 1 && (numeroEntregado.length() == 0 || !Util.isNumeric(numeroEntregado))) {
            jTextFieldS1D1Entregado.setBackground(Color.red);
            jTextFieldS1D1Entregado.requestFocusInWindow();
            return;
        }
        if (numeroEntregado.length() > 3 && !Util.isNumeric(numeroEntregado)) {
            jTextFieldS1D1Entregado.setBackground(Color.red);
            jTextFieldS1D1Entregado.requestFocusInWindow();
        } else if (Util.isNumeric(numeroEntregado)) {
            jTextFieldS1D1Entregado.setBackground(Color.white);
            /*cargar liquidacion del dispensador 1 del surtidor 1*/
            cargarLiquidacionSurtidor(SURTIDOR1, DISPENSADOR1);
            /*Si ya se ingreso dinero, recalcular la diferencia*/
            if (jTextFieldS1D1TotalDinero.getText().trim().length() > 0) {
                jTextFieldS1D1TotalDineroFocusLost(null);
            }
        }

    }//GEN-LAST:event_jTextFieldS1D1EntregadoFocusLost

    private void jTextFieldS1D1TotalDineroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldS1D1TotalDineroFocusLost
        String dineroEntregado = jTextFieldS1D1TotalDinero.getText().trim();
        if (jTextFieldS1D1Recibido.getText().trim().length() > 1 && jTextFieldS1D1Entregado.getText().trim().length() > 0 && Util.isNumeric(jTextFieldS1D1GalonesIngresados.getText().trim())) {
            if (dineroEntregado.length() == 0 || !Util.isNumeric(dineroEntregado)) {
                jTextFieldS1D1TotalDinero.setBackground(Color.red);
                jTextFieldS1D1TotalDinero.requestFocusInWindow();
            } else if (Util.isNumeric(dineroEntregado)) {
                jTextFieldS1D1TotalDinero.setBackground(Color.white);
                double dineroCalculado = Double.parseDouble(jTextFieldS1D1TotalDineroCalculado.getText().trim().replace("$", "").replace(".", "").replace(",", "."));
                jTextFieldS1D1Diferencia.setText("$" + Util.formatearMiles(calcularDiferenciaDinero(Double.parseDouble(dineroEntregado), dineroCalculado)));

                {
                    /*crear el objeto liquidacion actual*/
                    double numeroEntregado = Double.parseDouble(jTextFieldS1D1Entregado.getText().trim());
                    double numeroRecibido = Double.parseDouble(jTextFieldS1D1Entregado.getText().trim());
                    double galones = Double.parseDouble(jTextFieldS1D1GalonesIngresados.getText().trim());
                    double galonesCalculados = Double.parseDouble(jTextFieldS1D1GalonesCalculados.getText().trim().replace("$", "").replace(".", "").replace(",", "."));
                    double diferencia = Double.parseDouble(jTextFieldS1D1Diferencia.getText().trim().replace("$", "").replace(".", "").replace(",", "."));
                    int position = DISPENSADOR1 - 1;

                    /*Guardar la liquidacion del dispensador 1 en el array en la posicion por parametro*/
                    guardarLiquidacionDispensadorEnLista(numeroEntregado, numeroRecibido, galones, galonesCalculados, Double.parseDouble(dineroEntregado), dineroCalculado, diferencia, position);
                    /*calcular el total de liquidacion de combustibles*/
                    calcularTotalCombustibles();
                }
            }
        }
    }//GEN-LAST:event_jTextFieldS1D1TotalDineroFocusLost

    private void jTextFieldS1D2EntregadoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldS1D2EntregadoFocusLost
        String numeroEntregado = jTextFieldS1D2Entregado.getText().trim();
        if (jTextFieldS1D2Recibido.getText().trim().length() > 1 && (numeroEntregado.length() == 0 || !Util.isNumeric(numeroEntregado))) {
            jTextFieldS1D2Entregado.setBackground(Color.red);
            jTextFieldS1D2Entregado.requestFocusInWindow();
            return;
        }
        if (numeroEntregado.length() > 3 && !Util.isNumeric(numeroEntregado)) {
            jTextFieldS1D2Entregado.setBackground(Color.red);
            jTextFieldS1D2Entregado.requestFocusInWindow();
        } else if (Util.isNumeric(numeroEntregado)) {
            jTextFieldS1D2Entregado.setBackground(Color.white);
            cargarLiquidacionSurtidor(SURTIDOR1, DISPENSADOR2);
            /*Si ya se ingreso dinero, recalcular la diferencia*/
            if (jTextFieldS1D2TotalDinero.getText().trim().length() > 0) {
                jTextFieldS1D2TotalDineroFocusLost(null);
            }
        }
    }//GEN-LAST:event_jTextFieldS1D2EntregadoFocusLost

    private void jTextFieldS2D1EntregadoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldS2D1EntregadoFocusLost
        String numeroEntregado = jTextFieldS2D1Entregado.getText().trim();

        if (jTextFieldS2D1Recibido.getText().trim().length() > 1 && (numeroEntregado.length() == 0 || !Util.isNumeric(numeroEntregado))) {
            jTextFieldS2D1Entregado.setBackground(Color.red);
            jTextFieldS2D1Entregado.requestFocusInWindow();
            return;
        }
        if (numeroEntregado.length() > 3 && !Util.isNumeric(numeroEntregado)) {
            jTextFieldS2D1Entregado.setBackground(Color.red);
            jTextFieldS2D1Entregado.requestFocusInWindow();
        } else if (Util.isNumeric(numeroEntregado)) {
            jTextFieldS2D1Entregado.setBackground(Color.white);
            /*cargar liquidacion del dispensador 1 del surtidor 1*/
            cargarLiquidacionSurtidor(SURTIDOR2, DISPENSADOR3);
            /*Si ya se ingreso dinero, recalcular la diferencia*/
            if (jTextFieldS2D1TotalDinero.getText().trim().length() > 0) {
                jTextFieldS2D1TotalDineroFocusLost(null);
            }
        }
    }//GEN-LAST:event_jTextFieldS2D1EntregadoFocusLost

    private void jTextFieldS2D2EntregadoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldS2D2EntregadoFocusLost
        String numeroEntregado = jTextFieldS2D2Entregado.getText().trim();
        if (jTextFieldS2D2Recibido.getText().trim().length() > 1 && (numeroEntregado.length() == 0 || !Util.isNumeric(numeroEntregado))) {
            jTextFieldS2D2Entregado.setBackground(Color.red);
            jTextFieldS2D2Entregado.requestFocusInWindow();
            return;
        }
        if (numeroEntregado.length() > 3 && !Util.isNumeric(numeroEntregado)) {
            jTextFieldS2D2Entregado.setBackground(Color.red);
            jTextFieldS2D2Entregado.requestFocusInWindow();
        } else if (Util.isNumeric(numeroEntregado)) {
            jTextFieldS2D2Entregado.setBackground(Color.white);
            /*cargar liquidacion del dispensador 1 del surtidor 1*/
            cargarLiquidacionSurtidor(SURTIDOR2, DISPENSADOR4);
            /*Si ya se ingreso dinero, recalcular la diferencia*/
            if (jTextFieldS2D2TotalDinero.getText().trim().length() > 0) {
                jTextFieldS2D2TotalDineroFocusLost(null);
            }
        }
    }//GEN-LAST:event_jTextFieldS2D2EntregadoFocusLost

    private void jTextFieldS3D1EntregadoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldS3D1EntregadoFocusLost
        String numeroEntregado = jTextFieldS3D1Entregado.getText().trim();
        if (jTextFieldS3D1Recibido.getText().trim().length() > 1 && (numeroEntregado.length() == 0 || !Util.isNumeric(numeroEntregado))) {
            jTextFieldS3D1Entregado.setBackground(Color.red);
            jTextFieldS3D1Entregado.requestFocusInWindow();
            return;
        }
        if (numeroEntregado.length() > 3 && !Util.isNumeric(numeroEntregado)) {
            jTextFieldS3D1Entregado.setBackground(Color.red);
            jTextFieldS3D1Entregado.requestFocusInWindow();
        } else if (Util.isNumeric(numeroEntregado)) {
            jTextFieldS3D1Entregado.setBackground(Color.white);
            /*cargar liquidacion del dispensador 1 del surtidor 1*/
            cargarLiquidacionSurtidor(SURTIDOR3, DISPENSADOR5);
            /*Si ya se ingreso dinero, recalcular la diferencia*/
            if (jTextFieldS3D1TotalDinero.getText().trim().length() > 0) {
                jTextFieldS3D1TotalDineroFocusLost(null);
            }
        }
    }//GEN-LAST:event_jTextFieldS3D1EntregadoFocusLost

    private void jTextFieldS3D2EntregadoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldS3D2EntregadoFocusLost
        String numeroEntregado = jTextFieldS3D2Entregado.getText().trim();
        if (jTextFieldS3D2Recibido.getText().trim().length() > 1 && (numeroEntregado.length() == 0 || !Util.isNumeric(numeroEntregado))) {
            jTextFieldS3D2Entregado.setBackground(Color.red);
            jTextFieldS3D2Entregado.requestFocusInWindow();
            return;
        }
        if (numeroEntregado.length() > 3 && !Util.isNumeric(numeroEntregado)) {
            jTextFieldS3D2Entregado.setBackground(Color.red);
            jTextFieldS3D2Entregado.requestFocusInWindow();
        } else if (Util.isNumeric(numeroEntregado)) {
            jTextFieldS3D2Entregado.setBackground(Color.white);
            /*cargar liquidacion del dispensador 1 del surtidor 1*/
            cargarLiquidacionSurtidor(SURTIDOR3, DISPENSADOR6);
            /*Si ya se ingreso dinero, recalcular la diferencia*/
            if (jTextFieldS3D2TotalDinero.getText().trim().length() > 0) {
                jTextFieldS3D2TotalDineroFocusLost(null);
            }
        }
    }//GEN-LAST:event_jTextFieldS3D2EntregadoFocusLost

    private void jTextFieldS2D2TotalDineroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldS2D2TotalDineroFocusLost
        String dineroEntregado = jTextFieldS2D2TotalDinero.getText().trim();
        if (jTextFieldS2D2Recibido.getText().trim().length() > 1 && jTextFieldS2D2Entregado.getText().trim().length() > 0 && Util.isNumeric(jTextFieldS2D2GalonesIngresados.getText().trim())) {
            if (dineroEntregado.length() == 0 || !Util.isNumeric(dineroEntregado)) {
                jTextFieldS2D2TotalDinero.setBackground(Color.red);
                jTextFieldS2D2TotalDinero.requestFocusInWindow();
            } else if (Util.isNumeric(dineroEntregado)) {
                jTextFieldS2D2TotalDinero.setBackground(Color.white);
                double dineroCalculado = Double.parseDouble(jTextFieldS2D2TotalDineroCalculado.getText().trim().replace("$", "").replace(".", "").replace(",", "."));
                jTextFieldS2D2Diferencia.setText("$" + Util.formatearMiles(calcularDiferenciaDinero(Double.parseDouble(dineroEntregado), dineroCalculado)));

                {
                    /*crear el objeto liquidacion actual*/
                    double numeroEntregado = Double.parseDouble(jTextFieldS2D2Entregado.getText().trim());
                    double numeroRecibido = Double.parseDouble(jTextFieldS2D2Entregado.getText().trim());
                    double galones = Double.parseDouble(jTextFieldS2D2GalonesIngresados.getText().trim());
                    double galonesCalculados = Double.parseDouble(jTextFieldS2D2GalonesCalculados.getText().trim().replace("$", "").replace(".", "").replace(",", "."));
                    double diferencia = Double.parseDouble(jTextFieldS2D2Diferencia.getText().trim().replace("$", "").replace(".", "").replace(",", "."));
                    int position = DISPENSADOR4 - 1;

                    /*Guardar la liquidacion del dispensador 1 en el array en la posicion por parametro*/
                    guardarLiquidacionDispensadorEnLista(numeroEntregado, numeroRecibido, galones, galonesCalculados, Double.parseDouble(dineroEntregado), dineroCalculado, diferencia, position);
                    /*calcular el total de liquidacion de combustibles*/
                    calcularTotalCombustibles();
                }
            }
        }
    }//GEN-LAST:event_jTextFieldS2D2TotalDineroFocusLost

    private void jTextFieldS2D1TotalDineroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldS2D1TotalDineroFocusLost
        String dineroEntregado = jTextFieldS2D1TotalDinero.getText().trim();
        if (jTextFieldS2D1Recibido.getText().trim().length() > 1 && jTextFieldS2D1Entregado.getText().trim().length() > 0 && Util.isNumeric(jTextFieldS2D1GalonesIngresados.getText().trim())) {
            if (dineroEntregado.length() == 0 || !Util.isNumeric(dineroEntregado)) {
                jTextFieldS2D1TotalDinero.setBackground(Color.red);
                jTextFieldS2D1TotalDinero.requestFocusInWindow();
            } else if (Util.isNumeric(dineroEntregado)) {
                jTextFieldS2D1TotalDinero.setBackground(Color.white);
                double dineroCalculado = Double.parseDouble(jTextFieldS2D1TotalDineroCalculado.getText().trim().replace("$", "").replace(".", "").replace(",", "."));
                jTextFieldS2D1Diferencia.setText("$" + Util.formatearMiles(calcularDiferenciaDinero(Double.parseDouble(dineroEntregado), dineroCalculado)));

                {
                    /*crear el objeto liquidacion actual*/
                    double numeroEntregado = Double.parseDouble(jTextFieldS2D1Entregado.getText().trim());
                    double numeroRecibido = Double.parseDouble(jTextFieldS2D1Entregado.getText().trim());
                    double galones = Double.parseDouble(jTextFieldS2D1GalonesIngresados.getText().trim());
                    double galonesCalculados = Double.parseDouble(jTextFieldS2D1GalonesCalculados.getText().trim().replace("$", "").replace(".", "").replace(",", "."));
                    double diferencia = Double.parseDouble(jTextFieldS2D1Diferencia.getText().trim().replace("$", "").replace(".", "").replace(",", "."));
                    int position = DISPENSADOR3 - 1;

                    /*Guardar la liquidacion del dispensador 1 en el array en la posicion por parametro*/
                    guardarLiquidacionDispensadorEnLista(numeroEntregado, numeroRecibido, galones, galonesCalculados, Double.parseDouble(dineroEntregado), dineroCalculado, diferencia, position);
                    /*calcular el total de liquidacion de combustibles*/
                    calcularTotalCombustibles();
                }
            }
        }
    }//GEN-LAST:event_jTextFieldS2D1TotalDineroFocusLost

    private void jTextFieldS1D2TotalDineroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldS1D2TotalDineroFocusLost
        String dineroEntregado = jTextFieldS1D2TotalDinero.getText().trim();
        if (jTextFieldS1D2Recibido.getText().trim().length() > 1 && jTextFieldS1D2Entregado.getText().trim().length() > 0 && Util.isNumeric(jTextFieldS1D2GalonesIngresados.getText().trim())) {
            if (dineroEntregado.length() == 0 || !Util.isNumeric(dineroEntregado)) {
                jTextFieldS1D2TotalDinero.setBackground(Color.red);
                jTextFieldS1D2TotalDinero.requestFocusInWindow();
            } else if (Util.isNumeric(dineroEntregado)) {
                jTextFieldS1D2TotalDinero.setBackground(Color.white);
                double dineroCalculado = Double.parseDouble(jTextFieldS1D2TotalDineroCalculado.getText().trim().replace("$", "").replace(".", "").replace(",", "."));
                jTextFieldS1D2Diferencia.setText("$" + Util.formatearMiles(calcularDiferenciaDinero(Double.parseDouble(dineroEntregado), dineroCalculado)));

                {
                    /*crear el objeto liquidacion actual*/
                    double numeroEntregado = Double.parseDouble(jTextFieldS1D2Entregado.getText().trim());
                    double numeroRecibido = Double.parseDouble(jTextFieldS1D2Entregado.getText().trim());
                    double galones = Double.parseDouble(jTextFieldS1D2GalonesIngresados.getText().trim());
                    double galonesCalculados = Double.parseDouble(jTextFieldS1D2GalonesCalculados.getText().trim().replace("$", "").replace(".", "").replace(",", "."));
                    double diferencia = Double.parseDouble(jTextFieldS1D2Diferencia.getText().trim().replace("$", "").replace(".", "").replace(",", "."));
                    int position = DISPENSADOR2 - 1;

                    /*Guardar la liquidacion del dispensador 1 en el array en la posicion por parametro*/
                    guardarLiquidacionDispensadorEnLista(numeroEntregado, numeroRecibido, galones, galonesCalculados, Double.parseDouble(dineroEntregado), dineroCalculado, diferencia, position);
                    /*calcular el total de liquidacion de combustibles*/
                    calcularTotalCombustibles();
                }
            }
        }
    }//GEN-LAST:event_jTextFieldS1D2TotalDineroFocusLost

    private void jTextFieldS3D1TotalDineroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldS3D1TotalDineroFocusLost
        String dineroEntregado = jTextFieldS3D1TotalDinero.getText().trim();
        if (jTextFieldS3D1Recibido.getText().trim().length() > 1 && jTextFieldS3D1Entregado.getText().trim().length() > 0 && Util.isNumeric(jTextFieldS3D1GalonesIngresados.getText().trim())) {
            if (dineroEntregado.length() == 0 || !Util.isNumeric(dineroEntregado)) {
                jTextFieldS3D1TotalDinero.setBackground(Color.red);
                jTextFieldS3D1TotalDinero.requestFocusInWindow();
            } else if (Util.isNumeric(dineroEntregado)) {
                jTextFieldS3D1TotalDinero.setBackground(Color.white);
                double dineroCalculado = Double.parseDouble(jTextFieldS3D1TotalDineroCalculado.getText().trim().replace("$", "").replace(".", "").replace(",", "."));
                jTextFieldS3D1Diferencia.setText("$" + Util.formatearMiles(calcularDiferenciaDinero(Double.parseDouble(dineroEntregado), dineroCalculado)));

                {
                    /*crear el objeto liquidacion actual*/
                    double numeroEntregado = Double.parseDouble(jTextFieldS3D1Entregado.getText().trim());
                    double numeroRecibido = Double.parseDouble(jTextFieldS3D1Entregado.getText().trim());
                    double galones = Double.parseDouble(jTextFieldS3D1GalonesIngresados.getText().trim());
                    double galonesCalculados = Double.parseDouble(jTextFieldS3D1GalonesCalculados.getText().trim().replace("$", "").replace(".", "").replace(",", "."));
                    double diferencia = Double.parseDouble(jTextFieldS3D1Diferencia.getText().trim().replace("$", "").replace(".", "").replace(",", "."));
                    int position = DISPENSADOR5 - 1;

                    /*Guardar la liquidacion del dispensador 1 en el array en la posicion por parametro*/
                    guardarLiquidacionDispensadorEnLista(numeroEntregado, numeroRecibido, galones, galonesCalculados, Double.parseDouble(dineroEntregado), dineroCalculado, diferencia, position);
                    /*calcular el total de liquidacion de combustibles*/
                    calcularTotalCombustibles();
                }
            }
        }
    }//GEN-LAST:event_jTextFieldS3D1TotalDineroFocusLost

    private void jTextFieldS3D2TotalDineroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldS3D2TotalDineroFocusLost
        String dineroEntregado = jTextFieldS3D2TotalDinero.getText().trim();
        if (jTextFieldS3D2Recibido.getText().trim().length() > 1 && jTextFieldS3D2Entregado.getText().trim().length() > 0 && Util.isNumeric(jTextFieldS3D2GalonesIngresados.getText().trim())) {
            if (dineroEntregado.length() == 0 || !Util.isNumeric(dineroEntregado)) {
                jTextFieldS3D2TotalDinero.setBackground(Color.red);
                jTextFieldS3D2TotalDinero.requestFocusInWindow();
            } else if (Util.isNumeric(dineroEntregado)) {
                jTextFieldS3D2TotalDinero.setBackground(Color.white);
                double dineroCalculado = Double.parseDouble(jTextFieldS3D2TotalDineroCalculado.getText().trim().replace("$", "").replace(".", "").replace(",", "."));
                jTextFieldS3D2Diferencia.setText("$" + Util.formatearMiles(calcularDiferenciaDinero(Double.parseDouble(dineroEntregado), dineroCalculado)));

                {
                    /*crear el objeto liquidacion actual*/
                    double numeroEntregado = Double.parseDouble(jTextFieldS3D2Entregado.getText().trim());
                    double numeroRecibido = Double.parseDouble(jTextFieldS3D2Entregado.getText().trim());
                    double galones = Double.parseDouble(jTextFieldS3D2GalonesIngresados.getText().trim());
                    double galonesCalculados = Double.parseDouble(jTextFieldS3D2GalonesCalculados.getText().trim().replace("$", "").replace(".", "").replace(",", "."));
                    double diferencia = Double.parseDouble(jTextFieldS3D2Diferencia.getText().trim().replace("$", "").replace(".", "").replace(",", "."));
                    int position = DISPENSADOR6 - 1;

                    /*Guardar la liquidacion del dispensador 1 en el array en la posicion por parametro*/
                    guardarLiquidacionDispensadorEnLista(numeroEntregado, numeroRecibido, galones, galonesCalculados, Double.parseDouble(dineroEntregado), dineroCalculado, diferencia, position);
                    /*calcular el total de liquidacion de combustibles*/
                    calcularTotalCombustibles();
                }
            }
        }
    }//GEN-LAST:event_jTextFieldS3D2TotalDineroFocusLost

    private void jTextFieldS3D2TotalDineroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldS3D2TotalDineroActionPerformed
        jButtonGuardarLiquidacion.requestFocusInWindow();
    }//GEN-LAST:event_jTextFieldS3D2TotalDineroActionPerformed

    private void jTextFieldS1D1GalonesIngresadosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldS1D1GalonesIngresadosFocusLost
        String galonesIngresados = jTextFieldS1D1GalonesIngresados.getText().trim();
        if (jTextFieldS1D1Recibido.getText().trim().length() > 1 && jTextFieldS1D1Entregado.getText().trim().length() > 0) {
            if (galonesIngresados.length() == 0 || !Util.isNumeric(galonesIngresados)) {
                jTextFieldS1D1GalonesIngresados.setBackground(Color.red);
                jTextFieldS1D1GalonesIngresados.requestFocusInWindow();
            } else if (Util.isNumeric(galonesIngresados)) {
                jTextFieldS1D1GalonesIngresados.setBackground(Color.white);
            }
        }
    }//GEN-LAST:event_jTextFieldS1D1GalonesIngresadosFocusLost

    private void jTextFieldS1D2GalonesIngresadosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldS1D2GalonesIngresadosFocusLost
        String galonesIngresados = jTextFieldS1D2GalonesIngresados.getText().trim();
        if (jTextFieldS1D2Recibido.getText().trim().length() > 1 && jTextFieldS1D2Entregado.getText().trim().length() > 0) {
            if (galonesIngresados.length() == 0 || !Util.isNumeric(galonesIngresados)) {
                jTextFieldS1D2GalonesIngresados.setBackground(Color.red);
                jTextFieldS1D2GalonesIngresados.requestFocusInWindow();
            } else if (Util.isNumeric(galonesIngresados)) {
                jTextFieldS1D2GalonesIngresados.setBackground(Color.white);
            }
        }
    }//GEN-LAST:event_jTextFieldS1D2GalonesIngresadosFocusLost

    private void jTextFieldS2D1GalonesIngresadosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldS2D1GalonesIngresadosFocusLost
        String galonesIngresados = jTextFieldS2D1GalonesIngresados.getText().trim();
        if (jTextFieldS2D1Recibido.getText().trim().length() > 1 && jTextFieldS2D1Entregado.getText().trim().length() > 0) {
            if (galonesIngresados.length() == 0 || !Util.isNumeric(galonesIngresados)) {
                jTextFieldS2D1GalonesIngresados.setBackground(Color.red);
                jTextFieldS2D1GalonesIngresados.requestFocusInWindow();
            } else if (Util.isNumeric(galonesIngresados)) {
                jTextFieldS2D1GalonesIngresados.setBackground(Color.white);
            }
        }
    }//GEN-LAST:event_jTextFieldS2D1GalonesIngresadosFocusLost

    private void jTextFieldS2D2GalonesIngresadosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldS2D2GalonesIngresadosFocusLost
        String galonesIngresados = jTextFieldS2D2GalonesIngresados.getText().trim();
        if (jTextFieldS2D2Recibido.getText().trim().length() > 1 && jTextFieldS2D2Entregado.getText().trim().length() > 0) {
            if (galonesIngresados.length() == 0 || !Util.isNumeric(galonesIngresados)) {
                jTextFieldS2D2GalonesIngresados.setBackground(Color.red);
                jTextFieldS2D2GalonesIngresados.requestFocusInWindow();
            } else if (Util.isNumeric(galonesIngresados)) {
                jTextFieldS2D2GalonesIngresados.setBackground(Color.white);
            }
        }
    }//GEN-LAST:event_jTextFieldS2D2GalonesIngresadosFocusLost

    private void jTextFieldS3D1GalonesIngresadosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldS3D1GalonesIngresadosFocusLost
        String galonesIngresados = jTextFieldS3D1GalonesIngresados.getText().trim();
        if (jTextFieldS3D1Recibido.getText().trim().length() > 1 && jTextFieldS3D1Entregado.getText().trim().length() > 0) {
            if (galonesIngresados.length() == 0 || !Util.isNumeric(galonesIngresados)) {
                jTextFieldS3D1GalonesIngresados.setBackground(Color.red);
                jTextFieldS3D1GalonesIngresados.requestFocusInWindow();
            } else if (Util.isNumeric(galonesIngresados)) {
                jTextFieldS3D1GalonesIngresados.setBackground(Color.white);
            }
        }
    }//GEN-LAST:event_jTextFieldS3D1GalonesIngresadosFocusLost

    private void jTextFieldS3D2GalonesIngresadosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldS3D2GalonesIngresadosFocusLost
        String galonesIngresados = jTextFieldS3D2GalonesIngresados.getText().trim();
        if (jTextFieldS3D2Recibido.getText().trim().length() > 1 && jTextFieldS3D2Entregado.getText().trim().length() > 0) {
            if (galonesIngresados.length() == 0 || !Util.isNumeric(galonesIngresados)) {
                jTextFieldS3D2GalonesIngresados.setBackground(Color.red);
                jTextFieldS3D2GalonesIngresados.requestFocusInWindow();
            } else if (Util.isNumeric(galonesIngresados)) {
                jTextFieldS3D2GalonesIngresados.setBackground(Color.white);
            }
        }
    }//GEN-LAST:event_jTextFieldS3D2GalonesIngresadosFocusLost

    /**
     * Validar que el total de aceites ingresados sea un numero valido.
     *
     * @param evt
     */
    private void jTextFieldVentasAceitesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldVentasAceitesFocusLost
        String sTotalAceites = jTextFieldVentasAceites.getText().trim();
        if (sTotalAceites.length() > 1 && !Util.isNumeric(sTotalAceites)) {
            jTextFieldVentasAceites.setBackground(Color.red);
        } else if (Util.isNumeric(sTotalAceites)) {
            jTextFieldVentasAceites.setBackground(Color.white);
            calcularLiquidacionTotal();
        }
    }//GEN-LAST:event_jTextFieldVentasAceitesFocusLost

    private void jTextFieldCompraCombustibleSurtidor1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldCompraCombustibleSurtidor1FocusLost
        String sTotalComprado = jTextFieldCompraCombustibleSurtidor1.getText().trim();
        if (sTotalComprado.length() > 1 && !Util.isNumeric(sTotalComprado)) {
            jTextFieldCompraCombustibleSurtidor1.setBackground(Color.red);
        } else if (Util.isNumeric(sTotalComprado)) {
            jTextFieldCompraCombustibleSurtidor1.setBackground(Color.white);
        }
    }//GEN-LAST:event_jTextFieldCompraCombustibleSurtidor1FocusLost

    private void jTextFieldCompraCombustibleSurtidor2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldCompraCombustibleSurtidor2FocusLost
        String sTotalComprado = jTextFieldCompraCombustibleSurtidor2.getText().trim();
        if (sTotalComprado.length() > 1 && !Util.isNumeric(sTotalComprado)) {
            jTextFieldCompraCombustibleSurtidor2.setBackground(Color.red);
        } else if (Util.isNumeric(sTotalComprado)) {
            jTextFieldCompraCombustibleSurtidor2.setBackground(Color.white);
        }
    }//GEN-LAST:event_jTextFieldCompraCombustibleSurtidor2FocusLost

    private void jTextFieldCompraCombustibleSurtidor3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldCompraCombustibleSurtidor3FocusLost
        String sTotalComprado = jTextFieldCompraCombustibleSurtidor3.getText().trim();
        if (sTotalComprado.length() > 1 && !Util.isNumeric(sTotalComprado)) {
            jTextFieldCompraCombustibleSurtidor3.setBackground(Color.red);
        } else if (Util.isNumeric(sTotalComprado)) {
            jTextFieldCompraCombustibleSurtidor3.setBackground(Color.white);
        }
    }//GEN-LAST:event_jTextFieldCompraCombustibleSurtidor3FocusLost

    private void jTextFieldVentasAceitesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldVentasAceitesActionPerformed
        jTextFieldCompraCombustibleSurtidor1.requestFocusInWindow();
    }//GEN-LAST:event_jTextFieldVentasAceitesActionPerformed

    private void jMenuItemCalibracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCalibracionActionPerformed
        JFrameCalibracion jFrameCalibracion = new JFrameCalibracion();
        jFrameCalibracion.setLocationRelativeTo(this);
        jFrameCalibracion.setVisible(true);
    }//GEN-LAST:event_jMenuItemCalibracionActionPerformed

    private void jMenuItemEmpleadosAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEmpleadosAgregarActionPerformed
        JFrameAgregarEmpleados jFrameAgregarEmpleados = new JFrameAgregarEmpleados();
        jFrameAgregarEmpleados.setLocationRelativeTo(this);
        jFrameAgregarEmpleados.setVisible(true);
    }//GEN-LAST:event_jMenuItemEmpleadosAgregarActionPerformed

  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonGuardarLiquidacion;
    private javax.swing.JButton jButtonIngresarDinero;
    private javax.swing.JComboBox<String> jComboBoxCambiarIslero;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelFecha;
    private javax.swing.JLabel jLabelPrecioAcpm;
    private javax.swing.JLabel jLabelPrecioGasolinaCorriente;
    private javax.swing.JLabel jLabelS1D1Diferencia;
    private javax.swing.JLabel jLabelS1D1Entregado;
    private javax.swing.JLabel jLabelS1D1GalonesCalculados;
    private javax.swing.JLabel jLabelS1D1GalonesIngresados;
    private javax.swing.JLabel jLabelS1D1Recibido;
    private javax.swing.JLabel jLabelS1D1TotalDinero;
    private javax.swing.JLabel jLabelS1D1TotalDineroCalculado;
    private javax.swing.JLabel jLabelS1D2Diferencia;
    private javax.swing.JLabel jLabelS1D2Entregado;
    private javax.swing.JLabel jLabelS1D2GalonesCalculados;
    private javax.swing.JLabel jLabelS1D2GalonesIngresados;
    private javax.swing.JLabel jLabelS1D2Recibido;
    private javax.swing.JLabel jLabelS1D2TotalDinero;
    private javax.swing.JLabel jLabelS1D2TotalDineroCalculado;
    private javax.swing.JLabel jLabelS2D1Diferencia;
    private javax.swing.JLabel jLabelS2D1Entregado;
    private javax.swing.JLabel jLabelS2D1GalonesCalculados;
    private javax.swing.JLabel jLabelS2D1GalonesIngresados;
    private javax.swing.JLabel jLabelS2D1Recibido;
    private javax.swing.JLabel jLabelS2D1TotalDinero;
    private javax.swing.JLabel jLabelS2D1TotalDineroCalculado;
    private javax.swing.JLabel jLabelS2D2Diferencia;
    private javax.swing.JLabel jLabelS2D2Entregado;
    private javax.swing.JLabel jLabelS2D2GalonesCalculados;
    private javax.swing.JLabel jLabelS2D2GalonesIngresados;
    private javax.swing.JLabel jLabelS2D2Recibido;
    private javax.swing.JLabel jLabelS2D2TotalDinero;
    private javax.swing.JLabel jLabelS2D2TotalDineroCalculado;
    private javax.swing.JLabel jLabelS3D1Diferencia;
    private javax.swing.JLabel jLabelS3D1Entregado;
    private javax.swing.JLabel jLabelS3D1GalonesCalculados;
    private javax.swing.JLabel jLabelS3D1GalonesIngresados;
    private javax.swing.JLabel jLabelS3D1Recibido;
    private javax.swing.JLabel jLabelS3D1TotalDinero;
    private javax.swing.JLabel jLabelS3D1TotalDineroCalculado;
    private javax.swing.JLabel jLabelS3D2Diferencia;
    private javax.swing.JLabel jLabelS3D2Entregado;
    private javax.swing.JLabel jLabelS3D2GalonesCalculados;
    private javax.swing.JLabel jLabelS3D2GalonesIngresados;
    private javax.swing.JLabel jLabelS3D2Recibido;
    private javax.swing.JLabel jLabelS3D2TotalDinero;
    private javax.swing.JLabel jLabelS3D2TotalDineroCalculado;
    private javax.swing.JMenu jMenuArchivo;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenu jMenuBaseDeDatos;
    private javax.swing.JMenu jMenuEmpleados;
    private javax.swing.JMenu jMenuHerramientas;
    private javax.swing.JMenuItem jMenuItemActualizarPrecios;
    private javax.swing.JMenuItem jMenuItemBackUp;
    private javax.swing.JMenuItem jMenuItemCalibracion;
    private javax.swing.JMenuItem jMenuItemEmpleadosAgregar;
    private javax.swing.JMenuItem jMenuItemEmpleadosEliminar;
    private javax.swing.JMenuItem jMenuItemExtraLiquidacion;
    private javax.swing.JMenuItem jMenuItemRestaurar;
    private javax.swing.JMenuItem jMenuItemSalir;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelAceites;
    private javax.swing.JPanel jPanelCambiarIslero;
    private javax.swing.JPanel jPanelCombustibles;
    private javax.swing.JPanel jPanelContenedorSurtidores;
    private javax.swing.JPanel jPanelEnpleado;
    private javax.swing.JPanel jPanelIngresoDinero;
    private javax.swing.JPanel jPanelLiquidacion;
    private javax.swing.JPanel jPanelPreciosActuales;
    private javax.swing.JPanel jPanelPreciosActualesAcpm;
    private javax.swing.JPanel jPanelPreciosActualesFecha;
    private javax.swing.JPanel jPanelPreciosActualesGasolinaCorriente;
    private javax.swing.JPanel jPanelResumenLiquidacion;
    private javax.swing.JPanel jPanelS1D1DiferenciaDinero;
    private javax.swing.JPanel jPanelS1D1Entregado;
    private javax.swing.JPanel jPanelS1D1Galones;
    private javax.swing.JPanel jPanelS1D1GalonesCalculados;
    private javax.swing.JPanel jPanelS1D1Recibido;
    private javax.swing.JPanel jPanelS1D1TotalDinero;
    private javax.swing.JPanel jPanelS1D1TotalDineroCalculado;
    private javax.swing.JPanel jPanelS1D2DiferenciaDinero;
    private javax.swing.JPanel jPanelS1D2Entregado;
    private javax.swing.JPanel jPanelS1D2Galones;
    private javax.swing.JPanel jPanelS1D2GalonesCalculados;
    private javax.swing.JPanel jPanelS1D2Recibido;
    private javax.swing.JPanel jPanelS1D2TotalDinero;
    private javax.swing.JPanel jPanelS1D2TotalDineroCalculado;
    private javax.swing.JPanel jPanelS2D1DiferenciaDinero;
    private javax.swing.JPanel jPanelS2D1Entregado;
    private javax.swing.JPanel jPanelS2D1Galones;
    private javax.swing.JPanel jPanelS2D1GalonesCalculados;
    private javax.swing.JPanel jPanelS2D1Recibido;
    private javax.swing.JPanel jPanelS2D1TotalDinero;
    private javax.swing.JPanel jPanelS2D1TotalDineroCalculado;
    private javax.swing.JPanel jPanelS2D2DiferenciaDinero;
    private javax.swing.JPanel jPanelS2D2Entregado;
    private javax.swing.JPanel jPanelS2D2Galones;
    private javax.swing.JPanel jPanelS2D2GalonesCalculados;
    private javax.swing.JPanel jPanelS2D2Recibido;
    private javax.swing.JPanel jPanelS2D2TotalDinero;
    private javax.swing.JPanel jPanelS2D2TotalDineroCalculado;
    private javax.swing.JPanel jPanelS3D1DiferenciaDinero;
    private javax.swing.JPanel jPanelS3D1Entregado;
    private javax.swing.JPanel jPanelS3D1Galones;
    private javax.swing.JPanel jPanelS3D1GalonesCalculados;
    private javax.swing.JPanel jPanelS3D1Recibido;
    private javax.swing.JPanel jPanelS3D1TotalDinero;
    private javax.swing.JPanel jPanelS3D1TotalDineroCalculado;
    private javax.swing.JPanel jPanelS3D2DiferenciaDinero;
    private javax.swing.JPanel jPanelS3D2Entregado;
    private javax.swing.JPanel jPanelS3D2Galones;
    private javax.swing.JPanel jPanelS3D2GalonesCalculados;
    private javax.swing.JPanel jPanelS3D2Recibido;
    private javax.swing.JPanel jPanelS3D2TotalDinero;
    private javax.swing.JPanel jPanelS3D2TotalDineroCalculado;
    private javax.swing.JPanel jPanelSurtDosDispDos;
    private javax.swing.JPanel jPanelSurtDosDispUno;
    private javax.swing.JPanel jPanelSurtTresDispDos;
    private javax.swing.JPanel jPanelSurtTresDispUno;
    private javax.swing.JPanel jPanelSurtUnoDispDos;
    private javax.swing.JPanel jPanelSurtUnoDispUno;
    private javax.swing.JPanel jPanelSurtidor1;
    private javax.swing.JPanel jPanelSurtidor2;
    private javax.swing.JPanel jPanelSurtidor3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTextField jTextFieldCompraCombustibleSurtidor1;
    private javax.swing.JTextField jTextFieldCompraCombustibleSurtidor2;
    private javax.swing.JTextField jTextFieldCompraCombustibleSurtidor3;
    private javax.swing.JTextField jTextFieldDiferencia;
    private javax.swing.JTextField jTextFieldEntregado;
    private javax.swing.JTextField jTextFieldS1D1Diferencia;
    private javax.swing.JTextField jTextFieldS1D1Entregado;
    private javax.swing.JTextField jTextFieldS1D1GalonesCalculados;
    private javax.swing.JTextField jTextFieldS1D1GalonesIngresados;
    private javax.swing.JTextField jTextFieldS1D1Recibido;
    private javax.swing.JTextField jTextFieldS1D1TotalDinero;
    private javax.swing.JTextField jTextFieldS1D1TotalDineroCalculado;
    private javax.swing.JTextField jTextFieldS1D2Diferencia;
    private javax.swing.JTextField jTextFieldS1D2Entregado;
    private javax.swing.JTextField jTextFieldS1D2GalonesCalculados;
    private javax.swing.JTextField jTextFieldS1D2GalonesIngresados;
    private javax.swing.JTextField jTextFieldS1D2Recibido;
    private javax.swing.JTextField jTextFieldS1D2TotalDinero;
    private javax.swing.JTextField jTextFieldS1D2TotalDineroCalculado;
    private javax.swing.JTextField jTextFieldS2D1Diferencia;
    private javax.swing.JTextField jTextFieldS2D1Entregado;
    private javax.swing.JTextField jTextFieldS2D1GalonesCalculados;
    private javax.swing.JTextField jTextFieldS2D1GalonesIngresados;
    private javax.swing.JTextField jTextFieldS2D1Recibido;
    private javax.swing.JTextField jTextFieldS2D1TotalDinero;
    private javax.swing.JTextField jTextFieldS2D1TotalDineroCalculado;
    private javax.swing.JTextField jTextFieldS2D2Diferencia;
    private javax.swing.JTextField jTextFieldS2D2Entregado;
    private javax.swing.JTextField jTextFieldS2D2GalonesCalculados;
    private javax.swing.JTextField jTextFieldS2D2GalonesIngresados;
    private javax.swing.JTextField jTextFieldS2D2Recibido;
    private javax.swing.JTextField jTextFieldS2D2TotalDinero;
    private javax.swing.JTextField jTextFieldS2D2TotalDineroCalculado;
    private javax.swing.JTextField jTextFieldS3D1Diferencia;
    private javax.swing.JTextField jTextFieldS3D1Entregado;
    private javax.swing.JTextField jTextFieldS3D1GalonesCalculados;
    private javax.swing.JTextField jTextFieldS3D1GalonesIngresados;
    private javax.swing.JTextField jTextFieldS3D1Recibido;
    private javax.swing.JTextField jTextFieldS3D1TotalDinero;
    private javax.swing.JTextField jTextFieldS3D1TotalDineroCalculado;
    private javax.swing.JTextField jTextFieldS3D2Diferencia;
    private javax.swing.JTextField jTextFieldS3D2Entregado;
    private javax.swing.JTextField jTextFieldS3D2GalonesCalculados;
    private javax.swing.JTextField jTextFieldS3D2GalonesIngresados;
    private javax.swing.JTextField jTextFieldS3D2Recibido;
    private javax.swing.JTextField jTextFieldS3D2TotalDinero;
    private javax.swing.JTextField jTextFieldS3D2TotalDineroCalculado;
    private javax.swing.JTextField jTextFieldTotalCombustibles;
    private javax.swing.JTextField jTextFieldTotalLiquidado;
    private javax.swing.JTextField jTextFieldVentasAceites;
    // End of variables declaration//GEN-END:variables

    /**
     * Metodo para cargar el comboBox de empleados de rol islero con los datos
     * disponibles en BD.
     */
    private void cargarComboIsleros() {
        /*Marcar posicion por defecto y asignarla al combo*/
        jComboBoxCambiarIslero.insertItemAt("Seleccione Islero - ", POSITION_DEFAULT);
        jComboBoxCambiarIslero.setSelectedIndex(POSITION_DEFAULT);

        /*Agregar los items con los datos de los empleados con rol islero.*/
        ControllerBO.cargarListaEmpleadosIsleros().forEach((islero) -> {
            jComboBoxCambiarIslero.addItem(islero.getNombres() + " " + islero.getApellidos());
        });
    }

    /**
     * Metodo que permite cargar los precios vigentes de combustibles
     */
    private void cargarPreciosCombustiblesVigente() {
        try {

            /*cargar los precios vigentes desde BD*/
            this.precioCorriente = ControllerBO.cargarCombustiblePorTipo(Combustible.CORRIENTE).getPrecioVigente();
            this.precioAcpm = ControllerBO.cargarCombustiblePorTipo(Combustible.ACPM).getPrecioVigente();

            /*mostrar los precios vigentes al usuario*/
            jLabelPrecioGasolinaCorriente.setText("$" + Util.formatearMiles(precioCorriente));
            jLabelPrecioAcpm.setText("$" + Util.formatearMiles(precioAcpm));
        } catch (Exception ex) {
            Logger.getLogger(JFrameLiquidacion.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage(), " Error ", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Mostrar la fecha actual
     */
    private void cargarFechaActual() {
        jLabelFecha.setText(Util.getFechaActual().toUpperCase(new Locale("es", "CO")));
    }

    @Override
    public void dineroIngresado(double totalDineroIngresado) {
        this.totalDineroIngresado = totalDineroIngresado;
        mostrarDineroIngresado();
    }

    /**
     * Metodo para cargar la informacion de los surtidores
     */
    private void cargarIdentificadorDeSurtidores() {
        ControllerBO.cargarListaSurtidores().forEach(surtidor -> {
            switch (surtidor.getIdSurtidor()) {
                case SURTIDOR1:
                    jPanelSurtidor1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, surtidor.getCodigoIdentificador() + " " + Util.formatearMiles(surtidor.getGalonaje()) + " GL.", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
                    break;
                case SURTIDOR2:
                    jPanelSurtidor2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, surtidor.getCodigoIdentificador() + " " + Util.formatearMiles(surtidor.getGalonaje()) + " GL.", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
                    break;
                case SURTIDOR3:
                    jPanelSurtidor3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, surtidor.getCodigoIdentificador() + " " + Util.formatearMiles(surtidor.getGalonaje()) + " GL.", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
                    break;
            }
        });
    }

    /**
     * Metodo que se encarga de seleccionar el surtidor y dispensador adecuado
     * para cargar y mostrar la informacion de la liquidacion.
     *
     * @param surtidor
     * @param dispensador
     */
    private void cargarLiquidacionSurtidor(int surtidor, int dispensador) {

        for (LiquidacionDispensador liquidacionDispensador : listaUltimaLiquidacionDispensador) {
            /*Cargar la informacion de los dispensadores del surtidor 1*/
            if (surtidor == SURTIDOR1 && dispensador == DISPENSADOR1 && liquidacionDispensador.getIdDispensador() == dispensador) {
                cargarInformacionSurtidor1Dispensador1(liquidacionDispensador.getNumeroRecibido());
                break;
            }
            if (surtidor == SURTIDOR1 && dispensador == DISPENSADOR2 && liquidacionDispensador.getIdDispensador() == dispensador) {
                cargarInformacionSurtidor1Dispensador2(liquidacionDispensador.getNumeroRecibido());
                break;
            }

            /*Cargar la informacion de los dispensadores del surtidor 2*/
            if (surtidor == SURTIDOR2 && dispensador == DISPENSADOR3 && liquidacionDispensador.getIdDispensador() == dispensador) {
                cargarInformacionSurtidor2Dispensador1(liquidacionDispensador.getNumeroRecibido());
                break;
            }
            if (surtidor == SURTIDOR2 && dispensador == DISPENSADOR4 && liquidacionDispensador.getIdDispensador() == dispensador) {
                cargarInformacionSurtidor2Dispensador2(liquidacionDispensador.getNumeroRecibido());
                break;
            }

            /*Cargar la informacion de los dispensadores del surtidor 3*/
            if (surtidor == SURTIDOR3 && dispensador == DISPENSADOR5 && liquidacionDispensador.getIdDispensador() == dispensador) {
                cargarInformacionSurtidor3Dispensador1(liquidacionDispensador.getNumeroRecibido());
                break;
            }
            if (surtidor == SURTIDOR3 && dispensador == DISPENSADOR6 && liquidacionDispensador.getIdDispensador() == dispensador) {
                cargarInformacionSurtidor3Dispensador2(liquidacionDispensador.getNumeroRecibido());
                break;
            }
        }
    }

    /**
     * Cargar la informacion del dispensador 1 del surtidor 1.
     */
    private void cargarInformacionSurtidor1Dispensador1(double numeroRecibido) {
        double entregado = Double.parseDouble(jTextFieldS1D1Entregado.getText().trim());
        double galonesCalculados = Util.round(entregado - numeroRecibido, 2);
        double dineroCalculado = Util.round(galonesCalculados * this.precioCorriente, 2);
        jTextFieldS1D1Recibido.setText(String.valueOf(Util.formatearMiles(numeroRecibido)));
        jTextFieldS1D1GalonesCalculados.setText(String.valueOf(Util.formatearMiles(galonesCalculados)));
        jTextFieldS1D1TotalDineroCalculado.setText("$" + String.valueOf(Util.formatearMiles(dineroCalculado)));
    }

    /**
     * Cargar la informacion del dispensador 2 del surtidor 1
     *
     * @param numeroRecibido
     */
    private void cargarInformacionSurtidor1Dispensador2(double numeroRecibido) {
        double entregado = Double.parseDouble(jTextFieldS1D2Entregado.getText().trim());
        double galonesCalculados = Util.round(entregado - numeroRecibido, 2);
        double dineroCalculado = Util.round(galonesCalculados * this.precioCorriente, 2);
        jTextFieldS1D2Recibido.setText(String.valueOf(Util.formatearMiles(numeroRecibido)));
        jTextFieldS1D2GalonesCalculados.setText(String.valueOf(Util.formatearMiles(galonesCalculados)));
        jTextFieldS1D2TotalDineroCalculado.setText("$" + String.valueOf(Util.formatearMiles(dineroCalculado)));
    }

    /**
     * Cargar la informacion del dispensador 1 del surtidor 2
     *
     * @param numeroRecibido
     */
    private void cargarInformacionSurtidor2Dispensador1(double numeroRecibido) {
        double entregado = Double.parseDouble(jTextFieldS2D1Entregado.getText().trim());
        double galonesCalculados = Util.round(entregado - numeroRecibido, 2);
        double dineroCalculado = Util.round(galonesCalculados * this.precioCorriente, 2);
        jTextFieldS2D1Recibido.setText(String.valueOf(Util.formatearMiles(numeroRecibido)));
        jTextFieldS2D1GalonesCalculados.setText(String.valueOf(Util.formatearMiles(galonesCalculados)));
        jTextFieldS2D1TotalDineroCalculado.setText("$" + String.valueOf(Util.formatearMiles(dineroCalculado)));
    }

    /**
     * Cargar la informacion del dispensador 2 del surtidor 2
     *
     * @param numeroRecibido
     */
    private void cargarInformacionSurtidor2Dispensador2(double numeroRecibido) {
        double entregado = Double.parseDouble(jTextFieldS2D2Entregado.getText().trim());
        double galonesCalculados = Util.round(entregado - numeroRecibido, 2);
        double dineroCalculado = Util.round(galonesCalculados * this.precioCorriente, 2);
        jTextFieldS2D2Recibido.setText(String.valueOf(Util.formatearMiles(numeroRecibido)));
        jTextFieldS2D2GalonesCalculados.setText(String.valueOf(Util.formatearMiles(galonesCalculados)));
        jTextFieldS2D2TotalDineroCalculado.setText("$" + String.valueOf(Util.formatearMiles(dineroCalculado)));
    }

    /**
     * Cargar la informacion del dispensador 1 del surtidor 3
     *
     * @param numeroRecibido
     */
    private void cargarInformacionSurtidor3Dispensador1(double numeroRecibido) {
        double entregado = Double.parseDouble(jTextFieldS3D1Entregado.getText().trim());
        double galonesCalculados = Util.round(entregado - numeroRecibido, 2);
        double dineroCalculado = Util.round(galonesCalculados * this.precioAcpm, 2);
        jTextFieldS3D1Recibido.setText(String.valueOf(Util.formatearMiles(numeroRecibido)));
        jTextFieldS3D1GalonesCalculados.setText(String.valueOf(Util.formatearMiles(galonesCalculados)));
        jTextFieldS3D1TotalDineroCalculado.setText("$" + String.valueOf(Util.formatearMiles(dineroCalculado)));
    }

    /**
     * Cargar la informacion del dispensador 2 del surtidor 3
     *
     * @param numeroRecibido
     */
    private void cargarInformacionSurtidor3Dispensador2(double numeroRecibido) {
        double entregado = Double.parseDouble(jTextFieldS3D2Entregado.getText().trim());
        double galonesCalculados = Util.round(entregado - numeroRecibido, 2);
        double dineroCalculado = Util.round(galonesCalculados * this.precioAcpm, 2);
        jTextFieldS3D2Recibido.setText(String.valueOf(Util.formatearMiles(numeroRecibido)));
        jTextFieldS3D2GalonesCalculados.setText(String.valueOf(Util.formatearMiles(galonesCalculados)));
        jTextFieldS3D2TotalDineroCalculado.setText("$" + String.valueOf(Util.formatearMiles(dineroCalculado)));
    }

    /**
     * Metodo que calcula la dirferencia entre dos cantidades de dinero
     * ingresadas.
     *
     * @param dineroIngresado
     * @param dineroCalculado
     * @return la diferencia entre el dinero ingresado y el dinero calculado.
     */
    private double calcularDiferenciaDinero(double dineroIngresado, double dineroCalculado) {
        return Util.round(dineroIngresado - dineroCalculado, 2);
    }

    /**
     * Guardar los datos de la liquidacion actual en el array
     *
     * @param numeroEntregado
     * @param numeroRecibido
     * @param galones
     * @param galonesCalculados
     * @param dineroEntregado
     * @param dineroCalculado
     * @param diferencia
     * @param position
     */
    private void guardarLiquidacionDispensadorEnLista(double numeroEntregado, double numeroRecibido, double galones, double galonesCalculados, double dineroEntregado, double dineroCalculado, double diferencia, int position) {
        liquidacionesPorDispensador[position].setNumeroEntregado(numeroEntregado);
        liquidacionesPorDispensador[position].setNumeroRecibido(numeroRecibido);
        liquidacionesPorDispensador[position].setGalones(galones);
        liquidacionesPorDispensador[position].setGalonesCalculados(galonesCalculados);
        liquidacionesPorDispensador[position].setDineroEntregado(dineroEntregado);
        liquidacionesPorDispensador[position].setDineroCalculado(dineroCalculado);
        liquidacionesPorDispensador[position].setDiferenciaDinero(diferencia);
    }

    /**
     * Recalculoar todas las liquidaciones antes de guardar la informacion de la
     * liquidacion en base de datos.
     */
    private void iniciarRecalculoDeLiquidaciones() {
        jTextFieldS1D1EntregadoFocusLost(null);
        jTextFieldS1D2EntregadoFocusLost(null);
        jTextFieldS2D1EntregadoFocusLost(null);
        jTextFieldS2D2EntregadoFocusLost(null);
        jTextFieldS3D1EntregadoFocusLost(null);
        jTextFieldS3D2EntregadoFocusLost(null);
    }

    /**
     * Calcular el total de combustibles liquidado hasta el momento.
     */
    private void calcularTotalCombustibles() {
        double totalCombustibles = 0;
        double totalDiferencia = 0;
        for (LiquidacionDispensador liquidacionDispensador : liquidacionesPorDispensador) {
            totalCombustibles += liquidacionDispensador.getDineroEntregado();
        }
        jTextFieldTotalCombustibles.setText("$" + Util.formatearMiles(Util.round(totalCombustibles, 2)));
    }

    /**
     * Validar que los campos ingresados para liquidar sean correctos.
     *
     * @throws Exception
     */
    private void validarDatosDeLiquidaciones() throws Exception {
        for (LiquidacionDispensador liquidacionDispensador : liquidacionesPorDispensador) {
            if (liquidacionDispensador.getNumeroEntregado() == 0) {
                throw new Exception("Hay liquidaciones con numero entregado no validos!.\nVerifique los datos de liquidacion.");
            }
            if (liquidacionDispensador.getGalones() == 0) {
                throw new Exception("Hay liquidaciones con galones no validos!.\nVerifique los datos de liquidacion.");
            }
            if (liquidacionDispensador.getDineroEntregado() == 0) {
                throw new Exception("Hay liquidaciones con dinero entregado no validos!.\nVerifique los datos de liquidacion.");
            }
        }
    }

    /**
     * Calcular la cabecera de la liquidacion actual.
     */
    private void mostrarResumenDeLiquidacion() {
        String fecha = Util.getFechaActual().toUpperCase(new Locale("es", "CO"));
        String hora = Util.getHoraActual().toUpperCase(new Locale("es", "CO"));
        String islero = jComboBoxCambiarIslero.getSelectedItem().toString().trim();
        String mensajeResumen = "<html><h3><U><p align=center>RESUMEN</p></U></h3>"
                + "<ul>"
                + "<li>FECHA:&nbsp;&nbsp;&nbsp;"
                + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                + "<b>" + fecha + "</b></li>"
                + "<li>HORA:&nbsp;&nbsp;&nbsp;"
                + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                + "<b>" + hora + "</b></li>"
                + "<li>LIQUIDADOR:&nbsp;&nbsp;&nbsp;&nbsp;<b>JORGE CASTRO</b></li>"
                + "<li>ISLERO:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                + "<b>" + islero + " </li>"
                + "<li>DINERO ENTREGADO:&nbsp;<b><font color= blue>$" + Util.formatearMiles(this.totalDineroIngresado) + "</font></b></li>"
                + "<li>TOTAL LIQUIDACION:&nbsp;<b><font color= blue>$" + Util.formatearMiles(this.totalLiquidado) + "</font></b></li>"
                + "<li>DIFERENCIA:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                + "<b><font color= blue>$" + Util.formatearMiles(this.totalDineroIngresado - this.totalLiquidado) + "</font> </b></li>"
                + "</ul>"
                + "<p><b>Si esta correcto por favor seleccione la opcion <b>SI</b>."
                + "<br>De lo contrario cierre esta ventana o seleccione <b>NO</b>.</b></p></html>";

        /*Mostrar un resumen de la liquidacioin actual*/
        int opcion = JOptionPane.showConfirmDialog(this, mensajeResumen, "RESUMEN", JOptionPane.OK_OPTION);
        if (opcion == 0) {
            confirmarInsercionDeLiquidacion();
        }
    }

    /**
     * Calcular el total de la liquidacion actual.
     */
    private void calcularLiquidacionTotal() {
        /*obtener el total de aceites ingresado, si no hay nada ingresado se calcula como cero*/
        String sTotalAceites = jTextFieldVentasAceites.getText().trim();
        double totalAceites = (sTotalAceites.length() > 0) ? Double.parseDouble(sTotalAceites) : 0.0;

        /*Total de dinero entregado por liquidacion de combustibles*/
        double totalCombustibles = 0;

        /*total de diferencia de dinero por cada liquidacion de surtidor*/
        double totalDiferencias = 0;

        for (LiquidacionDispensador liquidacionDispensador : liquidacionesPorDispensador) {
            totalCombustibles += liquidacionDispensador.getDineroEntregado();
            totalDiferencias += liquidacionDispensador.getDiferenciaDinero();
        }
        /*asignar el total de liquidacion*/
        this.totalLiquidado = Util.round((totalAceites + totalCombustibles + totalDiferencias), 2);

        /*Mostrar el total de liquidacion*/
        jTextFieldTotalLiquidado.setText("$" + Util.formatearMiles(this.totalLiquidado));
        mostrarDineroIngresado();
    }

    private void mostrarDineroIngresado() {
        jTextFieldEntregado.setText("$" + Util.formatearMiles(this.totalDineroIngresado));
        jTextFieldDiferencia.setText("$" + Util.formatearMiles(totalDineroIngresado - this.totalLiquidado));
        if ((totalDineroIngresado - this.totalLiquidado) < 0) {
            jTextFieldDiferencia.setDisabledTextColor(Color.red);
        } else {
            jTextFieldDiferencia.setDisabledTextColor(Color.black);
        }

    }

    /**
     * Mostrar ultima confirmacion al usuario antes de insertar la liquidacion.
     */
    private void confirmarInsercionDeLiquidacion() {
        String mensaje = "<html><h3><b>Si esta seguro de guardar "
                + "la liquidacion actual seleccione aceptar."
                + "<br>De lo contrario cancele la transaccion.</b></h3></html>";
        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "¿GUARDAR LIQUIDACION?", JOptionPane.CANCEL_OPTION);
        if (opcion == 0) {
            insertarLiquidacion();
        }
    }

    /**
     * Iniciar el hilo de insercion de la liquidacion actual.
     */
    private void insertarLiquidacion() {

        /*obtener el total de aceites ingresado, si no hay nada ingresado se calcula como cero*/
        String sTotalAceites = jTextFieldVentasAceites.getText().trim();
        double totalAceites = (sTotalAceites.length() > 0) ? Double.parseDouble(sTotalAceites) : 0.0;

        /*Total de dinero entregado por liquidacion de combustibles*/
        double totalCombustibles = 0;

        /*total de diferencia de dinero por cada liquidacion de surtidor*/
        double totalDiferencias = 0;

        String textLiquidacionesDispensador = "";
        for (LiquidacionDispensador liquidacionDispensador : liquidacionesPorDispensador) {
            totalCombustibles += liquidacionDispensador.getDineroEntregado();

            /*Texto que contiene la informacion de liquidacion de cada surtidor*/
            textLiquidacionesDispensador
                    += liquidacionDispensador.getIdSurtidor() + ";"
                    + liquidacionDispensador.getIdDispensador() + ";"
                    + liquidacionDispensador.getIdPrecio() + ";"
                    + liquidacionDispensador.getIdEmpleadoLiquidado() + ";"
                    + liquidacionDispensador.getNumeroEntregado() + ";"
                    + liquidacionDispensador.getNumeroRecibido() + ";"
                    + liquidacionDispensador.getGalones() + ";"
                    + liquidacionDispensador.getGalonesCalculados() + ";"
                    + liquidacionDispensador.getDineroEntregado() + ";"
                    + liquidacionDispensador.getDineroCalculado() + ";"
                    + liquidacionDispensador.getDiferenciaDinero() + "*";
        }

        /*crear el worker encargado de crear los hilos para insercion*/
        WorkerBO worked = new WorkerBO(empleadoLiquidador.getIdEmpleado(),
                totalCombustibles,
                totalAceites,
                this.totalLiquidado,
                this.totalDineroIngresado,
                (totalDineroIngresado - this.totalLiquidado),
                textLiquidacionesDispensador);
        worked.execute();
//        JOptionPane.showMessageDialog(this, "Datos insertados de forma correcta.", "DATOS INSERTADOR", JOptionPane.OK_OPTION);
    }
}
