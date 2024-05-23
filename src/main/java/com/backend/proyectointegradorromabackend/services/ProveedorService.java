package com.backend.proyectointegradorromabackend.services;

import com.backend.proyectointegradorromabackend.models.entities.Proveedor;

import java.util.List;
import java.util.Optional;

public interface ProveedorService {

    //listar
    List<Proveedor> findAll();

    //obtener por id
    Optional<Proveedor> findById(Integer id);

    //guardar
    Proveedor save(Proveedor proveedor);

    //actualizar
    Optional<Proveedor> update(Proveedor proveedor, Integer id);

    //eliminar
    void remote(Integer id);
}
