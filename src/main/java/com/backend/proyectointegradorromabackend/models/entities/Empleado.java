package com.backend.proyectointegradorromabackend.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "empleados")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String dni;

    private String celular;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private User user;


}
