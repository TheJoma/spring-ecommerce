package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.Usuario;
import java.util.Optional;

public interface IUsuarioService {
    Optional<Usuario> findById(Long id);
}
