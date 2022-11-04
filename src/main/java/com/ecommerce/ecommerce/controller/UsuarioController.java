package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.model.Orden;
import com.ecommerce.ecommerce.model.Usuario;
import com.ecommerce.ecommerce.service.IOrdenService;
import com.ecommerce.ecommerce.service.IUsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IOrdenService ordenService;

    @GetMapping("/registro")
    public String create(){
        return "usuario/registro";
    }

    @PostMapping("/save")
    public String save(Usuario usuario){
        logger.info("Usuario registro : {}",usuario);
        usuario.setTipo("USER");
        usuarioService.save(usuario);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(){
       return "usuario/login";
    }

    @PostMapping("/acceder")
    public String acceder(Usuario usuario, HttpSession session){
        logger.info("Accesos : {}",usuario);
        Optional<Usuario> user = usuarioService.findByEmail(usuario.getEmail());

        //logger.info("Usuario de db: {}",user.get());

        if(user.isPresent()){
            session.setAttribute("idusuario",user.get().getId());
             if(user.get().getTipo().equals("ADMIN")){
                 return "redirect:/administrador";
             }else{
                 return "redirect:/";
             }
        } else {
            logger.info("Usuario no existe");
        }

        return "redirect:/";
    }

    @GetMapping("/compras")
    public String obtenerCompras(Model model, HttpSession session){
        model.addAttribute("session",session.getAttribute("idusuario"));
        Usuario usuario = usuarioService.findById(Long.parseLong(session.getAttribute("idusuario").toString())).get();
        List<Orden> ordenes = ordenService.findByUsuario(usuario);

        model.addAttribute("ordenes",ordenes);
        return "usuario/compras";
    }

    @GetMapping("/detalle/{id}")
    public String  detalleCompra(@PathVariable Long id, HttpSession session,Model model){
        logger.info("Id de la ordem : {}",id);
        Optional<Orden> orden = ordenService.findById(id);

        model.addAttribute("detalles",orden.get().getDetalle());
        //sesion
        model.addAttribute("sesion",session.getAttribute("idusuario"));
        return "usuario/detallecompra";
    }

    @GetMapping("/cerrar")
    public String cerrarSession(HttpSession session){
        session.removeAttribute("idusuario");
        return "redirect:/";
    }



}
