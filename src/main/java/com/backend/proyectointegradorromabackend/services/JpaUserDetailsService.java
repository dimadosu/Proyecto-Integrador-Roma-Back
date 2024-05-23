package com.backend.proyectointegradorromabackend.services;

import com.backend.proyectointegradorromabackend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    //método para hacer el login
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<com.backend.proyectointegradorromabackend.models.entities.User> userOptional =
                userRepository.findByUsername(username);

        //si no existe
        if(!userOptional.isPresent()){
            throw new UsernameNotFoundException(String.format("Username %s no existe en el sistema", username));
        }
        //obtenemos el usuario
        com.backend.proyectointegradorromabackend.models.entities.User user = userOptional.orElseThrow();

        //agregamos roles
        //obteniendo los roles del usuario y  seteamos como SimpleGrantedAuthority
        //antes era lista de grandeAuthority
        List<SimpleGrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toList());

        return new User(
                user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                authorities
        );
    }
}
