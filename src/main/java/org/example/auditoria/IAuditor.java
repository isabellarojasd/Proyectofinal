package org.example.auditoria;

/**
 * Define el contrato para registrar auditorías de las operaciones
 * realizadas sobre el inventario
 */
public interface IAuditor {

    /**
     * Registra una operación realizada sobre el inventario.
     * @param operacion tipo de operación realizada (ej. "AGREGAR", "ELIMINAR", "ACTUALIZAR").
     * @param ip dirección IP del cliente que ejecutó la operación.
     * @param recurso recurso afectado por la operación (ej. nombre o id del producto).
     */
    void registrar(String operacion, String ip, String recurso);
}