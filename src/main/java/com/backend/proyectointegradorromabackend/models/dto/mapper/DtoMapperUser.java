package com.backend.proyectointegradorromabackend.models.dto.mapper;

import com.backend.proyectointegradorromabackend.models.dto.UserDto;
import com.backend.proyectointegradorromabackend.models.entities.User;

public class DtoMapperUser {


    private User user;

    private DtoMapperUser(){} // no se puede hacer instancia de la clase

    public static DtoMapperUser builder(){
        return  new DtoMapperUser();
    }

    public DtoMapperUser setUser(User user) {
        this.user = user;
        return this;
    }

    public UserDto build(){

        if(user == null){
            throw new RuntimeException("Debe pasar el entity user");
        }

        boolean isAdmin = user.getRoles().stream().anyMatch(r -> "ROLE_ADMIN".equals(r.getName()));
        return new UserDto(user.getId()
                , user.getUsername()
                , user.getEmail()
                , isAdmin
                , user.getNombres()
                ,user.getApellidoPaterno()
                ,user.getApellidoMaterno());
    }

}
