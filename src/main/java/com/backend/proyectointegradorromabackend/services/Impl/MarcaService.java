package com.backend.proyectointegradorromabackend.services.Impl;

import com.backend.proyectointegradorromabackend.Repository.MarcaRepository;
import com.backend.proyectointegradorromabackend.models.entities.Marca;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    //listartodo
    @Transactional(readOnly = true)
    public List<Marca> list(){
        return marcaRepository.findAll();
    }

    //obtener uno
    @Transactional(readOnly = true)
    public Optional<Marca> findById(Integer id){
        return marcaRepository.findById(id);
    }

    //guardar
    @Transactional
    public Marca save(Marca marca){
        return marcaRepository.save(marca);
    }

    //actualizar
    @Transactional
    public Optional<Marca> update (Marca marca, Integer id){
        Optional<Marca> marcaOptional = this.findById(id);
        if(marcaOptional.isPresent()){
            Marca marcaUpdate = marcaOptional.orElseThrow();
            marcaUpdate.setNombre(marca.getNombre());
            marcaUpdate.setProveedor(marca.getProveedor());
            return Optional.of(this.save(marcaUpdate));
        }
        return Optional.empty();
    }

    public void remote(Integer id){
        marcaRepository.deleteById(id);
    }
}
