package org.example.servidor;
import org.example.protocolo.Peticion;
import org.example.protocolo.Respuesta;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ControladorClientes implements Runnable{

    private Socket socket;
    private DespachadorPeticion despachador;

    public ControladorClientes(Socket socket, DespachadorPeticion despachador) {
        this.socket = socket;
        this.despachador = despachador;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream objectEntrada = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream objectSalida = new ObjectOutputStream(socket.getOutputStream()) ;
            Peticion peticion = (Peticion) objectEntrada.readObject();
            Respuesta respuesta = despachador.procesar(peticion);
            objectSalida.writeObject(respuesta);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ocurrio un error al intentar conectar" + e.getMessage());
        }


    }
}

