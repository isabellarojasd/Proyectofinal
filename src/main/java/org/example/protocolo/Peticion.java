package org.example.protocolo;

import org.example.modelo.Producto;
import java.io.Serializable;

/**
 * Representa una petición enviada desde el cliente hacia el servidor,
 * indicando la operación a realizar y los datos necesarios para ejecutarla.
 */
public class Peticion implements Serializable {

    /**
     * Tipo de operación que se desea ejecutar (agregar, eliminar, actualizar, buscar, listar).
     */
    private TipoOperacion tipo;

    /**
     * Producto involucrado en la operación (usado en AGREGAR y ACTUALIZAR).
     * Puede ser null si la operación no lo requiere.
     */
    private Producto producto;

    /**
     * Criterio usado para BUSCAR o ELIMINAR (por ejemplo, nombre o id como texto).
     * Puede ser null si la operación no lo requiere.
     */
    private String criterioBusqueda;

    /**
     * Crea una nueva petición hacia el servidor.
     * @param tipo tipo de operación a realizar.
     * @param producto producto involucrado (o null si no aplica).
     * @param criterioBusqueda criterio de búsqueda/eliminación (o null si no aplica).
     */
    public Peticion(TipoOperacion tipo, Producto producto, String criterioBusqueda) {
        this.tipo = tipo;
        this.producto = producto;
        this.criterioBusqueda = criterioBusqueda;
    }

    /**
     * Obtiene el tipo de operación.
     * @return el tipo de operación.
     */
    public TipoOperacion getTipo() {
        return tipo;
    }

    /**
     * Obtiene el producto involucrado en la operación.
     * @return el producto, o null si no aplica.
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * Obtiene el criterio de búsqueda o eliminación.
     * @return el criterio, o null si no aplica.
     */
    public String getCriterioBusqueda() {
        return criterioBusqueda;
    }
}