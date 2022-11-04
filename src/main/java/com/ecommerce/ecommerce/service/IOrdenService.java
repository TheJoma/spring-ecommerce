package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.Orden;
import com.ecommerce.ecommerce.model.Usuario;

import java.util.List;

public interface IOrdenService {
    Orden save(Orden orden);
    List<Orden> findAll();
    String generarNumeroOrden();

    List<Orden> findByUsuario(Usuario usuario);
}
