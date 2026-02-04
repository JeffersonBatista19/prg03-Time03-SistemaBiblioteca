package br.com.ifba.biblioteca.emprestimo.service;

/**
 *
 * @author guilhermeAmedrado
 */

import br.com.ifba.biblioteca.cliente.entity.Cliente;
import br.com.ifba.biblioteca.emprestimo.entity.Emprestimo;
import br.com.ifba.biblioteca.emprestimo.repository.EmprestimoRepository;
import br.com.ifba.biblioteca.exemplar.entity.Exemplar;
import br.com.ifba.biblioteca.exemplar.entity.StatusExemplar;
import br.com.ifba.biblioteca.exemplar.repository.ExemplarRepository;
import br.com.ifba.biblioteca.pessoa.entity.StatusPessoa;
import br.com.ifba.biblioteca.cliente.repository.ClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class EmprestimoService implements EmprestimoIService {

    private final EmprestimoRepository repository;
    private final ClienteRepository clienteRepository;
    private final ExemplarRepository exemplarRepository;

    private final br.com.ifba.biblioteca.reserva.repository.ReservaRepository reservaRepository;

    public EmprestimoService(EmprestimoRepository repository, ClienteRepository clienteRepository, ExemplarRepository exemplarRepository, br.com.ifba.biblioteca.reserva.repository.ReservaRepository reservaRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
        this.exemplarRepository = exemplarRepository;
        this.reservaRepository = reservaRepository;
    }

    @Override
    @Transactional
    public Emprestimo save(Emprestimo emprestimo) {
        //  Busca Cliente e Exemplar
        Cliente cliente = clienteRepository.findById(emprestimo.getCliente().getId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado."));

        Exemplar exemplar = exemplarRepository.findById(emprestimo.getExemplar().getId())
                .orElseThrow(() -> new RuntimeException("Exemplar não encontrado."));

        // Validação CRÍTICA: Verifica se o exemplar está disponível
        if (exemplar.getStatus() != StatusExemplar.DISPONIVEL) {
            throw new RuntimeException("Empréstimo negado: Este exemplar não está disponível. Status atual: " + exemplar.getStatus());
        }

        // Validações do ENUM
        if (cliente.getStatusPessoa() != StatusPessoa.ATIVO) {
            throw new RuntimeException("Empréstimo negado: Usuário inativo ou bloqueado.");
        }

        //Cliente possui livros atrasados?
        if (repository.countLateLoansByCliente(cliente) > 0) {
            throw new RuntimeException("Empréstimo negado: O cliente possui livros com devolução atrasada.");
        }

        //Cliente possui multas pendentes (STATUS = PENDENTE)?
        if (repository.countPendingFinesByCliente(cliente) > 0) {
            throw new RuntimeException("Empréstimo negado: O cliente possui multas pendentes no sistema.");
        }

        // Verifica limite de livros (exemplo: 3 livros)
        if (repository.countByClienteAndStatusAtivo(cliente) >= 3) {
             throw new RuntimeException("Limite de empréstimos excedido.");
        }

        // Busca reservas pendentes para este exemplar
        List<br.com.ifba.biblioteca.reserva.entity.Reserva> reservas = 
            reservaRepository.findByExemplarAndStatus(exemplar, br.com.ifba.biblioteca.reserva.entity.StatusReserva.PENDENTE);

        for (br.com.ifba.biblioteca.reserva.entity.Reserva reserva : reservas) {
            if (!reserva.getCliente().getId().equals(cliente.getId())) {
                // Se o exemplar está reservado para OUTRA pessoa
                throw new RuntimeException("Empréstimo bloqueado: Este exemplar está reservado para o cliente " + reserva.getCliente().getNomeCompleto());
            } else {
                // Se é a reserva DESTE cliente, atualizamos para ATENDIDA
                reserva.setStatus(br.com.ifba.biblioteca.reserva.entity.StatusReserva.ATENDIDA);
                reservaRepository.save(reserva);
            }
        }

        // Regra de Data (ALUNO = 14, PROFESSOR = 21)
        int dias = 14; 
        if (cliente.getTipoCliente() == br.com.ifba.biblioteca.cliente.entity.TipoCliente.PROFESSOR) {
            dias = 21;
        }

        // Montagem
        emprestimo.setCliente(cliente);
        // emprestimo.setIdUsuarioRedundante(null); // Agora é nullable para evitar conflito de FK
        emprestimo.setExemplar(exemplar);
        emprestimo.setDataEmprestimo(LocalDate.now());
        LocalDate dataPrevista = LocalDate.now().plusDays(dias);
        emprestimo.setDataPrevistaDevolucao(dataPrevista);
        emprestimo.setDataPrevistaDevolucaoRedundante(dataPrevista); // Satisfaz redundância do BD
        emprestimo.setStatus(Emprestimo.StatusEmprestimo.ATIVO);

        // CRÍTICO: Atualiza o status do exemplar para EMPRESTADO
        exemplar.setStatus(StatusExemplar.EMPRESTADO);
        exemplarRepository.save(exemplar);

        return repository.save(emprestimo);
    }

    //Os métodos update, delete, findAll e findById
    @Override
    public Emprestimo update(Emprestimo emprestimo) {
        Emprestimo existente = findById(emprestimo.getId());
        
        // Validação: data de devolução não pode ser anterior à data do empréstimo
        if (emprestimo.getDataPrevistaDevolucao() != null && 
            emprestimo.getDataPrevistaDevolucao().isBefore(existente.getDataEmprestimo())) {
            throw new RuntimeException("Erro: A data de devolução não pode ser anterior à data do empréstimo (" + resistente_data_formatada(existente.getDataEmprestimo()) + ").");
        }
        
        existente.setDataPrevistaDevolucao(emprestimo.getDataPrevistaDevolucao());
        existente.setDataPrevistaDevolucaoRedundante(emprestimo.getDataPrevistaDevolucao()); // Mantém redundância
        existente.setStatus(emprestimo.getStatus());
        
        return repository.save(existente);
    }

    private String resistente_data_formatada(LocalDate date) {
        if (date == null) return "N/A";
        return date.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    @Override
    public void delete(Emprestimo emprestimo) {
        Emprestimo existente = findById(emprestimo.getId());
        if (existente.getStatus() == Emprestimo.StatusEmprestimo.ATIVO) {
            Exemplar ex = existente.getExemplar();
            ex.setStatus(StatusExemplar.DISPONIVEL);
            exemplarRepository.save(ex);
        }
        repository.delete(existente);
    }

    @Override
    public List<Emprestimo> findAll() {
        return repository.findAll();
    }

    @Override
    public Emprestimo findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado."));
    }
}
