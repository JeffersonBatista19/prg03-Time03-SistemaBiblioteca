package br.com.ifba.biblioteca.cliente.entity;

/**
 *
 * @author misae
 */

//esse Enum define o tipo de cliente da biblioteca, utilizado para aplicar regras específicas como
//limite de empréstimos e prazos.
public enum TipoCliente {
    ALUNO,
    PROFESSOR,
    VISITANTE
}
