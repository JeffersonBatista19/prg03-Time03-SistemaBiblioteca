package br.com.ifba.biblioteca.livro.entity;

import br.com.ifba.infrastructure.entity.PersistenceEntity;
import br.com.ifba.biblioteca.editora.entity.Editora;
import br.com.ifba.biblioteca.categoria.entity.Categoria;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "livro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Livro extends PersistenceEntity {

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, unique = true)
    private String isbn;

    private int anoPublicacao;

    private int totalExemplares;

    @Column(name = "autor_nome", nullable = false)
    private String autorNome;

    // relacionamento categoria
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    // Campo redundante para desnormalização (usado no BD)
    @Column(name = "categoria_nome", nullable = true)
    private String categoriaNome;

    // relacionamento editora
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "editora_id", nullable = false)
    private Editora editora;

    // Campo redundante para desnormalização (usado no BD)
    @Column(name = "editora_nome", nullable = true)
    private String editoraNome;
}
