package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.model.Producto;
import com.ecommerce.ecommerce.model.Usuario;
import com.ecommerce.ecommerce.service.ProductoServiceImpl;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    //esto es para los test
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductoServiceImpl productoService;

    @GetMapping("")
    public String show(){
        return "producto/show";
    }

    @GetMapping("/create")
    public String create(){
        return "producto/create";
    }

    @PostMapping("/save")
    public String save(Producto producto){
        LOGGER.info("Este es el objeto de producto {}",producto);
        Usuario usuario = new Usuario(1L,"","","","","","","");
        producto.setUsuario(usuario);
        productoService.save(producto);
        return "redirect:/productos";
    }

}
