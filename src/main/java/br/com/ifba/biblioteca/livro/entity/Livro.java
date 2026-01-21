package br.com.ifba.biblioteca.livro.entity;

import br.com.ifba.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Long autorNome;

    @Column(name = "categoria_nome", nullable = false)
    private Long categoriaNome;

    @Column(name = "editora_nome", nullable = false)
    private Long editoraNome;
}
