package org.example.auditoria;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Convierte el archivo de auditoría (texto plano generado por AuditorArchivo)
 * en un archivo CSV, para facilitar su análisis en herramientas externas.
 */
public class ExportadorAuditoriaCsv {

    /**
     * Lee el archivo de auditoría en texto plano y genera un archivo CSV
     * con columnas: Fecha, Operacion, IP, Recurso.
     *
     * @param rutaOrigen ruta del archivo de auditoría en texto plano.
     * @param rutaDestino ruta donde se generará el archivo CSV.
     * @throws IOException si ocurre un error al leer el origen o escribir el destino.
     */
    public void exportar(String rutaOrigen, String rutaDestino) throws IOException {
        List<String> lineas = Files.readAllLines(Paths.get(rutaOrigen));

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaDestino))) {
            escritor.write("Fecha,Operacion,IP,Recurso");
            escritor.newLine();

            for (String linea : lineas) {
                String[] partes = linea.split("\\|");
                if (partes.length == 4) {
                    String fecha = partes[0].trim();
                    String operacion = partes[1].trim();
                    String ip = partes[2].replace("IP:", "").trim();
                    String recurso = partes[3].replace("Recurso:", "").trim();

                    escritor.write(fecha + "," + operacion + "," + ip + "," + recurso);
                    escritor.newLine();
                }
            }
        }
    }
}