package org.example.presentacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Ventana para la visualización de los registros de auditoría.
 * Aplica HERENCIA desde VentaBase e INTERFAZ mediante INavegación.
 */
public class Registros extends VentaBase implements INavegación {

    private JTable tblRegistros;
    private JPanel panelRegistro;
    private JButton btnExportarRe;

    /**
     * Constrctor que inicia la venta de registro
     */
    public Registros() {
        super("Registros de Auditoría", 800, 500);
        if (panelRegistro != null) {
            setContentPane(panelRegistro);
        }
        configurarTablaLogs();
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


    @Override
    public JPanel getPanelPrincipal() {
        return this.panelRegistro;
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
    }
}