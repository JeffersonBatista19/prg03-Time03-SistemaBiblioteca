package br.com.ifba.biblioteca.cliente.entity;

import br.com.ifba.biblioteca.pessoa.entity.Pessoa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author misae
 */

//Entidade que representa um Cliente do sistema.
//Herda os dados básicos de Pessoa e adiciona informações específicas do perfil de cliente.
@Entity
@Table(name = "cliente")
@Getter
@Setter
public class Cliente extends Pessoa{

    // aqui define a quantidade máxima de empréstimos permitidos ao cliente
    @Column(name = "limite_emprestimo", nullable = false)
    private Integer limiteEmprestimo;
    
    // define o tipo do cliente (ALUNO, PROFESSOR, VISITANTE)
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cliente", nullable = false)
    private TipoCliente tipoCliente;
}
