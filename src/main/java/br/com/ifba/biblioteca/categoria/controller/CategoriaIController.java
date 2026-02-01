/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.biblioteca.categoria.controller;

import br.com.ifba.biblioteca.categoria.entity.Categoria;
import java.util.List;

/**
 *
 * @author jeffe
 */

public interface CategoriaIController {

    Categoria save(Categoria categoria);
    Categoria update(Categoria categoria);

    void inativar(Long id);

    List<Categoria> findAllAtivas();
    Categoria findByIdAtiva(Long id);
    List<Categoria> findByNome(String nome);
}
