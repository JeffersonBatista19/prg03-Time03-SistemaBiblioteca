package br.com.ifba.biblioteca.exemplar.service;

import br.com.ifba.biblioteca.exemplar.entity.Exemplar;
import br.com.ifba.biblioteca.exemplar.entity.StatusExemplar;
import br.com.ifba.biblioteca.exemplar.repository.ExemplarRepository;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExemplarService implements ExemplarIService {

    private final ExemplarRepository repository;

    public ExemplarService(ExemplarRepository repository) {
        this.repository = repository;
    }

    @Override
    public Exemplar save(Exemplar exemplar) {

        Exemplar existente =
                repository.findByNumeroTombamento(exemplar.getNumeroTombamento());

        if (existente != null) {
            throw new RuntimeException("Já existe um exemplar com este número de tombamento.");
        }

        exemplar.setStatus(StatusExemplar.DISPONIVEL);
        Exemplar salvo = repository.save(exemplar);

        log.info("Exemplar salvo com sucesso. Tombamento: {}", exemplar.getNumeroTombamento());

        return salvo;
    }

    @Override
    public Exemplar update(Exemplar exemplar) {

        Exemplar existente = repository.findById(exemplar.getId()).orElse(null);

        if (existente == null) {
            throw new RuntimeException("Exemplar não encontrado.");
        }

        existente.setNumeroTombamento(exemplar.getNumeroTombamento());
        existente.setConservacao(exemplar.getConservacao());
        existente.setLocalizacaoFisica(exemplar.getLocalizacaoFisica());
        existente.setStatus(exemplar.getStatus());

        Exemplar atualizado = repository.save(existente);

        log.info("Exemplar atualizado com sucesso. ID: {}", existente.getId());

        return atualizado;
    }

    @Override
    public void delete(Exemplar exemplar) {

        Exemplar existente = repository.findById(exemplar.getId()).orElse(null);

        if (existente == null) {
            throw new RuntimeException("Exemplar não encontrado.");
        }

        if (existente.getStatus() == StatusExemplar.EMPRESTADO ||
            existente.getStatus() == StatusExemplar.RESERVADO) {
            throw new RuntimeException("Exemplar não pode ser removido.");
        }

        repository.delete(existente);

        log.info("Exemplar deletado com sucesso. ID: {}", existente.getId());
    }

    @Override
    public List<Exemplar> findAll() {
        return repository.findAll();
    }

    @Override
    public Exemplar findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Exemplar findByNumeroTombamento(int numeroTombamento) {
        return repository.findByNumeroTombamento(numeroTombamento);
    }
}
