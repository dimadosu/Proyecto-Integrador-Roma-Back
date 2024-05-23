package com.backend.proyectointegradorromabackend.Repository;

import com.backend.proyectointegradorromabackend.models.entities.UnidadMedida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnidadMedidaRepository extends JpaRepository<UnidadMedida, Integer> {

    Optional<UnidadMedida> findByNombre(String nombre);

}
