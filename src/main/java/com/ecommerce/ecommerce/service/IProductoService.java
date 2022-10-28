package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.Producto;

import java.util.List;
import java.util.Optional;

public interface IProductoService {
    //guardar
    public Producto save(Producto producto);

    //para obtener un objeto de la base de datos si retorna null por eso es el optional
    public Optional<Producto> get(Long id);

    //actualizar producto
    public void update(Producto producto);

    //Eliminar Producto
    public void delete(Long id);

    //para mostrar el producto
    public List<Producto> findAll();

}
