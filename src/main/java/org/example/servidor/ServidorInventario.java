package org.example.servidor;
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
        try(ServerSocket servidor = new ServerSocket(puerto)){
            System.out.println("El servido essta escuchando el puerto: " + puerto);
            while (true){
                Socket cliente = servidor.accept();
                ControladorClientes controlador = new ControladorClientes(cliente, despachador);
                Thread thread = new Thread(controlador);
                thread.start();
            }
        }catch (IOException e){
            System.out.println("[Server] Error critico: "+e.getMessage());
        }

    }

}
