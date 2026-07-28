package org.example.presentacion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Esta clase representa la ventana principal del sistema
 * Permite al usuario seleccionar la opcion que desea abrir y acceder a las diferentes pantallas de la aplicacion
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
        super("Menú Principal", 500, 350);


        if (panelInicio != null) {
            setContentPane(panelInicio);
        }


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
     * Lee la opción elegida en el JComboBox y abre la pantalla correspondiente
     */
    private void ejecutarOpcionSeleccionada() {
        if (cbOpciones != null && cbOpciones.getSelectedItem() != null) {
            String opcion = cbOpciones.getSelectedItem().toString();


            if (opcion.equalsIgnoreCase("Inventario") || opcion.contains("Inventario")) {
                ManejoInventario inventario = new ManejoInventario();
                inventario.abrir();
                this.cerrar();
            } else if (opcion.equalsIgnoreCase("Registros") || opcion.contains("Registros")) {
                Registros registros = new Registros();
                registros.abrir();
                this.cerrar();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor seleccione una opción válida.");
            }
        }
    }


    /**
     * Este metodo devuelve el panel principal de la ventana
     */
    @Override
    public JPanel getPanelPrincipal() {
        return this.panelInicio;
    }

    /**
     * Este metodo muestra la ventana al usuario
     */
    @Override
    public void abrir() {
        this.setVisible(true);
    }

    /**
     * Este metodo cierra la ventana actual
     */
    @Override
    public void cerrar() {
        this.dispose();
    }

    /**
     * Este metodo reinicia la seleccion del ComboBox dejando la primera opcion seleccionada
     */
    @Override
    public void limpiarFormulario() {
        if (cbOpciones != null && cbOpciones.getItemCount() > 0) {
            cbOpciones.setSelectedIndex(0); // Reinicia la selección al primer elemento
        }
    }
}