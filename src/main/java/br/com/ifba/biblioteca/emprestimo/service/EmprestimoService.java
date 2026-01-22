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
import br.com.ifba.biblioteca.pessoa.entity.StatusPessoa;
import br.com.ifba.biblioteca.pessoa.entity.TipoPerfil;
import br.com.ifba.biblioteca.usuario.entity.Usuario;
import br.com.ifba.biblioteca.usuario.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class EmprestimoService implements EmprestimoIService {

    private final EmprestimoRepository repository;
    private final UsuarioRepository usuarioRepository; // Trocamos ClienteRepo por UsuarioRepo
    private final ExemplarRepository exemplarRepository;

    public EmprestimoService(EmprestimoRepository repository,UsuarioRepository usuarioRepository, ExemplarRepository exemplarRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.exemplarRepository = exemplarRepository;
    }

    @Override
    @Transactional
    public Emprestimo save(Emprestimo emprestimo) {
        // 1. Busca USUARIO e Exemplar
        Usuario usuario = usuarioRepository.findById(emprestimo.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        Exemplar exemplar = exemplarRepository.findById(emprestimo.getExemplar().getId())
                .orElseThrow(() -> new RuntimeException("Exemplar não encontrado."));

        // 2. Validações
        if (usuario.getStatusPessoa() != StatusPessoa.ATIVO) {
            throw new RuntimeException("Empréstimo negado: Usuário inativo ou bloqueado.");
        }

        // Verifica limite de livros (exemplo: 3 livros)
        if (repository.countByUsuarioAndStatusAtivo(usuario) >= 3) {
             throw new RuntimeException("Limite de empréstimos excedido.");
        }

        // 3. Regra de Data (CLIENTE = 14, FUNCIONARIO = 21)
        int dias = 14; 
        if (usuario.getTipoPerfil() == TipoPerfil.BIBLIOTECARIO || 
            usuario.getTipoPerfil() == TipoPerfil.ADMINISTRADOR) {
            dias = 21;
        }

        // Montagem
        emprestimo.setUsuario(usuario);
        emprestimo.setExemplar(exemplar);
        emprestimo.setDataEmprestimo(LocalDate.now());
        emprestimo.setDataDevolucaoPrevista(LocalDate.now().plusDays(dias));
        emprestimo.setStatus(Emprestimo.StatusEmprestimo.ATIVO);

        return repository.save(emprestimo);
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
//Atualização final