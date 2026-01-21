package br.com.ifba.infrastructure.entity;

import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass

public class PersistenceEntity {
    @Id //marca o campo como chave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)//gera automatico o ID pelo banco de dados.
    private Long id;
}
