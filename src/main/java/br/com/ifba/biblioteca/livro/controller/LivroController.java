package br.com.ifba.biblioteca.livro.controller;

import br.com.ifba.biblioteca.livro.entity.Livro;
import br.com.ifba.biblioteca.livro.repository.LivroRepository;
import br.com.ifba.biblioteca.livro.service.LivroIService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// Define que esta classe é um componente Spring gerenciável
@Component
public class LivroController implements LivroIController {
    
    // Injeta a implementação do serviço de livros (responsável pela lógica de negócio)
    @Autowired
    private LivroIService service;

    // Injeta o repositório (para consultas diretas ao banco, se necessário)
    @Autowired
    private LivroRepository repository;

    @Override
    public Livro save(Livro livro) {
        return service.save(livro);
    }

    @Override
    public Livro update(Livro livro) {
        return service.update(livro);
    }

    @Override
    public void delete(Livro livro) {
        service.delete(livro);
    }

    @Override
    public List<Livro> findAll() {
        return service.findAll();
    }

    @Override
    public Livro findById(Long id) {
        return service.findById(id);
    }

    @Override
    public Livro findByIsbn(String isbn) {
        return service.findByIsbn(isbn);
    }

    @Override
    public boolean existsByIsbn(String isbn) {
        return service.existsByIsbn(isbn);
    }
    
  

}
