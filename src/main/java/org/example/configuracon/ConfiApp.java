package org.example.configuracon;

public class ConfiApp implements IConfiApp {

    /**
     * Variables de la clase configuración
     */
    private final String puertoSSL;
    private final String rutaKeystore;
    private final String passwordKeystore;

    /**
     * Constructor que inicializa los valores predeterminados
     * del sistema para la seguridad y conectividad
     */
    public ConfiApp() {
        this.puertoSSL = "8443";
        this.rutaKeystore = "src/main/resources/keystore.jks";
        this.passwordKeystore = "123456";
    }

    @Override
    public String getPuertoSSL() {
        return this.puertoSSL;
    }

    @Override
    public String getRutaKeystore() {
        return this.rutaKeystore;
    }

    @Override
    public String getPasswordKeystore() {
        return this.passwordKeystore;
    }

    @Override
    public void cargarConfiSSL() {
        cargarConfiSSLEstatico();
    }

    /**
     * Método estático para cargar las propiedades globales de SSL
     * directamente sin necesidad de instanciar la clase.
     */
    public static void cargarConfiSSLEstatico() {
        try {
            System.setProperty("javax.net.ssl.keyStore", "src/main/resources/keystore.jks");
            System.setProperty("javax.net.ssl.keyStorePassword", "123456");
            System.setProperty("javax.net.ssl.trustStore", "src/main/resources/keystore.jks");
            System.setProperty("javax.net.ssl.trustStorePassword", "123456");

            System.out.println("[CONFIG] Propiedades SSL cargadas exitosamente (Estatico).");
        } catch (Exception e) {
            System.err.println("[ERROR CONFIG] Ocurrió un error al cargar la configuración SSL: " + e.getMessage());
        }
    }
}