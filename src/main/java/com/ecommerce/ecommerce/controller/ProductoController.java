package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.model.Producto;
import com.ecommerce.ecommerce.model.Usuario;
import com.ecommerce.ecommerce.service.ProductoServiceImpl;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    //esto es para los test
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductoServiceImpl productoService;

    //vista para la lista de productos
    @GetMapping("")
    public String show(Model model){
        model.addAttribute("productos",productoService.findAll());
        return "producto/show";
    }

    //ir a la vista de registrar producto
    @GetMapping("/create")
    public String create(){
        return "producto/create";
    }
   //metodo para registrar el producto
    @PostMapping("/save")
    public String save(Producto producto){
        LOGGER.info("Este es el objeto de producto {}",producto);
        Usuario usuario = new Usuario(1L,"","","","","","","");
        producto.setUsuario(usuario);
        productoService.save(producto);
        return "redirect:/productos";
    }

    // obtiene los datos del producto y los muestra en la vista de actualizar
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        Producto producto = new Producto();
        Optional<Producto> optionalProducto = productoService.get(id);
        producto = optionalProducto.get();

        LOGGER.info("producto buscando : {}",producto);
        model.addAttribute("producto",producto);
       return "producto/edit";
    }

    //actualizar y dirigir a la vista de show
    @PostMapping("/update")
    public String update(Producto producto){
        productoService.update(producto);
        return "redirect:/productos";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        productoService.delete(id);
        return "redirect:/productos";
    }




}
