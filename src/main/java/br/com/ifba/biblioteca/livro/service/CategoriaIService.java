/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.biblioteca.livro.service;

import br.com.ifba.biblioteca.livro.entity.Categoria;
import java.util.List;

/**
 *
 * @author jeffe
 */

public interface CategoriaIService {

    Categoria save(Categoria categoria);

    Categoria update(Categoria categoria);

    void inativar(Long id);

    Categoria findByIdAtivo(Long id);

    List<Categoria> findAllAtivas();

    List<Categoria> findByNome(String nome);
}
