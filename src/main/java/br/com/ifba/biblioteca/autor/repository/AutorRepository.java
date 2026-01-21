/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.biblioteca.autor.repository;
import br.com.ifba.biblioteca.autor.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
/**
 *
 * @author guilherme
 */


@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    // Verifica se já existe um nome igual no banco (Regra de Duplicidade)
    boolean existsByNome(String nome);

    // Busca autores contendo o trecho do nome (para a pesquisa)
    List<Autor> findByNomeContainingIgnoreCase(String nome);
}
