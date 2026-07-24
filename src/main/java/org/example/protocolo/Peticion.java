package org.example.protocolo;
import java.io.Serializable;
import org.example.modelo.Producto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
    @AllArgsConstructor
    @NoArgsConstructor

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
}