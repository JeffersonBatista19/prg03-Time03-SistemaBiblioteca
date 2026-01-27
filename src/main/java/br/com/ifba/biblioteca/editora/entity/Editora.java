package br.com.ifba.biblioteca.editora.entity;

import br.com.ifba.infrastructure.entity.PersistenceEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author misae
 */

// entidade que representa uma Editora do sistema
@Entity
@Table(name = "editora")
@Getter
@Setter
public class Editora extends PersistenceEntity{
    
    // CNPJ da editora, que é identificador único nesse domínio
    @Column(name = "cnpj", nullable = false)
    private String cnpj;
    
    @Column(name = "nome", nullable = false)
    private String nome;
    
    @Column(name = "telefone", nullable = false)
    private String telefone;
    
    @Column(name = "endereco", nullable = false)
    private String endereco;
    
    // Indica se a editora está ativa no sistema, necessaria na parte de inativação logica
    @Column(name = "situacao", nullable = false)
    private String situacao = "ATIVO";


}
