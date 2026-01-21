package br.com.ifba.biblioteca.autor.controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import br.com.ifba.biblioteca.autor.entity.Autor;
import java.util.List;

/**
 *
 * @author guilherme
 */

public interface AutorIController {
    
    Autor save(Autor autor);
    Autor update(Autor autor);
    void delete(Long id); 
    List<Autor> findAll();
    Autor findById(Long id);
    List<Autor> findByNome(String nome);
}