package br.com.ifba.biblioteca.local.entity;

import br.com.ifba.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "local")
@Getter
@Setter
public class Local extends PersistenceEntity {

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String rua;

    @Column(nullable = false)
    private Integer numero;

    @Column
    private String complemento;

    @Column(nullable = false)
    private Boolean ativo = true;
}

