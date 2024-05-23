package com.backend.proyectointegradorromabackend.Repository;

import com.backend.proyectointegradorromabackend.models.entities.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {

    Optional<Proveedor> findByNombre(String nombre);
}
