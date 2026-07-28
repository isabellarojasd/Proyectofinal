package org.example.presentacion;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Clase abstracta es la clase padre de todas las ventanas
 * Aplica,os herencia para que no tengas que repetir la configuración
 * de tamaño, centrado y cierre en cada ventana.
 */
public abstract class VentaBase extends JFrame {

    /**
     * Constructor base para configurar el marco
     *
     * @param titulo Texto que saldrá en la barra superior
     * @param ancho  Ancho de la ventana en píxeles
     * @param alto   Alto de la ventana en píxeles
     */
    public VentaBase(String titulo, int ancho, int alto) {
        super(titulo);
        this.setSize(ancho, alto);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Método abstracto que obliga a cada clase hija a indicar cuál es su JPanel principal
     */
    public abstract JPanel getPanelPrincipal();
}