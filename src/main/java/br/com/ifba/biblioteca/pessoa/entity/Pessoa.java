package br.com.ifba.biblioteca.pessoa.entity;

import br.com.ifba.infrastructure.entity.PersistenceEntity;

import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

// Classe base abstrata para entidades do tipo Pessoa.
//Contém atributos comuns a Cliente e Usuario.
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Pessoa extends PersistenceEntity {

    @Column(name = "nome_completo", nullable = false)
    private String nomeCompleto;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(name = "data_cadastro", nullable = false)
    private LocalDate dataCadastro;

    @Column(nullable = false)
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_acesso", nullable = false)
    private NivelAcesso nivelAcesso;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_perfil", nullable = false)
    private TipoPerfil tipoPerfil;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pessoa", nullable = false)
    private StatusPessoa statusPessoa;
}
