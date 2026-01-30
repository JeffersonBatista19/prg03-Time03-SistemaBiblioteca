package br.com.ifba.biblioteca.multa.repository;

import br.com.ifba.biblioteca.multa.entity.Multa;
import br.com.ifba.biblioteca.multa.entity.StatusMulta;
import br.com.ifba.biblioteca.emprestimo.entity.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MultaRepository extends JpaRepository<Multa, Long> {

    // Busca multa por empréstimo.
    Optional<Multa> findByEmprestimo(Emprestimo emprestimo);

    // filtros básicos.
    List<Multa> findByStatus(StatusMulta status);

    // filtro por data de geração.
    List<Multa> findByDataGeracaoBetween(LocalDate inicio, LocalDate fim);

    // filtro por valor.
    List<Multa> findByValorTotalBetween(
            java.math.BigDecimal min,
            java.math.BigDecimal max
    );

    // cliente (via relacionamento).
    List<Multa> findByEmprestimo_Cliente_NomeContainingIgnoreCase(String nome);

    // exemplar.
    List<Multa> findByEmprestimo_Exemplar_Livro_TituloContainingIgnoreCase(String titulo);

    // empréstimo ID.
    Optional<Multa> findByEmprestimo_Id(Long idEmprestimo);
}
