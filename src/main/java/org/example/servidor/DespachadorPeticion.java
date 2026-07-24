package org.example.servidor;

import org.example.excepciones.DatoInvalidoException;
import org.example.excepciones.ProductoNoEncontradoException;
import org.example.inventario.Inventario;
import org.example.modelo.Producto;
import org.example.protocolo.Peticion;
import org.example.protocolo.Respuesta;
import java.util.List;


public class DespachadorPeticion {
    private Inventario inventario;

    public DespachadorPeticion(Inventario inventario){
        this.inventario = inventario;
    }
    public Respuesta procesar (Peticion peticion) {
        switch (peticion.getTipo()) {
            case AGREGAR:
                try {
                    inventario.agregarProducto(peticion.getProducto());
                    return new Respuesta(true, "Agregado con exito", null);
                } catch (DatoInvalidoException e) {
                    return new Respuesta(false, e.getMessage(), null);
                }
            case ELIMINAR:
                try {
                    int id = Integer.parseInt(peticion.getCriterioBusqueda());
                    inventario.eliminarProducto(id);
                    return new Respuesta(true, "Producto eliminado exitosamente", null);
                } catch (ProductoNoEncontradoException e) {
                    return new Respuesta(false, e.getMessage(), null);
                } catch (NumberFormatException e) {
                    return new Respuesta(false, "El id proporcionado no es válido", null);
                }
            case ACTUALIZAR:
                try {
                    int id = Integer.parseInt(peticion.getCriterioBusqueda());
                    Producto p = peticion.getProducto();
                    inventario.editarProducto(id, p.getNombre(), p.getDescripcion(), p.getPrecio(), p.getCantidadDisponible());
                    return new Respuesta(true, "Producto actualizado con exito", null);
                } catch (DatoInvalidoException | ProductoNoEncontradoException e) {
                    return new Respuesta(false, e.getMessage(), null);
                } catch (NumberFormatException e){
                    return new Respuesta(false, "El id no es valido", null);
                }
            case BUSCAR:
                List<Producto> encontrados = inventario.buscarProductoPorNombre(peticion.getCriterioBusqueda());
                return new Respuesta(true, "Busqueda realizada", encontrados);

            case LISTAR:
                List<Producto> todos = inventario.listarTodos();
                return new Respuesta(true, "Listado completo", todos);


        }
        return null;

    }

}
