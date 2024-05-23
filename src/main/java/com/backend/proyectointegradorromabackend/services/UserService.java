package com.backend.proyectointegradorromabackend.services;

import com.backend.proyectointegradorromabackend.models.dto.UserDto;
import com.backend.proyectointegradorromabackend.models.entities.User;
import com.backend.proyectointegradorromabackend.models.request.UserRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {

    //listartodo
    List<UserDto> findAll();

    Optional<UserDto> findById(Integer id);

    //recibe un user para guardar, pero devuelve un userDTO
    UserDto save(User user);

    Optional<UserDto> update (UserRequest user, Integer id);

    void remote(Integer id);

    UserDto saveCustomer(User user);
}
