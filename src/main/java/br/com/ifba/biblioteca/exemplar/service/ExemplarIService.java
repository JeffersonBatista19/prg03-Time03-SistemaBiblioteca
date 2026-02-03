package br.com.ifba.biblioteca.exemplar.service;

import br.com.ifba.biblioteca.exemplar.entity.Exemplar;
import java.util.List;

public interface ExemplarIService {

    Exemplar save(Exemplar exemplar);

    Exemplar update(Exemplar exemplar);

    void delete(Exemplar exemplar);

    List<Exemplar> findAll();

    Exemplar findById(Long id);

    Exemplar findByNumeroTombamento(int numeroTombamento);
     
    boolean existsByNumeroTombamento(int tombamento);
     
}
