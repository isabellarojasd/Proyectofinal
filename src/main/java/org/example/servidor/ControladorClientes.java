package org.example.servidor;
import org.example.protocolo.Peticion;
import org.example.protocolo.Respuesta;

import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Atiende la comunicación con un cliente conectado, ejecutándose en su
 * propio hilo para permitir que el servidor maneje múltiples clientes
 * de forma concurrente.
 * Lee la petición del cliente, la delega al despachador para su
 * procesamiento, y devuelve la respuesta correspondiente.
 *
 * @author Isabella Duque Estrada
 */

public class ControladorClientes implements Runnable{

    /**
     * Conexión SSL establecida con el cliente atendido por esta instancia.
     */
    private SSLSocket socket;

    /**
     * Despachador utilizado para procesar la petición recibida del cliente.
     */
    private DespachadorPeticion despachador;

    /**
     * Crea un controlador para atender a un cliente específico.
     *
     * @param socket la conexión SSL con el cliente
     * @param despachador el despachador que procesará la petición
     */
    public ControladorClientes(SSLSocket socket, DespachadorPeticion despachador) {
        this.socket = socket;
        this.despachador = despachador;
    }

    /**
     * Lee la petición enviada por el cliente, la procesa a través del
     * despachador, y envía la respuesta correspondiente. Cierra la
     * conexión al finalizar, ocurra o no un error.
     */

    @Override
    public void run() {
        try {
            ObjectOutputStream objectSalida = new ObjectOutputStream(socket.getOutputStream());
            objectSalida.flush();
            ObjectInputStream objectEntrada = new ObjectInputStream(socket.getInputStream());
            Peticion peticion = (Peticion) objectEntrada.readObject();
            Respuesta respuesta = despachador.procesar(peticion);
            objectSalida.writeObject(respuesta);
            objectSalida.flush();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ocurrio un error al intentar conectar" + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Error al cerrar la conexión" + e.getMessage());
            }
        }


    }
}

