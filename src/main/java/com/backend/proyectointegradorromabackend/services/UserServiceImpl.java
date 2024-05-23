package com.backend.proyectointegradorromabackend.services;

import com.backend.proyectointegradorromabackend.Repository.RolRepository;
import com.backend.proyectointegradorromabackend.Repository.UserRepository;
import com.backend.proyectointegradorromabackend.models.IUser;
import com.backend.proyectointegradorromabackend.models.dto.UserDto;
import com.backend.proyectointegradorromabackend.models.dto.mapper.DtoMapperUser;
import com.backend.proyectointegradorromabackend.models.entities.Rol;
import com.backend.proyectointegradorromabackend.models.entities.User;
import com.backend.proyectointegradorromabackend.models.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RolRepository rolRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {

        List<User> users = userRepository.findAll();

        //convertimos cada usuario a objeto dto
        return users
                .stream()
                .map(u -> DtoMapperUser.builder().setUser(u).build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDto> findById(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            return Optional.of(
                   DtoMapperUser.builder().setUser(userOptional.orElseThrow()).build()
            );
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public UserDto save(User user) {
        //encriptamos la password del cliente
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        List<Rol> roles = getRoles(user);
        //cada vez que se crea un usuario tiene el rol user, el admin lo cambia despues
        user.setRoles(roles);
        return DtoMapperUser.builder().setUser(userRepository.save(user)).build();
    }

    @Override
    @Transactional
    public UserDto saveCustomer(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));//encriptamo la pass
        Optional<Rol> optionalRolCliente = rolRepository.findByName("ROLE_CUSTOMER"); //obtenemos el objeto de este rol
        List<Rol> roles = new ArrayList<>();
        roles.add(optionalRolCliente.orElseThrow());
        user.setRoles(roles); //agreamos el rol a usuario
        return DtoMapperUser.builder().setUser(userRepository.save(user)).build();
    }

    @Override
    @Transactional
    public Optional<UserDto> update(UserRequest user, Integer id) {
        //buscamos si existe el usuario
        Optional<User> userOptional = userRepository.findById(id);
        User userUpdate = null;
        if(userOptional.isPresent()){
            List<Rol> roles = getRoles(user);
            User userDb = userOptional.orElseThrow();
            userDb.setUsername(user.getUsername());
            userDb.setRoles(roles);
            userDb.setEmail(user.getEmail());
            userDb.setNombres(user.getNombres());
            userDb.setApellidoMaterno(user.getApellidoMaterno());
            userDb.setApellidoPaterno(user.getApellidoPaterno());
            //devuelve opctional con valor
            userUpdate = userRepository.save(userDb);
        }

        return Optional.ofNullable(DtoMapperUser.builder().setUser(userUpdate).build());
    }

    @Override
    public void remote(Integer id) {
        userRepository.deleteById(id);
    }

    private List<Rol> getRoles(IUser user){
        Optional<Rol> optionalRolUsuario = rolRepository.findByName("ROLE_USER");

        List<Rol> roles = new ArrayList<>();

        //por defecto todos los usuarios creados son user
        if(optionalRolUsuario.isPresent()){
            roles.add(optionalRolUsuario.orElseThrow());
        }

        if(user.isAdmin()){
            Optional<Rol> optionalRolAdmin = rolRepository.findByName("ROLE_ADMIN");
            if(optionalRolAdmin.isPresent()){
                roles.add(optionalRolAdmin.orElseThrow());
            }
        }
        return roles;
    }
}
