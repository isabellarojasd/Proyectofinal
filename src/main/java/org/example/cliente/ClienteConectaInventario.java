package org.example.cliente;

import org.example.configuracon.ConfiApp;
import org.example.protocolo.Peticion;
import org.example.protocolo.Respuesta;

import java.io.*;
import java.net.Socket;

/**
 * Encargada de establecer la conexión de red con el servidor de inventario
 * y de enviar/recibir los objetos del protocolo (Peticion / Respuesta).
 * No contiene lógica de negocio: solo transporte.
 */
public class ClienteConectaInventario {

    private final String host;
    private final int puerto;

    /**
     * Crea una nueva instancia de conexión hacia el servidor.
     * @param host dirección del servidor (ej. "localhost")
     * @param puerto puerto en el que escucha el servidor
     */
    public ClienteConectaInventario(String host, int puerto) {
        ConfiApp.cargarConfiSSLEstatico();;
        this.host = host;
        this.puerto = puerto;
    }

    /**
     * Envía una petición al servidor y espera la respuesta correspondiente.
     * Abre una conexión nueva por cada petición (modelo simple y seguro para concurrencia).
     *
     * @param peticion la operación que se desea ejecutar en el servidor.
     * @return la respuesta enviada por el servidor.
     * @throws IOException si ocurre un error de conexión o comunicación.
     */
    public Respuesta enviarPeticion(Peticion peticion) throws IOException {
        try (Socket socket = javax.net.ssl.SSLSocketFactory.getDefault().createSocket(host, puerto)) {
            ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
            salida.flush();
            ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());

            salida.writeObject(peticion);
            salida.flush();

            Object respuestaObj = entrada.readObject();
            if (respuestaObj instanceof Respuesta respuesta) {
                return respuesta;
            } else {
                throw new IOException("Respuesta inesperada del servidor.");
            }
        } catch (ClassNotFoundException e) {
            throw new IOException("Error al interpretar la respuesta del servidor.", e);
        }
    }
}