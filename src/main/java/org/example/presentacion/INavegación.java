package org.example.presentacion;

/**
 * Interfaz Define el contrato de navegación
 * que deben cumplir todas las ventanas del sistema.
 */
public interface INavegación {
    /**
     * Muestra la ventana en la pantalla.
     */
    public abstract void abrir();

    /**
     * Cierra la ventana actual y libera sus recursos.
     */
    public abstract void cerrar();

    /**
     * Limpia o reinicia los campos de texto y selecciones del formulario.
     */
    public abstract void limpiarFormulario();
}
