package br.com.ifba.biblioteca.emprestimo.entity;

import br.com.ifba.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "multa_fake")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Multafake extends PersistenceEntity {

    @Column(name = "valor_total")
    private float valorTotal;

    @Column(name = "data_devolucao")
    private LocalDate dataDevolucao;

    @Column(name = "dias_atraso")
    private int diasAtraso;
}
