package org.example.configuracon;

public class ConfiApp implements IConfiApp{

    /**
     * Variables de la clase configuraion
     */
    private final String puertoSSL;
    private final String rutaKeystore;
    private final String passwordKeystore;

    /**
     * Constructor Inicializa los valores predeterminados
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

    /**
     * Establece las propiedades globales del sistema mediante System.setProperty
     * para que Java entienda dónde está ubicado el Keystore SSL.
     */
    @Override
    public void cargarConfiSSL() {
        try {
            System.setProperty("javax.net.ssl.keyStore", getRutaKeystore());
            System.setProperty("javax.net.ssl.keyStorePassword", getPasswordKeystore());

            System.out.println("[CONFIG] Propiedades SSL cargadas exitosamente.");
            System.out.println("[CONFIG] Ruta Keystore: " + getRutaKeystore());
            System.out.println("[CONFIG] Puerto asignado: " + getPuertoSSL());
        } catch (Exception e) {
            System.err.println("[ERROR CONFIG] Ocurrió un error al cargar la configuración SSL: " + e.getMessage());
        }
    }
}
