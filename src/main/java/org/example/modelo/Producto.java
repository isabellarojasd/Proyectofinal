package org.example.modelo;
import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Producto {
    private String nombre;
    private String descripcion;
    private Double precio;
    private int cantidadDisponible;
    private int id;

}

 