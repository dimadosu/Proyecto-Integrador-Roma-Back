package com.backend.proyectointegradorromabackend.services.Impl;

import com.backend.proyectointegradorromabackend.Repository.*;
import com.backend.proyectointegradorromabackend.models.dto.ProductoDto;
import com.backend.proyectointegradorromabackend.models.dto.mapper.DtoMapperProducto;
import com.backend.proyectointegradorromabackend.models.entities.Producto;
import com.backend.proyectointegradorromabackend.models.entities.User;
import com.backend.proyectointegradorromabackend.models.request.ProductoRequest;
import com.backend.proyectointegradorromabackend.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UnidadMedidaRepository unidadMedidaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDto> findAll() {
        //obtenemostodo
        List<Producto> productos = (List<Producto>) productoRepository.findAll();
        //convertimos los productos a objeto dtoproducto
        return productos
                .stream()
                .map(p -> DtoMapperProducto.builder().setProducto(p).build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoDto> findById(Integer id) {
        Optional<Producto> productoOptional = productoRepository.findById(id);
        if(productoOptional.isPresent()){
            return Optional.of(
              DtoMapperProducto.builder().setProducto(productoOptional.orElseThrow()).build()
            );
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public ProductoDto save(Producto producto) {
        Producto productoSave = productoRepository.save(producto);
        return new ProductoDto(
            productoSave.getId(),
            productoSave.getCategoria().getNombre(),
            productoSave.getMarca().getNombre(),
            productoSave.getPrecio(),
            productoSave.getCantidad(),
            productoSave.getFechaVencimiento(),
            productoSave.getNombre(),
            productoSave.getUnidadMedida().getNombre());
    }

    @Override
    @Transactional
    public Optional<ProductoDto> update(ProductoRequest producto, Integer id) {
        return Optional.empty();
    }


    @Override
    @Transactional
    public void remote(Integer id) {
        productoRepository.deleteById(id);
    }
}
