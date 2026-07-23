package org.example.auditoria;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Implementación de Auditor que registra cada operación en un archivo
 * de texto plano, agregando fecha y hora a cada entrada.
 */
public class AuditorArchivo implements IAuditor {

    private final String rutaArchivo;
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Crea un auditor que escribe en el archivo indicado.
     * @param rutaArchivo ruta del archivo donde se guardarán los registros de auditoría.
     */
    public AuditorArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    /**
     * Registra una operación agregando una línea al archivo de auditoría.
     * Si ocurre un error al escribir, se imprime el error en consola
     * pero no se interrumpe la operación principal del sistema.
     * @param operacion tipo de operación realizada.
     * @param ip dirección IP del cliente que ejecutó la operación.
     * @param recurso recurso afectado por la operación.
     */
    @Override
    public void registrar(String operacion, String ip, String recurso) {
        String fecha = LocalDateTime.now().format(FORMATO_FECHA);
        String linea = fecha + " | " + operacion + " | IP: " + ip + " | Recurso: " + recurso;

        try (FileWriter escritor = new FileWriter(rutaArchivo, true)) {
            escritor.write(linea + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de auditoría: " + e.getMessage());
        }
    }
}