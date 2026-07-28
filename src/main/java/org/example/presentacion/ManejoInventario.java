package org.example.presentacion;

import org.example.cliente.ServicioCliente;
import org.example.exportar.ExportarInventarioCSV;
import org.example.exportar.IExportarInventario;
import org.example.modelo.Producto;
import org.example.protocolo.Respuesta;
import org.example.configuracon.IConfigReader;
import org.example.configuracon.PropertiesManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Ventana para la gestión del inventario con conexión SSL.
 */
public class ManejoInventario extends VentaBase implements INavegación {

    private ServicioCliente servicioCliente;

    // Guarda el ID del producto actualmente seleccionado en la tabla (para Editar)
    private Integer idSeleccionado = null;

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
    private JLabel abNombre;
    private JLabel abDescripcion;
    private JLabel abPrecio;
    private JLabel abStock;
    private JButton btnRegresar;
    private JTextField txtId;
    private JLabel abId;

    public ManejoInventario() {
        super("Gestión de Inventario", 800, 600);

        if (panelInventario != null) {
            setContentPane(panelInventario);
        }

        IConfigReader configReader = new PropertiesManager("config.properties");
        this.servicioCliente = new ServicioCliente( configReader.getString("servidor.host"), configReader.getInt("tcp.port"));

        configurarTabla();
        cargarProductosServidor();

        inicializarEventos();
    }

