/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.biblioteca.local.controller;

import br.com.ifba.biblioteca.local.entity.Local;
import br.com.ifba.biblioteca.local.service.LocalIService;
import java.util.List;
import org.springframework.stereotype.Controller;

/**
 *
 * @author jeffe
 */

@Controller
public class LocalController implements LocalIController {

    private final LocalIService service;

    public LocalController(LocalIService service) {
        this.service = service;
    }

    @Override
    public Local save(Local local) {
        return service.save(local);
    }

    @Override
    public Local update(Local local) {
        return service.update(local);
    }

    @Override
    public void inativar(Long id) {
        service.inativar(id);
    }

    @Override
    public List<Local> findAllAtivos() {
        return service.findAllAtivos();
    }

    @Override
    public Local findByIdAtivo(Long id) {
        return service.findByIdAtivo(id);
    }

    @Override
    public List<Local> findByCidadeAndRua(String cidade, String rua) {
        return service.findByCidadeAndRua(cidade, rua);
    }
}
