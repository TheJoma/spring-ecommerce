package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.model.DetalleOrden;
import com.ecommerce.ecommerce.model.Orden;
import com.ecommerce.ecommerce.model.Producto;
import com.ecommerce.ecommerce.model.Usuario;
import com.ecommerce.ecommerce.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class HomeController {

    private final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private IProductoService productoService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IOrdenService ordenService;

    @Autowired
    private IDetalleOrdenService detalleOrdenService;

    //esto es para almacenar los detalles de la orden
    List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();

    //almacenar los datos de la orden
    Orden orden = new Orden();

    @GetMapping("")
    public String home(Model model){

        model.addAttribute("productos",productoService.findAll());

        return "usuario/home";
    }

    @GetMapping("productoHome/{id}")
    public String productoHome(@PathVariable Long id,Model model){
        log.info("id producto enviado como parametro {}",id);

        Producto producto = new Producto();
        Optional<Producto> productoOptional = productoService.get(id);
        producto = productoOptional.get();

        model.addAttribute("producto",producto);

        return "usuario/productoHome";
    }

    @PostMapping("/cart")
    public String addCart(@RequestParam Long id,@RequestParam Integer cantidad, Model model){
        DetalleOrden detalleOrden = new DetalleOrden();
        Producto producto = new Producto();
        double sumaTotal = 0;

        Optional<Producto> optionalProducto = productoService.get(id);
        log.info("Producto añadido: {}", optionalProducto.get());
        log.info("cantidad: {}",cantidad);

        producto=optionalProducto.get();

        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setNombre(producto.getNombre());
        detalleOrden.setTotal(producto.getPrecio()*cantidad);
        detalleOrden.setProducto(producto);

        //validar que el producto no se añada 2 veces
        Long idProducto = producto.getId();
        boolean ingresado =detalles.stream().anyMatch(p -> p.getProducto().getId()==idProducto);

        if(!ingresado){
            detalles.add(detalleOrden);
        }

        sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();

        orden.setTotal(sumaTotal);
        model.addAttribute("cart",detalles);
        model.addAttribute("orden",orden);


        return "usuario/carrito";
    }


    //quitar un producto del carrito
    @GetMapping("/delete/cart/{id}")
    public String deleteProductoCart(@PathVariable Long id, Model model){
        //lista nueva de productos
        List<DetalleOrden> ordenesNueva = new ArrayList<DetalleOrden>();

        for(DetalleOrden detalleOrden : detalles){
            if(detalleOrden.getProducto().getId()!=id){
                ordenesNueva.add(detalleOrden);
            }
        }

        //poner la nueva lista con los productos restantes
        detalles=ordenesNueva;

        double sumaTotal = 0;
        sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();

        orden.setTotal(sumaTotal);
        model.addAttribute("cart",detalles);
        model.addAttribute("orden",orden);

        return "usuario/carrito";
    }

    @GetMapping("/getCart")
    public String getCart(Model model){
        model.addAttribute("cart",detalles);
        model.addAttribute("orden",orden);
        return "/usuario/carrito";
    }

    @GetMapping("/order")
    public String order(Model model){
        Usuario usuario = usuarioService.findById(1L).get();
        log.info("usuario : {}" , usuario);
        model.addAttribute("cart",detalles);
        model.addAttribute("orden",orden);
        model.addAttribute("usuario",usuario);
        return "usuario/resumenorden";
    }

    //guardar la orden
    @GetMapping("/saveOrder")
    public String saveOrder(){
        Date fechaCreacion = new Date();
        orden.setFechaCreacion(fechaCreacion);
        orden.setNumero(ordenService.generarNumeroOrden());

        //usuario de la orden
        Usuario usuario = usuarioService.findById(1L).get();

        orden.setUsuario(usuario);
        ordenService.save(orden);

        //guardar detalles
        for(DetalleOrden dt: detalles){
            dt.setOrden(orden);
            detalleOrdenService.save(dt);
        }

        ///limpiar lista y orden
        orden = new Orden();
        detalles.clear();

        return "redirect:/";
    }

}
