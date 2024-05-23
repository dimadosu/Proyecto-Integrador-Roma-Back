package com.backend.proyectointegradorromabackend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private Integer id;

    private String username;

    private String email;

    private boolean admin;

    private String nombres;

    private String apellidoPaterno;

    private String apellidoMaterno;


}
