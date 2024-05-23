package com.backend.proyectointegradorromabackend.controller;

import com.backend.proyectointegradorromabackend.models.entities.Marca;
import com.backend.proyectointegradorromabackend.services.Impl.CategoriaService;
import com.backend.proyectointegradorromabackend.services.Impl.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/marca")
@CrossOrigin("*")
public class MarcaController {

    @Autowired
    private MarcaService marcaService;


    @GetMapping("/list")
    public List<Marca> listar(){
        return marcaService.list();
    }

    @PostMapping("/save")
    public ResponseEntity<?> guardar(@RequestBody Marca marca){
        return ResponseEntity.status(HttpStatus.CREATED).body(marcaService.save(marca));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id){
        Optional<Marca> optionalMarca = marcaService.findById(id);
        if(optionalMarca.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(optionalMarca.orElseThrow());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@RequestBody Marca marca, @PathVariable Integer id){
        Optional<Marca> marcaOptional = marcaService.update(marca, id);
        if(marcaOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(marcaOptional.orElseThrow());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id){
        Optional<Marca> marcaOptional = marcaService.findById(id);
        if(marcaOptional.isPresent()){
            marcaService.remote(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
