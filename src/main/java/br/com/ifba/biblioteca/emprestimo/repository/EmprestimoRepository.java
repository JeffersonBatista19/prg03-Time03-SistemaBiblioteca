package br.com.ifba.biblioteca.emprestimo.repository;

/**
 *
 * @author guilhermeAmedrado
 */

import br.com.ifba.biblioteca.emprestimo.entity.Emprestimo;
import br.com.ifba.biblioteca.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    // Conta quantos empréstimos ATIVOS este USUARIO possui
    // Note que mudei "e.cliente" para "e.usuario" na query
    @Query("SELECT COUNT(e) FROM Emprestimo e WHERE e.usuario = :usuario AND e.status = 'ATIVO'")
    long countByUsuarioAndStatusAtivo(Usuario usuario);
}