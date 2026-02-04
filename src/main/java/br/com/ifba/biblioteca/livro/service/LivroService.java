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

    @Autowired
    private br.com.ifba.biblioteca.categoria.repository.CategoriaRepository categoriaRepository;

    @Autowired
    private br.com.ifba.biblioteca.editora.repository.EditoraRepository editoraRepository;
    
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Livro save(Livro livro) {
        if (existsByIsbn(livro.getIsbn())) {
            throw new RuntimeException("Já existe um livro com este ISBN.");
        }
        
        // CORREÇÃO: Busca entidade completa para garantir que o nome não seja nulo
        if (livro.getCategoria() != null && livro.getCategoria().getId() != null) {
            br.com.ifba.biblioteca.categoria.entity.Categoria cat = categoriaRepository.findById(livro.getCategoria().getId()).orElse(null);
            if (cat != null) {
                livro.setCategoria(cat); // Reanexa objeto completo
                livro.setCategoriaNome(cat.getNome());
            }
        }
        
        if (livro.getEditora() != null && livro.getEditora().getId() != null) {
            br.com.ifba.biblioteca.editora.entity.Editora ed = editoraRepository.findById(livro.getEditora().getId()).orElse(null);
            if (ed != null) {
                livro.setEditora(ed); // Reanexa objeto completo
                livro.setEditoraNome(ed.getNome());
            }
        }
        
        return livroRepository.save(livro);
    }

    @Override
    public Livro update(Livro livro) {
        if (livro.getId() == null) {
            throw new RuntimeException("Livro não identificado para atualização.");
        }
        
        // CORREÇÃO: Busca entidade completa para garantir que o nome não seja nulo
        if (livro.getCategoria() != null && livro.getCategoria().getId() != null) {
            br.com.ifba.biblioteca.categoria.entity.Categoria cat = categoriaRepository.findById(livro.getCategoria().getId()).orElse(null);
            if (cat != null) {
                livro.setCategoria(cat);
                livro.setCategoriaNome(cat.getNome());
            }
        }
        
        if (livro.getEditora() != null && livro.getEditora().getId() != null) {
            br.com.ifba.biblioteca.editora.entity.Editora ed = editoraRepository.findById(livro.getEditora().getId()).orElse(null);
            if (ed != null) {
                livro.setEditora(ed);
                livro.setEditoraNome(ed.getNome());
            }
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
        if (isbn == null) return null;
        return livroRepository.findByIsbnIgnoreCase(isbn.trim());
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

    return livros.stream().map(l -> {
        // Usa o nome desnormalizado se existir, senão tenta obter do objeto relacionado
        String nomeEditora = l.getEditoraNome();
        if (nomeEditora == null && l.getEditora() != null) {
            nomeEditora = l.getEditora().getNome();
        }
        
        String nomeCategoria = l.getCategoriaNome();
        if (nomeCategoria == null && l.getCategoria() != null) {
            nomeCategoria = l.getCategoria().getNome();
        }
        
        return new Object[]{
            l.getId(),
            l.getTitulo(),
            l.getIsbn(),
            l.getAutorNome(),
            nomeEditora != null ? nomeEditora : "N/A",
            nomeCategoria != null ? nomeCategoria : "N/A",
            l.getAnoPublicacao()
        };
    }).toList();
}





}
