package br.com.ifba.biblioteca.livro.service;

import br.com.ifba.biblioteca.livro.entity.Livro;
import br.com.ifba.biblioteca.livro.repository.LivroRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Responsável por regras de negócio da entidade Livro
@Service
public class LivroService implements LivroIService {
    
    // Injeta o repositório de livros para acessar o banco
    @Autowired
    private LivroRepository livroRepository;
    
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Livro save(Livro livro) {
        if (existsByIsbn(livro.getIsbn())) {
            throw new RuntimeException("Já existe um livro com este ISBN.");
        }
        return livroRepository.save(livro);
    }

    @Override
    public Livro update(Livro livro) {
        if (livro.getId() == null) {
            throw new RuntimeException("Livro não identificado para atualização.");
        }
        return livroRepository.save(livro);
    }

    @Override
    public void delete(Livro livro) {
        livroRepository.delete(livro);
    }

    @Override
    public Livro findById(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado."));
    }

    @Override
    public List<Livro> findAll() {
        return livroRepository.findAll();
    }
    
    @Override
    public Livro findByIsbn(String isbn) {
        return livroRepository.findByIsbn(isbn);
    }

    @Override
    public boolean existsByIsbn(String isbn) {
        return livroRepository.existsByIsbn(isbn);
    }
    
    @Override
    public boolean existsByTitulo(String titulo) {
        return livroRepository.existsByTitulo(titulo);
    }
    
    
    public List<Object[]> findAllComNomeAutor() {
    List<Livro> livros = livroRepository.findAll();

    return livros.stream().map(l -> new Object[]{
        l.getId(),
        l.getTitulo(),
        l.getIsbn(),
        l.getAutorNome(),
        l.getEditora().getNome(),
        l.getCategoriaId(),    
        l.getAnoPublicacao()
    }).toList();
}





}
