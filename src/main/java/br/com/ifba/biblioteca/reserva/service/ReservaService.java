package br.com.ifba.biblioteca.reserva.service;

import br.com.ifba.biblioteca.reserva.entity.Reserva;
import br.com.ifba.biblioteca.reserva.entity.StatusReserva;
import br.com.ifba.biblioteca.reserva.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author misae
 */

// Aqui ficam as regras de negócio da Reserva
@Slf4j
@Service
public class ReservaService implements ReservaIService {

    private final ReservaRepository repository;

    public ReservaService(ReservaRepository repository) {
        this.repository = repository;
    }

    // ================== SAVE ==================
    @Override
    public Reserva save(Reserva reserva) {

        log.info("Iniciando processo de criação de reserva: {}", reserva);

        if (reserva == null) {
            log.error("Tentativa de salvar reserva nula.");
            throw new RuntimeException("Reserva não pode ser nula!");
        }

        if (reserva.getClienteId() == null) {
            log.warn("Reserva sem cliente.");
            throw new RuntimeException("Reserva deve possuir um cliente!");
        }

        if (reserva.getExemplar() == null) {
            log.warn("Reserva sem exemplar.");
            throw new RuntimeException("Reserva deve possuir um exemplar!");
        }

        reserva.setDataReserva(LocalDate.now());
        reserva.setStatus(StatusReserva.PENDENTE);

        log.info("Reserva criada com sucesso!");
        return repository.save(reserva);
    }

    // ================== UPDATE ==================
    // Atender reserva
    @Override
    public Reserva update(Reserva reserva) {

        log.info("Iniciando atendimento da reserva: {}", reserva);

        if (reserva == null) {
            log.error("Tentativa de atualizar reserva nula.");
            throw new RuntimeException("Reserva não pode ser nula!");
        }

        if (reserva.getId() == null) {
            log.error("ID ausente ao tentar atender reserva.");
            throw new RuntimeException("ID da reserva é obrigatório!");
        }

        Reserva reservaExistente = repository.findById(reserva.getId())
                .orElseThrow(() -> {
                    log.warn("Reserva não encontrada para atendimento. ID: {}", reserva.getId());
                    return new RuntimeException("Reserva não encontrada!");
                });

         // Regra de negócio: só reservas ATIVAS podem ser atendidas
        if (reserva.getStatus() == StatusReserva.ATENDIDA) {
             throw new RuntimeException("Esta reserva já foi atendida.");
         }
        
        if (reserva.getStatus() == StatusReserva.CANCELADA) {
            throw new RuntimeException("Esta reserva está cancelada e não pode ser atendida.");
        }
        
        //// Atualiza o status para ATENDIDA
        reservaExistente.setStatus(StatusReserva.ATENDIDA);

        log.info("Reserva atendida com sucesso!");
        return repository.save(reservaExistente); //salva no banco
    }

    // ================== DELETE ==================
    // Cancelar reserva (delete lógico)
    @Override
    public void delete(Reserva reserva) {

        log.info("Iniciando cancelamento da reserva: {}", reserva);

        if (reserva == null) {
            log.error("Tentativa de cancelar reserva nula.");
            throw new RuntimeException("Reserva não pode ser nula!");
        }

        if (reserva.getId() == null) {
            log.error("ID ausente ao tentar cancelar reserva.");
            throw new RuntimeException("ID da reserva é obrigatório!");
        }

        Reserva reservaExistente = repository.findById(reserva.getId())
                .orElseThrow(() -> {
                    log.warn("Reserva não encontrada para cancelamento. ID: {}", reserva.getId());
                    return new RuntimeException("Reserva não encontrada!");
                });

        reservaExistente.setStatus(StatusReserva.CANCELADA);
        repository.save(reservaExistente);

        log.info("Reserva cancelada com sucesso!");
    }

    // ================== FIND ALL ==================
    @Override
    public List<Reserva> findAll() {

        log.info("Listando todas as reservas.");
        return repository.findAll();
    }

    // ================== FIND BY ID ==================
    @Override
    public Reserva findById(Long id) {

        log.info("Buscando reserva por ID: {}", id);

        if (id == null) {
            log.error("ID nulo ao buscar reserva.");
            throw new RuntimeException("ID não pode ser nulo!");
        }

        return repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Reserva não encontrada. ID: {}", id);
                    return new RuntimeException("Reserva não encontrada!");
                });
    }
}
