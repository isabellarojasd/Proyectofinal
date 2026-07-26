package org.example.servidor;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorInventario implements IServidorInventario{
    private int puerto;
    private DespachadorPeticion despachador;

    public ServidorInventario(int puerto, DespachadorPeticion despachador) {
        this.puerto = puerto;
        this.despachador = despachador;
    }

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
