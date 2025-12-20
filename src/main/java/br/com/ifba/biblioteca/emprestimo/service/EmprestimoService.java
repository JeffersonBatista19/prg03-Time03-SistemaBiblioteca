package br.com.ifba.biblioteca.emprestimo.service;

/**
 *
 * @author guilhermeAmedrado
 */

import br.com.ifba.biblioteca.emprestimo.entity.Emprestimo;
import br.com.ifba.biblioteca.emprestimo.repository.EmprestimoRepository;
import br.com.ifba.biblioteca.exemplar.entity.Exemplar;
import br.com.ifba.biblioteca.exemplar.entity.StatusExemplar;
import br.com.ifba.biblioteca.exemplar.repository.ExemplarRepository;
import br.com.ifba.biblioteca.pessoa.entity.Cliente; // <--- CLASSE NOVA
import br.com.ifba.biblioteca.pessoa.entity.ClienteRepository;
import br.com.ifba.biblioteca.pessoa.entity.StatusPessoa;
import br.com.ifba.biblioteca.pessoa.entity.TipoPerfil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class EmprestimoService implements EmprestimoIService {

    private final EmprestimoRepository repository;
    private final ClienteRepository clienteRepository; // Mudou de Usuario para Cliente
    private final ExemplarRepository exemplarRepository;

    public EmprestimoService(EmprestimoRepository repository,
                             ClienteRepository clienteRepository,
                             ExemplarRepository exemplarRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
        this.exemplarRepository = exemplarRepository;
    }

    @Override
    @Transactional
    public Emprestimo save(Emprestimo emprestimo) {
        // 1. Busca CLIENTE e Exemplar
        // O getId() aqui depende de como o Controller monta o objeto.
        // Se der erro de NullPointerException, verifique se emprestimo.getCliente() não é nulo.
        Cliente cliente = clienteRepository.findById(emprestimo.getCliente().getId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado."));

        Exemplar exemplar = exemplarRepository.findById(emprestimo.getExemplar().getId())
                .orElseThrow(() -> new RuntimeException("Exemplar não encontrado."));

        // 2. Validações
        if (cliente.getStatusPessoa() != StatusPessoa.ATIVO) {
            throw new RuntimeException("Empréstimo negado: Cliente inativo.");
        }
        
        if (exemplar.getStatus() != StatusExemplar.DISPONIVEL) {
            throw new RuntimeException("Empréstimo negado: Exemplar não está disponível.");
        }

        if (repository.countByClienteAndStatusAtivo(cliente) >= 3) {
            throw new RuntimeException("Limite de 3 livros excedido.");
        }

        // 3. Regra de Data
        int dias = (cliente.getTipoPerfil() == TipoPerfil.PROFESSOR) ? 21 : 14;

        emprestimo.setCliente(cliente); // Setando Cliente
        emprestimo.setExemplar(exemplar);
        emprestimo.setDataEmprestimo(LocalDate.now());
        emprestimo.setDataDevolucaoPrevista(LocalDate.now().plusDays(dias));
        emprestimo.setStatus(Emprestimo.StatusEmprestimo.ATIVO);

        // 4. Atualiza Exemplar
        exemplar.setStatus(StatusExemplar.EMPRESTADO);
        exemplarRepository.save(exemplar);

        Emprestimo salvo = repository.save(emprestimo);
        log.info("Empréstimo salvo. ID: {}, Cliente: {}", salvo.getId(), cliente.getNomeCompleto());
        
        return salvo;
    }

    @Override
    public Emprestimo update(Emprestimo emprestimo) {
        Emprestimo existente = findById(emprestimo.getId());
        existente.setDataDevolucaoPrevista(emprestimo.getDataDevolucaoPrevista());
        existente.setStatus(emprestimo.getStatus());
        
        Emprestimo atualizado = repository.save(existente);
        log.info("Empréstimo atualizado. ID: {}", atualizado.getId());
        return atualizado;
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
        log.info("Empréstimo deletado. ID: {}", existente.getId());
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