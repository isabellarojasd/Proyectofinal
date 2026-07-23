package org.example.exportar;

import org.example.modelo.Producto;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Implementación de ExportadorInventario que genera un archivo CSV
 * con el listado completo del inventario.
 */
public class ExportarInventarioCSV implements IExportarInventario {

    /**
     * Genera un archivo CSV con columnas: Id, Nombre, Descripcion, Precio, CantidadDisponible.
     * @param productos lista de productos a exportar.
     * @param rutaDestino ruta del archivo CSV que se generará.
     * @throws IOException si ocurre un error al escribir el archivo.
     */
    @Override
    public void exportar(List<Producto> productos, String rutaDestino) throws IOException {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaDestino))) {
            escritor.write("Id,Nombre,Descripcion,Precio,CantidadDisponible");
            escritor.newLine();

            for (Producto producto : productos) {
                String linea = producto.getId() + ","
                        + escaparCampo(producto.getNombre()) + ","
                        + escaparCampo(producto.getDescripcion()) + ","
                        + escaparCampo(String.valueOf(producto.getPrecio())) + ","
                        + producto.getCantidadDisponible();

                escritor.write(linea);
                escritor.newLine();
            }
        }
    }

    /**
     * Escapa un campo de texto para formato CSV, envolviéndolo en comillas
     * si contiene comas, comillas o saltos de línea, para no romper el formato.
     * @param campo el texto original.
     * @return el texto seguro para insertar en una línea CSV.
     */
    private String escaparCampo(String campo) {
        if (campo == null) {
            return "";
        }
        if (campo.contains(",") || campo.contains("\"") || campo.contains("\n")) {
            String escapado = campo.replace("\"", "\"\"");
            return "\"" + escapado + "\"";
        }
        return campo;
    }
}