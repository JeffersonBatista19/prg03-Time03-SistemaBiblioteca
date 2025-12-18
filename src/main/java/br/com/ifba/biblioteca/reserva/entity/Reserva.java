package br.com.ifba.biblioteca.reserva.entity;

import br.com.ifba.biblioteca.exemplar.entity.Exemplar;
import br.com.ifba.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reservas")
public class Reserva extends PersistenceEntity {

    @Column(name = "data_reserva", nullable = false)
    private LocalDate dataReserva;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusReserva status;

    // Cliente que realizou a reserva
    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    // Exemplar reservado
    @ManyToOne
    @JoinColumn(name = "exemplar_id", nullable = false)
    private Exemplar exemplar;
    
}
