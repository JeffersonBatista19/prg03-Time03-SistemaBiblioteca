package br.com.ifba.biblioteca.evento.service;

/**
 *
 * @author guilhermeAmedrado
 */

import br.com.ifba.biblioteca.evento.entity.Evento;
import br.com.ifba.biblioteca.evento.entity.StatusEvento;
import br.com.ifba.biblioteca.evento.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventoService implements EventoIService {

    @Autowired
    private EventoRepository repository;

    @Override
    public List<Evento> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Evento> findByTitulo(String titulo) {
        return repository.findByTituloContainingIgnoreCase(titulo);
    }
    
    public List<Evento> findByStatus(StatusEvento status) {
        return repository.findByStatus(status);
    }

    public List<Evento> findByStatusNot(StatusEvento status) {
        return repository.findByStatusNot(status);
    }

    @Override
    public Evento findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Evento save(Evento evento) {
        
        // Validação: O título não pode ser vazio
        if (evento.getTitulo() == null || evento.getTitulo().trim().isEmpty()) {
            throw new RuntimeException("Preencha todos os campos obrigatórios: O título do evento é obrigatório.");
        }

        // Validação: O evento deve ser marcado para uma data futura
        if (evento.getDataHorario() == null || evento.getDataHorario().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("A data do evento deve ser no futuro.");
        }

        // Validação: O local é obrigatório para cadastrar o evento
        if (evento.getLocalEntity() == null) {
            throw new RuntimeException("Preencha todos os campos obrigatórios: O local do evento deve ser informado.");
        }

        // Validação: Deve haver pelo menos uma vaga
        if (evento.getLimiteVagas() == null || evento.getLimiteVagas() <= 0) {
            throw new RuntimeException("Preencha todos os campos obrigatórios: O limite de vagas deve ser maior que zero.");
        }

        // Configurações padrão para novos eventos (ID nulo)
        if (evento.getId() == null) {
            evento.setStatus(StatusEvento.ATIVO);
            evento.setVagasPreenchidas(0);
        }
        
        // Mantém o nome do local na coluna antiga para não quebrar o banco de dados legado
        if (evento.getLocalEntity() != null) {
            evento.setLocalLegado(evento.getLocalEntity().getNome());
        }
        
        return repository.save(evento);
    }

    @Override
    public void cancelar(Long id) {
        Evento evento = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado."));

        // Regra: Só pode cancelar eventos que ainda não aconteceram
        if (evento.getDataHorario().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Não é possível cancelar um evento que já foi realizado.");
        }

        // Regra: Não permite cancelar um evento que já está cancelado
        if (evento.getStatus() == StatusEvento.CANCELADO) {
            throw new RuntimeException("Este evento já se encontra cancelado.");
        }

        if (evento.getDataHorario().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Não é possível cancelar um evento que já foi realizado.");
        }

        evento.setStatus(StatusEvento.CANCELADO);
        repository.save(evento);
    }

    @Override
    public Evento update(Evento evento) {
        if (evento.getId() == null) {
            throw new RuntimeException("ID do evento é necessário para atualização.");
        }

        Evento eventoExistente = repository.findById(evento.getId())
                .orElseThrow(() -> new RuntimeException("Nenhum evento encontrado para atualização."));

        // Regra: Não permite trocar o título se já houver pessoas inscritas
        if (eventoExistente.getVagasPreenchidas() > 0 && !eventoExistente.getTitulo().equals(evento.getTitulo())) {
            throw new RuntimeException("Alteração não permitida: O título não pode ser alterado após o início das inscrições.");
        }

        // Validação de campos obrigatórios no fluxo de edição
        if (evento.getTitulo() == null || evento.getTitulo().trim().isEmpty() ||
            evento.getLocalEntity() == null ||
            evento.getDataHorario() == null || evento.getLimiteVagas() == null) {
            throw new RuntimeException("Preencha todos os campos obrigatórios.");
        }

        // Regra: Não permite editar eventos que já aconteceram
        if (eventoExistente.getDataHorario().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Não é possível alterar um evento que já foi realizado.");
        }

        // Sincroniza com a coluna legada do banco para evitar erro de NOT NULL
        if (evento.getLocalEntity() != null) {
            evento.setLocalLegado(evento.getLocalEntity().getNome());
        }

        return repository.save(evento);
    }
}