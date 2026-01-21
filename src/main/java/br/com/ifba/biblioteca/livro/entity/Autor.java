package br.com.ifba.biblioteca.livro.entity;

import br.com.ifba.infrastructure.entity.PersistenceEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Autor extends PersistenceEntity {

    private String nome;
    private String nacionalidade;
    private String biografia;
    private int qtdObrasCadastradas;
}
