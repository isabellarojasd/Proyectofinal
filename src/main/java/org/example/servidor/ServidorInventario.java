package org.example.servidor;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.*;

/**
 * Servidor de inventario que acepta conexiones SSL entrantes y lanza
 * un hilo independiente por cada cliente conectado, permitiendo así
 * atender múltiples clientes de forma concurrente.
 *
 * @author Isabella Duque Estrada
 */
public class ServidorInventario implements IServidorInventario{
    /**
     * Puerto en el que el servidor escucha conexiones SSL entrantes.
     */
    private int puerto;

    /**
     * Despachador que se entrega a cada cliente atendido, para procesar
     * sus peticiones sobre el inventario.
     */
    private DespachadorPeticion despachador;

    /**
     * Crea un servidor configurado para escuchar en el puerto indicado.
     *
     * @param puerto el puerto donde el servidor escuchará conexiones
     * @param despachador el despachador que procesará las peticiones de los clientes
     */
    public ServidorInventario(int puerto, DespachadorPeticion despachador) {
        this.puerto = puerto;
        this.despachador = despachador;
    }

    /**
     * Inicia el servidor: crea el socket SSL en el puerto configurado y
     * queda a la espera de conexiones de forma indefinida. Por cada
     * cliente aceptado, crea un {@link ControladorClientes} y lo ejecuta
     * en un hilo independiente.
     */
    @Override
    public void iniciarServidor() {
        SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        try(SSLServerSocket servidor = (SSLServerSocket) factory.createServerSocket(puerto)){
            System.out.println("El servido essta escuchando el puerto: " + puerto);
            while (true){
                SSLSocket cliente = (SSLSocket) servidor.accept();
                ControladorClientes controlador = new ControladorClientes(cliente, despachador);
                Thread thread = new Thread(controlador);
                thread.start();
            }
        }catch (IOException e){
            System.out.println("[Server] Error critico: "+e.getMessage());
        }

    }

}
