package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Registros extends JFrame {

    private JTable tblRegistros;
    private JPanel mainPanel;
    private JButton btnExportarRe;

    public Registros() {
        setContentPane(mainPanel);
        setTitle("Registros de Auditoría");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        configurarTablaLogs();
    }

    private void configurarTablaLogs() {
        String[] columnas = {"Fecha/Hora", "Operación", "IP Cliente", "Recurso"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);

        tblRegistros.setModel(modelo);
    }
}