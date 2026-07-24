package org.example.configuracon;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesManager implements IConfigReader {

    private Properties props;

    public PropertiesManager(String rutaArchivo) {
        this.props = new Properties();
        cargarPropiedades(rutaArchivo);
    }

    private void cargarPropiedades(String rutaArchivo) {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(rutaArchivo)) {
            if (input == null) {
                System.err.println("[ERROR CONFIG] No se encontró '" + rutaArchivo + "' en el classpath.");
                return;
            }
            props.load(input);
            System.out.println("[CONFIG] " + rutaArchivo + " cargado exitosamente.");
        } catch (IOException e) {
            System.err.println("[ERROR CONFIG] Error al leer " + rutaArchivo + ": " + e.getMessage());
        }
    }

    @Override
    public String getString(String key) {
        return props.getProperty(key);
    }

    @Override
    public int getInt(String key) {
        return Integer.parseInt(props.getProperty(key));
    }

    @Override
    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(props.getProperty(key));
    }
}