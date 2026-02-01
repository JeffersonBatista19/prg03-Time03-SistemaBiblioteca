package br.com.ifba.biblioteca.reserva.repository;

import br.com.ifba.biblioteca.reserva.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Utiliza o Spring Data JPA para fornecer automaticamente as operações básicas de persistência (CRUD),
//sem necessidade de implementação manual.
@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    // Busca reservas de um exemplar específico com um status específico (ex: PENDENTE)
    java.util.List<Reserva> findByExemplarAndStatus(br.com.ifba.biblioteca.exemplar.entity.Exemplar exemplar, br.com.ifba.biblioteca.reserva.entity.StatusReserva status);
}