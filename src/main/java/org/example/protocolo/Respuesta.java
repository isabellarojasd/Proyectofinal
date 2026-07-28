package org.example.protocolo;

import lombok.*;
import org.example.modelo.Producto;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * Esta clase representa la respuesta que envia el servidor al cliente
 * Guarda si la operacion fue exitosa un mensaje y una lista de productos cuando es necesario
 */
public class Respuesta implements Serializable {
    private boolean exito;
    private String mensaje;
    private List<Producto> productos; // resultado de BUSCAR/LISTAR, o vacío
}