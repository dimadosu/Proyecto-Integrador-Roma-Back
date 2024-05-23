package com.backend.proyectointegradorromabackend.controller;

import com.backend.proyectointegradorromabackend.models.dto.ProductoDto;
import com.backend.proyectointegradorromabackend.models.entities.Producto;
import com.backend.proyectointegradorromabackend.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/producto")
@CrossOrigin("*")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/list")
    public List<ProductoDto> listar(){
        return productoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Integer id){
        Optional<ProductoDto>  productoDto = productoService.findById(id);
        if(productoDto.isPresent()){
            return ResponseEntity.ok(productoDto.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    public ResponseEntity<?> guardar(@RequestBody Producto producto){

        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.save(producto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id){
        Optional<ProductoDto> optionalProductoDto = productoService.findById(id);
        if(optionalProductoDto.isPresent()){
            productoService.remote(id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }
}
