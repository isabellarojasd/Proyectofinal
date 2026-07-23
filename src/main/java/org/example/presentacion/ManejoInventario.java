package org.example.presentacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Ventana para la gestión del inventario.
 * Aplica HERENCIA desde VentaBase e INTERFAZ mediante INavegación.
 */
public class ManejoInventario extends VentaBase implements INavegación {

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
        // Llama al constructor de VentaBase: super(Título, Ancho, Alto)
        super("Gestión de Inventario", 800, 600);

        if (panelInventario != null) {
            setContentPane(panelInventario);
        }

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

    // --- MÉTODOS IMPLEMENTADOS DE VentaBase Y INavegación ---

    @Override
    public JPanel getPanelPrincipal() {
        return this.panelInventario;
    }

    @Override
    public void abrir() {
        this.setVisible(true);
    }

    @Override
    public void cerrar() {
        this.dispose();
    }

    @Override
    public void limpiarFormulario() {
        if (txtBusqueda != null) txtBusqueda.setText("");
        if (txtNombre != null) txtNombre.setText("");
        if (txtDescrpcion != null) txtDescrpcion.setText("");
        if (txtPrecio != null) txtPrecio.setText("");
        if (txtStock != null) txtStock.setText("");
    }
}