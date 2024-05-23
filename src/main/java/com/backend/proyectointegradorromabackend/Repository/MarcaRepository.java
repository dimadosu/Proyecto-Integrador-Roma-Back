package com.backend.proyectointegradorromabackend.Repository;

import com.backend.proyectointegradorromabackend.models.entities.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarcaRepository extends JpaRepository<Marca, Integer> {

    Optional<Marca> findByNombre(String nombre);
}
