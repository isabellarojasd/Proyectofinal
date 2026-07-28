package org.example.servidor;

import org.example.excepciones.DatoInvalidoException;
import org.example.excepciones.ProductoNoEncontradoException;
import org.example.inventario.Inventario;
import org.example.modelo.Producto;
import org.example.protocolo.Peticion;
import org.example.protocolo.Respuesta;
import java.util.List;

/**
 * Traduce las peticiones recibidas del cliente en operaciones concretas
 * sobre el inventario, y arma la respuesta correspondiente.
 * Centraliza el manejo de excepciones para que ninguna operación fallida
 * detenga la conexión con el cliente.
 *
 * @author Isabella Duque Estrada
 */
public class DespachadorPeticion {
    /**
     * Inventario sobre el cual se ejecutan las operaciones solicitadas.
     */

    private Inventario inventario;

    /**
     * Crea un despachador asociado a un inventario específico.
     *
     * @param inventario el inventario que se va a gestionar
     */
    public DespachadorPeticion(Inventario inventario){
        this.inventario = inventario;
    }

    /**
     * Procesa una petición del cliente, ejecuta la operación indicada por
     * su tipo, y devuelve la respuesta correspondiente. Si la operación
     * lanza una excepción de negocio, se captura y se traduce en una
     * respuesta de error, sin propagar la excepción hacia el llamador.
     *
     * @param peticion la petición enviada por el cliente
     * @return la respuesta con el resultado de la operación
     */
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
