package org.example.configuracon;

/**
 * INTERFAZ: Define el contrato de métodos que debe tener
 * cualquier clase de configuración dentro del sistema
 */
public interface IConfiApp {

    /**
     * Obtiene el puerto seguro configurado para las conexiones SSL
     * @return El número de puerto como String.
     */
    public abstract String getPuertoSSL();

    /**
     * Obtiene la ruta relativa o absoluta del archivo Keystore (.jks)
     * @return La ruta del archivo Keystore como String
     */
    public abstract String getRutaKeystore();

    /**
     * Obtiene la contraseña de seguridad del archivo Keystore
     * @return La contraseña como String
     */
    public abstract String getPasswordKeystore();

    /**
     * Configura e inicializa las propiedades del sistema Java
     * para habilitar la seguridad por certificado SSL/TLS
     */
    public abstract void cargarConfiSSL();

}
