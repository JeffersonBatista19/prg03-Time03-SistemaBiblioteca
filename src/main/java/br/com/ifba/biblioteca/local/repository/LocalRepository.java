/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.biblioteca.local.repository;

import br.com.ifba.biblioteca.local.entity.Local;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jeffe
 */

@Repository
public interface LocalRepository extends JpaRepository<Local, Long>{
    boolean existsByCidadeIgnoreCaseAndRuaIgnoreCaseAndAtivoTrue(
            String cidade, String rua);

    List<Local> findByAtivoTrue();

    Optional<Local> findByIdAndAtivoTrue(Long id);

    List<Local> findByCidadeContainingIgnoreCaseAndRuaContainingIgnoreCaseAndAtivoTrue(
            String cidade, String rua);
}
