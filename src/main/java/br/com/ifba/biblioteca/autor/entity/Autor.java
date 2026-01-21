/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.biblioteca.autor.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 * @author guilherme
 */


//cria a tabela de autores

@Entity
@Table(name = "autores")
@Data 
@AllArgsConstructor
@NoArgsConstructor
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @Column(name = "nacionalidade")
    private String nacionalidade;

    @Column(name = "ano_nascimento")
    private Integer anoNascimento;

    @Column(name = "ano_falecimento")
    private Integer anoFalecimento;

    @Column(name = "biografia", columnDefinition = "TEXT")
    private String biografia;

    // --- RELACIONAMENTO COM LIVROS ---
    // Quand oarrumar a classe Livro, descomentar essas 2 linhas:
    /*
    @OneToMany(mappedBy = "autor", fetch = FetchType.LAZY)
    private List<Livro> obras;
    */

    // --- MÉTODOS AUXILIARES ---

    // Esse método é necessário no Service (para não deixar excluir autor com livros)
    // Por enquanto retorna 0 para permitir que o código compile.
    @Transient
    public int getQuantidadeObras() {
        // TODO: Quando descomentar a lista 'obras' acima, mudar para:
        // return (obras != null) ? obras.size() : 0;
        
        return 0; // Placeholder temporário
    }
}
