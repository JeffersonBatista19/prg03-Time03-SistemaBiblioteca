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

//Classe responsável pelas regras de negócio da entidade Reserva.

// Habilita logs automáticos usando SLF4J
@Slf4j
// Define esta classe como um Service gerenciado pelo Spring
@Service
public class ReservaService implements ReservaIService {

    // Repositório responsável pelo acesso aos dados de Reserva
    private final ReservaRepository repository;

    //Construtor com injeção de dependência do Repository. O Spring injeta automaticamente a implementação
    public ReservaService(ReservaRepository repository) {
        this.repository = repository;
    }

    //Cria uma nova reserva. Aplica as regras de negócio antes de salvar no banco.
    @Override
    public Reserva save(Reserva reserva) {

        log.info("Iniciando processo de criação de reserva: {}", reserva);

        // Validação: reserva não pode ser nula
        if (reserva == null) {
            log.error("Tentativa de salvar reserva nula.");
            throw new RuntimeException("Reserva não pode ser nula!");
        }

        // Validação: reserva deve possuir cliente
        if (reserva.getClienteId() == null) {
            log.warn("Reserva sem cliente.");
            throw new RuntimeException("Reserva deve possuir um cliente!");
        }

        // Validação: reserva deve possuir exemplar
        if (reserva.getExemplar() == null) {
            log.warn("Reserva sem exemplar.");
            throw new RuntimeException("Reserva deve possuir um exemplar!");
        }

        // Define automaticamente a data da reserva
        reserva.setDataReserva(LocalDate.now());
        
        // Define o status inicial como PENDENTE
        reserva.setStatus(StatusReserva.PENDENTE);

        log.info("Reserva criada com sucesso!");
        
        // Persiste a reserva no banco de dados
        return repository.save(reserva);
    }

    
    // Atender reserva existente. Altera o status da reserva para ATENDIDA.
    @Override
    public Reserva update(Reserva reserva) {

        log.info("Iniciando atendimento da reserva: {}", reserva);

        // Validação, q a reserva não pode ser nula
        if (reserva == null) {
            log.error("Tentativa de atualizar reserva nula.");
            throw new RuntimeException("Reserva não pode ser nula!");
        }

        // Validação, q o ID da reserva é obrigatório
        if (reserva.getId() == null) {
            log.error("ID ausente ao tentar atender reserva.");
            throw new RuntimeException("ID da reserva é obrigatório!");
        }

        // Busca a reserva no banco pelo ID
        Reserva reservaExistente = repository.findById(reserva.getId())
                .orElseThrow(() -> {
                    log.warn("Reserva não encontrada para atendimento. ID: {}", reserva.getId());
                    return new RuntimeException("Reserva não encontrada!");
                });

         //Regra de negócio: reserva já atendida não pode ser atendida novamente
        if (reserva.getStatus() == StatusReserva.ATENDIDA) {
             throw new RuntimeException("Esta reserva já foi atendida.");
         }
        
        // Regra de negócio: reserva cancelada não pode ser atendida
        if (reserva.getStatus() == StatusReserva.CANCELADA) {
            throw new RuntimeException("Esta reserva está cancelada e não pode ser atendida.");
        }
        
        //// Atualiza o status para ATENDIDA
        reservaExistente.setStatus(StatusReserva.ATENDIDA);

        log.info("Reserva atendida com sucesso!");
        return repository.save(reservaExistente); //salva no banco
    }

    
    // Cancelar reserva (delete lógico), ou seja, O cancelamento é feito através de alteração de status
    @Override
    public void delete(Reserva reserva) {

        log.info("Iniciando cancelamento da reserva: {}", reserva);

        //reserva não pode ser nula
        if (reserva == null) {
            log.error("Tentativa de cancelar reserva nula.");
            throw new RuntimeException("Reserva não pode ser nula!");
        }

        //ID da reserva é obrigatório
        if (reserva.getId() == null) {
            log.error("ID ausente ao tentar cancelar reserva.");
            throw new RuntimeException("ID da reserva é obrigatório!");
        }

        // Busca a reserva no banco pelo ID
        Reserva reservaExistente = repository.findById(reserva.getId())
                .orElseThrow(() -> {
                    log.warn("Reserva não encontrada para cancelamento. ID: {}", reserva.getId());
                    return new RuntimeException("Reserva não encontrada!");
                });

        // Atualiza o status para CANCELADA
        reservaExistente.setStatus(StatusReserva.CANCELADA);
        repository.save(reservaExistente);

        log.info("Reserva cancelada com sucesso!");
    }

    //Retorna todas as reservas cadastradas.
    @Override
    public List<Reserva> findAll() {

        log.info("Listando todas as reservas.");
        return repository.findAll();
    }

    // Busca uma reserva pelo ID.
    @Override
    public Reserva findById(Long id) {

        log.info("Buscando reserva por ID: {}", id);

        
        //faz a validação, ID não pode ser nulo
        if (id == null) {
            log.error("ID nulo ao buscar reserva.");
            throw new RuntimeException("ID não pode ser nulo!");
        }

        //Retorna a reserva se existir
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Reserva não encontrada. ID: {}", id);
                    return new RuntimeException("Reserva não encontrada!");
                });
    }
}
