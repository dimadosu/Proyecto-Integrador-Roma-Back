package com.backend.proyectointegradorromabackend.models.entities;

import com.backend.proyectointegradorromabackend.models.IUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User implements IUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 30) //tamaño del nombre de usuario +
    @NotBlank //establecemo que el campo no este vacio y sin espacio en blanco
    @Column(unique = true) //establecemos como campo unico
    private String username;

    @NotBlank
    private String password;

    @NotEmpty
    @Email
    @Column(unique = true) //email como unico
    private String email;

    @Transient
    private boolean admin;

    @NotBlank
    private String nombres;

    @NotBlank
    private String apellidoPaterno;

    @NotBlank
    private String apellidoMaterno;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles", // nombre de la tabla  intermedia
            joinColumns = @JoinColumn(name = "user_id") //llave foranea
            , inverseJoinColumns = @JoinColumn(name = "role_id") //llave foranea 2
            , uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id","role_id"})}) // las llaves no se pueden repetir
    private List<Rol> roles;

}
