package br.com.ifba.biblioteca.evento.repository;

/**
 *
 * @author guilhermeAmedrado
 */

import br.com.ifba.biblioteca.evento.entity.Localfake;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalfakeRepository extends JpaRepository<Localfake, Long> {
}
