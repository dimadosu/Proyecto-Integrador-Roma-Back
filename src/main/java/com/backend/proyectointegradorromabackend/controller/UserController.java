package com.backend.proyectointegradorromabackend.controller;

import com.backend.proyectointegradorromabackend.models.dto.UserDto;
import com.backend.proyectointegradorromabackend.models.entities.User;
import com.backend.proyectointegradorromabackend.models.request.UserRequest;
import com.backend.proyectointegradorromabackend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin(originPatterns = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public List<UserDto> listar(){
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Integer id){
        Optional<UserDto> userOptional = userService.findById(id);
        if(userOptional.isPresent()){
            return ResponseEntity.ok(userOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody User user, BindingResult result){
        if(result.hasErrors()){
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@Valid @RequestBody UserRequest userRequest, BindingResult result, @PathVariable Integer id){
        if(result.hasErrors()){
            return validation(result);
        }

        Optional<UserDto> userDtoOptional = userService.update(userRequest, id);
        if(userDtoOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(userDtoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id){
        Optional<UserDto> userOptional = userService.findById(id);
        if(userOptional.isPresent()){
            userService.remote(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> registrarCliente(@RequestBody User user){
        return  ResponseEntity.status(HttpStatus.CREATED).body(userService.saveCustomer(user));
    }

    private ResponseEntity<?> validation (BindingResult result){
        //objeto map que contiene los mensajes de errores
        Map<String, String> errors = new HashMap<>();
        //obtenemos los mensaje y
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(),"El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return  ResponseEntity.badRequest().body(errors);
    }

}
