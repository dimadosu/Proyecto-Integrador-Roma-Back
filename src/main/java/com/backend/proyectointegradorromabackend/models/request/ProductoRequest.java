package com.backend.proyectointegradorromabackend.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoRequest {

    private Integer idCategoria;

    private Integer idMarca;

    //private Integer idUsuario;

    private double precio;

    private int cantidad;

    private String fechaVencimiento;

    private String nombre;

    private Integer idUnidadMedida;

}