    /**
     * Este metodo configura la tabla donde se van a mostrar todos los productos del inventario
     * Aqui se crean las columnas y despues se asigna el modelo a la tabla para poder mostrar la informacion
     */
    private void configurarTabla() {
        String[] columnas = {"ID", "Nombre", "Descripción", "Precio", "Stock"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        if (tblProductos != null) {
            tblProductos.setModel(modelo);
        }
    }

    /**
     *  Este metodo inicializa todos los eventos de los botones
     *  Cada boton ejecuta una accion diferente como buscar agregar editar eliminar exportar o regresar
     */
    private void inicializarEventos() {
        if (btnExportarCSV != null) {
            btnExportarCSV.addActionListener(e -> accionExportarCSV());
        }
        if (btnBuscar != null) {
            btnBuscar.addActionListener(e -> accionBuscar());
        }
        if (btnAgregar != null) {
            btnAgregar.addActionListener(e -> accionAgregar());
        }
        if (btnEditar != null) {
            btnEditar.addActionListener(e -> accionEditar());
        }
        if (btnEliminar != null) {
            btnEliminar.addActionListener(e -> accionEliminar());
        }
        if (btnRegresar != null) {
            btnRegresar.addActionListener(e -> accionRegresar());
        }

        // Al hacer clic en una fila de la tabla, se carga el producto en el formulario
        if (tblProductos != null) {
            tblProductos.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    cargarSeleccionEnFormulario();
                }
            });
        }
    }

    /**
     * Toma la fila seleccionada en la tabla y llena el formulario con sus datos,
     * guardando el ID para poder usarlo luego al editar.
     */
    private void cargarSeleccionEnFormulario() {
        if (tblProductos == null) return;

        int fila = tblProductos.getSelectedRow();
        if (fila < 0) {
            idSeleccionado = null;
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) tblProductos.getModel();
        Object valorId = modelo.getValueAt(fila, 0);
        idSeleccionado = (valorId != null) ? Integer.parseInt(valorId.toString()) : null;

        if (txtNombre != null) txtNombre.setText(String.valueOf(modelo.getValueAt(fila, 1)));
        if (txtDescrpcion != null) txtDescrpcion.setText(String.valueOf(modelo.getValueAt(fila, 2)));
        if (txtPrecio != null) txtPrecio.setText(String.valueOf(modelo.getValueAt(fila, 3)));
        if (txtStock != null) txtStock.setText(String.valueOf(modelo.getValueAt(fila, 4)));
    }

    /**
     * Este metodo exporta el inventario a un archivo CSV
     * Primero obtiene los productos del servidor despues permite elegir donde guardar el archivo y finalmente realiza la exportacion
     */

    private void accionExportarCSV() {
        try {
            // 1. Pedimos los productos actualizados al servidor
            Respuesta respuesta = servicioCliente.listarInventario();

            if (respuesta == null || !respuesta.isExito() || respuesta.getProductos() == null || respuesta.getProductos().isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay productos disponibles en el inventario para exportar.", "Sin datos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 2. Abrimos el selector de archivos (JFileChooser)
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar Inventario en CSV");
            fileChooser.setSelectedFile(new File("inventario_exportado.csv"));

            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File archivoGuardar = fileChooser.getSelectedFile();
                String ruta = archivoGuardar.getAbsolutePath();

                // Aseguramos la extensión .csv
                if (!ruta.toLowerCase().endsWith(".csv")) {
                    ruta += ".csv";
                }

                // 3. Exportamos utilizando tu clase ExportarInventarioCSV
                IExportarInventario exportador = new ExportarInventarioCSV();
                exportador.exportar(respuesta.getProductos(), ruta);

                JOptionPane.showMessageDialog(this,
                        "¡Inventario exportado exitosamente!\nUbicación: " + ruta,
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error de red/SSL al obtener inventario: " + e.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al generar el archivo CSV: " + e.getMessage(), "Error de Exportación", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Este metodo busca un producto usando el texto ingresado por el usuario
     * Si el campo esta vacio carga nuevamente todo el inventario
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

    /**
     * Este metodo agrega un nuevo producto al inventario
     * Toma los datos del formulario crea el producto y lo envia al servidor
     */
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

    /**
     * Edita el producto seleccionado en la tabla con los nuevos valores del formulario
     * Requiere haber hecho clic antes en una fila de la tabla
     */
    private void accionEditar() {
        if (idSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto de la tabla para poder editarlo.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String nombre = txtNombre.getText().trim();
            String desc = txtDescrpcion.getText().trim();
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            int cantidad = Integer.parseInt(txtStock.getText().trim());

            Producto actualizado = new Producto(nombre, desc, precio, cantidad, idSeleccionado);

            Respuesta respuesta = servicioCliente.actualizarProducto(actualizado);
            if (respuesta != null && respuesta.isExito()) {
                JOptionPane.showMessageDialog(this, "Producto editado con éxito");
                limpiarFormulario();
                idSeleccionado = null;
                cargarProductosServidor();
            } else if (respuesta != null) {
                JOptionPane.showMessageDialog(this, respuesta.getMensaje(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Asegúrate de ingresar valores numéricos válidos en Precio y Stock.", "Datos Inválidos", JOptionPane.WARNING_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error SSL al editar: " + e.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Este metodo elimina un producto del inventario
     * Primero solicita una confirmacion y despues envia la solicitud al servidor para eliminar el producto seleccionado
     */
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
                    idSeleccionado = null;
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
     * Este metodo obtiene todos los productos almacenados en el servidor
     * Despues de recibir la informacion actualiza la tabla con los datos mas recientes
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
        modelo.setRowCount(0);

        if (productos != null) {
            for (Producto p : productos) {
                Object[] fila = {p.getId(), p.getNombre(), p.getDescripcion(), p.getPrecio(), p.getCantidadDisponible()};
                modelo.addRow(fila);
            }
        }
    }

    /**
     * Este metodo devuelve el panel principal de la ventana del inventario
     */
    private void accionRegresar() {
        this.cerrar();
        Iniciosesion inicio = new Iniciosesion();
        inicio.abrir();
    }

    /**
     * Métodos obligatorios de la interfaz INavegación
     */

    /**
     * Obtiene el panel principal de la ventana
     *
     * @return panel principal del inventario
     */
    @Override
    public JPanel getPanelPrincipal() {
        return this.panelInventario;
    }

    /**
     * Abre la ventana del inventario
     */
    @Override
    public void abrir() {
        this.setVisible(true);
    }

    /**
     * Cierra la ventana del inventario
     */
    @Override
    public void cerrar() {
        this.dispose();
    }

    /**
     * Limpia los campos del formulario
     */
    @Override
    public void limpiarFormulario() {

        if (txtBusqueda != null) {
            txtBusqueda.setText("");
        }

        if (txtNombre != null) {
            txtNombre.setText("");
        }

        if (txtDescrpcion != null) {
            txtDescrpcion.setText("");
        }

        if (txtPrecio != null) {
            txtPrecio.setText("");
        }

        if (txtStock != null) {
            txtStock.setText("");
        }
    }
}