package com.backend.proyectointegradorromabackend.services;

import com.backend.proyectointegradorromabackend.models.dto.ProductoDto;
import com.backend.proyectointegradorromabackend.models.entities.Producto;
import com.backend.proyectointegradorromabackend.models.entities.User;
import com.backend.proyectointegradorromabackend.models.request.ProductoRequest;

import java.util.List;
import java.util.Optional;

public interface ProductoService {

    //listartodo
    List<ProductoDto> findAll();

    //obtener por id
    Optional<ProductoDto> findById(Integer id);

    //recibe un user para guardar, pero devuelve un userDTO
    ProductoDto save(Producto producto);

    //actualizar
    Optional<ProductoDto> update (ProductoRequest producto, Integer id);

    //eliminar
    void remote(Integer id);
}
