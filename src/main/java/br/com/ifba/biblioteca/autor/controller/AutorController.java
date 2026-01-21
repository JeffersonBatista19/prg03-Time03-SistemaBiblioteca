/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.biblioteca.autor.controller;

import br.com.ifba.biblioteca.autor.entity.Autor;
import br.com.ifba.biblioteca.autor.service.AutorIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 *
 * @author guilherme
 */

@Controller // Define que esta classe é um Controller (conecta a tela ao service)
public class AutorController implements AutorIController {

    @Autowired // O Spring conecta o service automaticamente aqui
    private AutorIService autorService;

    @Override
    public Autor save(Autor autor) {
        // Manda o service salvar o autor no banco
        return autorService.save(autor);
    }

    @Override
    public Autor update(Autor autor) {
        // Manda o service atualizar os dados do autor
        return autorService.update(autor);
    }

    @Override
    public void delete(Long id) {
        // Manda o service deletar o autor pelo ID
        autorService.delete(id);
    }

    @Override
    public List<Autor> findAll() {
        // Pede ao service a lista de todos os autores
        return autorService.findAll();
    }

    @Override
    public Autor findById(Long id) {
        // Busca um autor específico pelo ID
        return autorService.findById(id);
    }

    @Override
    public List<Autor> findByNome(String nome) {
        // Busca autores pelo nome (para a pesquisa)
        return autorService.findByNome(nome);
    }
}