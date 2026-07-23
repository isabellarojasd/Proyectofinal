package org.example.exportar;

import org.example.modelo.Producto;

import java.io.IOException;
import java.util.List;

/**
 * Define el contrato para exportar el inventario a distintos formatos.
 */
public interface IExportarInventario {

    /**
     * Exporta la lista de productos al destino indicado.
     * @param productos lista de productos a exportar.
     * @param rutaDestino ruta del archivo donde se guardará la exportación.
     * @throws IOException si ocurre un error al escribir el archivo.
     */
    void exportar(List<Producto> productos, String rutaDestino) throws IOException;
}