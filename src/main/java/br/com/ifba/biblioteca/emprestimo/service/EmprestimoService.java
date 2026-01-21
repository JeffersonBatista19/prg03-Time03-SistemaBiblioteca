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

import br.com.ifba.biblioteca.pessoa.entity.Cliente; 
import br.com.ifba.biblioteca.pessoa.entity.ClienteRepository;
import br.com.ifba.biblioteca.pessoa.entity.StatusPessoa; // ATIVO, INATIVO...
import br.com.ifba.biblioteca.pessoa.entity.TipoPerfil; // CLIENTE, BIBLIOTECARIO, ADMINISTRADOR

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

    public EmprestimoService(EmprestimoRepository repository,ClienteRepository clienteRepository, ExemplarRepository exemplarRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
        this.exemplarRepository = exemplarRepository;
    }

    @Override
    @Transactional
    public Emprestimo save(Emprestimo emprestimo) {
        // 1. Busca CLIENTE e Exemplar
        Cliente cliente = clienteRepository.findById(emprestimo.getCliente().getId()).orElseThrow(() -> new RuntimeException("Cliente não encontrado."));

        Exemplar exemplar = exemplarRepository.findById(emprestimo.getExemplar().getId()).orElseThrow(() -> new RuntimeException("Exemplar não encontrado."));

        // 2. Validações
        
        // Valida se a pessoa está INATIVA (Independente do perfil)
        if (cliente.getStatusPessoa() != StatusPessoa.ATIVO) {
            throw new RuntimeException("Empréstimo negado: Cliente inativo.");
        }
        
        // Valida disponibilidade do livro
        if (exemplar.getStatus() != StatusExemplar.DISPONIVEL) {
            throw new RuntimeException("Empréstimo negado: Exemplar não está disponível.");
        }

        // Valida limite de livros (Apenas para CLIENTES comuns, talvez?)
        // Se for CLIENTE, checa o limite. Se for ADM, talvez possa pegar mais.
        if (cliente.getTipoPerfil() == TipoPerfil.CLIENTE) {
             if (repository.countByClienteAndStatusAtivo(cliente) >= 3) {
                throw new RuntimeException("Limite de 3 livros excedido para clientes.");
            }
        }

        // 3. Regra de Data 
        // Lógica: Se for CLIENTE = 14 dias. Se for BIBLIOTECARIO ou ADM = 21 dias.
        int dias = 14; 
        
        if (cliente.getTipoPerfil() == TipoPerfil.BIBLIOTECARIO || 
            cliente.getTipoPerfil() == TipoPerfil.ADMINISTRADOR) {
            dias = 21; // Prazo estendido para funcionários
        }

        // Montagem do Objeto
        emprestimo.setCliente(cliente);
        emprestimo.setExemplar(exemplar);
        emprestimo.setDataEmprestimo(LocalDate.now());
        emprestimo.setDataDevolucaoPrevista(LocalDate.now().plusDays(dias));
        
        // Define o status do empréstimo (Seu Enum interno de Emprestimo)
        emprestimo.setStatus(Emprestimo.StatusEmprestimo.ATIVO);

        // 4. Atualiza o status do Livro para EMPRESTADO
        exemplar.setStatus(StatusExemplar.EMPRESTADO);
        exemplarRepository.save(exemplar);

        // Salva e Loga
        Emprestimo salvo = repository.save(emprestimo);
        log.info("Empréstimo realizado. ID: {}, Cliente: {}, Perfil: {}", 
                salvo.getId(), cliente.getNomeCompleto(), cliente.getTipoPerfil());
        
        return salvo;
    }

    //Os métodos update, delete, findAll e findById
    @Override
    public Emprestimo update(Emprestimo emprestimo) {
        Emprestimo existente = findById(emprestimo.getId());
        existente.setDataDevolucaoPrevista(emprestimo.getDataDevolucaoPrevista());
        existente.setStatus(emprestimo.getStatus());
        Emprestimo atualizado = repository.save(existente);
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