package com.backend.proyectointegradorromabackend.services.Impl;

import com.backend.proyectointegradorromabackend.Repository.CategoriaRepository;
import com.backend.proyectointegradorromabackend.models.entities.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    //listatodo
    @Transactional(readOnly = true)
    public List<Categoria> list(){
        return categoriaRepository.findAll();
    }


    //obtener por id
    @Transactional(readOnly = true)
    public Optional<Categoria> findById(Integer id){
        return categoriaRepository.findById(id);
    }

    //guarda
    @Transactional
    public Categoria save(Categoria categoria){
        return categoriaRepository.save(categoria);
    }

    //actualizar
    @Transactional
    public Optional<Categoria> update(Categoria categoria, Integer id){
        Optional<Categoria> categoriaOptional = this.findById(id);
        if(categoriaOptional.isPresent()){
            Categoria categoriaUpdate = categoriaOptional.orElseThrow();
            categoriaUpdate.setNombre(categoria.getNombre());
            return Optional.of(this.save(categoriaUpdate));
        }
        return Optional.empty();
    }

    //eliminar
    public void remote(Integer id){
        categoriaRepository.deleteById(id);
    }
}
