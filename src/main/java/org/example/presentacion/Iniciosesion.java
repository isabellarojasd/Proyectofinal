package org.example.presentacion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * PANTALLA PRINCIPAL / MENÚ DE SELECCIÓN
 * Aplica HERENCIA desde VentaBase e INTERFAZ mediante INavegación.
 */
public class Iniciosesion extends VentaBase implements INavegación {

    // Tus componentes exactos declarados en el .form
    private JComboBox cbOpciones;
    private JButton btnAbrir;
    private JPanel panelInicio;

    /**
     * Constructor de la pantalla de inicio.
     */
    public Iniciosesion() {
        // Llama al constructor PADRE (VentaBase): super(Título, Ancho, Alto)
        super("Sistema de Gestión - Menú Principal", 500, 350);

        // Se asigna el panel diseñado visualmente
        if (panelInicio != null) {
            setContentPane(panelInicio);
        }

        // --- ACCIÓN DEL BOTÓN ABRIR ---
        if (btnAbrir != null) {
            btnAbrir.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ejecutarOpcionSeleccionada();
                }
            });
        }
    }

    /**
     * Lee la opción elegida en el JComboBox y abre la pantalla correspondiente.
     */
    private void ejecutarOpcionSeleccionada() {
        if (cbOpciones != null && cbOpciones.getSelectedItem() != null) {
            String opcion = cbOpciones.getSelectedItem().toString();

            // Evalúa la opción elegida en el ComboBox
            if (opcion.equalsIgnoreCase("Inventario") || opcion.contains("Inventario")) {
                ManejoInventario inventario = new ManejoInventario();
                inventario.abrir();
                this.cerrar();
            } else if (opcion.equalsIgnoreCase("Registros") || opcion.contains("Registros")) {
                // Aquí va la llamada a tu pantalla de registros/auditoría
                Registros registros = new Registros();
                registros.abrir();
                this.cerrar();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor seleccione una opción válida.");
            }
        }
    }

    // --- MÉTODOS OBLIGATORIOS IMPLEMENTADOS (DE VentaBase Y INavegación) ---

    @Override
    public JPanel getPanelPrincipal() {
        return this.panelInicio;
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
        if (cbOpciones != null && cbOpciones.getItemCount() > 0) {
            cbOpciones.setSelectedIndex(0); // Reinicia la selección al primer elemento
        }
    }
}