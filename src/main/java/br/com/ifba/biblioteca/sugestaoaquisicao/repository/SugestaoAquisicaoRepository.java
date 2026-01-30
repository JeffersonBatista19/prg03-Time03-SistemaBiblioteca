package br.com.ifba.biblioteca.sugestaoaquisicao.repository;

/**
 *
 * @author guilhermeAmedrado
 */

import br.com.ifba.biblioteca.sugestaoaquisicao.entity.SugestaoAquisicao;
import br.com.ifba.biblioteca.sugestaoaquisicao.entity.StatusSugestao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SugestaoAquisicaoRepository extends JpaRepository<SugestaoAquisicao, Long> {
    List<SugestaoAquisicao> findByTituloSugeridoContainingIgnoreCase(String titulo);
    List<SugestaoAquisicao> findByStatus(StatusSugestao status);
}
