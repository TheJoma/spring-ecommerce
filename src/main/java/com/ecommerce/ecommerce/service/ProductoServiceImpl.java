package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.Producto;
import com.ecommerce.ecommerce.repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements IProductoService{

    @Autowired
    IProductoRepository productoRepository;

    //guardar el producto
    @Override
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    //Obtener un solo producto
    @Override
    public Optional<Producto> get(Long id) {
        return productoRepository.findById(id);
    }

    //actualizar el producto
    @Override
    public void update(Producto producto) {
        productoRepository.save(producto);
    }

    //eliminar el producto
    @Override
    public void delete(Long id) {
        productoRepository.deleteById(id);
    }
    
    //mostrar el producto
    @Override
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }


}
