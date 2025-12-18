package br.com.ifba.biblioteca.exemplar.entity;

import br.com.ifba.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "exemplar")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Exemplar extends PersistenceEntity {

    @Column(nullable = false, unique = true)
    private int numeroTombamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_conservacao", nullable = false)
    private EstadoConservacao conservacao;


    @Column(nullable = false)
    private String localizacaoFisica;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusExemplar status;
}

