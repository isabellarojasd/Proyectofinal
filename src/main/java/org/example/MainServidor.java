package org.example;

import org.example.configuracon.IConfigReader;
import org.example.configuracon.ISSLConfig;
import org.example.configuracon.PropertiesManager;
import org.example.configuracon.SSLConfig;
import org.example.inventario.Inventario;
import org.example.servidor.DespachadorPeticion;
import org.example.servidor.ServidorInventario;

    public class MainServidor {
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
