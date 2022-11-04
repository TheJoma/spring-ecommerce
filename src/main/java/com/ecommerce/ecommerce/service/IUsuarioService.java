package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    List<Usuario> findAll();
    Optional<Usuario> findById(Long id);
    Usuario save(Usuario usuario);
    Optional<Usuario> findByEmail (String email);
}
