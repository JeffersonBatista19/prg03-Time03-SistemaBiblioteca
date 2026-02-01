package br.com.ifba.biblioteca.emprestimo.repository;

/**
 *
 * @author guilhermeAmedrado
 */

import br.com.ifba.biblioteca.emprestimo.entity.Emprestimo;
import br.com.ifba.biblioteca.cliente.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    // Conta quantos empréstimos ATIVOS este CLIENTE possui
    @Query("SELECT COUNT(e) FROM Emprestimo e WHERE e.cliente = :cliente AND e.status = 'ATIVO'")
    long countByClienteAndStatusAtivo(Cliente cliente);

    // Conta quantos empréstimos ATRASADOS (ativos e ja passou da data) este CLIENTE possui
    @Query("SELECT COUNT(e) FROM Emprestimo e WHERE e.cliente = :cliente AND e.status = 'ATIVO' AND e.dataPrevistaDevolucao < CURRENT_DATE")
    long countLateLoansByCliente(Cliente cliente);

    // Conta quantas multas PENDENTES o cliente tem 
    @Query("SELECT COUNT(m) FROM Multa m JOIN m.emprestimo e WHERE e.cliente = :cliente AND m.status = 'PENDENTE'")
    long countPendingFinesByCliente(Cliente cliente);
}