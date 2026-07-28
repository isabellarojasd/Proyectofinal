package org.example.servidor;
/**
 * Esta interfaz define el metodo que debe implementar cualquier servidor del inventario
 */
public interface IServidorInventario {
    /**
     * Este metodo inicia el servidor y lo deja listo para atender las solicitudes de los clientes
     */
    public void iniciarServidor();
}
