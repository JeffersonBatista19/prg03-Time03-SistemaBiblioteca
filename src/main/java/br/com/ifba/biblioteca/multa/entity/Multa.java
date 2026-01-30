package br.com.ifba.biblioteca.multa.entity;

import br.com.ifba.biblioteca.emprestimo.entity.Emprestimo;
import br.com.ifba.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "multas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Multa extends PersistenceEntity {

    // Relação com empréstimo.
    @OneToOne
    @JoinColumn(name = "emprestimo_id", nullable = false, unique = true)
    private Emprestimo emprestimo;

    @Column(name = "dias_atraso", nullable = false)
    private Integer diasAtraso;

    @Column(name = "valor_por_dia", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorPorDia;

    @Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @Column(name = "data_geracao", nullable = false)
    private LocalDate dataGeracao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_multa", nullable = false)
    private StatusMulta status;
}
