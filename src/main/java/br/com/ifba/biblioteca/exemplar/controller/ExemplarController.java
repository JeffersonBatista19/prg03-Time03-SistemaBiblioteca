package br.com.ifba.biblioteca.exemplar.controller;

import br.com.ifba.biblioteca.exemplar.entity.Exemplar;
import br.com.ifba.biblioteca.exemplar.repository.ExemplarRepository;
import br.com.ifba.biblioteca.exemplar.service.ExemplarIService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExemplarController implements ExemplarIController {

    // aqui tem as regras de negócio, só que sem acoplamento.
    @Autowired
    private ExemplarIService service;
    
    @Autowired
    private ExemplarRepository exemplarRepository;

    @Override
    public Exemplar save(Exemplar exemplar) {
        return service.save(exemplar);
    }

    @Override
    public Exemplar update(Exemplar exemplar) {
        return service.update(exemplar);
    }

    @Override
    public void delete(Exemplar exemplar) {
        service.delete(exemplar);
    }

    @Override
    public List<Exemplar> findAll() {
        return service.findAll();
    }

    @Override
    public Exemplar findById(Long id) {
        return service.findById(id);
    }

    @Override
    public Exemplar findByNumeroTombamento(int numeroTombamento) {
        return service.findByNumeroTombamento(numeroTombamento);
    }
    
    @Override
    public boolean existsByNumeroTombamento(int tombamento) {
    return service.existsByNumeroTombamento(tombamento);
    }
}




