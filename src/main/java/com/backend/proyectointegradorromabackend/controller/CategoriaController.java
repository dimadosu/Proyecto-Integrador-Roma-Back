package com.backend.proyectointegradorromabackend.controller;

import com.backend.proyectointegradorromabackend.models.entities.Categoria;
import com.backend.proyectointegradorromabackend.services.Impl.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categoria")
@CrossOrigin("*")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    //listartodo
    @GetMapping("/list")
    public List<Categoria> listar(){
        return categoriaService.list();
    }

    //guardar
    @PostMapping("/save")
    public ResponseEntity<?> guardar(@RequestBody Categoria categoria){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.save(categoria));
    }

    //obtener por id
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id){
        Optional<Categoria> categoriaOptional = categoriaService.findById(id);
        if(categoriaOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(categoriaOptional.orElseThrow());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@RequestBody Categoria categoria, @PathVariable Integer id){
        Optional<Categoria> categoriaOptional = categoriaService.update(categoria,id);
        if(categoriaOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(categoriaOptional.orElseThrow());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id){
        Optional<Categoria> categoriaOptional = categoriaService.findById(id);
        if(categoriaOptional.isPresent()){
            categoriaService.remote(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
