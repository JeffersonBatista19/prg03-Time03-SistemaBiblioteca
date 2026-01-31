package br.com.ifba.biblioteca.livro.entity;

import br.com.ifba.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import br.com.ifba.biblioteca.editora.entity.Editora;


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


    // categoria SEM relacionamento (não existe classe)
    @Column(name = "categoria_id", nullable = false)
    private Long categoriaId;
    
    @Column(name = "categoria_nome", nullable = false)
    private String categoriaNome;

    // editora relacionamento
    @ManyToOne
    @JoinColumn(name = "editora_id", nullable = false)
    private Editora editora;
    
    @Column(name = "editora_nome", nullable = false)
    private String editoraNome;

}
