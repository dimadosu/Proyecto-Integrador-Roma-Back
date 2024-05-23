package com.backend.proyectointegradorromabackend.models.request;

import com.backend.proyectointegradorromabackend.models.IUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest implements IUser {

    @Size(max = 30)
    @NotBlank // campo no vacio ni espacio
    private String username;

    @NotEmpty
    @Email
    private String email;

    private boolean admin;

    @NotBlank
    private String nombres;

    @NotBlank
    private String apellidoPaterno;

    @NotBlank
    private String apellidoMaterno;

}
