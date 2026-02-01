/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.biblioteca.categoria.controller;

import br.com.ifba.biblioteca.categoria.entity.Categoria;
import br.com.ifba.biblioteca.categoria.service.CategoriaIService;
import java.util.List;
import org.springframework.stereotype.Controller;

/**
 *
 * @author jeffe
 */

@Controller
public class CategoriaController implements CategoriaIController {

    private final CategoriaIService service;

    public CategoriaController(CategoriaIService service) {
        this.service = service;
    }

    @Override
    public Categoria save(Categoria categoria) {
        return service.save(categoria);
    }

    @Override
    public Categoria update(Categoria categoria) {
        return service.update(categoria);
    }

    @Override
    public void inativar(Long id) {
        service.inativar(id);
    }

    @Override
    public List<Categoria> findAllAtivas() {
        return service.findAllAtivas();
    }

    @Override
    public Categoria findByIdAtiva(Long id) {
        return service.findByIdAtiva(id);
    }

    @Override
    public List<Categoria> findByNome(String nome) {
        return service.findByNome(nome);
    }
}
