package br.com.ifba.biblioteca.multa.service;

import br.com.ifba.biblioteca.multa.entity.Multa;
import br.com.ifba.biblioteca.multa.entity.StatusMulta;
import br.com.ifba.biblioteca.multa.repository.MultaRepository;
import br.com.ifba.biblioteca.emprestimo.entity.Emprestimo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class MultaService implements MultaIService {

    @Autowired
    private MultaRepository multaRepository;

    // CRUD básico.

    @Override
    public Multa save(Multa multa) {
        return multaRepository.save(multa);
    }

    @Override
    public Multa update(Multa multa) {
        return multaRepository.save(multa);
    }

    @Override
    public void delete(Long id) {
        multaRepository.deleteById(id);
    }

    @Override
    public Optional<Multa> findById(Long id) {
        return multaRepository.findById(id);
    }

    @Override
    public Optional<Multa> findByEmprestimo(Emprestimo emprestimo) {
        return multaRepository.findByEmprestimo(emprestimo);
    }

    @Override
    public List<Multa> findAll() {
        return multaRepository.findAll();
    }

    // geração de multa.

    @Override
    public Multa gerarMultaManual(Emprestimo emprestimo, int diasAtraso, BigDecimal valorPorDia) {

        // Validação CRÍTICA: Verifica se há atraso real
        LocalDate hoje = LocalDate.now();
        LocalDate dataPrevista = emprestimo.getDataPrevistaDevolucao();
        
        // Se o empréstimo ainda não venceu, não pode gerar multa
        if (hoje.isBefore(dataPrevista) || hoje.isEqual(dataPrevista)) {
            throw new RuntimeException("Não é possível gerar multa: O empréstimo ainda não está atrasado. Data prevista de devolução: " + dataPrevista);
        }
        
        // Calcula os dias de atraso reais
        long diasAtrasoReais = ChronoUnit.DAYS.between(dataPrevista, hoje);
        
        // Se o usuário passou um valor diferente, usa o menor entre os dois (segurança)
        if (diasAtraso > diasAtrasoReais) {
            throw new RuntimeException("Dias de atraso informados (" + diasAtraso + ") são maiores que o atraso real (" + diasAtrasoReais + " dias).");
        }
        
        // Verifica se já existe multa para este empréstimo
        Optional<Multa> multaExistente = findByEmprestimo(emprestimo);
        if (multaExistente.isPresent()) {
            throw new RuntimeException("Já existe uma multa cadastrada para este empréstimo (ID: " + multaExistente.get().getId() + ").");
        }

        BigDecimal valorTotal = valorPorDia.multiply(BigDecimal.valueOf(diasAtraso));

        Multa multa = new Multa();
        multa.setEmprestimo(emprestimo);
        multa.setDiasAtraso(diasAtraso);
        multa.setValorPorDia(valorPorDia);
        multa.setValorTotal(valorTotal);
        multa.setDataGeracao(LocalDate.now());
        multa.setStatus(StatusMulta.PENDENTE);

        // ligação bidirecional.
        emprestimo.setMulta(multa);

        return multaRepository.save(multa);
    }

    @Override
    public Multa gerarMultaAutomatica(Emprestimo emprestimo) {

        if (emprestimo.getDataDevolucao() == null) {
            throw new RuntimeException("Empréstimo ainda não foi devolvido.");
        }

        long diasAtraso = ChronoUnit.DAYS.between(
                emprestimo.getDataPrevistaDevolucao(),
                emprestimo.getDataDevolucao()
        );

        if (diasAtraso <= 0) {
            return null;
        }

        BigDecimal valorPorDia = new BigDecimal("2.50");
        BigDecimal valorTotal = valorPorDia.multiply(BigDecimal.valueOf(diasAtraso));

        Multa multa = new Multa();
        multa.setEmprestimo(emprestimo);
        multa.setDiasAtraso((int) diasAtraso);
        multa.setValorPorDia(valorPorDia);
        multa.setValorTotal(valorTotal);
        multa.setDataGeracao(LocalDate.now());
        multa.setStatus(StatusMulta.PENDENTE);

        // ligação bidirecional.
        emprestimo.setMulta(multa);

        return multaRepository.save(multa);
    }

    // status da multa.

    @Override
    public Multa pagar(Long idMulta) {
        Multa multa = findById(idMulta)
                .orElseThrow(() -> new RuntimeException("Multa não encontrada"));
        multa.setStatus(StatusMulta.PAGA);
        return multaRepository.save(multa);
    }

    @Override
    public Multa cancelar(Long idMulta) {
        Multa multa = findById(idMulta)
                .orElseThrow(() -> new RuntimeException("Multa não encontrada"));
        multa.setStatus(StatusMulta.CANCELADA);
        return multaRepository.save(multa);
    }

    // filtros.

    @Override
    public List<Multa> findByStatus(StatusMulta status) {
        return multaRepository.findByStatus(status);
    }

    @Override
    public List<Multa> findByPeriodo(LocalDate inicio, LocalDate fim) {
        return multaRepository.findByDataGeracaoBetween(inicio, fim);
    }

    @Override
    public List<Multa> findByCliente(String nomeCliente) {
        return multaRepository
    .findByEmprestimo_Cliente_NomeCompletoContainingIgnoreCase(nomeCliente);

    }

  //  @Override
  //  public List<Multa> findByLivro(String tituloLivro) {
  //      return multaRepository.findByEmprestimo_Exemplar_Livro_TituloContainingIgnoreCase(tituloLivro);
  //  }
}
