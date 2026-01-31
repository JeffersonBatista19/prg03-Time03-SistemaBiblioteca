package br.com.ifba.biblioteca.livro.repository;

import br.com.ifba.biblioteca.livro.entity.Livro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    Livro findByIsbn(String isbn); // procura pelo ISBN.

    boolean existsByIsbn(String isbn); // Verifica se já existe algum livro com o ISBN informado.
    
    boolean existsByTitulo(String titulo); // Verifica se já existe algum livro com o título informado.
    
   


}
