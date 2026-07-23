package org.example.presentacion;

import org.example.cliente.ServicioCliente;
import org.example.modelo.Producto;
import org.example.protocolo.Respuesta;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.util.List;

/**
 * Ventana para la gestión del inventario con conexión SSL.
 */
public class ManejoInventario extends VentaBase implements INavegación {

    // Instancia del servicio cliente (Host: localhost, Puerto SSL: 8443)
    private ServicioCliente servicioCliente;

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

    public ManejoInventario() {
        super("Gestión de Inventario", 800, 600);

        if (panelInventario != null) {
            setContentPane(panelInventario);
        }

        // 1. Inicializamos el servicio apuntando al servidor SSL
        this.servicioCliente = new ServicioCliente("localhost", 8443);

        configurarTabla();
        cargarProductosServidor(); // Carga inicial

        inicializarEventos();
    }

    private void configurarTabla() {
        String[] columnas = {"ID", "Nombre", "Descripción", "Precio", "Stock"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        if (tblProductos != null) {
            tblProductos.setModel(modelo);
        }
    }

    /**
     * Registra los eventos de clic en los botones de la interfaz.
     */
    private void inicializarEventos() {
        if (btnBuscar != null) {
            btnBuscar.addActionListener(e -> accionBuscar());
        }
        if (btnAgregar != null) {
            btnAgregar.addActionListener(e -> accionAgregar());
        }
        if (btnEliminar != null) {
            btnEliminar.addActionListener(e -> accionEliminar());
        }
    }

    /**
     * Estas son las acciones de los botones
     */

    private void accionBuscar() {
        String criterio = txtBusqueda != null ? txtBusqueda.getText().trim() : "";
        if (criterio.isEmpty()) {
            cargarProductosServidor(); // Si no hay nada escrito, trae todo el inventario
            return;
        }
        try {
            Respuesta respuesta = servicioCliente.buscarProducto(criterio);
            if (respuesta != null && respuesta.isExito()) {
                llenarTabla(respuesta.getProductos());
            } else if (respuesta != null) {
                JOptionPane.showMessageDialog(this, respuesta.getMensaje(), "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error SSL al buscar: " + e.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionAgregar() {
        try {
            String nombre = txtNombre.getText().trim();
            String desc = txtDescrpcion.getText().trim();
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            int cantidad = Integer.parseInt(txtStock.getText().trim());

            // Producto nuevo (el ID suele ser 0 si lo asigna la BD)
            Producto nuevo = new Producto(nombre, desc, precio, cantidad, 0);

            Respuesta respuesta = servicioCliente.agregarProducto(nuevo);
            if (respuesta != null && respuesta.isExito()) {
                JOptionPane.showMessageDialog(this, "Producto agregado con éxito");
                limpiarFormulario();
                cargarProductosServidor(); // Recargamos la tabla con el nuevo producto
            } else if (respuesta != null) {
                JOptionPane.showMessageDialog(this, respuesta.getMensaje(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Asegúrate de ingresar valores numéricos válidos en Precio y Stock.", "Datos Inválidos", JOptionPane.WARNING_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error SSL al agregar: " + e.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionEliminar() {
        String criterio = txtBusqueda != null ? txtBusqueda.getText().trim() : "";
        if (criterio.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el ID o Nombre en el campo de búsqueda para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas eliminar el producto: " + criterio + "?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Respuesta respuesta = servicioCliente.eliminarProducto(criterio);
                if (respuesta != null && respuesta.isExito()) {
                    JOptionPane.showMessageDialog(this, "Producto eliminado correctamente");
                    limpiarFormulario();
                    cargarProductosServidor();
                } else if (respuesta != null) {
                    JOptionPane.showMessageDialog(this, respuesta.getMensaje(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error SSL al eliminar: " + e.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Usa ServicioCliente para pedir el inventario al servidor vía SSL.
     */
    public void cargarProductosServidor() {
        try {
            Respuesta respuesta = servicioCliente.listarInventario();
            if (respuesta != null && respuesta.isExito()) {
                llenarTabla(respuesta.getProductos());
            } else if (respuesta != null) {
                JOptionPane.showMessageDialog(this, respuesta.getMensaje(), "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error de red/SSL: " + e.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Vuelca la lista de productos dentro del JTable.
     */
    private void llenarTabla(List<Producto> productos) {
        if (tblProductos == null) return;

        DefaultTableModel modelo = (DefaultTableModel) tblProductos.getModel();
        modelo.setRowCount(0); // Limpia filas viejas

        if (productos != null) {
            for (Producto p : productos) {
                Object[] fila = {p.getId(), p.getNombre(), p.getDescripcion(), p.getPrecio(), p.getCantidadDisponible()};
                modelo.addRow(fila);
            }
        }
    }

    // --- MÉTODOS OBLIGATORIOS IMPLEMENTADOS ---

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