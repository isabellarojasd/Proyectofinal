package org.example.inventario;

import java.util.ArrayList;
import java.util.List;

import org.example.excepciones.DatoInvalidoException;
import org.example.excepciones.ProductoNoEncontradoException;
import org.example.modelo.Producto;

public class Inventario {
    private List<Producto> productos;
    private int idProximo;

    public Inventario() {
        this.productos = new ArrayList<Producto>();
        this.idProximo = 1;
    }

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
    public List<Producto> buscarProductoPorNombre(String nombre) {
        return productos.stream()
                .filter(p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .toList();

    }
    public List<Producto> listarTodos (){
        return new ArrayList<>(productos);
    }



    }


