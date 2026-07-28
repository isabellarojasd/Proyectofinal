package org.example;

import org.example.configuracon.IConfigReader;
import org.example.configuracon.ISSLConfig;
import org.example.configuracon.PropertiesManager;
import org.example.configuracon.SSLConfig;
import org.example.inventario.Inventario;
import org.example.servidor.DespachadorPeticion;
import org.example.servidor.ServidorInventario;


/**
 * Punto de entrada del lado servidor del sistema de gestión de inventario.
 * Carga la configuración SSL desde el archivo de propiedades, configura
 * el keystore para las conexiones seguras, arma el inventario y el
 * despachador de peticiones, y arranca el servidor para que quede
 * escuchando conexiones de clientes de forma indefinida.
 *
 * @author Isabella Duque Estrada
 */

    public class MainServidor {

    /**
     * Configura el entorno SSL y arranca el servidor de inventario.
     *
     * @param args argumentos de línea de comandos (no utilizados)
     */

    public static void main(String[] args) {

        IConfigReader configReader = new PropertiesManager("config.properties");
        ISSLConfig sslConfig = new SSLConfig(configReader);

        System.setProperty("javax.net.ssl.keyStore", sslConfig.getKeyStorePath());
        System.setProperty("javax.net.ssl.keyStorePassword", sslConfig.getKeyStorePassword());
        System.setProperty("javax.net.ssl.trustStore", sslConfig.getKeyStorePath());
        System.setProperty("javax.net.ssl.trustStorePassword", sslConfig.getKeyStorePassword());

        Inventario inventario = new Inventario();
        DespachadorPeticion despachador = new DespachadorPeticion(inventario);
        ServidorInventario servidor = new ServidorInventario(sslConfig.getPuertoSSL(), despachador);
        servidor.iniciarServidor();
    }
}
