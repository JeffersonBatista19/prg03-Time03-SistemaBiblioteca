package br.com.ifba.biblioteca.evento.repository;

/**
 *
 * @author guilhermeAmedrado
 */

/**
 * Interface responsável pela comunicação com o banco de dados da tabela "eventos".
 * O Spring Data JPA implementa os comandos SQL automaticamente em tempo de execução.
 */

import br.com.ifba.biblioteca.evento.entity.Evento;
import br.com.ifba.biblioteca.evento.entity.StatusEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    
    // Consultas personalizadas seguindo os requisitos do projeto
    List<Evento> findByTituloContainingIgnoreCase(String titulo);
    List<Evento> findByDataHorarioBetween(LocalDateTime inicio, LocalDateTime fim);
    List<Evento> findByStatus(StatusEvento status);
    List<Evento> findByLocalEntityNomeContainingIgnoreCase(String localNome);
}