/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.biblioteca.categoria.repository;

import br.com.ifba.biblioteca.categoria.entity.Categoria;
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

    boolean existsByNomeIgnoreCase(String nome);
    
    boolean existsByNomeIgnoreCaseAndAtivoTrue(String nome);

    List<Categoria> findByAtivoTrue();

    Optional<Categoria> findByIdAndAtivoTrue(Long id);

    List<Categoria> findByNomeContainingIgnoreCaseAndAtivoTrue(String nome);
    
    List<Categoria> findByNomeIgnoreCase(String nome);
}