package br.com.ifba.biblioteca.reserva.entity;


//classe Cliente utilizada de forma PROVISÓRIA

// OBS: Esta classe NÃO é uma entidade JPA. Ela foi criada apenas para permitir o teste e funcionamento
//da funcionalidade de Reserva, já que o módulo de Cliente ainda não foi implementado por outro integrante do grupo.
public class Cliente {

    private Long id;
    private String nome;
    private String cpf;
    private String telefone;

    public Cliente() {}

    public Cliente(Long id, String nome, String cpf, String telefone) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }
}
