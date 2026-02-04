/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.biblioteca.local.controller;

import br.com.ifba.biblioteca.local.entity.Local;
import java.util.List;

/**
 *
 * @author jeffe
 */
public interface LocalIController {
    Local save(Local local);
    Local update(Local local);

    void inativar(Long id);

    List<Local> findAllAtivos();
    Local findByIdAtivo(Long id);

    List<Local> findByCidadeAndRua(String cidade, String rua);
}
