package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.model.Producto;
import com.ecommerce.ecommerce.model.Usuario;
import com.ecommerce.ecommerce.service.ProductoServiceImpl;
import com.ecommerce.ecommerce.service.UploadFileService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    //esto es para los test
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductoServiceImpl productoService;

    @Autowired
    private UploadFileService upload;

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
    public String save(Producto producto,@RequestParam("img") MultipartFile file) throws IOException {
        LOGGER.info("Este es el objeto de producto {}",producto);
        Usuario usuario = new Usuario(1L,"","","","","","","");
        producto.setUsuario(usuario);

        //imagen
        if(producto.getId()==null){ //cuando se crea un producto el id sera null
            String nombreImagen = upload.saveImage(file);
            producto.setImagen(nombreImagen);
        }

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
    public String update(Producto producto,@RequestParam("img") MultipartFile file) throws IOException {
        Producto p= new Producto();
        p=productoService.get(producto.getId()).get();

        if (file.isEmpty()){//editamos el producto pero no cambiamos la imagen
            producto.setImagen(p.getImagen());
        }else{//cuadno se edita tbn la imagen
            //para eliminar cuando no sea la imagen por defecto
            if(!p.getImagen().equals("default.jpg")){
                upload.deleteImage(p.getImagen());
            }
            String nombreImagen = upload.saveImage(file);
            producto.setImagen(nombreImagen);
        }
        producto.setUsuario(p.getUsuario());
        productoService.update(producto);

        return "redirect:/productos";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        Producto p = new Producto();
        p=productoService.get(id).get();

        //para eliminar cuando no sea la imagen por defecto
        if(!p.getImagen().equals("default.jpg")){
            upload.deleteImage(p.getImagen());
        }

        productoService.delete(id);
        return "redirect:/productos";
    }




}
