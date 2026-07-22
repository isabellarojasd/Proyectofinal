package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Ventana para la gestión del inventario.
 */
public class ManejoInventario extends JFrame {

    private JPanel panelInventario;

    private JTextField txtBusqueda;
    private JTextField txtNombre;
    private JTextField txtDescrpcion;
    private JTextField txtPrecio;
    private JTextField txtStock;

    private JButton btnBuscar;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnExportarCSV;

    private JTable tblProductos;

    /**
     * Constructor que inicializa la ventana de inventario.
     */
    public ManejoInventario() {

        setContentPane(panelInventario);

        setTitle("Gestión de Inventario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        configurarTabla();
    }

    /**
     * Configura el modelo y las columnas de la tabla de productos.
     */
    private void configurarTabla() {
        String[] columnas = {"ID", "Nombre", "Descripción", "Precio", "Stock"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        tblProductos.setModel(modelo);
    }
}