package com.backend.proyectointegradorromabackend.models.dto.mapper;

import com.backend.proyectointegradorromabackend.models.dto.ProductoDto;
import com.backend.proyectointegradorromabackend.models.entities.Producto;

public class DtoMapperProducto {

    private Producto producto;

    private DtoMapperProducto(){

    }

    public static DtoMapperProducto builder(){
        return new DtoMapperProducto();
    }

    public DtoMapperProducto setProducto(Producto producto){
        this.producto = producto;
        return this;
    }

    public ProductoDto build(){
        if(producto == null){
            throw  new RuntimeException("Debe pasar el entity producto");
        }
        return new ProductoDto(
                producto.getId(),
                producto.getCategoria().getNombre(),
                producto.getMarca().getNombre(),
                producto.getMarca().getProveedor().getNombre(),
                producto.getPrecio(),
                producto.getCantidad(),
                producto.getFechaVencimiento(),
                producto.getNombre(),
                producto.getUnidadMedida().getNombre()
        );
    }
}
