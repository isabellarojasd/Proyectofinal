package org.example.protocolo;

import lombok.*;
import org.example.modelo.Producto;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Respuesta implements Serializable {
    private boolean exito;
    private String mensaje;
    private List<Producto> productos; // resultado de BUSCAR/LISTAR, o vacío
}