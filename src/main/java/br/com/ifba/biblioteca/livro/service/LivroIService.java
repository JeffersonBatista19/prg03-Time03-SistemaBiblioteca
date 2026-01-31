package br.com.ifba.biblioteca.livro.service;

import br.com.ifba.biblioteca.livro.entity.Livro;
import java.util.List;

public interface LivroIService {

    Livro save(Livro livro);

    Livro update(Livro livro);

    void delete(Livro livro);

    Livro findById(Long id);

    List<Livro> findAll();
    
    Livro findByIsbn(String isbn);

    boolean existsByIsbn(String isbn);
    
    public boolean existsByTitulo(String titulo);
    
    

}
