/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.biblioteca.livro.repository;

import br.com.ifba.biblioteca.livro.entity.Categoria;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jeffe
 */

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findByAtivoTrue();

    List<Categoria> findByNomeContainingIgnoreCaseAndAtivoTrue(String nome);

    Optional<Categoria> findByIdAndAtivoTrue(Long id);

    boolean existsByNomeIgnoreCase(String nome);
}
