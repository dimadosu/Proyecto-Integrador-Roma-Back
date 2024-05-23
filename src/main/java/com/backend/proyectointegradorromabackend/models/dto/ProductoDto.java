package com.backend.proyectointegradorromabackend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@NoArgsConstructor
@Getter
@Setter
public class ProductoDto {

    private Integer id;

    private String categoria;

    private String marca;

    private String proveedor;

    private String usuario;

    private double precio;

    private int cantidad;

    private String fechaVencimiento;

    private String nombre;

    private String unidadMedida;

    public ProductoDto(Integer id, String categoria, String marca, String proveedor, double precio, int cantidad,
                       String fechaVencimiento, String nombre, String unidadMedida){
        this.id= id;
        this.categoria = categoria;
        this.marca = marca;
        this.proveedor = proveedor;
        this.precio = precio;
        this.cantidad = cantidad;
        this.fechaVencimiento = fechaVencimiento;
        this.nombre = nombre;
        this.unidadMedida = unidadMedida;
    }

    public ProductoDto(Integer id, String categoria, String marca,double precio, int cantidad,
                       String fechaVencimiento, String nombre, String unidadMedida){
        this.id= id;
        this.categoria = categoria;
        this.marca = marca;
        this.precio = precio;
        this.cantidad = cantidad;
        this.fechaVencimiento = fechaVencimiento;
        this.nombre = nombre;
        this.unidadMedida = unidadMedida;
    }



}
