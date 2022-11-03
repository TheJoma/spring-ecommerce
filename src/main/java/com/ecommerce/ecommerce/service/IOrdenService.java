package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.Orden;

import java.util.List;

public interface IOrdenService {
    Orden save(Orden orden);

    List<Orden> findAll();

    String generarNumeroOrden();
}
