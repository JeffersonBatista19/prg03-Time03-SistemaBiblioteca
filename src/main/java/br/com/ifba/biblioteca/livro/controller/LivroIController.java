package br.com.ifba.biblioteca.livro.controller;

import br.com.ifba.biblioteca.livro.entity.Livro;
import java.util.List;

public interface LivroIController {

    Livro save(Livro livro);

    Livro update(Livro livro);

    void delete(Livro livro);

    List<Livro> findAll();

    Livro findById(Long id);

    Livro findByIsbn(String isbn);

    boolean existsByIsbn(String isbn);
}
