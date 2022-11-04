package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.Orden;
import com.ecommerce.ecommerce.model.Usuario;
import com.ecommerce.ecommerce.repository.IOrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrdenServiceImpl implements IOrdenService{

    @Autowired
    private IOrdenRepository ordenRepository;

    @Override
    public Orden save(Orden orden) {
        return ordenRepository.save(orden);
    }

    @Override
    public List<Orden> findAll() {
        return ordenRepository.findAll();
    }

    //formato 000010
    @Override
    public String generarNumeroOrden(){
        int numero = 0;
        String numeroConcatenado = "";

        List<Orden> ordenes = findAll();

        List<Integer> numeros = new ArrayList<Integer>();

        ordenes.stream().forEach(p -> numeros.add(Integer.parseInt(p.getNumero())));

        if(ordenes.isEmpty()){
            numero=1;
        }else{
            numero = numeros.stream().max(Integer::compare).get();
            numero++;
        }

        if (numero<10){
            numeroConcatenado = "000000000" + String.valueOf(numero);
        } else if (numero<100) {
            numeroConcatenado = "00000000" + String.valueOf(numero);
        } else if (numero<1000) {
            numeroConcatenado = "0000000" + String.valueOf(numero);
        }

        return numeroConcatenado;
    }

    @Override
    public List<Orden> findByUsuario(Usuario usuario) {
        return ordenRepository.findByUsuario(usuario);
    }

    @Override
    public Optional<Orden> findById(Long id) {
        return ordenRepository.findById(id);
    }
}
