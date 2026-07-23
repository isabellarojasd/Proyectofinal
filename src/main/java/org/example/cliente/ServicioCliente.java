package org.example.cliente;

import org.example.modelo.Producto;
import org.example.protocolo.Peticion;
import org.example.protocolo.Respuesta;
import org.example.protocolo.TipoOperacion;

import java.io.IOException;
import java.util.List;

/**
 * Capa de negocio del lado cliente. Traduce las acciones del usuario
 * (agregar, eliminar, actualizar, buscar, listar productos) en peticiones
 * hacia el servidor, usando ClienteConectaInventario para el transporte.
 * Esta es la clase que debe usar la interfaz gráfica (GUI).
 */
public class ServicioCliente {

    private final ClienteConectaInventario conexion;

    /**
     * Crea el servicio de cliente indicando a qué servidor conectarse.
     * @param host dirección del servidor.
     * @param puerto puerto del servidor.
     */
    public ServicioCliente(String host, int puerto) {
        this.conexion = new ClienteConectaInventario(host, puerto);
    }

    /**
     * Solicita al servidor agregar un nuevo producto al inventario.
     * @param producto el producto a agregar.
     * @return la respuesta del servidor indicando éxito o error.
     * @throws IOException si ocurre un error de comunicación con el servidor.
     */
    public Respuesta agregarProducto(Producto producto) throws IOException {
        Peticion peticion = new Peticion(TipoOperacion.AGREGAR, producto, null);
        return conexion.enviarPeticion(peticion);
    }

    /**
     * Solicita al servidor eliminar un producto del inventario.
     * @param criterioBusqueda nombre o id del producto a eliminar.
     * @return la respuesta del servidor indicando éxito o error.
     * @throws IOException si ocurre un error de comunicación con el servidor.
     */
    public Respuesta eliminarProducto(String criterioBusqueda) throws IOException {
        Peticion peticion = new Peticion(TipoOperacion.ELIMINAR, null, criterioBusqueda);
        return conexion.enviarPeticion(peticion);
    }

    /**
     * Solicita al servidor actualizar los datos de un producto existente.
     * @param producto el producto con los datos actualizados (debe incluir su id).
     * @return la respuesta del servidor indicando éxito o error.
     * @throws IOException si ocurre un error de comunicación con el servidor.
     */
    public Respuesta actualizarProducto(Producto producto) throws IOException {
        Peticion peticion = new Peticion(TipoOperacion.ACTUALIZAR, producto, null);
        return conexion.enviarPeticion(peticion);
    }

    /**
     * Busca uno o varios productos por nombre.
     * @param nombre nombre (o parte del nombre) del producto a buscar.
     * @return la respuesta del servidor con la lista de productos encontrados.
     * @throws IOException si ocurre un error de comunicación con el servidor.
     */
    public Respuesta buscarProducto(String nombre) throws IOException {
        Peticion peticion = new Peticion(TipoOperacion.BUSCAR, null, nombre);
        return conexion.enviarPeticion(peticion);
    }

    /**
     * Solicita al servidor la lista completa del inventario actual.
     * @return la respuesta del servidor con todos los productos.
     * @throws IOException si ocurre un error de comunicación con el servidor.
     */
    public Respuesta listarInventario() throws IOException {
        Peticion peticion = new Peticion(TipoOperacion.LISTAR, null, null);
        return conexion.enviarPeticion(peticion);
    }
}
