package com.backend.proyectointegradorromabackend.Repository;

import com.backend.proyectointegradorromabackend.models.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
