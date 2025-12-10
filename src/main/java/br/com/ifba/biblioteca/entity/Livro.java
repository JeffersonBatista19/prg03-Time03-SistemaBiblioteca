package br.com.ifba.biblioteca.entity;


import br.com.ifba.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
@Table(name = "livros")
public class Livro extends PersistenceEntity {
    
    @Column(name = "titulo", nullable = false)
    private String titulo;
    
    @Column(name = "isbn", nullable = false, unique = true)
    private String isbn;

    @Column(name = "categoria", nullable = false)
    private String categoria;
    
    @Column(name = "ano_publicacao", nullable = false)
    private int anoPublicacao;
    
    @Column(name = "autor", nullable = false)
    private String autor;

    @Column(name = "editora", nullable = false)
    private String editora;
}
