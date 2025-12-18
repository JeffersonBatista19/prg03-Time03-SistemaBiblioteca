package br.com.ifba.biblioteca.emprestimo.repository;

/**
 *
 * @author guilhermeAmedrado
 */

import br.com.ifba.biblioteca.emprestimo.entity.Emprestimo;
import br.com.ifba.biblioteca.pessoa.entity.Cliente; // 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    // Conta quantos empréstimos ATIVOS este CLIENTE possui
    @Query("SELECT COUNT(e) FROM Emprestimo e WHERE e.cliente = :cliente AND e.status = 'ATIVO'")
    long countByClienteAndStatusAtivo(Cliente cliente);
}