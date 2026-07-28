package org.example.modelo;
import lombok.*;
import org.example.servidor.ServidorInventario;

import java.io.Serializable;

/**
 * Representa un producto dentro del inventario
 * Tiene los datos del producto: nombre, descripcion, precio, la cantidad disponible y su codigo (id)
 * No tiene lógica de negocio;
 * Las validaciones y operaciones sobre estos productos se encuentran en la clase Inventario.
 * @author Isabella Duque E
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Producto implements Serializable {

    /**
     * Nombre del producto
     */
    private String nombre;

    /**
     * Descripcion detallada del producto
     */
    private String descripcion;

    /**
     * Precio por unidad del producto
     */
    private Double precio;

    /**
     * Cantidad disponible del producto en el inventario
     */
    private int cantidadDisponible;

    /**
     * Identificador único del producto, asignado automaticamente
     * por la clase Inventario al momento de agregarlo
     */
    private int id;


}

