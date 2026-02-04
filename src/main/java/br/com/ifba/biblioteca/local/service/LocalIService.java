/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.biblioteca.local.service;

import br.com.ifba.biblioteca.local.entity.Local;
import java.util.List;

/**
 *
 * @author jeffe
 */
public interface LocalIService {
    
    Local save(Local local);
    Local update(Local local);

    void inativar(Long id);

    List<Local> findAllAtivos();
    Local findByIdAtivo(Long id);

    // busca por cidade e rua (parcial). Se ambos vazios -> lista tudo
    List<Local> findByCidadeAndRua(String cidade, String rua);
    
}
