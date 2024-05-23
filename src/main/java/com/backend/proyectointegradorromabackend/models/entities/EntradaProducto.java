package com.backend.proyectointegradorromabackend.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "entrada_productos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EntradaProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int stock;

    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;


}
