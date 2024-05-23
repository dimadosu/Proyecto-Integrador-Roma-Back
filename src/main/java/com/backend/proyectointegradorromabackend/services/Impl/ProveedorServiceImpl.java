package com.backend.proyectointegradorromabackend.services.Impl;

import com.backend.proyectointegradorromabackend.Repository.ProveedorRepository;
import com.backend.proyectointegradorromabackend.models.entities.Proveedor;
import com.backend.proyectointegradorromabackend.services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorServiceImpl implements ProveedorService {


    @Autowired
    private ProveedorRepository proveedorRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Proveedor> findAll() {
        return proveedorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Proveedor> findById(Integer id) {
        Optional<Proveedor> proveedorOptional = proveedorRepository.findById(id); //buscamos el proveedor
        return proveedorOptional;
    }

    @Override
    @Transactional
    public Proveedor save(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    @Override
    @Transactional
    public Optional<Proveedor> update(Proveedor proveedor, Integer id) {
        //buscamos
        Optional<Proveedor> proveedorOptional = proveedorRepository.findById(id);
        if(proveedorOptional.isPresent()){
            Proveedor proveedorUpdate = proveedorOptional.orElseThrow();
            proveedorUpdate.setNombre(proveedor.getNombre());
            proveedorUpdate.setRuc(proveedor.getRuc());
            proveedorUpdate.setRazonSocial(proveedor.getRazonSocial());
            proveedorUpdate.setNombreContacto(proveedor.getNombreContacto());
            proveedorUpdate.setNumeroContacto(proveedor.getNumeroContacto());
            proveedorUpdate.setEmail(proveedor.getEmail());
            return Optional.of(proveedorRepository.save(proveedorUpdate));
        }
        return Optional.empty();
    }

    @Override
    public void remote(Integer id) {
        proveedorRepository.deleteById(id);
    }
}
