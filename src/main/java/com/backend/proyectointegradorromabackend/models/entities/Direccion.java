package com.backend.proyectointegradorromabackend.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "direcciones")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String distrito;

    private String calle;

    private String referencia;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
}
