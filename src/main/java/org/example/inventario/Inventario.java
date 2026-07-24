package org.example.inventario;

import java.util.ArrayList;
import java.util.List;

import org.example.excepciones.DatoInvalidoException;
import org.example.excepciones.ProductoNoEncontradoException;
import org.example.modelo.Producto;

/**
 * Esta clase gestiona el inevnatario de los productos del sistema
 * Permite agregar, eliminar, editar, buscar y listtar productos,
 * realizzando asi las validaciones necesarias para garantizar la integridad de la informacion
 *
 * @author Isabella Duque E
 */

public class Inventario {
    /**
     * Lista que almacena los productos registrados
     */
    private List<Producto> productos;

    /**
     * El identificador o codigo unico que se le va a asignar al proximo producto agregado
     */
    private int idProximo;

    /**
     * Crea un inventario vacio e inicializa el contador de identificaadores
     */
    public Inventario() {
        this.productos = new ArrayList<Producto>();
        this.idProximo = 1;
    }

    /**
     * Agrega un nuevo producto al inventario
     * @param producto que se desea agregar.
     * @throws DatoInvalidoException si el nombre esta vacio o precio y cantidad son negativos  ya existe un producto con el mismo nombre lanza esta excepcion.
     */

    public void agregarProducto(Producto producto) {
        if (producto.getNombre() == null || producto.getNombre().isEmpty()) {
            throw new DatoInvalidoException("Este campo no puede estar vacío");
        }
        if (producto.getPrecio() < 0) {
            throw new DatoInvalidoException("El precio no puede ser negativo");
        }
        if (producto.getCantidadDisponible() < 0) {
            throw new DatoInvalidoException("La cantidad no puede ser negativa");
        }
        if (productos.stream().anyMatch(p -> p.getNombre().equals(producto.getNombre()))) {
            throw new DatoInvalidoException("Ya existe un producto con ese nombre");
        }

        producto.setId(idProximo);
        idProximo++;

        productos.add(producto);
    }

    /**
     * Modifica la información de un producto existente.
     *
     * @param id Identificador del producto.
     * @param nombre Nuevo nombre del producto.
     * @param descripcion Nueva descripción del producto.
     * @param precio Nuevo precio del producto.
     * @param cantidadDisponible Nueva cantidad disponible.
     * @throws ProductoNoEncontradoException Si el producto no existe.
     * @throws DatoInvalidoException Si alguno de los datos ingresados no es válido.
     */
    public void eliminarProducto(int id) {
        if (!productos.stream().anyMatch(p -> p.getId() == id)) {
            throw new ProductoNoEncontradoException("No existe el producto con el id: " + id + "Intentelo de nuevo");

        }   }
    public void editarProducto(int id, String nombre, String descripcion, double precio, int cantidadDisponible) {
        List<Producto> resultado = productos.stream()
                .filter(producto -> producto.getId() == id)
                .toList();
        if (resultado.isEmpty()) {
            throw new ProductoNoEncontradoException("No existe un producto con ese id");
        }

        if (nombre == null || nombre.isEmpty()) {
            throw new DatoInvalidoException("Este campo no puede estar vacío");
        }
        if (precio < 0) {
            throw new DatoInvalidoException("El precio no puede ser negativo");
        }
        if (cantidadDisponible < 0) {
            throw new DatoInvalidoException("La cantidad no puede ser negativa");
        }
        if (productos.stream().anyMatch(p -> p.getNombre().equals(nombre) && p.getId() != id)) {
            throw new DatoInvalidoException("Ya existe un producto con ese nombre");
        }

        Producto encontrado = resultado.get(0);
        encontrado.setNombre(nombre);
        encontrado.setDescripcion(descripcion);
        encontrado.setPrecio(precio);
        encontrado.setCantidadDisponible(cantidadDisponible);
    }

    /**
     * Busca productos cuyo nombre contenga el texto indicado
     *
     * @param nombre Nombre o parte del nombre buscar
     * @return La lista de productos que coinciden que la busqueda
     */
    public List<Producto> buscarProductoPorNombre(String nombre) {
        return productos.stream()
                .filter(p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .toList();

    }

    /**
     * Obtiene todos los productos registrados del inventario
     *
     * @return Una copia de la lista de productos.
     */
    public List<Producto> listarTodos (){
        return new ArrayList<>(productos);
    }



    }


