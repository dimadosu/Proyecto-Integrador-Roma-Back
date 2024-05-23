package com.backend.proyectointegradorromabackend.controller;

import com.backend.proyectointegradorromabackend.models.entities.Proveedor;
import com.backend.proyectointegradorromabackend.services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/proveedor")
@CrossOrigin(originPatterns = "*")
public class ProveedorController {


    @Autowired
    private ProveedorService proveedorService;

    @GetMapping("/list")
    public List<Proveedor> listar(){
        return proveedorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id){
        Optional<Proveedor> proveedorOptional = proveedorService.findById(id);
        if(proveedorOptional.isPresent()){
            return ResponseEntity.ok(proveedorOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    public ResponseEntity<?> guardar(@RequestBody Proveedor proveedor){
        return ResponseEntity.status(HttpStatus.CREATED).body(proveedorService.save(proveedor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@RequestBody Proveedor proveedor, @PathVariable Integer id){
        Optional<Proveedor> proveedorOptional = proveedorService.update(proveedor, id);
        if(proveedorOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(proveedorOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id){
        Optional<Proveedor> proveedorOptional = proveedorService.findById(id);
        if(proveedorOptional.isPresent()){
            proveedorService.remote(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
