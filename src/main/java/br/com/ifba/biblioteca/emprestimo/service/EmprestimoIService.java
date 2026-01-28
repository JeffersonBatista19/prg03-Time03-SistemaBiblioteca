/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.biblioteca.emprestimo.service;

/**
 *
 * @author guilhermeAmedrado
 */

import br.com.ifba.biblioteca.emprestimo.entity.Emprestimo;
import java.util.List;

public interface EmprestimoIService {
    Emprestimo save(Emprestimo emprestimo);
    Emprestimo update(Emprestimo emprestimo);
    void delete(Emprestimo emprestimo);
    List<Emprestimo> findAll();
    Emprestimo findById(Long id);
}