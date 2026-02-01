/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */



package br.com.ifba.biblioteca.usuario.controller;

import br.com.ifba.biblioteca.usuario.entity.Usuario;
import br.com.ifba.biblioteca.usuario.service.UsuarioIService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author jeffe
 */


@Controller
public class UsuarioController implements UsuarioIController {

    @Autowired
    private UsuarioIService service;

    @Override
    public Usuario save(Usuario usuario) {
        return service.save(usuario);
    }

    @Override
    public Usuario update(Usuario usuario) {
        return service.update(usuario);
    }

    @Override
    public List<Usuario> findAll() {
        return service.findAll();
    }

    @Override
    public Usuario findById(Long id) {
        return service.findById(id);
    }

    @Override
    public Usuario findByLogin(String login) {
        return service.findByLogin(login);
    }

    @Override
    public boolean existsByCpf(String cpf) {
        return service.existsByCpf(cpf);
    }

}