package br.com.ifba.biblioteca.exemplar.repository;

import br.com.ifba.biblioteca.exemplar.entity.Exemplar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExemplarRepository extends JpaRepository<Exemplar, Long> {

    Exemplar findByNumeroTombamento(int numeroTombamento);
    boolean existsByNumeroTombamento(int numeroTombamento);
}

