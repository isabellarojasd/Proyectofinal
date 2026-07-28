package org.example.presentacion;

import org.example.auditoria.ExportadorAuditoriaCsv;
import org.example.configuracon.IConfigReader;
import org.example.configuracon.PropertiesManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Ventana para la visualización de los registros de auditoría.
 */
public class Registros extends VentaBase implements INavegación {

    private JTable tblRegistros;
    private JPanel panelRegistro;
    private JButton btnExportarRe;
    private JButton btnRegresar;

    private final String rutaAuditoria;

    /**
     * Constrctor que inicia la venta de registro
     */
    public Registros() {
        super("Registros de Auditoría", 800, 500);

        IConfigReader configReader = new PropertiesManager("config.properties");
        this.rutaAuditoria = configReader.getString("auditoria.log.path");

        if (panelRegistro != null) {
            setContentPane(panelRegistro);
        }
        configurarTablaLogs();
        cargarRegistros();
        inicializarEventos();
    }

    /**
     * Configura el modelo y las columnas de la tabla de logs.
     */
    private void configurarTablaLogs() {
        String[] columnas = {"Fecha/Hora", "Operación", "IP Cliente", "Recurso"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        if (tblRegistros != null) {
            tblRegistros.setModel(modelo);
        }
    }

    /**
     * Lee el archivo de auditoría en texto plano y llena la tabla.
     */
    private void cargarRegistros() {
        if (tblRegistros == null) return;

        DefaultTableModel modelo = (DefaultTableModel) tblRegistros.getModel();
        modelo.setRowCount(0);

        Path ruta = Paths.get(rutaAuditoria);
        if (!Files.exists(ruta)) {
            return;
        }

        try {
            List<String> lineas = Files.readAllLines(ruta);
            for (String linea : lineas) {
                String[] partes = linea.split("\\|");
                if (partes.length == 4) {
                    String fecha = partes[0].trim();
                    String operacion = partes[1].trim();
                    String ip = partes[2].replace("IP:", "").trim();
                    String recurso = partes[3].replace("Recurso:", "").trim();
                    modelo.addRow(new Object[]{fecha, operacion, ip, recurso});
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al leer el archivo de auditoría: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Registra los eventos de clic en los botones de la interfaz.
     */
    private void inicializarEventos() {
        if (btnRegresar != null) {
            btnRegresar.addActionListener(e -> accionRegresar());
        }
        if (btnExportarRe != null) {
            btnExportarRe.addActionListener(e -> accionExportarCsv());
        }
    }

    /**
     * Exporta el archivo de auditoría en texto plano a un CSV elegido por el usuario.
     */
    private void accionExportarCsv() {
        Path ruta = Paths.get(rutaAuditoria);
        if (!Files.exists(ruta)) {
            JOptionPane.showMessageDialog(this, "Todavía no hay registros de auditoría para exportar.",
                    "Sin datos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Registros de Auditoría en CSV");
        fileChooser.setSelectedFile(new File("auditoria_exportada.csv"));

        int seleccion = fileChooser.showSaveDialog(this);
        if (seleccion != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File archivoDestino = fileChooser.getSelectedFile();
        String rutaDestino = archivoDestino.getAbsolutePath();
        if (!rutaDestino.toLowerCase().endsWith(".csv")) {
            rutaDestino += ".csv";
        }

        try {
            ExportadorAuditoriaCsv exportador = new ExportadorAuditoriaCsv();
            exportador.exportar(rutaAuditoria, rutaDestino);
            JOptionPane.showMessageDialog(this, "¡Registros exportados exitosamente!\nUbicación: " + rutaDestino,
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al exportar los registros: " + e.getMessage(),
                    "Error de Exportación", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Cierra esta ventana y regresa a la pantalla de inicio.
     */
    private void accionRegresar() {
        this.cerrar();
        Iniciosesion inicio = new Iniciosesion();
        inicio.abrir();
    }

    /**
     * Este metodo devuelve el panel principal de la ventana
     */
    @Override
    public JPanel getPanelPrincipal() {
        return this.panelRegistro;
    }

    /**
     * Este metodo muestra la ventana de registros
     */
    @Override
    public void abrir() {
        this.setVisible(true);
    }

    /**
     * Este metodo cierra la ventana de registros
     */
    @Override
    public void cerrar() {
        this.dispose();
    }

    /**
     * Este metodo limpia el formulario aunque en esta ventana no es necesario realizar ninguna accion
     */
    @Override
    public void limpiarFormulario() {
    }
}